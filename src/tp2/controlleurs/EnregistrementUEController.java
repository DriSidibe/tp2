/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2.controlleurs;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import tp2.dao.EnregistrerAnneeDao;
import tp2.dao.EnregistrerUeDao;
import tp2.helpers.dialogs;
import tp2.modeles.Annee;
import tp2.modeles.Ue;

/**
 * FXML Controller class
 *
 * @author drissa sidibe
 */
public class EnregistrementUEController implements Initializable {
    
    @FXML
    private Button enregistrerUE_vider_btn;
    @FXML
    private Button enregistrerUE_enregistrer_btn;
    @FXML
    private Button enregistrerUE_modifier_btn;
    @FXML
    private Button enregistrerUE_supprimer_btn;
    @FXML
    private Button annuler_mod;
    @FXML
    private TextField enregistrerUE_libelleUE;
    @FXML
    private TextField enregistrerUE_nbrHeureCM;
    @FXML
    private TextField enregistrerUE_nbrHeureTD;
    @FXML
    private TextField enregistrerUE_nbrHeureTP;
    @FXML
    private TextField enregistrerUE_nbrgroupeCM;
    @FXML
    private TextField enregistrerUE_nbrgroupeTD;
    @FXML
    private TextField enregistrerUE_nbrgroupeTP;
    @FXML
    private Text enregistrerUE_nbrUe;
    @FXML
    private TableView<Ue> enregistrerUE_UE_table;
    @FXML
    private TableColumn<Ue, String> enregistrerUE_UE_table_ue_col;
    @FXML
    private TableColumn<Ue, Integer> enregistrerUE_UE_table_heureCM_col;
    @FXML
    private TableColumn<Ue, Integer> enregistrerUE_UE_table_heureTD_col;
    @FXML
    private TableColumn<Ue, Integer> enregistrerUE_UE_table_heureTP_col;
    @FXML
    private TableColumn<Ue, Integer> enregistrerUE_UE_table_groupeCM_col;
    @FXML
    private TableColumn<Ue, Integer> enregistrerUE_UE_table_groupeTD_col;
    @FXML
    private TableColumn<Ue, Integer> enregistrerUE_UE_table_groupeTP_col;
    
    EnregistrerUeDao enregistrerUeDao = new EnregistrerUeDao();
    ObservableList<Ue> ueTableData = FXCollections.observableArrayList();
    Boolean isInModification = false;
    int ueInModificationId;
    int ueTotal = 0;
    Annee annee;
    EnregistrerAnneeDao anregistrerAnneeDao = new EnregistrerAnneeDao();
    
    @FXML
    private void enregistrer() throws IOException, Exception {
        int id = 0;
        if (!"".equals(enregistrerUE_libelleUE.getText()) && !"".equals(enregistrerUE_nbrHeureCM.getText()) && !"".equals(enregistrerUE_nbrHeureTD.getText()) && !"".equals(enregistrerUE_nbrHeureTP.getText()) && !"".equals(enregistrerUE_nbrgroupeCM.getText()) && !"".equals(enregistrerUE_nbrgroupeTD.getText()) && !"".equals(enregistrerUE_nbrgroupeTP.getText())) {
            if(isInModification){
                for (Ue ue : ueTableData) {
                    if (ue.getId() == ueInModificationId) {
                        ue.setLibelle(enregistrerUE_libelleUE.getText());
                        ue.setNombreGroupeCm(Integer.valueOf(enregistrerUE_nbrgroupeCM.getText()));
                        ue.setNombreGroupeTd(Integer.valueOf(enregistrerUE_nbrgroupeTD.getText()));
                        ue.setNombreGroupeTp(Integer.valueOf(enregistrerUE_nbrgroupeTP.getText()));
                        ue.setNombreHeureCm(Integer.valueOf(enregistrerUE_nbrHeureCM.getText()));
                        ue.setNombreHeureTd(Integer.valueOf(enregistrerUE_nbrHeureTD.getText()));
                        ue.setNombreHeureTp(Integer.valueOf(enregistrerUE_nbrHeureTP.getText()));
                        enregistrerUeDao.modifyUeInDatabase(ue);
                    }
                }
                isInModification = false;
                enregistrerUE_UE_table.setItems(ueTableData);
                enregistrerUE_UE_table.refresh();
                annuler_mod.setVisible(false);
                Alert info_box = dialogs.information("information", "Modification", "Person modified with success!");
                info_box.showAndWait();
            }else{
                for (Ue ue : ueTableData) {
                    if (id < ue.getId()) {
                        id = ue.getId();
                    }
                }
                Ue newUe = new Ue(enregistrerUE_libelleUE.getText(), Integer.valueOf(enregistrerUE_nbrHeureCM.getText()), Integer.valueOf(enregistrerUE_nbrHeureTD.getText()), Integer.valueOf(enregistrerUE_nbrHeureTP.getText()),Integer.valueOf(enregistrerUE_nbrHeureCM.getText()), Integer.valueOf(enregistrerUE_nbrHeureTD.getText()), Integer.valueOf(enregistrerUE_nbrHeureTP.getText()), Integer.valueOf(enregistrerUE_nbrgroupeCM.getText()), Integer.valueOf(enregistrerUE_nbrgroupeTD.getText()), Integer.valueOf(enregistrerUE_nbrgroupeTP.getText()), annee);
                enregistrerUeDao.createUeInDatabase(newUe);
                ueTableData.add(newUe);
                Alert info_box = dialogs.information("information", "Registration", "Person save with success!");
                info_box.showAndWait();
            }
            reinitialize_all_fields();
            ueTotal++;
            update();
        }
    }
    
    @FXML
    private void modifier() throws IOException {
        Alert error_box = dialogs.error("Error", "Modification", "Vous devez supprimer l'ue puis la recreer");
        error_box.showAndWait();
        /*
        Ue selectedUe = enregistrerUE_UE_table.getSelectionModel().getSelectedItem();
        if (selectedUe != null) {
            annuler_mod.setVisible(true);
            isInModification = true;
            enregistrerUE_libelleUE.setText(selectedUe.getLibelle());
            enregistrerUE_nbrHeureCM.setText(selectedUe.getNombreHeureCm().toString());
            enregistrerUE_nbrHeureTD.setText(selectedUe.getNombreHeureTd().toString());
            enregistrerUE_nbrHeureTP.setText(selectedUe.getNombreHeureTp().toString());
            enregistrerUE_nbrgroupeCM.setText(selectedUe.getNombreGroupeCm().toString());
            enregistrerUE_nbrgroupeTD.setText(selectedUe.getNombreGroupeTd().toString());
            enregistrerUE_nbrgroupeTP.setText(selectedUe.getNombreGroupeTp().toString());
            ueInModificationId = selectedUe.getId();
        }
        */
    }
    
    @FXML
    private void annuler_modification() throws IOException {
        isInModification = false;
        reinitialize_all_fields();
        annuler_mod.setVisible(false);
    }
    
    @FXML
    private void supprimer() throws Exception{
        Ue selectedUe = enregistrerUE_UE_table.getSelectionModel().getSelectedItem();
        if (selectedUe != null && !isInModification) {
            Alert confirm_box = dialogs.confirmation("confirmation", "Registration", "Etes vous sure de vouloir supprimer "+selectedUe.toString());
            Optional<ButtonType> result = confirm_box.showAndWait();
            if (result.get() == ButtonType.OK){
                ueTableData.remove(selectedUe);
                enregistrerUE_UE_table.getItems().remove(selectedUe);
                enregistrerUeDao.destroyUeInDatabase(selectedUe);
                ueTotal--;
                update();
            }
        }else{
            Alert war_box = dialogs.warning("warninig", "Avertissement", "vous etes en mode modification");
            war_box.showAndWait();
        }
    }
    
    @FXML
    private void vider() throws Exception{
        enregistrerUeDao.destroyUesInDatabase(annee);
        ueTableData.clear();
        ueTotal = 0;
        update();
    }
    
    public void reinitialize_all_fields(){
        enregistrerUE_libelleUE.setText("");
        enregistrerUE_nbrHeureCM.setText("");
        enregistrerUE_nbrHeureTD.setText("");
        enregistrerUE_nbrHeureTP.setText("");
        enregistrerUE_nbrgroupeCM.setText("");
        enregistrerUE_nbrgroupeTD.setText("");
        enregistrerUE_nbrgroupeTP.setText("");
    }
    
    public void update(){
        enregistrerUE_nbrUe.setText(ueTotal + "");
    }
    
    public void updateTable() throws Exception{
        ueTableData.clear();
        ueTableData.addAll(enregistrerUeDao.getUesInDatabase(annee));
    }
    
    public void setAnnee(Annee a){
        annee = a;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enregistrerUE_enregistrer_btn.setTooltip(new Tooltip("enregistrer un nouveau UE"));
        enregistrerUE_vider_btn.setTooltip(new Tooltip("vider le table des UEs"));
        enregistrerUE_modifier_btn.setTooltip(new Tooltip("modifier la ligne du tableau selectionnée"));
        enregistrerUE_supprimer_btn.setTooltip(new Tooltip("supprimer la ligne du tableau sélectionnée"));
        
        enregistrerUE_UE_table_ue_col.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        enregistrerUE_UE_table_heureCM_col.setCellValueFactory(new PropertyValueFactory<>("nombreHeureCm"));
        enregistrerUE_UE_table_heureTD_col.setCellValueFactory(new PropertyValueFactory<>("nombreHeureTd"));
        enregistrerUE_UE_table_heureTP_col.setCellValueFactory(new PropertyValueFactory<>("nombreHeureTp"));
        enregistrerUE_UE_table_groupeCM_col.setCellValueFactory(new PropertyValueFactory<>("nombreGroupeCm"));
        enregistrerUE_UE_table_groupeTD_col.setCellValueFactory(new PropertyValueFactory<>("nombreGroupeTd"));
        enregistrerUE_UE_table_groupeTP_col.setCellValueFactory(new PropertyValueFactory<>("nombreGroupeTp"));
        
        try {
            annee = anregistrerAnneeDao.getLastAnneesInDatabase();
        } catch (Exception ex) {
            Logger.getLogger(EnregistrementUEController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ueTableData.addAll(enregistrerUeDao.getUesInDatabase(annee));
        } catch (Exception ex) {
            Logger.getLogger(EnregistrementUEController.class.getName()).log(Level.SEVERE, null, ex);
        }
        enregistrerUE_UE_table.setItems(ueTableData);
        ueTotal = ueTableData.size();
        update();
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2.controlleurs;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import tp2.dao.EnregistrerAnneeDao;
import tp2.dao.EnregistrerAttributionDao;
import tp2.dao.EnregistrerEnseignantDao;
import tp2.modeles.Annee;
import tp2.modeles.Attribution;
import tp2.modeles.BilanTableModel;
import tp2.modeles.Enseignant;

/**
 * FXML Controller class
 *
 * @author drissa sidibe
 */
public class BilanController implements Initializable {
    @FXML
    private ChoiceBox<Enseignant> bilan_enseignat_chb;
    @FXML
    private TextField bilan_heureCM;
    @FXML
    private TextField bilan_heureTD;
    @FXML
    private TextField bilan_heureTP;
    @FXML
    private Text Bilan_total_effectuer;
    @FXML
    private TableView<BilanTableModel> bilan_table;
    @FXML
    private TableColumn<BilanTableModel, String> bilan_table_ue;
    @FXML
    private TableColumn<BilanTableModel, Integer> bilan_table_cm;
    @FXML
    private TableColumn<BilanTableModel, Integer> bilan_table_td;
    @FXML
    private TableColumn<BilanTableModel, Integer> bilan_table_tp;
    
    EnregistrerEnseignantDao enregistrerEnseignantDao = new EnregistrerEnseignantDao();
    EnregistrerAttributionDao enregistrerAttributionDao = new EnregistrerAttributionDao();
    ObservableList<Enseignant> enseignantChbData = FXCollections.observableArrayList();
    ObservableList<BilanTableModel> bilanTableModelTableData = FXCollections.observableArrayList();
    Enseignant selectedEnseigant;
    Annee annee;
    EnregistrerAnneeDao anregistrerAnneeDao = new EnregistrerAnneeDao();
    
    public void updateTotalHeureFields() throws Exception{
        if (selectedEnseigant != null) {
            bilan_heureCM.setText(selectedEnseigant.getQuotaCmAttribuer().toString());
            bilan_heureTD.setText(selectedEnseigant.getQuotaTdAttribuer().toString());
            bilan_heureTP.setText(selectedEnseigant.getQuotaTpAttribuer().toString());
            for (Enseignant enseignant : enregistrerEnseignantDao.getEnseignantsInDatabase(annee)) {
                if (Objects.equals(enseignant.getId(), selectedEnseigant.getId())) {
                    enseignant.getQuotaCmEffectuer();
                    enseignant.getQuotaTdEffectuer();
                    enseignant.getQuotaTpEffectuer();
                }
            }
        }
    }
    
    public void updateTable() throws Exception{
        if (selectedEnseigant != null) {
            int totalEffectuer = 0;
            for (Attribution attribution : enregistrerAttributionDao.getAttributionsInDatabase(annee)) {
                if (Objects.equals(attribution.getIdEnseignant().getId(), selectedEnseigant.getId())) {
                    attribution.getIdEnseignant(); 
                    bilanTableModelTableData.add(new BilanTableModel(attribution.getIdUe().getLibelle(), attribution.getQuotaCm(), attribution.getQuotaTd(), attribution.getQuotaTp(), annee));
                    switch (selectedEnseigant.getGrade()) {
                        case "MC":
                        case "PT":
                            totalEffectuer += (int) (selectedEnseigant.getQuotaCmEffectuer() + selectedEnseigant.getQuotaTdEffectuer()/1.6 + (selectedEnseigant.getQuotaTpEffectuer()/1.5)/1.6);
                            break;
                        case "MA":
                            totalEffectuer += (int) (selectedEnseigant.getQuotaTdEffectuer() + (selectedEnseigant.getQuotaTpEffectuer()/1.5)/1.6);
                            break;
                        case "As":
                            totalEffectuer += (int) (selectedEnseigant.getQuotaTdEffectuer() + selectedEnseigant.getQuotaTpEffectuer()*1.5);
                            break;
                    }
                }
            }
            Bilan_total_effectuer.setText(totalEffectuer+"");
        }
    }
    
    public void updateEnseignantChoiceBox() throws Exception{
        enseignantChbData.clear();
        enseignantChbData.addAll(enregistrerEnseignantDao.getEnseignantsInDatabase(annee));
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
        bilan_table_ue.setCellValueFactory(new PropertyValueFactory<>("ue"));
        bilan_table_cm.setCellValueFactory(new PropertyValueFactory<>("cm"));
        bilan_table_td.setCellValueFactory(new PropertyValueFactory<>("td"));
        bilan_table_tp.setCellValueFactory(new PropertyValueFactory<>("tp"));
        bilan_table.setItems(bilanTableModelTableData);
        
        try {
            annee = anregistrerAnneeDao.getLastAnneesInDatabase();
        } catch (Exception ex) {
            Logger.getLogger(EnregistrementUEController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            enseignantChbData.addAll(enregistrerEnseignantDao.getEnseignantsInDatabase(annee));
            bilan_enseignat_chb.setItems(enseignantChbData);
            bilan_enseignat_chb.getSelectionModel().selectedIndexProperty().addListener(
                    (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                try {
                    selectedEnseigant = enseignantChbData.get(new_val.intValue());
                    updateTotalHeureFields();
                    updateTable();
                } catch (Exception ex) {
                    Logger.getLogger(BilanController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    });
        } catch (Exception ex) {
            Logger.getLogger(BilanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}

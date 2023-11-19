/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2.controlleurs;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tp2.dao.AnneecourantDao;
import tp2.dao.EnregistrerAnneeDao;
import tp2.dao.EnregistrerEnseignantDao;
import tp2.helpers.dialogs;
import tp2.modeles.Annee;
import tp2.modeles.Enseignant;

/**
 * FXML Controller class
 *
 * @author drissa sidibe
 */
public class EnregistrementEnseignantController implements Initializable {
    @FXML
    private TextField enregistrer_enseignant_nom;
    @FXML
    private TextField enregistrer_enseignant_prenom;
    @FXML
    private TextField enregistrer_enseignant_telephone;
    @FXML
    private TextField enregistrer_enseignant_email;
    @FXML
    private ChoiceBox<String> enregistrer_enseignant_grade;
    @FXML
    private Button enregistrer_enseignant_enregistrer_btn;
    @FXML
    private Button enregistrer_enseignant_modifier_btn;
    @FXML
    private Button enregistrer_enseignant_supprimer_btn;
    @FXML
    private Button enregistrer_enseignant_vider_btn;
    @FXML
    private Button annuler_mod;
    @FXML
    private Label enregistrerEnseignant_nbrEnseignant;
    @FXML
    private TableView<Enseignant> enregistrer_enseignant_table;
    @FXML
    private TableColumn<Enseignant, String> enregistrer_enseignant_table_nom;
    @FXML
    private TableColumn<Enseignant, String> enregistrer_enseignant_table_prenom;
    @FXML
    private TableColumn<Enseignant, String> enregistrer_enseignant_table_telephone;
    @FXML
    private TableColumn<Enseignant, String> enregistrer_enseignant_table_email;
    @FXML
    private TableColumn<Enseignant, String> enregistrer_enseignant_table_grade;
    
    Annee annee;
    EnregistrerAnneeDao anregistrerAnneeDao = new EnregistrerAnneeDao();
    AnneecourantDao anneecourantDao = new AnneecourantDao();


    EnregistrerEnseignantDao enregistrerEnseignantDao = new EnregistrerEnseignantDao();
    ObservableList<Enseignant> enseignantTableData = FXCollections.observableArrayList();
    ObservableList<String> gradeChbData = FXCollections.observableArrayList("MC", "PT", "MA", "AS");
    Boolean isInModification = false;
    int enseignantInModificationId;
    int enseignantTotal = 0;
    
    @FXML
    private void enregistrer() throws IOException, Exception {
        int id = 0;
         if (enregistrer_enseignant_grade.getValue() != null && !"".equals(enregistrer_enseignant_nom.getText()) && !"".equals(enregistrer_enseignant_prenom.getText()) && !"".equals(enregistrer_enseignant_telephone.getText()) && !"".equals(enregistrer_enseignant_email.getText()) && !"".equals(enregistrer_enseignant_grade.getValue())) {
            if(isInModification){
                for (Enseignant enseignant : enseignantTableData) {
                    if (enseignant.getId() == enseignantInModificationId) {
                        enseignant.setNom(enregistrer_enseignant_nom.getText());
                        enseignant.setPrenom(enregistrer_enseignant_prenom.getText());
                        enseignant.setTel(enregistrer_enseignant_telephone.getText());
                        enseignant.setEmail(enregistrer_enseignant_email.getText());
                        enseignant.setGrade(enregistrer_enseignant_grade.getValue());
                        enregistrerEnseignantDao.modifyEnseignantInDatabase(enseignant);
                    }
                }
                isInModification = false;
                enregistrer_enseignant_table.setItems(enseignantTableData);
                enregistrer_enseignant_table.refresh();
                annuler_mod.setVisible(false);
                Alert info_box = dialogs.information("information", "Modification", "Person modified with success!");
                info_box.showAndWait();
            }else{
                int quota = 0;
                for (Enseignant enseignant : enseignantTableData) {
                    if (id < enseignant.getId()) {
                        id = enseignant.getId();
                    }
                }
                String grade = enregistrer_enseignant_grade.getValue();
                if (null != grade) switch (grade) {
                    case "MC":
                    case "PT":
                        quota = 150;
                        break;
                    case "MA":
                        quota = 240;
                        break;
                    case "AS":
                        quota = 360;
                        break;
                }
                Enseignant newenseignant = new Enseignant(enregistrer_enseignant_nom.getText(), enregistrer_enseignant_prenom.getText(), enregistrer_enseignant_telephone.getText(), enregistrer_enseignant_email.getText(), grade, quota, 0, 0, 0, 0, 0, 0, annee);
                enregistrerEnseignantDao.createEnseignantInDatabase(newenseignant);
                enseignantTableData.add(newenseignant);
                Alert info_box = dialogs.information("information", "Registration", "Person save with success!");
                info_box.showAndWait();
            }
            reinitialize_all_fields();
            enseignantTotal++;
            update();
        }
    }
    
    @FXML
    private void modifier() throws IOException {
        Enseignant selectedEnseignant = enregistrer_enseignant_table.getSelectionModel().getSelectedItem();
        if (selectedEnseignant != null) {
            annuler_mod.setVisible(true);
            isInModification = true;
            enregistrer_enseignant_nom.setText(selectedEnseignant.getNom());
            enregistrer_enseignant_prenom.setText(selectedEnseignant.getPrenom());
            enregistrer_enseignant_telephone.setText(selectedEnseignant.getTel());
            enregistrer_enseignant_email.setText(selectedEnseignant.getEmail());
            enregistrer_enseignant_grade.setValue(selectedEnseignant.getGrade());
            enseignantInModificationId = selectedEnseignant.getId();
        }
    }
    
    @FXML
    private void annuler_modification() throws IOException {
        isInModification = false;
        reinitialize_all_fields();
        annuler_mod.setVisible(false);
    }
    
    @FXML
    private void supprimer() throws Exception{
        Enseignant selectedEnseignant = enregistrer_enseignant_table.getSelectionModel().getSelectedItem();
        if (selectedEnseignant != null && !isInModification) {
            Alert confirm_box = dialogs.confirmation("confirmation", "Registration", "Etes vous sure de vouloir supprimer "+selectedEnseignant.toString());
            Optional<ButtonType> result = confirm_box.showAndWait();
            if (result.get() == ButtonType.OK){
                enseignantTableData.remove(selectedEnseignant);
                enregistrer_enseignant_table.getItems().remove(selectedEnseignant);
                enregistrerEnseignantDao.destroyEnseignantInDatabase(selectedEnseignant);
                enseignantTotal--;
                update();
            }
        }else{
            Alert war_box = dialogs.warning("warninig", "Avertissement", "vous etes en mode modification");
            war_box.showAndWait();
        }
    }
    
    @FXML
    private void vider() throws Exception{
        enregistrerEnseignantDao.destroyEnseignantsInDatabase(annee);
        enseignantTableData.clear();
        enseignantTotal = 0;
        update();
    }
    
    public void reinitialize_all_fields(){
        enregistrer_enseignant_nom.setText("");
        enregistrer_enseignant_prenom.setText("");
        enregistrer_enseignant_telephone.setText("");
        enregistrer_enseignant_email.setText("");
        enregistrer_enseignant_grade.setValue("");
    }
    
    public void update(){
        enregistrerEnseignant_nbrEnseignant.setText(enseignantTotal + "");
    }
    
    public void updateTable() throws Exception{
        enseignantTableData.clear();
        enseignantTableData.addAll(enregistrerEnseignantDao.getEnseignantsInDatabase(annee));
        if(!Objects.equals(anneecourantDao.getLastAnneecourantsInDatabase().getIdAnnee().getLabel(), annee.getLabel())){
            enregistrer_enseignant_enregistrer_btn.setDisable(true);
            enregistrer_enseignant_modifier_btn.setDisable(true);
            enregistrer_enseignant_supprimer_btn.setDisable(true);
            enregistrer_enseignant_vider_btn.setDisable(true);
        }else{
            enregistrer_enseignant_enregistrer_btn.setDisable(false);
            enregistrer_enseignant_modifier_btn.setDisable(false);
            enregistrer_enseignant_supprimer_btn.setDisable(false);
            enregistrer_enseignant_vider_btn.setDisable(false);
        }
        enseignantTotal = enseignantTableData.size();
        update();
    }
    
    public void setAnnee(Annee a) throws Exception{
        annee = a;
        updateTable();
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enregistrer_enseignant_enregistrer_btn.setTooltip(new Tooltip("enregistrer un nouveau enseignant"));
        enregistrer_enseignant_vider_btn.setTooltip(new Tooltip("vider le table des enseignants"));
        enregistrer_enseignant_modifier_btn.setTooltip(new Tooltip("modifier la ligne du tableau selectionnée"));
        enregistrer_enseignant_supprimer_btn.setTooltip(new Tooltip("supprimer la ligne du tableau sélectionnée"));
        
        enregistrer_enseignant_table_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        enregistrer_enseignant_table_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        enregistrer_enseignant_table_telephone.setCellValueFactory(new PropertyValueFactory<>("tel"));
        enregistrer_enseignant_table_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        enregistrer_enseignant_table_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        
        try {
            annee = anregistrerAnneeDao.getLastAnneesInDatabase();
        } catch (Exception ex) {
            Logger.getLogger(EnregistrementUEController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            enseignantTableData.addAll(enregistrerEnseignantDao.getEnseignantsInDatabase(annee));
        } catch (Exception ex) {
            Logger.getLogger(EnregistrementUEController.class.getName()).log(Level.SEVERE, null, ex);
        }
        enregistrer_enseignant_table.setItems(enseignantTableData);
        enseignantTotal = enseignantTableData.size();
        update();
        enregistrer_enseignant_grade.setItems(gradeChbData);
    }    
    
}

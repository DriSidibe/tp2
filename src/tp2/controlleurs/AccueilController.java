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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.dao.EnregistrerAnneeDao;
import tp2.dao.EnregistrerAttributionDao;
import tp2.dao.EnregistrerEnseignantDao;
import tp2.dao.EnregistrerUeDao;
import tp2.helpers.dialogs;
import tp2.modeles.Annee;

/**
 * FXML Controller class
 *
 * @author drissa sidibe
 */
public class AccueilController implements Initializable {
    @FXML
    private BorderPane container;
    @FXML
    private Button accuieil_ue;
    @FXML
    private Button accuieil_enseignant;
    @FXML
    private Button accuieil_attribution;
    @FXML
    private Button accuieil_bilan;
    @FXML
    private Button accuieil_reinitialiser;
    @FXML
    private Button accuieil_propos;
    @FXML
    private Button accuieil_annee;
    @FXML
    private ChoiceBox<Annee> annee_chb;
    @FXML
    private Button helper;
    @FXML
    private Label message;

    boolean isMessageUpdate = false;
    private Button currentButton;
    String clickedButtonName;
    EnregistrerAnneeDao enregistrerAnneeDao = new EnregistrerAnneeDao();
    ObservableList<Annee> anneeChbData = FXCollections.observableArrayList();
    Object currentController;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            anneeChbData.addAll(enregistrerAnneeDao.getAnneesInDatabase());
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        annee_chb.setItems(anneeChbData);
        try {
            Annee a = enregistrerAnneeDao.getLastAnneesInDatabase();
            if (a != null) {
                annee_chb.setValue(a);
            }
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentButton = helper;
        clickedButtonName = "ue";
        ActionEvent event = null;
        try {
            show_new_scene("EnregistrementUE", event);
            updateButtons(accuieil_ue);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        annee_chb.getSelectionModel().selectedIndexProperty().addListener(
                    (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                        Annee newAnnee = anneeChbData.get(new_val.intValue());
                        if (currentController instanceof BilanController) {
                            BilanController bilanController = new BilanController();
                            bilanController.setAnnee(annee_chb.getValue());
                            try {
                                bilanController.updateEnseignantChoiceBox();
                            } catch (Exception ex) {
                                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if (currentController instanceof RepartitionDesHeuresController) {
                            RepartitionDesHeuresController repartitionDesHeuresController = new RepartitionDesHeuresController();
                            repartitionDesHeuresController.setAnnee(annee_chb.getValue());
                        }else if (currentController instanceof EnregistrementEnseignantController) {
                            EnregistrementEnseignantController enregistrementEnseignantController = new EnregistrementEnseignantController();
                            enregistrementEnseignantController.setAnnee(annee_chb.getValue());
                            try {
                                enregistrementEnseignantController.updateTable();
                            } catch (Exception ex) {
                                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if (currentController instanceof EnregistrerUeDao) {
                            EnregistrementUEController enregistrementUEController = new EnregistrementUEController();
                            enregistrementUEController.setAnnee(annee_chb.getValue());
                            try {
                                enregistrementUEController.updateTable();
                            } catch (Exception ex) {
                                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
    }    

    @FXML
    private void enregistrerUE(ActionEvent event) throws IOException {
        if (!isMessageUpdate) {
            message.setText("chargement...");
        }
        container.setCenter(message);
        show_new_scene("EnregistrementUE", event);
        lookForReclick(accuieil_ue, "ue");
    }

    @FXML
    private void enregistrerEnseignant(ActionEvent event) throws IOException {
        if (!isMessageUpdate) {
            message.setText("chargement...");
        }
        container.setCenter(message);
        show_new_scene("EnregistrementEnseignant", event);
        lookForReclick(accuieil_enseignant, "enseignant");
    }

    @FXML
    private void attribuerHeures(ActionEvent event) throws IOException {
        if (!isMessageUpdate) {
            message.setText("chargement...");
        }
        container.setCenter(message);
        show_new_scene("RepartitionDesHeures", event);
        lookForReclick(accuieil_attribution, "attribution");
    }
    
    @FXML
    private void enregistrerAnnee(ActionEvent event) throws IOException {
        if (!isMessageUpdate) {
            message.setText("chargement...");
        }
        container.setCenter(message);
        show_new_scene("Annee", event);
        lookForReclick(accuieil_annee, "annee");
    }
    
    @FXML
    private void bilan(ActionEvent event) throws IOException {
        if (!isMessageUpdate) {
            message.setText("chargement...");
        }
        container.setCenter(message);
        show_new_scene("Bilan", event);
        lookForReclick(accuieil_bilan, "bilan");
    }
    
    @FXML
    private void reinitialiser(ActionEvent event) throws IOException, NonexistentEntityException {
        lookForReclick(accuieil_reinitialiser, "reinitialiser");
        Alert confirm_box = dialogs.confirmation("confirmation", "Reseting", "Etes vous sure de vouloir reinitialiser l'application ?");
        Optional<ButtonType> result = confirm_box.showAndWait();
        if (result.get() == ButtonType.OK){
            EnregistrerEnseignantDao enregistrerEnseignantDao = new EnregistrerEnseignantDao();
            EnregistrerUeDao enregistrerUeDao = new EnregistrerUeDao();
            EnregistrerAttributionDao enregistrerAttributionDao = new EnregistrerAttributionDao();

            enregistrerAttributionDao.empty();
            enregistrerEnseignantDao.empty();
            enregistrerUeDao.empty();
        }
    }
    
    @FXML
    private void apropos(ActionEvent event) throws IOException, NonexistentEntityException {
        if (!isMessageUpdate) {
            message.setText("chargement...");
        }
        container.setCenter(message);
        show_new_scene("Info", event);
        lookForReclick(accuieil_propos, "propos");
    }
    
    public void show_new_scene(String ressource, ActionEvent event) throws IOException{
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("tp2/vues/"+ressource+".fxml"));
        root = loader.load();
        if ("Bilan".equals(ressource)) {
            BilanController bilanController = loader.getController();
            bilanController.setAnnee(annee_chb.getValue());
            currentController = bilanController;
        }else if ("RepartitionDesHeures".equals(ressource)) {
            RepartitionDesHeuresController repartitionDesHeuresController = loader.getController();
            repartitionDesHeuresController.setAnnee(annee_chb.getValue());
            currentController = repartitionDesHeuresController;
        }else if ("EnregistrementEnseignant".equals(ressource)) {
            EnregistrementEnseignantController enregistrementEnseignantController = loader.getController();
            enregistrementEnseignantController.setAnnee(annee_chb.getValue());
            currentController = enregistrementEnseignantController;
        }else if ("EnregistrementUE".equals(ressource)) {
            EnregistrementUEController enregistrementUEController = loader.getController();
            enregistrementUEController.setAnnee(annee_chb.getValue());
            currentController = enregistrementUEController;
        }else if ("Annee".equals(ressource)) {
            AnneeController anneeController = loader.getController();
            anneeController.setStage((Stage) annee_chb.getScene().getWindow());
        }
        container.setCenter(root);
    }
    
    public void lookForReclick(Button b, String bName){
        if (clickedButtonName != bName) {
            updateButtons(b);
        }
        clickedButtonName = bName;
    }
    
    public void updateButtons(Button clicked){
        clicked.setStyle("-fx-background-color:#03b3ff; -fx-text-fill:white;");
        currentButton.setStyle("-fx-background-color:white; -fx-text-fill:black;");
        currentButton = clicked;
    }
    
}

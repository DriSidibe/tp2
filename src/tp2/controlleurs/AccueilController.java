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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.dao.AnneecourantDao;
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
    FXMLLoader currentLoader;
    Annee newAnnee;
    AnneecourantDao anneeCourantDao = new AnneecourantDao();

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
        currentButton = helper;
        clickedButtonName = "ue";
        ActionEvent event = null;
        try {
            show_new_scene("EnregistrementUE", event);
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateButtons(accuieil_ue);
        annee_chb.getSelectionModel().selectedIndexProperty().addListener(
                    (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                        newAnnee = anneeChbData.get(new_val.intValue());
                        if (currentController instanceof BilanController) {
                            try {
                                BilanController bilanController = currentLoader.getController();
                                bilanController.setAnnee(newAnnee);
                                try {
                                    bilanController.updateTable();
                                } catch (Exception ex) {
                                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (Exception ex) {
                                  Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if (currentController instanceof RepartitionDesHeuresController) {
                            try {
                                RepartitionDesHeuresController repartitionDesHeuresController = currentLoader.getController();
                                repartitionDesHeuresController.setAnnee(newAnnee);
                                try {
                                    repartitionDesHeuresController.updateTable();
                                } catch (Exception ex) {
                                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (Exception ex) {
                                  Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if (currentController instanceof EnregistrementEnseignantController) {
                            try {
                                EnregistrementEnseignantController enregistrementEnseignantController = currentLoader.getController();
                                enregistrementEnseignantController.setAnnee(newAnnee);
                                try {
                                    enregistrementEnseignantController.updateTable();
                                } catch (Exception ex) {
                                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (Exception ex) {
                                  Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if (currentController instanceof EnregistrementUEController) {
                            try {
                                EnregistrementUEController enregistrementUEController = currentLoader.getController();
                                enregistrementUEController.setAnnee(newAnnee);
                                try {
                                    enregistrementUEController.updateTable();
                                } catch (Exception ex) {
                                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (Exception ex) {
                                  Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if (currentController instanceof InfoController) {
                            try {
                                InfoController infoController = currentLoader.getController();
                                infoController.setAnnee(newAnnee);
                            } catch (Exception ex) {
                                  Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
        try {
            for (Annee annee : anneeChbData) {
                if (annee.getLabel() == null ? anneeCourantDao.getLastAnneecourantsInDatabase().getIdAnnee().getLabel() == null : annee.getLabel().equals(anneeCourantDao.getLastAnneecourantsInDatabase().getIdAnnee().getLabel())) {
                    annee_chb.setValue(annee);
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void enregistrerUE(ActionEvent event) throws IOException {
        try {
            if (!isMessageUpdate) {
                message.setText("chargement...");
            }
            container.setCenter(message);
            show_new_scene("EnregistrementUE", event);
            lookForReclick(accuieil_ue, "ue");
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void enregistrerEnseignant(ActionEvent event) throws IOException {
        try {
            if (!isMessageUpdate) {
                message.setText("chargement...");
            }
            container.setCenter(message);
            show_new_scene("EnregistrementEnseignant", event);
            lookForReclick(accuieil_enseignant, "enseignant");
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void attribuerHeures(ActionEvent event) throws IOException {
        try {
            if (!isMessageUpdate) {
                message.setText("chargement...");
            }
            container.setCenter(message);
            show_new_scene("RepartitionDesHeures", event);
            lookForReclick(accuieil_attribution, "attribution");
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void enregistrerAnnee(ActionEvent event) throws IOException {
        try {
            if (!isMessageUpdate) {
                message.setText("chargement...");
            }
            container.setCenter(message);
            show_new_scene("Annee", event);
            lookForReclick(accuieil_annee, "annee");
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void bilan(ActionEvent event) throws IOException {
        try {
            if (!isMessageUpdate) {
                message.setText("chargement...");
            }
            container.setCenter(message);
            show_new_scene("Bilan", event);
            lookForReclick(accuieil_bilan, "bilan");
        } catch (Exception ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Alert info_box = dialogs.information("information", "Enrégistrement", "L'application a ete reinitialiser avec succes\nL'applicationn va redemarer pour a prise en compte!");
            info_box.showAndWait();
            
            Stage stage = (Stage) accuieil_annee.getScene().getWindow();
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("tp2/vues/Accueil.fxml"));
            root = loader.load();
            stage.close();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    
    @FXML
    private void apropos(ActionEvent event) throws IOException, NonexistentEntityException, Exception {
        if (!isMessageUpdate) {
            message.setText("chargement...");
        }
        container.setCenter(message);
        show_new_scene("Info", event);
        lookForReclick(accuieil_propos, "propos");
    }
    
    @FXML
    public void changerAnnee(ActionEvent event) throws IOException{
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("tp2/vues/ChangerAnneeCourrante.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Changer annee courrante");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
            ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }
    
    public void show_new_scene(String ressource, ActionEvent event) throws IOException, Exception{
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("tp2/vues/"+ressource+".fxml"));
        root = loader.load();
        if (null != ressource) switch (ressource) {
            case "Bilan":
                BilanController bilanController = loader.getController();
                bilanController.setAnnee(newAnnee);
                currentController = bilanController;
                break;
            case "RepartitionDesHeures":
                RepartitionDesHeuresController repartitionDesHeuresController = loader.getController();
                repartitionDesHeuresController.setAnnee(newAnnee);
                currentController = repartitionDesHeuresController;
                break;
            case "EnregistrementEnseignant":
                EnregistrementEnseignantController enregistrementEnseignantController = loader.getController();
                enregistrementEnseignantController.setAnnee(newAnnee);
                currentController = enregistrementEnseignantController;
                break;
            case "EnregistrementUE":
                EnregistrementUEController enregistrementUEController = loader.getController();
                enregistrementUEController.setAnnee(newAnnee);
                currentController = enregistrementUEController;
                break;
            case "Annee":
                AnneeController anneeController = loader.getController();
                anneeController.setStage((Stage) annee_chb.getScene().getWindow());
                break;
            case "Info":
                InfoController infoController = loader.getController();
                infoController.setAnnee(newAnnee);
                currentController = infoController;
                break;
            default:
                break;
        }
        currentLoader = loader;
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

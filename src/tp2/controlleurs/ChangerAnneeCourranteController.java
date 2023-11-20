/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tp2.controlleurs;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.dao.AnneecourantDao;
import tp2.dao.EnregistrerAnneeDao;
import tp2.helpers.dialogs;
import tp2.modeles.Annee;
import tp2.modeles.Anneecourant;

/**
 * FXML Controller class
 *
 * @author drissa sidibe
 */
public class ChangerAnneeCourranteController implements Initializable {

    @FXML
    private ListView<Annee> listview;
    
    EnregistrerAnneeDao enregistrerAnneeDao = new EnregistrerAnneeDao();
    AnneecourantDao anneecourantDao = new AnneecourantDao();
    ObservableList<Annee> anneeListViewData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            anneeListViewData.addAll(enregistrerAnneeDao.getAnneesInDatabase());
        } catch (Exception ex) {
            Logger.getLogger(ChangerAnneeCourranteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listview.setItems(anneeListViewData);
    }    

    @FXML
    private void choisir(ActionEvent event) throws NonexistentEntityException {
        Annee seectedAnnee = listview.getSelectionModel().getSelectedItem();
        if (seectedAnnee != null) {
            anneecourantDao.deleteAnneecourantInDatabase();
            anneecourantDao.createAnneecourantInDatabase(new Anneecourant(seectedAnnee));
            Alert info_box = dialogs.information("information", "Registration", "Annee courrante modifiée avec success!");
            info_box.showAndWait();
        }
    }
    
}

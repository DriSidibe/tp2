/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp2.controlleurs;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tp2.dao.EnregistrerAnneeDao;
import tp2.helpers.dialogs;
import tp2.modeles.Annee;

/**
 *
 * @author drissa sidibe
 */
public class AnneeController {
    
    @FXML
    private TextField annee;
    
    Stage stage;
    
    EnregistrerAnneeDao enregistrerAnneeDao = new EnregistrerAnneeDao();
    
    public void setStage(Stage s){
        stage = s;
    }
    
    @FXML
    private void enregistrer() throws IOException{
        if (annee.getText() != "") {
            Annee a = new Annee(annee.getText());
            enregistrerAnneeDao.createAnneeInDatabase(a);
            Alert info_box = dialogs.information("information", "Registration", "Annee enregistrer avec success\nL'applicationn va redemarer pour a prise en compte!");
            info_box.showAndWait();
            
            annee.setText("");
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("tp2/vues/Accueil.fxml"));
            root = loader.load();
            stage.close();
            stage.setScene(new Scene(root));
            stage.show();
        }else{
            Alert error_box = dialogs.error("Error", "Annee vide", "Veuillez renseigner une annee universitaire svp.");
            error_box.showAndWait();
        }
    }
}

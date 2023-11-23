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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import tp2.dao.AnneecourantDao;
import tp2.dao.EnregistrerAnneeDao;
import tp2.dao.EnregistrerAttributionDao;
import tp2.dao.EnregistrerEnseignantDao;
import tp2.dao.EnregistrerUeDao;
import tp2.helpers.dialogs;
import tp2.modeles.Annee;
import tp2.modeles.Attribution;
import tp2.modeles.Enseignant;
import tp2.modeles.Ue;

/**
 * FXML Controller class
 *
 * @author drissa sidibe
 */
public class RepartitionDesHeuresController implements Initializable {
    @FXML
    private ChoiceBox<Ue> repartition_ue_chb;
    @FXML
    private ChoiceBox<Enseignant> repartition_enseignant_chb;
    @FXML
    private Circle repartition_deja_attribuer_circle;
    @FXML
    private Text repartition_ueAttribuer;
    @FXML
    private TextField repartition_seance_cm;
    @FXML
    private TextField repartition_seance_td;
    @FXML
    private TextField repartition_seance_tp;
    @FXML
    private Button repartition_enregistrer_btn;
    @FXML
    private Button repartition_modifier_btn;
    @FXML
    private Button repartition_supprimer_btn;
    @FXML
    private Button repartition_vider_btn;
    @FXML
    private Button annuler_mod;
    @FXML
    private Text repartition_info_dejaCM;
    @FXML
    private Text repartition_info_dejaTD;
    @FXML
    private Text repartition_info_dejaTP;
    @FXML
    private Text repartition_info_restantCM;
    @FXML
    private Text repartition_info_restantTD;
    @FXML
    private Text repartition_info_restantTP;
    @FXML
    private Text repartition_groupe_CM;
    @FXML
    private Text repartition_groupe_TD;
    @FXML
    private Text repartition_groupe_TP;
    @FXML
    private TableView<Attribution> repartition_table;
    @FXML
    private TableColumn<Attribution, String> repartition_table_nom;
    @FXML
    private TableColumn<Attribution, Integer> repartition_table_CM;
    @FXML
    private TableColumn<Attribution, Integer> repartition_table_TD;
    @FXML
    private TableColumn<Attribution, Integer> repartition_table_TP;
    
    Annee annee;
    EnregistrerAnneeDao anregistrerAnneeDao = new EnregistrerAnneeDao();
    
    EnregistrerEnseignantDao enregistrerEnseignantDao = new EnregistrerEnseignantDao();
    EnregistrerUeDao enregistrerUeDao = new EnregistrerUeDao();
    ObservableList<Ue> ueChbData = FXCollections.observableArrayList();
    ObservableList<Enseignant> enseignantChbData = FXCollections.observableArrayList();
    EnregistrerAttributionDao enregistrerAttributionDao = new EnregistrerAttributionDao();
    ObservableList<Attribution> attributionTableData = FXCollections.observableArrayList();
    Boolean isInModification = false;
    int attributionInModificationId;
    int attributionTotal = 0;
    Ue selectedUe_chb;
    Enseignant selectedEnseigant;
    AnneecourantDao anneecourantDao = new AnneecourantDao();
  
    @FXML
    private void enregistrer() throws IOException, Exception {
        boolean canUpdate = false;
        int id = 0;
        if (!isInModification && selectedUe_chb != null && !"".equals(repartition_seance_cm.getText()) && !"".equals(repartition_seance_td.getText()) && !"".equals(repartition_seance_tp.getText()) && (selectedUe_chb.getNombreHeureRestCm() < Integer.valueOf(repartition_seance_cm.getText()) || selectedUe_chb.getNombreHeureRestTd() < Integer.valueOf(repartition_seance_td.getText()) || selectedUe_chb.getNombreHeureRestTp() < Integer.valueOf(repartition_seance_tp.getText()))) {
            Alert error_box = dialogs.error("Erreur", "Atteinte de quota", "Un des quota de l'ue est atteint.");
            error_box.showAndWait();
        }else{
            if (!"".equals(repartition_seance_cm.getText()) && !"".equals(repartition_seance_td.getText()) && !"".equals(repartition_seance_tp.getText()) && selectedEnseigant != null && selectedUe_chb != null) {
                if(isInModification){
                    Attribution attributionInModification = enregistrerAttributionDao.getAttributionInDatabase(attributionInModificationId);
                    for (Attribution attribution : attributionTableData) {
                        if (attribution.getId() == attributionInModificationId) {
                            attribution.setIdEnseignant(selectedEnseigant);
                            attribution.setIdUe(selectedUe_chb);
                            selectedUe_chb.setNombreHeureRestCm(selectedUe_chb.getNombreHeureRestCm()+attributionInModification.getQuotaCm());
                            selectedUe_chb.setNombreHeureRestTd(selectedUe_chb.getNombreHeureRestTd()+attributionInModification.getQuotaTd());
                            selectedUe_chb.setNombreHeureRestTp(selectedUe_chb.getNombreHeureRestTp()+attributionInModification.getQuotaTp());
                            if (selectedUe_chb.getNombreHeureRestCm() >= Integer.valueOf(repartition_seance_cm.getText()) && selectedUe_chb.getNombreHeureRestTd() >= Integer.valueOf(repartition_seance_td.getText()) && selectedUe_chb.getNombreHeureRestTp() >= Integer.valueOf(repartition_seance_tp.getText())) {
                                int totalQuotaRenseigner = 0;
                                int ancienQuota = 0;
                                selectedEnseigant.setQuotaCmAttribuer(selectedEnseigant.getQuotaCmAttribuer()-attribution.getQuotaCm());
                                selectedEnseigant.setQuotaTdAttribuer(selectedEnseigant.getQuotaTdAttribuer()-attribution.getQuotaTd());
                                selectedEnseigant.setQuotaTpAttribuer(selectedEnseigant.getQuotaTpAttribuer()-attribution.getQuotaTp());
                                switch (selectedEnseigant.getGrade()) {
                                    case "MC":
                                    case "PT":
                                        ancienQuota = (int) (selectedEnseigant.getQuotaCmAttribuer() + selectedEnseigant.getQuotaTdAttribuer()/1.6 + (selectedEnseigant.getQuotaTpAttribuer()/1.5)/1.6);
                                        totalQuotaRenseigner = (int) (Integer.parseInt(repartition_seance_cm.getText()) + Integer.parseInt(repartition_seance_td.getText())/1.6 + (Integer.parseInt(repartition_seance_tp.getText())/1.5)/1.6);
                                        break;
                                    case "MA":
                                        ancienQuota = (int) (selectedEnseigant.getQuotaTdAttribuer() + (selectedEnseigant.getQuotaTpAttribuer()/1.5)/1.6);
                                        totalQuotaRenseigner = (int) (Integer.parseInt(repartition_seance_td.getText()) + Integer.parseInt(repartition_seance_tp.getText())/1.5);
                                        break;
                                    case "As":
                                        ancienQuota = (int) (selectedEnseigant.getQuotaTdAttribuer() + selectedEnseigant.getQuotaTpAttribuer()*1.5);
                                        totalQuotaRenseigner = (int) (Integer.parseInt(repartition_seance_td.getText()) + Integer.parseInt(repartition_seance_tp.getText())*1.5);
                                        break;
                                }
                                if (ancienQuota + totalQuotaRenseigner > selectedEnseigant.getQuotaRestant()) {
                                    selectedEnseigant.setQuotaCmAttribuer(selectedEnseigant.getQuotaCmAttribuer()+attribution.getQuotaCm());
                                    selectedEnseigant.setQuotaTdAttribuer(selectedEnseigant.getQuotaTdAttribuer()+attribution.getQuotaTd());
                                    selectedEnseigant.setQuotaTpAttribuer(selectedEnseigant.getQuotaTpAttribuer()+attribution.getQuotaTp());
                                    Alert error_box = dialogs.error("Erreur", "Atteinte de quota", "Le total des heures attribuer depasse le quota prevu pour l'enseignant");
                                    error_box.showAndWait();
                                    break;
                                            
                                }
                                attribution.setQuotaCm(Integer.valueOf(repartition_seance_cm.getText()));
                                attribution.setQuotaTd(Integer.valueOf(repartition_seance_td.getText()));
                                attribution.setQuotaTp(Integer.valueOf(repartition_seance_tp.getText()));
                                enregistrerAttributionDao.modifyAttributionInDatabase(attribution);
                                selectedEnseigant.setQuotaCmAttribuer(selectedEnseigant.getQuotaCmAttribuer()-attributionInModification.getQuotaCm());
                                selectedEnseigant.setQuotaTdAttribuer(selectedEnseigant.getQuotaTdAttribuer()-attributionInModification.getQuotaTd());
                                selectedEnseigant.setQuotaTpAttribuer(selectedEnseigant.getQuotaTpAttribuer()-attributionInModification.getQuotaTp());
                                enregistrerUeDao.modifyUeInDatabase(selectedUe_chb);
                                canUpdate = true;
                            }else{
                                selectedUe_chb.setNombreHeureRestCm(selectedUe_chb.getNombreHeureRestCm()-attributionInModification.getQuotaCm());
                                selectedUe_chb.setNombreHeureRestTd(selectedUe_chb.getNombreHeureRestTd()-attributionInModification.getQuotaTd());
                                selectedUe_chb.setNombreHeureRestTp(selectedUe_chb.getNombreHeureRestTp()-attributionInModification.getQuotaTp());
                                Alert error_box = dialogs.error("Erreur", "Atteinte de quota", "Un des quota de l'ue est atteint.");
                                error_box.showAndWait();
                            }
                            break;
                        }
                    }
                    if (canUpdate) {
                        isInModification = false;
                        repartition_table.setItems(attributionTableData);
                        repartition_table.refresh();
                        annuler_mod.setVisible(false);
                        Alert info_box = dialogs.information("information", "Modification", "Modification effectuée avec succes!");
                        info_box.showAndWait();
                        repartition_enseignant_chb.setDisable(false);
                        repartition_ue_chb.setDisable(false);
                    }
                }else{
                    boolean isOkForRegistretion = true;
                    for (Attribution a : enregistrerAttributionDao.getAttributionsInDatabase(annee)) {
                        if (Objects.equals(a.getIdUe().getId(), selectedUe_chb.getId()) && Objects.equals(a.getIdEnseignant().getId(), selectedEnseigant.getId())) {
                            Alert error_box = dialogs.error("Erreur", "Ue deja attribue", "Cette ue a deja ete attribué a ce enseignant.");
                            error_box.showAndWait();
                            isOkForRegistretion = false;
                            break;
                        }
                        Enseignant ens = a.getIdEnseignant();
                        if (Objects.equals(a.getIdUe().getId(), selectedUe_chb.getId()) && Objects.equals(a.getIdEnseignant().getId(), selectedEnseigant.getId()) && (("PT".equals(ens.getGrade()) || "MC".equals(ens.getGrade())) && Integer.parseInt(repartition_seance_cm.getText()) != 0)) {
                            if (("MC".equals(selectedEnseigant.getGrade()) || "PT".equals(selectedEnseigant.getGrade())) && Integer.parseInt(repartition_seance_cm.getText()) != 0) {
                                Alert error_box = dialogs.error("Erreur", "Ue deja attribue", "Le cm de cette ue a deja ete attribué a un enseignant.");
                                error_box.showAndWait();
                                isOkForRegistretion = false;
                                break;
                            }
                        }
                    }
                    if (isOkForRegistretion) {
                        int ancienQuota = 0;
                        int totalQuotaRenseigner;
                        switch (selectedEnseigant.getGrade()) {
                            case "MC":
                            case "PT":
                                {
                                    ancienQuota = (int) (selectedEnseigant.getQuotaCmAttribuer() + selectedEnseigant.getQuotaTdAttribuer()/1.6 + (selectedEnseigant.getQuotaTpAttribuer()/1.5)/1.6);
                                    totalQuotaRenseigner = (int) (Integer.parseInt(repartition_seance_cm.getText()) + Integer.parseInt(repartition_seance_td.getText())/1.6 + (Integer.parseInt(repartition_seance_tp.getText())/1.5)/1.6);
                                    if (totalQuotaRenseigner + ancienQuota <= selectedEnseigant.getQuotaRestant()) {
                                        for (Attribution attribution : attributionTableData) {
                                            if (id < attribution.getId()) {
                                                id = attribution.getId();
                                            }
                                        }
                                        Attribution newAttribution = new Attribution(selectedEnseigant.getNom()+" "+selectedEnseigant.getPrenom(), Integer.valueOf(repartition_seance_cm.getText()), Integer.valueOf(repartition_seance_td.getText()), Integer.valueOf(repartition_seance_tp.getText()), 0, 0, 0, annee, selectedEnseigant, selectedUe_chb);
                                        enregistrerAttributionDao.createAttributionInDatabase(newAttribution);
                                        attributionTableData.add(newAttribution);
                                        Alert info_box = dialogs.information("information", "Enregistrement", "Attribution effectuée avec succes!");
                                        info_box.showAndWait();
                                        canUpdate = true;
                                    }else{
                                        Alert error_box = dialogs.error("Error", "Atteinte de quota", "Le total des heures attribuer depasse le quota prevu pour l'enseignant");
                                        error_box.showAndWait();
                                    }       break;
                                }
                            case "MA":
                            {
                                ancienQuota = (int) (selectedEnseigant.getQuotaTdAttribuer() + (selectedEnseigant.getQuotaTpAttribuer()/1.5)/1.6);
                                totalQuotaRenseigner = (int) (Integer.parseInt(repartition_seance_td.getText()) + Integer.parseInt(repartition_seance_tp.getText())/1.5);
                                if (totalQuotaRenseigner + ancienQuota <= selectedEnseigant.getQuotaRestant()) {
                                    for (Attribution attribution : attributionTableData) {
                                        if (id < attribution.getId()) {
                                            id = attribution.getId();
                                        }
                                    }
                                    Attribution newAttribution = new Attribution(selectedEnseigant.getNom()+" "+selectedEnseigant.getPrenom(), Integer.valueOf(repartition_seance_cm.getText()), Integer.valueOf(repartition_seance_td.getText()), Integer.valueOf(repartition_seance_tp.getText()), 0, 0, 0, annee, selectedEnseigant, selectedUe_chb);
                                    enregistrerAttributionDao.createAttributionInDatabase(newAttribution);
                                    attributionTableData.add(newAttribution);
                                    Alert info_box = dialogs.information("information", "Enregistrement", "Person save with success!");
                                    info_box.showAndWait();
                                    canUpdate = true;
                                }else{
                                    Alert error_box = dialogs.error("Error", "Atteinte de quota", "Le total des heures attribuer depasse le quota prevu pour l'enseignant");
                                error_box.showAndWait();
                                }       break;
                            }
                            case "AS":
                            {
                                ancienQuota = (int) (selectedEnseigant.getQuotaTdAttribuer() + selectedEnseigant.getQuotaTpAttribuer()*1.5);
                                totalQuotaRenseigner = (int) (Integer.parseInt(repartition_seance_td.getText()) + Integer.parseInt(repartition_seance_tp.getText())*1.5);
                                if (totalQuotaRenseigner + ancienQuota <= selectedEnseigant.getQuotaRestant()) {
                                    for (Attribution attribution : attributionTableData) {
                                        if (id < attribution.getId()) {
                                            id = attribution.getId();
                                        }
                                    }
                                    Attribution newAttribution = new Attribution(selectedEnseigant.getNom()+" "+selectedEnseigant.getPrenom(), Integer.valueOf(repartition_seance_cm.getText()), Integer.valueOf(repartition_seance_td.getText()), Integer.valueOf(repartition_seance_tp.getText()), 0, 0, 0, annee, selectedEnseigant, selectedUe_chb);
                                    enregistrerAttributionDao.createAttributionInDatabase(newAttribution);
                                    attributionTableData.add(newAttribution);
                                    Alert info_box = dialogs.information("information", "Enregistrement", "Person save with success!");
                                    info_box.showAndWait();
                                    canUpdate = true;
                                }else{
                                    Alert error_box = dialogs.error("Erreur", "Atteinte de quota", "Le total des heures attribuer depasse le quota prevu pour l'enseignant");
                                error_box.showAndWait();
                            }       break;
                                }
                        }
                    }
                }
                if (canUpdate) {
                    megaUpdate();
                }
            }else{
                Alert error_box = dialogs.error("Erreur", "Champs vide", "Tous les champs pouvant etre renseignés doivent l'etre");
                error_box.showAndWait();
            }
        }
    }
    
    @FXML
    private void modifier() throws IOException {
        modifierFun(null);
    }
    
    private void modifierFun(Attribution attributionElm) throws IOException {
        Attribution selectedAttribution = repartition_table.getSelectionModel().getSelectedItem();
        if (attributionElm != null) {
            selectedAttribution = attributionElm;
        }
        if (selectedAttribution != null) {
            annuler_mod.setVisible(true);
            isInModification = true;
            repartition_enseignant_chb.setValue(selectedAttribution.getIdEnseignant());
            repartition_ue_chb.setValue(selectedAttribution.getIdUe());
            repartition_seance_cm.setText(selectedAttribution.getQuotaCm()+"");
            repartition_seance_td.setText(selectedAttribution.getQuotaTd()+"");
            repartition_seance_tp.setText(selectedAttribution.getQuotaTp()+"");
            attributionInModificationId = selectedAttribution.getId();
            repartition_enseignant_chb.setDisable(true);
            repartition_ue_chb.setDisable(true);
        }
    }
    
    @FXML
    private void annuler_modification() throws IOException {
        isInModification = false;
        reinitialize_all_fields();
        annuler_mod.setVisible(false);
        repartition_enseignant_chb.setDisable(false);
        repartition_ue_chb.setDisable(false);
    }
    
    @FXML
    private void supprimer() throws Exception{
        Attribution selectedAttribution = repartition_table.getSelectionModel().getSelectedItem();
        if (selectedAttribution != null && !isInModification) {
            Alert confirm_box = dialogs.confirmation("confirmation", "Enregistrement", "Etes vous sure de vouloir supprimer cet attribution?");
            Optional<ButtonType> result = confirm_box.showAndWait();
            if (result.get() == ButtonType.OK){
                Attribution att = enregistrerAttributionDao.getAttributionInDatabase(selectedAttribution);
                selectedUe_chb = att.getIdUe();
                selectedEnseigant = att.getIdEnseignant();
                selectedUe_chb.setNombreHeureRestCm(selectedUe_chb.getNombreHeureRestCm()+att.getQuotaCm());
                selectedUe_chb.setNombreHeureRestTd(selectedUe_chb.getNombreHeureRestTd()+att.getQuotaTd());
                selectedUe_chb.setNombreHeureRestTp(selectedUe_chb.getNombreHeureRestTp()+att.getQuotaTp());
                selectedEnseigant.setQuotaCmAttribuer(selectedEnseigant.getQuotaCmAttribuer()-att.getQuotaCm());
                selectedEnseigant.setQuotaTdAttribuer(selectedEnseigant.getQuotaTdAttribuer()-att.getQuotaTd());
                selectedEnseigant.setQuotaTpAttribuer(selectedEnseigant.getQuotaTpAttribuer()-att.getQuotaTp());
                enregistrerEnseignantDao.modifyEnseignantInDatabase(selectedEnseigant);
                attributionTableData.remove(selectedAttribution);
                repartition_table.getItems().remove(selectedAttribution);
                enregistrerAttributionDao.destroyAttributionInDatabase(selectedAttribution);
                reinitialize_all_fields();
                attributionTotal++;
                enregistrerUeDao.modifyUeInDatabase(selectedUe_chb);
                updateDataTable("ue", selectedUe_chb);
                updateInfo();
                attributionTotal--;
                
                for (Enseignant enseignant : enseignantChbData) {
                    if (Objects.equals(enseignant.getId(), selectedEnseigant.getId())) {
                        enseignant.setQuotaCmAttribuer(0);
                        enseignant.setQuotaTdAttribuer(0);
                        enseignant.setQuotaTpAttribuer(0);
                        break;
                    }
                }
            }
            updateNombreUeAttribuer();
        }else{
            Alert war_box = dialogs.warning("Avertissement", "Avertissement", "vous etes en mode modification");
            war_box.showAndWait();
        }
    }
    
    @FXML
    private void vider() throws Exception{
        Alert confirm_box = dialogs.confirmation("confirmation", "Vidage", "Etes vous sure de vouloir vider le tableau ?");
        Optional<ButtonType> result = confirm_box.showAndWait();
        if (result.get() == ButtonType.OK){
            enregistrerAttributionDao.destroyAttributionsInDatabase(annee);
            attributionTableData.clear();
            attributionTotal = 0;
            for (Ue ue : enregistrerUeDao.getUesInDatabase(annee)) {
                ue.setNombreHeureRestCm(ue.getNombreHeureCm());
                ue.setNombreHeureRestTd(ue.getNombreHeureTd());
                ue.setNombreHeureRestTp(ue.getNombreHeureTp());
                enregistrerUeDao.modifyUeInDatabase(ue);
            }
            for (Ue ue : ueChbData) {
                ue.setNombreHeureRestCm(ue.getNombreHeureCm());
                ue.setNombreHeureRestTd(ue.getNombreHeureTd());
                ue.setNombreHeureRestTp(ue.getNombreHeureTp());
            }
            for (Enseignant enseignant : enregistrerEnseignantDao.getEnseignantsInDatabase(annee)) {
                enseignant.setQuotaCmAttribuer(0);
                enseignant.setQuotaTdAttribuer(0);
                enseignant.setQuotaTpAttribuer(0);
                enregistrerEnseignantDao.modifyEnseignantInDatabase(enseignant);
            }
            for (Enseignant enseignant : enseignantChbData) {
                enseignant.setQuotaCmAttribuer(0);
                enseignant.setQuotaTdAttribuer(0);
                enseignant.setQuotaTpAttribuer(0);
            }
            updateInfo();
            annuler_modification();
            updateNombreUeAttribuer();
        }
    }
    
    public void reinitialize_all_fields(){
        repartition_seance_cm.setText("");
        repartition_seance_td.setText("");
        repartition_seance_tp.setText("");
    }
    
    public void updateInfo() throws Exception{
        boolean mod = false;
        if (selectedUe_chb != null) {
            repartition_info_dejaCM.setText(selectedUe_chb.getNombreHeureCm()-selectedUe_chb.getNombreHeureRestCm()+"");
            repartition_info_dejaTD.setText(selectedUe_chb.getNombreHeureTd()-selectedUe_chb.getNombreHeureRestTd()+"");
            repartition_info_dejaTP.setText(selectedUe_chb.getNombreHeureTp()-selectedUe_chb.getNombreHeureRestTp()+"");

            repartition_info_restantCM.setText(selectedUe_chb.getNombreHeureRestCm()+"");
            repartition_info_restantTD.setText(selectedUe_chb.getNombreHeureRestTd()+"");
            repartition_info_restantTP.setText(selectedUe_chb.getNombreHeureRestTp()+"");
            
            repartition_groupe_CM.setText(selectedUe_chb.getNombreGroupeCm()+"");
            repartition_groupe_TD.setText(selectedUe_chb.getNombreGroupeTd()+"");
            repartition_groupe_TP.setText(selectedUe_chb.getNombreGroupeTp()+"");
            
            if (selectedEnseigant != null) {
                for (Attribution a : enregistrerAttributionDao.getAttributionsInDatabase(annee)) {
                    if (Objects.equals(a.getIdEnseignant().getId(), selectedEnseigant.getId()) && Objects.equals(a.getIdUe().getId(), selectedUe_chb.getId())) {
                        mod = true;
                        repartition_deja_attribuer_circle.setFill(Paint.valueOf("red"));
                        break;
                    }
                }
            }
            if (!mod) {
                repartition_deja_attribuer_circle.setFill(Paint.valueOf("green"));
            }
        }
    }
    
    public void updateInfoUeAttribuer() throws Exception{
        boolean mod = false;
        if (selectedEnseigant != null) {
            if ("AS".equals(selectedEnseigant.getGrade()) || "MA".equals(selectedEnseigant.getGrade())) {
                repartition_seance_cm.setText("0");
                repartition_seance_cm.setEditable(false);
            }else{
                repartition_seance_cm.setText("");
                repartition_seance_cm.setEditable(true);
            }
            updateNombreUeAttribuer();
            if (selectedUe_chb != null) {
                for (Attribution a : enregistrerAttributionDao.getAttributionsInDatabase(annee)) {
                    if (Objects.equals(a.getIdEnseignant().getId(), selectedEnseigant.getId()) && Objects.equals(a.getIdUe().getId(), selectedUe_chb.getId())) {
                        mod = true;
                        repartition_deja_attribuer_circle.setFill(Paint.valueOf("red"));
                        break;
                    }
                }
            }
            if (!mod) {
                repartition_deja_attribuer_circle.setFill(Paint.valueOf("green"));
            }
        }
    }
    
    public void updateNombreUeAttribuer() throws Exception{
        int count = 0;
        if (selectedEnseigant != null) {
            for (Attribution a : enregistrerAttributionDao.getAttributionsInDatabase(annee)) {
                if (Objects.equals(a.getIdEnseignant(), selectedEnseigant)) {
                    count++;
                }
            }
        }
        repartition_ueAttribuer.setText(count+"");
    }
    
    public void updateDataTable(String type, Object data) throws Exception{
        if ("ue".equals(type)) {
            Ue UeData = (Ue) data;
            ueChbData.stream().filter((ue) -> (Objects.equals(ue.getId(), UeData.getId()))).forEach((ue) -> {
                ue = UeData;
            });
        }
    }
    
    public void megaUpdate() throws Exception{
        selectedUe_chb.setNombreHeureRestCm(selectedUe_chb.getNombreHeureRestCm()-Integer.valueOf(repartition_seance_cm.getText()));
        selectedUe_chb.setNombreHeureRestTd(selectedUe_chb.getNombreHeureRestTd()-Integer.valueOf(repartition_seance_td.getText()));
        selectedUe_chb.setNombreHeureRestTp(selectedUe_chb.getNombreHeureRestTp()-Integer.valueOf(repartition_seance_tp.getText()));
        selectedEnseigant.setQuotaCmAttribuer(selectedEnseigant.getQuotaCmAttribuer()+Integer.valueOf(repartition_seance_cm.getText()));
        selectedEnseigant.setQuotaTdAttribuer(selectedEnseigant.getQuotaTdAttribuer()+Integer.valueOf(repartition_seance_td.getText()));
        selectedEnseigant.setQuotaTpAttribuer(selectedEnseigant.getQuotaTpAttribuer()+Integer.valueOf(repartition_seance_tp.getText()));
        enregistrerEnseignantDao.modifyEnseignantInDatabase(selectedEnseigant);
        enregistrerUeDao.modifyUeInDatabase(selectedUe_chb);
        reinitialize_all_fields();
        attributionTotal++;
        updateDataTable("ue", selectedUe_chb);
        updateInfo();
        repartition_enseignant_chb.setDisable(false);
        repartition_ue_chb.setDisable(false);
        updateNombreUeAttribuer();
    }
    
    public void updateTable() throws Exception{
        attributionTableData.clear();
        ueChbData.clear();
        enseignantChbData.clear();
        attributionTableData.setAll(enregistrerAttributionDao.getAttributionsInDatabase(annee));
        ueChbData.addAll(enregistrerUeDao.getUesInDatabase(annee));
        enseignantChbData.addAll(enregistrerEnseignantDao.getEnseignantsInDatabase(annee));
        repartition_seance_cm.setText("");
        repartition_seance_td.setText("");
        repartition_seance_tp.setText("");
        
        repartition_info_dejaCM.setText("0");
        repartition_info_dejaTD.setText("0");
        repartition_info_dejaTP.setText("0");

        repartition_info_restantCM.setText("0");
        repartition_info_restantTD.setText("0");
        repartition_info_restantTP.setText("0");
            
        repartition_groupe_CM.setText("0");
        repartition_groupe_TD.setText("0");
        repartition_groupe_TP.setText("0");
        
        if(!Objects.equals(anneecourantDao.getLastAnneecourantsInDatabase().getIdAnnee().getLabel(), annee.getLabel())){
            repartition_enregistrer_btn.setDisable(true);
            repartition_modifier_btn.setDisable(true);
            repartition_supprimer_btn.setDisable(true);
            repartition_vider_btn.setDisable(true);
        }else{
            repartition_enregistrer_btn.setDisable(false);
            repartition_modifier_btn.setDisable(false);
            repartition_supprimer_btn.setDisable(false);
            repartition_vider_btn.setDisable(false);
        }
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
        try {
            repartition_enregistrer_btn.setTooltip(new Tooltip("enregistrer un nouveau l'attribution"));
            repartition_vider_btn.setTooltip(new Tooltip("vider le table des attributions"));
            repartition_modifier_btn.setTooltip(new Tooltip("modifier la ligne du tableau selectionnée"));
            repartition_supprimer_btn.setTooltip(new Tooltip("supprimer la ligne du tableau sélectionnée"));
            
            try {
                annee = anregistrerAnneeDao.getLastAnneesInDatabase();
            } catch (Exception ex) {
                Logger.getLogger(EnregistrementUEController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            repartition_table_nom.setCellValueFactory(new PropertyValueFactory<>("enseignantFullName"));
            repartition_table_CM.setCellValueFactory(new PropertyValueFactory<>("quotaCm"));
            repartition_table_TD.setCellValueFactory(new PropertyValueFactory<>("quotaTd"));
            repartition_table_TP.setCellValueFactory(new PropertyValueFactory<>("quotaTp"));
            repartition_table.setItems(attributionTableData);
            attributionTableData.setAll(enregistrerAttributionDao.getAttributionsInDatabase(annee));
            
            ueChbData.addAll(enregistrerUeDao.getUesInDatabase(annee));
            enseignantChbData.addAll(enregistrerEnseignantDao.getEnseignantsInDatabase(annee));
            repartition_ue_chb.setItems(ueChbData);
            repartition_enseignant_chb.setItems(enseignantChbData);
            repartition_ue_chb.getSelectionModel().selectedIndexProperty().addListener(
                    (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                        if (!ueChbData.isEmpty()) {;
                            selectedUe_chb = ueChbData.get(new_val.intValue());
                        }
                try {
                    updateInfo();
                } catch (Exception ex) {
                    Logger.getLogger(RepartitionDesHeuresController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    });
            repartition_enseignant_chb.getSelectionModel().selectedIndexProperty().addListener(
                    (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                        if (!enseignantChbData.isEmpty()) {;
                            selectedEnseigant = enseignantChbData.get(new_val.intValue());
                        }
                try {
                    updateInfoUeAttribuer();
                } catch (Exception ex) {
                    Logger.getLogger(RepartitionDesHeuresController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    });
        } catch (Exception ex) {
            Logger.getLogger(RepartitionDesHeuresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
}

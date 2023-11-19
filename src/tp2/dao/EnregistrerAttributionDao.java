/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tp2.controlleurs.database.AttributionJpaController;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.modeles.Annee;
import tp2.modeles.Attribution;

/**
 *
 * @author drissa sidibe
 */
public class EnregistrerAttributionDao {
    private final AttributionJpaController attributionJpaController;
    private final EntityManagerFactory emf;

    public EnregistrerAttributionDao() {
        emf = Persistence.createEntityManagerFactory("tp2PU");
        attributionJpaController = new AttributionJpaController(emf);
    }
    
    public void createAttributionInDatabase(Attribution attribution) throws SQLException{
        String password = "what up?";
//        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tp2?user=drissa&password="+password)) {
//            if (con != null) {
//                Statement statement = con.createStatement();
//                String values = "'"+attribution.getIdEnseignant().getId()+"'"+","+"'"+attribution.getIdUe().getId()+"'"+","+"'"+attribution.getIdEnseignant().getNom()+"'"+","+"'"+attribution.getQuotaCm()+"'"+","+"'"+attribution.getQuotaTd()+"'"+","+"'"+attribution.getQuotaTp()+"'"+","+"'"+attribution.getQuotaCmEffectuer()+"'"+","+"'"+attribution.getQuotaTdEffectuer()+"'"+","+"'"+attribution.getQuotaTpEffectuer()+"'";
//                    statement.execute("INSERT INTO attribution (id_enseignant, id_ue, enseignant_full_name, quota_cm, quota_td, quota_tp, quota_cm_effectuer, quota_td_effectuer, quota_tp_effectuer) VALUES ("+values+");");
//            }
//        }
        attributionJpaController.create(attribution);
    }
    
    public void modifyAttributionInDatabase(Attribution attribution) throws Exception{
        attributionJpaController.edit(attribution);
    }
    
    public void destroyAttributionInDatabase(Attribution attribution) throws Exception{
        attributionJpaController.destroy(attribution.getId());
    }
    
    public void destroyAttributionsInDatabase(Annee annee) throws Exception{
        for (Attribution attribution : getAttributionsInDatabase(annee)) {
            attributionJpaController.destroy(attribution.getId());
        }
    }
    
    public Attribution getAttributionInDatabase(Attribution attribution) throws Exception{
        return attributionJpaController.findAttribution(attribution.getId());
    }
    
    public Attribution getAttributionInDatabase(int id) throws Exception{
        return attributionJpaController.findAttribution(id);
    }
    
    public List<Attribution> getAttributionsInDatabase(Annee annee) throws Exception{
        List<Attribution> listAttribution = new ArrayList<>();
        for (Attribution attribution : attributionJpaController.findAttributionEntities()) {
            if (attribution.getIdAnnee() != null) {
                if (Objects.equals(attribution.getIdAnnee().getId(), annee.getId())) {
                    listAttribution.add(attribution);
                }
            }
        }
        return listAttribution;
    }

    public void empty() throws NonexistentEntityException {
        for (Attribution attribution : attributionJpaController.findAttributionEntities()) {
            attributionJpaController.destroy(attribution.getId());
        }
    }
}
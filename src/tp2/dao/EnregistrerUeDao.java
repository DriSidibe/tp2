/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tp2.controlleurs.database.UeJpaController;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.modeles.Annee;
import tp2.modeles.Ue;

/**
 *
 * @author drissa sidibe
 */
public class EnregistrerUeDao {
    private UeJpaController ueJpaController;
    private EntityManagerFactory emf;

    public EnregistrerUeDao() {
        emf = Persistence.createEntityManagerFactory("tp2PU");
        ueJpaController = new UeJpaController(emf);
    }
    
    public void createUeInDatabase(Ue ue){
        ueJpaController.create(ue);
    }
    
    public void modifyUeInDatabase(Ue ue) throws Exception{
        ueJpaController.edit(ue);
    }
    
    public void destroyUeInDatabase(Ue ue) throws Exception{
        ueJpaController.destroy(ue.getId());
    }
    
    public void destroyUesInDatabase(Annee annee) throws Exception{
        for (Ue ue : getUesInDatabase(annee)) {
            ueJpaController.destroy(ue.getId());
        }
    }
    
    public void getUeInDatabase(Ue ue) throws Exception{
        ueJpaController.findUe(ue.getId());
    }
    
    public List<Ue> getUesInDatabase(Annee annee) throws Exception{
        List<Ue> listEnseignant = new ArrayList<>();
        for (Ue ue : ueJpaController.findUeEntities()) {
            if (ue.getIdAnnee() != null) {
                if (Objects.equals(ue.getIdAnnee().getId(), annee.getId())) {
                    listEnseignant.add(ue);
                }
            }
        }
        return listEnseignant;
    }

    public void empty() throws NonexistentEntityException {
        for (Ue ue : ueJpaController.findUeEntities()) {
            ueJpaController.destroy(ue.getId());
        }
    }
}

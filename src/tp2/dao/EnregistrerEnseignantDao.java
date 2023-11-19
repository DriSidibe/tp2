/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tp2.controlleurs.database.EnseignantJpaController;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.modeles.Annee;
import tp2.modeles.Enseignant;

/**
 *
 * @author drissa sidibe
 */
public class EnregistrerEnseignantDao {
    private final EnseignantJpaController enseignantJpaController;
    private final EntityManagerFactory emf;

    public EnregistrerEnseignantDao() {
        emf = Persistence.createEntityManagerFactory("tp2PU");
        enseignantJpaController = new EnseignantJpaController(emf);
    }
    
    public void createEnseignantInDatabase(Enseignant enseignant){
        enseignantJpaController.create(enseignant);
    }
    
    public void modifyEnseignantInDatabase(Enseignant enseignant) throws Exception{
        enseignantJpaController.edit(enseignant);
    }
    
    public void destroyEnseignantInDatabase(Enseignant enseignant) throws Exception{
        enseignantJpaController.destroy(enseignant.getId());
    }
    
    public void destroyEnseignantsInDatabase(Annee annee) throws Exception{
        for (Enseignant enseignant : getEnseignantsInDatabase(annee)) {
            enseignantJpaController.destroy(enseignant.getId());
        }
    }
    
    public void getEnseignantInDatabase(Enseignant enseignant) throws Exception{
        enseignantJpaController.findEnseignant(enseignant.getId());
    }
    
    public List<Enseignant> getEnseignantsInDatabase(Annee annee) throws Exception{
        List<Enseignant> listEnseignant = new ArrayList<>();
        for (Enseignant enseignant : enseignantJpaController.findEnseignantEntities()) {
            if (enseignant.getIdAnnee() != null) {
                if (enseignant.getIdAnnee().getId() == annee.getId()) {
                    listEnseignant.add(enseignant);
                }
            }
        }
        return listEnseignant;
    }

    public void empty() throws NonexistentEntityException {
        for (Enseignant enseignant : enseignantJpaController.findEnseignantEntities()) {
            enseignantJpaController.destroy(enseignant.getId());
        }
    }
}

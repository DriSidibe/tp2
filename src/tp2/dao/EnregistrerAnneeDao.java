/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp2.dao;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tp2.controlleurs.database.AnneeJpaController;
import tp2.modeles.Annee;

/**
 *
 * @author drissa sidibe
 */
public class EnregistrerAnneeDao {
    private AnneeJpaController anneeJpaController;
    private EntityManagerFactory emf;

    public EnregistrerAnneeDao() {
        emf = Persistence.createEntityManagerFactory("tp2PU");
        anneeJpaController = new AnneeJpaController(emf);
    }
    
    public void createAnneeInDatabase(Annee annee){
        anneeJpaController.create(annee);
    }
    
    public void getAnneeInDatabase(Annee annee) throws Exception{
        anneeJpaController.findAnnee(annee.getId());
    }
    
    public List<Annee> getAnneesInDatabase() throws Exception{
        return anneeJpaController.findAnneeEntities();
    }
    
    public Annee getLastAnneesInDatabase() throws Exception{
        int id = 0;
        for (Annee a : anneeJpaController.findAnneeEntities()) {
            if (id < a.getId()) {
                id = a.getId();
            }
        }
        return anneeJpaController.findAnnee(id);
    }
}

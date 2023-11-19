/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp2.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tp2.controlleurs.database.AnneecourantJpaController;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.modeles.Anneecourant;

/**
 *
 * @author drissa sidibe
 */
public class AnneecourantDao {
    private final AnneecourantJpaController anneecourantJpaController;
    private final EntityManagerFactory emf;

    public AnneecourantDao() {
        emf = Persistence.createEntityManagerFactory("tp2PU");
        anneecourantJpaController = new AnneecourantJpaController(emf);
    }

    public void createAnneecourantInDatabase(Anneecourant anneecourant){
        anneecourantJpaController.create(anneecourant);
    }
    
    public void deleteAnneecourantInDatabase() throws NonexistentEntityException{
        for (Anneecourant anneecourant : anneecourantJpaController.findAnneecourantEntities()) {
            anneecourantJpaController.destroy(anneecourant.getId());
        }
    }

    public Anneecourant getLastAnneecourantsInDatabase() throws Exception{
        int id = 0;
        for (Anneecourant a : anneecourantJpaController.findAnneecourantEntities()) {
            if (id < a.getId()) {
                id = a.getId();
            }
        }
        return anneecourantJpaController.findAnneecourant(id);
    }
}

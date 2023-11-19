/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp2.controlleurs.database;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.modeles.Annee;
import tp2.modeles.Anneecourant;

/**
 *
 * @author drissa sidibe
 */
public class AnneecourantJpaController implements Serializable {

    public AnneecourantJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anneecourant anneecourant) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Annee idAnnee = anneecourant.getIdAnnee();
            if (idAnnee != null) {
                idAnnee = em.getReference(idAnnee.getClass(), idAnnee.getId());
                anneecourant.setIdAnnee(idAnnee);
            }
            em.persist(anneecourant);
            if (idAnnee != null) {
                idAnnee.getAnneecourantList().add(anneecourant);
                idAnnee = em.merge(idAnnee);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anneecourant anneecourant) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anneecourant persistentAnneecourant = em.find(Anneecourant.class, anneecourant.getId());
            Annee idAnneeOld = persistentAnneecourant.getIdAnnee();
            Annee idAnneeNew = anneecourant.getIdAnnee();
            if (idAnneeNew != null) {
                idAnneeNew = em.getReference(idAnneeNew.getClass(), idAnneeNew.getId());
                anneecourant.setIdAnnee(idAnneeNew);
            }
            anneecourant = em.merge(anneecourant);
            if (idAnneeOld != null && !idAnneeOld.equals(idAnneeNew)) {
                idAnneeOld.getAnneecourantList().remove(anneecourant);
                idAnneeOld = em.merge(idAnneeOld);
            }
            if (idAnneeNew != null && !idAnneeNew.equals(idAnneeOld)) {
                idAnneeNew.getAnneecourantList().add(anneecourant);
                idAnneeNew = em.merge(idAnneeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = anneecourant.getId();
                if (findAnneecourant(id) == null) {
                    throw new NonexistentEntityException("The anneecourant with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Anneecourant anneecourant;
            try {
                anneecourant = em.getReference(Anneecourant.class, id);
                anneecourant.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anneecourant with id " + id + " no longer exists.", enfe);
            }
            Annee idAnnee = anneecourant.getIdAnnee();
            if (idAnnee != null) {
                idAnnee.getAnneecourantList().remove(anneecourant);
                idAnnee = em.merge(idAnnee);
            }
            em.remove(anneecourant);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Anneecourant> findAnneecourantEntities() {
        return findAnneecourantEntities(true, -1, -1);
    }

    public List<Anneecourant> findAnneecourantEntities(int maxResults, int firstResult) {
        return findAnneecourantEntities(false, maxResults, firstResult);
    }

    private List<Anneecourant> findAnneecourantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anneecourant.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Anneecourant findAnneecourant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anneecourant.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnneecourantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anneecourant> rt = cq.from(Anneecourant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

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
import tp2.modeles.Enseignant;

/**
 *
 * @author drissa sidibe
 */
public class EnseignantJpaController1 implements Serializable {

    public EnseignantJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enseignant enseignant) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Annee idAnnee = enseignant.getIdAnnee();
            if (idAnnee != null) {
                idAnnee = em.getReference(idAnnee.getClass(), idAnnee.getId());
                enseignant.setIdAnnee(idAnnee);
            }
            em.persist(enseignant);
            if (idAnnee != null) {
                idAnnee.getEnseignantList().add(enseignant);
                idAnnee = em.merge(idAnnee);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enseignant enseignant) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enseignant persistentEnseignant = em.find(Enseignant.class, enseignant.getId());
            Annee idAnneeOld = persistentEnseignant.getIdAnnee();
            Annee idAnneeNew = enseignant.getIdAnnee();
            if (idAnneeNew != null) {
                idAnneeNew = em.getReference(idAnneeNew.getClass(), idAnneeNew.getId());
                enseignant.setIdAnnee(idAnneeNew);
            }
            enseignant = em.merge(enseignant);
            if (idAnneeOld != null && !idAnneeOld.equals(idAnneeNew)) {
                idAnneeOld.getEnseignantList().remove(enseignant);
                idAnneeOld = em.merge(idAnneeOld);
            }
            if (idAnneeNew != null && !idAnneeNew.equals(idAnneeOld)) {
                idAnneeNew.getEnseignantList().add(enseignant);
                idAnneeNew = em.merge(idAnneeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = enseignant.getId();
                if (findEnseignant(id) == null) {
                    throw new NonexistentEntityException("The enseignant with id " + id + " no longer exists.");
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
            Enseignant enseignant;
            try {
                enseignant = em.getReference(Enseignant.class, id);
                enseignant.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enseignant with id " + id + " no longer exists.", enfe);
            }
            Annee idAnnee = enseignant.getIdAnnee();
            if (idAnnee != null) {
                idAnnee.getEnseignantList().remove(enseignant);
                idAnnee = em.merge(idAnnee);
            }
            em.remove(enseignant);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enseignant> findEnseignantEntities() {
        return findEnseignantEntities(true, -1, -1);
    }

    public List<Enseignant> findEnseignantEntities(int maxResults, int firstResult) {
        return findEnseignantEntities(false, maxResults, firstResult);
    }

    private List<Enseignant> findEnseignantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enseignant.class));
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

    public Enseignant findEnseignant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enseignant.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnseignantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enseignant> rt = cq.from(Enseignant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

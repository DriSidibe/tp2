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
import tp2.modeles.Ue;

/**
 *
 * @author drissa sidibe
 */
public class UeJpaController implements Serializable {

    public UeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ue ue) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Annee idAnnee = ue.getIdAnnee();
            if (idAnnee != null) {
                idAnnee = em.getReference(idAnnee.getClass(), idAnnee.getId());
                ue.setIdAnnee(idAnnee);
            }
            em.persist(ue);
            if (idAnnee != null) {
                idAnnee.getUeList().add(ue);
                idAnnee = em.merge(idAnnee);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ue ue) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ue persistentUe = em.find(Ue.class, ue.getId());
            Annee idAnneeOld = persistentUe.getIdAnnee();
            Annee idAnneeNew = ue.getIdAnnee();
            if (idAnneeNew != null) {
                idAnneeNew = em.getReference(idAnneeNew.getClass(), idAnneeNew.getId());
                ue.setIdAnnee(idAnneeNew);
            }
            ue = em.merge(ue);
            if (idAnneeOld != null && !idAnneeOld.equals(idAnneeNew)) {
                idAnneeOld.getUeList().remove(ue);
                idAnneeOld = em.merge(idAnneeOld);
            }
            if (idAnneeNew != null && !idAnneeNew.equals(idAnneeOld)) {
                idAnneeNew.getUeList().add(ue);
                idAnneeNew = em.merge(idAnneeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ue.getId();
                if (findUe(id) == null) {
                    throw new NonexistentEntityException("The ue with id " + id + " no longer exists.");
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
            Ue ue;
            try {
                ue = em.getReference(Ue.class, id);
                ue.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ue with id " + id + " no longer exists.", enfe);
            }
            Annee idAnnee = ue.getIdAnnee();
            if (idAnnee != null) {
                idAnnee.getUeList().remove(ue);
                idAnnee = em.merge(idAnnee);
            }
            em.remove(ue);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ue> findUeEntities() {
        return findUeEntities(true, -1, -1);
    }

    public List<Ue> findUeEntities(int maxResults, int firstResult) {
        return findUeEntities(false, maxResults, firstResult);
    }

    private List<Ue> findUeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ue.class));
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

    public Ue findUe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ue.class, id);
        } finally {
            em.close();
        }
    }

    public int getUeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ue> rt = cq.from(Ue.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

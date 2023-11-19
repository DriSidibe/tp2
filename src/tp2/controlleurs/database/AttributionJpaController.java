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
import tp2.modeles.Attribution;
import tp2.modeles.Ue;

/**
 *
 * @author drissa sidibe
 */
public class AttributionJpaController implements Serializable {

    public AttributionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attribution attribution) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ue idUe = attribution.getIdUe();
            if (idUe != null) {
                idUe = em.getReference(idUe.getClass(), idUe.getId());
                attribution.setIdUe(idUe);
            }
            em.persist(attribution);
            if (idUe != null) {
                idUe.getAttributionList().add(attribution);
                idUe = em.merge(idUe);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Attribution attribution) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attribution persistentAttribution = em.find(Attribution.class, attribution.getId());
            Ue idUeOld = persistentAttribution.getIdUe();
            Ue idUeNew = attribution.getIdUe();
            if (idUeNew != null) {
                idUeNew = em.getReference(idUeNew.getClass(), idUeNew.getId());
                attribution.setIdUe(idUeNew);
            }
            attribution = em.merge(attribution);
            if (idUeOld != null && !idUeOld.equals(idUeNew)) {
                idUeOld.getAttributionList().remove(attribution);
                idUeOld = em.merge(idUeOld);
            }
            if (idUeNew != null && !idUeNew.equals(idUeOld)) {
                idUeNew.getAttributionList().add(attribution);
                idUeNew = em.merge(idUeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attribution.getId();
                if (findAttribution(id) == null) {
                    throw new NonexistentEntityException("The attribution with id " + id + " no longer exists.");
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
            Attribution attribution;
            try {
                attribution = em.getReference(Attribution.class, id);
                attribution.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attribution with id " + id + " no longer exists.", enfe);
            }
            Ue idUe = attribution.getIdUe();
            if (idUe != null) {
                idUe.getAttributionList().remove(attribution);
                idUe = em.merge(idUe);
            }
            em.remove(attribution);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Attribution> findAttributionEntities() {
        return findAttributionEntities(true, -1, -1);
    }

    public List<Attribution> findAttributionEntities(int maxResults, int firstResult) {
        return findAttributionEntities(false, maxResults, firstResult);
    }

    private List<Attribution> findAttributionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attribution.class));
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

    public Attribution findAttribution(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attribution.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttributionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attribution> rt = cq.from(Attribution.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

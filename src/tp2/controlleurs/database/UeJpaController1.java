/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp2.controlleurs.database;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tp2.modeles.Annee;
import tp2.modeles.Attribution;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.modeles.Ue;

/**
 *
 * @author drissa sidibe
 */
public class UeJpaController1 implements Serializable {

    public UeJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ue ue) {
        if (ue.getAttributionList() == null) {
            ue.setAttributionList(new ArrayList<Attribution>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Annee idAnnee = ue.getIdAnnee();
            if (idAnnee != null) {
                idAnnee = em.getReference(idAnnee.getClass(), idAnnee.getId());
                ue.setIdAnnee(idAnnee);
            }
            List<Attribution> attachedAttributionList = new ArrayList<Attribution>();
            for (Attribution attributionListAttributionToAttach : ue.getAttributionList()) {
                attributionListAttributionToAttach = em.getReference(attributionListAttributionToAttach.getClass(), attributionListAttributionToAttach.getId());
                attachedAttributionList.add(attributionListAttributionToAttach);
            }
            ue.setAttributionList(attachedAttributionList);
            em.persist(ue);
            if (idAnnee != null) {
                idAnnee.getUeList().add(ue);
                idAnnee = em.merge(idAnnee);
            }
            for (Attribution attributionListAttribution : ue.getAttributionList()) {
                Ue oldIdUeOfAttributionListAttribution = attributionListAttribution.getIdUe();
                attributionListAttribution.setIdUe(ue);
                attributionListAttribution = em.merge(attributionListAttribution);
                if (oldIdUeOfAttributionListAttribution != null) {
                    oldIdUeOfAttributionListAttribution.getAttributionList().remove(attributionListAttribution);
                    oldIdUeOfAttributionListAttribution = em.merge(oldIdUeOfAttributionListAttribution);
                }
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
            List<Attribution> attributionListOld = persistentUe.getAttributionList();
            List<Attribution> attributionListNew = ue.getAttributionList();
            if (idAnneeNew != null) {
                idAnneeNew = em.getReference(idAnneeNew.getClass(), idAnneeNew.getId());
                ue.setIdAnnee(idAnneeNew);
            }
            List<Attribution> attachedAttributionListNew = new ArrayList<Attribution>();
            for (Attribution attributionListNewAttributionToAttach : attributionListNew) {
                attributionListNewAttributionToAttach = em.getReference(attributionListNewAttributionToAttach.getClass(), attributionListNewAttributionToAttach.getId());
                attachedAttributionListNew.add(attributionListNewAttributionToAttach);
            }
            attributionListNew = attachedAttributionListNew;
            ue.setAttributionList(attributionListNew);
            ue = em.merge(ue);
            if (idAnneeOld != null && !idAnneeOld.equals(idAnneeNew)) {
                idAnneeOld.getUeList().remove(ue);
                idAnneeOld = em.merge(idAnneeOld);
            }
            if (idAnneeNew != null && !idAnneeNew.equals(idAnneeOld)) {
                idAnneeNew.getUeList().add(ue);
                idAnneeNew = em.merge(idAnneeNew);
            }
            for (Attribution attributionListOldAttribution : attributionListOld) {
                if (!attributionListNew.contains(attributionListOldAttribution)) {
                    attributionListOldAttribution.setIdUe(null);
                    attributionListOldAttribution = em.merge(attributionListOldAttribution);
                }
            }
            for (Attribution attributionListNewAttribution : attributionListNew) {
                if (!attributionListOld.contains(attributionListNewAttribution)) {
                    Ue oldIdUeOfAttributionListNewAttribution = attributionListNewAttribution.getIdUe();
                    attributionListNewAttribution.setIdUe(ue);
                    attributionListNewAttribution = em.merge(attributionListNewAttribution);
                    if (oldIdUeOfAttributionListNewAttribution != null && !oldIdUeOfAttributionListNewAttribution.equals(ue)) {
                        oldIdUeOfAttributionListNewAttribution.getAttributionList().remove(attributionListNewAttribution);
                        oldIdUeOfAttributionListNewAttribution = em.merge(oldIdUeOfAttributionListNewAttribution);
                    }
                }
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
            List<Attribution> attributionList = ue.getAttributionList();
            for (Attribution attributionListAttribution : attributionList) {
                attributionListAttribution.setIdUe(null);
                attributionListAttribution = em.merge(attributionListAttribution);
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

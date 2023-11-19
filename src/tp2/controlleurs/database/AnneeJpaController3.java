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
import tp2.modeles.Ue;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import tp2.controlleurs.database.exceptions.NonexistentEntityException;
import tp2.modeles.Annee;
import tp2.modeles.Enseignant;
import tp2.modeles.Anneecourant;

/**
 *
 * @author drissa sidibe
 */
public class AnneeJpaController3 implements Serializable {

    public AnneeJpaController3(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Annee annee) {
        if (annee.getUeList() == null) {
            annee.setUeList(new ArrayList<Ue>());
        }
        if (annee.getEnseignantList() == null) {
            annee.setEnseignantList(new ArrayList<Enseignant>());
        }
        if (annee.getAnneecourantList() == null) {
            annee.setAnneecourantList(new ArrayList<Anneecourant>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ue> attachedUeList = new ArrayList<Ue>();
            for (Ue ueListUeToAttach : annee.getUeList()) {
                ueListUeToAttach = em.getReference(ueListUeToAttach.getClass(), ueListUeToAttach.getId());
                attachedUeList.add(ueListUeToAttach);
            }
            annee.setUeList(attachedUeList);
            List<Enseignant> attachedEnseignantList = new ArrayList<Enseignant>();
            for (Enseignant enseignantListEnseignantToAttach : annee.getEnseignantList()) {
                enseignantListEnseignantToAttach = em.getReference(enseignantListEnseignantToAttach.getClass(), enseignantListEnseignantToAttach.getId());
                attachedEnseignantList.add(enseignantListEnseignantToAttach);
            }
            annee.setEnseignantList(attachedEnseignantList);
            List<Anneecourant> attachedAnneecourantList = new ArrayList<Anneecourant>();
            for (Anneecourant anneecourantListAnneecourantToAttach : annee.getAnneecourantList()) {
                anneecourantListAnneecourantToAttach = em.getReference(anneecourantListAnneecourantToAttach.getClass(), anneecourantListAnneecourantToAttach.getId());
                attachedAnneecourantList.add(anneecourantListAnneecourantToAttach);
            }
            annee.setAnneecourantList(attachedAnneecourantList);
            em.persist(annee);
            for (Ue ueListUe : annee.getUeList()) {
                Annee oldIdAnneeOfUeListUe = ueListUe.getIdAnnee();
                ueListUe.setIdAnnee(annee);
                ueListUe = em.merge(ueListUe);
                if (oldIdAnneeOfUeListUe != null) {
                    oldIdAnneeOfUeListUe.getUeList().remove(ueListUe);
                    oldIdAnneeOfUeListUe = em.merge(oldIdAnneeOfUeListUe);
                }
            }
            for (Enseignant enseignantListEnseignant : annee.getEnseignantList()) {
                Annee oldIdAnneeOfEnseignantListEnseignant = enseignantListEnseignant.getIdAnnee();
                enseignantListEnseignant.setIdAnnee(annee);
                enseignantListEnseignant = em.merge(enseignantListEnseignant);
                if (oldIdAnneeOfEnseignantListEnseignant != null) {
                    oldIdAnneeOfEnseignantListEnseignant.getEnseignantList().remove(enseignantListEnseignant);
                    oldIdAnneeOfEnseignantListEnseignant = em.merge(oldIdAnneeOfEnseignantListEnseignant);
                }
            }
            for (Anneecourant anneecourantListAnneecourant : annee.getAnneecourantList()) {
                Annee oldIdAnneeOfAnneecourantListAnneecourant = anneecourantListAnneecourant.getIdAnnee();
                anneecourantListAnneecourant.setIdAnnee(annee);
                anneecourantListAnneecourant = em.merge(anneecourantListAnneecourant);
                if (oldIdAnneeOfAnneecourantListAnneecourant != null) {
                    oldIdAnneeOfAnneecourantListAnneecourant.getAnneecourantList().remove(anneecourantListAnneecourant);
                    oldIdAnneeOfAnneecourantListAnneecourant = em.merge(oldIdAnneeOfAnneecourantListAnneecourant);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Annee annee) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Annee persistentAnnee = em.find(Annee.class, annee.getId());
            List<Ue> ueListOld = persistentAnnee.getUeList();
            List<Ue> ueListNew = annee.getUeList();
            List<Enseignant> enseignantListOld = persistentAnnee.getEnseignantList();
            List<Enseignant> enseignantListNew = annee.getEnseignantList();
            List<Anneecourant> anneecourantListOld = persistentAnnee.getAnneecourantList();
            List<Anneecourant> anneecourantListNew = annee.getAnneecourantList();
            List<Ue> attachedUeListNew = new ArrayList<Ue>();
            for (Ue ueListNewUeToAttach : ueListNew) {
                ueListNewUeToAttach = em.getReference(ueListNewUeToAttach.getClass(), ueListNewUeToAttach.getId());
                attachedUeListNew.add(ueListNewUeToAttach);
            }
            ueListNew = attachedUeListNew;
            annee.setUeList(ueListNew);
            List<Enseignant> attachedEnseignantListNew = new ArrayList<Enseignant>();
            for (Enseignant enseignantListNewEnseignantToAttach : enseignantListNew) {
                enseignantListNewEnseignantToAttach = em.getReference(enseignantListNewEnseignantToAttach.getClass(), enseignantListNewEnseignantToAttach.getId());
                attachedEnseignantListNew.add(enseignantListNewEnseignantToAttach);
            }
            enseignantListNew = attachedEnseignantListNew;
            annee.setEnseignantList(enseignantListNew);
            List<Anneecourant> attachedAnneecourantListNew = new ArrayList<Anneecourant>();
            for (Anneecourant anneecourantListNewAnneecourantToAttach : anneecourantListNew) {
                anneecourantListNewAnneecourantToAttach = em.getReference(anneecourantListNewAnneecourantToAttach.getClass(), anneecourantListNewAnneecourantToAttach.getId());
                attachedAnneecourantListNew.add(anneecourantListNewAnneecourantToAttach);
            }
            anneecourantListNew = attachedAnneecourantListNew;
            annee.setAnneecourantList(anneecourantListNew);
            annee = em.merge(annee);
            for (Ue ueListOldUe : ueListOld) {
                if (!ueListNew.contains(ueListOldUe)) {
                    ueListOldUe.setIdAnnee(null);
                    ueListOldUe = em.merge(ueListOldUe);
                }
            }
            for (Ue ueListNewUe : ueListNew) {
                if (!ueListOld.contains(ueListNewUe)) {
                    Annee oldIdAnneeOfUeListNewUe = ueListNewUe.getIdAnnee();
                    ueListNewUe.setIdAnnee(annee);
                    ueListNewUe = em.merge(ueListNewUe);
                    if (oldIdAnneeOfUeListNewUe != null && !oldIdAnneeOfUeListNewUe.equals(annee)) {
                        oldIdAnneeOfUeListNewUe.getUeList().remove(ueListNewUe);
                        oldIdAnneeOfUeListNewUe = em.merge(oldIdAnneeOfUeListNewUe);
                    }
                }
            }
            for (Enseignant enseignantListOldEnseignant : enseignantListOld) {
                if (!enseignantListNew.contains(enseignantListOldEnseignant)) {
                    enseignantListOldEnseignant.setIdAnnee(null);
                    enseignantListOldEnseignant = em.merge(enseignantListOldEnseignant);
                }
            }
            for (Enseignant enseignantListNewEnseignant : enseignantListNew) {
                if (!enseignantListOld.contains(enseignantListNewEnseignant)) {
                    Annee oldIdAnneeOfEnseignantListNewEnseignant = enseignantListNewEnseignant.getIdAnnee();
                    enseignantListNewEnseignant.setIdAnnee(annee);
                    enseignantListNewEnseignant = em.merge(enseignantListNewEnseignant);
                    if (oldIdAnneeOfEnseignantListNewEnseignant != null && !oldIdAnneeOfEnseignantListNewEnseignant.equals(annee)) {
                        oldIdAnneeOfEnseignantListNewEnseignant.getEnseignantList().remove(enseignantListNewEnseignant);
                        oldIdAnneeOfEnseignantListNewEnseignant = em.merge(oldIdAnneeOfEnseignantListNewEnseignant);
                    }
                }
            }
            for (Anneecourant anneecourantListOldAnneecourant : anneecourantListOld) {
                if (!anneecourantListNew.contains(anneecourantListOldAnneecourant)) {
                    anneecourantListOldAnneecourant.setIdAnnee(null);
                    anneecourantListOldAnneecourant = em.merge(anneecourantListOldAnneecourant);
                }
            }
            for (Anneecourant anneecourantListNewAnneecourant : anneecourantListNew) {
                if (!anneecourantListOld.contains(anneecourantListNewAnneecourant)) {
                    Annee oldIdAnneeOfAnneecourantListNewAnneecourant = anneecourantListNewAnneecourant.getIdAnnee();
                    anneecourantListNewAnneecourant.setIdAnnee(annee);
                    anneecourantListNewAnneecourant = em.merge(anneecourantListNewAnneecourant);
                    if (oldIdAnneeOfAnneecourantListNewAnneecourant != null && !oldIdAnneeOfAnneecourantListNewAnneecourant.equals(annee)) {
                        oldIdAnneeOfAnneecourantListNewAnneecourant.getAnneecourantList().remove(anneecourantListNewAnneecourant);
                        oldIdAnneeOfAnneecourantListNewAnneecourant = em.merge(oldIdAnneeOfAnneecourantListNewAnneecourant);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = annee.getId();
                if (findAnnee(id) == null) {
                    throw new NonexistentEntityException("The annee with id " + id + " no longer exists.");
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
            Annee annee;
            try {
                annee = em.getReference(Annee.class, id);
                annee.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The annee with id " + id + " no longer exists.", enfe);
            }
            List<Ue> ueList = annee.getUeList();
            for (Ue ueListUe : ueList) {
                ueListUe.setIdAnnee(null);
                ueListUe = em.merge(ueListUe);
            }
            List<Enseignant> enseignantList = annee.getEnseignantList();
            for (Enseignant enseignantListEnseignant : enseignantList) {
                enseignantListEnseignant.setIdAnnee(null);
                enseignantListEnseignant = em.merge(enseignantListEnseignant);
            }
            List<Anneecourant> anneecourantList = annee.getAnneecourantList();
            for (Anneecourant anneecourantListAnneecourant : anneecourantList) {
                anneecourantListAnneecourant.setIdAnnee(null);
                anneecourantListAnneecourant = em.merge(anneecourantListAnneecourant);
            }
            em.remove(annee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Annee> findAnneeEntities() {
        return findAnneeEntities(true, -1, -1);
    }

    public List<Annee> findAnneeEntities(int maxResults, int firstResult) {
        return findAnneeEntities(false, maxResults, firstResult);
    }

    private List<Annee> findAnneeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Annee.class));
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

    public Annee findAnnee(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Annee.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnneeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Annee> rt = cq.from(Annee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

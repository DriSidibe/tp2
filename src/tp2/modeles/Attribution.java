/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp2.modeles;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author drissa sidibe
 */
@Entity
@Table(name = "attribution")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attribution.findAll", query = "SELECT a FROM Attribution a"),
    @NamedQuery(name = "Attribution.findById", query = "SELECT a FROM Attribution a WHERE a.id = :id"),
    @NamedQuery(name = "Attribution.findByEnseignantFullName", query = "SELECT a FROM Attribution a WHERE a.enseignantFullName = :enseignantFullName"),
    @NamedQuery(name = "Attribution.findByQuotaCm", query = "SELECT a FROM Attribution a WHERE a.quotaCm = :quotaCm"),
    @NamedQuery(name = "Attribution.findByQuotaTd", query = "SELECT a FROM Attribution a WHERE a.quotaTd = :quotaTd"),
    @NamedQuery(name = "Attribution.findByQuotaTp", query = "SELECT a FROM Attribution a WHERE a.quotaTp = :quotaTp"),
    @NamedQuery(name = "Attribution.findByQuotaCmEffectuer", query = "SELECT a FROM Attribution a WHERE a.quotaCmEffectuer = :quotaCmEffectuer"),
    @NamedQuery(name = "Attribution.findByQuotaTdEffectuer", query = "SELECT a FROM Attribution a WHERE a.quotaTdEffectuer = :quotaTdEffectuer"),
    @NamedQuery(name = "Attribution.findByQuotaTpEffectuer", query = "SELECT a FROM Attribution a WHERE a.quotaTpEffectuer = :quotaTpEffectuer")})
public class Attribution implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "enseignant_full_name")
    private String enseignantFullName;
    @Column(name = "quota_cm")
    private Integer quotaCm;
    @Column(name = "quota_td")
    private Integer quotaTd;
    @Column(name = "quota_tp")
    private Integer quotaTp;
    @Column(name = "quota_cm_effectuer")
    private Integer quotaCmEffectuer;
    @Column(name = "quota_td_effectuer")
    private Integer quotaTdEffectuer;
    @Column(name = "quota_tp_effectuer")
    private Integer quotaTpEffectuer;
    @JoinColumn(name = "id_annee", referencedColumnName = "id")
    @ManyToOne
    private Annee idAnnee;
    @JoinColumn(name = "id_enseignant", referencedColumnName = "id")
    @ManyToOne
    private Enseignant idEnseignant;
    @JoinColumn(name = "id_ue", referencedColumnName = "id")
    @ManyToOne
    private Ue idUe;

    public Attribution() {
    }

    public Attribution(Integer id) {
        this.id = id;
    }

    public Attribution(String enseignantFullName, Integer quotaCm, Integer quotaTd, Integer quotaTp, Integer quotaCmEffectuer, Integer quotaTdEffectuer, Integer quotaTpEffectuer, Annee idAnnee, Enseignant idEnseignant, Ue idUe) {
        this.enseignantFullName = enseignantFullName;
        this.quotaCm = quotaCm;
        this.quotaTd = quotaTd;
        this.quotaTp = quotaTp;
        this.quotaCmEffectuer = quotaCmEffectuer;
        this.quotaTdEffectuer = quotaTdEffectuer;
        this.quotaTpEffectuer = quotaTpEffectuer;
        this.idAnnee = idAnnee;
        this.idEnseignant = idEnseignant;
        this.idUe = idUe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnseignantFullName() {
        return enseignantFullName;
    }

    public void setEnseignantFullName(String enseignantFullName) {
        this.enseignantFullName = enseignantFullName;
    }

    public Integer getQuotaCm() {
        return quotaCm;
    }

    public void setQuotaCm(Integer quotaCm) {
        this.quotaCm = quotaCm;
    }

    public Integer getQuotaTd() {
        return quotaTd;
    }

    public void setQuotaTd(Integer quotaTd) {
        this.quotaTd = quotaTd;
    }

    public Integer getQuotaTp() {
        return quotaTp;
    }

    public void setQuotaTp(Integer quotaTp) {
        this.quotaTp = quotaTp;
    }

    public Integer getQuotaCmEffectuer() {
        return quotaCmEffectuer;
    }

    public void setQuotaCmEffectuer(Integer quotaCmEffectuer) {
        this.quotaCmEffectuer = quotaCmEffectuer;
    }

    public Integer getQuotaTdEffectuer() {
        return quotaTdEffectuer;
    }

    public void setQuotaTdEffectuer(Integer quotaTdEffectuer) {
        this.quotaTdEffectuer = quotaTdEffectuer;
    }

    public Integer getQuotaTpEffectuer() {
        return quotaTpEffectuer;
    }

    public void setQuotaTpEffectuer(Integer quotaTpEffectuer) {
        this.quotaTpEffectuer = quotaTpEffectuer;
    }

    public Annee getIdAnnee() {
        return idAnnee;
    }

    public void setIdAnnee(Annee idAnnee) {
        this.idAnnee = idAnnee;
    }

    public Enseignant getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(Enseignant idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    public Ue getIdUe() {
        return idUe;
    }

    public void setIdUe(Ue idUe) {
        this.idUe = idUe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attribution)) {
            return false;
        }
        Attribution other = (Attribution) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "enseignantFullName: "+enseignantFullName+" ; "+"quotaCm: "+quotaCm+" ; "+"quotaTd: "+quotaTd+" ; "+"quotaTp: "+quotaTp+" ; "+"quotaCmEffectuer: "+quotaCmEffectuer+" ; "+"quotaTdEffectuer: "+quotaTdEffectuer+" ; "+"quotaTpEffectuer: "+quotaTpEffectuer+" ; "+"enseignant: "+idEnseignant.getNom()+" ; "+"ue: "+idUe.getLibelle();
    }
    
}

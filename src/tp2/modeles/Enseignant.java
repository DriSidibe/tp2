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
@Table(name = "enseignant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enseignant.findAll", query = "SELECT e FROM Enseignant e"),
    @NamedQuery(name = "Enseignant.findById", query = "SELECT e FROM Enseignant e WHERE e.id = :id"),
    @NamedQuery(name = "Enseignant.findByNom", query = "SELECT e FROM Enseignant e WHERE e.nom = :nom"),
    @NamedQuery(name = "Enseignant.findByPrenom", query = "SELECT e FROM Enseignant e WHERE e.prenom = :prenom"),
    @NamedQuery(name = "Enseignant.findByTel", query = "SELECT e FROM Enseignant e WHERE e.tel = :tel"),
    @NamedQuery(name = "Enseignant.findByEmail", query = "SELECT e FROM Enseignant e WHERE e.email = :email"),
    @NamedQuery(name = "Enseignant.findByGrade", query = "SELECT e FROM Enseignant e WHERE e.grade = :grade"),
    @NamedQuery(name = "Enseignant.findByQuotaRestant", query = "SELECT e FROM Enseignant e WHERE e.quotaRestant = :quotaRestant"),
    @NamedQuery(name = "Enseignant.findByQuotaCmAttribuer", query = "SELECT e FROM Enseignant e WHERE e.quotaCmAttribuer = :quotaCmAttribuer"),
    @NamedQuery(name = "Enseignant.findByQuotaTdAttribuer", query = "SELECT e FROM Enseignant e WHERE e.quotaTdAttribuer = :quotaTdAttribuer"),
    @NamedQuery(name = "Enseignant.findByQuotaTpAttribuer", query = "SELECT e FROM Enseignant e WHERE e.quotaTpAttribuer = :quotaTpAttribuer"),
    @NamedQuery(name = "Enseignant.findByQuotaCmEffectuer", query = "SELECT e FROM Enseignant e WHERE e.quotaCmEffectuer = :quotaCmEffectuer"),
    @NamedQuery(name = "Enseignant.findByQuotaTdEffectuer", query = "SELECT e FROM Enseignant e WHERE e.quotaTdEffectuer = :quotaTdEffectuer"),
    @NamedQuery(name = "Enseignant.findByQuotaTpEffectuer", query = "SELECT e FROM Enseignant e WHERE e.quotaTpEffectuer = :quotaTpEffectuer")})
public class Enseignant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "tel")
    private String tel;
    @Column(name = "email")
    private String email;
    @Column(name = "grade")
    private String grade;
    @Column(name = "quota_restant")
    private Integer quotaRestant;
    @Column(name = "quota_cm_attribuer")
    private Integer quotaCmAttribuer;
    @Column(name = "quota_td_attribuer")
    private Integer quotaTdAttribuer;
    @Column(name = "quota_tp_attribuer")
    private Integer quotaTpAttribuer;
    @Column(name = "quota_cm_effectuer")
    private Integer quotaCmEffectuer;
    @Column(name = "quota_td_effectuer")
    private Integer quotaTdEffectuer;
    @Column(name = "quota_tp_effectuer")
    private Integer quotaTpEffectuer;
    @JoinColumn(name = "id_annee", referencedColumnName = "id")
    @ManyToOne
    private Annee idAnnee;

    public Enseignant() {
    }

    public Enseignant(Integer id) {
        this.id = id;
    }

    public Enseignant(String nom, String prenom, String tel, String email, String grade, Integer quotaRestant, Integer quotaCmAttribuer, Integer quotaTdAttribuer, Integer quotaTpAttribuer, Integer quotaCmEffectuer, Integer quotaTdEffectuer, Integer quotaTpEffectuer, Annee idAnnee) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.grade = grade;
        this.quotaRestant = quotaRestant;
        this.quotaCmAttribuer = quotaCmAttribuer;
        this.quotaTdAttribuer = quotaTdAttribuer;
        this.quotaTpAttribuer = quotaTpAttribuer;
        this.quotaCmEffectuer = quotaCmEffectuer;
        this.quotaTdEffectuer = quotaTdEffectuer;
        this.quotaTpEffectuer = quotaTpEffectuer;
        this.idAnnee = idAnnee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getQuotaRestant() {
        return quotaRestant;
    }

    public void setQuotaRestant(Integer quotaRestant) {
        this.quotaRestant = quotaRestant;
    }

    public Integer getQuotaCmAttribuer() {
        return quotaCmAttribuer;
    }

    public void setQuotaCmAttribuer(Integer quotaCmAttribuer) {
        this.quotaCmAttribuer = quotaCmAttribuer;
    }

    public Integer getQuotaTdAttribuer() {
        return quotaTdAttribuer;
    }

    public void setQuotaTdAttribuer(Integer quotaTdAttribuer) {
        this.quotaTdAttribuer = quotaTdAttribuer;
    }

    public Integer getQuotaTpAttribuer() {
        return quotaTpAttribuer;
    }

    public void setQuotaTpAttribuer(Integer quotaTpAttribuer) {
        this.quotaTpAttribuer = quotaTpAttribuer;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enseignant)) {
            return false;
        }
        Enseignant other = (Enseignant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom+" "+prenom;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp2.modeles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author drissa sidibe
 */
@Entity
@Table(name = "ue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ue.findAll", query = "SELECT u FROM Ue u"),
    @NamedQuery(name = "Ue.findById", query = "SELECT u FROM Ue u WHERE u.id = :id"),
    @NamedQuery(name = "Ue.findByLibelle", query = "SELECT u FROM Ue u WHERE u.libelle = :libelle"),
    @NamedQuery(name = "Ue.findByNombreHeureCm", query = "SELECT u FROM Ue u WHERE u.nombreHeureCm = :nombreHeureCm"),
    @NamedQuery(name = "Ue.findByNombreHeureTd", query = "SELECT u FROM Ue u WHERE u.nombreHeureTd = :nombreHeureTd"),
    @NamedQuery(name = "Ue.findByNombreHeureTp", query = "SELECT u FROM Ue u WHERE u.nombreHeureTp = :nombreHeureTp"),
    @NamedQuery(name = "Ue.findByNombreHeureRestCm", query = "SELECT u FROM Ue u WHERE u.nombreHeureRestCm = :nombreHeureRestCm"),
    @NamedQuery(name = "Ue.findByNombreHeureRestTd", query = "SELECT u FROM Ue u WHERE u.nombreHeureRestTd = :nombreHeureRestTd"),
    @NamedQuery(name = "Ue.findByNombreHeureRestTp", query = "SELECT u FROM Ue u WHERE u.nombreHeureRestTp = :nombreHeureRestTp"),
    @NamedQuery(name = "Ue.findByNombreGroupeCm", query = "SELECT u FROM Ue u WHERE u.nombreGroupeCm = :nombreGroupeCm"),
    @NamedQuery(name = "Ue.findByNombreGroupeTd", query = "SELECT u FROM Ue u WHERE u.nombreGroupeTd = :nombreGroupeTd"),
    @NamedQuery(name = "Ue.findByNombreGroupeTp", query = "SELECT u FROM Ue u WHERE u.nombreGroupeTp = :nombreGroupeTp")})
public class Ue implements Serializable {

    @OneToMany(mappedBy = "idUe")
    private List<Attribution> attributionList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "nombre_heure_cm")
    private Integer nombreHeureCm;
    @Column(name = "nombre_heure_td")
    private Integer nombreHeureTd;
    @Column(name = "nombre_heure_tp")
    private Integer nombreHeureTp;
    @Column(name = "nombre_heure_rest_cm")
    private Integer nombreHeureRestCm;
    @Column(name = "nombre_heure_rest_td")
    private Integer nombreHeureRestTd;
    @Column(name = "nombre_heure_rest_tp")
    private Integer nombreHeureRestTp;
    @Column(name = "nombre_groupe_cm")
    private Integer nombreGroupeCm;
    @Column(name = "nombre_groupe_td")
    private Integer nombreGroupeTd;
    @Column(name = "nombre_groupe_tp")
    private Integer nombreGroupeTp;
    @JoinColumn(name = "id_annee", referencedColumnName = "id")
    @ManyToOne
    private Annee idAnnee;

    public Ue() {
    }

    public Ue(Integer id) {
        this.id = id;
    }

    public Ue(String libelle, Integer nombreHeureCm, Integer nombreHeureTd, Integer nombreHeureTp, Integer nombreHeureRestCm, Integer nombreHeureRestTd, Integer nombreHeureRestTp, Integer nombreGroupeCm, Integer nombreGroupeTd, Integer nombreGroupeTp, Annee idAnnee) {
        this.attributionList = new ArrayList<>();
        this.libelle = libelle;
        this.nombreHeureCm = nombreHeureCm;
        this.nombreHeureTd = nombreHeureTd;
        this.nombreHeureTp = nombreHeureTp;
        this.nombreHeureRestCm = nombreHeureRestCm;
        this.nombreHeureRestTd = nombreHeureRestTd;
        this.nombreHeureRestTp = nombreHeureRestTp;
        this.nombreGroupeCm = nombreGroupeCm;
        this.nombreGroupeTd = nombreGroupeTd;
        this.nombreGroupeTp = nombreGroupeTp;
        this.idAnnee = idAnnee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getNombreHeureCm() {
        return nombreHeureCm;
    }

    public void setNombreHeureCm(Integer nombreHeureCm) {
        this.nombreHeureCm = nombreHeureCm;
    }

    public Integer getNombreHeureTd() {
        return nombreHeureTd;
    }

    public void setNombreHeureTd(Integer nombreHeureTd) {
        this.nombreHeureTd = nombreHeureTd;
    }

    public Integer getNombreHeureTp() {
        return nombreHeureTp;
    }

    public void setNombreHeureTp(Integer nombreHeureTp) {
        this.nombreHeureTp = nombreHeureTp;
    }

    public Integer getNombreHeureRestCm() {
        return nombreHeureRestCm;
    }

    public void setNombreHeureRestCm(Integer nombreHeureRestCm) {
        this.nombreHeureRestCm = nombreHeureRestCm;
    }

    public Integer getNombreHeureRestTd() {
        return nombreHeureRestTd;
    }

    public void setNombreHeureRestTd(Integer nombreHeureRestTd) {
        this.nombreHeureRestTd = nombreHeureRestTd;
    }

    public Integer getNombreHeureRestTp() {
        return nombreHeureRestTp;
    }

    public void setNombreHeureRestTp(Integer nombreHeureRestTp) {
        this.nombreHeureRestTp = nombreHeureRestTp;
    }

    public Integer getNombreGroupeCm() {
        return nombreGroupeCm;
    }

    public void setNombreGroupeCm(Integer nombreGroupeCm) {
        this.nombreGroupeCm = nombreGroupeCm;
    }

    public Integer getNombreGroupeTd() {
        return nombreGroupeTd;
    }

    public void setNombreGroupeTd(Integer nombreGroupeTd) {
        this.nombreGroupeTd = nombreGroupeTd;
    }

    public Integer getNombreGroupeTp() {
        return nombreGroupeTp;
    }

    public void setNombreGroupeTp(Integer nombreGroupeTp) {
        this.nombreGroupeTp = nombreGroupeTp;
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
        if (!(object instanceof Ue)) {
            return false;
        }
        Ue other = (Ue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return libelle;
    }

    @XmlTransient
    public List<Attribution> getAttributionList() {
        return attributionList;
    }

    public void setAttributionList(List<Attribution> attributionList) {
        this.attributionList = attributionList;
    }
    
}

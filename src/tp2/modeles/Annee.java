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
@Table(name = "annee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annee.findAll", query = "SELECT a FROM Annee a"),
    @NamedQuery(name = "Annee.findById", query = "SELECT a FROM Annee a WHERE a.id = :id"),
    @NamedQuery(name = "Annee.findByLabel", query = "SELECT a FROM Annee a WHERE a.label = :label")})
public class Annee implements Serializable {

    @OneToMany(mappedBy = "idAnnee")
    private List<Anneecourant> anneecourantList;

    @OneToMany(mappedBy = "idAnnee")
    private List<Ue> ueList;
    @OneToMany(mappedBy = "idAnnee")
    private List<Enseignant> enseignantList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "label")
    private String label;

    public Annee() {
    }

    public Annee(Integer id) {
        this.id = id;
    }

    public Annee(String label) {
        this.ueList = new ArrayList<>();
        this.enseignantList = new ArrayList<>();
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
        if (!(object instanceof Annee)) {
            return false;
        }
        Annee other = (Annee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return label;
    }

    @XmlTransient
    public List<Ue> getUeList() {
        return ueList;
    }

    public void setUeList(List<Ue> ueList) {
        this.ueList = ueList;
    }

    @XmlTransient
    public List<Enseignant> getEnseignantList() {
        return enseignantList;
    }

    public void setEnseignantList(List<Enseignant> enseignantList) {
        this.enseignantList = enseignantList;
    }

    @XmlTransient
    public List<Anneecourant> getAnneecourantList() {
        return anneecourantList;
    }

    public void setAnneecourantList(List<Anneecourant> anneecourantList) {
        this.anneecourantList = anneecourantList;
    }
    
}

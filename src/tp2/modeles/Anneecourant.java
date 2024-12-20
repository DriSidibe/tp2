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
@Table(name = "anneecourant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anneecourant.findAll", query = "SELECT a FROM Anneecourant a"),
    @NamedQuery(name = "Anneecourant.findById", query = "SELECT a FROM Anneecourant a WHERE a.id = :id")})
public class Anneecourant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_annee", referencedColumnName = "id")
    @ManyToOne
    private Annee idAnnee;

    public Anneecourant() {
    }

    public Anneecourant(Integer id) {
        this.id = id;
    }

    public Anneecourant(Annee idAnnee) {
        this.idAnnee = idAnnee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Anneecourant)) {
            return false;
        }
        Anneecourant other = (Anneecourant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tp2.modeles.Anneecourant[ id=" + id + " ]";
    }
    
}

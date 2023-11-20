/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2.modeles;

/**
 *
 * @author drissa sidibe
 */
public class BilanTableModel {
    String ue;
    int cm;
    int td;
    int tp;
    Annee annee;

    public BilanTableModel(String ue, int cm, int td, int tp, Annee annee) {
        this.ue = ue;
        this.cm = cm;
        this.td = td;
        this.tp = tp;
        this.annee = annee;
    }

    public Annee getAnnee() {
        return annee;
    }

    public int getTd() {
        return td;
    }

    public int getCm() {
        return cm;
    }

    public int getTp() {
        return tp;
    }

    public String getUe() {
        return ue;
    }

    public void setCm(int cm) {
        this.cm = cm;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }
    
    
}

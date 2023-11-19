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
}

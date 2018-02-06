/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

/**
 *
 * @author nathan
 */
public class Competence {
    private String id;
    private String libEng;
    private String libFr;

    /**
     * Constructeur de competence, prend en parametre l'id, le libellé en anglais et le libellé en francais
     * @param id
     * @param libEng
     * @param libFr
     */
    public Competence(String id, String libEng, String libFr) {
        this.id = id;
        this.libEng = libEng;
        this.libFr = libFr;
    }

    /**
     * Methode d'affichage d'une competence
     * @return
     */
    @Override
    public String toString() {
        return id + libFr + "(" +libEng +")";
    }
    
    
    
    
    
}

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

    public Competence(String id, String libEng, String libFr) {
        this.id = id;
        this.libEng = libEng;
        this.libFr = libFr;
    }

    public String getId() {
        return id;
    }

    public String getLibEng() {
        return libEng;
    }

    public String getLibFr() {
        return libFr;
    }

    
    
    @Override
    public String toString() {
        return id + " " + libFr + "(" +libEng +")";
    }
    
    
    public void salut() {
     int i=0;
    }
}

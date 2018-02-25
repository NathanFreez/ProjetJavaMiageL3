/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author nathan
 */
public class Employe {
    private int id;
    private String prenom;
    private String nom;
    private String dateE;
    private ArrayList<String> listeComp;

    public Employe(int id, String prenom, String nom, String dateE) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.dateE = dateE;
        listeComp = new ArrayList<String>();
    }

    Employe() {
    }

    public ArrayList<String> getListeComp() {
        return listeComp;
    }

    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getDateE() {
        return dateE;
    }

    public void setListeComp(ArrayList<String> listeComp) {
        this.listeComp = listeComp;
    }    

    @Override
    public String toString() {
        String chaine = id + " " + prenom + " " + nom + " embauché le " + dateE + ". Competence {";
        if(this.listeComp != null) {
            for(String c : listeComp){
                if(c != null){
                    chaine += c.toString()+" ";
                }
            }            
        }
        chaine += "}";
        return chaine;
    }
    
    
    
}

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

    /**
     * Constructeur d'Employe, prend en parametre un id, un prenom, un nom et une date d'embauche
     * @param id
     * @param prenom
     * @param nom
     * @param dateE
     */
    public Employe(int id, String prenom, String nom, String dateE) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.dateE = dateE;
        listeComp = new ArrayList<String>();
    }

    /**
     * Methode qui permet de envoyer la liste de Competence d'un employé
     * @return
     */
    public ArrayList<String> getListeComp() {
        return listeComp;
    }

    /**
     * Methode d'affiche d'un employe, affiche ses caractéristiques et ses competences
     * @return
     */
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

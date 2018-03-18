/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.util.ArrayList;
import java.util.Date;

/**
 * Employe d'une entreprise qui a des competences
 * @author nathan
 */
public class Employe {
    private int id;
    private String prenom;
    private String nom;
    private String dateE;
    private ArrayList<String> listeComp;

    /**
     * Constructeur d'Employe avec un id, un prenom, un nom et une date d'embauche
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

    Employe() {
    }

    /**
     * retourne la liste des competence d'un employe
     * @return
     */
    public ArrayList<String> getListeComp() {
        return listeComp;
    }

    /**
     * retourne l'id d'un employe
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * retourne le prenom d'un employe
     * @return
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * retourne le nom d'un employe
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     * retourne la date d'embauche d'un employe
     * @return
     */
    public String getDateE() {
        return dateE;
    }

    /**
     * modifie la liste de competence d'un employe
     * @param listeComp
     */
    public void setListeComp(ArrayList<String> listeComp) {
        this.listeComp = listeComp;
    }    

    /**
     * défini comment un employe doit s'afficher
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

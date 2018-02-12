/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author nathan
 */
public class Mission {
    
    private String id;
    private String nom;
    private TypeMission type;
    private String dateD;
    private int duree;
    private HashMap<Competence,Integer> mapC;
    private int nbTotalEmp;
    private HashMap<Competence,Employe[]> mapE;

    /**
     * Constructeur d'une mission qui prend en parametre un id, une date de début de mission, et une duree (en jours)
     * @param id
     * @param type
     * @param dateD
     * @param duree
     */
    public Mission(String id, String nom, String dateD, int duree, int nbTotalEmp) {
        this.id = id;
        this.nom = nom;
        this.type = TypeMission.preparation;
        this.dateD = dateD;
        this.duree = duree;
        this.mapC = new HashMap<Competence,Integer>();
        this.nbTotalEmp = nbTotalEmp;
        this.mapE = new HashMap<Competence,Employe[]>();
    }

    public Mission(String id, String nom, TypeMission type, String dateD, int duree, int nbTotalEmp) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.dateD = dateD;
        this.duree = duree;
        this.mapC = new HashMap<Competence,Integer>();
        this.nbTotalEmp = nbTotalEmp;
        this.mapE = new HashMap<Competence,Employe[]>();
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public TypeMission getType() {
        return type;
    }

    public String getDateD() {
        return dateD;
    }

    public int getDuree() {
        return duree;
    }

    public int getNbTotalEmp() {
        return nbTotalEmp;
    }

    public HashMap<Competence, Integer> getMapC() {
        return mapC;
    }

    public void setMapC(HashMap<Competence, Integer> mapC) {
        this.mapC = mapC;
    }
    
    
    
       
}

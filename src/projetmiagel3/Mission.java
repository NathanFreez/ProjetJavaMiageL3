/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nathan
 */
public class Mission {
    
    protected String id;
    protected String nom;
    protected TypeMission type;
    protected Date dateD;
    protected int duree;
    protected HashMap<Competence,Integer> mapC;
    protected int nbTotalEmp;
    protected HashMap<Competence,Employe[]> mapE;

    Mission() {
    }
    
    /**
     * Constructeur d'une mission qui prend en parametre un id, une date de début de mission, et une duree (en jours)
     * @param id
     * @param type
     * @param dateD
     * @param duree
     */
    public Mission(String id, String nom, Date dateD, int duree, int nbTotalEmp) {
        this.id = id;
        this.nom = nom;
        this.type = TypeMission.preparation;
        this.dateD = dateD;
        this.duree = duree;
        this.mapC = new HashMap<Competence,Integer>();
        this.nbTotalEmp = nbTotalEmp;
        this.mapE = new HashMap<Competence,Employe[]>();
    }

    public Mission(String id, String nom, TypeMission type, Date dateD, int duree, int nbTotalEmp) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.dateD = dateD;
        this.duree = duree;
        this.mapC = new HashMap<Competence,Integer>();
        this.nbTotalEmp = nbTotalEmp;
        this.mapE = new HashMap<Competence,Employe[]>();
    }
    
    public ArrayList<Employe> getEmpMission(){
        ArrayList<Employe> listTmpEmp = new ArrayList<Employe>();
        for (Map.Entry<Competence,Employe[]> ce : this.mapE.entrySet()){
            for (int i=0; i<ce.getValue().length;i++){
                if(!listTmpEmp.contains(ce.getValue()[i])){
                    listTmpEmp.add(ce.getValue()[i]);
                }
            }
        }
        return listTmpEmp;
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

    public Date getDateD() {
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
    
    public void setType(TypeMission type) {
        this.type = type;
    }    
}

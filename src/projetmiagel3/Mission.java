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
 * Mission qui doit etre réalisé par des employes
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
     * Constructeur d'une mission qui prend en parametre un id, un nom, une date de début de mission, et une duree (en jours)
     * @param id
     * @param nom
     * @param dateD
     * @param duree
     * @param nbTotalEmp
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

    /**
     * Constructeur d'une mission qui prend en parametre un id, un nom, un type, une date de début de mission, et une duree (en jours)
     * @param id
     * @param nom
     * @param type
     * @param dateD
     * @param duree
     * @param nbTotalEmp
     */
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
    
    /**
     * retourne la liste des employe de la mission
     * @return
     */
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

    /**
     * retourne l'id de la mission
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *retourn le nom de la mission
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     * retourne le type de la mission
     * @return
     */
    public TypeMission getType() {
        return type;
    }

    /**
     * retourne la date de début de la mission
     * @return
     */
    public Date getDateD() {
        return dateD;
    }

    /**
     * retourne la duree de la mission
     * @return
     */
    public int getDuree() {
        return duree;
    }

    /**
     * retourne le nombre total d'meploye de la mission
     * @return
     */
    public int getNbTotalEmp() {
        return nbTotalEmp;
    }

    /**
     * retourne la liste des competences et le nombre d'employe necessaire pour celle-ci
     * @return
     */
    public HashMap<Competence, Integer> getMapC() {
        return mapC;
    }

    /**
     * modifie la liste competences et le nombre d'employe necessaire pour celle-ci
     * @param mapC
     */
    public void setMapC(HashMap<Competence, Integer> mapC) {
        this.mapC = mapC;
    }
    
    /**
     * modifie le type de la mission
     * @param type
     */
    public void setType(TypeMission type) {
        this.type = type;
    }    
}

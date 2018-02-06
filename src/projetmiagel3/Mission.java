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
    
    private int id;
    private TypeMission type;
    private Date dateD;
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
    public Mission(int id, Date dateD, int duree) {
        this.id = id;
        this.type = TypeMission.preparation;
        this.dateD = dateD;
        this.duree = duree;
        this.mapC = new HashMap<Competence,Integer>();
        this.nbTotalEmp = 0;
        this.mapE = new HashMap<Competence,Employe[]>();
    }
       
}

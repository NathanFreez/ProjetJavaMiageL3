/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Formation qui permet a un employe d'obtenir une competence
 * @author nathan
 */
public class Formation extends Mission{
    
    /**
     * constructeur de formation avec un id, un nom, une date de début, une duree et le nombre total d'employe
     * @param id
     * @param nom
     * @param dateD
     * @param duree
     * @param nbTotalEmp
     */
    public Formation(String id, String nom, Date dateD, int duree, int nbTotalEmp) {
        super(id, nom, dateD, duree, nbTotalEmp);
    }

    /**
     * constructeur de formation avec un id, un nom, un type de formation, une date de début, une duree et le nombre total d'employe
     * @param id
     * @param nom
     * @param type
     * @param dateD
     * @param duree
     * @param nbTotalEmp
     */
    public Formation(String id, String nom, TypeMission type, Date dateD, int duree, int nbTotalEmp) {
        super(id, nom, type, dateD, duree, nbTotalEmp);

    }

    /**
     * modifie le type de la formation
     * @param type
     */
    public void setType(TypeMission type) {
        this.type = type;
    }

    /**
     * retourne la liste des employe ainsi que la competence a acquérir
     * @return
     */
    public HashMap<Competence, Employe[]> getMapE() {
        return mapE;
    }

    /**
     * modifie la liste des employe
     * @param c
     * @param e
     */
    public void setMapE(Competence c, Employe[] e) {
        this.mapE.put(c, e);
    }
    
    /**
     * retourne l'employe de la formation
     * @return
     */
    public Employe getEmp(){
        Employe e = new Employe();
        for(Map.Entry<Competence, Employe[]> ce : this.mapE.entrySet()){
            e = ce.getValue()[0];
        }
        return e;
    }
    
    /**
     * retourne la competence de la formation
     * @return
     */
    public Competence getComp(){
        Competence c = new Competence();
        for(Map.Entry<Competence, Employe[]> ce : this.mapE.entrySet()){
            c = ce.getKey();
        }
        return c;
    }
    
    
    
    
    
}

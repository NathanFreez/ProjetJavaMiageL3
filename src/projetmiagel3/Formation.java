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
 *
 * @author nathan
 */
public class Formation extends Mission{
    
    public Formation(String id, String nom, Date dateD, int duree, int nbTotalEmp) {
        super(id, nom, dateD, duree, nbTotalEmp);
    }

    public Formation(String id, String nom, TypeMission type, Date dateD, int duree, int nbTotalEmp) {
        super(id, nom, type, dateD, duree, nbTotalEmp);

    }

    public HashMap<Competence, Employe[]> getMapE() {
        return mapE;
    }

    public void setMapE(Competence c, Employe[] e) {
        this.mapE.put(c, e);
    }
    
    public Employe getEmp(){
        Employe e = new Employe();
        for(Map.Entry<Competence, Employe[]> ce : this.mapE.entrySet()){
            e = ce.getValue()[0];
        }
        return e;
    }
    
    public Competence getComp(){
        Competence c = new Competence();
        for(Map.Entry<Competence, Employe[]> ce : this.mapE.entrySet()){
            c = ce.getKey();
        }
        return c;
    }
    
    
    
    
    
}

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
    private Mission type;
    private Date dateD;
    private int duree;
    private HashMap<Competence,Integer> mapC;
    private int nbTotalEmp;
    private HashMap<Competence,Employe[]> mapE;

    public Mission(int id, Mission type, Date dateD, int duree) {
        this.id = id;
        this.type = type;
        this.dateD = dateD;
        this.duree = duree;
    }
       
}

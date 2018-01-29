/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Nathan
 */
public class Entreprise {
    private ArrayList<Employe> listEmp;
    private ArrayList<Competence> listComp;
    private ArrayList<Mission> listMis;

    public Entreprise() throws FileNotFoundException {
        listEmp = new ArrayList<Employe>();
        listComp = new ArrayList<Competence>();
        listMis = new ArrayList<Mission>();
        chargerEmploye();
        chargerCompetence();
        chargerCompetenceEmploye();
    }
    
    public void chargerEmploye() throws FileNotFoundException{
        String line;
        String fichierEmploye = System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_personnel.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierEmploye));
        line = entree.nextLine();
        try {
            while ((line = entree.nextLine()) != null){
                String values[] = line.split(";");
                Employe empTmp = new Employe(Integer.parseInt(values[3]), values[0], values[1], values[2]);
                listEmp.add(empTmp);
            }
        }
        catch (Exception e){
        }
    }
    
    
   public void chargerCompetence() throws FileNotFoundException {
        String line;
        String fichierCompetence = System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_competences.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierCompetence));
        try {
            while ((line = entree.nextLine()) != null){
                String values[] = line.split(";");
                Competence compTmp = new Competence(values[0], values[1], values[2]);
                listComp.add(compTmp);
            }
        }
        catch (Exception e){
        }
   }
   
   public void chargerCompetenceEmploye() throws FileNotFoundException{
        String line;
        String fichierCompetence = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_personnel.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierCompetence));
        line = entree.nextLine();
        try {
            while ((line = entree.nextLine()) != null){
                String values[] = line.split(";");
                for(int i=1; i<values.length; i++){
                    listEmp.get(Integer.parseInt(values[0])-1).getListeComp().add(values[i]);
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
   }

    public ArrayList<Employe> getListEmp() {
        return listEmp;
    }

    public ArrayList<Competence> getListComp() {
        return listComp;
    }

    public ArrayList<Mission> getListMis() {
        return listMis;
    }

    public void ajouterMission(Mission m){
        this.listMis.add(m);
    }
   
   

}
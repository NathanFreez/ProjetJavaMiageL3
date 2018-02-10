/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Nathan
 */
public class Entreprise implements IFichierCSV{
    private ArrayList<Employe> listEmp;
    private ArrayList<Competence> listComp;
    private ArrayList<Mission> listMis;

    /**
     * Constructeur d'Entreprise, ne prend pas de paramètres, elle instancie les listes et les charges grâce aux fichier csv
     * @throws FileNotFoundException
     */
    public Entreprise() throws FileNotFoundException {
        listEmp = new ArrayList<Employe>();
        listComp = new ArrayList<Competence>();
        listMis = new ArrayList<Mission>();
        chargerEmploye();
        chargerCompetence();
        chargerCompetenceEmploye();
        chargerMission();
        chargerCompetenceMission();
    }
    
    /**
     * Methode qui permet de charger la liste d'employe en fonction du fichier csv
     * @throws FileNotFoundException
     */
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
            System.out.println(e);
        }
    }
    
    /**
     * Methode qui permet de charger la liste des compétences en fonction du fichier csv
     * @throws FileNotFoundException
     */
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
            System.out.println(e);
        }
   }
   
    /**
     * Methode qui permet de charger la liste des compétences d'un employé en fonction du fichier csv
     * @throws FileNotFoundException
     */
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
    
    public void chargerMission() throws FileNotFoundException{
        String line;
        String fichierMission = System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_missions.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierMission));
        try {
            while ((line = entree.nextLine()) != null){
                String values[] = line.split(";");
                Mission MisTmp = new Mission(values[0], values[1], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                listMis.add(MisTmp);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    public void chargerCompetenceMission() throws FileNotFoundException{
        String line;
        String fichierCompetence = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_mission.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierCompetence));
        try {
            while ((line = entree.nextLine()) != null){
                String values[] = line.split(";");
                for(int i=1; i<values.length; i++){
                    String compNb[] = values[i].split(":");
                    for (Competence c : listComp){
                        if(c.getId().equals(compNb[0])){
                            listMis.get(Integer.parseInt(values[0])-1).getMapC().put(c, Integer.parseInt(compNb[1]));
                            //break
                        }
                        //break;
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        
        /*
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
        */
    }
    
    public void sauvegarderEmploye(ArrayList<Employe> listE) throws IOException{
        this.listEmp = listE;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_personnel.csv");
        writer.write("Prenom;Nom;date entrée entreprise;identifiant\n");
        for (Employe e : listE){
            writer.write(e.getPrenom() + ";" + e.getNom() + ";" + e.getDateE() + ";" + e.getId() + "\n");
        }
        writer.close();
    }
    
    public void sauvegarderCompetence(ArrayList<Competence> listC) throws IOException{
        this.listComp = listC;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_competences.csv");
        for (Competence c : listC){
            writer.write(c.getId() + ";"+ c.getLibEng() + ";" + c.getLibFr() + "\n");
        }
        writer.close();
    }
    
    public void sauvegarderMission(ArrayList<Mission> listM) throws IOException{
        this.listMis = listM;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_missions.csv");
        for (Mission m : listM){
            writer.write(m.getId() + ";" + m.getNom() + ";" + m.getType() + ";" + m.getDateD() + ";" + m.getDuree() + ";" + m.getNbTotalEmp() + "\n");
        }
        writer.close();
    }
    
    public void sauvegardeCompetenceMission(ArrayList<Mission> listM) throws IOException{
        //this.listMis = listM;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_mission.csv");
        for (Mission m : listM){
            writer.write(m.getId());
            for(Map.Entry<Competence, Integer> ci : m.getMapC().entrySet()){
                writer.write(";" + ci.getKey().getId() + ":" + ci.getValue());
            }
            writer.write("\n");
        }
        writer.close();
    }

    public void ajouterMission(Mission m) throws IOException{
        this.listMis.add(m);
        sauvegarderMission(listMis);
        sauvegardeCompetenceMission(listMis);
    }
        
    /**
     * Methode qui permet de envoyer la liste d'employé de l'entreprise
     * @return
     */
    public ArrayList<Employe> getListEmp() {
        return listEmp;
    }

    /**
     * Methode qui permet de envoyer la liste de compétences de l'entreprise
     * @return
     */
    public ArrayList<Competence> getListComp() {
        return listComp;
    }

    /**
     * Methode qui permet de envoyer la liste de mission de l'entreprise
     * @return
     */
    public ArrayList<Mission> getListMis() {
        return listMis;
    }

    /**
     * Methode qui prend en parametre une mission et qui permet de l'ajouter à la liste des missions de l'entreprise
     * @param m
     */
}
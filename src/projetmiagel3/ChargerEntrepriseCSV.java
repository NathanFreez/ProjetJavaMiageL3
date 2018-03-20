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
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author nathan
 */
public abstract class ChargerEntrepriseCSV implements IFichier{

    protected ArrayList<Employe> listEmp;
    protected ArrayList<Competence> listComp;
    protected ArrayList<Mission> listMis;
    protected ArrayList<Formation> listForm;
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
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String fichierMission = System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_missions.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierMission));
        try {
            while ((line = entree.nextLine()) != null){
                String values[] = line.split(";");
                Mission MisTmp = new Mission();
                switch(values[2]){
                    case "preparation" :
                        MisTmp = new Mission(values[0], values[1], TypeMission.preparation, sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                        break;
                    case "planifie" :
                        MisTmp = new Mission(values[0], values[1], TypeMission.planifie, sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                        break;
                    case "encours" :
                        MisTmp = new Mission(values[0], values[1], TypeMission.encours, sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                        break;
                    case "termine" :
                        MisTmp = new Mission(values[0], values[1], TypeMission.termine, sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                        break;
                }
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
    }
    
    public void chargerEmployeMission() throws FileNotFoundException{
        String line;
        boolean trouver;
        Employe[] tabemp;
        String fichierEmploye = System.getProperty("user.dir") + "\\src\\projetmiagel3\\employes_mission.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierEmploye));
        try {
            while ((line = entree.nextLine()) != null){
                trouver = false;
                String values[] = line.split(";");
                String infoEmp[] = values[2].split(" ");
                for(Mission m : listMis){
                    if(m.getId().equals(values[0])){
                        for(Competence c : listComp){
                            if(c.getId().equals(values[1])){
                                for(Employe e : listEmp){
                                    if(e.getId()==Integer.parseInt(infoEmp[0])){
                                        for(Map.Entry<Competence, Employe[]> ce : m.getMapE().entrySet()){
                                            if(ce.getKey().equals(c)){
                                            tabemp = new Employe[ce.getValue().length+1];
                                            for (int j=0; j<ce.getValue().length; j++){
                                                tabemp[j] = ce.getValue()[j];
                                            }
                                            tabemp[tabemp.length-1] = e;
                                            m.getMapE().put(c, tabemp);
                                            trouver = true;
                                            }
                                        }
                                        if(trouver==false){
                                            tabemp = new Employe[1];
                                            tabemp[0] = e;
                                            m.getMapE().put(c, tabemp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("cc");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    public void chargerFormation() throws FileNotFoundException{
        String line;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        String fichierMission = System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_formations.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierMission));
        try {
            while ((line = entree.nextLine()) != null){
                String values[] = line.split(";");
                Formation formTmp = new Formation(values[0], values[1], sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                switch(values[2]){
                    case "preparation" :
                        formTmp = new Formation(values[0], values[1], TypeMission.preparation, sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                        break;
                    case "planifie" :
                        formTmp = new Formation(values[0], values[1], TypeMission.planifie, sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                        break;
                    case "encours" :
                        formTmp = new Formation(values[0], values[1], TypeMission.encours, sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                        break;
                    case "termine" :
                        formTmp = new Formation(values[0], values[1], TypeMission.termine, sdf.parse(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                        break;
                }
                listForm.add(formTmp);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    public void chargerCompetenceEtEmployeFormation() throws FileNotFoundException{
        String line;
        String fichierCompetence = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_formation.csv";
        String fichierEmploye = System.getProperty("user.dir") + "\\src\\projetmiagel3\\employes_formation.csv";
        Competence cForm = new Competence();
        Employe[] eForm = new Employe[1];
        java.util.Scanner entreeComp = new java.util.Scanner(new FileReader(fichierCompetence));
        java.util.Scanner entreeEmp = new java.util.Scanner(new FileReader(fichierEmploye));
        try {
            while ((line = entreeComp.nextLine()) != null){
                String values[] = line.split(";");
                for (Competence c : listComp){
                    if(c.getId().equals(values[1])){
                        cForm = c;
                    }
                }
                while ((line = entreeEmp.nextLine()) != null){
                    values = line.split(";");
                    for (Employe e : listEmp){
                        if(e.getId() == Integer.parseInt((values[1].split(" "))[0])){
                            eForm[0] = e;
                            listForm.get(Integer.parseInt(values[0])-1).setMapE(cForm, eForm);
                        }
                    }
                }
            }
            
        }
        catch (Exception e){
            System.out.println(e);
        }
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
        this.listMis = listM;
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
    
    public void sauvegarderEmployeMission(ArrayList<Mission> listM) throws IOException{
        this.listMis = listM;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\employes_mission.csv");
        for (Mission m : listM){
            for(Map.Entry<Competence, Employe[]> ce: m.getMapE().entrySet()){
                for(int i=0; i<ce.getValue().length; i++){
                    writer.write(m.getId());
                    writer.write(";" + ce.getKey().getId() + ";" + ce.getValue()[i].getId() + " " + ce.getValue()[i].getNom() + " " + ce.getValue()[i].getPrenom());
                    writer.write("\n");
                }
            }
        }
        writer.close();
    }
    
    public void sauvegarderFormation(ArrayList<Formation> listF) throws IOException{
        this.listForm = listF;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_formations.csv");
        for (Formation f : listF){
            writer.write(f.getId() + ";" + f.getNom() + ";" + f.getType() + ";" + f.getDateD() + ";" + f.getDuree() + ";" + f.getNbTotalEmp() + "\n");
        }
        writer.close();
    }
    
    public void sauvegardeCompetenceFormation(ArrayList<Formation> listF) throws IOException{
        this.listForm = listF;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_formation.csv");
        for (Formation f : listF){
            writer.write(f.getId());
            for(Map.Entry<Competence, Employe[]> ce : f.getMapE().entrySet()){
                writer.write(";" + ce.getKey().getId());
            }
            writer.write("\n");
        }
        writer.close();
    }
    
    public void sauvegardeEmployeFormation(ArrayList<Formation> listF) throws IOException{
        this.listForm = listF;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\employes_formation.csv");
        for (Formation f : listF){
            writer.write(f.getId());
            for(Map.Entry<Competence, Employe[]> ce : f.getMapE().entrySet()){
                writer.write(";" + ce.getValue()[0].getId() + " " + ce.getValue()[0].getNom() + " " + ce.getValue()[0].getPrenom());
            }
            writer.write("\n");
        }
        writer.close();
    }
    
    public void sauvegardeListeComp(int id, String compAdd) throws IOException{
        //Variable pour savoir si la compétence existe déjà
        int check=0;
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_personnel.csv");        
        writer.write("Employe;Liste Competences \n");
        for(Employe e : listEmp){
            //Variable de recuperation de l'id employe
            int memoId = e.getId();
            writer.write(memoId+";");
            for(String s : e.getListeComp()){
                if(s.equals(compAdd)){
                    check=1;
                }
                if(!s.equals(" ")){
                writer.write(s+";");}
                
            }
            if(e.getId()==id && check ==0){
                //Ajout de la competence de formation
                writer.write(compAdd+";");
            }
            writer.write("\n");
        }
        writer.close();
    }

}

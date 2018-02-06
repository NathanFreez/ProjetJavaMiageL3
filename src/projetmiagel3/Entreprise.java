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
import java.util.ArrayList;

/**
 *
 * @author Nathan
 */
public class Entreprise {
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
    
    public void sauvegarderEmploye(ArrayList<Employe> listE) throws IOException{
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_personnel.csv");
        writer.write("Prenom;Nom;date entrée entreprise;identifiant\n");
        for (Employe e : listE){
                writer.write(e.getPrenom() + ";" + e.getNom() + ";" + e.getDateE() + ";" + e.getId() + "\n");
            }
            writer.close();
    }
    
    public void sauvegarderCompetence(ArrayList<Competence> listC) throws IOException{
        FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_competences.csv");
        for (Competence c : listC){
                writer.write(c.getId() + ";"+ c.getLibEng() + ";" + c.getLibFr() + "\n");
            }
            writer.close();
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
    public void ajouterMission(Mission m){
        this.listMis.add(m);
    }
   
   

}
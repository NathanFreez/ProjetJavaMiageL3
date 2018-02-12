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
public class Entreprise extends ChargerEntrepriseCSV{

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
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
 * Entreprise
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
        listForm = new ArrayList<Formation>();
        chargerEmploye();
        chargerCompetence();
        chargerCompetenceEmploye();
        chargerMission();
        chargerCompetenceMission();
        chargerEmployeMission();
        chargerFormation();
        chargerCompetenceEtEmployeFormation();
    }

    /**
     * Permet d'ajouter une mission à la liste des mission de l'entreprise
     * @param m
     * @throws IOException
     */
    public void ajouterMission(Mission m) throws IOException{
        this.listMis.add(m);
        sauvegarderMission(listMis);
        sauvegardeCompetenceMission(listMis);
    }
    
    /**
     * Permet d'ajouter une formation à la liste des formation de l'entreprise
     * @param f
     * @throws IOException
     */
    public void ajouterFormation(Formation f) throws IOException{
        this.listForm.add(f);
        sauvegarderFormation(listForm);
        sauvegardeCompetenceFormation(listForm);
        sauvegardeEmployeFormation(listForm);
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
     * Methode qui permet de envoyer la liste des formations de l'entreprise
     * @return
     */
    public ArrayList<Formation> getListForm() {
        return listForm;
    }
    
    /**
     *
     * @param ent
     * @param d
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void mAJDate(Entreprise ent, Date d) throws FileNotFoundException, IOException{
        //Mise à jour de l'avancement des mission
        ArrayList majMission = ent.getListMis();
        for(int i = 0; i < majMission.size(); i++){
            //Récupération mission par mission
            Mission m = (Mission) majMission.get(i);
            //Gestion de la date du début et de fin du projet
            Date dateDebM = m.getDateD();
            Date dateFinM = new Date();
            dateFinM.setDate(dateFinM.getDate()+m.duree);
            //Comparaison des intervalles de temps
            if (d.after(dateFinM)){
                m.setType(TypeMission.termine);
            }
            if(d.after(dateDebM) && d.before(dateFinM)){
                m.setType(TypeMission.encours);
            }
            if(d.before(dateDebM)){
                m.setType(TypeMission.planifie);
            }
        }
        sauvegarderMission(majMission);
        
        //Mise à jour de l'avancement des formations
        ArrayList <Formation> majFormation = ent.getListForm();
        for(int i = 0; i < majFormation.size(); i++){
            //Récupération formation par formation
            Formation f = majFormation.get(i);
            //Gestion de la date du début et de fin de la formation
            Date dateDebF = f.getDateD();
            Date dateFinF = new Date();
            dateFinF.setDate(dateFinF.getDate()+f.duree);
            if (d.after(dateFinF)){
                //Ajout de la compétence Formation à l'employé
                
                f.setType(TypeMission.termine);
                Employe e = f.getEmp();
                ArrayList<String> test = e.getListeComp();
                Competence c = f.getComp();
                
                sauvegardeListeComp(e.getId(),c.getId());
            }
            if(d.after(dateDebF) && d.before(dateFinF)){
                f.setType(TypeMission.encours);
            }
            if(d.before(dateDebF)){
                f.setType(TypeMission.planifie);
            }
            
        }
        sauvegarderFormation(majFormation);
        
    }
    

}
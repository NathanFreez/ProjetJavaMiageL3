package projetmiagel3;

import java.io.*;
import java.util.*;



public class Proposition {
    
    /*
    Fonction EmployeChoisis qui renvoit une arraylist IdEmployÃ© maitrisant la competence
    */
    public static  ArrayList <String> EmployeChoisis (String competenceR) throws IOException {
       String line;
       String fichierMission = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_personnel.csv";
       java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierMission));
       ArrayList <String> listEmpChoisis = new ArrayList<>();
       try {
           while ((line = entree.nextLine()) != null) { 
                if (line.contains(competenceR)) { 
                    String a = emp(line);
                    listEmpChoisis.add(a);
                    
                } 
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return listEmpChoisis;
    }
    
    /*
    hm renvoit une hashmap IdEmploye,integer (nombre de competences missions maitrisees)
    */
    public static HashMap<String,Integer> hm (String competenceR) throws IOException {
        HashMap<String,Integer> hm = new HashMap<String,Integer>();
        String line;
        String fichierMission = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_personnel.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierMission));
        try {
           while ((line = entree.nextLine()) != null) { 
                if (line.contains(competenceR)) { 
                    String a = emp(line);
                    hm.put(a, new Integer(1));
                } 
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return hm;
    }
    
    /*
    recupMission recupere les missions du csv
    */
    public static Mission recupMission () throws FileNotFoundException {
        String line;
        String fichierMission = System.getProperty("user.dir") + "\\src\\projetmiagel3\\liste_missions.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierMission));
        try {
            if ((line = entree.nextLine()) != null){
                String values[] = line.split(";");
                Mission MisTmp = new Mission(values[0], values[1], values[3], Integer.parseInt(values[4]), Integer.parseInt(values[5]));
                return MisTmp;
            }
            else 
                return null;
        }
        catch (Exception e){
            return null;
        }
    }
    
    /*
    comp utilisÃ© pour rÃ©cup IdMission sans le nombre de personnes necessaires
    */
    public static String comp (String c) {
        String valeur[] = c.split(":");
        return valeur[0];
    }
    
    /*
    emp utilisÃ© pour rÃ©cup Id seulement
    */
    public static String emp (String c) {
        String valeur[] = c.split(";");
        return valeur[0];
    }
    
    /*
    recupCompMission recupere une competence de la mission
    */
    public static Competence recupCompMis (Mission m) throws FileNotFoundException {
        String line;
        String fichierCompetence = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_mission.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierCompetence));
        try {
            if ((line = entree.nextLine()) != null){
                String values[] = line.split(";"); 
                Competence compTmp = new Competence(values[0], values[1], values[2]);
                String a = comp(values[1]);
                String b = comp(values[2]);
                compTmp.setLibEng(a);
                compTmp.setLibFr(b);
                return compTmp;        
            }
            else 
                return null;
        }
        catch (Exception e){
            return null;
        }
    }
    /*
    afficherHM permet d'afficher hashmap string,integer
    */
    public static void afficherHM (HashMap<String,Integer> listcomp) {
        for (String s : listcomp.keySet()) {
            
            System.out.println("EmployÃ© "+s+" - /Nombre de compÃ©tences missions : "+listcomp.get(s));

        }

    }
    
    public static void main (String args[]) throws IOException {
        //liste des employÃ©s du plus intÃ©ressant au moins intÃ©ressant
        //hashmap employÃ© compteur
        
        //RÃ©cup mission
        Mission m = recupMission();
        System.out.println(m.toString());
        
        Competence c = recupCompMis (m);
        
        System.out.println(c.getId()+" "+c.getLibEng()+" "+c.getLibFr());
        
        //ArrayList <String> list = EmployeChoisis ();
        HashMap<String,Integer> hm = hm (c.getLibEng());
        afficherHM(hm);
              
        //Evoluer : renvoyer toutes les compÃ©tences de la mission (arraylist)
        //Parcourir arraylist et appeler la fonction hm pour chaque competence
        //Ne plus toucher aux fichiers, appeler directement depuis entreprise les getters des listes
    }
}

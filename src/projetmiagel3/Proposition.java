package projetmiagel3;

import java.io.*;
import java.util.*;



public class Proposition {
    
    public Proposition () {
        
    }
    /*
    Fonction EmployeChoisis qui renvoit une arraylist IdEmployÃƒÂ© maitrisant la competence
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
    public static HashMap<String,Integer> EmployeMap (ArrayList <String> listcompM) throws IOException {
        HashMap<String,Integer> hm = new HashMap<String,Integer>();
        String line;
        String fichierMission = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_personnel.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierMission));
        try {
           while ((entree.hasNext()) && (line = entree.nextLine()) != null) { 
               for (int i = 0; i<listcompM.size();i++) {
                    if (line.contains(listcompM.get(i))) {
                        String a = emp(line);
                        Integer obj = hm.get(a);
                        if (obj == null)
                            hm.put(a, new Integer(1)); 
                        else
                        {
                            // sinon on incrÃ©mente
                            Integer prec = (Integer) obj;
                            hm.put(a, new Integer(prec.intValue()+1));
                        }
                            
                    } 
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
    /*public static Mission recupMission () throws FileNotFoundException {
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
    comp utilisÃƒÂ© pour rÃƒÂ©cup IdMission sans le nombre de personnes necessaires
    */
    public static String comp (String c) {
        String valeur[] = c.split(":");
        return valeur[0];
    }
    
    /*
    emp utilisÃƒÂ© pour rÃƒÂ©cup Id seulement
    */
    public static String emp (String c) {
        String valeur[] = c.split(";");
        return valeur[0];
    }
    

    public static ArrayList <String> recupComp (Mission m) throws IOException {
        ArrayList <String> listcomp = new ArrayList <String>();
        String line;
        String idMission = m.getId();
        String fichierCompetence = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_mission.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierCompetence));
        try {
            while ((line = entree.nextLine()) != null) { //Lecture du fichier
                String values[] = line.split(";");
                if (idMission.equals(values[0])) {
                    for (int i=1; i <values.length; i++) {
                        String a = comp(values[i]);
                        listcomp.add(a);
                    }
                }
                
            }
            return listcomp;
        } catch (Exception e) {
            return listcomp;
        }
    }
    /*
    afficherHM permet d'afficher hashmap string,integer
    */
    public static void afficherHM (HashMap<String,Integer> listcomp) {
        for (String s : listcomp.keySet()) {
            System.out.println("EmployÃ© "+s+" - Nombre de compÃ©tences missions : "+listcomp.get(s));
        }
    }
    
    public static HashMap <String,String> CompetenceEmploye () throws FileNotFoundException {
        String line;
        String fichierMission = System.getProperty("user.dir") + "\\src\\projetmiagel3\\competences_personnel.csv";
        java.util.Scanner entree = new java.util.Scanner(new FileReader(fichierMission));  
        HashMap <String,String> CompetenceEmploye = new HashMap <String,String>();
        try {
            line = entree.nextLine();
           while ((entree.hasNext())&&(line = entree.nextLine()) != null) { 
                String[] valeur = line.split(";");
                String s = valeur[1];
                for (int i=2;i<valeur.length;i++) {
                    s += " - " +valeur[i];
                }
                CompetenceEmploye.put(valeur[0], "Competence "+s);
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return CompetenceEmploye;
    }
    
    public static void main (String args[]) throws IOException {
        //liste des employÃƒÂ©s du plus intÃƒÂ©ressant au moins intÃƒÂ©ressant
        //hashmap employÃƒÂ© compteur
        
        //RÃƒÂ©cup mission
        /*Mission m = recupMission();
        System.out.println(m.toString());
        
        
        ArrayList<String> listeCompetence = recupComp (m);
        for(int i = 0; i<listeCompetence.size();i++)
            System.out.println(listeCompetence.get(i));
        HashMap<String,Integer> hm = EmployeMap (listeCompetence);
        afficherHM(hm);*/
        HashMap <String,String> CompetenceEmploye = CompetenceEmploye();
        for (String s : CompetenceEmploye.keySet())
            System.out.println(s+" "+CompetenceEmploye.get(s));
        

        //Ne plus toucher aux fichiers, appeler directement depuis entreprise les getters des listes
    }
}

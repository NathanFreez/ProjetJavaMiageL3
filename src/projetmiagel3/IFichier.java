package projetmiagel3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface permettant d'instancier les diverses méthodes lié à l'entreprise
 * L'ensemble des méthodes permet de charger en mémoire une entreprise
 * Ou de sauvegarder les divers fichiers liés à l'entreprise
 * 
 */
public interface IFichier {
    public abstract void chargerEmploye()throws FileNotFoundException;
    public abstract void chargerCompetence()throws FileNotFoundException;
    public abstract void chargerCompetenceEmploye()throws FileNotFoundException;
    public abstract void chargerMission() throws FileNotFoundException;
    public abstract void chargerCompetenceMission() throws FileNotFoundException;
    public abstract void sauvegarderEmploye(ArrayList<Employe> listE) throws IOException;
    public abstract void sauvegarderCompetence(ArrayList<Competence> listC) throws IOException;
    public abstract void sauvegarderMission(ArrayList<Mission> listM) throws IOException;
    public abstract void sauvegardeCompetenceMission(ArrayList<Mission> listM) throws IOException;
}

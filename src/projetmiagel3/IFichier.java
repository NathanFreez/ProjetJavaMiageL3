/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author VIALA Guillaume
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

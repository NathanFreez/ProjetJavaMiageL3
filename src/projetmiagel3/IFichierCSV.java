/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

import java.io.FileNotFoundException;

/**
 *
 * @author VIALA Guillaume
 */
public interface IFichierCSV extends IFichier{
    public abstract void chargerEmploye()throws FileNotFoundException;
    public abstract void chargerCompetence()throws FileNotFoundException;
    public abstract void chargerCompetenceEmploye()throws FileNotFoundException;    
}

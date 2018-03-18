/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetmiagel3;

/**
 * Competence a effectuer une tache
 * @author nathan
 */
public class Competence {
    private String id;
    private String libEng;
    private String libFr;

    /**
     * Constructeur de Competence vide
     */
    public Competence() {
    }
    
    /**
     * Constructeur de Competence avec un id, une description anglaise et une description française 
     * @param id
     * @param libEng
     * @param libFr
     */
    public Competence(String id, String libEng, String libFr) {
        this.id = id;
        this.libEng = libEng;
        this.libFr = libFr;
    }

    /**
     * Retourne l'id d'un Competence
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * retourne la description anglaise d'une Comptence
     * @return
     */
    public String getLibEng() {
        return libEng;
    }

    /**
     * retourne la description française d'une Comptence
     * @return
     */
    public String getLibFr() {
        return libFr;
    }

    /**
     * Défini comment une competence doit d'afficher
     * @return
     */
    @Override
    public String toString() {
        return id + " " + libFr + "(" +libEng +")";
    }

    /**
     * Modifie l'id d'une comptence
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * modifie la description anglaise d'une competence
     * @param libEng
     */
    public void setLibEng(String libEng) {
        this.libEng = libEng;
    }

    /**
     * modifie la description anglaise d'une competence
     * @param libFr
     */
    public void setLibFr(String libFr) {
        this.libFr = libFr;
    }
}

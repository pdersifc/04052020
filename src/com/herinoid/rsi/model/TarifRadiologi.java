/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model;

/**
 *
 * @author Hewrei
 */
public class TarifRadiologi {
    private String kodeTarif;
    private String namaTarif;
    private double tarif;
    private String kelas;
    
    public TarifRadiologi(){}

    /**
     * @return the kodeTarif
     */
    public String getKodeTarif() {
        return kodeTarif;
    }

    /**
     * @param kodeTarif the kodeTarif to set
     */
    public void setKodeTarif(String kodeTarif) {
        this.kodeTarif = kodeTarif;
    }

    /**
     * @return the namaTarif
     */
    public String getNamaTarif() {
        return namaTarif;
    }

    /**
     * @param namaTarif the namaTarif to set
     */
    public void setNamaTarif(String namaTarif) {
        this.namaTarif = namaTarif;
    }

    /**
     * @return the tarif
     */
    public double getTarif() {
        return tarif;
    }

    /**
     * @param tarif the tarif to set
     */
    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    /**
     * @return the kelas
     */
    public String getKelas() {
        return kelas;
    }

    /**
     * @param kelas the kelas to set
     */
    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
    
    
    
}

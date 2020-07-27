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
public class TarifOperasi {
    private String kelas;
    private String namaPaket;
    private double tarif; 

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

    /**
     * @return the namaPaket
     */
    public String getNamaPaket() {
        return namaPaket;
    }

    /**
     * @param namaPaket the namaPaket to set
     */
    public void setNamaPaket(String namaPaket) {
        this.namaPaket = namaPaket;
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
    
    
}

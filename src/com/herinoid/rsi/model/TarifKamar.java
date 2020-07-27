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
public class TarifKamar {
    private String kodeKamar;
    private String kodeBangsal;
    private String kelas;
    private double tarif;
    
    public TarifKamar(){}

    /**
     * @return the kodeKamar
     */
    public String getKodeKamar() {
        return kodeKamar;
    }

    /**
     * @param kodeKamar the kodeKamar to set
     */
    public void setKodeKamar(String kodeKamar) {
        this.kodeKamar = kodeKamar;
    }

    /**
     * @return the kodeBangsal
     */
    public String getKodeBangsal() {
        return kodeBangsal;
    }

    /**
     * @param kodeBangsal the kodeBangsal to set
     */
    public void setKodeBangsal(String kodeBangsal) {
        this.kodeBangsal = kodeBangsal;
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

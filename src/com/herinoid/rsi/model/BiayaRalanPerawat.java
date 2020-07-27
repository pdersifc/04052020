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
public class BiayaRalanPerawat {
    private String kodeRawat;
    private String namaRawat;
    private double jumlah;
    private double biaya;
    private double total;
        
    public BiayaRalanPerawat(){}

    /**
     * @return the kodeRawat
     */
    public String getKodeRawat() {
        return kodeRawat;
    }

    /**
     * @param kodeRawat the kodeRawat to set
     */
    public void setKodeRawat(String kodeRawat) {
        this.kodeRawat = kodeRawat;
    }

    /**
     * @return the namaRawat
     */
    public String getNamaRawat() {
        return namaRawat;
    }

    /**
     * @param namaRawat the namaRawat to set
     */
    public void setNamaRawat(String namaRawat) {
        this.namaRawat = namaRawat;
    }

    /**
     * @return the jumlah
     */
    public double getJumlah() {
        return jumlah;
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    /**
     * @return the biaya
     */
    public double getBiaya() {
        return biaya;
    }

    /**
     * @param biaya the biaya to set
     */
    public void setBiaya(double biaya) {
        this.biaya = biaya;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }
    
    
}

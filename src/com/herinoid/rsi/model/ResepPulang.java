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
public class ResepPulang {
    private String kdObat;
    private String namaObat;
    private double hargaSatuan;
    private double jumlah;
    private double total;
    private double pembanding;
    private double iurPasien;
    
    
    public ResepPulang(){}

    /**
     * @return the kdObat
     */
    public String getKdObat() {
        return kdObat;
    }

    /**
     * @param kdObat the kdObat to set
     */
    public void setKdObat(String kdObat) {
        this.kdObat = kdObat;
    }

    /**
     * @return the namaObat
     */
    public String getNamaObat() {
        return namaObat;
    }

    /**
     * @param namaObat the namaObat to set
     */
    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    /**
     * @return the hargaSatuan
     */
    public double getHargaSatuan() {
        return hargaSatuan;
    }

    /**
     * @param hargaSatuan the hargaSatuan to set
     */
    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
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

    /**
     * @return the pembanding
     */
    public double getPembanding() {
        return pembanding;
    }

    /**
     * @param pembanding the pembanding to set
     */
    public void setPembanding(double pembanding) {
        this.pembanding = pembanding;
    }

    /**
     * @return the iurPasien
     */
    public double getIurPasien() {
        return iurPasien;
    }

    /**
     * @param iurPasien the iurPasien to set
     */
    public void setIurPasien(double iurPasien) {
        this.iurPasien = iurPasien;
    }
    
    
}

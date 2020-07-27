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
public class BillingSimulator {
    private int nomer;
    private String uraian;
    private double jumlah;
    private double tagihan;
    private double pembanding;
    private double iurPasien;
    
    public BillingSimulator(){}

    /**
     * @return the uraian
     */
    public String getUraian() {
        return uraian;
    }

    /**
     * @param uraian the uraian to set
     */
    public void setUraian(String uraian) {
        this.uraian = uraian;
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
     * @return the tagihan
     */
    public double getTagihan() {
        return tagihan;
    }

    /**
     * @param tagihan the tagihan to set
     */
    public void setTagihan(double tagihan) {
        this.tagihan = tagihan;
    }

    /**
     * @return the nomer
     */
    public int getNomer() {
        return nomer;
    }

    /**
     * @param nomer the nomer to set
     */
    public void setNomer(int nomer) {
        this.nomer = nomer;
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

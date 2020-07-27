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
public class ProlanisDetail {
    private String kodeObat;
    private String namaObat;
    private String jumlah;
    private String satuan;
    private String harga;
    private String diskon;
    private String total;
    
    public ProlanisDetail(){}

    /**
     * @return the kodeObat
     */
    public String getKodeObat() {
        return kodeObat;
    }

    /**
     * @param kodeObat the kodeObat to set
     */
    public void setKodeObat(String kodeObat) {
        this.kodeObat = kodeObat;
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
     * @return the jumlah
     */
    public String getJumlah() {
        return jumlah;
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    /**
     * @return the satuan
     */
    public String getSatuan() {
        return satuan;
    }

    /**
     * @param satuan the satuan to set
     */
    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    /**
     * @return the harga
     */
    public String getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(String harga) {
        this.harga = harga;
    }

    /**
     * @return the diskon
     */
    public String getDiskon() {
        return diskon;
    }

    /**
     * @param diskon the diskon to set
     */
    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }
    
    
    
}

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
public class ObatBhp {
    private String kodeBarang;
    private String namaBarang;
    private String jenisBarang;
    private double biaya;
    private double jumlah;
    private double tambahan;
    private double total;
    
    public ObatBhp(){}

    /**
     * @return the kodeBarang
     */
    public String getKodeBarang() {
        return kodeBarang;
    }

    /**
     * @param kodeBarang the kodeBarang to set
     */
    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    /**
     * @return the namaBarang
     */
    public String getNamaBarang() {
        return namaBarang;
    }

    /**
     * @param namaBarang the namaBarang to set
     */
    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    /**
     * @return the jenisBarang
     */
    public String getJenisBarang() {
        return jenisBarang;
    }

    /**
     * @param jenisBarang the jenisBarang to set
     */
    public void setJenisBarang(String jenisBarang) {
        this.jenisBarang = jenisBarang;
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
     * @return the tambahan
     */
    public double getTambahan() {
        return tambahan;
    }

    /**
     * @param tambahan the tambahan to set
     */
    public void setTambahan(double tambahan) {
        this.tambahan = tambahan;
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

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
public class Pasien {
    private String norm;
    private String nama;
    private String alamat;
    private String noTelp;
    private String namaIbu;
    
    public Pasien(){}
    
    public Pasien(String norm,String nama,String alamat,String noTelp,String namaIbu){
        this.norm = norm;
        this.nama = nama;
        this.alamat = alamat;
        this.noTelp = noTelp;
        this.namaIbu = namaIbu;
    }

    /**
     * @return the norm
     */
    public String getNorm() {
        return norm;
    }

    /**
     * @param norm the norm to set
     */
    public void setNorm(String norm) {
        this.norm = norm;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the alamat
     */
    public String getAlamat() {
        return alamat;
    }

    /**
     * @param alamat the alamat to set
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    /**
     * @return the noTelp
     */
    public String getNoTelp() {
        return noTelp;
    }

    /**
     * @param noTelp the noTelp to set
     */
    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    /**
     * @return the namaIbu
     */
    public String getNamaIbu() {
        return namaIbu;
    }

    /**
     * @param namaIbu the namaIbu to set
     */
    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }
    
    
}

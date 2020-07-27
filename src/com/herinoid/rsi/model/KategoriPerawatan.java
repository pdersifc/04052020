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
public class KategoriPerawatan {
    private String kodeKategori;
    private String namaKategori;
    
    public KategoriPerawatan(){}

    /**
     * @return the kodeKategori
     */
    public String getKodeKategori() {
        return kodeKategori;
    }

    /**
     * @param kodeKategori the kodeKategori to set
     */
    public void setKodeKategori(String kodeKategori) {
        this.kodeKategori = kodeKategori;
    }

    /**
     * @return the namaKategori
     */
    public String getNamaKategori() {
        return namaKategori;
    }

    /**
     * @param namaKategori the namaKategori to set
     */
    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
    
    
}

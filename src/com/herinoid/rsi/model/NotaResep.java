/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model;

import java.util.List;

/**
 *
 * @author Hewrei
 */
public class NotaResep {
    private String noresep;
    private String tglResep;
    private String pasien;
    private String dokter;
    private List<RincianResepVerifikasi> details;
    
    public NotaResep(){}

    /**
     * @return the noresep
     */
    public String getNoresep() {
        return noresep;
    }

    /**
     * @param noresep the noresep to set
     */
    public void setNoresep(String noresep) {
        this.noresep = noresep;
    }

    /**
     * @return the tglResep
     */
    public String getTglResep() {
        return tglResep;
    }

    /**
     * @param tglResep the tglResep to set
     */
    public void setTglResep(String tglResep) {
        this.tglResep = tglResep;
    }

    /**
     * @return the pasien
     */
    public String getPasien() {
        return pasien;
    }

    /**
     * @param pasien the pasien to set
     */
    public void setPasien(String pasien) {
        this.pasien = pasien;
    }

    /**
     * @return the dokter
     */
    public String getDokter() {
        return dokter;
    }

    /**
     * @param dokter the dokter to set
     */
    public void setDokter(String dokter) {
        this.dokter = dokter;
    }

    /**
     * @return the details
     */
    public List<RincianResepVerifikasi> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(List<RincianResepVerifikasi> details) {
        this.details = details;
    }
    
    
    
}

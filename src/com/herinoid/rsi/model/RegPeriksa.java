/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model;

import java.util.Date;

/**
 *
 * @author Hewrei
 */
public class RegPeriksa {
    private String noRawat;
    private Date tanggalRawat;
    private String jamRawat;
    private String kdPj;
    private String norm;
    private String statusBayar;
    private String kdPoli;
    
    public RegPeriksa(){}

    /**
     * @return the noRawat
     */
    public String getNoRawat() {
        return noRawat;
    }

    /**
     * @param noRawat the noRawat to set
     */
    public void setNoRawat(String noRawat) {
        this.noRawat = noRawat;
    }

    /**
     * @return the tanggalRawat
     */
    public Date getTanggalRawat() {
        return tanggalRawat;
    }

    /**
     * @param tanggalRawat the tanggalRawat to set
     */
    public void setTanggalRawat(Date tanggalRawat) {
        this.tanggalRawat = tanggalRawat;
    }

    /**
     * @return the jamRawat
     */
    public String getJamRawat() {
        return jamRawat;
    }

    /**
     * @param jamRawat the jamRawat to set
     */
    public void setJamRawat(String jamRawat) {
        this.jamRawat = jamRawat;
    }

    /**
     * @return the kdPj
     */
    public String getKdPj() {
        return kdPj;
    }

    /**
     * @param kdPj the kdPj to set
     */
    public void setKdPj(String kdPj) {
        this.kdPj = kdPj;
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
     * @return the statusBayar
     */
    public String getStatusBayar() {
        return statusBayar;
    }

    /**
     * @param statusBayar the statusBayar to set
     */
    public void setStatusBayar(String statusBayar) {
        this.statusBayar = statusBayar;
    }

    /**
     * @return the kdPoli
     */
    public String getKdPoli() {
        return kdPoli;
    }

    /**
     * @param kdPoli the kdPoli to set
     */
    public void setKdPoli(String kdPoli) {
        this.kdPoli = kdPoli;
    }
    
    
    
    
}

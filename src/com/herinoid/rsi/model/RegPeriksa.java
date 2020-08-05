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
    
    
    
    
}

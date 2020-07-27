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
public class GambarRadiologi {
    private String noRawat;
    private String tanggal;
    private String jam;
    private String lokasiGambar;

    public GambarRadiologi(){}
    
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
     * @return the tanggal
     */
    public String getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the jam
     */
    public String getJam() {
        return jam;
    }

    /**
     * @param jam the jam to set
     */
    public void setJam(String jam) {
        this.jam = jam;
    }

    /**
     * @return the lokasiGambar
     */
    public String getLokasiGambar() {
        return lokasiGambar;
    }

    /**
     * @param lokasiGambar the lokasiGambar to set
     */
    public void setLokasiGambar(String lokasiGambar) {
        this.lokasiGambar = lokasiGambar;
    }
    
    
    
}

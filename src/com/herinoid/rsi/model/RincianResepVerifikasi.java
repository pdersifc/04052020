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
public class RincianResepVerifikasi {
    private String kodeObat;
    private String namaObat;
    private String rincian;
    private String aturanPakai;
    
    public RincianResepVerifikasi(){}

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
     * @return the rincian
     */
    public String getRincian() {
        return rincian;
    }

    /**
     * @param rincian the rincian to set
     */
    public void setRincian(String rincian) {
        this.rincian = rincian;
    }

    /**
     * @return the aturanPakai
     */
    public String getAturanPakai() {
        return aturanPakai;
    }

    /**
     * @param aturanPakai the aturanPakai to set
     */
    public void setAturanPakai(String aturanPakai) {
        this.aturanPakai = aturanPakai;
    }
    
    
}

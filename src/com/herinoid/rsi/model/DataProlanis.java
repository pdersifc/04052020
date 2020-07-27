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
public class DataProlanis {
    private String noRekamMedis;
    private String namaPasien;
    private String noNota;
    private String tglPiutang;
    private String jatuhTempo;
    private String lokasi;
    private List<ProlanisDetail> prolanisDetails;
    
    public DataProlanis(){}
    

    /**
     * @return the namaPasien
     */
    public String getNamaPasien() {
        return namaPasien;
    }

    /**
     * @param namaPasien the namaPasien to set
     */
    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    /**
     * @return the noNota
     */
    public String getNoNota() {
        return noNota;
    }

    /**
     * @param noNota the noNota to set
     */
    public void setNoNota(String noNota) {
        this.noNota = noNota;
    }

    /**
     * @return the tglPiutang
     */
    public String getTglPiutang() {
        return tglPiutang;
    }

    /**
     * @param tglPiutang the tglPiutang to set
     */
    public void setTglPiutang(String tglPiutang) {
        this.tglPiutang = tglPiutang;
    }

    /**
     * @return the jatuhTempo
     */
    public String getJatuhTempo() {
        return jatuhTempo;
    }

    /**
     * @param jatuhTempo the jatuhTempo to set
     */
    public void setJatuhTempo(String jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    /**
     * @return the lokasi
     */
    public String getLokasi() {
        return lokasi;
    }

    /**
     * @param lokasi the lokasi to set
     */
    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    /**
     * @return the prolanisDetails
     */
    public List<ProlanisDetail> getProlanisDetails() {
        return prolanisDetails;
    }

    /**
     * @param prolanisDetails the prolanisDetails to set
     */
    public void setProlanisDetails(List<ProlanisDetail> prolanisDetails) {
        this.prolanisDetails = prolanisDetails;
    }

    /**
     * @return the noRekamMedis
     */
    public String getNoRekamMedis() {
        return noRekamMedis;
    }

    /**
     * @param noRekamMedis the noRekamMedis to set
     */
    public void setNoRekamMedis(String noRekamMedis) {
        this.noRekamMedis = noRekamMedis;
    }
    
    
    
}

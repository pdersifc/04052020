/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Hewrei
 */
public class Resep {
    public static final String STATUS_BELUM_VERIFIKASI = "BELUM VALIDASI";
    public static final String STATUS_SUDAH_VERIFIKASI = "TERVALIDASI";
    public static final String STATUS_SAMPAI_PASIEN = "SAMPAI PASIEN";
    public static final String STATUS_PACKING = "SELESAI PENGEPAKAN";
    private String noResep;
    private Date tglResep;
    private String jamResep;
    private String noRawat;
    private String kdDokter;
    private String status;
    private String jenisPasien;
    private List<ObatResep> obatResepDetail;
    private List<ObatResep> obatResepRacikanDetail;
    
    public Resep(){}

    /**
     * @return the noResep
     */
    public String getNoResep() {
        return noResep;
    }

    /**
     * @param noResep the noResep to set
     */
    public void setNoResep(String noResep) {
        this.noResep = noResep;
    }

    /**
     * @return the tglResep
     */
    public Date getTglResep() {
        return tglResep;
    }

    /**
     * @param tglResep the tglResep to set
     */
    public void setTglResep(Date tglResep) {
        this.tglResep = tglResep;
    }

    /**
     * @return the jamResep
     */
    public String getJamResep() {
        return jamResep;
    }

    /**
     * @param jamResep the jamResep to set
     */
    public void setJamResep(String jamResep) {
        this.jamResep = jamResep;
    }

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
     * @return the kdDokter
     */
    public String getKdDokter() {
        return kdDokter;
    }

    /**
     * @param kdDokter the kdDokter to set
     */
    public void setKdDokter(String kdDokter) {
        this.kdDokter = kdDokter;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the obatResepDetail
     */
    public List<ObatResep> getObatResepDetail() {
        return obatResepDetail;
    }

    /**
     * @param obatResepDetail the obatResepDetail to set
     */
    public void setObatResepDetail(List<ObatResep> obatResepDetail) {
        this.obatResepDetail = obatResepDetail;
    }

    /**
     * @return the obatResepRacikanDetail
     */
    public List<ObatResep> getObatResepRacikanDetail() {
        return obatResepRacikanDetail;
    }

    /**
     * @param obatResepRacikanDetail the obatResepRacikanDetail to set
     */
    public void setObatResepRacikanDetail(List<ObatResep> obatResepRacikanDetail) {
        this.obatResepRacikanDetail = obatResepRacikanDetail;
    }

    /**
     * @return the jenisPasien
     */
    public String getJenisPasien() {
        return jenisPasien;
    }

    /**
     * @param jenisPasien the jenisPasien to set
     */
    public void setJenisPasien(String jenisPasien) {
        this.jenisPasien = jenisPasien;
    }
    
    
}

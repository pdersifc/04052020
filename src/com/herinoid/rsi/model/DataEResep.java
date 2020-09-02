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
public class DataEResep {
    private String norm;
    private String noRawat;
    private String noResep;
    private String tglResep;
    private String poli;
    private String status;
    private String pasien;
    private String dokter;
    private String kdDokter;
    private String jaminan;
    private String validasi;
    private String diterima;
    private String packing;
    private List<ObatResep> obatDetails;
    private String statusBayar;

    public DataEResep() {
    }

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
     * @return the poli
     */
    public String getPoli() {
        return poli;
    }

    /**
     * @param poli the poli to set
     */
    public void setPoli(String poli) {
        this.poli = poli;
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
     * @return the jaminan
     */
    public String getJaminan() {
        return jaminan;
    }

    /**
     * @param jaminan the jaminan to set
     */
    public void setJaminan(String jaminan) {
        this.jaminan = jaminan;
    }

    /**
     * @return the validasi
     */
    public String getValidasi() {
        return validasi;
    }

    /**
     * @param validasi the validasi to set
     */
    public void setValidasi(String validasi) {
        this.validasi = validasi;
    }

    /**
     * @return the diterima
     */
    public String getDiterima() {
        return diterima;
    }

    /**
     * @param diterima the diterima to set
     */
    public void setDiterima(String diterima) {
        this.diterima = diterima;
    }

    /**
     * @return the obatDetails
     */
    public List<ObatResep> getObatDetails() {
        return obatDetails;
    }

    /**
     * @param obatDetails the obatDetails to set
     */
    public void setObatDetails(List<ObatResep> obatDetails) {
        this.obatDetails = obatDetails;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataEResep eresep = (DataEResep) o;

        if (getNoResep() == null ? eresep.getNoResep() != null : !getNoResep().equals(eresep.getNoResep())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getNoResep() != null ? getNoResep().hashCode() : 0;
    }

    /**
     * @return the packing
     */
    public String getPacking() {
        return packing;
    }

    /**
     * @param packing the packing to set
     */
    public void setPacking(String packing) {
        this.packing = packing;
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
    
    
}

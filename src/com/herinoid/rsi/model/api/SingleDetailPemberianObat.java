/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model.api;

/**
 *
 * @author HEWRIE
 */
import java.io.Serializable;
import java.util.Date;

public class SingleDetailPemberianObat implements Serializable {   
    private Date tglPerawatan;
    private Date jam;
    private String noRawat;
    private String kodeBrng;
    private String noBatch;
    private String noFaktur;
    private double hBeli;
    private double biayaObat;
    private double jml;
    private double embalase;
    private double tuslah;
    private double total;
    private String status;
    private String kdBangsal;
    private String aturanPakai;
    
    public SingleDetailPemberianObat(){}

    public Date getTglPerawatan() {
        return tglPerawatan;
    }

    public void setTglPerawatan(Date tglPerawatan) {
        this.tglPerawatan = tglPerawatan;
    }

    public Date getJam() {
        return jam;
    }

    public void setJam(Date jam) {
        this.jam = jam;
    }

    public String getNoRawat() {
        return noRawat;
    }

    public void setNoRawat(String noRawat) {
        this.noRawat = noRawat;
    }

    public String getKodeBrng() {
        return kodeBrng;
    }

    public void setKodeBrng(String kodeBrng) {
        this.kodeBrng = kodeBrng;
    }

    public String getNoBatch() {
        return noBatch;
    }

    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
    }

    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
    }

    public double gethBeli() {
        return hBeli;
    }

    public void sethBeli(double hBeli) {
        this.hBeli = hBeli;
    }

    public double getBiayaObat() {
        return biayaObat;
    }

    public void setBiayaObat(double biayaObat) {
        this.biayaObat = biayaObat;
    }

    public double getJml() {
        return jml;
    }

    public void setJml(double jml) {
        this.jml = jml;
    }

    public double getEmbalase() {
        return embalase;
    }

    public void setEmbalase(double embalase) {
        this.embalase = embalase;
    }

    public double getTuslah() {
        return tuslah;
    }

    public void setTuslah(double tuslah) {
        this.tuslah = tuslah;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKdBangsal() {
        return kdBangsal;
    }

    public void setKdBangsal(String kdBangsal) {
        this.kdBangsal = kdBangsal;
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

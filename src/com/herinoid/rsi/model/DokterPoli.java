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
public class DokterPoli {
    private String kodeDokter;
    private String namaDokter;
    private String kodePoli;
    private String namaPoli;
    private String ruang;
    
    public DokterPoli(){
        
    }

    /**
     * @return the kodeDokter
     */
    public String getKodeDokter() {
        return kodeDokter;
    }

    /**
     * @param kodeDokter the kodeDokter to set
     */
    public void setKodeDokter(String kodeDokter) {
        this.kodeDokter = kodeDokter;
    }

    /**
     * @return the namaDokter
     */
    public String getNamaDokter() {
        return namaDokter;
    }

    /**
     * @param namaDokter the namaDokter to set
     */
    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    /**
     * @return the kodePoli
     */
    public String getKodePoli() {
        return kodePoli;
    }

    /**
     * @param kodePoli the kodePoli to set
     */
    public void setKodePoli(String kodePoli) {
        this.kodePoli = kodePoli;
    }

    /**
     * @return the namaPoli
     */
    public String getNamaPoli() {
        return namaPoli;
    }

    /**
     * @param namaPoli the namaPoli to set
     */
    public void setNamaPoli(String namaPoli) {
        this.namaPoli = namaPoli;
    }

    /**
     * @return the ruang
     */
    public String getRuang() {
        return ruang;
    }

    /**
     * @param ruang the ruang to set
     */
    public void setRuang(String ruang) {
        this.ruang = ruang;
    }
    
    
    
}

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
public class MetodeRacikan {
    private String kode;
    private String metode;
    
    public MetodeRacikan(){
        
    }

    /**
     * @return the kode
     */
    public String getKode() {
        return kode;
    }

    /**
     * @param kode the kode to set
     */
    public void setKode(String kode) {
        this.kode = kode;
    }

    /**
     * @return the metode
     */
    public String getMetode() {
        return metode;
    }

    /**
     * @param metode the metode to set
     */
    public void setMetode(String metode) {
        this.metode = metode;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MetodeRacikan racikan = (MetodeRacikan) o;

        if (getKode() != racikan.getKode()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getKode().hashCode();
    }
    
    @Override
    public String toString() {
        return "com.herinoid.rsi.model[ kode=" + kode + " ]";
    }
}

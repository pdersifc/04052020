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
public class PengurangBiaya {
    private String noRawat;
    private String namaBiaya;
    private double biaya;
    
    public PengurangBiaya(){}

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
     * @return the namaBiaya
     */
    public String getNamaBiaya() {
        return namaBiaya;
    }

    /**
     * @param namaBiaya the namaBiaya to set
     */
    public void setNamaBiaya(String namaBiaya) {
        this.namaBiaya = namaBiaya;
    }

    /**
     * @return the biaya
     */
    public double getBiaya() {
        return biaya;
    }

    /**
     * @param biaya the biaya to set
     */
    public void setBiaya(double biaya) {
        this.biaya = biaya;
    }
    
    
}

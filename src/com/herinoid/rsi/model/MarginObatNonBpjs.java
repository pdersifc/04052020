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
public class MarginObatNonBpjs {
    private String kodeJaminan;
    private double margin;
    
    public MarginObatNonBpjs(){}
    
    public MarginObatNonBpjs(String kodeJaminan,double margin){
        this.kodeJaminan = kodeJaminan;
        this.margin = margin;
    }

    /**
     * @return the kodeJaminan
     */
    public String getKodeJaminan() {
        return kodeJaminan;
    }

    /**
     * @param kodeJaminan the kodeJaminan to set
     */
    public void setKodeJaminan(String kodeJaminan) {
        this.kodeJaminan = kodeJaminan;
    }

    /**
     * @return the margin
     */
    public double getMargin() {
        return margin;
    }

    /**
     * @param margin the margin to set
     */
    public void setMargin(double margin) {
        this.margin = margin;
    }
    
    
    
    
}

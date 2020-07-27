/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model;

/**
 *
 * @author Herinoid@gmail.com
 */
public class BorLosReport {
    private String bangsal;
    private int month;
    private String bor;
    private String los;
    private String bto;
    private String toi;
    
    public BorLosReport(){        
    }

    /**
     * @return the bangsal
     */
    public String getBangsal() {
        return bangsal;
    }

    /**
     * @param bangsal the bangsal to set
     */
    public void setBangsal(String bangsal) {
        this.bangsal = bangsal;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the bor
     */
    public String getBor() {
        return bor;
    }

    /**
     * @param bor the bor to set
     */
    public void setBor(String bor) {
        this.bor = bor;
    }

    /**
     * @return the los
     */
    public String getLos() {
        return los;
    }

    /**
     * @param los the los to set
     */
    public void setLos(String los) {
        this.los = los;
    }

    /**
     * @return the bto
     */
    public String getBto() {
        return bto;
    }

    /**
     * @param bto the bto to set
     */
    public void setBto(String bto) {
        this.bto = bto;
    }

    /**
     * @return the toi
     */
    public String getToi() {
        return toi;
    }

    /**
     * @param toi the toi to set
     */
    public void setToi(String toi) {
        this.toi = toi;
    }
    
    
}

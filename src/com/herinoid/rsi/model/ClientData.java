/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.model;

/**
 *
 * @author HEWRIE
 */
public class ClientData {
    private String unit;
    private int tunggal;
    private int racik;
    private boolean nonracik;

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the tunggal
     */
    public int getTunggal() {
        return tunggal;
    }

    /**
     * @param tunggal the tunggal to set
     */
    public void setTunggal(int tunggal) {
        this.tunggal = tunggal;
    }

    /**
     * @return the racik
     */
    public int getRacik() {
        return racik;
    }

    /**
     * @param racik the racik to set
     */
    public void setRacik(int racik) {
        this.racik = racik;
    }

    /**
     * @return the nonracik
     */
    public boolean isNonracik() {
        return nonracik;
    }

    /**
     * @param nonracik the nonracik to set
     */
    public void setNonracik(boolean nonracik) {
        this.nonracik = nonracik;
    }
    
    
}

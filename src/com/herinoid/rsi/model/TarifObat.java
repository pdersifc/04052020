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
public class TarifObat {
    private String kodeObat;
    private String namaObat;
    private double kelas1;
    private double kelas2;
    private double kelas3;
    private double kelasUtama;
    private double kelasVip;
    private double kelasVVIP;
    
    public TarifObat(){}

    /**
     * @return the kodeObat
     */
    public String getKodeObat() {
        return kodeObat;
    }

    /**
     * @param kodeObat the kodeObat to set
     */
    public void setKodeObat(String kodeObat) {
        this.kodeObat = kodeObat;
    }

    /**
     * @return the namaObat
     */
    public String getNamaObat() {
        return namaObat;
    }

    /**
     * @param namaObat the namaObat to set
     */
    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    /**
     * @return the kelas1
     */
    public double getKelas1() {
        return kelas1;
    }

    /**
     * @param kelas1 the kelas1 to set
     */
    public void setKelas1(double kelas1) {
        this.kelas1 = kelas1;
    }

    /**
     * @return the kelas2
     */
    public double getKelas2() {
        return kelas2;
    }

    /**
     * @param kelas2 the kelas2 to set
     */
    public void setKelas2(double kelas2) {
        this.kelas2 = kelas2;
    }

    /**
     * @return the kelas3
     */
    public double getKelas3() {
        return kelas3;
    }

    /**
     * @param kelas3 the kelas3 to set
     */
    public void setKelas3(double kelas3) {
        this.kelas3 = kelas3;
    }

    /**
     * @return the kelasUtama
     */
    public double getKelasUtama() {
        return kelasUtama;
    }

    /**
     * @param kelasUtama the kelasUtama to set
     */
    public void setKelasUtama(double kelasUtama) {
        this.kelasUtama = kelasUtama;
    }

    /**
     * @return the kelasVip
     */
    public double getKelasVip() {
        return kelasVip;
    }

    /**
     * @param kelasVip the kelasVip to set
     */
    public void setKelasVip(double kelasVip) {
        this.kelasVip = kelasVip;
    }

    /**
     * @return the kelasVVIP
     */
    public double getKelasVVIP() {
        return kelasVVIP;
    }

    /**
     * @param kelasVVIP the kelasVVIP to set
     */
    public void setKelasVVIP(double kelasVVIP) {
        this.kelasVVIP = kelasVVIP;
    }
    
    
}

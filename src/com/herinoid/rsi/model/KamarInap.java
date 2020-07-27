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
public class KamarInap {
    private String noRawat;
    private String kodeKamar;
    private String namaKamar;
    private double tarif;
    private String kelas;
    private double lama;
    private double total;
    
    public KamarInap(){}

    /**
     * @return the kodeKamar
     */
    public String getKodeKamar() {
        return kodeKamar;
    }

    /**
     * @param kodeKamar the kodeKamar to set
     */
    public void setKodeKamar(String kodeKamar) {
        this.kodeKamar = kodeKamar;
    }

    /**
     * @return the namaKamar
     */
    public String getNamaKamar() {
        return namaKamar;
    }

    /**
     * @param namaKamar the namaKamar to set
     */
    public void setNamaKamar(String namaKamar) {
        this.namaKamar = namaKamar;
    }

    /**
     * @return the tarif
     */
    public double getTarif() {
        return tarif;
    }

    /**
     * @param tarif the tarif to set
     */
    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    /**
     * @return the kelas
     */
    public String getKelas() {
        return kelas;
    }

    /**
     * @param kelas the kelas to set
     */
    public void setKelas(String kelas) {
        this.kelas = kelas;
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
     * @return the lama
     */
    public double getLama() {
        return lama;
    }

    /**
     * @param lama the lama to set
     */
    public void setLama(double lama) {
        this.lama = lama;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }
    
    
}

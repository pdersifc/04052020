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
public class ObatResep {
    private String kodeObat;
    private String namaObat;
    private int jumlah;
    private String satuan;
    private String kandungan;
    private double harga;
    private String jenisObat;
    private String aturanPakai;
    private String kategori;
    private double hargaBeli;
    private double stok;
    private String metodeRacikKode;
    private String metodeRacik;
    private String racikan;
    private boolean flag;
    private boolean parent;
    private String kodeRacikan;
    private int nomorRacik;
    private double embalase;
    private double tuslah;
    
    
    public ObatResep(){}
    public ObatResep(String kodeObat,String namaObat,String satuan,String kategori,String jenisObat,double stok){
        this.kodeObat = kodeObat;
        this.namaObat = namaObat;
        this.satuan = satuan;
        this.kategori = kategori;
        this.jenisObat = jenisObat;
        this.stok = stok;
    }

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
     * @return the jumlah
     */
    public int getJumlah() {
        return jumlah;
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    /**
     * @return the satuan
     */
    public String getSatuan() {
        return satuan;
    }

    /**
     * @param satuan the satuan to set
     */
    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    /**
     * @return the kandungan
     */
    public String getKandungan() {
        return kandungan;
    }

    /**
     * @param kandungan the kandungan to set
     */
    public void setKandungan(String kandungan) {
        this.kandungan = kandungan;
    }

    /**
     * @return the harga
     */
    public double getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(double harga) {
        this.harga = harga;
    }

    /**
     * @return the jenisObat
     */
    public String getJenisObat() {
        return jenisObat;
    }

    /**
     * @param jenisObat the jenisObat to set
     */
    public void setJenisObat(String jenisObat) {
        this.jenisObat = jenisObat;
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

    /**
     * @return the kategori
     */
    public String getKategori() {
        return kategori;
    }

    /**
     * @param kategori the kategori to set
     */
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    /**
     * @return the hargaBeli
     */
    public double getHargaBeli() {
        return hargaBeli;
    }

    /**
     * @param hargaBeli the hargaBeli to set
     */
    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    /**
     * @return the stok
     */
    public double getStok() {
        return stok;
    }

    /**
     * @param stok the stok to set
     */
    public void setStok(double stok) {
        this.stok = stok;
    }

    /**
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * @return the metodeRacik
     */
    public String getMetodeRacik() {
        return metodeRacik;
    }

    /**
     * @param metodeRacik the metodeRacik to set
     */
    public void setMetodeRacik(String metodeRacik) {
        this.metodeRacik = metodeRacik;
    }

    /**
     * @return the metodeRacikKode
     */
    public String getMetodeRacikKode() {
        return metodeRacikKode;
    }

    /**
     * @param metodeRacikKode the metodeRacikKode to set
     */
    public void setMetodeRacikKode(String metodeRacikKode) {
        this.metodeRacikKode = metodeRacikKode;
    }

    /**
     * @return the racikan
     */
    public String getRacikan() {
        return racikan;
    }

    /**
     * @param racikan the racikan to set
     */
    public void setRacikan(String racikan) {
        this.racikan = racikan;
    }

    /**
     * @return the parent
     */
    public boolean isParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(boolean parent) {
        this.parent = parent;
    }

    /**
     * @return the kodeRacikan
     */
    public String getKodeRacikan() {
        return kodeRacikan;
    }

    /**
     * @param kodeRacikan the kodeRacikan to set
     */
    public void setKodeRacikan(String kodeRacikan) {
        this.kodeRacikan = kodeRacikan;
    }

    /**
     * @return the nomorRacik
     */
    public int getNomorRacik() {
        return nomorRacik;
    }

    /**
     * @param nomorRacik the nomorRacik to set
     */
    public void setNomorRacik(int nomorRacik) {
        this.nomorRacik = nomorRacik;
    }

    /**
     * @return the embalase
     */
    public double getEmbalase() {
        return embalase;
    }

    /**
     * @param embalase the embalase to set
     */
    public void setEmbalase(double embalase) {
        this.embalase = embalase;
    }

    /**
     * @return the tuslah
     */
    public double getTuslah() {
        return tuslah;
    }

    /**
     * @param tuslah the tuslah to set
     */
    public void setTuslah(double tuslah) {
        this.tuslah = tuslah;
    }
    
    
    
}

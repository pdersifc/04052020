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
    private boolean flag;
    
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
    
    
    
}

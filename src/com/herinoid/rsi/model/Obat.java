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
public class Obat {
    public static final String OBAT_RACIKAN = "Racikan";
    private String kodeObat;
    private String namaObat;
    private String satuan;
    private String kandungan;
    private double harga;
    private String jenisObat;
    private String aturanPakai;
    private String kategori;
    private double hargaBeli;
    private double hargaDasar;
    private double stok;
    private double kapasitas;
    
    public Obat(){}

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
     * @return the kapasitas
     */
    public double getKapasitas() {
        return kapasitas;
    }

    /**
     * @param kapasitas the kapasitas to set
     */
    public void setKapasitas(double kapasitas) {
        this.kapasitas = kapasitas;
    }

    /**
     * @return the hargaDasar
     */
    public double getHargaDasar() {
        return hargaDasar;
    }

    /**
     * @param hargaDasar the hargaDasar to set
     */
    public void setHargaDasar(double hargaDasar) {
        this.hargaDasar = hargaDasar;
    }
    
    
    
}

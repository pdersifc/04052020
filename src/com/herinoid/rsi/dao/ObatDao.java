/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.util.Konstan;
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hewrei
 */
public class ObatDao {

    private static Connection koneksi = koneksiDB.condb();
    

    public static List<Obat> getObatByCategory(String kdBangsal, String kdKategori, String jenisPasien) {
        List<Obat> obatList = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet  rs = null;
        try {
            ps = koneksi.prepareStatement("SELECT databarang.kode_brng, databarang.nama_brng,jenis.nama AS jenis,kategori_barang.nama AS kategori,kodesatuan.satuan,"
                    + "databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1,"
                    + "databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,databarang.kapasitas,"
                    + "databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok "
                    + "FROM databarang INNER JOIN jenis INNER JOIN industrifarmasi INNER JOIN gudangbarang INNER JOIN kategori_barang INNER JOIN kodesatuan "
                    + "ON databarang.kdjns=jenis.kdjns AND databarang.kode_brng=gudangbarang.kode_brng AND databarang.kode_kategori=kategori_barang.kode AND databarang.kode_sat=kodesatuan.kode_sat "
                    + "AND industrifarmasi.kode_industri=databarang.kode_industri "
                    + "WHERE  databarang.status='1' AND gudangbarang.kd_bangsal=? AND databarang.kode_kategori=? ORDER BY databarang.nama_brng");
            ps.setString(1, kdBangsal);
            ps.setString(2, kdKategori);
            rs = ps.executeQuery();
            while (rs.next()) {
                Obat obat = new Obat();
                obat.setKodeObat(rs.getString("kode_brng"));
                obat.setNamaObat(rs.getString("nama_brng"));
                obat.setSatuan(rs.getString("satuan"));
                obat.setStok(rs.getInt("stok"));
                obat.setKategori(rs.getString("kategori"));
                obat.setJenisObat(rs.getString("jenis"));
                obat.setKapasitas(rs.getDouble("kapasitas"));
                double harga = rs.getDouble("ralan");
                obat.setHargaBeli(rs.getDouble("h_beli"));
                if (jenisPasien.equals(Konstan.PASIEN_KARYAWAN)) {
                    harga = rs.getDouble("karyawan");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS_VVIP)) {
                    harga = rs.getDouble("vvip");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS_VIP)) {
                    harga = rs.getDouble("vip");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS1)) {
                    harga = rs.getDouble("kelas1");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS2)) {
                    harga = rs.getDouble("kelas2");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS3)) {
                    harga = rs.getDouble("kelas3");
                }
//                System.out.println("letak barang = "+rs.getString("letak_barang"));
                obat.setHarga(harga);
                obatList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatList;
    }
    
    public static List<Obat> getObatByDepo(String kdBangsal,String jenisPasien) {
        List<Obat> obatList = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet  rs = null;
        try {
            ps = koneksi.prepareStatement("SELECT databarang.kode_brng, databarang.nama_brng,jenis.nama AS jenis,kategori_barang.nama AS kategori,kodesatuan.satuan,"
                    + "databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1,"
                    + "databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,databarang.kapasitas,"
                    + "databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok "
                    + "FROM databarang INNER JOIN jenis INNER JOIN industrifarmasi INNER JOIN gudangbarang INNER JOIN kategori_barang INNER JOIN kodesatuan "
                    + "ON databarang.kdjns=jenis.kdjns AND databarang.kode_brng=gudangbarang.kode_brng AND databarang.kode_kategori=kategori_barang.kode AND databarang.kode_sat=kodesatuan.kode_sat "
                    + "AND industrifarmasi.kode_industri=databarang.kode_industri "
                    + "WHERE  databarang.status='1' AND gudangbarang.kd_bangsal=? ORDER BY databarang.nama_brng");
            ps.setString(1, kdBangsal);
            rs = ps.executeQuery();
            while (rs.next()) {
                Obat obat = new Obat();
                obat.setKodeObat(rs.getString("kode_brng"));
                obat.setNamaObat(rs.getString("nama_brng"));
                obat.setSatuan(rs.getString("satuan"));
                obat.setStok(rs.getInt("stok"));
                obat.setKategori(rs.getString("kategori"));
                obat.setJenisObat(rs.getString("jenis"));
                obat.setKapasitas(rs.getDouble("kapasitas"));
                obat.setHargaBeli(rs.getDouble("h_beli"));
                double harga = rs.getDouble("ralan");
                if (jenisPasien.equals(Konstan.PASIEN_KARYAWAN)) {
                    harga = rs.getDouble("karyawan");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS_VVIP)) {
                    harga = rs.getDouble("vvip");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS_VIP)) {
                    harga = rs.getDouble("vip");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS1)) {
                    harga = rs.getDouble("kelas1");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS2)) {
                    harga = rs.getDouble("kelas2");
                }else if (jenisPasien.equals(Konstan.PASIEN_KELAS3)) {
                    harga = rs.getDouble("kelas3");
                }
//                System.out.println("letak barang = "+rs.getString("letak_barang"));
                obat.setHarga(harga);
                obatList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatList;
    }
    
    public static Obat getObat(String kdBangsal,String kodeObat) {
        Obat obat = null;
        PreparedStatement ps = null;
        ResultSet  rs = null;
        try {
            ps = koneksi.prepareStatement("SELECT databarang.kode_brng, databarang.nama_brng,jenis.nama AS jenis,kategori_barang.nama AS kategori,kodesatuan.satuan,"
                    + "databarang.ralan,databarang.ralan,databarang.beliluar,databarang.kelas1,"
                    + "databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,databarang.kapasitas,"
                    + "databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok "
                    + "FROM databarang INNER JOIN jenis INNER JOIN industrifarmasi INNER JOIN gudangbarang INNER JOIN kategori_barang INNER JOIN kodesatuan "
                    + "ON databarang.kdjns=jenis.kdjns AND databarang.kode_brng=gudangbarang.kode_brng AND databarang.kode_kategori=kategori_barang.kode AND databarang.kode_sat=kodesatuan.kode_sat "
                    + "AND industrifarmasi.kode_industri=databarang.kode_industri "
                    + "WHERE  databarang.status='1' AND gudangbarang.kd_bangsal=? AND databarang.kode_brng = ?");
            ps.setString(1, kdBangsal);
            ps.setString(2, kodeObat);
            rs = ps.executeQuery();
            while (rs.next()) {
                obat = new Obat();
                obat.setKodeObat(rs.getString("kode_brng"));
                obat.setNamaObat(rs.getString("nama_brng"));
                obat.setSatuan(rs.getString("satuan"));
                obat.setStok(rs.getInt("stok"));
                obat.setKategori(rs.getString("kategori"));
                obat.setJenisObat(rs.getString("jenis"));
                obat.setKapasitas(rs.getDouble("kapasitas"));
                obat.setHargaBeli(rs.getDouble("h_beli"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obat;
    }
}

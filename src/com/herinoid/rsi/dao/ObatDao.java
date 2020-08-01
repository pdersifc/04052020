/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ObatBhp;
import com.herinoid.rsi.model.TarifObat;
import com.herinoid.rsi.util.Konstan;
import static com.herinoid.rsi.util.Konstan.PASIEN_RALAN;
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static List<Obat> getObatByCategory(String kdBangsal, String kdKategori, String jenisPasien) {
        List<Obat> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT databarang.kode_brng, databarang.nama_brng,jenis.nama AS jenis,kategori_barang.nama AS kategori,kodesatuan.satuan,"
                    + "databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1,"
                    + "databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,"
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
                obat.setKandungan("Kimia");
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
}

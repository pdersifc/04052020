/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ObatBhp;
import com.herinoid.rsi.model.ObatResep;
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
public class PemberianObatDetailDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static List<ObatResep> getPemberianObatDetailByNorawat(String norawat,String kdBangsal) {
        List<ObatResep> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT d.no_rawat,o.kode_brng,o.nama_brng,d.jml,s.satuan,d.embalase,d.tuslah,g.stok,o.h_beli,d.biaya_obat,j.nama AS jenis,k.nama AS kategori "
                    + "FROM detail_pemberian_obat d "
                    + "INNER JOIN databarang o ON d.kode_brng = o.kode_brng "
                    + "INNER JOIN gudangbarang g ON d.kode_brng = g.kode_brng "
                    + "INNER JOIN kodesatuan s ON s.kode_sat=o.kode_sat INNER JOIN jenis j ON j.kdjns=o.kdjns INNER JOIN kategori_barang k ON k.kode=o.kode_kategori "
                    + "WHERE d.no_rawat = ? AND g.kd_bangsal=? ORDER BY o.nama_brng");
            ps.setString(1, norawat);
            ps.setString(2, kdBangsal);
            rs = ps.executeQuery();
            while (rs.next()) {
                ObatResep obat = new ObatResep();
                obat.setKodeObat(rs.getString("kode_brng"));
                obat.setNamaObat(rs.getString("nama_brng"));
                obat.setSatuan(rs.getString("satuan"));
                obat.setStok(rs.getInt("stok"));
                obat.setKategori(rs.getString("kategori"));
                obat.setEmbalase(rs.getDouble("embalase"));
                obat.setTuslah(rs.getDouble("tuslah"));
                obat.setJumlah(rs.getDouble("jml"));
                obat.setHarga(rs.getDouble("biaya_obat"));
                obat.setJenisObat(rs.getString("jenis"));
                obat.setKategori(rs.getString("kategori"));
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
                Logger.getLogger(PemberianObatDetailDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatList;
    }

    public static List<ObatResep> getResepByNoresep(String noResep, String depo) {
        List<ObatResep> obatDetailList = new LinkedList<>();
        PreparedStatement psttmn = null;
        ResultSet rset = null;
        try {
            psttmn = koneksi.prepareStatement("SELECT d.no_resep,o.kode_brng,o.nama_brng,d.jml,s.satuan,d.embalase,d.tuslah,d.aturan_pakai,g.stok,o.h_beli,o.karyawan,o.ralan,o.beliluar,o.kelas1,o.kelas2,o.kelas3,o.vip,o.vvip,j.nama AS jenis,k.nama AS kategori "
                    + "FROM e_resep_rsifc_detail d "
                    + "INNER JOIN databarang o ON d.kode_brng = o.kode_brng "
                    + "INNER JOIN gudangbarang g ON d.kode_brng = g.kode_brng "
                    + "INNER JOIN kodesatuan s ON s.kode_sat=o.kode_sat INNER JOIN jenis j ON j.kdjns=o.kdjns INNER JOIN kategori_barang k ON k.kode=o.kode_kategori "
                    + "WHERE d.no_resep = ? AND g.kd_bangsal=?");
            psttmn.setString(1, noResep);
            psttmn.setString(2, depo);
            rset = psttmn.executeQuery();
            while (rset.next()) {
                ObatResep obat = new ObatResep();
                obat.setKodeObat(rset.getString("kode_brng"));
                obat.setNamaObat(rset.getString("nama_brng"));
                obat.setJumlah(rset.getDouble("jml"));
                obat.setAturanPakai(rset.getString("aturan_pakai"));
                obat.setHargaBeli(rset.getDouble("h_beli"));
                obat.setSatuan(rset.getString("satuan"));
                obat.setJenisObat(rset.getString("jenis"));
                obat.setKategori(rset.getString("kategori"));
                obat.setEmbalase(rset.getDouble("embalase"));
                obat.setTuslah(rset.getDouble("tuslah"));
                obat.setStok(rset.getDouble("stok"));
                double harga = rset.getDouble("ralan");                
                obat.setHarga(harga);
                obatDetailList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (psttmn != null) {

                    psttmn.close();

                }
                if (rset != null) {
                    rset.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatDetailList;
    }

}

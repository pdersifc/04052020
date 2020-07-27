/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.RekapPembelianObat;
import com.herinoid.rsi.util.Utils;
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
public class RekapPembelianObatDAO {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static List<RekapPembelianObat> getPembelianByPeriode(Date startDate, Date endDate) {
        List<RekapPembelianObat> rekapPembelianObats = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT pembelian.`no_faktur`,pembelian.`tgl_beli`,pembelian.`kode_suplier`,datasuplier.`nama_suplier`,"
                    + "pembelian.total2,pembelian.`ppn`,pembelian.`tagihan`,petugas.`nama`,bangsal.`nm_bangsal` FROM pembelian "
                    + "LEFT JOIN datasuplier ON pembelian.`kode_suplier`=datasuplier.`kode_suplier` "
                    + "LEFT JOIN petugas ON pembelian.`nip`=petugas.`nip` LEFT JOIN bangsal ON pembelian.`kd_bangsal`=bangsal.`kd_bangsal` "
                    + "WHERE pembelian.tgl_beli BETWEEN ? AND ?");
            ps.setString(1, Utils.formatDb(startDate));
            ps.setString(2, Utils.formatDb(endDate));
            rs = ps.executeQuery();
            while (rs.next()) {  
                double discount = getDiskonFaktur(rs.getString("no_faktur"));
                RekapPembelianObat b = new RekapPembelianObat();
                b.setNoFaktur(rs.getString("no_faktur"));
                b.setTanggalBeli(Utils.format(rs.getDate("tgl_beli")));
                b.setNamaSuplier(rs.getString("nama_suplier"));
                b.setNamaPetugas(rs.getString("nama"));
                b.setJumlah(Utils.format(rs.getDouble("total2"), 0));
                b.setPpn(Utils.format(rs.getDouble("ppn"), 0));
                b.setTotal(Utils.format(rs.getDouble("tagihan") - discount, 0));
                b.setLokasi(rs.getString("nm_bangsal"));
                b.setDiskon(Utils.format(discount, 0));
                rekapPembelianObats.add(b);
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
                Logger.getLogger(BangsalDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rekapPembelianObats;
    }
    
    private static double getDiskonFaktur(String noFaktur) {
        PreparedStatement st = null;
        ResultSet res = null;
        double diskon = 0;
        try {
            st = koneksi.prepareStatement("SELECT SUM(besardis) FROM detailbeli WHERE no_faktur =?");
            st.setString(1, noFaktur);
            res = st.executeQuery();
            while (res.next()) {
                diskon = res.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BangsalDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return diskon;
    }
}

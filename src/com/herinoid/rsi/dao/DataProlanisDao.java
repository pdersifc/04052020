/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.DataProlanis;
import com.herinoid.rsi.model.ProlanisDetail;
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
public class DataProlanisDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static List<DataProlanis> getDataProlanisByTanggal(String fromDate, String toDate) {
        List<DataProlanis> proList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT * FROM piutang WHERE tgl_piutang BETWEEN ? AND ? AND catatan = 'prolanis'");
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                DataProlanis b = new DataProlanis();
                b.setNoNota(rs.getString("nota_piutang"));
                b.setNoRekamMedis(rs.getString("no_rkm_medis"));
                b.setNamaPasien(rs.getString("nm_pasien"));
                b.setTglPiutang(rs.getString("tgl_piutang"));
                b.setJatuhTempo(rs.getString("tgltempo"));
                b.setLokasi(rs.getString("kd_bangsal"));
                b.setProlanisDetails(getListDetailProlanis(rs.getString("nota_piutang")));
                proList.add(b);
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
                Logger.getLogger(DataProlanisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return proList;
    }

    public static List<ProlanisDetail> getListDetailProlanis(String noNota) {
        List<ProlanisDetail> details = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("SELECT detailpiutang.`kode_brng`,databarang.`nama_brng`,detailpiutang.`jumlah`,detailpiutang.`h_jual`,detailpiutang.kode_sat,"
                    + "detailpiutang.`bsr_dis`,detailpiutang.`subtotal`,detailpiutang.`total`,detailpiutang.`no_batch`,detailpiutang.`no_faktur` FROM detailpiutang JOIN databarang ON detailpiutang.kode_brng = databarang.kode_brng WHERE detailpiutang.nota_piutang = ?");
            prep.setString(1, noNota);
            res = prep.executeQuery();
            while (res.next()) {
                ProlanisDetail b = new ProlanisDetail();
                b.setKodeObat(res.getString("kode_brng"));
                b.setNamaObat(res.getString("nama_brng"));
                b.setJumlah(res.getString("jumlah"));
                b.setHarga(res.getString("h_jual"));
                b.setSatuan(res.getString("kode_sat"));
                b.setDiskon(res.getString("bsr_dis"));
                b.setTotal(res.getString("subtotal"));
                details.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataProlanisDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return details;
    }

  
}

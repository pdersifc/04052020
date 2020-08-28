/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.MarginBpjs;
import com.herinoid.rsi.model.MarginObatNonBpjs;
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
public class MarginDao {

    private static Connection koneksi = koneksiDB.condb();
    

    public static MarginBpjs getMarginBpjs(String kodeBarang) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MarginBpjs margin = null;
        try {
            ps = koneksi.prepareStatement("SELECT * FROM setpenjualanperbarang where kode_brng = ?");
            ps.setString(1, kodeBarang);
            rs = ps.executeQuery();
            while (rs.next()) {
                margin = new MarginBpjs();
                margin.setKodeBarang(rs.getString("kode_brng"));
                margin.setRalan(rs.getDouble("ralan"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(MarginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MarginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return margin;
    }
    
    public static MarginObatNonBpjs getMarginNonBpjs(String kodeJaminan) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        MarginObatNonBpjs margin = null;
        try {
            ps = koneksi.prepareStatement("SELECT * FROM set_harga_obat_ralan where kd_pj = ?");
            ps.setString(1, kodeJaminan);
            rs = ps.executeQuery();
            while (rs.next()) {
                margin = new MarginObatNonBpjs();
                margin.setKodeJaminan(rs.getString("kd_pj"));
                margin.setMargin(rs.getDouble("hargajual"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(MarginDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(MarginDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return margin;
    }
    
}

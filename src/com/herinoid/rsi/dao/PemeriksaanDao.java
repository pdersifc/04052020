/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.PemeriksaanRalan;
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
public class PemeriksaanDao {

    private static Connection koneksi = koneksiDB.condb();

    public static PemeriksaanRalan getPemeriksaanRalanByNoRawat(String norawat) {
        PemeriksaanRalan periksa = new PemeriksaanRalan();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = koneksi.prepareStatement("SELECT * FROM pemeriksaan_ralan WHERE no_rawat = ?");
            ps.setString(1, norawat);
            rs = ps.executeQuery();
            while (rs.next()) {

                periksa.setAlergi(rs.getString("alergi"));
                periksa.setBeratBadan(rs.getString("berat"));
                periksa.setTinggiBadan(rs.getString("tinggi"));
                periksa.setSuhuTubuh(rs.getString("suhu_tubuh"));
                periksa.setTekanDarah(rs.getString("tensi"));
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
                Logger.getLogger(PemeriksaanDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return periksa;
    }
    
    public static PemeriksaanRalan getPemeriksaanRanapByNoRawat(String norawat) {
        PemeriksaanRalan periksa = new PemeriksaanRalan();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = koneksi.prepareStatement("SELECT * FROM pemeriksaan_ranap WHERE no_rawat = ?");
            ps.setString(1, norawat);
            rs = ps.executeQuery();
            while (rs.next()) {

                periksa.setAlergi(rs.getString("alergi"));
                periksa.setBeratBadan(rs.getString("berat"));
                periksa.setTinggiBadan(rs.getString("tinggi"));
                periksa.setSuhuTubuh(rs.getString("suhu_tubuh"));
                periksa.setTekanDarah(rs.getString("tensi"));
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
                Logger.getLogger(PemeriksaanDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return periksa;
    }

}

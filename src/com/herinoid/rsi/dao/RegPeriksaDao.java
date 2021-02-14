/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.RegPeriksa;
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
public class RegPeriksaDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static RegPeriksa get(String norawat) {
        RegPeriksa regPeriksa =  null;
        try {
            ps = koneksi.prepareStatement("SELECT no_rawat,tgl_registrasi,jam_reg,kd_pj,no_rkm_medis,status_bayar,kd_poli from reg_periksa where no_rawat = ?");
            ps.setString(1, norawat);
            rs = ps.executeQuery();
            while (rs.next()) {
                regPeriksa = new RegPeriksa();
                regPeriksa.setNoRawat(rs.getString("no_rawat"));
                regPeriksa.setTanggalRawat(rs.getDate("tgl_registrasi"));
                regPeriksa.setJamRawat(rs.getString("jam_reg"));
                regPeriksa.setKdPj(rs.getString("kd_pj"));
                regPeriksa.setNorm(rs.getString("no_rkm_medis"));
                regPeriksa.setStatusBayar(rs.getString("status_bayar"));
                regPeriksa.setKdPoli(rs.getString("kd_poli"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegPeriksaDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegPeriksaDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return regPeriksa;
    }
    
    public static boolean isClosed(String norawat) {
        boolean closed =  false;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        try {
            ps1 = koneksi.prepareStatement("SELECT * from reg_periksa where no_rawat = ? and status_bayar ='Sudah Bayar'");
            ps1.setString(1, norawat);
            rs1 = ps1.executeQuery();
            while (rs1.next()) {
                closed = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegPeriksaDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (rs1 != null) {

                    rs1.close();

                }
                if (ps1 != null) {
                    ps1.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegPeriksaDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return closed;
    }
  
}

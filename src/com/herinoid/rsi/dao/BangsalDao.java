/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
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
public class BangsalDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static List<Bangsal> getAllBangsal() {
        List<Bangsal> bangsals = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT bangsal.`kd_bangsal`,bangsal.`nm_bangsal` FROM bangsal JOIN kamar ON kamar.`kd_bangsal`=bangsal.`kd_bangsal` WHERE bangsal.kd_bangsal <> 'BOX' GROUP BY bangsal.`kd_bangsal`");
            rs = ps.executeQuery();
            while (rs.next()) {
                Bangsal b = new Bangsal();
                b.setKode(rs.getString("kd_bangsal"));
                b.setNama(rs.getString("nm_bangsal"));
                bangsals.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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
        return bangsals;
    }
    
    public static List<Bangsal> getAllKelasInBangsal() {
        List<Bangsal> bangsals = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT bangsal.`kd_bangsal`,bangsal.`nm_bangsal`,kamar.`kelas` FROM bangsal JOIN kamar ON kamar.`kd_bangsal`=bangsal.`kd_bangsal` WHERE bangsal.kd_bangsal <> 'BOX' GROUP BY bangsal.`kd_bangsal`, kamar.`kelas`");
            rs = ps.executeQuery();
            while (rs.next()) {
                Bangsal b = new Bangsal();
                b.setKode(rs.getString("kd_bangsal"));
                b.setNama(rs.getString("nm_bangsal"));
                b.setKelas(rs.getString("kelas"));
                bangsals.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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
        return bangsals;
    }

    public static Bangsal get(String kode) {
        Bangsal bangsal = null;
        try {
            ps = koneksi.prepareStatement("SELECT * FROM bangsal where kd_bangsal = ?");
            ps.setString(1, kode);
            rs = ps.executeQuery();
            while (rs.next()) {
                bangsal = new Bangsal();
                bangsal.setKode(rs.getString("kd_bangsal"));
                bangsal.setNama(rs.getString("nm_bangsal"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(BangsalDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return bangsal;
    }
    
     public static List<Bangsal> getDepoObat() {
        List<Bangsal> bangsals = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT * FROM bangsal WHERE kd_bangsal IN ('DEPO1','DEPO2','DEPO3','GDFAR','KMOBT') AND STATUS = '1'");
            rs = ps.executeQuery();
            while (rs.next()) {
                Bangsal b = new Bangsal();
                b.setKode(rs.getString("kd_bangsal"));
                b.setNama(rs.getString("nm_bangsal"));
                bangsals.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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
        return bangsals;
    }
}

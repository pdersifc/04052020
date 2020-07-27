/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.DokterPoli;
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
public class DokterPoliDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static List<DokterPoli> getAllDokterPoli() {
        List<DokterPoli> dokterPolis = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT dp.`kd_dokter`,d.`nm_dokter`,p.`kd_poli`,p.`nm_poli`,dp.`ruang` FROM dokter_poli dp LEFT JOIN poliklinik p ON dp.`kd_poli` = p.`kd_poli` LEFT JOIN dokter d ON dp.`kd_dokter`=d.`kd_dokter` GROUP BY dp.`kd_dokter` ORDER BY dp.`ruang` ");
            rs = ps.executeQuery();
            while (rs.next()) {
                DokterPoli b = new DokterPoli();
                b.setKodeDokter(rs.getString("kd_dokter"));
                b.setNamaDokter(rs.getString("nm_dokter"));
                b.setKodePoli(rs.getString("kd_poli"));
                b.setNamaPoli(rs.getString("nm_poli"));
                b.setRuang(rs.getString("ruang"));
                dokterPolis.add(b);
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
                Logger.getLogger(DokterPoliDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dokterPolis;
    }
    
    public static boolean save(String kdDokter,String kdPoli,String ruang){
        boolean sukses = true;
        try {
                ps = koneksi.prepareStatement("insert into dokter_poli(kd_dokter,kd_poli,ruang) values(?,?,?)");
                try {
                    ps.setString(1, kdDokter);
                    ps.setString(2, kdPoli);
                    ps.setString(3, ruang);
                    ps.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (ps != null) {
                        ps.close();
                    }
                }
            } catch (Exception e) {
                sukses = false;
                System.out.println("Notifikasi : " + e);
            }
        return sukses;
    }
    
    public static boolean update(String kdDokter,String kdPoli,String ruang){
        boolean sukses = true;
        try {
                ps = koneksi.prepareStatement("update dokter_poli set ruang=? where kd_dokter=? and kd_poli=?");
                try {
                    ps.setString(1, ruang);
                    ps.setString(2, kdDokter);
                    ps.setString(3, kdPoli);
                    ps.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (ps != null) {
                        ps.close();
                    }
                }
            } catch (Exception e) {
                sukses = false;
                System.out.println("Notifikasi : " + e);
            }
        return sukses;
    }
    
    public static boolean isExist(String kdDokter,String kdPoli,String ruang) {
        boolean ada = false;
        try {
            ps = koneksi.prepareStatement("SELECT * FROM dokter_poli where kd_dokter=? and kd_poli =?");
            ps.setString(1, kdDokter);
            ps.setString(2, kdPoli);
            rs = ps.executeQuery();
            while (rs.next()) {
                ada = true;
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
                Logger.getLogger(DokterPoliDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ada;
    }
    
    public static List<DokterPoli> findByDokterName(String cari) {
        List<DokterPoli> dokterPolis = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT dp.`kd_dokter`,d.`nm_dokter`,p.`kd_poli`,p.`nm_poli`,dp.`ruang` FROM dokter_poli dp LEFT JOIN poliklinik p ON dp.`kd_poli` = p.`kd_poli` LEFT JOIN dokter d ON dp.`kd_dokter`=d.`kd_dokter` where d.`nm_dokter` like ? GROUP BY dp.`kd_dokter` ORDER BY dp.`ruang` ");
            ps.setString(1, "%"+cari+"%");
            rs = ps.executeQuery();
            while (rs.next()) {
                DokterPoli b = new DokterPoli();
                b.setKodeDokter(rs.getString("kd_dokter"));
                b.setNamaDokter(rs.getString("nm_dokter"));
                b.setKodePoli(rs.getString("kd_poli"));
                b.setNamaPoli(rs.getString("nm_poli"));
                b.setRuang(rs.getString("ruang"));
                dokterPolis.add(b);
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
                Logger.getLogger(DokterPoliDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dokterPolis;
    }
}

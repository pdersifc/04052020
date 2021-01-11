/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.Pasien;
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
public class PasienDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    

    public static Pasien get(String norm) {
        Pasien pasien = null;
        try {
            ps = koneksi.prepareStatement("SELECT * FROM pasien where no_rkm_medis = ?");
            ps.setString(1, norm);
            rs = ps.executeQuery();
            while (rs.next()) {
                pasien = new Pasien(rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("alamat"),rs.getString("no_tlp"),rs.getString("nm_ibu"),rs.getString("tgl_lahir"),rs.getString("no_ktp"));               
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(PasienDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PasienDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pasien;
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
                Logger.getLogger(PasienDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bangsals;
    }
}

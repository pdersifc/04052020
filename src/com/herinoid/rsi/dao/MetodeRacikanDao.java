/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.MetodeRacikan;
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hewrei
 */
public class MetodeRacikanDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static List<MetodeRacikan> getAllMetode() {
        List<MetodeRacikan> bangsals = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT * from metode_racik ");
            rs = ps.executeQuery();
            while (rs.next()) {
                MetodeRacikan b = new MetodeRacikan();
                b.setKode(rs.getString("kd_racik"));
                b.setMetode(rs.getString("nm_racik"));
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
                Logger.getLogger(MetodeRacikanDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bangsals;
    }
    
  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.DisplayFarmasi;
import com.herinoid.rsi.model.Poliklinik;
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
public class PoliklinikDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    
    
    public static List<Poliklinik> getAllPoliklinik() {
        List<Poliklinik> polis = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT * FROM poliklinik WHERE status='1'");
            rs = ps.executeQuery();
            while (rs.next()) {
                Poliklinik b = new Poliklinik();
                b.setKdPoli(rs.getString("kd_poli"));
                b.setNmPoli(rs.getString("nm_poli"));
                polis.add(b);
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
                Logger.getLogger(PoliklinikDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return polis;
    }

   
}

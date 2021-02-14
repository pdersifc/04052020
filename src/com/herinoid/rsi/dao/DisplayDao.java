/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.DisplayFarmasi;
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
public class DisplayDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    
    
    public static List<DisplayFarmasi> getAllDisplayFarmasi() {
        List<DisplayFarmasi> displays = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT * FROM set_display WHERE unit='FARMASI' and is_active=1");
            rs = ps.executeQuery();
            while (rs.next()) {
                DisplayFarmasi b = new DisplayFarmasi();
                b.setId(rs.getInt("id"));
                b.setIp(rs.getString("ip_display"));
                b.setUnit(rs.getString("unit"));
                b.setActive(rs.getBoolean("is_active"));
                displays.add(b);
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
                Logger.getLogger(DisplayDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return displays;
    }

   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.GambarRadiologi;
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
public class GambarRadiologiDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static List<GambarRadiologi> getAllGambarRadiologi(String norawat) {
        List<GambarRadiologi> gambarRadiologis = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT * FROM gambar_radiologi where no_rawat = ?");
            ps.setString(1,norawat );
            rs = ps.executeQuery();
            while (rs.next()) {
                GambarRadiologi b = new GambarRadiologi();
                b.setNoRawat(rs.getString("no_rawat"));
                b.setTanggal(rs.getString("tgl_periksa"));
                b.setJam(rs.getString("jam"));
                b.setLokasiGambar(rs.getString("lokasi_gambar"));
                gambarRadiologis.add(b);
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
                Logger.getLogger(GambarRadiologiDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return gambarRadiologis;
    }
}

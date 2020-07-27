/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.ObatBhp;
import com.herinoid.rsi.model.TarifObat;
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
public class ObatBhpDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static TarifObat getTarifObatBhp(String kdObat) {
        TarifObat obat = new TarifObat();
        try {
            ps = koneksi.prepareStatement("SELECT * FROM databarang WHERE kode_brng= ?");
            ps.setString(1, kdObat);
            rs = ps.executeQuery();
            while (rs.next()) {
                obat.setKodeObat(rs.getString("kode_brng"));
                obat.setNamaObat(rs.getString("nama_brng"));
                obat.setKelas1(rs.getDouble("kelas1"));
                obat.setKelas2(rs.getDouble("kelas2"));
                obat.setKelas3(rs.getDouble("kelas3"));
                obat.setKelasUtama(rs.getDouble("utama"));
                obat.setKelasVip(rs.getDouble("vip"));
                obat.setKelasVVIP(rs.getDouble("vvip"));
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
                Logger.getLogger(ObatBhpDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obat;
    }
}

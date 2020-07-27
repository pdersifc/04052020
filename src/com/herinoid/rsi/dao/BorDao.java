/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import fungsi.koneksiDB;
import fungsi.validasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Herinoid@gmail.com
 */
public class BorDao {

    private static Connection koneksi = koneksiDB.condb();
    private static validasi Valid = new validasi();
    private static PreparedStatement ps;
    private static ResultSet rs;

    //================================bor los toi bto per bangsal==========================//
    public static double getBor(Date date1, Date date2,String kdBangsal) {
        double bor = 0;
        int bed = getJumlahBed(kdBangsal);
        double lamaDirawat = getJumlahLamaDirawat(date1, date2,kdBangsal);
        int jumlahHari = getSelisihHari(date1, date2);
        if(bed < 1)bed = 1;
        if(lamaDirawat < 1)lamaDirawat = 1;
        if(jumlahHari < 1)jumlahHari = 1; 
        
        bor = (lamaDirawat / (bed * jumlahHari)) * 100;
        return bor;
    }
    
    public static double getAvlos(Date fromDate,Date toDate,String kdBangsal){
        int pasienKeluar = getJumlahPasienKeluar(fromDate,toDate,kdBangsal);
        double lamaDirawat = getJumlahLamaDirawat(fromDate,toDate,kdBangsal);
        if(pasienKeluar < 1)pasienKeluar = 1;            
        if(lamaDirawat < 1)lamaDirawat = 1;        
        return  lamaDirawat / pasienKeluar;
    }
    
    public static double getToi(Date fromDate,Date toDate,String kdBangsal){
        int bed = getJumlahBed(kdBangsal);
        int jmlPeriode = getSelisihHari(fromDate, toDate);
        double jmlPerawatan = getJumlahLamaDirawat(fromDate, toDate, kdBangsal);
        int pasienKeluar = getJumlahPasienKeluar(fromDate, toDate, kdBangsal);
        if(pasienKeluar < 1)pasienKeluar=1;
        if(bed < 1)bed = 1;
        if(jmlPerawatan < 1 )jmlPerawatan=1;
        return ((bed * jmlPeriode) - jmlPerawatan) / pasienKeluar;
    }
    
    public static double getBto(Date fromDate,Date toDate,String kdBangsal){
        double bed = getJumlahBed(kdBangsal);
        double pasienKeluar = getJumlahPasienKeluar(fromDate, toDate, kdBangsal);
        return pasienKeluar / bed;
    }
   //=================================bor los toi bto per kelas =======================================    
   
    public static double getBorPerKelas(Date date1, Date date2,String kdBangsal,String kelas) {
        double bor = 0;
        int bed = getJumlahBedPerKelas(kdBangsal,kelas);
        double lamaDirawat = getJumlahLamaDirawatPerKelas(date1, date2,kdBangsal,kelas);
        int jumlahHari = getSelisihHari(date1, date2);
        if(bed < 1)bed = 1;
        if(lamaDirawat < 1)lamaDirawat = 1;
        if(jumlahHari < 1)jumlahHari = 1; 
        
       
        double bor1 = bed * jumlahHari;
        double bor2 = lamaDirawat / bor1;
        bor = bor2 * 100;
        return bor;
    }
    
    public static double getAvlosPerKelas(Date fromDate,Date toDate,String kdBangsal,String kelas){
        int pasienKeluar = getJumlahPasienKeluarPerKelas(fromDate,toDate,kdBangsal,kelas);
        double lamaDirawat = getJumlahLamaDirawatPerKelas(fromDate,toDate,kdBangsal,kelas);
        if(pasienKeluar < 1)pasienKeluar = 1;
        return  lamaDirawat / pasienKeluar;
    }
    
    public static double getToiPerKelas(Date fromDate,Date toDate,String kdBangsal,String kelas){
        int bed = getJumlahBedPerKelas(kdBangsal,kelas);
        int jmlPeriode = getSelisihHari(fromDate, toDate);
        double jmlPerawatan = getJumlahLamaDirawatPerKelas(fromDate, toDate, kdBangsal,kelas);
        int pasienKeluar = getJumlahPasienKeluarPerKelas(fromDate, toDate, kdBangsal,kelas);
        if(pasienKeluar < 1)pasienKeluar=1;
        double toi1 =bed * jmlPeriode;
        double toi2 = toi1 - jmlPerawatan;
        double toi = toi2 / pasienKeluar;
//        ((bed * jmlPeriode) - jmlPerawatan) / pasienKeluar;
        return toi;
    }
    
    public static double getBtoPerKelas(Date fromDate,Date toDate,String kdBangsal,String kelas){
        double bed = getJumlahBedPerKelas(kdBangsal,kelas);
        double pasienKeluar = getJumlahPasienKeluarPerKelas(fromDate, toDate, kdBangsal,kelas);
        if(pasienKeluar < 1)pasienKeluar = 1;
        return pasienKeluar / bed;
    }
    
  //================================== bor los toi bto all bangsal ====================================  
    
    public static double getAllBor(Date fromDate, Date toDate) {
        double bor = 0;
        int bed = getJumlahBedAll();
        double lamaDirawat = getJumlahLamaDirawatAllBangsal(fromDate, toDate);
        int jumlahHari = getSelisihHari(fromDate, toDate);
        if(bed < 1)bed = 1;
        if(lamaDirawat < 1)lamaDirawat = 1;
        if(jumlahHari < 1)jumlahHari = 1; 
        
        bor = (lamaDirawat / (bed * jumlahHari)) * 100;
        return bor;
    }
    
    public static double getAvlosAll(Date fromDate,Date toDate){
        int pasienKeluar = getJumlahPasienKeluarAllBangsal(fromDate,toDate);
        double lamaDirawat = getJumlahLamaDirawatAllBangsal(fromDate,toDate);
        if(pasienKeluar < 1)pasienKeluar = 1;            
        if(lamaDirawat < 1)lamaDirawat = 1;        
        return  lamaDirawat / pasienKeluar;
    }
    
    public static double getToiAll(Date fromDate,Date toDate){
        int bed = getJumlahBedAll();
        int jmlPeriode = getSelisihHari(fromDate, toDate);
        double jmlPerawatan = getJumlahLamaDirawatAllBangsal(fromDate, toDate);
        int pasienKeluar = getJumlahPasienKeluarAllBangsal(fromDate, toDate);
        return ((bed * jmlPeriode) - jmlPerawatan) / pasienKeluar;
    }
    
    public static double getBtoAll(Date fromDate,Date toDate){
        double bed = getJumlahBedAll();
        double pasienKeluar = getJumlahPasienKeluarAllBangsal(fromDate, toDate);
        return pasienKeluar / bed;
    }
  //===================================================================================================
    
    private static int getJumlahBed(String kdbangsal) {
        int bed = 0;
        try {
            ps = koneksi.prepareStatement("SELECT COUNT(*) FROM kamar WHERE kd_bangsal = ? and kd_bangsal <> 'BOX'");
            ps.setString(1, kdbangsal);
            rs = ps.executeQuery();
            while (rs.next()) {
                bed = rs.getInt(1);
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
        return bed;
    }
    
    private static int getJumlahBedPerKelas(String kdbangsal,String kelas) {
        int bed = 0;
        try {
            ps = koneksi.prepareStatement("SELECT COUNT(*) FROM kamar WHERE kd_bangsal = ? and kelas = ? and kd_bangsal <> 'BOX'");
            ps.setString(1, kdbangsal);
            ps.setString(2, kelas);
            rs = ps.executeQuery();
            while (rs.next()) {
                bed = rs.getInt(1);
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
        return bed;
    }
    
    private static int getJumlahBedAll() {
        int bed = 0;
        try {
            ps = koneksi.prepareStatement("SELECT COUNT(*) FROM kamar WHERE kd_bangsal <> 'BOX'");
            rs = ps.executeQuery();
            while (rs.next()) {
                bed = rs.getInt(1);
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
        return bed;
    }

    public static int getSelisihHari(Date date1, Date date2) {
        try {
            long miliDetik = Math.abs(date1.getTime() - date2.getTime());
            long selisih = TimeUnit.MILLISECONDS.toDays(miliDetik);
            return Long.valueOf(selisih).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    

    private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    
    private static double getJumlahLamaDirawat(Date fromDate, Date toDate,String kdBangsal) {
        int lama = 0;
        try {
            String query = "SELECT SUM(kamar_inap.lama)\n"
                    + "FROM kamar_inap INNER JOIN reg_periksa INNER JOIN pasien INNER JOIN kamar INNER JOIN bangsal\n"
                    + "ON kamar_inap.no_rawat=reg_periksa.no_rawat AND reg_periksa.no_rkm_medis=pasien.no_rkm_medis\n"
                    + "AND kamar_inap.kd_kamar=kamar.kd_kamar AND kamar.kd_bangsal=bangsal.kd_bangsal \n"
                    + "WHERE kamar_inap.tgl_masuk BETWEEN ? AND ? AND bangsal.kd_bangsal = ?";
            ps = koneksi.prepareStatement(query);
            ps.setDate(1, convertUtilToSql(fromDate));
            ps.setDate(2, convertUtilToSql(toDate));
            ps.setString(3,kdBangsal);
            rs = ps.executeQuery();
            while (rs.next()) {
                lama = rs.getInt(1);
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
        return lama;
    }
    
     private static double getJumlahLamaDirawatPerKelas(Date fromDate, Date toDate,String kdBangsal,String kelas) {
        int lama = 0;
        try {
            String query = "SELECT SUM(kamar_inap.lama)\n"
                    + "FROM kamar_inap INNER JOIN reg_periksa INNER JOIN pasien INNER JOIN kamar INNER JOIN bangsal\n"
                    + "ON kamar_inap.no_rawat=reg_periksa.no_rawat AND reg_periksa.no_rkm_medis=pasien.no_rkm_medis\n"
                    + "AND kamar_inap.kd_kamar=kamar.kd_kamar AND kamar.kd_bangsal=bangsal.kd_bangsal \n"
                    + "WHERE kamar_inap.tgl_masuk BETWEEN ? AND ? AND bangsal.kd_bangsal = ? and kamar.kelas = ?";
            ps = koneksi.prepareStatement(query);
            ps.setDate(1, convertUtilToSql(fromDate));
            ps.setDate(2, convertUtilToSql(toDate));
            ps.setString(3,kdBangsal);
            ps.setString(4, kelas);
            rs = ps.executeQuery();
            while (rs.next()) {
                lama = rs.getInt(1);
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
        return lama;
    }
    
    private static double getJumlahLamaDirawatAllBangsal(Date fromDate, Date toDate) {
        int lama = 0;
        try {
            String query = "SELECT SUM(kamar_inap.lama)\n"
                    + "FROM kamar_inap INNER JOIN reg_periksa INNER JOIN pasien INNER JOIN kamar INNER JOIN bangsal\n"
                    + "ON kamar_inap.no_rawat=reg_periksa.no_rawat AND reg_periksa.no_rkm_medis=pasien.no_rkm_medis\n"
                    + "AND kamar_inap.kd_kamar=kamar.kd_kamar AND kamar.kd_bangsal=bangsal.kd_bangsal \n"
                    + "WHERE kamar_inap.tgl_masuk BETWEEN ? AND ? ";
            ps = koneksi.prepareStatement(query);
            ps.setDate(1, convertUtilToSql(fromDate));
            ps.setDate(2, convertUtilToSql(toDate));
            rs = ps.executeQuery();
            while (rs.next()) {
                lama = rs.getInt(1);
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
        return lama;
    }

    private static int getJumlahPasienKeluar(Date fromDate, Date toDate,String kdBangsal) {
        int lama = 0;
        try {
            String query = "SELECT COUNT(*) FROM kamar_inap INNER JOIN reg_periksa INNER JOIN pasien INNER JOIN kamar INNER JOIN bangsal\n"
                    + "ON kamar_inap.no_rawat=reg_periksa.no_rawat AND reg_periksa.no_rkm_medis=pasien.no_rkm_medis\n"
                    + "AND kamar_inap.kd_kamar=kamar.kd_kamar AND kamar.kd_bangsal=bangsal.kd_bangsal \n"
                    + "WHERE kamar_inap.tgl_masuk BETWEEN ? AND ? AND bangsal.kd_bangsal = ? AND tgl_keluar <> '0000-00-00' AND kamar_inap.stts_pulang <> '-' AND kamar_inap.stts_pulang <> 'Pindah Kamar'";
            ps = koneksi.prepareStatement(query);
            ps.setDate(1, convertUtilToSql(fromDate));
            ps.setDate(2, convertUtilToSql(toDate));
            ps.setString(3,kdBangsal);
            rs = ps.executeQuery();
            while (rs.next()) {
                lama = rs.getInt(1);
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
        return lama;
    }
    
    private static int getJumlahPasienKeluarPerKelas(Date fromDate, Date toDate,String kdBangsal,String kelas) {
        int lama = 0;
        try {
            String query = "SELECT COUNT(*) FROM kamar_inap INNER JOIN reg_periksa INNER JOIN pasien INNER JOIN kamar INNER JOIN bangsal\n"
                    + "ON kamar_inap.no_rawat=reg_periksa.no_rawat AND reg_periksa.no_rkm_medis=pasien.no_rkm_medis\n"
                    + "AND kamar_inap.kd_kamar=kamar.kd_kamar AND kamar.kd_bangsal=bangsal.kd_bangsal \n"
                    + "WHERE kamar_inap.tgl_masuk BETWEEN ? AND ? AND bangsal.kd_bangsal = ? AND kamar.kelas = ? AND tgl_keluar <> '0000-00-00' AND kamar_inap.stts_pulang <> '-' AND kamar_inap.stts_pulang <> 'Pindah Kamar'";
            ps = koneksi.prepareStatement(query);
            ps.setDate(1, convertUtilToSql(fromDate));
            ps.setDate(2, convertUtilToSql(toDate));
            ps.setString(3,kdBangsal);
            ps.setString(4,kdBangsal);
            rs = ps.executeQuery();
            while (rs.next()) {
                lama = rs.getInt(1);
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
        return lama;
    }
    
    private static int getJumlahPasienKeluarAllBangsal(Date fromDate, Date toDate) {
        int lama = 0;
        try {
            String query = "SELECT COUNT(*) FROM kamar_inap INNER JOIN reg_periksa INNER JOIN pasien INNER JOIN kamar INNER JOIN bangsal\n"
                    + "ON kamar_inap.no_rawat=reg_periksa.no_rawat AND reg_periksa.no_rkm_medis=pasien.no_rkm_medis\n"
                    + "AND kamar_inap.kd_kamar=kamar.kd_kamar AND kamar.kd_bangsal=bangsal.kd_bangsal \n"
                    + "WHERE kamar_inap.tgl_masuk BETWEEN ? AND ? AND tgl_keluar <> '0000-00-00' AND kamar_inap.stts_pulang <> '-' AND kamar_inap.stts_pulang <> 'Pindah Kamar'";
            ps = koneksi.prepareStatement(query);
            ps.setDate(1, convertUtilToSql(fromDate));
            ps.setDate(2, convertUtilToSql(toDate));
            rs = ps.executeQuery();
            while (rs.next()) {
                lama = rs.getInt(1);
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
        return lama;
    }   
    
    
}

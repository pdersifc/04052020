/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.DataEResep;
import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ObatResep;
import com.herinoid.rsi.model.RegPeriksa;
import com.herinoid.rsi.model.Resep;
import com.herinoid.rsi.util.Konstan;
import com.herinoid.rsi.util.NumberUtils;
import static com.herinoid.rsi.util.NumberUtils.DATE_FORMAT_NUMBERING;
import com.herinoid.rsi.util.Utils;
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
import javax.swing.JOptionPane;

/**
 *
 * @author Hewrei
 */
public class ResepDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

    public static boolean updateNoResep(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("update no_e_resep set no_resep = ?");
            try {
                pst.setString(1, noresep);
                pst.executeUpdate();
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (Exception e) {
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean deleteDetailRacikanByNoResep(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("delete from e_resep_racikan_rsifc_detail where no_resep = ?");
            try {
                pst.setString(1, noresep);
                pst.execute();
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (Exception e) {
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean saveRacikanDetail(String norawat, String noResep, List<ObatResep> reseps) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            for (ObatResep o : reseps) {
                if (o.isParent()) {
                    saveObatRacikan(norawat, o);
                } else {
                    psttmn = koneksi.prepareStatement("insert into e_resep_racikan_rsifc_detail(no_resep,kode_racik,nama_racik,is_parent,kandungan,kode_brng,jml,embalase,tuslah,aturan_pakai) values(?,?,?,?,?,?,?,?,?,?)");
                    try {
                        psttmn.setString(1, noResep);
                        psttmn.setString(2, o.getKodeRacikan());
                        psttmn.setString(3, o.getRacikan());
                        psttmn.setBoolean(4, o.isParent());
                        psttmn.setString(5, o.getKandungan());
                        psttmn.setString(6, o.getKodeObat());
                        psttmn.setInt(7, o.getJumlah());
                        psttmn.setDouble(8, o.getEmbalase());
                        psttmn.setDouble(9, o.getTuslah());
                        psttmn.setString(10, o.getAturanPakai());
                        psttmn.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (psttmn != null) {
                            psttmn.close();
                        }
                    }
                }
            }

        } catch (Exception e) {
             e.printStackTrace();
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean deleteDetailByNoResep(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("delete from e_resep_rsifc_detail where no_resep = ?");
            try {
                pst.setString(1, noresep);
                pst.execute();
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (Exception e) {
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean saveDetail(String noResep, List<ObatResep> reseps) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            for (ObatResep o : reseps) {
                psttmn = koneksi.prepareStatement("insert into e_resep_rsifc_detail(no_resep,kode_brng,jml,embalase,tuslah,aturan_pakai) values(?,?,?,?,?,?)");
                try {
                    psttmn.setString(1, noResep);
                    psttmn.setString(2, o.getKodeObat());
                    psttmn.setInt(3, o.getJumlah());
                    psttmn.setDouble(4, o.getEmbalase());
                    psttmn.setDouble(5, o.getTuslah());
                    psttmn.setString(6, o.getAturanPakai());
                    psttmn.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (psttmn != null) {
                        psttmn.close();
                    }
                }
            }

        } catch (Exception e) {
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean save(Resep resep) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
//            String noresep = getNoResepForUpdate();
            psttmn = koneksi.prepareStatement("insert into e_resep_rsifc(no_resep,tgl_resep,jam_resep,no_rawat,kd_dokter_peresep,status) values(?,?,?,?,?,?)");
            try {
                psttmn.setString(1, resep.getNoResep());
                psttmn.setString(2, Utils.formatDb(resep.getTglResep()));
                psttmn.setString(3, resep.getJamResep());
                psttmn.setString(4, resep.getNoRawat());
                psttmn.setString(5, resep.getKdDokter());
                psttmn.setString(6, resep.getStatus());
                psttmn.executeUpdate();
                saveDetail(resep.getNoResep(), resep.getObatResepDetail());
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (psttmn != null) {
                    psttmn.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            sukses = false;
            JOptionPane.showMessageDialog(null, "error : " + e);
        }
        return sukses;
    }

    public static boolean saveRacikan(Resep resep) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
//            String noresep = getNoResepForUpdate();
            psttmn = koneksi.prepareStatement("insert into e_resep_racikan_rsifc(no_resep,tgl_resep,jam_resep,no_rawat,kd_dokter_peresep,status) values(?,?,?,?,?,?)");
            try {
                psttmn.setString(1, resep.getNoResep());
                psttmn.setString(2, Utils.formatDb(resep.getTglResep()));
                psttmn.setString(3, resep.getJamResep());
                psttmn.setString(4, resep.getNoRawat());
                psttmn.setString(5, resep.getKdDokter());
                psttmn.setString(6, resep.getStatus());
                psttmn.executeUpdate();
                saveRacikanDetail(resep.getNoRawat(), resep.getNoResep(), resep.getObatResepRacikanDetail());
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (psttmn != null) {
                    psttmn.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            sukses = false;
            JOptionPane.showMessageDialog(null, "error : " + e);
        }
        return sukses;
    }

    public static boolean saveObatRacikan(String norawat, ObatResep obat) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            RegPeriksa reg = RegPeriksaDao.get(norawat);
            psttmn = koneksi.prepareStatement("insert into obat_racikan_eresep_rsifc(tgl_perawatan,jam,no_rawat,no_racik,nama_racik,kd_racik,jml_dr,aturan_pakai,keterangan) values(?,?,?,?,?,?,?,?,?)");
            try {
                psttmn.setString(1, Utils.formatDb(reg.getTanggalRawat()));
                psttmn.setString(2, reg.getJamRawat());
                psttmn.setString(3, norawat);
                psttmn.setString(4, String.valueOf(obat.getNomorRacik()));
                psttmn.setString(5, obat.getNamaObat());
                psttmn.setString(6, obat.getMetodeRacikKode());
                psttmn.setInt(7, obat.getJumlah());
                psttmn.setString(8, obat.getAturanPakai());
                psttmn.setString(9, obat.getKandungan());
                psttmn.executeUpdate();
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (psttmn != null) {
                    psttmn.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
            sukses = false;
            JOptionPane.showMessageDialog(null, "error : " + e);
        }
        return sukses;
    }

    public static String getNoResepForUpdate() {
        String noresep = null;
        PreparedStatement psttmn = null;
        ResultSet rset = null;
        try {
            psttmn = koneksi.prepareStatement("select * from no_e_resep for update");
            rset = psttmn.executeQuery();
            while (rset.next()) {
                noresep = rset.getString("no_resep");
            }
            updateNoResep(NumberUtils.getNoResep(noresep));
            String oldDate = noresep.substring(6, 14);
            Date tglnoresep = Utils.convertToDate(oldDate, DATE_FORMAT_NUMBERING);
            if (tglnoresep.before(Utils.getDateOnly(new Date()))) {
                String noresepnew = getNoResepForUpdateDiferentDate();
                updateNoResep(NumberUtils.getNoResep(noresepnew));
                noresep = noresepnew;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rset != null) {

                    rset.close();

                }
                if (psttmn != null) {
                    psttmn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return noresep;
    }

    public static String getNoResepForUpdateDiferentDate() {
        String noresep = null;
        PreparedStatement psttmn = null;
        ResultSet rset = null;
        try {
            psttmn = koneksi.prepareStatement("select * from no_e_resep for update");
            rset = psttmn.executeQuery();
            while (rset.next()) {
                noresep = rset.getString("no_resep");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rset != null) {

                    rset.close();

                }
                if (psttmn != null) {
                    psttmn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return noresep;
    }

    public static List<DataEResep> getResepByDateAndDepo(String fromDate, String toDate, String depo, String tarif) {
        List<DataEResep> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT r.`no_rawat`,e.`no_resep`,e.`tgl_resep`,e.`jam_resep`,p.`nm_poli`,j.`png_jawab`,d.`nm_dokter`,s.`no_rkm_medis`,s.`nm_pasien`,e.`validasi`,e.`sampai_pasien`,"
                    + "e.`status` FROM e_resep_rsifc e "
                    + "INNER JOIN reg_periksa r ON r.`no_rawat`=e.`no_rawat` "
                    + "INNER JOIN poliklinik p ON p.`kd_poli`=r.`kd_poli` "
                    + "INNER JOIN penjab j ON j.`kd_pj`=r.`kd_pj` "
                    + "INNER JOIN dokter d ON e.`kd_dokter_peresep`=d.`kd_dokter` "
                    + "INNER JOIN pasien s ON r.`no_rkm_medis` =s.`no_rkm_medis` "
                    + "WHERE e.tgl_resep BETWEEN ? AND ? ORDER BY e.`no_resep`");
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                DataEResep obat = new DataEResep();
                obat.setNoResep(rs.getString("no_resep"));
                obat.setTglResep(Utils.formatDateSql(rs.getDate("tgl_resep")) + " " + rs.getString("jam_resep"));
                obat.setPoli(rs.getString("nm_poli"));
                obat.setDokter(rs.getString("nm_dokter"));
                obat.setJaminan(rs.getString("png_jawab"));
                obat.setPasien(rs.getString("no_rawat") + " " + rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"));
                obat.setValidasi(rs.getString("validasi"));
                obat.setDiterima(rs.getString("sampai_pasien"));
                obat.setStatus(rs.getString("status"));
                obat.setNoRawat(rs.getString("no_rawat"));
                List<ObatResep> obatDetails = getObatResepDetail(obat.getNoResep(), depo, tarif);
                obat.setObatDetails(obatDetails);

                obatList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatList;
    }

    public static List<ObatResep> getObatResepDetail(String noResep, String depo, String tarif) {
        List<ObatResep> obatDetailList = new LinkedList<>();
        PreparedStatement psttmn = null;
        ResultSet rset = null;
        try {
            psttmn = koneksi.prepareStatement("SELECT d.no_resep,o.kode_brng,o.nama_brng,d.jml,s.satuan,d.embalase,d.tuslah,d.aturan_pakai,g.stok,o.h_beli,o.karyawan,o.ralan,o.beliluar,o.kelas1,o.kelas2,o.kelas3,o.vip,o.vvip,j.nama AS jenis,k.nama AS kategori "
                    + "FROM e_resep_rsifc_detail d "
                    + "INNER JOIN databarang o ON d.kode_brng = o.kode_brng "
                    + "INNER JOIN gudangbarang g ON d.kode_brng = g.kode_brng "
                    + "INNER JOIN kodesatuan s ON s.kode_sat=o.kode_sat INNER JOIN jenis j ON j.kdjns=o.kdjns INNER JOIN kategori_barang k ON k.kode=o.kode_kategori "
                    + "WHERE d.no_resep = ? AND g.kd_bangsal=?");
            psttmn.setString(1, noResep);
            psttmn.setString(2, depo);
            rset = psttmn.executeQuery();
            while (rset.next()) {
                ObatResep obat = new ObatResep();
                obat.setKodeObat(rset.getString("kode_brng"));
                obat.setNamaObat(rset.getString("nama_brng"));
                obat.setJumlah(rset.getInt("jml"));
                obat.setAturanPakai(rset.getString("aturan_pakai"));
                obat.setHargaBeli(rset.getDouble("h_beli"));
                obat.setSatuan(rset.getString("satuan"));
                obat.setJenisObat(rset.getString("jenis"));
                obat.setKategori(rset.getString("kategori"));
                obat.setEmbalase(rset.getDouble("embalase"));
                obat.setTuslah(rset.getDouble("tuslah"));
                obat.setStok(rset.getDouble("stok"));
                double harga = rset.getDouble("ralan");
                if (tarif.equals(Konstan.PASIEN_KARYAWAN)) {
                    harga = rset.getDouble("karyawan");
                } else if (tarif.equals(Konstan.PASIEN_KELAS_VVIP)) {
                    harga = rset.getDouble("vvip");
                } else if (tarif.equals(Konstan.PASIEN_KELAS_VIP)) {
                    harga = rset.getDouble("vip");
                } else if (tarif.equals(Konstan.PASIEN_KELAS1)) {
                    harga = rset.getDouble("kelas1");
                } else if (tarif.equals(Konstan.PASIEN_KELAS2)) {
                    harga = rset.getDouble("kelas2");
                } else if (tarif.equals(Konstan.PASIEN_KELAS3)) {
                    harga = rset.getDouble("kelas3");
                } else if (tarif.equals(Konstan.PASIEN_BELILUAR)) {
                    harga = rset.getDouble("beliluar");
                }
                obat.setHarga(harga);
                obatDetailList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (psttmn != null) {

                    psttmn.close();

                }
                if (rset != null) {
                    rset.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatDetailList;
    }

    public static boolean saveDetailPemberianObat(String sttRawat, String norawat, List<ObatResep> obats, String depo) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            RegPeriksa reg = RegPeriksaDao.get(norawat);
            for (ObatResep obat : obats) {
                psttmn = koneksi.prepareStatement("insert into detail_pemberian_obat(tgl_perawatan,jam,no_rawat,kode_brng,h_beli,biaya_obat,jml,embalase,tuslah,total,status,kd_bangsal,no_batch,no_faktur) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                try {
                    psttmn.setString(1, Utils.formatDb(reg.getTanggalRawat()));
                    psttmn.setString(2, reg.getJamRawat());
                    psttmn.setString(3, norawat);
                    psttmn.setString(4, obat.getKodeObat());
                    psttmn.setDouble(5, obat.getHargaBeli());
                    psttmn.setDouble(6, obat.getHarga());
                    psttmn.setInt(7, obat.getJumlah());
                    psttmn.setDouble(8, obat.getEmbalase());
                    psttmn.setDouble(9, obat.getTuslah());
                    psttmn.setDouble(10, (obat.getHarga() * obat.getJumlah()) + obat.getEmbalase() + obat.getTuslah());
                    psttmn.setString(11, sttRawat);
                    psttmn.setString(12, depo);
                    psttmn.setString(13, "");
                    psttmn.setString(14, "");
                    psttmn.executeUpdate();
                    updateStokGudang(obat.getStok() - obat.getJumlah(), obat.getKodeObat(), depo);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (psttmn != null) {
                        psttmn.close();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Notifikasi : " + e);
            sukses = false;
            JOptionPane.showMessageDialog(null, "error : " + e);
        }
        return sukses;
    }

    public static boolean updateValidasi(String norawat, String noresep, Date validasi, List<ObatResep> reseps) {
        boolean sukses = true;
        PreparedStatement pst = null;
        List<ObatResep> racikans = new LinkedList<>();
        List<ObatResep> biasas = new LinkedList<>();
        for (ObatResep o : reseps) {
            if (!Utils.isBlank(o.getRacikan())) {                
                racikans.add(o);
            } else {
                biasas.add(o);
            }
        }
        try {
            if (biasas.size() > 0) {
                pst = koneksi.prepareStatement("update e_resep_rsifc set validasi = ?, status = ? where no_resep = ?");
                try {
                    pst.setString(1, Utils.formatDateTimeDb(validasi));
                    pst.setString(2, Resep.STATUS_SUDAH_VERIFIKASI);
                    pst.setString(3, noresep);
                    pst.executeUpdate();
                    boolean isDeleted = deleteDetailByNoResep(noresep);
                    if (isDeleted) {
                        saveDetail(noresep, biasas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (pst != null) {
                        pst.close();
                    }
                }
            }
            if (racikans.size() > 0) {
                if (isResepRacikanExist(noresep)) {
                    updateValidasiResepRacikan(noresep);
                    boolean isDeletedDetail = deleteDetailRacikanByNoResep(noresep);
                    if (isDeletedDetail) {
                        saveRacikanDetail(norawat, noresep, racikans);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean updateStokGudang(double stok, String kdBarang, String kdBangsal) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            if (stok > 0) {
                pst = koneksi.prepareStatement("update gudangbarang set stok = ? where kode_brng = ? and kd_bangsal = ?");
                try {
                    pst.setDouble(1, stok);
                    pst.setString(2, kdBarang);
                    pst.setString(3, kdBangsal);
                    pst.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (pst != null) {
                        pst.close();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean updateValidasiResepRacikan(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("update e_resep_racikan_rsifc set validasi = ?, status = ? where no_resep = ?");
            try {
                pst.setString(1, Utils.formatDateTimeDb(new Date()));
                pst.setString(2, Resep.STATUS_SUDAH_VERIFIKASI);
                pst.setString(3, noresep);
                pst.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Notifikasi : " + e);
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static List<DataEResep> getResepRacikanByDateAndDepo(String fromDate, String toDate, String depo, String tarif) {
        List<DataEResep> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT r.`no_rawat`,e.`no_resep`,e.`tgl_resep`,e.`jam_resep`,p.`nm_poli`,j.`png_jawab`,d.`nm_dokter`,s.`no_rkm_medis`,s.`nm_pasien`,e.`validasi`,e.`sampai_pasien`,"
                    + "e.`status` FROM e_resep_racikan_rsifc e "
                    + "INNER JOIN reg_periksa r ON r.`no_rawat`=e.`no_rawat` "
                    + "INNER JOIN poliklinik p ON p.`kd_poli`=r.`kd_poli` "
                    + "INNER JOIN penjab j ON j.`kd_pj`=r.`kd_pj` "
                    + "INNER JOIN dokter d ON e.`kd_dokter_peresep`=d.`kd_dokter` "
                    + "INNER JOIN pasien s ON r.`no_rkm_medis` =s.`no_rkm_medis` "
                    + "WHERE e.tgl_resep BETWEEN ? AND ? ORDER BY e.`no_resep`");
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                DataEResep obat = new DataEResep();
                obat.setNoResep(rs.getString("no_resep"));
                obat.setTglResep(Utils.formatDateSql(rs.getDate("tgl_resep")) + " " + rs.getString("jam_resep"));
                obat.setPoli(rs.getString("nm_poli"));
                obat.setDokter(rs.getString("nm_dokter"));
                obat.setJaminan(rs.getString("png_jawab"));
                obat.setPasien(rs.getString("no_rawat") + " " + rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"));
                obat.setValidasi(rs.getString("validasi"));
                obat.setDiterima(rs.getString("sampai_pasien"));
                obat.setStatus(rs.getString("status"));
                obat.setNoRawat(rs.getString("no_rawat"));
                List<ObatResep> obatDetails = getObatResepRacikanDetail(obat.getNoResep(), depo, tarif);
                obat.setObatDetails(obatDetails);

                obatList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatList;
    }

    public static boolean isResepRacikanExist(String noresep) {
        boolean isExist = false;
        PreparedStatement pre = null;
        ResultSet rset = null;
        try {
            pre = koneksi.prepareStatement("SELECT * FROM e_resep_racikan_rsifc WHERE no_resep = ?");
            pre.setString(1, noresep);
            rset = pre.executeQuery();
            while (rset.next()) {
                isExist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rset != null) {

                    rset.close();

                }
                if (pre != null) {
                    pre.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isExist;
    }

    public static List<ObatResep> getObatResepRacikanDetail(String noResep, String depo, String tarif) {
        List<ObatResep> obatDetailList = new LinkedList<>();
        PreparedStatement psttmn = null;
        ResultSet rset = null;
        try {
            psttmn = koneksi.prepareStatement("SELECT d.no_resep,o.kode_brng,o.nama_brng,d.kode_racik,d.nama_racik,d.jml,d.is_parent,d.kandungan,s.satuan,d.embalase,d.tuslah,d.aturan_pakai,g.stok,o.h_beli,o.karyawan,o.ralan,o.beliluar,o.kelas1,o.kelas2,o.kelas3,o.vip,o.vvip,j.nama AS jenis,k.nama AS kategori "
                    + "FROM e_resep_racikan_rsifc_detail d "
                    + "INNER JOIN databarang o ON d.kode_brng = o.kode_brng "
                    + "INNER JOIN gudangbarang g ON d.kode_brng = g.kode_brng "
                    + "INNER JOIN kodesatuan s ON s.kode_sat=o.kode_sat INNER JOIN jenis j ON j.kdjns=o.kdjns INNER JOIN kategori_barang k ON k.kode=o.kode_kategori "
                    + "WHERE d.no_resep = ? AND g.kd_bangsal=?");
            psttmn.setString(1, noResep);
            psttmn.setString(2, depo);
            rset = psttmn.executeQuery();
            while (rset.next()) {
                ObatResep obat = new ObatResep();
                obat.setKodeObat(rset.getString("kode_brng"));
                obat.setNamaObat(rset.getString("nama_brng"));
                obat.setKodeRacikan(rset.getString("kode_racik"));
                obat.setRacikan(rset.getString("nama_racik"));
                obat.setJumlah(rset.getInt("jml"));
                obat.setParent(rset.getBoolean("is_parent"));
                obat.setKandungan(rset.getString("kandungan"));
                obat.setAturanPakai(rset.getString("aturan_pakai"));
                obat.setHargaBeli(rset.getDouble("h_beli"));
                obat.setSatuan(rset.getString("satuan"));
                obat.setJenisObat(rset.getString("jenis"));
                obat.setKategori(rset.getString("kategori"));
                obat.setEmbalase(rset.getDouble("embalase"));
                obat.setTuslah(rset.getDouble("tuslah"));
                obat.setStok(rset.getDouble("stok"));
                double harga = rset.getDouble("ralan");
                if (tarif.equals(Konstan.PASIEN_KARYAWAN)) {
                    harga = rset.getDouble("karyawan");
                } else if (tarif.equals(Konstan.PASIEN_KELAS_VVIP)) {
                    harga = rset.getDouble("vvip");
                } else if (tarif.equals(Konstan.PASIEN_KELAS_VIP)) {
                    harga = rset.getDouble("vip");
                } else if (tarif.equals(Konstan.PASIEN_KELAS1)) {
                    harga = rset.getDouble("kelas1");
                } else if (tarif.equals(Konstan.PASIEN_KELAS2)) {
                    harga = rset.getDouble("kelas2");
                } else if (tarif.equals(Konstan.PASIEN_KELAS3)) {
                    harga = rset.getDouble("kelas3");
                } else if (tarif.equals(Konstan.PASIEN_BELILUAR)) {
                    harga = rset.getDouble("beliluar");
                }
                obat.setHarga(harga);
                obatDetailList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (psttmn != null) {

                    psttmn.close();

                }
                if (rset != null) {
                    rset.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatDetailList;
    }

}

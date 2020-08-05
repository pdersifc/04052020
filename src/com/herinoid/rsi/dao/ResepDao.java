/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

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

    public static boolean saveRacikanDetail(String norawat, String noResep, List<ObatResep> reseps) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            for (ObatResep o : reseps) {
                if (o.isParent()) {
                    saveObatRacikan(norawat, o);
                } else {
                    psttmn = koneksi.prepareStatement("insert into e_resep_racikan_rsifc_detail(no_resep,kode_racik,nama_racik,is_parent,kandungan,kode_brng,jml,aturan_pakai) values(?,?,?,?,?,?,?,?)");
                    try {
                        psttmn.setString(1, noResep);
                        psttmn.setString(2, o.getKodeRacikan());
                        psttmn.setString(3, o.getRacikan());
                        psttmn.setBoolean(4, o.isParent());
                        psttmn.setString(5, o.getKandungan());
                        psttmn.setString(6, o.getKodeObat());
                        psttmn.setInt(7, o.getJumlah());
                        psttmn.setString(8, o.getAturanPakai());
                        psttmn.executeUpdate();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (psttmn != null) {
                            psttmn.close();
                        }
                    }
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
                psttmn = koneksi.prepareStatement("insert into e_resep_rsifc_detail(no_resep,kode_brng,jml,aturan_pakai) values(?,?,?,?)");
                try {
                    psttmn.setString(1, noResep);
                    psttmn.setString(2, o.getKodeObat());
                    psttmn.setInt(3, o.getJumlah());
                    psttmn.setString(4, o.getAturanPakai());
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
                saveRacikanDetail(resep.getNoRawat(),resep.getNoResep(), resep.getObatResepRacikanDetail());
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

    public static List<Obat> getObatByCategory(String kdBangsal, String kdKategori, String jenisPasien) {
        List<Obat> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT databarang.kode_brng, databarang.nama_brng,jenis.nama AS jenis,kategori_barang.nama AS kategori,kodesatuan.satuan,"
                    + "databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.kelas1,"
                    + "databarang.kelas2,databarang.kelas3,databarang.vip,databarang.vvip,"
                    + "databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,gudangbarang.stok "
                    + "FROM databarang INNER JOIN jenis INNER JOIN industrifarmasi INNER JOIN gudangbarang INNER JOIN kategori_barang INNER JOIN kodesatuan "
                    + "ON databarang.kdjns=jenis.kdjns AND databarang.kode_brng=gudangbarang.kode_brng AND databarang.kode_kategori=kategori_barang.kode AND databarang.kode_sat=kodesatuan.kode_sat "
                    + "AND industrifarmasi.kode_industri=databarang.kode_industri "
                    + "WHERE  databarang.status='1' AND gudangbarang.kd_bangsal=? AND databarang.kode_kategori=? ORDER BY databarang.nama_brng");
            ps.setString(1, kdBangsal);
            ps.setString(2, kdKategori);
            rs = ps.executeQuery();
            while (rs.next()) {
                Obat obat = new Obat();
                obat.setKodeObat(rs.getString("kode_brng"));
                obat.setNamaObat(rs.getString("nama_brng"));
                obat.setSatuan(rs.getString("satuan"));
                obat.setStok(rs.getInt("stok"));
                obat.setKategori(rs.getString("kategori"));
                obat.setJenisObat(rs.getString("jenis"));
                obat.setKandungan("Kimia");
                double harga = rs.getDouble("ralan");
                if (jenisPasien.equals(Konstan.PASIEN_KARYAWAN)) {
                    harga = rs.getDouble("karyawan");
                } else if (jenisPasien.equals(Konstan.PASIEN_KELAS_VVIP)) {
                    harga = rs.getDouble("vvip");
                } else if (jenisPasien.equals(Konstan.PASIEN_KELAS_VIP)) {
                    harga = rs.getDouble("vip");
                } else if (jenisPasien.equals(Konstan.PASIEN_KELAS1)) {
                    harga = rs.getDouble("kelas1");
                } else if (jenisPasien.equals(Konstan.PASIEN_KELAS2)) {
                    harga = rs.getDouble("kelas2");
                } else if (jenisPasien.equals(Konstan.PASIEN_KELAS3)) {
                    harga = rs.getDouble("kelas3");
                }
//                System.out.println("letak barang = "+rs.getString("letak_barang"));
                obat.setHarga(harga);
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
}

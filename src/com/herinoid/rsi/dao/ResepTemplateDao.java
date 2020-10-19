/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.ResepTemplate;
import com.herinoid.rsi.model.EtiketObat;
import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ObatResep;
import com.herinoid.rsi.model.RegPeriksa;
import com.herinoid.rsi.model.Resep;
import com.herinoid.rsi.model.RincianResepVerifikasi;
import com.herinoid.rsi.util.Konstan;
import com.herinoid.rsi.util.NumberUtils;
import com.herinoid.rsi.dao.MarginDao;
import com.herinoid.rsi.dao.ResepTemplateDao.Actions;
import com.herinoid.rsi.model.MarginBpjs;
import com.herinoid.rsi.model.MarginObatNonBpjs;
import com.herinoid.rsi.model.ResepTemplate;
import static com.herinoid.rsi.util.NumberUtils.DATE_FORMAT_NUMBERING;
import com.herinoid.rsi.util.Utils;
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Hewrei
 */
public class ResepTemplateDao {

    private static Connection koneksi = koneksiDB.condb();
    private static PreparedStatement ps;
    private static ResultSet rs;

   
    public static boolean deleteDetailRacikanByTemplate(String kodeTemplate) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("delete from e_resep_template_detail_racikan where e_resep_template_id = ?");
            try {
                pst.setString(1, kodeTemplate);
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

    

    public static boolean saveRacikanDetail(String kodeTemplate, List<ObatResep> reseps) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            for (ObatResep o : reseps) {
                psttmn = koneksi.prepareStatement("insert into e_resep_template_detail_racikan(e_resep_template_id,kode_racik,nama_racik,is_parent,kandungan,kode_brng,jml,embalase,tuslah,p1,p2,aturan_pakai,code,metode_racik) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                try {
                    psttmn.setString(1, kodeTemplate);
                    psttmn.setString(2, o.getKodeRacikan());
                    psttmn.setString(3, o.getRacikan());
                    psttmn.setBoolean(4, o.isParent());
                    psttmn.setDouble(5, o.getKandungan());
                    psttmn.setString(6, o.getKodeObat());
                    psttmn.setDouble(7, o.getJumlah());
                    psttmn.setDouble(8, o.getEmbalase());
                    psttmn.setDouble(9, o.getTuslah());
                    psttmn.setInt(10, o.getPembilang());
                    psttmn.setInt(11, o.getPenyebut());
                    psttmn.setString(12, o.getAturanPakai());                    
                    psttmn.setString(13, Utils.TSID(new Date()));
                    psttmn.setString(14, o.getMetodeRacikKode());
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

        } catch (Exception e) {
            e.printStackTrace();
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean deleteDetailByTemplate(String kodeTemplate) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("delete from e_resep_template_detail where e_resep_template_id = ?");
            try {
                pst.setString(1, kodeTemplate);
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

    public static boolean deleteTemplateResepByCode(String code) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("delete from e_resep_template where code = ?");
            try {
                pst.setString(1, code);
                pst.execute();
                deleteDetailByTemplate(code);
                deleteDetailRacikanByTemplate(code);
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

    public static boolean saveDetail(String kdTemplate, List<ObatResep> reseps) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            for (ObatResep o : reseps) {
                psttmn = koneksi.prepareStatement("insert into e_resep_template_detail(e_resep_template_id,kode_brng,jml,embalase,tuslah,aturan_pakai) values(?,?,?,?,?,?)");
                try {
                    psttmn.setString(1, kdTemplate);
                    psttmn.setString(2, o.getKodeObat());
                    psttmn.setDouble(3, o.getJumlah());
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

    public static boolean save(ResepTemplate resep) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        if (resep.getObatTemplateRacikanDetail().size() > 0) {
            resep.setRacikan(true);
        }
        try {
            psttmn = koneksi.prepareStatement("insert into e_resep_template(code,kd_dokter,nama_dokter,nama_template,is_racikan,kd_jaminan) values(?,?,?,?,?,?)");
            try {
                psttmn.setString(1, resep.getCode());
                psttmn.setString(2, resep.getKdDokter());
                psttmn.setString(3, resep.getNamaDokter());
                psttmn.setString(4, resep.getNamaTemplate());
                psttmn.setBoolean(5, resep.isRacikan());
                psttmn.setString(6, resep.getKdJaminan());
                psttmn.executeUpdate();
                if (resep.getObatTemplateDetail().size() > 0) {
                    saveDetail(resep.getCode(), resep.getObatTemplateDetail());
                }
                
                if (resep.getObatTemplateRacikanDetail().size() > 0) {
                    saveRacikanDetail(resep.getCode(), resep.getObatTemplateRacikanDetail());
                }
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
    
    public static boolean update(ResepTemplate resep) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        if (resep.getObatTemplateRacikanDetail().size() > 0) {
            resep.setRacikan(true);
        }
        try {
            psttmn = koneksi.prepareStatement("update e_resep_template set kd_dokter = ?,nama_dokter = ?,nama_template = ?,is_racikan = ?,kd_jaminan = ? where code = ?");
            try {
                
                psttmn.setString(1, resep.getKdDokter());
                psttmn.setString(2, resep.getNamaDokter());
                psttmn.setString(3, resep.getNamaTemplate());
                psttmn.setBoolean(4, resep.isRacikan());
                psttmn.setString(5, resep.getKdJaminan());
                psttmn.setString(6, resep.getCode());
                psttmn.executeUpdate();
                if (resep.getObatTemplateDetail().size() > 0) {
                    deleteDetailByTemplate(resep.getCode());
                    saveDetail(resep.getCode(), resep.getObatTemplateDetail());
                }
                
                if (resep.getObatTemplateRacikanDetail().size() > 0) {
                    deleteDetailRacikanByTemplate(resep.getCode());
                    saveRacikanDetail(resep.getCode(), resep.getObatTemplateRacikanDetail());
                }
            } catch (Exception e) {
                sukses = false;
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

   

    public static List<ResepTemplate> getTemplateByDokter(String kdDokter, String depo) {
        List<ResepTemplate> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT e.`code`,e.`kd_dokter`,e.`nama_dokter`,e.`kd_jaminan`,e.`nama_template`,e.`is_racikan` FROM e_resep_template e where e.`kd_dokter` = ? ");
            ps.setString(1, kdDokter);
            rs = ps.executeQuery();
            while (rs.next()) {
                ResepTemplate obat = new ResepTemplate();
                obat.setCode(rs.getString("code"));
                obat.setKdDokter(rs.getString("kd_dokter"));
                obat.setNamaDokter(rs.getString("nama_dokter"));
                obat.setKdJaminan(rs.getString("kd_jaminan"));
                obat.setNamaTemplate(rs.getString("nama_template"));
                obat.setRacikan(rs.getBoolean("is_racikan"));
                if (obat.isRacikan()) {
                    List<ObatResep> obatRacikanDetails = getObatResepRacikanDetail(obat.getCode(), depo, obat.getKdJaminan());
                    Collections.sort(obatRacikanDetails, Comparator
                            .comparing(ObatResep::getKodeRacikan));
                    obat.setObatTemplateRacikanDetail(obatRacikanDetails);
                } else {
                    List<ObatResep> obatDetails = getObatResepDetail(obat.getCode(), depo, obat.getKdJaminan());
                    Collections.sort(obatDetails, Comparator
                            .comparing(ObatResep::getNamaObat)
                            .thenComparing(ObatResep::getNamaObat));
                    obat.setObatTemplateDetail(obatDetails);
                }

                obatList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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
    enum Actions {
        HAPUS, EDIT;
    }
    public static List<ResepTemplate> getAllTemplate() {
        
        List<ResepTemplate> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT e.`code`,e.`kd_dokter`,e.`nama_dokter`,e.`kd_jaminan`,e.`nama_template`,e.`is_racikan` FROM e_resep_template e ");            
            rs = ps.executeQuery();
            while (rs.next()) {
                ResepTemplate obat = new ResepTemplate();
                obat.setCode(rs.getString("code"));
                obat.setKdDokter(rs.getString("kd_dokter"));
                obat.setNamaDokter(rs.getString("nama_dokter"));
                obat.setKdJaminan(rs.getString("kd_jaminan"));
                obat.setNamaTemplate(rs.getString("nama_template"));
                obat.setRacikan(rs.getBoolean("is_racikan"));
//                if (obat.isRacikan()) {
//                    List<ObatResep> obatRacikanDetails = getObatResepRacikanDetail(obat.getCode(), depo, obat.getKdJaminan());
//                    Collections.sort(obatRacikanDetails, Comparator
//                            .comparing(ObatResep::getKodeRacikan));
//                    obat.setObatTemplateRacikanDetail(obatRacikanDetails);
//                } else {
//                    List<ObatResep> obatDetails = getObatResepDetail(obat.getCode(), depo, obat.getKdJaminan());
//                    Collections.sort(obatDetails, Comparator
//                            .comparing(ObatResep::getNamaObat)
//                            .thenComparing(ObatResep::getNamaObat));
//                    obat.setObatTemplateDetail(obatDetails);
//                }

                obatList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public static List<ObatResep> getObatResepDetail(String noResep, String depo, String jaminan) {
        List<ObatResep> obatDetailList = new LinkedList<>();
        PreparedStatement psttmn = null;
        ResultSet rset = null;
        try {
            psttmn = koneksi.prepareStatement("SELECT d.e_resep_template_id,o.kode_brng,o.nama_brng,d.jml,s.satuan,d.embalase,d.tuslah,d.aturan_pakai,g.stok,o.h_beli,o.karyawan,o.ralan,o.beliluar,o.kelas1,o.kelas2,o.kelas3,o.vip,o.vvip,j.nama AS jenis,k.nama AS kategori "
                    + "FROM e_resep_template_detail d "
                    + "INNER JOIN databarang o ON d.kode_brng = o.kode_brng "
                    + "INNER JOIN gudangbarang g ON d.kode_brng = g.kode_brng "
                    + "INNER JOIN kodesatuan s ON s.kode_sat=o.kode_sat INNER JOIN jenis j ON j.kdjns=o.kdjns INNER JOIN kategori_barang k ON k.kode=o.kode_kategori "
                    + "WHERE d.e_resep_template_id = ? AND g.kd_bangsal=?");
            psttmn.setString(1, noResep);
            psttmn.setString(2, depo);
            rset = psttmn.executeQuery();
            while (rset.next()) {
                ObatResep obat = new ObatResep();
                obat.setKodeObat(rset.getString("kode_brng"));
                obat.setNamaObat(rset.getString("nama_brng"));
                obat.setJumlah(rset.getDouble("jml"));
                obat.setAturanPakai(rset.getString("aturan_pakai"));
                obat.setHargaBeli(rset.getDouble("h_beli"));
                obat.setSatuan(rset.getString("satuan"));
                obat.setJenisObat(rset.getString("jenis"));
                obat.setKategori(rset.getString("kategori"));
                obat.setEmbalase(rset.getDouble("embalase"));
                obat.setTuslah(rset.getDouble("tuslah"));
                obat.setStok(rset.getDouble("stok"));
                obat.setParent(false);

                double marginPersen = 28;
                if (jaminan.equals("BPJ")) {
                    MarginBpjs marginBpjs = MarginDao.getMarginBpjs(obat.getKodeObat());
                    if (marginBpjs != null) {
                        marginPersen = marginBpjs.getRalan();
                    }

                } else {
                    MarginObatNonBpjs marginNon = MarginDao.getMarginNonBpjs(jaminan);
                    if (marginNon != null) {
                        marginPersen = marginNon.getMargin();
                    }

                }

                double margin = (obat.getHargaBeli() * marginPersen) / 100;
                double hpp = margin + obat.getHargaBeli();
//                System.out.println("margin persen = "+marginPersen+" || margin rupiah = "+margin+" || harga beli obat = "+obat.getHargaBeli()+ " || hpp = "+hpp);
                obat.setHarga(Utils.roundUp(hpp));
                obatDetailList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public static ObatResep getObatStock(String kdObat, String depo, String tarif) {
        ObatResep obat = new ObatResep();
        PreparedStatement psttmn = null;
        ResultSet rset = null;
        try {
            psttmn = koneksi.prepareStatement("SELECT o.kode_brng,o.nama_brng,s.satuan,g.stok,o.h_beli,o.karyawan,o.ralan,o.beliluar,o.kelas1,o.kelas2,o.kelas3,o.vip,o.vvip,j.nama AS jenis,k.nama AS kategori "
                    + "FROM databarang o "
                    + "INNER JOIN gudangbarang g ON o.kode_brng = g.kode_brng "
                    + "INNER JOIN kodesatuan s ON s.kode_sat=o.kode_sat INNER JOIN jenis j ON j.kdjns=o.kdjns INNER JOIN kategori_barang k ON k.kode=o.kode_kategori "
                    + "WHERE g.kd_bangsal=? AND o.kode_brng = ?");
            psttmn.setString(1, depo);
            psttmn.setString(2, kdObat);
            rset = psttmn.executeQuery();
            while (rset.next()) {
                obat.setKodeObat(rset.getString("kode_brng"));
                obat.setNamaObat(rset.getString("nama_brng"));
                obat.setJumlah(0);
                obat.setAturanPakai("");
                obat.setHargaBeli(rset.getDouble("h_beli"));
                obat.setSatuan(rset.getString("satuan"));
                obat.setJenisObat(rset.getString("jenis"));
                obat.setKategori(rset.getString("kategori"));
                obat.setEmbalase(0);
                obat.setTuslah(0);
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
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return obat;
    }

    public static boolean saveDetailPemberianObat(String sttRawat, String norawat, List<ObatResep> obats, String depo, String noResep, String jaminan) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            RegPeriksa reg = RegPeriksaDao.get(norawat);
            for (ObatResep obat : obats) {
                if (obat.isParent() == false) {
                    Obat obt = ObatDao.getObatForSave(depo, obat.getKodeObat(), jaminan, reg.getKdPj());
                    psttmn = koneksi.prepareStatement("insert into detail_pemberian_obat(tgl_perawatan,jam,no_rawat,kode_brng,h_beli,biaya_obat,jml,embalase,tuslah,total,status,kd_bangsal,no_batch,no_faktur) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    try {
                        psttmn.setString(1, Utils.formatDb(new Date()));
                        psttmn.setString(2, Utils.formatTime(new Date()));
                        psttmn.setString(3, norawat);
                        psttmn.setString(4, obat.getKodeObat());
                        psttmn.setDouble(5, obt.getHargaDasar());
                        psttmn.setDouble(6, obt.getHarga());
                        psttmn.setDouble(7, obat.getJumlah());
                        psttmn.setDouble(8, obat.getEmbalase());
                        psttmn.setDouble(9, obat.getTuslah());
                        psttmn.setDouble(10, Utils.roundUpKhanza((obt.getHarga() * obat.getJumlah()) + obat.getEmbalase() + obat.getTuslah(), 100));
                        psttmn.setString(11, Utils.isBlank(sttRawat) ? "Ralan" : sttRawat);
                        psttmn.setString(12, depo);
                        psttmn.setString(13, "");
                        psttmn.setString(14, noResep);
                        psttmn.executeUpdate();
                        updateStokGudang(obat.getStok() - Utils.rounding(obat.getJumlah()), obat.getKodeObat(), depo);
                        saveObatValidasi(noResep, obat);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (psttmn != null) {
                            psttmn.close();
                        }
                    }
                } else {
                    if (isResepRacikanExist(noResep)) {
                        updateAturanPakaiRacikan(obat.getAturanPakai(), noResep, obat.getKodeObat());
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
//        List<ObatResep> racikans = new LinkedList<>();
//        List<ObatResep> biasas = new LinkedList<>();
//        for (ObatResep o : reseps) {
//            if (!Utils.isBlank(o.getRacikan())) {
//                racikans.add(o);
//            } else {
//                biasas.add(o);
//            }
//        }
        try {
//            if (biasas.size() > 0) {
            pst = koneksi.prepareStatement("update e_resep_rsifc set validasi = ?, status = ? where no_resep = ?");
            try {
                pst.setString(1, Utils.formatDateTimeDb(validasi));
                pst.setString(2, Resep.STATUS_SUDAH_VERIFIKASI);
                pst.setString(3, noresep);
                pst.executeUpdate();
//                    boolean isDeleted = deleteDetailByNoResep(noresep);
//                    if (isDeleted) {
//                        saveDetail(noresep, biasas);
//                    }
            } catch (Exception e) {
                sukses = false;
                e.printStackTrace();
                System.out.println("Notifikasi : " + e);
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
//            }
//            if (racikans.size() > 0) {
            if (isResepRacikanExist(noresep)) {
                updateValidasiResepRacikan(noresep);
//                    boolean isDeletedDetail = deleteDetailRacikanByNoResep(noresep);
//                    if (isDeletedDetail) {
//                        saveRacikanDetail(norawat, noresep, racikans);
//                    }
            }
//            }

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
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public static boolean isResepExist(String noresep) {
        boolean isExist = false;
        PreparedStatement pre = null;
        ResultSet rset = null;
        try {
            pre = koneksi.prepareStatement("SELECT * FROM e_resep_rsifc WHERE no_resep = ?");
            pre.setString(1, noresep);
            rset = pre.executeQuery();
            while (rset.next()) {
                isExist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public static boolean isResepRacikanExistByNorawat(String norawat) {
        boolean isExist = false;
        PreparedStatement pre = null;
        ResultSet rset = null;
        try {
            pre = koneksi.prepareStatement("SELECT * FROM e_resep_racikan_rsifc WHERE no_rawat = ?");
            pre.setString(1, norawat);
            rset = pre.executeQuery();
            while (rset.next()) {
                isExist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public static boolean isResepExistByNorawat(String norawat) {
        boolean isExist = false;
        PreparedStatement pre = null;
        ResultSet rset = null;
        try {
            pre = koneksi.prepareStatement("SELECT * FROM e_resep_rsifc WHERE no_rawat = ?");
            pre.setString(1, norawat);
            rset = pre.executeQuery();
            while (rset.next()) {
                isExist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public static List<ObatResep> getObatResepRacikanDetail(String codeTemplate, String depo, String jaminan) {
        List<ObatResep> obatDetailList = new LinkedList<>();
        PreparedStatement psttmn = null,pstracikan = null;
        ResultSet rset = null,rsRck = null;
        try {
            pstracikan = koneksi.prepareStatement("SELECT d.e_resep_template_id,d.kode_brng,d.nama_racik,d.kode_racik,d.nama_racik,d.jml,d.is_parent,d.kandungan,d.embalase,d.tuslah,d.aturan_pakai,d.p1,d.p2,m.kd_racik,m.nm_racik FROM e_resep_template_detail_racikan d JOIN metode_racik m ON d.`metode_racik`=m.`kd_racik` where  d.kode_brng like CONCAT( '%',?,'%') and d.e_resep_template_id = ? ");
            pstracikan.setString(1, "RCK");
            pstracikan.setString(2, codeTemplate);            
            rsRck = pstracikan.executeQuery();
            while (rsRck.next()) {
                
                ObatResep obat = new ObatResep();
                obat.setNamaObat(rsRck.getString("nama_racik"));
                obat.setKodeObat(rsRck.getString("kode_brng"));
                obat.setKodeRacikan(rsRck.getString("kode_racik"));
                obat.setRacikan(rsRck.getString("nama_racik"));
                obat.setJumlah(rsRck.getDouble("jml"));
                obat.setParent(rsRck.getBoolean("is_parent"));
                obat.setKandungan(rsRck.getDouble("kandungan"));
                obat.setAturanPakai(rsRck.getString("aturan_pakai"));
                obat.setEmbalase(rsRck.getDouble("embalase"));
                obat.setTuslah(rsRck.getDouble("tuslah"));
                obat.setMetodeRacikKode(rsRck.getString("kd_racik"));
                obat.setSatuan(rsRck.getString("nm_racik"));
                obat.setPembilang(rsRck.getInt("p1"));
                obat.setPenyebut(rsRck.getInt("p2"));
                obat.setJenisObat(Obat.OBAT_RACIKAN);
                obat.setParent(true);               
                obatDetailList.add(obat);
            }
            psttmn = koneksi.prepareStatement("SELECT d.e_resep_template_id,d.kode_brng,o.nama_brng,o.kapasitas,d.kode_racik,d.nama_racik,d.jml,d.is_parent,d.kandungan,s.satuan,d.embalase,d.tuslah,d.aturan_pakai,d.p1,d.p2,g.stok,o.h_beli,o.karyawan,o.ralan,o.beliluar,o.kelas1,o.kelas2,o.kelas3,o.vip,o.vvip,j.nama AS jenis,k.nama AS kategori "
                    + "FROM e_resep_template_detail_racikan d "
                    + "INNER JOIN databarang o ON d.kode_brng = o.kode_brng "
                    + "INNER JOIN gudangbarang g ON d.kode_brng = g.kode_brng "
                    + "INNER JOIN kodesatuan s ON s.kode_sat=o.kode_sat INNER JOIN jenis j ON j.kdjns=o.kdjns INNER JOIN kategori_barang k ON k.kode=o.kode_kategori "
                    + "WHERE d.e_resep_template_id = ? AND g.kd_bangsal=?");
            psttmn.setString(1, codeTemplate);
            psttmn.setString(2, depo);
            rset = psttmn.executeQuery();
            while (rset.next()) {
                ObatResep obat = new ObatResep();
                obat.setKodeObat(rset.getString("kode_brng"));
                obat.setNamaObat(rset.getString("nama_brng"));
                obat.setKodeRacikan(rset.getString("kode_racik"));
                obat.setRacikan(rset.getString("nama_racik"));
                obat.setJumlah(rset.getDouble("jml"));
                obat.setParent(rset.getBoolean("is_parent"));
                obat.setKandungan(rset.getDouble("kandungan"));
                obat.setAturanPakai(rset.getString("aturan_pakai"));
                obat.setHargaBeli(rset.getDouble("h_beli"));
                obat.setSatuan(rset.getString("satuan"));
                obat.setJenisObat(Obat.OBAT_RACIKAN);
                obat.setKategori(rset.getString("kategori"));
                obat.setEmbalase(rset.getDouble("embalase"));
                obat.setTuslah(rset.getDouble("tuslah"));
                obat.setStok(rset.getDouble("stok"));
                obat.setPembilang(rset.getInt("p1"));
                obat.setPenyebut(rset.getInt("p2"));
                obat.setKapasitas(rset.getDouble("kapasitas"));
                obat.setParent(false);
                double marginPersen = 28;
                if (jaminan.equals("BPJ")) {
                    MarginBpjs marginBpjs = MarginDao.getMarginBpjs(obat.getKodeObat());
                    if (marginBpjs != null) {
                        marginPersen = marginBpjs.getRalan();
                    }

                } else {
                    MarginObatNonBpjs marginNon = MarginDao.getMarginNonBpjs(jaminan);
                    if (marginNon != null) {
                        marginPersen = marginNon.getMargin();
                    }

                }
                double margin = (obat.getHargaBeli() * marginPersen) / 100;
                double hpp = margin + obat.getHargaBeli();
                obat.setHarga(Utils.roundUp(hpp));
                obatDetailList.add(obat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstracikan != null) {
                    pstracikan.close();
                }
                
                if (rsRck != null) {
                    rsRck.close();
                }
                
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

    public static boolean updateDiterimaPasien(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            if (isResepExist(noresep)) {
                pst = koneksi.prepareStatement("update e_resep_rsifc set sampai_pasien = ?,status = ? where no_resep = ?");
                try {
                    pst.setString(1, Utils.formatDateTimeDb(new Date()));
                    pst.setString(2, Resep.STATUS_SAMPAI_PASIEN);
                    pst.setString(3, noresep);
                    pst.executeUpdate();
                    if (isResepRacikanExist(noresep)) {
                        updateDiterimaPasienRacikan(noresep);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (pst != null) {
                        pst.close();
                    }
                }

            } else {
                if (isResepRacikanExist(noresep)) {
                    updateDiterimaPasienRacikan(noresep);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean updateDiterimaPasienRacikan(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("update e_resep_racikan_rsifc set sampai_pasien = ?,status = ? where no_resep = ?");
            try {
                pst.setString(1, Utils.formatDateTimeDb(new Date()));
                pst.setString(2, Resep.STATUS_SAMPAI_PASIEN);
                pst.setString(3, noresep);
                pst.executeUpdate();
//                if (isResepRacikanExist(noresep)) {
//
//                }
            } catch (SQLException e) {
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

    public static boolean saveObatValidasi(String noResep, ObatResep obat) {
        boolean sukses = true;
        PreparedStatement psttmn = null;
        try {
            psttmn = koneksi.prepareStatement("insert into obat_validasi_eresep_rsifc(no_resep,kode_brng,is_racikan,kode_racikan,nama_racikan,jml,embalase,tuslah,aturan_pakai,code) values(?,?,?,?,?,?,?,?,?,?)");
            try {
                psttmn.setString(1, noResep);
                psttmn.setString(2, obat.getKodeObat());
                psttmn.setBoolean(3, !Utils.isBlank(obat.getRacikan()));
                psttmn.setString(4, !Utils.isBlank(obat.getRacikan()) ? obat.getKodeRacikan() : "");
                psttmn.setString(5, !Utils.isBlank(obat.getRacikan()) ? obat.getRacikan() : "");
                psttmn.setDouble(6, Utils.rounding(obat.getJumlah()));
                psttmn.setDouble(7, obat.getEmbalase());
                psttmn.setDouble(8, obat.getTuslah());
                psttmn.setString(9, obat.getAturanPakai());
                psttmn.setString(10, Utils.TSID(new Date()));
                psttmn.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Notifikasi : " + e);
            } finally {
                if (psttmn != null) {
                    psttmn.close();
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

    public static boolean updatePackingRacikan(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("update e_resep_racikan_rsifc set packing = ?,status = ? where no_resep = ?");
            try {
                pst.setString(1, Utils.formatDateTimeDb(new Date()));
                pst.setString(2, Resep.STATUS_PACKING);
                pst.setString(3, noresep);
                pst.executeUpdate();
            } catch (SQLException e) {
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

    public static boolean updatePacking(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            if (isResepExist(noresep)) {
                pst = koneksi.prepareStatement("update e_resep_rsifc set packing = ?,status = ? where no_resep = ?");
                try {
                    pst.setString(1, Utils.formatDateTimeDb(new Date()));
                    pst.setString(2, Resep.STATUS_PACKING);
                    pst.setString(3, noresep);
                    pst.executeUpdate();
                    if (isResepRacikanExist(noresep)) {
                        updatePackingRacikan(noresep);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (pst != null) {
                        pst.close();
                    }
                }

            } else {
                if (isResepRacikanExist(noresep)) {
                    updatePackingRacikan(noresep);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static List<EtiketObat> getEtiketByNoResep(String noResep, String depo) {
        List<EtiketObat> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT e.`no_resep`,e.`no_rawat`,e.`tgl_resep`,e.`jam_resep`,p.`no_rkm_medis`,p.`nm_pasien`,b.`nama_brng`,d.`jml`,s.`satuan`,b.`expire`,d.`aturan_pakai`,l.`nm_bangsal`,k.`nm_poli`,d.kode_racikan FROM obat_validasi_eresep_rsifc d "
                    + "JOIN e_resep_rsifc e ON e.`no_resep`=d.`no_resep` "
                    + "JOIN databarang b ON d.kode_brng = b.kode_brng "
                    + "JOIN gudangbarang g ON b.`kode_brng`=g.`kode_brng` "
                    + "JOIN bangsal l ON l.`kd_bangsal`= g.`kd_bangsal` "
                    + "JOIN kodesatuan s ON s.`kode_sat` = b.`kode_sat` "
                    + "JOIN reg_periksa r ON r.`no_rawat` = e.`no_rawat` "
                    + "JOIN poliklinik k ON r.`kd_poli`= k.`kd_poli` "
                    + "JOIN pasien p ON p.`no_rkm_medis` = r.`no_rkm_medis` "
                    + "WHERE d.`no_resep` = ? AND g.`kd_bangsal` = ?");
            ps.setString(1, noResep);
            ps.setString(2, depo);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (Utils.isBlank(rs.getString("kode_racikan"))) {
                    EtiketObat obat = new EtiketObat();
                    obat.setNoResep(rs.getString("no_resep"));
                    obat.setNoRawat(rs.getString("no_rawat"));
                    obat.setTanggal(Utils.formatDateSql(rs.getDate("tgl_resep")));
                    obat.setJam(rs.getString("jam_resep"));
                    obat.setNoRekamMedis(rs.getString("no_rkm_medis"));
                    obat.setPasien(rs.getString("nm_pasien"));
                    obat.setObat(rs.getString("nama_brng"));
                    obat.setSatuan(rs.getString("satuan"));
                    obat.setJml(rs.getInt("jml"));
                    obat.setExpire(rs.getDate("expire") == null ? "00:00:00" : Utils.formatDateSql(rs.getDate("expire")));
                    obat.setAturanPakai(rs.getString("aturan_pakai"));
                    obat.setLokasi(rs.getString("nm_poli"));
                    obatList.add(obat);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatList;
    }

    public static boolean updateAturanPakaiFarmasi(String aturanPakai, String noresep, String kodeObat) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("update obat_validasi_eresep_rsifc set aturan_pakai = ? where no_resep = ? and kode_brng = ?");
            try {
                pst.setString(1, aturanPakai);
                pst.setString(2, noresep);
                pst.setString(3, kodeObat);
                pst.executeUpdate();
            } catch (SQLException e) {
                sukses = false;
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

    public static boolean updateAturanPakaiRacikan(String aturanPakai, String noresep, String kodeObat) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("update obat_racikan_eresep_rsifc set aturan_pakai_farmasi = ? where no_resep = ? and kd_racik = ?");
            try {
                pst.setString(1, aturanPakai);
                pst.setString(2, noresep);
                pst.setString(3, kodeObat);
                pst.executeUpdate();
            } catch (SQLException e) {
                sukses = false;
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

    public static boolean deleteDataObatValidasiFarmasi(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("delete from obat_validasi_eresep_rsifc where no_resep = ?");
            try {
                pst.setString(1, noresep);
                pst.execute();
            } catch (Exception e) {
                sukses = false;
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

    public static boolean deleteDataObatValidasiFarmasiSatuan(String noresep, String kodeObat) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("delete from obat_validasi_eresep_rsifc where no_resep = ? and kode_brng = ?");
            try {
                pst.setString(1, noresep);
                pst.setString(2, kodeObat);
                pst.execute();
            } catch (Exception e) {
                sukses = false;
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

    public static boolean updateValidasiAfterHapus(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("update e_resep_rsifc set validasi = ?, status = ? where no_resep = ?");
            try {
                pst.setString(1, null);
                pst.setString(2, Resep.STATUS_BELUM_VERIFIKASI);
                pst.setString(3, noresep);
                pst.executeUpdate();
            } catch (Exception e) {
                sukses = false;
                e.printStackTrace();
                System.out.println("Notifikasi : " + e);
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
            if (isResepRacikanExist(noresep)) {
                updateValidasiResepRacikanAfterHapus(noresep);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sukses = false;
            System.out.println("Notifikasi : " + e);
        }
        return sukses;
    }

    public static boolean updateValidasiResepRacikanAfterHapus(String noresep) {
        boolean sukses = true;
        PreparedStatement pst = null;
        try {
            pst = koneksi.prepareStatement("update e_resep_racikan_rsifc set validasi = ?, status = ? where no_resep = ?");
            try {
                pst.setString(1, null);
                pst.setString(2, Resep.STATUS_BELUM_VERIFIKASI);
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

    public static List<EtiketObat> getEtiketRacikanByNoResep(String noResep) {
        List<EtiketObat> obatList = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT r.`no_rawat`,r.`no_resep`,r.`kd_racik`,r.`nama_racik`,r.`aturan_pakai_farmasi`,r.`jml_dr`,m.`nm_racik`,r.`tgl_perawatan`,r.`jam`,k.`nm_poli`,s.`nm_pasien`,s.`no_rkm_medis` FROM obat_racikan_eresep_rsifc r  "
                    + "JOIN metode_racik m ON r.`metode_racik`=m.`kd_racik` "
                    + "JOIN reg_periksa p ON p.`no_rawat` = r.`no_rawat`  "
                    + "JOIN poliklinik k ON p.`kd_poli`= k.`kd_poli`  "
                    + "JOIN pasien s ON s.`no_rkm_medis` = p.`no_rkm_medis`  "
                    + "WHERE r.`no_resep` = ?");
            ps.setString(1, noResep);
            rs = ps.executeQuery();
            while (rs.next()) {
                EtiketObat obat = new EtiketObat();
                obat.setNoResep(rs.getString("no_resep"));
                obat.setNoRawat(rs.getString("no_rawat"));
                obat.setTanggal(Utils.formatDateSql(rs.getDate("tgl_perawatan")));
                obat.setJam(rs.getString("jam"));
                obat.setPasien(rs.getString("nm_pasien"));
                obat.setObat(rs.getString("nama_racik"));
                obat.setSatuan(rs.getString("nm_racik"));
                obat.setJml(rs.getInt("jml_dr"));
                obat.setExpire(Utils.getNextMonthDate(rs.getDate("tgl_perawatan")));
                obat.setAturanPakai(rs.getString("aturan_pakai_farmasi"));
                obat.setLokasi(rs.getString("nm_poli"));
                obat.setNoRekamMedis(rs.getString("no_rkm_medis"));
                obatList.add(obat);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {

                    rs.close();

                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ObatDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obatList;
    }

    public static int getNewRecordEResep() {
        int x = 0;
        try {
            ps = koneksi.prepareStatement("SELECT COUNT(*) as jumlah FROM e_resep_rsifc WHERE tgl_resep = CURRENT_DATE");

            rs = ps.executeQuery();
            while (rs.next()) {
                x = rs.getInt("jumlah");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResepTemplateDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return x;
    }

}

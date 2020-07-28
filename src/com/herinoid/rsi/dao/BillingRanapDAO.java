/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.dao;

import com.herinoid.rsi.model.BiayaOperasi;
import com.herinoid.rsi.model.BiayaRalanDokter;
import com.herinoid.rsi.model.BiayaRalanDokterPerawat;
import com.herinoid.rsi.model.BiayaRalanPerawat;
import com.herinoid.rsi.model.BiayaRanapDokter;
import com.herinoid.rsi.model.BiayaRanapDokterPerawat;
import com.herinoid.rsi.model.BiayaRanapPerawat;
import com.herinoid.rsi.model.BiayaTambahan;
import com.herinoid.rsi.model.BillingRanap;
import com.herinoid.rsi.model.BillingSimulator;
import com.herinoid.rsi.model.DokterRajal;
import com.herinoid.rsi.model.DokterRanap;
import com.herinoid.rsi.model.KamarInap;
import com.herinoid.rsi.model.KategoriPerawatan;
import com.herinoid.rsi.model.ObatBhp;
import com.herinoid.rsi.model.ObatOperasi;
import com.herinoid.rsi.model.ResepPulang;
import com.herinoid.rsi.model.ReturObat;
import com.herinoid.rsi.model.TarifKamar;
import com.herinoid.rsi.model.TarifObat;
import com.herinoid.rsi.model.TarifOperasi;
import com.herinoid.rsi.model.TarifRawatInap;
import com.herinoid.rsi.util.Kelas;
import com.herinoid.rsi.util.Utils;
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Hewrei
 */
public class BillingRanapDAO {

    private static Connection koneksi = koneksiDB.condb();

    private static TarifKamar getTarifKamarInap(String kelas) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarifKamar kamarInap = new TarifKamar();
        try {
            ps = koneksi.prepareStatement("SELECT * FROM kamar WHERE kelas = ? LIMIT 1");
            ps.setString(1, kelas);
            rs = ps.executeQuery();
            while (rs.next()) {
                kamarInap.setTarif(rs.getDouble("trf_kamar"));
                kamarInap.setKelas(rs.getString("kelas"));
                kamarInap.setKodeBangsal(rs.getString("kd_bangsal"));
                kamarInap.setKodeKamar(rs.getString("kd_kamar"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            }
        }
        return kamarInap;
    }

    private static TarifRawatInap getTarifRawatInap(String kelas, String namaPerawatan) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarifRawatInap rawatInap = new TarifRawatInap();
        try {
            ps = koneksi.prepareStatement("SELECT (material+bhp+tarif_tindakandr+tarif_tindakanpr+kso+menejemen) AS total,kd_jenis_prw,nm_perawatan,kelas FROM jns_perawatan_inap WHERE kelas = ? AND nm_perawatan = ? LIMIT 1");
            ps.setString(1, kelas);
            ps.setString(2, namaPerawatan);
            rs = ps.executeQuery();
            while (rs.next()) {
                rawatInap.setTarif(rs.getDouble("total"));
                rawatInap.setKelas(rs.getString("kelas"));
                rawatInap.setKodeTarif(rs.getString("kd_jenis_prw"));
                rawatInap.setNamaTarif(rs.getString("nm_perawatan"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            }
        }
        return rawatInap;
    }

    private static TarifOperasi getTarifOperasi(String kelas, String namaPerawatan) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        TarifOperasi operasi = new TarifOperasi();
        try {
            ps = koneksi.prepareStatement("SELECT (operator1+operator2+operator3+asisten_operator1+asisten_operator2+asisten_operator3+instrumen+dokter_anak+perawaat_resusitas+dokter_anestesi+asisten_anestesi+asisten_anestesi2"
                    + "+ bidan+bidan2+bidan3+perawat_luar+sewa_ok+alat+akomodasi+bagian_rs+omloop+omloop2+omloop3+omloop4+omloop5+sarpras +dokter_pjanak+dokter_umum) AS total,kode_paket,nm_perawatan,kelas "
                    + "FROM paket_operasi WHERE kelas = ? and nm_perawatan = ?");
            ps.setString(1, kelas);
            ps.setString(2, namaPerawatan);
            rs = ps.executeQuery();
            while (rs.next()) {
                operasi.setTarif(rs.getDouble("total"));
                operasi.setKelas(rs.getString("kelas"));
                operasi.setNamaPaket(rs.getString("nm_perawatan"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            }
        }
        return operasi;
    }

    public static BillingRanap getRegistrasiPasien(String norawat) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        BillingRanap biling = new BillingRanap();
        try {
            ps = koneksi.prepareStatement("SELECT kamar_inap.tgl_masuk,kamar_inap.tgl_keluar,kamar_inap.jam_masuk,kamar_inap.jam_keluar,reg_periksa.`no_rawat`,pasien.`no_rkm_medis`,pasien.`nm_pasien`,pasien.`alamat`,kamar.`kelas`,bangsal.`nm_bangsal`,reg_periksa.kd_pj,nota_inap.no_nota FROM reg_periksa JOIN pasien ON reg_periksa.`no_rkm_medis`=pasien.`no_rkm_medis` "
                    + "JOIN kamar_inap ON reg_periksa.`no_rawat`= kamar_inap.`no_rawat` JOIN kamar ON kamar_inap.`kd_kamar`=kamar.`kd_kamar` JOIN bangsal ON kamar.`kd_bangsal`=bangsal.`kd_bangsal` LeFT JOIN nota_inap on nota_inap.`no_rawat`= reg_periksa.`no_rawat` "
                    + "WHERE reg_periksa.no_rawat= ?");
            ps.setString(1, norawat);
            rs = ps.executeQuery();
            while (rs.next()) {
                biling.setNoRawat(rs.getString("no_rawat"));
                biling.setNorm(rs.getString("no_rkm_medis"));
                biling.setNamaPasien(rs.getString("nm_pasien"));
                biling.setAlamat(rs.getString("alamat"));
                biling.setKelas(rs.getString("kelas"));
                biling.setNamaBangsal(rs.getString("nm_bangsal"));
                biling.setKdPj(rs.getString("kd_pj"));
                biling.setNoNota(rs.getString("no_nota"));
                String tglKeluar = rs.getString("tgl_keluar")==null?"":Utils.formatTanggal(rs.getDate("tgl_keluar"));
                String jamKeluar = rs.getString("jam_keluar")==null?"":rs.getString("jam_keluar");
                biling.setTglPerawatan(Utils.formatTanggal(rs.getDate("tgl_masuk")) + " " + rs.getString("jam_masuk") + " s/d " + tglKeluar + " " + jamKeluar);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            }
        }
        return biling;
    }

    public static List<DokterRanap> getDokterRanap(String norawat) {
        List<DokterRanap> dokters = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select dokter.kd_dokter, dokter.nm_dokter from rawat_inap_dr "
                    + "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "
                    + "where no_rawat=? group by rawat_inap_dr.kd_dokter");
            prep.setString(1, norawat);
            res = prep.executeQuery();
            while (res.next()) {
                DokterRanap b = new DokterRanap();
                b.setKodeDokter(res.getString("kd_dokter"));
                b.setNamaDokter(res.getString("nm_dokter"));
                dokters.add(b);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return dokters;
    }

    public static List<DokterRajal> getDokterRajal(String norawat) {
        List<DokterRajal> dokters = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select dokter.kd_dokter,dokter.nm_dokter from rawat_jl_dr "
                    + "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "
                    + "where no_rawat=? group by rawat_jl_dr.kd_dokter");
            prep.setString(1, norawat);
            res = prep.executeQuery();
            while (res.next()) {
                DokterRajal r = new DokterRajal();
                r.setKodeDokter(res.getString("kd_dokter"));
                r.setNamaDokter(res.getString("nm_dokter"));
                dokters.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return dokters;
    }

    public static List<KategoriPerawatan> getRawatKategori() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<KategoriPerawatan> kategoris = new LinkedList<>();
        try {
            ps = koneksi.prepareStatement("SELECT kd_kategori, nm_kategori FROM kategori_perawatan");
            rs = ps.executeQuery();
            while (rs.next()) {
                KategoriPerawatan kategori = new KategoriPerawatan();
                kategori.setKodeKategori(rs.getString("kd_kategori"));
                kategori.setNamaKategori(rs.getString("nm_kategori"));
                kategoris.add(kategori);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            }
        }
        return kategoris;
    }

    public static List<ObatOperasi> getObatOperasi(String norawat) {
        List<ObatOperasi> obats = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select obatbhp_ok.nm_obat,beri_obat_operasi.hargasatuan,beri_obat_operasi.jumlah, "
                    + "(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "
                    + "from obatbhp_ok inner join beri_obat_operasi "
                    + "on beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat where "
                    + "beri_obat_operasi.no_rawat=? group by beri_obat_operasi.kd_obat");
            prep.setString(1, norawat);
            res = prep.executeQuery();
            while (res.next()) {
                ObatOperasi r = new ObatOperasi();
                r.setKdObat(res.getString("kode_obat"));
                r.setNamaObat(res.getString("nm_obat"));
                r.setHargaSatuan(res.getDouble("hargasatuan"));
                r.setJumlah(res.getDouble("jumlah"));
                r.setTotal(res.getDouble("total"));
                obats.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return obats;
    }

    public static List<ReturObat> getReturObat(String noreturJual) {
        List<ReturObat> obats = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detreturjual.h_retur, "
                    + "sum(detreturjual.jml_retur * -1) as jml, "
                    + "sum(detreturjual.subtotal * -1) as ttl from detreturjual inner join databarang inner join returjual "
                    + "on detreturjual.kode_brng=databarang.kode_brng "
                    + "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual like ? group by databarang.kode_brng");
            prep.setString(1, noreturJual);
            res = prep.executeQuery();
            while (res.next()) {
                ReturObat r = new ReturObat();
                r.setKdObat(res.getString("kode_brng"));
                r.setNamaObat(res.getString("nama_brng"));
                r.setHargaSatuan(res.getDouble("h_retur"));
                r.setJumlah(res.getDouble("jml"));
                r.setTotal(res.getDouble("ttl"));
                obats.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return obats;
    }

    public static List<ObatBhp> getObatBhp(String noRawat) {
        List<ObatBhp> obats = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,jenis.nama,detail_pemberian_obat.biaya_obat,"
                    + "sum(detail_pemberian_obat.jml) as jml,sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah) as tambahan,"
                    + "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as total "
                    + "from detail_pemberian_obat inner join databarang inner join jenis "
                    + "on detail_pemberian_obat.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns where "
                    + "detail_pemberian_obat.no_rawat=? and detail_pemberian_obat.status like 'Ranap' group by databarang.kode_brng,detail_pemberian_obat.biaya_obat order by jenis.nama");
            prep.setString(1, noRawat);
            res = prep.executeQuery();
            while (res.next()) {
                ObatBhp r = new ObatBhp();
                r.setKodeBarang(res.getString("kode_brng"));
                r.setNamaBarang(res.getString("nama_brng"));
                r.setJenisBarang(res.getString("nama"));
                r.setBiaya(res.getDouble("biaya_obat"));
                r.setTotal(res.getDouble("tambahan"));
                r.setJumlah(res.getDouble("jml"));
                r.setTotal(res.getDouble("total"));
                obats.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return obats;
    }

    public static KamarInap getKamarInap(String noreturJual) {
        KamarInap inap = null;
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select kamar_inap.no_rawat,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,"
                    + "kamar_inap.lama,kamar_inap.ttl_biaya as total,kamar_inap.tgl_masuk,kamar.kelas, "
                    + "kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00',current_date(),kamar_inap.tgl_keluar) as tgl_keluar,"
                    + "if(kamar_inap.jam_keluar='00:00:00',current_time(),kamar_inap.jam_keluar) as jam_keluar,bangsal.kd_bangsal "
                    + "from kamar_inap inner join bangsal inner join kamar "
                    + "on kamar_inap.kd_kamar=kamar.kd_kamar "
                    + "and kamar.kd_bangsal=bangsal.kd_bangsal where "
                    + "kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk,kamar_inap.kd_kamar");
            prep.setString(1, noreturJual);
            res = prep.executeQuery();
            while (res.next()) {
                inap = new KamarInap();
                inap.setNoRawat(res.getString("no_rawat"));
                inap.setKodeKamar(res.getString("kd_bangsal"));
                inap.setNamaKamar(res.getString("nm_bangsal"));
                inap.setKelas(res.getString("kelas"));
                inap.setTarif(res.getDouble("trf_kamar"));
                inap.setLama(res.getDouble("lama"));
                inap.setTotal(res.getDouble("total"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return inap;
    }

    public static List<BiayaTambahan> getBiayaTambahan(String norawat) {
        List<BiayaTambahan> tambahans = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select nama_biaya, besar_biaya from tambahan_biaya where no_rawat=?");
            prep.setString(1, norawat);
            res = prep.executeQuery();
            while (res.next()) {
                BiayaTambahan tambahan = new BiayaTambahan();
                tambahan.setNamaBiaya(res.getString("nama_biaya"));
                tambahan.setBiaya(res.getDouble("besar_biaya"));
                tambahans.add(tambahan);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tambahans;
    }

    public static List<ResepPulang> getResepPulang(String noreturJual) {
        List<ResepPulang> resepPulangs = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_pulang.harga,"
                    + "resep_pulang.jml_barang,resep_pulang.dosis,resep_pulang.total "
                    + "from resep_pulang inner join databarang "
                    + "on resep_pulang.kode_brng=databarang.kode_brng where "
                    + "resep_pulang.no_rawat=? order by databarang.nama_brng");
            prep.setString(1, noreturJual);
            res = prep.executeQuery();
            while (res.next()) {
                ResepPulang r = new ResepPulang();
                r.setKdObat(res.getString("kode_brng"));
                r.setNamaObat(res.getString("nama_brng"));
                r.setHargaSatuan(res.getDouble("harga"));
                r.setJumlah(res.getDouble("jml_barang"));
                r.setTotal(res.getDouble("total"));
                resepPulangs.add(r);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return resepPulangs;
    }

    public static List<BiayaTambahan> getPotonganBiaya(String norawat) {
        List<BiayaTambahan> potongans = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select nama_pengurangan, besar_pengurangan from pengurangan_biaya where no_rawat=?");
            prep.setString(1, norawat);
            res = prep.executeQuery();
            while (res.next()) {
                BiayaTambahan tambahan = new BiayaTambahan();
                tambahan.setNamaBiaya(res.getString("nama_pengurangan"));
                tambahan.setBiaya(res.getDouble("besar_pengurangan"));
                potongans.add(tambahan);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return potongans;
    }

    public static List<BiayaRalanDokter> getBiayaRalanDokter(String norawat, String kdKategori) {
        List<BiayaRalanDokter> biayaRalanDokters = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select dokter.nm_dokter,jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,rawat_jl_dr.kd_dokter,rawat_jl_dr.biaya_rawat as total_byrdr,count(rawat_jl_dr.kd_jenis_prw) as jml, "
                    + "sum(rawat_jl_dr.biaya_rawat) as biaya,"
                    + "sum(rawat_jl_dr.bhp) as totalbhp,"
                    + "(sum(rawat_jl_dr.material)+sum(rawat_jl_dr.menejemen)+sum(rawat_jl_dr.kso)) as totalmaterial,"
                    + "rawat_jl_dr.tarif_tindakandr,"
                    + "sum(rawat_jl_dr.tarif_tindakandr) as totaltarif_tindakandr  "
                    + "from rawat_jl_dr inner join jns_perawatan inner join kategori_perawatan inner join dokter "
                    + "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "
                    + "jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori and rawat_jl_dr.kd_dokter=dokter.kd_dokter where "
                    + "rawat_jl_dr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_jl_dr.kd_jenis_prw");
            prep.setString(1, norawat);
            prep.setString(2, "KP01");
            res = prep.executeQuery();
            while (res.next()) {
                BiayaRalanDokter biaya = new BiayaRalanDokter();
                biaya.setKodePerawatan(res.getString("kd_jenis_prw"));
                biaya.setNamaPerawatan(res.getString("nm_perawatan"));
                biaya.setKodeDokter(res.getString("kd_dokter"));
                biaya.setNamaDokter(res.getString("nm_dokter"));
                biaya.setBiayaRawat(res.getDouble("total_byrdr"));
                biaya.setJumlah(res.getDouble("jml"));
                biaya.setBiaya(res.getDouble("biaya"));
                biaya.setBhp(res.getDouble("totalbhp"));
                biaya.setMaterial(res.getDouble("totalmaterial"));
                biaya.setTindakanDOkter(res.getDouble("tarif_tindakandr"));
                biaya.setTotal(res.getDouble("totaltarif_tindakandr"));
                biayaRalanDokters.add(biaya);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return biayaRalanDokters;
    }

    public static List<BiayaRalanDokterPerawat> getBiayaRalanDokterPerawat(String norawat, String kdKategori) {
        List<BiayaRalanDokterPerawat> biayaRalanDokterPerawats = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select dokter.kd_dokter,dokter.nm_dokter,jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,rawat_jl_drpr.biaya_rawat as total_byrdr,count(rawat_jl_drpr.kd_jenis_prw) as jml, "
                    + "sum(rawat_jl_drpr.biaya_rawat) as biaya,"
                    + "sum(rawat_jl_drpr.bhp) as totalbhp,"
                    + "(sum(rawat_jl_drpr.material)+sum(rawat_jl_drpr.menejemen)+sum(rawat_jl_drpr.kso)) as totalmaterial,"
                    + "rawat_jl_drpr.tarif_tindakandr,"
                    + "sum(rawat_jl_drpr.tarif_tindakanpr) as totaltarif_tindakanpr,"
                    + "sum(rawat_jl_drpr.tarif_tindakandr) as totaltarif_tindakandr  "
                    + "from rawat_jl_drpr inner join jns_perawatan inner join kategori_perawatan inner join dokter "
                    + "on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "
                    + "jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori and rawat_jl_drpr.kd_dokter=dokter.kd_dokter where "
                    + "rawat_jl_drpr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_jl_drpr.kd_jenis_prw");
            prep.setString(1, norawat);
            prep.setString(2, "KP01");
            res = prep.executeQuery();
            while (res.next()) {
                BiayaRalanDokterPerawat biaya = new BiayaRalanDokterPerawat();
                biaya.setKodePerawatan(res.getString("kd_jenis_prw"));
                biaya.setNamaPerawatan(res.getString("nm_perawatan"));
                biaya.setKodeDokter(res.getString("kd_dokter"));
                biaya.setNamaDokter(res.getString("nm_dokter"));
                biaya.setBiayaRawat(res.getDouble("total_byrdr"));
                biaya.setJumlah(res.getDouble("jml"));
                biaya.setBiaya(res.getDouble("biaya"));
                biaya.setBhp(res.getDouble("totalbhp"));
                biaya.setMaterial(res.getDouble("totalmaterial"));
                biaya.setBiayaTindakan(res.getDouble("tarif_tindakandr"));
                biaya.setTotalBiayaRawat(res.getDouble("totaltarif_tindakanpr"));
                biaya.setTotalBiayaTindakan(res.getDouble("totaltarif_tindakandr"));
                biaya.setTotal(res.getDouble("totaltarif_tindakandr"));
                biayaRalanDokterPerawats.add(biaya);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return biayaRalanDokterPerawats;
    }

    public static List<BiayaRanapDokter> getBiayaRanapDokter(String norawat, String kdKategori) {
        List<BiayaRanapDokter> biayaRanapDokters = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select dokter.kd_dokter,dokter.nm_dokter,jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,rawat_inap_dr.biaya_rawat as total_byrdr,count(rawat_inap_dr.kd_jenis_prw) as jml, "
                    + "sum(rawat_inap_dr.biaya_rawat) as biaya,"
                    + "sum(rawat_inap_dr.bhp) as totalbhp,"
                    + "(sum(rawat_inap_dr.material)+sum(rawat_inap_dr.menejemen)+sum(rawat_inap_dr.kso)) as totalmaterial,"
                    + "rawat_inap_dr.tarif_tindakandr,"
                    + "sum(rawat_inap_dr.tarif_tindakandr) as totaltarif_tindakandr "
                    + "from rawat_inap_dr inner join jns_perawatan_inap inner join kategori_perawatan inner join dokter "
                    + "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and "
                    + "jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori and rawat_inap_dr.kd_dokter=dokter.kd_dokter where "
                    + "rawat_inap_dr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_inap_dr.kd_jenis_prw");
            prep.setString(1, norawat);
            prep.setString(2, "KP02");
            res = prep.executeQuery();
            while (res.next()) {
                BiayaRanapDokter biaya = new BiayaRanapDokter();
                biaya.setKodePerawatan(res.getString("kd_jenis_prw"));
                biaya.setNamaPerawatan(res.getString("nm_perawatan"));
                biaya.setKodeDokter(res.getString("kd_dokter"));
                biaya.setNamaDokter(res.getString("nm_dokter"));
                biaya.setBiayaRawat(res.getDouble("total_byrdr"));
                biaya.setJumlah(res.getDouble("jml"));
                biaya.setBiaya(res.getDouble("biaya"));
                biaya.setBhp(res.getDouble("totalbhp"));
                biaya.setMaterial(res.getDouble("totalmaterial"));
                biaya.setBiayaTindakan(res.getDouble("tarif_tindakandr"));
//                biaya.setTotalBiayaRawat(res.getDouble("totaltarif_tindakanpr"));
                biaya.setTotalBiayaTindakan(res.getDouble("totaltarif_tindakandr"));
                biaya.setTotal(res.getDouble("totaltarif_tindakandr"));
                biayaRanapDokters.add(biaya);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return biayaRanapDokters;
    }

    public static List<BiayaRanapDokterPerawat> getBiayaRanapDokterPerawat(String norawat, String kdKategori) {
        List<BiayaRanapDokterPerawat> biayaRanapDokterPerawats = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select dokter.nm_dokter,jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,rawat_inap_drpr.biaya_rawat as total_byrdr,count(rawat_inap_drpr.kd_jenis_prw) as jml, "
                    + "sum(rawat_inap_drpr.biaya_rawat) as biaya,"
                    + "sum(rawat_inap_drpr.bhp) as totalbhp,"
                    + "(sum(rawat_inap_drpr.material)+sum(rawat_inap_drpr.menejemen)+sum(rawat_inap_drpr.kso)) as totalmaterial,"
                    + "rawat_inap_drpr.tarif_tindakandr,"
                    + "sum(rawat_inap_drpr.tarif_tindakanpr) as totaltarif_tindakanpr, "
                    + "sum(rawat_inap_drpr.tarif_tindakandr) as totaltarif_tindakandr "
                    + "from rawat_inap_drpr inner join jns_perawatan_inap inner join kategori_perawatan inner join dokter "
                    + "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and "
                    + "jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori and rawat_inap_drpr.kd_dokter=dokter.kd_dokter where "
                    + "rawat_inap_drpr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_inap_drpr.kd_jenis_prw");
            prep.setString(1, norawat);
            prep.setString(2, "KP02");
            res = prep.executeQuery();
            while (res.next()) {
                BiayaRanapDokterPerawat biaya = new BiayaRanapDokterPerawat();
                biaya.setKodePerawatan(res.getString("kd_jenis_prw"));
                biaya.setNamaPerawatan(res.getString("nm_perawatan"));
                biaya.setKodeDokter(res.getString("kd_dokter"));
                biaya.setNamaDokter(res.getString("nm_dokter"));
                biaya.setBiayaRawat(res.getDouble("total_byrdr"));
                biaya.setJumlah(res.getDouble("jml"));
                biaya.setBiaya(res.getDouble("biaya"));
                biaya.setBhp(res.getDouble("totalbhp"));
                biaya.setMaterial(res.getDouble("totalmaterial"));
                biaya.setBiayaTindakan(res.getDouble("tarif_tindakandr"));
                biaya.setTotalBiayaRawat(res.getDouble("totaltarif_tindakanpr"));
                biaya.setTotalBiayaTindakan(res.getDouble("totaltarif_tindakandr"));
                biaya.setTotal(res.getDouble("totaltarif_tindakandr"));
                biayaRanapDokterPerawats.add(biaya);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return biayaRanapDokterPerawats;
    }

    public static List<BiayaRalanPerawat> getBiayaRalanPerawat(String norawat, String kdKategori) {
        List<BiayaRalanPerawat> biayaRalanPerawats = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,jns_perawatan.total_byrpr,count(jns_perawatan.nm_perawatan) as jml, "
                    + "jns_perawatan.total_byrpr*count(jns_perawatan.nm_perawatan) as biaya "
                    + "from rawat_jl_pr inner join jns_perawatan inner join kategori_perawatan  "
                    + "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw  and "
                    + "jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori where "
                    + "rawat_jl_pr.no_rawat=? and kategori_perawatan.kd_kategori=? group by rawat_jl_pr.kd_jenis_prw");
            prep.setString(1, norawat);
            prep.setString(2, "KP01");
            res = prep.executeQuery();
            while (res.next()) {
                BiayaRalanPerawat biaya = new BiayaRalanPerawat();
                biaya.setKodeRawat(res.getString("kd_jenis_prw"));
                biaya.setNamaRawat(res.getString("nm_perawatan"));
                biaya.setJumlah(res.getDouble("jml"));
                biaya.setBiaya(res.getDouble("biaya"));
                biaya.setTotal(res.getDouble("total_byrpr"));
                biayaRalanPerawats.add(biaya);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return biayaRalanPerawats;
    }

    public static List<BiayaRanapPerawat> getBiayaRanapPerawat(String norawat, String kdKategori) {
        List<BiayaRanapPerawat> biayaRanapPerawats = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,jns_perawatan_inap.total_byrpr,count(jns_perawatan_inap.nm_perawatan) as jml, "
                    + "jns_perawatan_inap.total_byrpr*count(jns_perawatan_inap.nm_perawatan) as biaya "
                    + "from rawat_inap_pr inner join jns_perawatan_inap  inner join kategori_perawatan "
                    + "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw  and "
                    + "jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori where "
                    + "rawat_inap_pr.no_rawat=? and kategori_perawatan.kd_kategori=?  group by rawat_inap_pr.kd_jenis_prw");
            prep.setString(1, norawat);
            prep.setString(2, "KP02");
            res = prep.executeQuery();
            while (res.next()) {
                BiayaRanapPerawat biaya = new BiayaRanapPerawat();
                biaya.setKodeRawat(res.getString("kd_jenis_prw"));
                biaya.setNamaRawat(res.getString("nm_perawatan"));
                biaya.setJumlah(res.getDouble("jml"));
                biaya.setBiaya(res.getDouble("biaya"));
                biaya.setTotal(res.getDouble("total_byrpr"));
                biayaRanapPerawats.add(biaya);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return biayaRanapPerawats;
    }

    public static List<BiayaOperasi> getBiayaOperasi(String norawat) {
        List<BiayaOperasi> biayaOperasis = new LinkedList<>();
        PreparedStatement prep = null;
        ResultSet res = null;
        try {
            prep = koneksi.prepareStatement("select paket_operasi.kode_paket,paket_operasi.nm_perawatan,(operasi.biayaoperator1+operasi.biayaoperator2+"
                    + "operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+"
                    + "operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+"
                    + "operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+"
                    + "operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"
                    + "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+"
                    + "operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+"
                    + "operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+"
                    + "operasi.biaya_dokter_umum) as biaya,operasi.biayaoperator1,"
                    + "operasi.biayaoperator2,operasi.biayaoperator3,operasi.biayaasisten_operator1,operasi.biayaasisten_operator2,operasi.biayaasisten_operator3,"
                    + "operasi.biayainstrumen,operasi.biayadokter_anak,operasi.biayaperawaat_resusitas,"
                    + "operasi.biayadokter_anestesi,operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2,operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3,operasi.biayaperawat_luar,"
                    + "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,"
                    + "operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum "
                    + "from operasi inner join paket_operasi "
                    + "on operasi.kode_paket=paket_operasi.kode_paket where "
                    + "operasi.no_rawat=? and operasi.status like ?");
            prep.setString(1, norawat);
            prep.setString(2, "%Ranap%");
            res = prep.executeQuery();
            while (res.next()) {
                BiayaOperasi biaya = new BiayaOperasi();
                biaya.setKodePerawatan(res.getString("kode_paket"));
                biaya.setNamaPerawatan(res.getString("nm_perawatan"));
                biaya.setBiaya(res.getDouble("biaya"));
                biaya.setBiayaOpr1(res.getDouble("biayaoperator1"));
                biaya.setBiayaOpr3(res.getDouble("biayaoperator2"));
                biaya.setBiayaAsistenOpr1(res.getDouble("biayaasisten_operator1"));
                biaya.setBiayaAsistenOpr2(res.getDouble("biayaasisten_operator2"));
                biaya.setBiayaAsistenOpr3(res.getDouble("biayaasisten_operator3"));
                biaya.setBiayaInstrumen(res.getDouble("biayainstrumen"));
                biaya.setBiayaDokterAnak(res.getDouble("biayadokter_anak"));
                biaya.setBiayaPerawatResusitas(res.getDouble("biayaperawaat_resusitas"));
                biaya.setBiayaDokterAnastesi(res.getDouble("biayadokter_anestesi"));
                biaya.setBiayaAsistenAnastesi1(res.getDouble("biayaasisten_anestesi"));
                biaya.setBiayaAsistenAnastesi2(res.getDouble("biayaasisten_anestesi2"));
                biaya.setBiayaBidan1(res.getDouble("biayabidan"));
                biaya.setBiayaBidan2(res.getDouble("biayabidan2"));
                biaya.setBiayaBidan3(res.getDouble("biayabidan3"));
                biaya.setBiayaPerawatLuar(res.getDouble("biayaperawat_luar"));
                biaya.setBiayaAlat(res.getDouble("biayaalat"));
                biaya.setBiayaSewaOk(res.getDouble("biayasewaok"));
                biaya.setBiayaAkomodasi(res.getDouble("akomodasi"));
                biaya.setBagianRs(res.getDouble("bagian_rs"));
                biaya.setBiayaOmLoop1(res.getDouble("biaya_omloop"));
                biaya.setBiayaOmLoop2(res.getDouble("biaya_omloop2"));
                biaya.setBiayaOmLoop3(res.getDouble("biaya_omloop3"));
                biaya.setBiayaOmLoop4(res.getDouble("biaya_omloop4"));
                biaya.setBiayaOmLoop5(res.getDouble("biaya_omloop5"));
                biaya.setBiayaSarpras(res.getDouble("biayasarpras"));
                biaya.setBiayaDokterPJAnak(res.getDouble("biaya_dokter_pjanak"));
                biaya.setBiayaDokterUmum(res.getDouble("biaya_dokter_umum"));
                biayaOperasis.add(biaya);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (res != null) {

                    res.close();

                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return biayaOperasis;
    }

    public static List<BillingSimulator> getBilingKelasSekarang(String noRawat, String kelas) {
        String kdKategori = "";
        List<BillingSimulator> kelasSekarangList = new LinkedList<>();
        List<ObatOperasi> obatOperasis = getObatOperasi(noRawat);
        List<ReturObat> returObats = getReturObat(noRawat);
        KamarInap kamar = getKamarInap(noRawat);
        TarifKamar tarifKamar = getTarifKamarInap(kelas);

        String kelasSekarang = kamar.getKelas();
        List<BiayaTambahan> biayaTambahans = getBiayaTambahan(noRawat);
        List<ResepPulang> resepPulangs = getResepPulang(noRawat);
        List<BiayaTambahan> potonganBiayas = getPotonganBiaya(noRawat);
//        List<KategoriPerawatan> katgories = getRawatKategori();

        List<BiayaRalanDokter> biayaRalanDokters = getBiayaRalanDokter(noRawat, kdKategori);
        List<BiayaRalanDokterPerawat> biayaRalanDokterPerawats = getBiayaRalanDokterPerawat(noRawat, kdKategori);
        List<BiayaRanapDokter> biayaRanapDokters = getBiayaRanapDokter(noRawat, kdKategori);
        List<BiayaRanapDokterPerawat> biayaRanapDokterPerawats = getBiayaRanapDokterPerawat(noRawat, kdKategori);
        List<BiayaRalanPerawat> biayaRalanPerawats = getBiayaRalanPerawat(noRawat, kdKategori);
        List<BiayaRanapPerawat> biayaRanapPerawats = getBiayaRanapPerawat(noRawat, kdKategori);
        List<BiayaOperasi> biayaOperasis = getBiayaOperasi(noRawat);
        List<ObatBhp> obatBhpes = getObatBhp(noRawat);
        int kelasNow = 1;
        if (kamar != null) {
            BillingSimulator bilingKamar = new BillingSimulator();
            bilingKamar.setNomer(kelasNow);
            bilingKamar.setUraian(kamar.getNamaKamar());
            bilingKamar.setTagihan(kamar.getTotal());
            bilingKamar.setJumlah(kamar.getLama());
            bilingKamar.setPembanding(tarifKamar.getTarif() * kamar.getLama());
            if (Kelas.isNaik(kelasSekarang, kelas)) {
                bilingKamar.setIurPasien(bilingKamar.getPembanding() - bilingKamar.getTagihan());
            } else {
                bilingKamar.setIurPasien(bilingKamar.getTagihan() - bilingKamar.getPembanding());
            }
            kelasSekarangList.add(bilingKamar);
        }

        if (obatBhpes != null) {
            for (ObatBhp d : obatBhpes) {
                kelasNow++;
                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKodeBarang());
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaBarang());
                biling.setTagihan(d.getTotal());
                if (kelas.equals(Kelas.KELAS_TIGA)) {
                    biling.setPembanding((obat.getKelas3() * d.getJumlah()) + d.getTambahan());
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_DUA)) {
                    biling.setPembanding((obat.getKelas2() * d.getJumlah()) + d.getTambahan());
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_SATU)) {
                    biling.setPembanding((obat.getKelas1() * d.getJumlah()) + d.getTambahan());
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_VIP)) {
                    biling.setPembanding((obat.getKelasVip() * d.getJumlah()) + d.getTambahan());
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_VVIP)) {
//                    System.out.println("nama obat = " + obat.getNamaObat() + "kelas vvip = " + obat.getKelasVVIP() + ", jumlah = " + d.getJumlah() + ", tambahan = " + d.getTambahan());
                    biling.setPembanding((obat.getKelasVVIP() * d.getJumlah()) + d.getTambahan());
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                }
                biling.setJumlah(d.getJumlah());
                kelasSekarangList.add(biling);
            }
        }

        if (returObats != null) {
            for (ReturObat d : returObats) {
                kelasNow++;
                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKdObat());
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaObat());
                biling.setTagihan(d.getTotal());
                if (kelas.equals(Kelas.KELAS_TIGA)) {
                    biling.setPembanding((obat.getKelas3() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_DUA)) {
                    biling.setPembanding((obat.getKelas2() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_SATU)) {
                    biling.setPembanding((obat.getKelas1() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_VIP)) {
                    biling.setPembanding((obat.getKelasVip() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_VVIP)) {
                    biling.setPembanding((obat.getKelasVVIP() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                }
                biling.setJumlah(d.getJumlah());
                kelasSekarangList.add(biling);
            }
        }
        if (biayaTambahans != null) {
            for (BiayaTambahan d : biayaTambahans) {
                kelasNow++;
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaBiaya());
                biling.setTagihan(d.getBiaya());
                biling.setPembanding(d.getBiaya());
                biling.setJumlah(1);
                biling.setIurPasien(0);
                kelasSekarangList.add(biling);
            }
        }
        if (obatOperasis != null) {
            for (ObatOperasi d : obatOperasis) {
                kelasNow++;
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaObat());
                biling.setTagihan(d.getTotal());
                biling.setJumlah(d.getJumlah());
                biling.setPembanding(d.getTotal());
                biling.setIurPasien(0);
                kelasSekarangList.add(biling);
            }
        }
        if (potonganBiayas != null) {
            for (BiayaTambahan d : potonganBiayas) {
                kelasNow++;
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaBiaya());
                biling.setTagihan(d.getBiaya());
                biling.setPembanding(d.getBiaya());
                biling.setIurPasien(0);
                biling.setJumlah(1);
                kelasSekarangList.add(biling);
            }
        }
        if (resepPulangs != null) {
            for (ResepPulang d : resepPulangs) {
                kelasNow++;
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKdObat());
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaObat());
                biling.setTagihan(d.getTotal());
                if (kelas.equals(Kelas.KELAS_TIGA)) {
                    biling.setPembanding((obat.getKelas3() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_DUA)) {
                    biling.setPembanding((obat.getKelas2() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_SATU)) {
                    biling.setPembanding((obat.getKelas1() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_VIP)) {
                    biling.setPembanding((obat.getKelasVip() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                } else if (kelas.equals(Kelas.KELAS_VVIP)) {
                    biling.setPembanding((obat.getKelasVVIP() * d.getJumlah()));
                    if (Kelas.isNaik(kelasSekarang, kelas)) {
                        biling.setIurPasien(biling.getPembanding() - biling.getTagihan());
                    } else {
                        biling.setIurPasien(biling.getTagihan() - biling.getPembanding());
                    }
                }
                biling.setJumlah(d.getJumlah());
                kelasSekarangList.add(biling);
            }
        }
        if (biayaRalanDokters != null) {
            for (BiayaRalanDokter d : biayaRalanDokters) {
                kelasNow++;
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaDokter());
                biling.setTagihan(d.getTotal());
                biling.setJumlah(d.getJumlah());
                biling.setPembanding(d.getTotal());
                biling.setIurPasien(0);
                kelasSekarangList.add(biling);
            }
        }
        if (biayaRanapDokters != null) {
            for (BiayaRanapDokter d : biayaRanapDokters) {
                kelasNow++;
                TarifRawatInap ranap = getTarifRawatInap(kelas, d.getNamaPerawatan());
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaDokter());
                biling.setTagihan(d.getTotal());
                biling.setPembanding(ranap.getTarif());
                biling.setJumlah(d.getJumlah());
                kelasSekarangList.add(biling);
            }
        }

        if (biayaRalanDokterPerawats != null) {
            for (BiayaRalanDokterPerawat d : biayaRalanDokterPerawats) {
                kelasNow++;
//                TarifRawatInap ranap = getTarifRawatInap(kelas, d.getNamaPerawatan());
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaDokter());
                biling.setTagihan(d.getTotal());
                biling.setJumlah(d.getJumlah());
                biling.setPembanding(d.getTotal());
                biling.setIurPasien(0);
                biling.setJumlah(d.getJumlah());
                kelasSekarangList.add(biling);
            }
        }
        if (biayaRanapDokterPerawats != null) {
            for (BiayaRanapDokterPerawat d : biayaRanapDokterPerawats) {
                kelasNow++;
                TarifRawatInap ranap = getTarifRawatInap(kelas, d.getNamaPerawatan());
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaDokter());
                biling.setTagihan(d.getTotal());
                biling.setPembanding(ranap.getTarif());
                biling.setIurPasien(0);
                biling.setJumlah(d.getJumlah());
                kelasSekarangList.add(biling);
            }
        }
        if (biayaRalanPerawats != null) {
            for (BiayaRalanPerawat d : biayaRalanPerawats) {
                kelasNow++;
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaRawat());
                biling.setTagihan(d.getTotal());
                biling.setJumlah(d.getJumlah());
                kelasSekarangList.add(biling);
            }
        }
        if (biayaRanapPerawats != null) {
            for (BiayaRanapPerawat d : biayaRanapPerawats) {
                kelasNow++;
                TarifRawatInap ranap = getTarifRawatInap(kelas, d.getNamaRawat());
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaRawat());
                biling.setTagihan(d.getTotal());
                biling.setPembanding(ranap.getTarif());
                biling.setJumlah(d.getJumlah());
                kelasSekarangList.add(biling);
            }
        }
        if (biayaOperasis != null) {
            for (BiayaOperasi d : biayaOperasis) {
                kelasNow++;
                TarifOperasi operasi = getTarifOperasi(kelas,d.getNamaPerawatan());
                BillingSimulator biling = new BillingSimulator();
                biling.setNomer(kelasNow);
                biling.setUraian(d.getNamaPerawatan());
                biling.setTagihan(d.getBiaya());
                biling.setJumlah(1);
                biling.setPembanding(operasi.getTarif());
                biling.setIurPasien(0);
                kelasSekarangList.add(biling);
            }
        }
        kelasSekarangList.sort(Comparator.comparingInt(BillingSimulator::getNomer));
        return kelasSekarangList;
    }

//    public static List<BillingSimulator> getBilingKelasAtasnya(String noRawat) {
//        BillingRanap bill = getRegistrasiPasien(noRawat);
//        String kelasAtasnya = Kelas.getKelasAtasnya(bill.getKelas());
//        String kdKategori = "";
//        List<BillingSimulator> kelasSekarangList = new LinkedList<>();
//        List<ObatOperasi> obatOperasis = getObatOperasi(noRawat);
//        List<ReturObat> returObats = getReturObat(noRawat);
//        KamarInap kamar = getKamarInap(noRawat);
//        System.out.println("kelas atasnya " + Kelas.getKelasAtasnya(kamar.getKelas()) + " dan kode bangsal = " + kamar.getKodeKamar());
//        TarifKamar tarifKamar = getTarifKamarInap(Kelas.getKelasAtasnya(kamar.getKelas()));
//        kamar.setTarif(tarifKamar.getTarif());
//        kamar.setTotal(tarifKamar.getTarif() * kamar.getLama());
//        List<BiayaTambahan> biayaTambahans = getBiayaTambahan(noRawat);
//        List<ResepPulang> resepPulangs = getResepPulang(noRawat);
//        List<BiayaTambahan> potonganBiayas = getPotonganBiaya(noRawat);
//
//        List<BiayaRalanDokter> biayaRalanDokters = getBiayaRalanDokter(noRawat, kdKategori);
//        List<BiayaRalanDokterPerawat> biayaRalanDokterPerawats = getBiayaRalanDokterPerawat(noRawat, kdKategori);
//        List<BiayaRanapDokter> biayaRanapDokters = getBiayaRanapDokter(noRawat, kdKategori);
//        List<BiayaRanapDokterPerawat> biayaRanapDokterPerawats = getBiayaRanapDokterPerawat(noRawat, kdKategori);
//        List<BiayaRalanPerawat> biayaRalanPerawats = getBiayaRalanPerawat(noRawat, kdKategori);
//        List<BiayaRanapPerawat> biayaRanapPerawats = getBiayaRanapPerawat(noRawat, kdKategori);
//        List<BiayaOperasi> biayaOperasis = getBiayaOperasi(noRawat);
//        List<ObatBhp> obatBhpes = getObatBhp(noRawat);
//        int kelasNow = 1;
//        if (kamar != null) {
//            BillingSimulator bilingKamar = new BillingSimulator();
//            bilingKamar.setNomer(kelasNow);
//            bilingKamar.setUraian(kamar.getNamaKamar());
//            bilingKamar.setTagihan(kamar.getTotal());
//            bilingKamar.setJumlah(kamar.getLama());
//            kelasSekarangList.add(bilingKamar);
//        }
//
//        if (obatBhpes != null) {
//            for (ObatBhp d : obatBhpes) {
//                kelasNow++;
//                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKodeBarang());
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaBarang());
//                if (kelasAtasnya.equals(Kelas.KELAS_TIGA)) {
//                    biling.setTagihan((obat.getKelas3() * d.getJumlah()) + d.getTambahan());
//                } else if (kelasAtasnya.equals(Kelas.KELAS_DUA)) {
//                    biling.setTagihan((obat.getKelas2() * d.getJumlah()) + d.getTambahan());
//                } else if (kelasAtasnya.equals(Kelas.KELAS_SATU)) {
//                    biling.setTagihan((obat.getKelas1() * d.getJumlah()) + d.getTambahan());
//                } else if (kelasAtasnya.equals(Kelas.KELAS_VIP)) {
//                    biling.setTagihan((obat.getKelasVip() * d.getJumlah()) + d.getTambahan());
//                } else if (kelasAtasnya.equals(Kelas.KELAS_VVIP)) {
//                    biling.setTagihan((obat.getKelasVVIP() * d.getJumlah()) + d.getTambahan());
//                }
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//
//        if (obatOperasis != null) {
//            for (ObatOperasi d : obatOperasis) {
//                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKdObat());
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaObat());
//                if (kelasAtasnya.equals(Kelas.KELAS_TIGA)) {
//                    biling.setTagihan((obat.getKelas3() * d.getJumlah()));
//                } else if (kelasAtasnya.equals(Kelas.KELAS_DUA)) {
//                    biling.setTagihan((obat.getKelas2() * d.getJumlah()));
//                } else if (kelasAtasnya.equals(Kelas.KELAS_SATU)) {
//                    biling.setTagihan((obat.getKelas1() * d.getJumlah()));
//                } else if (kelasAtasnya.equals(Kelas.KELAS_VIP)) {
//                    biling.setTagihan((obat.getKelasVip() * d.getJumlah()));
//                } else if (kelasAtasnya.equals(Kelas.KELAS_VVIP)) {
//                    biling.setTagihan((obat.getKelasVVIP() * d.getJumlah()));
//                }
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//
//        if (returObats != null) {
//            for (ReturObat d : returObats) {
//                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKdObat());
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaObat());
//                if (kelasAtasnya.equals(Kelas.KELAS_TIGA)) {
//                    biling.setTagihan((obat.getKelas3() * d.getJumlah()) * -1);
//                } else if (kelasAtasnya.equals(Kelas.KELAS_DUA)) {
//                    biling.setTagihan((obat.getKelas2() * d.getJumlah()) * -1);
//                } else if (kelasAtasnya.equals(Kelas.KELAS_SATU)) {
//                    biling.setTagihan((obat.getKelas1() * d.getJumlah()) * -1);
//                } else if (kelasAtasnya.equals(Kelas.KELAS_VIP)) {
//                    biling.setTagihan((obat.getKelasVip() * d.getJumlah()) * -1);
//                } else if (kelasAtasnya.equals(Kelas.KELAS_VVIP)) {
//                    biling.setTagihan((obat.getKelasVVIP() * d.getJumlah()) * -1);
//                }
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaTambahans != null) {
//            for (BiayaTambahan d : biayaTambahans) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaBiaya());
//                biling.setTagihan(d.getBiaya());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (potonganBiayas != null) {
//            for (BiayaTambahan d : potonganBiayas) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaBiaya());
//                biling.setTagihan(d.getBiaya());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (resepPulangs != null) {
//            for (ResepPulang d : resepPulangs) {
//                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKdObat());
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaObat());
//                if (kelasAtasnya.equals(Kelas.KELAS_TIGA)) {
//                    biling.setTagihan((obat.getKelas3() * d.getJumlah()));
//                } else if (kelasAtasnya.equals(Kelas.KELAS_DUA)) {
//                    biling.setTagihan((obat.getKelas2() * d.getJumlah()));
//                } else if (kelasAtasnya.equals(Kelas.KELAS_SATU)) {
//                    biling.setTagihan((obat.getKelas1() * d.getJumlah()));
//                } else if (kelasAtasnya.equals(Kelas.KELAS_VIP)) {
//                    biling.setTagihan((obat.getKelasVip() * d.getJumlah()));
//                } else if (kelasAtasnya.equals(Kelas.KELAS_VVIP)) {
//                    biling.setTagihan((obat.getKelasVVIP() * d.getJumlah()));
//                }
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRalanDokters != null) {
//            for (BiayaRalanDokter d : biayaRalanDokters) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaDokter());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRanapDokters != null) {
//            for (BiayaRanapDokter d : biayaRanapDokters) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaDokter());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//
//        if (biayaRalanDokterPerawats != null) {
//            for (BiayaRalanDokterPerawat d : biayaRalanDokterPerawats) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaDokter());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRanapDokterPerawats != null) {
//            for (BiayaRanapDokterPerawat d : biayaRanapDokterPerawats) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaDokter());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRalanPerawats != null) {
//            for (BiayaRalanPerawat d : biayaRalanPerawats) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaRawat());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRanapPerawats != null) {
//            for (BiayaRanapPerawat d : biayaRanapPerawats) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaRawat());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaOperasis != null) {
//            for (BiayaOperasi d : biayaOperasis) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaPerawatan());
//                biling.setTagihan(d.getBiaya());
//                biling.setJumlah(1);
//                kelasSekarangList.add(biling);
//            }
//        }
//        kelasSekarangList.sort(Comparator.comparingInt(BillingSimulator::getNomer));
//        return kelasSekarangList;
//    }
//
//    public static List<BillingSimulator> getBilingKelasBawahnya(String noRawat, String kelas) {
//        String kelasBawahnya = kelas;
//
//        String kdKategori = "";
//        List<BillingSimulator> kelasSekarangList = new LinkedList<>();
//        List<ObatOperasi> obatOperasis = getObatOperasi(noRawat);
//        List<ReturObat> returObats = getReturObat(noRawat);
//        KamarInap kamar = getKamarInap(noRawat);
//        TarifKamar tarifKamar = getTarifKamarInap(kelas);
//        kamar.setTarif(tarifKamar.getTarif());
//        kamar.setTotal(tarifKamar.getTarif() * kamar.getLama());
//        List<BiayaTambahan> biayaTambahans = getBiayaTambahan(noRawat);
//        List<ResepPulang> resepPulangs = getResepPulang(noRawat);
//        List<BiayaTambahan> potonganBiayas = getPotonganBiaya(noRawat);
//        List<BiayaRalanDokter> biayaRalanDokters = getBiayaRalanDokter(noRawat, kdKategori);
//        List<BiayaRalanDokterPerawat> biayaRalanDokterPerawats = getBiayaRalanDokterPerawat(noRawat, kdKategori);
//        List<BiayaRanapDokter> biayaRanapDokters = getBiayaRanapDokter(noRawat, kdKategori);
//        List<BiayaRanapDokterPerawat> biayaRanapDokterPerawats = getBiayaRanapDokterPerawat(noRawat, kdKategori);
//        List<BiayaRalanPerawat> biayaRalanPerawats = getBiayaRalanPerawat(noRawat, kdKategori);
//        List<BiayaRanapPerawat> biayaRanapPerawats = getBiayaRanapPerawat(noRawat, kdKategori);
//        List<BiayaOperasi> biayaOperasis = getBiayaOperasi(noRawat);
//        List<ObatBhp> obatBhpes = getObatBhp(noRawat);
//        int kelasNow = 1;
//        if (kamar != null) {
//            BillingSimulator bilingKamar = new BillingSimulator();
//            bilingKamar.setNomer(kelasNow);
//            bilingKamar.setUraian(kamar.getNamaKamar());
//            bilingKamar.setTagihan(kamar.getTotal());
//            bilingKamar.setJumlah(kamar.getLama());
//            kelasSekarangList.add(bilingKamar);
//        }
//
//        if (obatBhpes != null) {
//            for (ObatBhp d : obatBhpes) {
//                kelasNow++;
//                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKodeBarang());
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaBarang());
//                if (kelasBawahnya.equals(Kelas.KELAS_TIGA)) {
//                    biling.setTagihan((obat.getKelas3() * d.getJumlah()) + d.getTambahan());
//                } else if (kelasBawahnya.equals(Kelas.KELAS_DUA)) {
//                    biling.setTagihan((obat.getKelas2() * d.getJumlah()) + d.getTambahan());
//                } else if (kelasBawahnya.equals(Kelas.KELAS_SATU)) {
//                    biling.setTagihan((obat.getKelas1() * d.getJumlah()) + d.getTambahan());
//                } else if (kelasBawahnya.equals(Kelas.KELAS_VIP)) {
//                    biling.setTagihan((obat.getKelasVip() * d.getJumlah()) + d.getTambahan());
//                } else if (kelasBawahnya.equals(Kelas.KELAS_VVIP)) {
//                    biling.setTagihan((obat.getKelasVVIP() * d.getJumlah()) + d.getTambahan());
//                }
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//
//        if (obatOperasis != null) {
//            for (ObatOperasi d : obatOperasis) {
//                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKdObat());
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaObat());
//                if (kelasBawahnya.equals(Kelas.KELAS_TIGA)) {
//                    biling.setTagihan((obat.getKelas3() * d.getJumlah()));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_DUA)) {
//                    biling.setTagihan((obat.getKelas2() * d.getJumlah()));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_SATU)) {
//                    biling.setTagihan((obat.getKelas1() * d.getJumlah()));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_VIP)) {
//                    biling.setTagihan((obat.getKelasVip() * d.getJumlah()));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_VVIP)) {
//                    biling.setTagihan((obat.getKelasVVIP() * d.getJumlah()));
//                }
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//
//        if (returObats != null) {
//            for (ReturObat d : returObats) {
//                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKdObat());
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaObat());
//                if (kelasBawahnya.equals(Kelas.KELAS_TIGA)) {
//                    biling.setTagihan((obat.getKelas3() * d.getJumlah()) * -1);
//                } else if (kelasBawahnya.equals(Kelas.KELAS_DUA)) {
//                    biling.setTagihan((obat.getKelas2() * d.getJumlah()) * -1);
//                } else if (kelasBawahnya.equals(Kelas.KELAS_SATU)) {
//                    biling.setTagihan((obat.getKelas1() * d.getJumlah()) * -1);
////                }else if(kelasAtasnya.equals(Kelas.KELAS_UTAMA)){
////                    biling.setTagihan((obat.getKelasUtama()* d.getJumlah() * -1));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_VIP)) {
//                    biling.setTagihan((obat.getKelasVip() * d.getJumlah()) * -1);
//                } else if (kelasBawahnya.equals(Kelas.KELAS_VVIP)) {
//                    biling.setTagihan((obat.getKelasVVIP() * d.getJumlah()) * -1);
//                }
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaTambahans != null) {
//            for (BiayaTambahan d : biayaTambahans) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaBiaya());
//                biling.setTagihan(d.getBiaya());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (potonganBiayas != null) {
//            for (BiayaTambahan d : potonganBiayas) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaBiaya());
//                biling.setTagihan(d.getBiaya());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (resepPulangs != null) {
//            for (ResepPulang d : resepPulangs) {
//                TarifObat obat = ObatBhpDao.getTarifObatBhp(d.getKdObat());
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaObat());
//                if (kelasBawahnya.equals(Kelas.KELAS_TIGA)) {
//                    biling.setTagihan((obat.getKelas3() * d.getJumlah()));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_DUA)) {
//                    biling.setTagihan((obat.getKelas2() * d.getJumlah()));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_SATU)) {
//                    biling.setTagihan((obat.getKelas1() * d.getJumlah()));
////                }else if(kelasBawahnya.equals(Kelas.KELAS_UTAMA)){
////                    biling.setTagihan((obat.getKelasUtama()* d.getJumlah()));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_VIP)) {
//                    biling.setTagihan((obat.getKelasVip() * d.getJumlah()));
//                } else if (kelasBawahnya.equals(Kelas.KELAS_VVIP)) {
//                    biling.setTagihan((obat.getKelasVVIP() * d.getJumlah()));
//                }
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRalanDokters != null) {
//            for (BiayaRalanDokter d : biayaRalanDokters) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaDokter());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRanapDokters != null) {
//            for (BiayaRanapDokter d : biayaRanapDokters) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaDokter());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//
//        if (biayaRalanDokterPerawats != null) {
//            for (BiayaRalanDokterPerawat d : biayaRalanDokterPerawats) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaDokter());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRanapDokterPerawats != null) {
//            for (BiayaRanapDokterPerawat d : biayaRanapDokterPerawats) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaDokter());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRalanPerawats != null) {
//            for (BiayaRalanPerawat d : biayaRalanPerawats) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaRawat());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaRanapPerawats != null) {
//            for (BiayaRanapPerawat d : biayaRanapPerawats) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaRawat());
//                biling.setTagihan(d.getTotal());
//                biling.setJumlah(d.getJumlah());
//                kelasSekarangList.add(biling);
//            }
//        }
//        if (biayaOperasis != null) {
//            for (BiayaOperasi d : biayaOperasis) {
//                kelasNow++;
//                BillingSimulator biling = new BillingSimulator();
//                biling.setNomer(kelasNow);
//                biling.setUraian(d.getNamaPerawatan());
//                biling.setTagihan(d.getBiaya());
//                biling.setJumlah(1);
//                kelasSekarangList.add(biling);
//            }
//        }
//        kelasSekarangList.sort(Comparator.comparingInt(BillingSimulator::getNomer));
//        return kelasSekarangList;
//    }
}

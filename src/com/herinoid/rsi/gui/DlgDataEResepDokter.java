/*
  
 */
package com.herinoid.rsi.gui;

import com.herinoid.rsi.dao.ResepDao;
import com.herinoid.rsi.gui.dialog.DialogAddQtyResepDokter;
import com.herinoid.rsi.gui.dialog.DialogRacikanResep;
import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.DataEResep;
import com.herinoid.rsi.model.ObatResep;
import com.herinoid.rsi.table.TabelDataResep;
import com.herinoid.rsi.table.TabelObatResepEditor;
import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.dao.ObatDao;
import fungsi.validasi;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import kepegawaian.DlgCariDokter;
import com.herinoid.rsi.util.Utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.herinoid.rsi.dao.BangsalDao;
import com.herinoid.rsi.dao.MarginDao;
import com.herinoid.rsi.dao.PasienDao;
import com.herinoid.rsi.dao.PemberianObatDetailDao;
import static com.herinoid.rsi.dao.ResepDao.getObatResepDetail;
import com.herinoid.rsi.gui.dialog.DlgCariObat;
import com.herinoid.rsi.model.EtiketObat;
import com.herinoid.rsi.model.PemeriksaanRalan;
import com.herinoid.rsi.model.Resep;
import com.herinoid.rsi.model.RincianResepVerifikasi;
import com.herinoid.rsi.table.TabelResepRincian;
import com.herinoid.rsi.util.Konstan;
import com.herinoid.rsi.widget.KeySelectionRenderer;
import com.herinoid.rsi.dao.PemeriksaanDao;
import com.herinoid.rsi.model.Pasien;
import com.herinoid.rsi.model.RegPeriksa;
import com.herinoid.rsi.dao.RegPeriksaDao;
import com.herinoid.rsi.model.MarginBpjs;
import com.herinoid.rsi.model.MarginObatNonBpjs;
import com.herinoid.rsi.model.NotaResep;
import fungsi.akses;
import fungsi.sekuel;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import widget.ComboBox;
import inventory.DlgAturanPakai;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author herinoid
 */
public final class DlgDataEResepDokter extends javax.swing.JDialog {

    private TabelDataResep model;
    private TabelObatResepEditor modelPilihan;
    private TabelResepRincian modelFarmasi;
    private TabelResepRincian modelDokter;
    private TableRowSorter<TableModel> rowSorter;
    private DlgCariObat addObat = new DlgCariObat(null, false);
    private ObatResep obatFromDialog;
    private ObatResep obatRacikan;
    private List<ObatResep> racikanList;
    private DialogRacikanResep dlgRacikan = new DialogRacikanResep(null, false);
    private validasi Valid = new validasi();
    private Properties pro = new Properties();
    private String sttRawat, kategoriObat;
    private int row, rowEditor;
    private String kdBangsal, tarif;
    private sekuel Sequel = new sekuel();
    private DlgAturanPakai aturanpakai = new DlgAturanPakai(null, false);
    private double total = 0;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgDataEResepDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            racikanList = new LinkedList<>();
            model = new TabelDataResep();
            modelPilihan = new TabelObatResepEditor();
            tblEditor.setModel(modelPilihan);
            modelFarmasi = new TabelResepRincian();
            modelDokter = new TabelResepRincian();
            this.setLocation(10, 2);
            setSize(656, 250);
            pro.loadFromXML(new FileInputStream("setting/database.xml"));
            rdoRajal.setSelected(true);
            kdBangsal = pro.getProperty("DEPOOBAT");
            btnEdit.setVisible(false);
            btnHapus.setVisible(false);
            txtCari.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent e) {
                    String text = txtCari.getText();

                    if (text.trim().length() == 0) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    String text = txtCari.getText();

                    if (text.trim().length() == 0) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

            });

            addObat.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {                  
                    obatFromDialog = addObat.getData();
                    if (obatFromDialog != null) {
                        modelPilihan.add(obatFromDialog);
                    }
                    addObat.clearData();
                }

                @Override
                public void windowIconified(WindowEvent e) {
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                }

                @Override
                public void windowActivated(WindowEvent e) {
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                }
            });

            tblData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    int selected = tblData.getSelectedRow();
                    if (selected != -1) {
                        DataEResep data = model.getAll().get(selected);
                        Pasien pasien = PasienDao.get(data.getNorm());
                        lblPasien.setText(data.getPasien());
                        lblTelp.setText(pasien.getNoTelp());
                        lblAlamat.setText(pasien.getAlamat());
                        PemeriksaanRalan periksa = PemeriksaanDao.getPemeriksaanRalanByNoRawat(data.getNoRawat());
                        lbBB.setText(periksa.getBeratBadan() == null ? " kg" : periksa.getBeratBadan() + " kg");
                        lbTB.setText(periksa.getTinggiBadan() == null ? " cm" : periksa.getTinggiBadan() + " cm");
                        lbSuhuBadan.setText(periksa.getSuhuTubuh());
                        lbTekanDarah.setText(periksa.getTekanDarah());
                        lbAlergi.setText(periksa.getAlergi());
                        if (Utils.isBlank(data.getValidasi())) {
                            panelSulapan.remove(panelResep);
                            panelDetailTotal.setVisible(true);
                            scrollDetail.setVisible(true);
                            panelResep.setVisible(false);
                            btnEdit.setVisible(false);
                            btnHapus.setVisible(false);
                            if (data.getJaminan().equalsIgnoreCase(Konstan.PASIEN_BPJS_KESEHATAN)) {
                                kategoriObat = "K01";
                            } else {
                                kategoriObat = "K02";
                            }
                            List<ObatResep> list = data.getObatDetails();
                            List<ObatResep> dataObats = getAllObatListByNoResep(list, data.getNoResep());
                            modelPilihan.removeAllElements();
                            modelPilihan.add(dataObats);
                            tblEditor.setModel(modelPilihan);
                            total = 0;
                            double ppn = 0;
                            double totalPpn = 0;

                            for (ObatResep o : dataObats) {
                                total = total + (o.getHarga() * o.getJumlah()) + o.getEmbalase() + o.getTuslah();
                            }
                            ppn = (total * 10) / 100;
                            totalPpn = total + ppn;
                            lblTotal.setText("Total : " + Utils.format(total, 0));
//                            lblPpn.setText("PPN : " + Utils.format(ppn, 0));
//                            lblTotalPpn.setText("Total + PPN : " + Utils.format(totalPpn, 0));
                        } else {
//                            panelSulapan.remove(Popup);

                            setResepVerifikasi(data.getNoRawat(), kdBangsal, data.getNoResep(), data.getJaminan());
                            panelSulapan.add(panelResep);
                            panelSulapan.repaint();
                            Bangsal bangsal = BangsalDao.get(kdBangsal);
                            lblDepoResep.setText(bangsal.getNama());
                            panelDetailTotal.setVisible(false);
                            scrollDetail.setVisible(false);
                            panelResep.setVisible(true);
                            btnEdit.setVisible(true);
                            btnHapus.setVisible(true);
                        }

                    }
                }
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DlgDataEResepDokter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DlgDataEResepDokter.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCmbData();

        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (aturanpakai.getTable().getSelectedRow() > -1) {
                    if (panelResep.isVisible()) {
                        tblFarmasi.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tblFarmasi.getSelectedRow(), 3);
                        tblFarmasi.requestFocus();
                    } else {
                        tblEditor.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tblEditor.getSelectedRow(), 11);
                        tblEditor.requestFocus();
                    }

                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

    }

    private List<ObatResep> getAllObatListByNoResep(List<ObatResep> dataObats, String noResep) {

        List<ObatResep> nonRacikans = new LinkedList<>();
        List<ObatResep> obatRacikans = new LinkedList<>();
        for (ObatResep o : dataObats) {
            if (Utils.isBlank(o.getRacikan())) {
                nonRacikans.add(o);
            } else {
                obatRacikans.add(o);
            }
        }

        List<ObatResep> obatTemotos = new LinkedList<>();
        obatTemotos.addAll(nonRacikans);
        if (obatRacikans.size() > 0) {
            Collections.sort(obatRacikans, Comparator.comparing(ObatResep::getKodeRacikan));
            String rck = null;
            int urut = 0;
            for (ObatResep f : obatRacikans) {
                urut++;
                if (Utils.isBlank(rck)) {
                    rck = f.getRacikan();
                    ObatResep r = new ObatResep();
                    ObatResep obatRck = PemberianObatDetailDao.getObatRacikanByNoResep(noResep, f.getKodeRacikan());
                    r.setKodeObat(f.getKodeRacikan());
                    r.setRacikan(f.getRacikan());
                    r.setNamaObat(obatRck.getMetodeRacik());
                    r.setJumlah(obatRck.getJmlRacik());
                    r.setParent(true);
//                    r.setJenisObat("Racikan");
                    r.setAturanPakai(obatRck.getAturanPakai());
//                    r.setKategori(f.getKategori());
                    r.setUrutan(urut);
                    obatTemotos.add(r);
                } else {
                    if (!rck.equals(f.getRacikan())) {
                        ObatResep r = new ObatResep();
                        ObatResep obatRck = PemberianObatDetailDao.getObatRacikanByNoResep(noResep, f.getKodeRacikan());
                        r.setKodeObat(f.getKodeRacikan());
                        r.setNamaObat(obatRck.getMetodeRacik());
                        r.setRacikan(f.getRacikan());
                        r.setJumlah(obatRck.getJmlRacik());
                        r.setAturanPakai(obatRck.getAturanPakai());
                        r.setParent(true);
                        r.setUrutan(urut);
                        obatTemotos.add(r);
                        rck = f.getRacikan();
                    }
                }

                ObatResep obat = new ObatResep();
                obat.setParent(false);
                obat.setKodeObat(f.getKodeObat());
                obat.setNamaObat(f.getNamaObat());
                obat.setJumlah(f.getJumlah());
                obat.setAturanPakai(f.getAturanPakai());
                obat.setHargaBeli(f.getHargaBeli());
                obat.setSatuan(f.getSatuan());
                obat.setJenisObat(f.getJenisObat());
                obat.setKategori(f.getKategori());
                obat.setEmbalase(f.getEmbalase());
                obat.setTuslah(f.getTuslah());
                obat.setStok(f.getStok());
                obat.setKodeRacikan(f.getKodeRacikan());
                obat.setKandungan(f.getKandungan());
                obat.setHarga(f.getHarga());
                obat.setRacikan(f.getRacikan());
                obat.setUrutan(urut);
                obatTemotos.add(obat);
            }

            Collections.sort(obatTemotos, Comparator
                    .comparing(ObatResep::getUrutan));
        }

        return obatTemotos;
    }

    public void setData(String kodeDepo, String kategoriObat, String jenisPasien) {
        this.tarif = jenisPasien;
        model.removeAllElements();
        tblData.setModel(model);
        rowSorter = new TableRowSorter<>(tblData.getModel());
        tblData.setRowSorter(rowSorter);

    }

    private void setCmbData() {
        cmbTarif.addItem(Konstan.PASIEN_RALAN);
        cmbTarif.addItem(Konstan.PASIEN_BELILUAR);
        cmbTarif.addItem(Konstan.PASIEN_KARYAWAN);
        cmbTarif.addItem(Konstan.PASIEN_KELAS1);
        cmbTarif.addItem(Konstan.PASIEN_KELAS2);
        cmbTarif.addItem(Konstan.PASIEN_KELAS3);
        cmbTarif.addItem(Konstan.PASIEN_KELAS_VIP);
        cmbTarif.addItem(Konstan.PASIEN_KELAS_VVIP);
    }

    private void setResepVerifikasi(String noRawat, String kdBangsal, String noresep, String jaminan) {
        RegPeriksa reg = RegPeriksaDao.get(noRawat);
        List<ObatResep> farmasis = PemberianObatDetailDao.getObatValidasiByNoResep(noresep, kdBangsal, jaminan, reg.getKdPj());
        List<ObatResep> dokters = PemberianObatDetailDao.getResepByNoresep(noresep, kdBangsal, jaminan, reg.getKdPj());
        List<ObatResep> dokterRacikans = ResepDao.getObatResepRacikanDetail(noresep, kdBangsal, jaminan, reg.getKdPj());
        List<RincianResepVerifikasi> rincians = new LinkedList<>();

        if (farmasis.size() > 0) {
            List<ObatResep> farmasiNonRaciks = new LinkedList<>();
            List<ObatResep> farmasiRaciks = new LinkedList<>();
            for (ObatResep f : farmasis) {
                if (Utils.isBlank(f.getKodeRacikan())) {
                    if (!farmasiNonRaciks.contains(f)) {
                        farmasiNonRaciks.add(f);
                    }
                } else {
                    if (!farmasiRaciks.contains(f)) {
                        farmasiRaciks.add(f);
                    }
                }
            }

            if (farmasiNonRaciks.size() > 0) {
                farmasiNonRaciks.stream().map((f) -> {
                    RincianResepVerifikasi r = new RincianResepVerifikasi();
                    r.setKodeObat(f.getKodeObat());
                    r.setNamaObat(f.getNamaObat());
                    double total = (f.getJumlah() * f.getHarga()) + f.getEmbalase() + f.getTuslah();
                    r.setRincian(Utils.format(f.getJumlah(), 0) + " x ( " + Utils.format(f.getHarga(), 0) + " + " + Utils.format(f.getEmbalase(), 0) + " + " + Utils.format(f.getTuslah(), 0) + " ) = " + Utils.format(total, 0));
                    r.setAturanPakai(f.getAturanPakai());
                    return r;
                }).forEachOrdered((r) -> {
                    rincians.add(r);
                });

            }

            if (farmasiRaciks.size() > 0) {
                Collections.sort(farmasiRaciks, Comparator.comparing(ObatResep::getKodeRacikan));
                String rck = null;
                int urut = 0;
                for (ObatResep f : farmasiRaciks) {
                    urut++;
                    if (Utils.isBlank(rck)) {
                        rck = f.getRacikan();
                        RincianResepVerifikasi r = new RincianResepVerifikasi();
                        ObatResep obatRck = PemberianObatDetailDao.getObatRacikanByNoResep(noresep, f.getKodeRacikan());
                        r.setKodeObat(f.getKodeRacikan());
                        r.setRacikan(f.getRacikan());
                        r.setNamaObat(obatRck.getMetodeRacik());
                        r.setRincian(String.valueOf(obatRck.getJmlRacik()));
                        r.setAturanPakai(obatRck.getAturanPakai());
                        r.setUrutan(urut);
                        rincians.add(r);
                    } else {
                        if (!rck.equals(f.getRacikan())) {
                            RincianResepVerifikasi r = new RincianResepVerifikasi();
                            ObatResep obatRck = PemberianObatDetailDao.getObatRacikanByNoResep(noresep, f.getKodeRacikan());
                            r.setKodeObat(f.getKodeRacikan());
                            r.setNamaObat(obatRck.getMetodeRacik());
                            r.setRacikan(f.getRacikan());
                            r.setRincian(String.valueOf(obatRck.getJmlRacik()));
                            r.setAturanPakai(obatRck.getAturanPakai());
                            r.setUrutan(urut);
                            rincians.add(r);
                            rck = f.getRacikan();
                        }
                    }
                    RincianResepVerifikasi r = new RincianResepVerifikasi();
                    r.setKodeObat(f.getKodeObat());
                    r.setNamaObat(f.getNamaObat());
                    double total = (f.getJumlah() * f.getHarga()) + f.getEmbalase() + f.getTuslah();
                    r.setRincian(Utils.format(f.getJumlah(), 0) + " x ( " + Utils.format(f.getHarga(), 0) + " + " + Utils.format(f.getEmbalase(), 0) + " + " + Utils.format(f.getTuslah(), 0) + " ) = " + Utils.format(total, 0));
                    r.setAturanPakai(f.getAturanPakai());
                    r.setUrutan(urut);
                    rincians.add(r);
                }
                Collections.sort(rincians, Comparator
                        .comparing(RincianResepVerifikasi::getUrutan));
            }

            modelFarmasi.removeAllElements();
            modelFarmasi.add(rincians);
            tblFarmasi.setModel(modelFarmasi);
        }
        List<RincianResepVerifikasi> rincianDokters = new LinkedList<>();
        if (dokters != null && dokters.size() > 0) {
            dokters.stream().map((f) -> {
                RincianResepVerifikasi r = new RincianResepVerifikasi();
                r.setKodeObat(f.getKodeObat());
                r.setNamaObat(f.getNamaObat());
                double total = (f.getJumlah() * f.getHarga()) + f.getEmbalase() + f.getTuslah();
                r.setRincian(Utils.format(f.getJumlah(), 0) + " x ( " + Utils.format(f.getHarga(), 0) + " + " + Utils.format(f.getEmbalase(), 0) + " + " + Utils.format(f.getTuslah(), 0) + " ) = " + Utils.format(total, 0));
                r.setAturanPakai(f.getAturanPakai());
                return r;
            }).forEachOrdered((r) -> {
                rincianDokters.add(r);
            });

        }

        if (dokterRacikans.size() > 0) {
            Collections.sort(dokterRacikans, Comparator.comparing(ObatResep::getKodeRacikan));

            String rck = null;
            int urut = 0;
            for (ObatResep f : dokterRacikans) {
                urut++;
                if (Utils.isBlank(rck)) {
                    RincianResepVerifikasi r = new RincianResepVerifikasi();
                    ObatResep obatRck = PemberianObatDetailDao.getObatRacikanByNoResep(noresep, f.getKodeRacikan());
                    r.setKodeObat(f.getKodeRacikan());
                    r.setNamaObat(obatRck.getMetodeRacik());
                    r.setRacikan(f.getRacikan());
                    r.setRincian(String.valueOf(obatRck.getJmlRacik()));
                    r.setAturanPakai(obatRck.getAturanPakai());
                    r.setUrutan(urut);
                    rck = f.getRacikan();
                    rincianDokters.add(r);
                } else {
                    if (!rck.equals(f.getRacikan())) {
                        rck = f.getRacikan();
                        RincianResepVerifikasi r = new RincianResepVerifikasi();
                        ObatResep obatRck = PemberianObatDetailDao.getObatRacikanByNoResep(noresep, f.getKodeRacikan());
                        r.setKodeObat(f.getKodeRacikan());
                        r.setNamaObat(obatRck.getMetodeRacik());
                        r.setRacikan(f.getRacikan());
                        r.setRincian(String.valueOf(obatRck.getJmlRacik()));
                        r.setAturanPakai(obatRck.getAturanPakai());
                        r.setUrutan(urut);
                        rincianDokters.add(r);
                    }
                }
                RincianResepVerifikasi r = new RincianResepVerifikasi();
                r.setKodeObat(f.getKodeObat());
                r.setNamaObat(f.getNamaObat());
                double total = (f.getJumlah() * f.getHarga()) + f.getEmbalase() + f.getTuslah();
                r.setRincian(Utils.format(f.getJumlah(), 0) + " x ( " + Utils.format(f.getHarga(), 0) + " + " + Utils.format(f.getEmbalase(), 0) + " + " + Utils.format(f.getTuslah(), 0) + " ) = " + Utils.format(total, 0));
                r.setAturanPakai(f.getAturanPakai());
                r.setUrutan(urut);
                rincianDokters.add(r);
            }
            Collections.sort(rincianDokters, Comparator
                    .comparing(RincianResepVerifikasi::getUrutan)
                    .thenComparing(RincianResepVerifikasi::getUrutan));
        }

        if (rincianDokters.size() > 0) {
            modelDokter.removeAllElements();
            modelDokter.add(rincianDokters);
            tblDokter.setModel(modelDokter);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        mnEditAturanPake = new javax.swing.JMenuItem();
        MnAllStock = new javax.swing.JMenuItem();
        mnHapusObat = new javax.swing.JMenuItem();
        perawatanGrup = new javax.swing.ButtonGroup();
        printPopup = new javax.swing.JPopupMenu();
        MnPackaging = new javax.swing.JMenuItem();
        mnTerimaPasien = new javax.swing.JMenuItem();
        MnEtiket = new javax.swing.JMenuItem();
        MnNotaObat = new javax.swing.JMenuItem();
        panelResep = new widget.PanelBiasa();
        panelBiasa4 = new widget.PanelBiasa();
        scrollPane1 = new widget.ScrollPane();
        tblFarmasi = new widget.Table();
        panelBiasa3 = new widget.PanelBiasa();
        scrollPane3 = new widget.ScrollPane();
        tblDokter = new widget.Table();
        PopupEditorFarmasi = new javax.swing.JPopupMenu();
        mnAturanPakai = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        btnAddObat = new widget.Button();
        label13 = new widget.Label();
        label2 = new widget.Label();
        btnHapus = new widget.Button();
        btnEdit = new widget.Button();
        btnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        cmbTanggalTo = new widget.Tanggal();
        cmbTarif = new widget.ComboBox();
        btnCariData = new widget.Button();
        jLabel10 = new widget.Label();
        rdoRajal = new javax.swing.JRadioButton();
        rdoRanap = new javax.swing.JRadioButton();
        cmbTanggalfrom = new widget.Tanggal();
        jLabel6 = new widget.Label();
        jLabel7 = new widget.Label();
        jLabel15 = new widget.Label();
        lbTB = new widget.Label();
        lbBB = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        lbTekanDarah = new widget.Label();
        jLabel18 = new widget.Label();
        lbSuhuBadan = new widget.Label();
        jLabel19 = new widget.Label();
        lbAlergi = new widget.Label();
        label7 = new widget.Label();
        lblPasien = new widget.Label();
        label9 = new widget.Label();
        lblTelp = new widget.Label();
        label11 = new widget.Label();
        lblAlamat = new widget.Label();
        lblDepoResep = new widget.Label();
        label10 = new widget.Label();
        jPanel1 = new javax.swing.JPanel();
        panelBiasa1 = new widget.PanelBiasa();
        panelBiasa2 = new widget.PanelBiasa();
        label1 = new widget.Label();
        txtCari = new widget.TextBox();
        scrollPane2 = new widget.ScrollPane();
        tblData = new widget.Table();
        panelSulapan = new widget.panelisi();
        scrollDetail = new widget.ScrollPane();
        tblEditor = new widget.Table();
        panelDetailTotal = new widget.panelisi();
        label6 = new widget.Label();
        label5 = new widget.Label();
        label4 = new widget.Label();
        label3 = new widget.Label();
        lblTotal = new widget.Label();
        lblDepo = new widget.Label();

        Popup.setName("Popup"); // NOI18N

        mnEditAturanPake.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Pills.png"))); // NOI18N
        mnEditAturanPake.setText("Edit Aturan Pakai");
        mnEditAturanPake.setName("mnEditAturanPake"); // NOI18N
        mnEditAturanPake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEditAturanPakeActionPerformed(evt);
            }
        });
        Popup.add(mnEditAturanPake);

        MnAllStock.setBackground(new java.awt.Color(255, 255, 255));
        MnAllStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Church.png"))); // NOI18N
        MnAllStock.setText("Tampilkan Semua Stock");
        MnAllStock.setName("MnAllStock"); // NOI18N
        MnAllStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAllStockActionPerformed(evt);
            }
        });
        Popup.add(MnAllStock);

        mnHapusObat.setBackground(new java.awt.Color(255, 255, 255));
        mnHapusObat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mnHapusObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/101.png"))); // NOI18N
        mnHapusObat.setText("Hapus Obat");
        mnHapusObat.setName("mnHapusObat"); // NOI18N
        mnHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnHapusObatActionPerformed(evt);
            }
        });
        Popup.add(mnHapusObat);

        printPopup.setName("printPopup"); // NOI18N

        MnPackaging.setBackground(new java.awt.Color(255, 255, 255));
        MnPackaging.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPackaging.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Vial-Pills.png"))); // NOI18N
        MnPackaging.setText("Pengepakan");
        MnPackaging.setName("MnPackaging"); // NOI18N
        MnPackaging.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPackagingActionPerformed(evt);
            }
        });
        printPopup.add(MnPackaging);

        mnTerimaPasien.setBackground(new java.awt.Color(255, 255, 255));
        mnTerimaPasien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mnTerimaPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale.png"))); // NOI18N
        mnTerimaPasien.setText("Diterima Pasien");
        mnTerimaPasien.setName("mnTerimaPasien"); // NOI18N
        mnTerimaPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTerimaPasienActionPerformed(evt);
            }
        });
        printPopup.add(mnTerimaPasien);

        MnEtiket.setBackground(new java.awt.Color(255, 255, 255));
        MnEtiket.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnEtiket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PrinterSettings.png"))); // NOI18N
        MnEtiket.setText("Cetak ETiket Obat");
        MnEtiket.setName("MnEtiket"); // NOI18N
        MnEtiket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnEtiketActionPerformed(evt);
            }
        });
        printPopup.add(MnEtiket);

        MnNotaObat.setBackground(new java.awt.Color(255, 255, 255));
        MnNotaObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNotaObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PrinterSettings.png"))); // NOI18N
        MnNotaObat.setText("Cetak Nota Obat");
        MnNotaObat.setName("MnNotaObat"); // NOI18N
        MnNotaObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNotaObatActionPerformed(evt);
            }
        });
        printPopup.add(MnNotaObat);

        panelResep.setName("panelResep"); // NOI18N
        panelResep.setLayout(new java.awt.GridLayout(1, 2));

        panelBiasa4.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Validasi Farmasi"));
        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(new javax.swing.BoxLayout(panelBiasa4, javax.swing.BoxLayout.LINE_AXIS));

        scrollPane1.setName("scrollPane1"); // NOI18N

        tblFarmasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblFarmasi.setComponentPopupMenu(PopupEditorFarmasi);
        tblFarmasi.setName("tblFarmasi"); // NOI18N
        tblFarmasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFarmasiMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tblFarmasi);

        panelBiasa4.add(scrollPane1);

        panelResep.add(panelBiasa4);

        panelBiasa3.setBorder(javax.swing.BorderFactory.createTitledBorder("Resep Asli Dokter"));
        panelBiasa3.setName("panelBiasa3"); // NOI18N
        panelBiasa3.setLayout(new javax.swing.BoxLayout(panelBiasa3, javax.swing.BoxLayout.LINE_AXIS));

        scrollPane3.setName("scrollPane3"); // NOI18N

        tblDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDokter.setName("tblDokter"); // NOI18N
        scrollPane3.setViewportView(tblDokter);

        panelBiasa3.add(scrollPane3);

        panelResep.add(panelBiasa3);

        PopupEditorFarmasi.setName("PopupEditorFarmasi"); // NOI18N

        mnAturanPakai.setBackground(new java.awt.Color(255, 255, 255));
        mnAturanPakai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mnAturanPakai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/EDIT2.png"))); // NOI18N
        mnAturanPakai.setText("Edit Aturan Pakai");
        mnAturanPakai.setName("mnAturanPakai"); // NOI18N
        mnAturanPakai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAturanPakaiActionPerformed(evt);
            }
        });
        PopupEditorFarmasi.add(mnAturanPakai);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data E-Resep Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        btnAddObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAddObat.setText("Obat Lain");
        btnAddObat.setName("btnAddObat"); // NOI18N
        btnAddObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddObatActionPerformed(evt);
            }
        });
        panelisi3.add(btnAddObat);

        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(500, 23));
        panelisi3.add(label13);

        label2.setText("  ");
        label2.setName("label2"); // NOI18N
        panelisi3.add(label2);

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Delete.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.setName("btnHapus"); // NOI18N
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        panelisi3.add(btnHapus);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/peminjaman.png"))); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelisi3.add(btnEdit);

        btnSimpan.setForeground(new java.awt.Color(0, 0, 0));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        btnSimpan.setText("Validasi");
        btnSimpan.setName("btnSimpan"); // NOI18N
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(btnSimpan);

        BtnKeluar.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(983, 120));
        FormInput.setLayout(null);

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tarif : ");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(370, 40, 60, 23);

        cmbTanggalTo.setForeground(new java.awt.Color(50, 70, 50));
        cmbTanggalTo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-08-2020" }));
        cmbTanggalTo.setDisplayFormat("dd-MM-yyyy");
        cmbTanggalTo.setName("cmbTanggalTo"); // NOI18N
        cmbTanggalTo.setOpaque(false);
        cmbTanggalTo.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(cmbTanggalTo);
        cmbTanggalTo.setBounds(270, 40, 90, 23);

        cmbTarif.setName("cmbTarif"); // NOI18N
        cmbTarif.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(cmbTarif);
        cmbTarif.setBounds(442, 40, 180, 23);

        btnCariData.setForeground(new java.awt.Color(0, 0, 0));
        btnCariData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/search_page.png"))); // NOI18N
        btnCariData.setMnemonic('2');
        btnCariData.setText("Cari");
        btnCariData.setToolTipText("Alt+2");
        btnCariData.setName("btnCariData"); // NOI18N
        btnCariData.setPreferredSize(new java.awt.Dimension(28, 23));
        btnCariData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariDataActionPerformed(evt);
            }
        });
        FormInput.add(btnCariData);
        btnCariData.setBounds(630, 40, 70, 23);

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Jenis Perawatan :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel10);
        jLabel10.setBounds(10, 10, 110, 23);

        perawatanGrup.add(rdoRajal);
        rdoRajal.setText("Rawat Jalan");
        rdoRajal.setName("rdoRajal"); // NOI18N
        rdoRajal.setOpaque(false);
        rdoRajal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRajalActionPerformed(evt);
            }
        });
        FormInput.add(rdoRajal);
        rdoRajal.setBounds(130, 10, 120, 23);

        perawatanGrup.add(rdoRanap);
        rdoRanap.setText("Rawat Inap");
        rdoRanap.setName("rdoRanap"); // NOI18N
        rdoRanap.setOpaque(false);
        rdoRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRanapActionPerformed(evt);
            }
        });
        FormInput.add(rdoRanap);
        rdoRanap.setBounds(261, 10, 130, 23);

        cmbTanggalfrom.setForeground(new java.awt.Color(50, 70, 50));
        cmbTanggalfrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-08-2020" }));
        cmbTanggalfrom.setDisplayFormat("dd-MM-yyyy");
        cmbTanggalfrom.setName("cmbTanggalfrom"); // NOI18N
        cmbTanggalfrom.setOpaque(false);
        cmbTanggalfrom.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(cmbTanggalfrom);
        cmbTanggalfrom.setBounds(140, 40, 90, 23);

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tanggal Rawat :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel6);
        jLabel6.setBounds(10, 40, 110, 23);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("S/D");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel7);
        jLabel7.setBounds(240, 40, 20, 23);

        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Tinggi Badan :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel15);
        jLabel15.setBounds(760, 10, 80, 23);

        lbTB.setForeground(new java.awt.Color(0, 0, 0));
        lbTB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTB.setText("cm");
        lbTB.setName("lbTB"); // NOI18N
        lbTB.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(lbTB);
        lbTB.setBounds(850, 10, 80, 23);

        lbBB.setForeground(new java.awt.Color(0, 0, 0));
        lbBB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbBB.setText("kg");
        lbBB.setName("lbBB"); // NOI18N
        lbBB.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(lbBB);
        lbBB.setBounds(850, 30, 80, 23);

        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Berat Badan :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel16);
        jLabel16.setBounds(760, 30, 80, 23);

        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Tekanan Darah :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel17);
        jLabel17.setBounds(760, 50, 80, 23);

        lbTekanDarah.setForeground(new java.awt.Color(0, 0, 0));
        lbTekanDarah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTekanDarah.setName("lbTekanDarah"); // NOI18N
        lbTekanDarah.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(lbTekanDarah);
        lbTekanDarah.setBounds(850, 50, 80, 23);

        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Suhu Badan :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel18);
        jLabel18.setBounds(760, 70, 80, 23);

        lbSuhuBadan.setForeground(new java.awt.Color(0, 0, 0));
        lbSuhuBadan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbSuhuBadan.setName("lbSuhuBadan"); // NOI18N
        lbSuhuBadan.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(lbSuhuBadan);
        lbSuhuBadan.setBounds(850, 70, 80, 23);

        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Alergi :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel19);
        jLabel19.setBounds(760, 90, 80, 23);

        lbAlergi.setForeground(new java.awt.Color(0, 0, 0));
        lbAlergi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbAlergi.setName("lbAlergi"); // NOI18N
        lbAlergi.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(lbAlergi);
        lbAlergi.setBounds(850, 90, 250, 23);

        label7.setText("Pasien : ");
        label7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(50, 70, 70, 20);

        lblPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPasien.setText("Pasien");
        lblPasien.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPasien.setName("lblPasien"); // NOI18N
        FormInput.add(lblPasien);
        lblPasien.setBounds(130, 70, 400, 20);

        label9.setText("No. Telp :");
        label9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label9.setName("label9"); // NOI18N
        FormInput.add(label9);
        label9.setBounds(60, 90, 60, 20);

        lblTelp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTelp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTelp.setName("lblTelp"); // NOI18N
        FormInput.add(lblTelp);
        lblTelp.setBounds(130, 90, 120, 20);

        label11.setText("Alamat : ");
        label11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label11.setName("label11"); // NOI18N
        FormInput.add(label11);
        label11.setBounds(260, 90, 60, 20);

        lblAlamat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAlamat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAlamat.setName("lblAlamat"); // NOI18N
        FormInput.add(lblAlamat);
        lblAlamat.setBounds(330, 90, 380, 20);

        lblDepoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDepoResep.setText("isi depo");
        lblDepoResep.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDepoResep.setName("lblDepoResep"); // NOI18N
        FormInput.add(lblDepoResep);
        lblDepoResep.setBounds(450, 10, 280, 20);

        label10.setText("Depo : ");
        label10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label10.setName("label10"); // NOI18N
        FormInput.add(label10);
        label10.setBounds(390, 10, 50, 20);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        panelBiasa1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: DATA E-RESEP ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(new java.awt.BorderLayout());

        panelBiasa2.setName("panelBiasa2"); // NOI18N

        label1.setText("Cari Data :");
        label1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label1.setName("label1"); // NOI18N
        panelBiasa2.add(label1);

        txtCari.setForeground(new java.awt.Color(0, 0, 0));
        txtCari.setName("txtCari"); // NOI18N
        txtCari.setPreferredSize(new java.awt.Dimension(400, 24));
        panelBiasa2.add(txtCari);

        panelBiasa1.add(panelBiasa2, java.awt.BorderLayout.PAGE_START);

        scrollPane2.setName("scrollPane2"); // NOI18N

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblData.setComponentPopupMenu(printPopup);
        tblData.setName("tblData"); // NOI18N
        tblData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataMouseClicked(evt);
            }
        });
        scrollPane2.setViewportView(tblData);

        panelBiasa1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelBiasa1);

        panelSulapan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: Detail Data E-Resep ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelSulapan.setName("panelSulapan"); // NOI18N
        panelSulapan.setLayout(new java.awt.BorderLayout());

        scrollDetail.setName("scrollDetail"); // NOI18N

        tblEditor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEditor.setComponentPopupMenu(Popup);
        tblEditor.setName("tblEditor"); // NOI18N
        tblEditor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEditorMouseClicked(evt);
            }
        });
        tblEditor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblEditorKeyReleased(evt);
            }
        });
        scrollDetail.setViewportView(tblEditor);

        panelSulapan.add(scrollDetail, java.awt.BorderLayout.CENTER);

        panelDetailTotal.setName("panelDetailTotal"); // NOI18N
        panelDetailTotal.setLayout(new java.awt.GridLayout(2, 4));

        label6.setName("label6"); // NOI18N
        panelDetailTotal.add(label6);

        label5.setName("label5"); // NOI18N
        panelDetailTotal.add(label5);

        label4.setName("label4"); // NOI18N
        panelDetailTotal.add(label4);

        label3.setName("label3"); // NOI18N
        panelDetailTotal.add(label3);

        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setText("Total : 0");
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotal.setName("lblTotal"); // NOI18N
        panelDetailTotal.add(lblTotal);

        lblDepo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDepo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDepo.setName("lblDepo"); // NOI18N
        panelDetailTotal.add(lblDepo);

        panelSulapan.add(panelDetailTotal, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(panelSulapan);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void btnCariDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDataActionPerformed
        String depo = pro.getProperty("DEPOOBAT");
        Bangsal bangsal = BangsalDao.get(depo);
        String jenisPasien = Konstan.PASIEN_RALAN;
        if (rdoRanap.isSelected()) {
            jenisPasien = Konstan.PASIEN_RANAP;
        }

        lblDepoResep.setText(bangsal.getNama());
        List<DataEResep> dataList = ResepDao.getResepByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString(), jenisPasien);
        List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString(), jenisPasien);
        showResepData(dataList, dataRacikanList);
    }//GEN-LAST:event_btnCariDataActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        // TODO add your handling code here:
        row = tblData.getSelectedRow();
    }//GEN-LAST:event_tblDataMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        String depo = pro.getProperty("DEPOOBAT");
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if (row > -1) {
            DataEResep resep = model.get(tblData.convertRowIndexToModel(row));
            List<ObatResep> newDetails = modelPilihan.getAll();
            boolean cekStok = true;
            String obatName = "";
            for (ObatResep o : newDetails) {
                if (o.getStok() < 1) {
                    cekStok = false;
                    obatName = o.getNamaObat();
                    break;
                } else if (o.getStok() < o.getJumlah()) {
                    cekStok = false;
                    obatName = o.getNamaObat();
                    break;
                }
            }
            if (cekStok) {
                if (resep.getStatus().equals(Resep.STATUS_BELUM_VERIFIKASI)) {
                    int emmmm = JOptionPane.showConfirmDialog(null, "Anda akan memverifikasi data resep, data yang sudah di verifikasi tidak dapat di verifikasi ulang. silahkan teliti kembali", "Perhatian", dialogButton);
                    if (emmmm == 0) {
                        if (resep.getObatDetails().size() > 0) {
                            boolean sukses = ResepDao.saveDetailPemberianObat(sttRawat, resep.getNoRawat(), newDetails, depo, resep.getNoResep());
                            if (sukses) {
                                String jenisPasien = Konstan.PASIEN_RALAN;
                                if (rdoRanap.isSelected()) {
                                    jenisPasien = Konstan.PASIEN_RANAP;
                                }
                                ResepDao.updateValidasi(resep.getNoRawat(), resep.getNoResep(), new Date(), newDetails);
                                List<DataEResep> dataList = ResepDao.getResepByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString(), jenisPasien);
                                List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString(), jenisPasien);
                                showResepData(dataList, dataRacikanList);
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "data obat kosong");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Data sudah diverifikasi, anda tidak dapat memverifikasi ulang..");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Stok obat " + obatName + " tidak mencukupi, silahkan edit lagi..");
            }

        }

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tblEditorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEditorKeyReleased
        // TODO add your handling code here:
        ObatResep obat = modelPilihan.get(tblEditor.convertRowIndexToModel(tblEditor.getSelectedRow()));
        if (obat.getStok() > 0 && obat.getStok() >= obat.getJumlah()) {
            total = total + (obat.getHarga() * obat.getJumlah()) + obat.getEmbalase() + obat.getTuslah();
            lblTotal.setText("Total : " + Utils.format(total, 0));
        } else {
            JOptionPane.showMessageDialog(null, "Stok obat tidak mencukupi, silahkan cari obat di lokasi lain");
            if (obat.getStok() > 0 && obat.getJumlah() > obat.getStok()) {
                obat.setJumlah(obat.getStok());
                removeDuplicate(obat);
                for (ObatResep o : modelPilihan.getAll()) {
                    total = total + (o.getHarga() * o.getJumlah()) + o.getEmbalase() + o.getTuslah();
                }
                lblTotal.setText("Total : " + Utils.format(total, 0));
            }

        }

    }//GEN-LAST:event_tblEditorKeyReleased

    private void rdoRajalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRajalActionPerformed
        // TODO add your handling code here:
        sttRawat = "Ralan";
    }//GEN-LAST:event_rdoRajalActionPerformed

    private void rdoRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRanapActionPerformed
        // TODO add your handling code here:
        sttRawat = "Ranap";
    }//GEN-LAST:event_rdoRanapActionPerformed

    private void tblEditorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEditorMouseClicked
        // TODO add your handling code here:
//        int barisPilihan = tblEditor.convertRowIndexToModel(tblEditor.getSelectedRow());
//        if (barisPilihan > -1) {
//            if (evt.getClickCount() == 2) {
//                if (tblEditor.getSelectedColumn() == 11) {
//                    aturanpakai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
//                    aturanpakai.setLocationRelativeTo(internalFrame1);
//                    aturanpakai.setVisible(true);
//                }
//            }
//
//        }
    }//GEN-LAST:event_tblEditorMouseClicked

    private void btnAddObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddObatActionPerformed
        // TODO add your handling code here:
        if (tblData.getSelectedRow() > -1) {
            String depo = pro.getProperty("DEPOOBAT");
            addObat.setData(depo, kategoriObat, cmbTarif.getSelectedItem().toString());
            addObat.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih salah satu data resep pada tabel paling atas..");
        }

    }//GEN-LAST:event_btnAddObatActionPerformed

    private void mnTerimaPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnTerimaPasienActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_NO_OPTION;
        String depo = pro.getProperty("DEPOOBAT");
        int baris = tblData.convertRowIndexToModel(tblData.getSelectedRow());
        if (baris > -1) {
            DataEResep resep = model.get(tblData.convertRowIndexToModel(baris));
            if (resep.getStatus().equals(Resep.STATUS_BELUM_VERIFIKASI)) {
                JOptionPane.showMessageDialog(null, "Resep belum divalidasi, silahkan di validasi terlebih dahulu..");
            } else if (resep.getStatus().equals(Resep.STATUS_SAMPAI_PASIEN)) {
                JOptionPane.showMessageDialog(null, "Resep sudah diambil pasien..");
            } else {
                int halo = JOptionPane.showConfirmDialog(null, "Apa benar obat sudah diambil pasien?? ", "Perhatian", dialogButton);
                if (halo == 0) {
                    boolean berhatsil = ResepDao.updateDiterimaPasien(resep.getNoResep());
                    if (berhatsil) {
                        String jenisPasien = Konstan.PASIEN_RALAN;
                        if (rdoRanap.isSelected()) {
                            jenisPasien = Konstan.PASIEN_RANAP;
                        }
                        List<DataEResep> dataList = ResepDao.getResepByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString(), jenisPasien);
                        List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString(), jenisPasien);
                        showResepData(dataList, dataRacikanList);
                    }
                }

            }

        }

    }//GEN-LAST:event_mnTerimaPasienActionPerformed

    private void MnAllStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAllStockActionPerformed
        // TODO add your handling code here:
        List<Bangsal> depos = BangsalDao.getDepoObat();
        ComboBox cmDepo = new ComboBox();
        for (Bangsal m : depos) {
            cmDepo.addItem(m);
            KeySelectionRenderer renderer = new KeySelectionRenderer(cmDepo) {
                @Override
                public String getDisplayValue(Object value) {
                    Bangsal depo = (Bangsal) value;
                    return depo.getNama();
                }
            };
        }

        JOptionPane.showMessageDialog(null, cmDepo, "Silahkan pilih Depo", JOptionPane.QUESTION_MESSAGE);
        Bangsal depo = (Bangsal) cmDepo.getSelectedItem();
        if (cmDepo.getSelectedItem() != null) {
            int baris = tblData.convertRowIndexToModel(tblData.getSelectedRow());
            int barisPilihan = tblEditor.convertRowIndexToModel(tblEditor.getSelectedRow());
            if (baris > -1) {
                DataEResep resep = model.get(tblData.convertRowIndexToModel(baris));
                if (barisPilihan > -1) {
                    ObatResep obatKlik = modelPilihan.get(tblEditor.convertRowIndexToModel(barisPilihan));
                    modelPilihan.remove(obatKlik);
                    ObatResep d = ResepDao.getObatStock(obatKlik.getKodeObat(), depo.getKode(), cmbTarif.getSelectedItem().toString());
                    obatKlik.setStok(d.getStok());
                    modelPilihan.add(obatKlik);
                    tblEditor.setModel(modelPilihan);
                }

            }
        }

    }//GEN-LAST:event_MnAllStockActionPerformed

    private void MnPackagingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPackagingActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_NO_OPTION;
        String depo = pro.getProperty("DEPOOBAT");
        int baris = tblData.convertRowIndexToModel(tblData.getSelectedRow());
        if (baris > -1) {
            DataEResep resep = model.get(tblData.convertRowIndexToModel(baris));
            if (resep.getStatus().equals(Resep.STATUS_BELUM_VERIFIKASI)) {
                JOptionPane.showMessageDialog(null, "Resep belum divalidasi, silahkan di validasi terlebih dahulu..");
            } else if (resep.getStatus().equals(Resep.STATUS_SAMPAI_PASIEN)) {
                JOptionPane.showMessageDialog(null, "Resep sudah diambil pasien..");
            } else {
                int halo = JOptionPane.showConfirmDialog(null, "Apakah anda mau melakukan pengepakan obat..? ", "Perhatian", dialogButton);
                if (halo == 0) {
                    boolean berhatsil = ResepDao.updatePacking(resep.getNoResep());
                    if (berhatsil) {
                        String jenisPasien = Konstan.PASIEN_RALAN;
                        if (rdoRanap.isSelected()) {
                            jenisPasien = Konstan.PASIEN_RANAP;
                        }
                        List<DataEResep> dataList = ResepDao.getResepByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString(), jenisPasien);
                        List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString(), jenisPasien);
                        showResepData(dataList, dataRacikanList);
                    }
                }

            }

        }
    }//GEN-LAST:event_MnPackagingActionPerformed

    private void MnEtiketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnEtiketActionPerformed
        // TODO add your handling code here:
        String depo = pro.getProperty("DEPOOBAT");
        int baris = tblData.convertRowIndexToModel(tblData.getSelectedRow());
        if (baris > -1) {
            DataEResep resep = model.get(tblData.convertRowIndexToModel(baris));
            if (resep.getStatus().equals(Resep.STATUS_BELUM_VERIFIKASI)) {
                JOptionPane.showMessageDialog(null, "Tidak bisa mencetak etiket karena Resep dengan No. " + resep.getNoResep() + " belum divalidasi");
            } else {
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                List<EtiketObat> data = ResepDao.getEtiketByNoResep(resep.getNoResep(), depo);
                if (data != null) {
                    String reportName = "etiketEResep.jasper";
                    String folder = "report";
                    Utils.print(reportName, folder, ".:: Etiket EResep ::.", data, param);
                }
            }
        }

    }//GEN-LAST:event_MnEtiketActionPerformed

    private void tblFarmasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFarmasiMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_tblFarmasiMouseClicked

    private void mnAturanPakaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAturanPakaiActionPerformed
        // TODO add your handling code here:
        int barisPilihan = tblFarmasi.convertRowIndexToModel(tblFarmasi.getSelectedRow());
        if (barisPilihan > -1) {
            if (tblFarmasi.getSelectedColumn() == 3) {
                aturanpakai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                aturanpakai.setLocationRelativeTo(internalFrame1);
                aturanpakai.setVisible(true);
            }

        }
    }//GEN-LAST:event_mnAturanPakaiActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int baris = tblData.convertRowIndexToModel(tblData.getSelectedRow());
        if (baris > -1) {
            DataEResep resep = model.get(tblData.convertRowIndexToModel(baris));
            for (RincianResepVerifikasi r : modelFarmasi.getAll()) {
                ResepDao.updateAturanPakaiFarmasi(r.getAturanPakai(), resep.getNoResep(), r.getKodeObat());
                if (!Utils.isBlank(r.getRacikan())) {
                    ResepDao.updateAturanPakaiRacikan(r.getAturanPakai(), resep.getNoResep(), r.getKodeObat());
                }

            }
            setResepVerifikasi(resep.getNoRawat(), kdBangsal, resep.getNoResep(), resep.getJaminan());

        }

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int baris = tblData.convertRowIndexToModel(tblData.getSelectedRow());
        if (baris > -1) {
            int nguiknguik = JOptionPane.showConfirmDialog(null, "Apakah anda akan menghapus data validasi resep..?", "PERHATIAN", JOptionPane.YES_NO_OPTION);
            if (nguiknguik == 0) {
                DataEResep resep = model.get(tblData.convertRowIndexToModel(baris));
                if (resep.getStatus().equals(Resep.STATUS_SUDAH_VERIFIKASI)) {
                    boolean isHapus = ResepDao.deleteDataObatValidasiFarmasi(resep.getNoResep());
                    if (isHapus) {
                        boolean isDel = PemberianObatDetailDao.deleteDetailPemberianObat(resep.getNoResep());
                        if (isDel) {
                            // update gudang
                            for (RincianResepVerifikasi r : modelFarmasi.getAll()) {

                                Obat obat = ObatDao.getObat(kdBangsal, r.getKodeObat());
                                double stok = 0;
                                double jumlah = 0;
                                if (obat != null) {
                                    stok = obat.getStok();
                                }
                                if (r.getRincian().length() > 6) {
                                    jumlah = Double.parseDouble(r.getRincian().substring(0, r.getRincian().indexOf("x")).replaceAll("\\s", ""));
                                } else {
                                    jumlah = Double.parseDouble(r.getRincian().replaceAll("\\s", ""));
                                }
                                ResepDao.updateStokGudang(stok + jumlah, r.getKodeObat(), kdBangsal);
                            }
                        }
                        ResepDao.updateValidasiAfterHapus(resep.getNoResep());
                        String jenisPasien = Konstan.PASIEN_RALAN;
                        if (rdoRanap.isSelected()) {
                            jenisPasien = Konstan.PASIEN_RANAP;
                        }
                        List<DataEResep> dataList = ResepDao.getResepByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), kdBangsal, cmbTarif.getSelectedItem().toString(), jenisPasien);
                        List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), kdBangsal, cmbTarif.getSelectedItem().toString(), jenisPasien);
                        showResepData(dataList, dataRacikanList);
                        panelSulapan.remove(panelResep);
                        panelDetailTotal.setVisible(true);
                        scrollDetail.setVisible(true);
                        panelResep.setVisible(false);
                        btnEdit.setVisible(false);
                        btnHapus.setVisible(false);
                        if (resep.getJaminan().equalsIgnoreCase(Konstan.PASIEN_BPJS_KESEHATAN)) {
                            kategoriObat = "K01";
                        } else {
                            kategoriObat = "K02";
                        }
                        List<ObatResep> list = resep.getObatDetails();
                        List<ObatResep> dataObats = getAllObatListByNoResep(list, resep.getNoResep());
                        modelPilihan.removeAllElements();
                        modelPilihan.add(dataObats);
                        tblEditor.setModel(modelPilihan);
                        total = 0;
                        double ppn = 0;
                        double totalPpn = 0;

                        for (ObatResep o : dataObats) {
                            total = total + (o.getHarga() * o.getJumlah()) + o.getEmbalase() + o.getTuslah();
                        }
                        ppn = (total * 10) / 100;
                        totalPpn = total + ppn;
                        lblTotal.setText("Total : " + Utils.format(total, 0));
//                        lblPpn.setText("PPN : " + Utils.format(ppn, 0));
//                        lblTotalPpn.setText("Total + PPN : " + Utils.format(totalPpn, 0));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya data resep yang sudah divalidasi yang bisa dihapus!");
                }

            }

        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void mnEditAturanPakeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEditAturanPakeActionPerformed
        // TODO add your handling code here:
        int barisPilihan = tblEditor.convertRowIndexToModel(tblEditor.getSelectedRow());
        if (barisPilihan > -1) {
            if (tblEditor.getSelectedColumn() == 11) {
                aturanpakai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                aturanpakai.setLocationRelativeTo(internalFrame1);
                aturanpakai.setVisible(true);
            }

        }
    }//GEN-LAST:event_mnEditAturanPakeActionPerformed

    private void MnNotaObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNotaObatActionPerformed
        // TODO add your handling code here:
        int baris = tblData.convertRowIndexToModel(tblData.getSelectedRow());
        if (baris > -1) {
            DataEResep resep = model.get(tblData.convertRowIndexToModel(baris));
            double globalTot = 0;
            if (resep.getStatus().equals(Resep.STATUS_BELUM_VERIFIKASI)) {
                JOptionPane.showMessageDialog(null, "Tidak bisa mencetak Nota Resep karena Resep dengan No. " + resep.getNoResep() + " belum divalidasi");
            } else {
                RegPeriksa reg = RegPeriksaDao.get(resep.getNoRawat());
                List<ObatResep> farmasis = PemberianObatDetailDao.getObatValidasiByNoResep(resep.getNoResep(), kdBangsal, resep.getJaminan(), reg.getKdPj());
                List<NotaResep> notas = new LinkedList<>();
                NotaResep nota = new NotaResep();
                nota.setNoresep(resep.getNoResep());
                nota.setDokter(resep.getDokter());
                nota.setPasien(resep.getPasien());
                nota.setTglResep(resep.getTglResep());

                List<RincianResepVerifikasi> rincians = new LinkedList<>();

                if (farmasis.size() > 0) {
                    List<ObatResep> farmasiNonRaciks = new LinkedList<>();
                    List<ObatResep> farmasiRaciks = new LinkedList<>();
                    for (ObatResep f : farmasis) {
                        if (Utils.isBlank(f.getKodeRacikan())) {
                            if (!farmasiNonRaciks.contains(f)) {
                                farmasiNonRaciks.add(f);
                            }
                        } else {
                            if (!farmasiRaciks.contains(f)) {
                                farmasiRaciks.add(f);
                            }
                        }
                    }

                    if (farmasiNonRaciks.size() > 0) {
                        for (ObatResep f : farmasiNonRaciks) {
                            RincianResepVerifikasi r = new RincianResepVerifikasi();
                            r.setKodeObat(f.getKodeObat());
                            r.setNamaObat(f.getNamaObat());
                            double total = (f.getJumlah() * f.getHarga()) + f.getEmbalase() + f.getTuslah();
                            globalTot = globalTot + total;
                            r.setRincian(Utils.format(f.getJumlah(), 0) + " x ( " + Utils.format(f.getHarga(), 0) + " + " + Utils.format(f.getEmbalase(), 0) + " + " + Utils.format(f.getTuslah(), 0) + " ) = " + Utils.format(total, 0));
                            r.setAturanPakai(f.getAturanPakai());
                            rincians.add(r);
                        }

                    }

                    if (farmasiRaciks.size() > 0) {
                        Collections.sort(farmasiRaciks, Comparator.comparing(ObatResep::getKodeRacikan));
                        String rck = null;
                        int urut = 0;
                        for (ObatResep f : farmasiRaciks) {
                            urut++;
                            if (Utils.isBlank(rck)) {
                                rck = f.getRacikan();
                                RincianResepVerifikasi r = new RincianResepVerifikasi();
                                ObatResep obatRck = PemberianObatDetailDao.getObatRacikanByNoResep(resep.getNoResep(), f.getKodeRacikan());
                                r.setKodeObat(f.getKodeRacikan());
                                r.setRacikan(f.getRacikan());
                                r.setNamaObat(obatRck.getMetodeRacik());
                                r.setRincian(String.valueOf(obatRck.getJmlRacik()));
                                r.setAturanPakai(obatRck.getAturanPakai());
                                r.setUrutan(urut);
                                rincians.add(r);
                            } else {
                                if (!rck.equals(f.getRacikan())) {
                                    RincianResepVerifikasi r = new RincianResepVerifikasi();
                                    ObatResep obatRck = PemberianObatDetailDao.getObatRacikanByNoResep(resep.getNoResep(), f.getKodeRacikan());
                                    r.setKodeObat(f.getKodeRacikan());
                                    r.setNamaObat(obatRck.getMetodeRacik());
                                    r.setRacikan(f.getRacikan());
                                    r.setRincian(String.valueOf(obatRck.getJmlRacik()));
                                    r.setAturanPakai(obatRck.getAturanPakai());
                                    r.setUrutan(urut);
                                    rincians.add(r);
                                    rck = f.getRacikan();
                                }
                            }
                            RincianResepVerifikasi r = new RincianResepVerifikasi();
                            r.setKodeObat(f.getKodeObat());
                            r.setNamaObat(f.getNamaObat());
                            double total = (f.getJumlah() * f.getHarga()) + f.getEmbalase() + f.getTuslah();
                            globalTot = globalTot + total;
                            r.setRincian(Utils.format(f.getJumlah(), 0) + " x ( " + Utils.format(f.getHarga(), 0) + " + " + Utils.format(f.getEmbalase(), 0) + " + " + Utils.format(f.getTuslah(), 0) + " ) = " + Utils.format(total, 0));
                            r.setAturanPakai(f.getAturanPakai());
                            r.setUrutan(urut);
                            rincians.add(r);
                        }
                        Collections.sort(rincians, Comparator
                                .comparing(RincianResepVerifikasi::getUrutan));
                    }
                    RincianResepVerifikasi totalan = new RincianResepVerifikasi();
                    totalan.setNamaObat("Total Biaya Resep : ");
                    totalan.setRincian("Rp " + Utils.format(globalTot, 0) + ",-");
                    totalan.setAturanPakai("");
                    totalan.setRacikan(">>");
                    rincians.add(totalan);
                    nota.setDetails(rincians);
                    notas.add(nota);

                }
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                String reportName = "notaEresep.jasper";
                String folder = "report";
                Utils.printWithSub(reportName, folder, ".:: Nota EResep ::.", notas, param);
            }

        }

    }//GEN-LAST:event_MnNotaObatActionPerformed

    private void mnHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnHapusObatActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int baris = tblEditor.convertRowIndexToModel(tblEditor.getSelectedRow());
        if (baris > -1) {
            DataEResep resep = model.get(tblData.convertRowIndexToModel(row));
            ObatResep obat = modelPilihan.get(baris);
            if (resep.getStatus().equals(Resep.STATUS_BELUM_VERIFIKASI)) {
                int halo = JOptionPane.showConfirmDialog(null, "serius mau hapus obat : " + obat.getNamaObat(), "Perhatian", dialogButton);
                if (halo == 0) {
                    modelPilihan.remove(baris);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Anda tidak dapat menghapus obat : " + obat.getNamaObat() + ", karena Resep sudah diveriikasi");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih baris obat yang mau di hapus..");
        }
    }//GEN-LAST:event_mnHapusObatActionPerformed

    private void deleteObatSatuan() {
//        int dialogButton = JOptionPane.YES_NO_OPTION;
//        int wkwkw = JOptionPane.showConfirmDialog(null, "Serius mau menghapus obat ini..?", "Perhatian", dialogButton);
//        if (wkwkw == 0) {
//            int baris = tblFarmasi.convertRowIndexToModel(tblFarmasi.getSelectedRow());
//            DataEResep resep = model.get(tblData.convertRowIndexToModel(tblData.getSelectedRow()));
//            if (baris > -1) {
//                RincianResepVerifikasi oresep = modelFarmasi.get(tblFarmasi.convertRowIndexToModel(baris));
//                modelFarmasi.remove(baris);
//                ResepDao.deleteDataObatValidasiFarmasiSatuan(resep.getNoResep(), oresep.getKodeObat());
//            } else {
//                JOptionPane.showMessageDialog(null, "Silahkan pilih baris obat yang mau di hapus..");
//            }
//        }
    }

    private void clean() {
        racikanList = new LinkedList<>();
        model.removeAllElements();
        modelPilihan.removeAllElements();
        modelFarmasi.removeAllElements();
        modelDokter.removeAllElements();
        obatRacikan = null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDataEResepDokter dialog = new DlgDataEResepDokter(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnKeluar;
    private widget.PanelBiasa FormInput;
    private javax.swing.JMenuItem MnAllStock;
    private javax.swing.JMenuItem MnEtiket;
    private javax.swing.JMenuItem MnNotaObat;
    private javax.swing.JMenuItem MnPackaging;
    private javax.swing.JPopupMenu Popup;
    private javax.swing.JPopupMenu PopupEditorFarmasi;
    private widget.Button btnAddObat;
    private widget.Button btnCariData;
    private widget.Button btnEdit;
    private widget.Button btnHapus;
    private widget.Button btnSimpan;
    private widget.Tanggal cmbTanggalTo;
    private widget.Tanggal cmbTanggalfrom;
    private widget.ComboBox cmbTarif;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label2;
    private widget.Label label3;
    private widget.Label label4;
    private widget.Label label5;
    private widget.Label label6;
    private widget.Label label7;
    private widget.Label label9;
    private widget.Label lbAlergi;
    private widget.Label lbBB;
    private widget.Label lbSuhuBadan;
    private widget.Label lbTB;
    private widget.Label lbTekanDarah;
    private widget.Label lblAlamat;
    private widget.Label lblDepo;
    private widget.Label lblDepoResep;
    private widget.Label lblPasien;
    private widget.Label lblTelp;
    private widget.Label lblTotal;
    private javax.swing.JMenuItem mnAturanPakai;
    private javax.swing.JMenuItem mnEditAturanPake;
    private javax.swing.JMenuItem mnHapusObat;
    private javax.swing.JMenuItem mnTerimaPasien;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.PanelBiasa panelBiasa3;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelDetailTotal;
    private widget.PanelBiasa panelResep;
    private widget.panelisi panelSulapan;
    private widget.panelisi panelisi3;
    private javax.swing.ButtonGroup perawatanGrup;
    private javax.swing.JPopupMenu printPopup;
    private javax.swing.JRadioButton rdoRajal;
    private javax.swing.JRadioButton rdoRanap;
    private widget.ScrollPane scrollDetail;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tblData;
    private widget.Table tblDokter;
    private widget.Table tblEditor;
    private widget.Table tblFarmasi;
    private widget.TextBox txtCari;
    // End of variables declaration//GEN-END:variables

    private void showResepData(List<DataEResep> dataList, List<DataEResep> dataRacikanList) {
        model.removeAllElements();
        List<DataEResep> newDataList = new LinkedList<>();
        if (dataList.size() > 0) {
            dataList.forEach((d) -> {
                if (dataRacikanList.size() > 0) {
                    dataRacikanList.forEach((r) -> {
                        if (d.getNoResep().equals(r.getNoResep())) {
                            newDataList.remove(d);
                            d.getObatDetails().addAll(r.getObatDetails());
                            newDataList.add(d);
                        } else {
                            if (!newDataList.contains(d)) {
                                newDataList.add(d);
                            }
                            if (!newDataList.contains(r)) {
                                newDataList.add(r);
                            }
                        }
                    });
                } else {
                    newDataList.add(d);
                }
            });
        } else if (dataRacikanList.size() > 0) {
            newDataList.addAll(dataRacikanList);
        }
        newDataList.sort(Comparator.comparing(DataEResep::getNoResep));
        model.add(newDataList);
        tblData.setModel(model);
        rowSorter = new TableRowSorter<>(tblData.getModel());
        tblData.setRowSorter(rowSorter);
    }

    private void removeDuplicate(ObatResep obatResep) {
        for (Iterator<ObatResep> i = modelPilihan.getAll().iterator(); i.hasNext();) {
            ObatResep obat = i.next();
            if (obat.getKodeObat().equals(obatResep.getKodeObat())) {
                int index = modelPilihan.getAll().indexOf(obat);
                modelPilihan.remove(index);
                break;
            }
        }
        modelPilihan.add(obatResep);
    }
}

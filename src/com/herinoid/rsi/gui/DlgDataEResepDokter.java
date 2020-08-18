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
import com.herinoid.rsi.dao.PemberianObatDetailDao;
import static com.herinoid.rsi.dao.ResepDao.getObatResepDetail;
import com.herinoid.rsi.gui.dialog.DlgCariObat;
import com.herinoid.rsi.model.Resep;
import com.herinoid.rsi.model.RincianResepVerifikasi;
import com.herinoid.rsi.table.TabelResepRincian;
import com.herinoid.rsi.util.Konstan;
import com.herinoid.rsi.widget.KeySelectionRenderer;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import widget.ComboBox;

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
                        if (Utils.isBlank(data.getValidasi())) {
                            panelSulapan.remove(panelResep);
                            panelDetailTotal.setVisible(true);
                            scrollDetail.setVisible(true);
                            panelResep.setVisible(false);
                            if (data.getJaminan().equalsIgnoreCase(Konstan.PASIEN_BPJS_KESEHATAN)) {
                                kategoriObat = "K01";
                            } else {
                                kategoriObat = "K02";
                            }
                            List<ObatResep> list = data.getObatDetails();
                            modelPilihan.removeAllElements();
                            modelPilihan.add(list);
                            tblEditor.setModel(modelPilihan);
                            double total = 0;
                            double ppn = 0;
                            double totalPpn = 0;
                            for (ObatResep o : list) {
                                total = total + (o.getHarga() * o.getJumlah()) + o.getEmbalase() + o.getTuslah();
                            }
                            ppn = (total * 10) / 100;
                            totalPpn = total + ppn;
                            lblTotal.setText("Total : " + Utils.format(total, 0));
                            lblPpn.setText("PPN : " + Utils.format(ppn, 0));
                            lblTotalPpn.setText("Total + PPN : " + Utils.format(totalPpn, 0));
                        } else {
//                            panelSulapan.remove(Popup);
                            setResepVerifikasi(data.getNoRawat(), kdBangsal, data.getNoResep());
                            panelSulapan.add(panelResep);
                            panelSulapan.repaint();
                            panelDetailTotal.setVisible(false);
                            scrollDetail.setVisible(false);
                            panelResep.setVisible(true);
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
    }

    public void setData(String kodeDepo, String kategoriObat, String jenisPasien) {
        this.tarif = jenisPasien;
        model.removeAllElements();
//        model.add(ObatDao.getObatByCategory(kodeDepo, kategoriObat, jenisPasien));
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

    private void setResepVerifikasi(String noRawat, String kdBangsal, String noresep) {
        List<ObatResep> farmasis = PemberianObatDetailDao.getObatValidasiByNoResep(noresep, kdBangsal);
        List<ObatResep> dokters = PemberianObatDetailDao.getResepByNoresep(noresep, kdBangsal);
        List<ObatResep> dokterRacikans = ResepDao.getObatResepRacikanDetail(noresep, kdBangsal, cmbTarif.getSelectedItem().toString());
        List<RincianResepVerifikasi> rincians = new LinkedList<>();
        if (farmasis.size() > 0) {
            for (ObatResep f : farmasis) {
                RincianResepVerifikasi r = new RincianResepVerifikasi();
                r.setKodeObat(f.getKodeObat());
                r.setNamaObat(f.getNamaObat());
                double total = (f.getJumlah() * f.getHarga()) + f.getEmbalase() + f.getTuslah();
                r.setRincian(Utils.format(f.getJumlah(), 0) + " x ( " + Utils.format(f.getHarga(), 0) + " + " + Utils.format(f.getEmbalase(), 0) + " + " + Utils.format(f.getTuslah(), 0) + " ) = " + Utils.format(total, 0));
                r.setAturanPakai(f.getAturanPakai());
                rincians.add(r);
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
            dokterRacikans.stream().map((f) -> {
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
        MnAllStock = new javax.swing.JMenuItem();
        popHapusRowObat = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        KdPj = new widget.TextBox();
        kelas = new widget.TextBox();
        perawatanGrup = new javax.swing.ButtonGroup();
        printPopup = new javax.swing.JPopupMenu();
        mnTerimaPasien = new javax.swing.JMenuItem();
        MnAturanPakai1 = new javax.swing.JMenuItem();
        MnAturanPakai2 = new javax.swing.JMenuItem();
        MnAturanPakai3 = new javax.swing.JMenuItem();
        panelResep = new widget.PanelBiasa();
        panelBiasa4 = new widget.PanelBiasa();
        scrollPane1 = new widget.ScrollPane();
        tblFarmasi = new widget.Table();
        panelBiasa3 = new widget.PanelBiasa();
        scrollPane3 = new widget.ScrollPane();
        tblDokter = new widget.Table();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        btnAddObat = new widget.Button();
        label13 = new widget.Label();
        btnSimpan = new widget.Button();
        label2 = new widget.Label();
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
        lblPpn = new widget.Label();
        lblTotalPpn = new widget.Label();
        lblDepo = new widget.Label();

        Popup.setName("Popup"); // NOI18N

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

        popHapusRowObat.setBackground(new java.awt.Color(255, 255, 255));
        popHapusRowObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        popHapusRowObat.setForeground(new java.awt.Color(50, 50, 50));
        popHapusRowObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Delete.png"))); // NOI18N
        popHapusRowObat.setText("Hapus Obat");
        popHapusRowObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        popHapusRowObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        popHapusRowObat.setName("popHapusRowObat"); // NOI18N
        popHapusRowObat.setPreferredSize(new java.awt.Dimension(200, 25));
        popHapusRowObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popHapusRowObatActionPerformed(evt);
            }
        });
        Popup.add(popHapusRowObat);

        TNoRw.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N

        kelas.setHighlighter(null);
        kelas.setName("kelas"); // NOI18N

        printPopup.setName("printPopup"); // NOI18N

        mnTerimaPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientMale.png"))); // NOI18N
        mnTerimaPasien.setText("Diterima Pasien");
        mnTerimaPasien.setName("mnTerimaPasien"); // NOI18N
        mnTerimaPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnTerimaPasienActionPerformed(evt);
            }
        });
        printPopup.add(mnTerimaPasien);

        MnAturanPakai1.setBackground(new java.awt.Color(255, 255, 255));
        MnAturanPakai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAturanPakai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PrinterSettings.png"))); // NOI18N
        MnAturanPakai1.setText("Cetak Aturan Pakai Model 1");
        MnAturanPakai1.setName("MnAturanPakai1"); // NOI18N
        printPopup.add(MnAturanPakai1);

        MnAturanPakai2.setBackground(new java.awt.Color(255, 255, 255));
        MnAturanPakai2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAturanPakai2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PrinterSettings.png"))); // NOI18N
        MnAturanPakai2.setText("Cetak Aturan Pakai Model 2");
        MnAturanPakai2.setName("MnAturanPakai2"); // NOI18N
        printPopup.add(MnAturanPakai2);

        MnAturanPakai3.setBackground(new java.awt.Color(255, 255, 255));
        MnAturanPakai3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAturanPakai3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PrinterSettings.png"))); // NOI18N
        MnAturanPakai3.setText("Cetak Aturan Pakai Model 3");
        MnAturanPakai3.setName("MnAturanPakai3"); // NOI18N
        printPopup.add(MnAturanPakai3);

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
        tblFarmasi.setName("tblFarmasi"); // NOI18N
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

        label2.setText("  ");
        label2.setName("label2"); // NOI18N
        panelisi3.add(label2);

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
        FormInput.setPreferredSize(new java.awt.Dimension(864, 79));
        FormInput.setLayout(null);

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tarif : ");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(370, 40, 60, 23);

        cmbTanggalTo.setForeground(new java.awt.Color(50, 70, 50));
        cmbTanggalTo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2020" }));
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
        cmbTanggalfrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-08-2020" }));
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

        lblPpn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPpn.setText("PPN : ");
        lblPpn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPpn.setName("lblPpn"); // NOI18N
        panelDetailTotal.add(lblPpn);

        lblTotalPpn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPpn.setText("Total + PPN : ");
        lblTotalPpn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalPpn.setName("lblTotalPpn"); // NOI18N
        panelDetailTotal.add(lblTotalPpn);

        lblDepo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDepo.setText("Depo : ");
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

        lblDepo.setText("Depo : " + bangsal.getKode() + "::" + bangsal.getNama());
        List<DataEResep> dataList = ResepDao.getResepByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString());
        List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString());
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
            if (resep.getStatus().equals(Resep.STATUS_BELUM_VERIFIKASI)) {
                int emmmm = JOptionPane.showConfirmDialog(null, "Anda akan memverifikasi data resep, data yang sudah di verifikasi tidak dapat di verifikasi ulang. silahkan teliti kembali", "Perhatian", dialogButton);
                if (emmmm == 0) {
                    if (resep.getObatDetails().size() > 0) {
                        boolean sukses = ResepDao.saveDetailPemberianObat(sttRawat, resep.getNoRawat(), newDetails, depo, resep.getNoResep());
                        if (sukses) {
                            ResepDao.updateValidasi(resep.getNoRawat(), resep.getNoResep(), new Date(), newDetails);
                            List<DataEResep> dataList = ResepDao.getResepByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString());
                            List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString());
                            showResepData(dataList, dataRacikanList);
//                            model.removeAllElements();
//                            model.add(dataList);
//                            tblData.setModel(model);
//                            rowSorter = new TableRowSorter<>(tblData.getModel());
//                            tblData.setRowSorter(rowSorter);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "data obat kosong");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Data sudah diverifikasi, anda tidak dapat memverifikasi ulang..");
            }

        }

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tblEditorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEditorKeyReleased
        // TODO add your handling code here:
        List<ObatResep> list = modelPilihan.getAll();
        double total = 0;
        double ppn = 0;
        double totalPpn = 0;
        for (ObatResep o : list) {
            total = total + (o.getHarga() * o.getJumlah()) + o.getEmbalase() + o.getTuslah();
        }
        ppn = (total * 10) / 100;
        totalPpn = total + ppn;
        lblTotal.setText("Total : " + Utils.format(total, 0));
        lblPpn.setText("PPN : " + Utils.format(ppn, 0));
        lblTotalPpn.setText("Total + PPN : " + Utils.format(totalPpn, 0));
    }//GEN-LAST:event_tblEditorKeyReleased

    private void rdoRajalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRajalActionPerformed
        // TODO add your handling code here:
        sttRawat = "Ralan";
    }//GEN-LAST:event_rdoRajalActionPerformed

    private void rdoRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoRanapActionPerformed
        // TODO add your handling code here:
        sttRawat = "Ranap";
    }//GEN-LAST:event_rdoRanapActionPerformed

    private void popHapusRowObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popHapusRowObatActionPerformed
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

    }//GEN-LAST:event_popHapusRowObatActionPerformed

    private void tblEditorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEditorMouseClicked
        // TODO add your handling code here:

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
                        List<DataEResep> dataList = ResepDao.getResepByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString());
                        List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDateAndDepo(Utils.formatDb(cmbTanggalfrom.getDate()), Utils.formatDb(cmbTanggalTo.getDate()), depo, cmbTarif.getSelectedItem().toString());
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
    private widget.TextBox KdPj;
    private javax.swing.JMenuItem MnAllStock;
    private javax.swing.JMenuItem MnAturanPakai1;
    private javax.swing.JMenuItem MnAturanPakai2;
    private javax.swing.JMenuItem MnAturanPakai3;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TNoRw;
    private widget.Button btnAddObat;
    private widget.Button btnCariData;
    private widget.Button btnSimpan;
    private widget.Tanggal cmbTanggalTo;
    private widget.Tanggal cmbTanggalfrom;
    private widget.ComboBox cmbTarif;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kelas;
    private widget.Label label1;
    private widget.Label label13;
    private widget.Label label2;
    private widget.Label label3;
    private widget.Label label4;
    private widget.Label label5;
    private widget.Label label6;
    private widget.Label lblDepo;
    private widget.Label lblPpn;
    private widget.Label lblTotal;
    private widget.Label lblTotalPpn;
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
    private javax.swing.JMenuItem popHapusRowObat;
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
}

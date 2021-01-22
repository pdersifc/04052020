/*
  
 */
package com.herinoid.rsi.gui.dialog;

import com.herinoid.rsi.dao.ResepDao;
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
import com.herinoid.rsi.util.Utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.herinoid.rsi.dao.BangsalDao;
import com.herinoid.rsi.dao.PasienDao;
import com.herinoid.rsi.dao.PemberianObatDetailDao;
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
import fungsi.akses;
import fungsi.sekuel;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JOptionPane;
import widget.ComboBox;
import inventory.DlgAturanPakai;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author herinoid
 */
public final class DlgHistoriResepPasien extends javax.swing.JDialog {

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
    private List<ObatResep> obatReseps;
    
    private boolean isAmbil = false;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgHistoriResepPasien(java.awt.Frame parent, boolean modal) {
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

            kdBangsal = pro.getProperty("DEPOOBAT");

            tblData.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    int selected = tblData.getSelectedRow();
                    if (selected != -1) {
                        DataEResep data = model.getAll().get(selected);
                        Pasien pasien = PasienDao.get(data.getNorm());
                        lblPasien.setText(data.getPasien());
                        lblTelp.setText(pasien.getNoTelp());
                        lblAlamat.setText(pasien.getAlamat());
//                        if (Utils.isBlank(data.getValidasi())) {
                        panelDetailTotal.setVisible(true);
                        scrollDetail.setVisible(true);
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
//                            double total = 0;
//                            double ppn = 0;
//                            double totalPpn = 0;
//                            for (ObatResep o : list) {
//                                total = total + (o.getHarga() * o.getJumlah()) + o.getEmbalase() + o.getTuslah();
//                            }
//                            ppn = (total * 10) / 100;
//                            totalPpn = total + ppn;
//                            lblTotal.setText("Total : " + Utils.format(total, 0));
//                            lblPpn.setText("PPN : " + Utils.format(ppn, 0));
//                            lblTotalPpn.setText("Total + PPN : " + Utils.format(totalPpn, 0));
//                        } 
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DlgHistoriResepPasien.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DlgHistoriResepPasien.class.getName()).log(Level.SEVERE, null, ex);
        }

        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (aturanpakai.getTable().getSelectedRow() != -1) {
                    tblEditor.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tblEditor.getSelectedRow(), 11);
                    tblEditor.requestFocus();
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

    public List<ObatResep> getData() {
        return this.obatReseps;
    }

    public boolean isDuplikate() {
        return this.isAmbil;
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

    public void setData(String kodeDokter, String norm, String depo) {
        model.removeAllElements();
        List<DataEResep> dataList = ResepDao.getResepByDokterAndPasien(kodeDokter, norm, depo);
        List<DataEResep> dataRacikanList = ResepDao.getResepRacikanByDokterAndPasien(kodeDokter, norm, depo);
        showResepData(dataList, dataRacikanList);
        tblData.setModel(model);
        rowSorter = new TableRowSorter<>(tblData.getModel());
        tblData.setRowSorter(rowSorter);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label13 = new widget.Label();
        btnSimpan = new widget.Button();
        label2 = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel7 = new widget.Label();
        lbTekanDarah = new widget.Label();
        lbSuhuBadan = new widget.Label();
        lbAlergi = new widget.Label();
        label7 = new widget.Label();
        lblPasien = new widget.Label();
        label9 = new widget.Label();
        lblTelp = new widget.Label();
        label11 = new widget.Label();
        lblAlamat = new widget.Label();
        label8 = new widget.Label();
        lblAlamat1 = new widget.Label();
        jPanel1 = new javax.swing.JPanel();
        panelBiasa1 = new widget.PanelBiasa();
        panelBiasa2 = new widget.PanelBiasa();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Histori E-Resep Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(500, 23));
        panelisi3.add(label13);

        btnSimpan.setForeground(new java.awt.Color(0, 0, 0));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnSimpan.setText("Duplikasi Resep");
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
        FormInput.setPreferredSize(new java.awt.Dimension(983, 95));
        FormInput.setLayout(null);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("S/D");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel7);
        jLabel7.setBounds(240, 40, 20, 23);

        lbTekanDarah.setForeground(new java.awt.Color(0, 0, 0));
        lbTekanDarah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTekanDarah.setName("lbTekanDarah"); // NOI18N
        lbTekanDarah.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(lbTekanDarah);
        lbTekanDarah.setBounds(850, 50, 80, 23);

        lbSuhuBadan.setForeground(new java.awt.Color(0, 0, 0));
        lbSuhuBadan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbSuhuBadan.setName("lbSuhuBadan"); // NOI18N
        lbSuhuBadan.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(lbSuhuBadan);
        lbSuhuBadan.setBounds(850, 70, 80, 23);

        lbAlergi.setForeground(new java.awt.Color(0, 0, 0));
        lbAlergi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbAlergi.setName("lbAlergi"); // NOI18N
        lbAlergi.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(lbAlergi);
        lbAlergi.setBounds(850, 90, 250, 23);

        label7.setText("Pasien :");
        label7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(30, 10, 80, 20);

        lblPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPasien.setText("Pasien");
        lblPasien.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPasien.setName("lblPasien"); // NOI18N
        FormInput.add(lblPasien);
        lblPasien.setBounds(120, 10, 410, 20);

        label9.setText("No. Telp :");
        label9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label9.setName("label9"); // NOI18N
        FormInput.add(label9);
        label9.setBounds(40, 30, 70, 20);

        lblTelp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTelp.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTelp.setName("lblTelp"); // NOI18N
        FormInput.add(lblTelp);
        lblTelp.setBounds(120, 30, 130, 20);

        label11.setText("Alamat :");
        label11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label11.setName("label11"); // NOI18N
        FormInput.add(label11);
        label11.setBounds(50, 50, 60, 20);

        lblAlamat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAlamat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAlamat.setName("lblAlamat"); // NOI18N
        FormInput.add(lblAlamat);
        lblAlamat.setBounds(120, 50, 380, 20);

        label8.setText("Dokter : ");
        label8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label8.setName("label8"); // NOI18N
        FormInput.add(label8);
        label8.setBounds(20, 70, 90, 20);

        lblAlamat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAlamat1.setText("Dokter");
        lblAlamat1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAlamat1.setName("lblAlamat1"); // NOI18N
        FormInput.add(lblAlamat1);
        lblAlamat1.setBounds(120, 70, 380, 20);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        panelBiasa1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: History E-RESEP Pasien ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(new java.awt.BorderLayout());

        panelBiasa2.setName("panelBiasa2"); // NOI18N
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
        tblData.setName("tblData"); // NOI18N
        tblData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataMouseClicked(evt);
            }
        });
        scrollPane2.setViewportView(tblData);

        panelBiasa1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelBiasa1);

        panelSulapan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: Detail Histori Data E-Resep ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
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

        panelSulapan.add(panelDetailTotal, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(panelSulapan);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        isAmbil = false;
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        // TODO add your handling code here:
        row = tblData.getSelectedRow();
    }//GEN-LAST:event_tblDataMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        if (row > -1) {
            isAmbil = true;
            obatReseps = modelPilihan.getAll();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data resep yang mau di duplikat..");
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
//        lblTotal.setText("Total : " + Utils.format(total, 0));
//        lblPpn.setText("PPN : " + Utils.format(ppn, 0));
//        lblTotalPpn.setText("Total + PPN : " + Utils.format(totalPpn, 0));
    }//GEN-LAST:event_tblEditorKeyReleased

    private void tblEditorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEditorMouseClicked
        // TODO add your handling code here:
        int barisPilihan = tblEditor.convertRowIndexToModel(tblEditor.getSelectedRow());
        if (barisPilihan > -1) {
            if (tblEditor.getSelectedColumn() == 11) {
                aturanpakai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                aturanpakai.setLocationRelativeTo(internalFrame1);
                aturanpakai.setVisible(true);
            }
        }
    }//GEN-LAST:event_tblEditorMouseClicked

    public void clean() {
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
            DlgHistoriResepPasien dialog = new DlgHistoriResepPasien(new javax.swing.JFrame(), true);
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
    private widget.Button btnSimpan;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label2;
    private widget.Label label3;
    private widget.Label label4;
    private widget.Label label5;
    private widget.Label label6;
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.Label lbAlergi;
    private widget.Label lbSuhuBadan;
    private widget.Label lbTekanDarah;
    private widget.Label lblAlamat;
    private widget.Label lblAlamat1;
    private widget.Label lblPasien;
    private widget.Label lblTelp;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelDetailTotal;
    private widget.panelisi panelSulapan;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollDetail;
    private widget.ScrollPane scrollPane2;
    private widget.Table tblData;
    private widget.Table tblEditor;
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

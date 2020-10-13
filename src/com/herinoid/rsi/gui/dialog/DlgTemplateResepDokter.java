/*
  
 */
package com.herinoid.rsi.gui.dialog;

import com.herinoid.rsi.dao.ResepDao;
import com.herinoid.rsi.gui.dialog.DialogRacikanResep;
import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.DataEResep;
import com.herinoid.rsi.model.ObatResep;
import com.herinoid.rsi.table.TabelTemplateResep;
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
import com.herinoid.rsi.dao.DokterDao;
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
import com.herinoid.rsi.dao.ResepTemplateDao;
import com.herinoid.rsi.model.DokterRajal;
import com.herinoid.rsi.model.Pasien;
import com.herinoid.rsi.model.ResepTemplate;
import com.herinoid.rsi.table.TabelTemplateResep;
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
public final class DlgTemplateResepDokter extends javax.swing.JDialog {

    private TabelTemplateResep model;
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
    private String kdBangsal, tarif,kdDokter;
    private sekuel Sequel = new sekuel();
    private DlgAturanPakai aturanpakai = new DlgAturanPakai(null, false);
    private List<ObatResep> obatReseps;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgTemplateResepDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            racikanList = new LinkedList<>();
            model = new TabelTemplateResep();
            modelPilihan = new TabelObatResepEditor();
            tblDetail.setModel(modelPilihan);
            modelFarmasi = new TabelResepRincian();
            modelDokter = new TabelResepRincian();
            this.setLocation(10, 2);
            setSize(656, 250);
            pro.loadFromXML(new FileInputStream("setting/database.xml"));

            kdBangsal = pro.getProperty("DEPOOBAT");

            tblTemplate.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    int selected = tblTemplate.getSelectedRow();
                    if (selected != -1) {
                        ResepTemplate data = model.getAll().get(selected);
                        lblNamaTemplate.setText(data.getNamaTemplate());
                        panelDetailTotal.setVisible(true);
                        scrollDetail.setVisible(true);
                        modelPilihan.removeAllElements();
                        if(data.isRacikan()){
                            List<ObatResep> listDetailRacikan = data.getObatTemplateRacikanDetail();
                            modelPilihan.add(listDetailRacikan);
                        }else{
                            List<ObatResep> listTunggal = data.getObatTemplateDetail();
                            modelPilihan.add(listTunggal);
                        }
                        tblDetail.setModel(modelPilihan);
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DlgTemplateResepDokter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DlgTemplateResepDokter.class.getName()).log(Level.SEVERE, null, ex);
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
                    tblDetail.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tblDetail.getSelectedRow(), 11);
                    tblDetail.requestFocus();
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
    
    private void setComboList() {
        DokterRajal doter = null;
        List<DokterRajal> dokters = DokterDao.findDokters();
        for (DokterRajal m : dokters) {
            if(m.getKodeDokter().equals(kdDokter)){
                doter = m;
            }
            cmbDokter.addItem(m);
        }
        KeySelectionRenderer renderer = new KeySelectionRenderer(cmbDokter) {
            @Override
            public String getDisplayValue(Object value) {
                DokterRajal dokter = (DokterRajal) value;
                return dokter.getNamaDokter();
            }
        };
        if(doter!=null){
            cmbDokter.getModel().setSelectedItem(doter);
        }
        
    }

    public void setData(String kodeDokter, String depo) {
        this.kdDokter = kodeDokter;
        model.removeAllElements();
        List<ResepTemplate> dataList = ResepTemplateDao.getTemplateByDokter(kodeDokter,depo);
        model.add(dataList);
        tblTemplate.setModel(model);
        rowSorter = new TableRowSorter<>(tblTemplate.getModel());
        tblTemplate.setRowSorter(rowSorter);
        setComboList();
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
        label7 = new widget.Label();
        cmbDokter = new widget.ComboBox();
        label1 = new widget.Label();
        lblNamaTemplate = new widget.Label();
        jPanel1 = new javax.swing.JPanel();
        panelBiasa1 = new widget.PanelBiasa();
        panelBiasa2 = new widget.PanelBiasa();
        scrollPane2 = new widget.ScrollPane();
        tblTemplate = new widget.Table();
        panelSulapan = new widget.panelisi();
        scrollDetail = new widget.ScrollPane();
        tblDetail = new widget.Table();
        panelDetailTotal = new widget.panelisi();
        label6 = new widget.Label();
        label5 = new widget.Label();
        label4 = new widget.Label();
        label3 = new widget.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Template E-Resep Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
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
        btnSimpan.setText("Ambil Template");
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
        FormInput.setPreferredSize(new java.awt.Dimension(983, 70));
        FormInput.setLayout(null);

        label7.setText("Dokter :");
        label7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(20, 10, 110, 20);

        cmbDokter.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbDokter.setName("cmbDokter"); // NOI18N
        cmbDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDokterActionPerformed(evt);
            }
        });
        FormInput.add(cmbDokter);
        cmbDokter.setBounds(140, 10, 340, 21);

        label1.setText("Nama Template :");
        label1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label1.setName("label1"); // NOI18N
        FormInput.add(label1);
        label1.setBounds(10, 40, 120, 20);

        lblNamaTemplate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNamaTemplate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNamaTemplate.setName("lblNamaTemplate"); // NOI18N
        FormInput.add(lblNamaTemplate);
        lblNamaTemplate.setBounds(140, 40, 440, 20);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(2, 0));

        panelBiasa1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: Template E-RESEP Dokter ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(new java.awt.BorderLayout());

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa1.add(panelBiasa2, java.awt.BorderLayout.PAGE_START);

        scrollPane2.setName("scrollPane2"); // NOI18N

        tblTemplate.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTemplate.setName("tblTemplate"); // NOI18N
        tblTemplate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTemplateMouseClicked(evt);
            }
        });
        scrollPane2.setViewportView(tblTemplate);

        panelBiasa1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelBiasa1);

        panelSulapan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: Data Obat E-Resep Template ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelSulapan.setName("panelSulapan"); // NOI18N
        panelSulapan.setLayout(new java.awt.BorderLayout());

        scrollDetail.setName("scrollDetail"); // NOI18N

        tblDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetail.setName("tblDetail"); // NOI18N
        tblDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetailMouseClicked(evt);
            }
        });
        tblDetail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblDetailKeyReleased(evt);
            }
        });
        scrollDetail.setViewportView(tblDetail);

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
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void tblTemplateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTemplateMouseClicked
        // TODO add your handling code here:
        row = tblTemplate.getSelectedRow();
        ResepTemplate data = model.getAll().get(row);
        lblNamaTemplate.setText(data.getNamaTemplate());
    }//GEN-LAST:event_tblTemplateMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        if (row > -1) {
            obatReseps = modelPilihan.getAll();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Pilih data resep yang mau di duplikat..");
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tblDetailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDetailKeyReleased
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
    }//GEN-LAST:event_tblDetailKeyReleased

    private void tblDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetailMouseClicked
        // TODO add your handling code here:
        int barisPilihan = tblDetail.convertRowIndexToModel(tblDetail.getSelectedRow());
        if (barisPilihan > -1) {
            if (tblDetail.getSelectedColumn() == 11) {
                aturanpakai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                aturanpakai.setLocationRelativeTo(internalFrame1);
                aturanpakai.setVisible(true);
            }
        }
    }//GEN-LAST:event_tblDetailMouseClicked

    private void cmbDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDokterActionPerformed
        // TODO add your handling code here:        
        DokterRajal dokter = (DokterRajal)cmbDokter.getItemAt(cmbDokter.getSelectedIndex());
        model.removeAllElements();
        List<ResepTemplate> dataList = ResepTemplateDao.getTemplateByDokter(dokter.getKodeDokter(),kdBangsal);
        model.add(dataList);
        tblTemplate.setModel(model);
        rowSorter = new TableRowSorter<>(tblTemplate.getModel());
        tblTemplate.setRowSorter(rowSorter);
    }//GEN-LAST:event_cmbDokterActionPerformed

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
            DlgTemplateResepDokter dialog = new DlgTemplateResepDokter(new javax.swing.JFrame(), true);
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
    private widget.ComboBox cmbDokter;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.Label label1;
    private widget.Label label13;
    private widget.Label label2;
    private widget.Label label3;
    private widget.Label label4;
    private widget.Label label5;
    private widget.Label label6;
    private widget.Label label7;
    private widget.Label lblNamaTemplate;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelDetailTotal;
    private widget.panelisi panelSulapan;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollDetail;
    private widget.ScrollPane scrollPane2;
    private widget.Table tblDetail;
    private widget.Table tblTemplate;
    // End of variables declaration//GEN-END:variables

    private void showResepData(List<ResepTemplate> dataList, List<ResepTemplate> dataRacikanList) {
        model.removeAllElements();
        List<ResepTemplate> newDataList = new LinkedList<>();
        if (dataList.size() > 0) {
            dataList.forEach((d) -> {
                if (dataRacikanList.size() > 0) {
                    dataRacikanList.forEach((r) -> {
                        if (d.getCode().equals(r.getCode())) {
                            newDataList.remove(d);
                            d.getObatTemplateDetail().addAll(r.getObatTemplateDetail());
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
        newDataList.sort(Comparator.comparing(ResepTemplate::getCode));
        model.add(newDataList);
        tblTemplate.setModel(model);
        rowSorter = new TableRowSorter<>(tblTemplate.getModel());
        tblTemplate.setRowSorter(rowSorter);
    }
}

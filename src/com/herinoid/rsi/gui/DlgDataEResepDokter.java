/*
  
 */
package com.herinoid.rsi.gui;

import com.herinoid.rsi.dao.ObatDao;
import com.herinoid.rsi.dao.ResepDao;
import static com.herinoid.rsi.dao.ResepDao.getNoResepForUpdate;
import com.herinoid.rsi.gui.dialog.DialogAddQtyResepDokter;
import com.herinoid.rsi.gui.dialog.DialogRacikanResep;
import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ObatResep;
import com.herinoid.rsi.model.Resep;
import com.herinoid.rsi.table.TabelCariObatResep;
import com.herinoid.rsi.table.TabelObatResepPilihan;
import com.herinoid.rsi.util.Konstan;
import fungsi.validasi;
import inventory.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import kepegawaian.DlgCariDokter;

/**
 *
 * @author herinoid
 */
public final class DlgDataEResepDokter extends javax.swing.JDialog {

    private TabelCariObatResep model;
    private TabelObatResepPilihan modelPilihan;
    private TableRowSorter<TableModel> rowSorter;
    private DialogAddQtyResepDokter addQty = new DialogAddQtyResepDokter(null, false);
    private ObatResep obatFromDialog;
    private ObatResep obatRacikan;
    private List<ObatResep> racikanList;
    private DialogRacikanResep dlgRacikan = new DialogRacikanResep(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private validasi Valid = new validasi();

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgDataEResepDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        racikanList = new LinkedList<>();
        model = new TabelCariObatResep();
        modelPilihan = new TabelObatResepPilihan();
//        model.add(ObatDao.getObatByCategory("KMOBT", "K01", Konstan.PASIEN_RALAN));
//        tblObat.setModel(model);
//        rowSorter = new TableRowSorter<>(tblObat.getModel());
//        tblObat.setRowSorter(rowSorter);
        this.setLocation(10, 2);
        setSize(656, 250);

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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    txtKodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    txtNamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                }
                txtKodeDokter.requestFocus();
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

    public void setData(String kodeDepo, String kategoriObat, String jenisPasien) {
        model.removeAllElements();
        model.add(ObatDao.getObatByCategory(kodeDepo, kategoriObat, jenisPasien));
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

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        ppStok1 = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        KdPj = new widget.TextBox();
        kelas = new widget.TextBox();
        perawatanGrup = new javax.swing.ButtonGroup();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label13 = new widget.Label();
        btnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        cmbTanggalTo = new widget.Tanggal();
        cmbMnt = new widget.ComboBox();
        label21 = new widget.Label();
        txtKodeDokter = new widget.TextBox();
        txtNamaDokter = new widget.TextBox();
        BtnPilihDokter = new widget.Button();
        jLabel10 = new widget.Label();
        rdoRajal = new javax.swing.JRadioButton();
        rdoRanap = new javax.swing.JRadioButton();
        cmbTanggalfrom = new widget.Tanggal();
        jLabel6 = new widget.Label();
        jLabel7 = new widget.Label();
        BtnPilihDokter1 = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        panelBiasa1 = new widget.PanelBiasa();
        panelBiasa2 = new widget.PanelBiasa();
        label1 = new widget.Label();
        txtCari = new widget.TextBox();
        scrollPane2 = new widget.ScrollPane();
        tblData = new widget.Table();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(50, 50, 50));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        ppStok1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok1.setForeground(new java.awt.Color(50, 50, 50));
        ppStok1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok1.setText("Tampilkan Stok Lokasi Lain");
        ppStok1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok1.setName("ppStok1"); // NOI18N
        ppStok1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStok1ActionPerformed(evt);
            }
        });
        Popup.add(ppStok1);

        TNoRw.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N

        kelas.setHighlighter(null);
        kelas.setName("kelas"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data E-Resep Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setWarnaAtas(new java.awt.Color(255, 153, 255));
        panelisi3.setWarnaBawah(new java.awt.Color(255, 0, 255));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(500, 23));
        panelisi3.add(label13);

        btnSimpan.setForeground(new java.awt.Color(0, 0, 0));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        btnSimpan.setText("Print");
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

        FormInput.setBackground(new java.awt.Color(255, 204, 255));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(864, 108));
        FormInput.setWarnaAtas(new java.awt.Color(255, 153, 255));
        FormInput.setWarnaBawah(new java.awt.Color(255, 0, 255));
        FormInput.setLayout(null);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Status : ");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(370, 40, 60, 23);

        cmbTanggalTo.setForeground(new java.awt.Color(50, 70, 50));
        cmbTanggalTo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2020" }));
        cmbTanggalTo.setDisplayFormat("dd-MM-yyyy");
        cmbTanggalTo.setName("cmbTanggalTo"); // NOI18N
        cmbTanggalTo.setOpaque(false);
        cmbTanggalTo.setPreferredSize(new java.awt.Dimension(100, 23));
        cmbTanggalTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbTanggalToKeyPressed(evt);
            }
        });
        FormInput.add(cmbTanggalTo);
        cmbTanggalTo.setBounds(270, 40, 90, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(442, 40, 180, 23);

        label21.setForeground(new java.awt.Color(255, 255, 255));
        label21.setText("Peresep : ");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(10, 70, 68, 23);

        txtKodeDokter.setEditable(false);
        txtKodeDokter.setForeground(new java.awt.Color(255, 255, 255));
        txtKodeDokter.setName("txtKodeDokter"); // NOI18N
        txtKodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        txtKodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(txtKodeDokter);
        txtKodeDokter.setBounds(80, 70, 100, 23);

        txtNamaDokter.setEditable(false);
        txtNamaDokter.setForeground(new java.awt.Color(255, 255, 255));
        txtNamaDokter.setName("txtNamaDokter"); // NOI18N
        txtNamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(txtNamaDokter);
        txtNamaDokter.setBounds(180, 70, 197, 23);

        BtnPilihDokter.setForeground(new java.awt.Color(255, 255, 255));
        BtnPilihDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/search_page.png"))); // NOI18N
        BtnPilihDokter.setMnemonic('2');
        BtnPilihDokter.setText("Cari");
        BtnPilihDokter.setToolTipText("Alt+2");
        BtnPilihDokter.setName("BtnPilihDokter"); // NOI18N
        BtnPilihDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPilihDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPilihDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnPilihDokter);
        BtnPilihDokter.setBounds(440, 70, 70, 23);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jenis Perawatan :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel10);
        jLabel10.setBounds(10, 10, 110, 23);

        perawatanGrup.add(rdoRajal);
        rdoRajal.setForeground(new java.awt.Color(255, 255, 255));
        rdoRajal.setText("Rawat Jalan");
        rdoRajal.setName("rdoRajal"); // NOI18N
        rdoRajal.setOpaque(false);
        FormInput.add(rdoRajal);
        rdoRajal.setBounds(130, 10, 85, 23);

        perawatanGrup.add(rdoRanap);
        rdoRanap.setForeground(new java.awt.Color(255, 255, 255));
        rdoRanap.setText("Rawat Inap");
        rdoRanap.setName("rdoRanap"); // NOI18N
        rdoRanap.setOpaque(false);
        FormInput.add(rdoRanap);
        rdoRanap.setBounds(240, 10, 81, 23);

        cmbTanggalfrom.setForeground(new java.awt.Color(50, 70, 50));
        cmbTanggalfrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2020" }));
        cmbTanggalfrom.setDisplayFormat("dd-MM-yyyy");
        cmbTanggalfrom.setName("cmbTanggalfrom"); // NOI18N
        cmbTanggalfrom.setOpaque(false);
        cmbTanggalfrom.setPreferredSize(new java.awt.Dimension(100, 23));
        cmbTanggalfrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbTanggalfromKeyPressed(evt);
            }
        });
        FormInput.add(cmbTanggalfrom);
        cmbTanggalfrom.setBounds(140, 40, 90, 23);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
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

        BtnPilihDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnPilihDokter1.setMnemonic('2');
        BtnPilihDokter1.setToolTipText("Alt+2");
        BtnPilihDokter1.setName("BtnPilihDokter1"); // NOI18N
        BtnPilihDokter1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPilihDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPilihDokter1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnPilihDokter1);
        BtnPilihDokter1.setBounds(380, 70, 28, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        panelBiasa1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: DATA OBAT ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(new java.awt.BorderLayout());

        panelBiasa2.setName("panelBiasa2"); // NOI18N

        label1.setText("Cari Data :");
        label1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label1.setName("label1"); // NOI18N
        panelBiasa2.add(label1);

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
        tblData.setName("tblData"); // NOI18N
        tblData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataMouseClicked(evt);
            }
        });
        scrollPane2.setViewportView(tblData);

        panelBiasa1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelBiasa1);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed

}//GEN-LAST:event_ppBersihkanActionPerformed

private void cmbTanggalToKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbTanggalToKeyPressed

}//GEN-LAST:event_cmbTanggalToKeyPressed

private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed

}//GEN-LAST:event_cmbMntKeyPressed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed

    }//GEN-LAST:event_ppStokActionPerformed

    private void txtKodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeDokterKeyPressed

    }//GEN-LAST:event_txtKodeDokterKeyPressed

    private void BtnPilihDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPilihDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnPilihDokterActionPerformed

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed

    }//GEN-LAST:event_ppStok1ActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        // TODO add your handling code here:
        int row = tblData.getSelectedRow();
        addQty.setVisible(true);
        addQty.setData(model.get(tblData.convertRowIndexToModel(row)), racikanList);
    }//GEN-LAST:event_tblDataMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
       dispose();

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void cmbTanggalfromKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbTanggalfromKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTanggalfromKeyPressed

    private void BtnPilihDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPilihDokter1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPilihDokter1ActionPerformed

    private void clean() {
        racikanList = new LinkedList<>();
        model.removeAllElements();
        modelPilihan.removeAllElements();
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
    private widget.Button BtnPilihDokter;
    private widget.Button BtnPilihDokter1;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdPj;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TNoRw;
    private widget.Button btnSimpan;
    private widget.ComboBox cmbMnt;
    private widget.Tanggal cmbTanggalTo;
    private widget.Tanggal cmbTanggalfrom;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kelas;
    private widget.Label label1;
    private widget.Label label13;
    private widget.Label label21;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi3;
    private javax.swing.ButtonGroup perawatanGrup;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private javax.swing.JMenuItem ppStok1;
    private javax.swing.JRadioButton rdoRajal;
    private javax.swing.JRadioButton rdoRanap;
    private widget.ScrollPane scrollPane2;
    private widget.Table tblData;
    private widget.TextBox txtCari;
    private widget.TextBox txtKodeDokter;
    private widget.TextBox txtNamaDokter;
    // End of variables declaration//GEN-END:variables

}

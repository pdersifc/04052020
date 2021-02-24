/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.gui.dialog;

import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ObatResep;
import com.herinoid.rsi.util.Utils;
import com.herinoid.rsi.widget.KeySelectionRenderer;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.swing.JOptionPane;
import widget.ComboBox;

/**
 *
 * @author Hewrei
 */
public class DialogAddQtyResepDokter extends javax.swing.JDialog {

    private ObatResep obatResep;
    private List<ObatResep> racikanList;

    /**
     * Creates new form DialogAddQtyResepDokter
     */
    public DialogAddQtyResepDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setPreferredSize(new Dimension(500, 375));
        lblRacikan.setVisible(false);
        cmbRacikan.setVisible(false);
        lblKandungan.setVisible(false);
        txtKandungan.setVisible(false);
        lblP1.setVisible(false);
        lblP2.setVisible(false);
        txtPembilang.setVisible(false);
        txtPenyebut.setVisible(false);
        setLocationRelativeTo(null);
    }

    public void setData(Obat obat, List<ObatResep> racikans) {
        this.racikanList = racikans;
        this.obatResep = new ObatResep(obat.getKodeObat(), obat.getNamaObat(), obat.getKapasitas(), obat.getSatuan(), obat.getKategori(), obat.getJenisObat(), obat.getStok(), obat.getHargaBeli());
        lblObat.setText(obat.getNamaObat());
        obatResep.setEdit(false);
        racikanList.sort((f1, f2) -> Integer.compare(f2.getUrutan(), f1.getUrutan()));
        if (racikanList.size() > 0) {
            cekRacikan.setSelected(true);
            cekRacikan.setText("Iya");
            cmbRacikan.removeAllItems();
            lblRacikan.setVisible(true);
            cmbRacikan.setVisible(true);
            lblKandungan.setVisible(true);
            txtKandungan.setVisible(true);
            lblP1.setVisible(true);
            lblP2.setVisible(true);
            txtPembilang.setVisible(true);
            txtPenyebut.setVisible(true);
            scrAturanPake.setVisible(false);
            txtAturanPakai.setVisible(false);
            txtAturanPakai.setText("-");
            lblAturanPake.setVisible(false);
            cmbJumlah.setVisible(false);
            btnAturan1.setVisible(false);
            btnAturan2.setVisible(false);
            btnAturan3.setVisible(false);
            for (ObatResep m : racikanList) {
                cmbRacikan.addItem(m);
                KeySelectionRenderer renderer = new KeySelectionRenderer(cmbRacikan) {
                    @Override
                    public String getDisplayValue(Object value) {
                        ObatResep racik = (ObatResep) value;
                        return racik.getNamaObat();
                    }
                };
            }
        } else {
            scrAturanPake.setVisible(true);
            txtAturanPakai.setVisible(true);
            lblAturanPake.setVisible(true);
            cmbJumlah.setVisible(true);
            btnAturan1.setVisible(true);
            btnAturan2.setVisible(true);
            btnAturan3.setVisible(true);
            cekRacikan.setText("Tidak");
            lblRacikan.setVisible(false);
            cmbRacikan.setVisible(false);
            lblKandungan.setVisible(false);
            txtKandungan.setVisible(false);
            lblP1.setVisible(false);
            lblP2.setVisible(false);
            txtPembilang.setVisible(false);
            txtPenyebut.setVisible(false);
        }
    }

    public void setDataEdit(ObatResep obat, List<ObatResep> racikans) {
        this.obatResep = obat;
        obatResep.setEdit(true);
        txtJumlah.setText(String.valueOf(obat.getJumlah()));
        txtAturanPakai.setText(obat.getAturanPakai());
        this.racikanList = racikans;
        if (racikanList.size() > 0) {
            cekRacikan.setSelected(true);
            cekRacikan.setText("Iya");
            cmbRacikan.removeAllItems();
            lblRacikan.setVisible(true);
            cmbRacikan.setVisible(true);
            lblKandungan.setVisible(true);
            txtKandungan.setVisible(true);
            lblP1.setVisible(true);
            lblP2.setVisible(true);
            txtPembilang.setVisible(true);
            txtPenyebut.setVisible(true);
            scrAturanPake.setVisible(false);
            txtAturanPakai.setVisible(false);
            cmbJumlah.setVisible(false);
            btnAturan1.setVisible(false);
            btnAturan2.setVisible(false);
            btnAturan3.setVisible(false);
            txtAturanPakai.setText("-");
            lblAturanPake.setVisible(false);
            for (ObatResep m : racikanList) {
                cmbRacikan.addItem(m);
                KeySelectionRenderer renderer = new KeySelectionRenderer(cmbRacikan) {
                    @Override
                    public String getDisplayValue(Object value) {
                        ObatResep racik = (ObatResep) value;
                        return racik.getNamaObat();
                    }
                };
            }
            cekRacikan.setText("Iya");
            txtKandungan.setText(String.valueOf(obat.getKandungan()));
            txtPembilang.setText(String.valueOf(obat.getPembilang()));
            txtPenyebut.setText(String.valueOf(obat.getPenyebut()));
//            cmbRacikan.setSelectedItem(obat.getR);

        } else {
            scrAturanPake.setVisible(true);
            txtAturanPakai.setVisible(true);
            lblAturanPake.setVisible(true);
            cmbJumlah.setVisible(true);
            btnAturan1.setVisible(true);
            btnAturan2.setVisible(true);
            btnAturan3.setVisible(true);
            cekRacikan.setText("Tidak");
            lblRacikan.setVisible(false);
            cmbRacikan.setVisible(false);
            lblKandungan.setVisible(false);
            txtKandungan.setVisible(false);
            lblP1.setVisible(false);
            lblP2.setVisible(false);
            txtPembilang.setVisible(false);
            txtPenyebut.setVisible(false);
        }
    }

    public ObatResep getData() {
        return this.obatResep;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBiasa1 = new widget.PanelBiasa();
        panelisi1 = new widget.panelisi();
        label1 = new widget.Label();
        txtJumlah = new widget.TextBox();
        label2 = new widget.Label();
        cekRacikan = new widget.CekBox();
        lblRacikan = new widget.Label();
        cmbRacikan = new widget.ComboBox();
        lblAturanPake = new widget.Label();
        scrAturanPake = new javax.swing.JScrollPane();
        txtAturanPakai = new widget.TextArea();
        btnSimpan = new widget.Button();
        btnBatal = new widget.Button();
        lblObat = new widget.Label();
        lblKandungan = new widget.Label();
        txtKandungan = new widget.TextBox();
        lblP1 = new widget.Label();
        txtPembilang = new widget.TextBox();
        txtPenyebut = new widget.TextBox();
        lblP2 = new widget.Label();
        cmbJumlah = new widget.ComboBox();
        btnAturan1 = new javax.swing.JButton();
        btnAturan2 = new javax.swing.JButton();
        btnAturan3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        panelBiasa1.setLayout(new javax.swing.BoxLayout(panelBiasa1, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(panelBiasa1);

        panelisi1.setPreferredSize(new java.awt.Dimension(489, 331));

        label1.setForeground(new java.awt.Color(0, 0, 0));
        label1.setText("Jumlah :");
        label1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        label2.setText("Apakah Racikan ?");
        label2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        cekRacikan.setText("Tidak");
        cekRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekRacikanActionPerformed(evt);
            }
        });

        lblRacikan.setText("Parent Racikan :");
        lblRacikan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        cmbRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRacikanActionPerformed(evt);
            }
        });

        lblAturanPake.setText("Aturan Pakai :");
        lblAturanPake.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtAturanPakai.setColumns(20);
        txtAturanPakai.setRows(5);
        txtAturanPakai.setOpaque(true);
        scrAturanPake.setViewportView(txtAturanPakai);

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        lblObat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblObat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        lblKandungan.setText("Dosis :");
        lblKandungan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtKandungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKandunganKeyReleased(evt);
            }
        });

        lblP1.setText("P1 :");
        lblP1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtPembilang.setText("1");

        txtPenyebut.setText("1");
        txtPenyebut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPenyebutKeyReleased(evt);
            }
        });

        lblP2.setText("/");
        lblP2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        cmbJumlah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih", "30", "60", "90" }));
        cmbJumlah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJumlahActionPerformed(evt);
            }
        });

        btnAturan1.setText("3 dd 1");
        btnAturan1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAturan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAturan1ActionPerformed(evt);
            }
        });

        btnAturan2.setText("2 dd 1");
        btnAturan2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAturan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAturan2ActionPerformed(evt);
            }
        });

        btnAturan3.setText("1 dd 1");
        btnAturan3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAturan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAturan3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelisi1Layout = new javax.swing.GroupLayout(panelisi1);
        panelisi1.setLayout(panelisi1Layout);
        panelisi1Layout.setHorizontalGroup(
            panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelisi1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblObat, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelisi1Layout.createSequentialGroup()
                            .addComponent(lblKandungan, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtKandungan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelisi1Layout.createSequentialGroup()
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelisi1Layout.createSequentialGroup()
                                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblAturanPake, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelisi1Layout.createSequentialGroup()
                                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cekRacikan, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scrAturanPake, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelisi1Layout.createSequentialGroup()
                                        .addComponent(btnAturan1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnAturan2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnAturan3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelisi1Layout.createSequentialGroup()
                            .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblRacikan, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelisi1Layout.createSequentialGroup()
                                    .addComponent(txtPembilang, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lblP2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtPenyebut, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cmbRacikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelisi1Layout.setVerticalGroup(
            panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelisi1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblObat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cekRacikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbRacikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRacikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPembilang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPenyebut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKandungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKandungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAturanPake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrAturanPake, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAturan2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAturan3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAturan1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(panelisi1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cekRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekRacikanActionPerformed
        // TODO add your handling code here:
        if (cekRacikan.isSelected()) {
            cekRacikan.setText("Iya");
            cmbRacikan.removeAllItems();
            lblRacikan.setVisible(true);
            cmbRacikan.setVisible(true);
            lblKandungan.setVisible(true);
            txtKandungan.setVisible(true);
            lblP1.setVisible(true);
            lblP2.setVisible(true);
            txtPembilang.setVisible(true);
            txtPenyebut.setVisible(true);
            scrAturanPake.setVisible(false);
            txtAturanPakai.setVisible(false);
            txtAturanPakai.setText("-");
            lblAturanPake.setVisible(false);
            cmbJumlah.setVisible(false);
            btnAturan1.setVisible(false);
            btnAturan2.setVisible(false);
            btnAturan3.setVisible(false);
            for (ObatResep m : racikanList) {
                cmbRacikan.addItem(m);
                KeySelectionRenderer renderer = new KeySelectionRenderer(cmbRacikan) {
                    @Override
                    public String getDisplayValue(Object value) {
                        ObatResep racik = (ObatResep) value;
                        return racik.getNamaObat();
                    }
                };
            }
        } else {
            scrAturanPake.setVisible(true);
            txtAturanPakai.setVisible(true);
            lblAturanPake.setVisible(true);
            cmbJumlah.setVisible(true);
            btnAturan1.setVisible(true);
            btnAturan2.setVisible(true);
            btnAturan3.setVisible(true);
            cekRacikan.setText("Tidak");
            lblRacikan.setVisible(false);
            cmbRacikan.setVisible(false);
            lblKandungan.setVisible(false);
            txtKandungan.setVisible(false);
            lblP1.setVisible(false);
            lblP2.setVisible(false);
            txtPembilang.setVisible(false);
            txtPenyebut.setVisible(false);
        }

    }//GEN-LAST:event_cekRacikanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        obatResep.setFlag(false);
        clean();
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        if (Utils.isBlank(txtJumlah.getText())) {
            JOptionPane.showMessageDialog(null, "Jumlah Obat tidak boleh kosong!");
        } else {
            obatResep.setJumlah(Double.parseDouble(txtJumlah.getText().replace(",", ".")));
            obatResep.setAturanPakai(txtAturanPakai.getText());
            obatResep.setFlag(true);
            obatResep.setEmbalase(0);
            obatResep.setTuslah(0);
            if (cekRacikan.isSelected()) {
                if (racikanList.size() > 0) {
                    ObatResep obatRacik = (ObatResep) cmbRacikan.getSelectedItem();
                    if (obatRacik != null) {
                        obatResep.setRacikan(obatRacik.getNamaObat());
                        obatResep.setKodeRacikan(obatRacik.getKodeObat());
                        obatResep.setJenisObat(Obat.OBAT_RACIKAN);
                        obatResep.setKandungan(Double.parseDouble(txtKandungan.getText().replace(",", ".")));
                        obatResep.setPembilang(Integer.parseInt(txtPembilang.getText()));
                        obatResep.setPenyebut(Integer.parseInt(txtPenyebut.getText()));
                        obatResep.setJmlRacik(obatRacik.getJumlah());

                    }
                }
            } else {
                obatResep.setRacikan("-");
            }

            clean();
            dispose();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void cmbRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRacikanActionPerformed
        // TODO add your handling code here:
        cmbRacikan = (ComboBox) evt.getSource();
        ObatResep metod = (ObatResep) cmbRacikan.getSelectedItem();
        if (metod != null) {
            obatResep.setRacikan(metod.getNamaObat());
            obatResep.setJmlRacik(metod.getJumlah());
        }
    }//GEN-LAST:event_cmbRacikanActionPerformed

    private void txtPenyebutKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPenyebutKeyReleased
        // TODO add your handling code here:
        int p1 = Integer.parseInt(txtPembilang.getText());
        int p2 = 1;
        if (txtPenyebut.getText().length() > 0 && !txtPenyebut.getText().isEmpty()) {
            p2 = Integer.parseInt(txtPenyebut.getText());
        }
        double kandungan = obatResep.getKapasitas() * p1 / p2;
//        BigDecimal bd = new BigDecimal(kandungan);
//        BigDecimal bdRounded = bd.setScale(0, RoundingMode.CEILING);
        txtKandungan.setText(Utils.format(kandungan, 2));
        double hasilHitung = (kandungan / obatResep.getKapasitas()) * obatResep.getJmlRacik();
        String hasilDecimal = Utils.format(hasilHitung, 2);
        txtJumlah.setText(hasilDecimal);
    }//GEN-LAST:event_txtPenyebutKeyReleased

    private void txtKandunganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKandunganKeyReleased
        // TODO add your handling code here:
        if (!Utils.isBlank(txtKandungan.getText())) {
            double hasilHitung = (Double.parseDouble(txtKandungan.getText().replace(",", ".")) / obatResep.getKapasitas()) * obatResep.getJmlRacik();
            String hasilDecimal = Utils.format(hasilHitung, 2);
            txtJumlah.setText(hasilDecimal);
        }
    }//GEN-LAST:event_txtKandunganKeyReleased

    private void cmbJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJumlahActionPerformed
        // TODO add your handling code here:
        txtJumlah.setText(cmbJumlah.getSelectedItem().toString());
    }//GEN-LAST:event_cmbJumlahActionPerformed

    private void btnAturan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAturan1ActionPerformed
        // TODO add your handling code here:
        txtAturanPakai.setText("3 dd 1");
    }//GEN-LAST:event_btnAturan1ActionPerformed

    private void btnAturan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAturan2ActionPerformed
        // TODO add your handling code here:
        txtAturanPakai.setText("2 dd 1");
    }//GEN-LAST:event_btnAturan2ActionPerformed

    private void btnAturan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAturan3ActionPerformed
        // TODO add your handling code here:
        txtAturanPakai.setText("1 dd 1");
    }//GEN-LAST:event_btnAturan3ActionPerformed

    void clean() {
        txtJumlah.setText(null);
        txtAturanPakai.setText(null);
        cmbRacikan.removeAllItems();
        lblObat.setText("");
        txtKandungan.setText(null);
        cekRacikan.setSelected(false);
        lblRacikan.setVisible(false);
        cmbRacikan.setVisible(false);
        txtKandungan.setVisible(false);
        lblKandungan.setVisible(false);
        lblP1.setVisible(false);
        lblP2.setVisible(false);
        txtPembilang.setText("1");
        txtPenyebut.setText("1");
        txtPembilang.setVisible(false);
        txtPenyebut.setVisible(false);
        txtAturanPakai.setVisible(true);
        scrAturanPake.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogAddQtyResepDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogAddQtyResepDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogAddQtyResepDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogAddQtyResepDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogAddQtyResepDokter dialog = new DialogAddQtyResepDokter(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAturan1;
    private javax.swing.JButton btnAturan2;
    private javax.swing.JButton btnAturan3;
    private widget.Button btnBatal;
    private widget.Button btnSimpan;
    private widget.CekBox cekRacikan;
    private widget.ComboBox cmbJumlah;
    private widget.ComboBox cmbRacikan;
    private widget.Label label1;
    private widget.Label label2;
    private widget.Label lblAturanPake;
    private widget.Label lblKandungan;
    private widget.Label lblObat;
    private widget.Label lblP1;
    private widget.Label lblP2;
    private widget.Label lblRacikan;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelisi1;
    private javax.swing.JScrollPane scrAturanPake;
    private widget.TextArea txtAturanPakai;
    private widget.TextBox txtJumlah;
    private widget.TextBox txtKandungan;
    private widget.TextBox txtPembilang;
    private widget.TextBox txtPenyebut;
    // End of variables declaration//GEN-END:variables
}

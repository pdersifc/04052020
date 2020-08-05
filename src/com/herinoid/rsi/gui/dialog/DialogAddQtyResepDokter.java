/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.herinoid.rsi.gui.dialog;

import com.herinoid.rsi.model.MetodeRacikan;
import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ObatResep;
import com.herinoid.rsi.widget.KeySelectionRenderer;
import java.awt.Dimension;
import java.util.List;
import org.apache.poi.hwmf.record.HwmfRecordType;
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
        setPreferredSize(new Dimension(524, 327));
        lblRacikan.setVisible(false);
        cmbRacikan.setVisible(false);
        lblKandungan.setVisible(false);
        txtKandungan.setVisible(false);
        setLocationRelativeTo(null);
    }

    public void setData(Obat obat, List<ObatResep> racikans) {
        this.racikanList = racikans;
        this.obatResep = new ObatResep(obat.getKodeObat(), obat.getNamaObat(), obat.getSatuan(), obat.getKategori(), obat.getJenisObat(), obat.getStok());
        lblObat.setText(obat.getNamaObat());
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

        panelisi1 = new widget.panelisi();
        label1 = new widget.Label();
        txtJumlah = new widget.TextBox();
        label2 = new widget.Label();
        cekRacikan = new widget.CekBox();
        lblRacikan = new widget.Label();
        cmbRacikan = new widget.ComboBox();
        label4 = new widget.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAturanPakai = new widget.TextArea();
        btnSimpan = new widget.Button();
        btnBatal = new widget.Button();
        lblObat = new widget.Label();
        lblKandungan = new widget.Label();
        txtKandungan = new widget.TextBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        panelisi1.setWarnaAtas(new java.awt.Color(204, 204, 204));
        panelisi1.setWarnaBawah(new java.awt.Color(204, 204, 204));

        label1.setForeground(new java.awt.Color(0, 0, 0));
        label1.setText("Jumlah :");
        label1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        label2.setText("Apakah Racikan ?");
        label2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        cekRacikan.setBackground(new java.awt.Color(204, 204, 204));
        cekRacikan.setText("Iya");
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

        label4.setText("Aturan Pakai :");
        label4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtAturanPakai.setColumns(20);
        txtAturanPakai.setRows(5);
        jScrollPane1.setViewportView(txtAturanPakai);

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

        lblKandungan.setText("Kandungan :");
        lblKandungan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

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
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelisi1Layout.createSequentialGroup()
                            .addComponent(lblRacikan, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbRacikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelisi1Layout.createSequentialGroup()
                                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelisi1Layout.createSequentialGroup()
                                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cekRacikan, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(20, Short.MAX_VALUE))
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
                .addGap(5, 5, 5)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKandungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKandungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelisi1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        getContentPane().add(panelisi1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cekRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekRacikanActionPerformed
        // TODO add your handling code here:
        if (cekRacikan.isSelected()) {
            cmbRacikan.removeAllItems();
            lblRacikan.setVisible(true);
            cmbRacikan.setVisible(true);
            lblKandungan.setVisible(true);
            txtKandungan.setVisible(true);
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
            lblRacikan.setVisible(false);
            cmbRacikan.setVisible(false);
            lblKandungan.setVisible(false);
            txtKandungan.setVisible(false);
        }

    }//GEN-LAST:event_cekRacikanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        obatResep.setFlag(false);
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        obatResep.setJumlah(Integer.parseInt(txtJumlah.getText()));
        obatResep.setAturanPakai(txtAturanPakai.getText());
        obatResep.setFlag(true);
        if (cekRacikan.isSelected()) {
            if (racikanList.size() == 1) {;
                ObatResep obatRacik = (ObatResep) cmbRacikan.getSelectedItem();
                if (obatRacik != null) {
                    obatResep.setRacikan(obatRacik.getNamaObat());
                    obatResep.setKodeRacikan(obatRacik.getKodeObat());
                    obatResep.setJenisObat(Obat.OBAT_RACIKAN);
                    obatResep.setKandungan(txtKandungan.getText());
                }
            }
        } else {
            obatResep.setRacikan("-");
        }

        clean();
        dispose();

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void cmbRacikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRacikanActionPerformed
        // TODO add your handling code here:
        cmbRacikan = (ComboBox) evt.getSource();
        ObatResep metod = (ObatResep) cmbRacikan.getSelectedItem();
        if (metod != null) {
            obatResep.setRacikan(metod.getNamaObat());
        }
    }//GEN-LAST:event_cmbRacikanActionPerformed

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
    private widget.Button btnBatal;
    private widget.Button btnSimpan;
    private widget.CekBox cekRacikan;
    private widget.ComboBox cmbRacikan;
    private javax.swing.JScrollPane jScrollPane1;
    private widget.Label label1;
    private widget.Label label2;
    private widget.Label label4;
    private widget.Label lblKandungan;
    private widget.Label lblObat;
    private widget.Label lblRacikan;
    private widget.panelisi panelisi1;
    private widget.TextArea txtAturanPakai;
    private widget.TextBox txtJumlah;
    private widget.TextBox txtKandungan;
    // End of variables declaration//GEN-END:variables
}

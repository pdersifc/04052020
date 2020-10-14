/*
  
 */
package com.herinoid.rsi.gui;

import com.herinoid.rsi.gui.dialog.DialogRacikanResep;
import com.herinoid.rsi.model.ObatResep;
import fungsi.validasi;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.herinoid.rsi.dao.DokterDao;
import com.herinoid.rsi.gui.dialog.DlgCariObat;
import com.herinoid.rsi.table.TabelResepRincian;
import com.herinoid.rsi.widget.KeySelectionRenderer;
import com.herinoid.rsi.dao.ResepTemplateDao;
import com.herinoid.rsi.model.DokterRajal;
import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ResepTemplate;
import com.herinoid.rsi.table.TabelListTemplateResep;
import com.herinoid.rsi.table.TabelTemplateResep;
import com.herinoid.rsi.widget.ButtonEditor;
import com.herinoid.rsi.widget.ButtonRenderer;
import fungsi.sekuel;
import java.util.Comparator;
import inventory.DlgAturanPakai;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.EnumSet;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import simrskhanza.frmUtama;
import usu.widget.glass.PanelGlass;
import widget.Button;
import widget.Table;

/**
 *
 * @author herinoid
 */
public final class DlgDaftarTemplateResepDokter extends javax.swing.JDialog {

    private TabelListTemplateResep model;
    private TableRowSorter<TableModel> rowSorter;
    private DlgCariObat addObat = new DlgCariObat(null, false);
    private ObatResep obatFromDialog;
    private ObatResep obatRacikan;
    private List<ObatResep> racikanList;
    private DlgInputTemplateResepDokter dialogInput;
    private validasi Valid = new validasi();
    private Properties pro = new Properties();
    private String sttRawat, kategoriObat;
    private int row, rowEditor;
    private String kdBangsal, tarif, kdDokter;
    private frmUtama parent = new frmUtama();
    private DlgDaftarTemplateResepDokter induk;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgDaftarTemplateResepDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            racikanList = new LinkedList<>();
            model = new TabelListTemplateResep();
            this.setLocation(10, 2);
            setSize(656, 250);
            pro.loadFromXML(new FileInputStream("setting/database.xml"));
            kdBangsal = pro.getProperty("DEPOOBAT");
            setData(kdBangsal);
            this.induk = this;
            dialogInput = new DlgInputTemplateResepDokter(null, false);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DlgDaftarTemplateResepDokter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DlgDaftarTemplateResepDokter.class.getName()).log(Level.SEVERE, null, ex);
        }

        dialogInput.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                setComboList();
                DokterRajal dokter = (DokterRajal) cmbDokter.getItemAt(cmbDokter.getSelectedIndex());
                model.removeAllElements();
                List<ResepTemplate> dataList = ResepTemplateDao.getTemplateByDokter(dokter.getKodeDokter(), kdBangsal);
                model.add(dataList);
                tblTemplate.setModel(model);
                rowSorter = new TableRowSorter<>(tblTemplate.getModel());
                tblTemplate.setRowSorter(rowSorter);
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

    private void setComboList() {
        DokterRajal doter = null;
        List<DokterRajal> dokters = DokterDao.findDokters();
        for (DokterRajal m : dokters) {
            if (m.getKodeDokter().equals(kdDokter)) {
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
        if (doter != null) {
            cmbDokter.getModel().setSelectedItem(doter);
        }

    }

    public void setData(String depo) {
        model.removeAllElements();
        List<ResepTemplate> dataList = ResepTemplateDao.getAllTemplate();
        model.add(dataList);
        tblTemplate.setModel(model);
        rowSorter = new TableRowSorter<>(tblTemplate.getModel());
        tblTemplate.setRowSorter(rowSorter);
        setComboList();
        tblTemplate.setRowHeight(36);
        TableColumn column = tblTemplate.getColumnModel().getColumn(3);
        column.setCellRenderer(new ButtonsRenderer());
        column.setCellEditor(new ButtonsEditor(tblTemplate));
//        ButtonRenderer renderer = new ButtonRenderer();
//        tblTemplate.getColumnModel().getColumn(3).setCellRenderer(renderer);
//        tblTemplate.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor());
//        tblTemplate.setRowHeight(renderer.getTableCellRendererComponent(tblTemplate, null, true, true, 0, 0).getPreferredSize().height);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label13 = new widget.Label();
        btnTambahTemplate = new widget.Button();
        label2 = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        label7 = new widget.Label();
        cmbDokter = new widget.ComboBox();
        btnRefresh = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        panelBiasa1 = new widget.PanelBiasa();
        panelBiasa2 = new widget.PanelBiasa();
        scrollPane2 = new widget.ScrollPane();
        tblTemplate = new javax.swing.JTable();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

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

        btnTambahTemplate.setForeground(new java.awt.Color(0, 0, 0));
        btnTambahTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnTambahTemplate.setText("Tambah Template");
        btnTambahTemplate.setName("btnTambahTemplate"); // NOI18N
        btnTambahTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahTemplateActionPerformed(evt);
            }
        });
        panelisi3.add(btnTambahTemplate);

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
        label7.setBounds(20, 20, 110, 20);

        cmbDokter.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbDokter.setName("cmbDokter"); // NOI18N
        cmbDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDokterActionPerformed(evt);
            }
        });
        FormInput.add(cmbDokter);
        cmbDokter.setBounds(140, 20, 340, 21);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png"))); // NOI18N
        btnRefresh.setName("btnRefresh"); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        FormInput.add(btnRefresh);
        btnRefresh.setBounds(490, 20, 30, 20);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

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
        scrollPane2.setViewportView(tblTemplate);

        panelBiasa1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelBiasa1);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void btnTambahTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahTemplateActionPerformed
        try {
            // TODO add your handling code here:
            DlgInputTemplateResepDokter data = new DlgInputTemplateResepDokter(parent, false);
            data.setSize(getSize().width, getSize().height);
            data.setLocationRelativeTo(parent);
            data.setVisible(true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DlgDaftarTemplateResepDokter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTambahTemplateActionPerformed

    private void cmbDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDokterActionPerformed
        // TODO add your handling code here:        
        DokterRajal dokter = (DokterRajal) cmbDokter.getItemAt(cmbDokter.getSelectedIndex());
        model.removeAllElements();
        List<ResepTemplate> dataList = ResepTemplateDao.getTemplateByDokter(dokter.getKodeDokter(), kdBangsal);
        model.add(dataList);
        tblTemplate.setModel(model);
        rowSorter = new TableRowSorter<>(tblTemplate.getModel());
        tblTemplate.setRowSorter(rowSorter);
    }//GEN-LAST:event_cmbDokterActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        setComboList();
        DokterRajal dokter = (DokterRajal) cmbDokter.getItemAt(cmbDokter.getSelectedIndex());
        model.removeAllElements();
        List<ResepTemplate> dataList = ResepTemplateDao.getTemplateByDokter(dokter.getKodeDokter(), kdBangsal);
        model.add(dataList);
        tblTemplate.setModel(model);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void clean() {
        racikanList = new LinkedList<>();
        model.removeAllElements();
        obatRacikan = null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDaftarTemplateResepDokter dialog = new DlgDaftarTemplateResepDokter(new javax.swing.JFrame(), true);
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
    private widget.Button btnRefresh;
    private widget.Button btnTambahTemplate;
    private widget.ComboBox cmbDokter;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label13;
    private widget.Label label2;
    private widget.Label label7;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane2;
    private javax.swing.JTable tblTemplate;
    // End of variables declaration//GEN-END:variables

    enum Actions {
        HAPUS, EDIT;
    }

    class ButtonsPanel extends JPanel {

        public final List<JButton> buttons = new ArrayList<>();

        public ButtonsPanel() {
            super(new FlowLayout(FlowLayout.LEFT));
            setOpaque(true);
            for (Actions a : Actions.values()) {
                JButton b = new JButton(a.toString());
                if (a.toString().equals(Actions.EDIT.toString())) {
                    b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/EDIT2.png")));
                } else {
                    b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
                }

                b.setFocusable(false);
                b.setRolloverEnabled(false);
                add(b);
                buttons.add(b);
            }
        }

        protected void updateButtons(Object value) {
            if (value instanceof EnumSet) {
                EnumSet ea = (EnumSet) value;
                removeAll();
                if (ea.contains(Actions.HAPUS)) {
                    buttons.get(0).setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png")));
                    add(buttons.get(0));
                }
                if (ea.contains(Actions.EDIT)) {
                    buttons.get(0).setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/EDIT2.png")));
                    add(buttons.get(1));
                }
            }
        }
    }

    class ButtonsRenderer implements TableCellRenderer {

        private final ButtonsPanel panel = new ButtonsPanel();

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            panel.updateButtons(value);
            return panel;
        }
    }

    class HapusAction extends AbstractAction {

        private final JTable table;

        public HapusAction(JTable table) {
            super(Actions.HAPUS.toString());
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int baris = table.convertRowIndexToModel(table.getSelectedRow());
            ResepTemplate template = model.get(table.convertRowIndexToModel(baris));
            int oke = JOptionPane.showConfirmDialog(table, "Apakah anda yakin mau menghapus template " + template.getNamaTemplate() + " ini?", "Perhatian", JOptionPane.YES_NO_OPTION);
            if (oke == 0) {
                ResepTemplateDao.deleteTemplateResepByCode(template.getCode());
                DokterRajal dokter = (DokterRajal) cmbDokter.getItemAt(cmbDokter.getSelectedIndex());
                model.removeAllElements();
                List<ResepTemplate> dataList = ResepTemplateDao.getTemplateByDokter(dokter.getKodeDokter(), kdBangsal);
                model.add(dataList);
                tblTemplate.setModel(model);
            }

        }
    }

    class EditAction extends AbstractAction {

        private final JTable table;

        public EditAction(JTable table) {
            super(Actions.EDIT.toString());
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int baris = table.convertRowIndexToModel(table.getSelectedRow());
                ResepTemplate template = model.get(table.convertRowIndexToModel(baris));
                DlgInputTemplateResepDokter data = new DlgInputTemplateResepDokter(parent, false);
                data.setToEdit(template);
                data.setSize(getSize().width, getSize().height);
                data.setLocationRelativeTo(parent);
                data.setVisible(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DlgDaftarTemplateResepDokter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class ButtonsEditor extends AbstractCellEditor implements TableCellEditor {

        private final ButtonsPanel panel = new ButtonsPanel();
        private final JTable table;
        private Object o;

        private class EditingStopHandler extends MouseAdapter implements ActionListener {

            @Override
            public void mousePressed(MouseEvent e) {
//                Object o = e.getSource();
//                if (o instanceof TableCellEditor) {
//                    actionPerformed(null);
//                } else if (o instanceof JButton) {
//                    ButtonModel m = ((JButton) e.getComponent()).getModel();
//                    if (m.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
//                        panel.setBackground(table.getBackground());
//                    }
//                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        fireEditingStopped();
                    }
                });
            }
        }

        public ButtonsEditor(JTable table) {
            super();
            this.table = table;
            panel.buttons.get(0).setAction(new HapusAction(table));
            panel.buttons.get(1).setAction(new EditAction(table));

            EditingStopHandler handler = new EditingStopHandler();
            for (JButton b : panel.buttons) {
                b.addMouseListener(handler);
                b.addActionListener(handler);
            }
            panel.addMouseListener(handler);
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int column) {
            panel.setBackground(table.getSelectionBackground());
            panel.updateButtons(value);
            o = value;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return o;
        }
    }
}

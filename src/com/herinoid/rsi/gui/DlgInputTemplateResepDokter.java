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
import com.herinoid.rsi.model.PemeriksaanRalan;
import com.herinoid.rsi.model.Resep;
import com.herinoid.rsi.model.RegPeriksa;
import com.herinoid.rsi.dao.RegPeriksaDao;
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
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import kepegawaian.DlgCariDokter;
import com.herinoid.rsi.dao.BangsalDao;
import com.herinoid.rsi.dao.MarginDao;
import com.herinoid.rsi.dao.ResepTemplateDao;
import com.herinoid.rsi.gui.dialog.DlgHistoriResepPasien;
import com.herinoid.rsi.model.Bangsal;
import com.herinoid.rsi.model.MarginBpjs;
import com.herinoid.rsi.model.MarginObatNonBpjs;
import com.herinoid.rsi.model.ResepTemplate;
import com.herinoid.rsi.session.SessionLogin;
import com.herinoid.rsi.util.Utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herinoid
 */
public final class DlgInputTemplateResepDokter extends javax.swing.JDialog {

    private TabelCariObatResep model;
    private TabelObatResepPilihan modelPilihan;
    private TableRowSorter<TableModel> rowSorter;
    private DialogAddQtyResepDokter addQty = new DialogAddQtyResepDokter(null, false);
    private DlgHistoriResepPasien cekHistory = new DlgHistoriResepPasien(null, false);
    private ObatResep obatFromDialog;
    private List<ObatResep> racikanList;
    private DialogRacikanResep dlgRacikan = new DialogRacikanResep(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private validasi Valid = new validasi();
    private String depoKode, kdJaminan, jaminan, jenisPasien;
    private double total;
    private Properties pro = new Properties();
    private ResepTemplate templateEdit;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgInputTemplateResepDokter(java.awt.Frame parent, boolean modal) throws FileNotFoundException {
        super(parent, modal);
        try {
            initComponents();
            racikanList = new LinkedList<>();
            model = new TabelCariObatResep();
            modelPilihan = new TabelObatResepPilihan();
            this.setLocation(10, 2);
            setSize(656, 250);
            pro.loadFromXML(new FileInputStream("setting/database.xml"));
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

            addQty.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    obatFromDialog = addQty.getData();
                    if (obatFromDialog != null) {
                        if (obatFromDialog.isFlag()) {
                            if (obatFromDialog.isEdit()) {
                                modelPilihan.remove(tblPilihan.getSelectedRow());
                            }

                            double marginPersen = 28;
                            if (jaminan.equals(Konstan.PASIEN_BPJS_KESEHATAN)) {
                                MarginBpjs marginBpjs = MarginDao.getMarginBpjs(obatFromDialog.getKodeObat());
                                if (marginBpjs != null) {
                                    marginPersen = marginBpjs.getRalan();
                                }
                            } else {
                                MarginObatNonBpjs marginNon = MarginDao.getMarginNonBpjs(kdJaminan);
                                if (marginNon != null) {
                                    marginPersen = marginNon.getMargin();
                                }
                            }
                            double margin = (obatFromDialog.getHargaBeli() * marginPersen) / 100;
                            double hpp = margin + obatFromDialog.getHargaBeli();
                            total = total + (hpp * obatFromDialog.getJumlah());
                            lblTotal.setText(Utils.format(total, 2));

                            removeDuplicate(obatFromDialog);
                            tblPilihan.setModel(modelPilihan);
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
            dlgRacikan.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    obatFromDialog = dlgRacikan.getData();
                    if (obatFromDialog != null) {
                        if (obatFromDialog.isFlag()) {
                            if (obatFromDialog.isEdit()) {
                                modelPilihan.remove(tblPilihan.getSelectedRow());
                            }
                            modelPilihan.add(obatFromDialog);
                            tblPilihan.setModel(modelPilihan);
                            racikanList.add(obatFromDialog);

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

            cekHistory.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                }

                @Override
                public void windowClosing(WindowEvent e) {
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    List<ObatResep> obats = cekHistory.getData();
                    if (obats != null && obats.size() > 0) {
                        modelPilihan.add(obats);
                        for (ObatResep o : obats) {
                            double marginPersen = 28;
                            if (jaminan.equals(Konstan.PASIEN_BPJS_KESEHATAN)) {
                                MarginBpjs marginBpjs = MarginDao.getMarginBpjs(o.getKodeObat());
                                if (marginBpjs != null) {
                                    marginPersen = marginBpjs.getRalan();
                                }

                            } else {
                                MarginObatNonBpjs marginNon = MarginDao.getMarginNonBpjs(kdJaminan);
                                if (marginNon != null) {
                                    marginPersen = marginNon.getMargin();
                                }

                            }
                            double margin = (o.getHargaBeli() * marginPersen) / 100;
                            double hpp = margin + o.getHargaBeli();
                            total = total + (hpp * o.getJumlah());
                            lblTotal.setText(Utils.format(total, 2));
                        }
                        tblPilihan.setModel(modelPilihan);

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
        } catch (IOException ex) {
            Logger.getLogger(DlgInputTemplateResepDokter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setData(String kodeDepo, String kategoriObat, String jenisPasien) {
        Bangsal bangsal = BangsalDao.get(kodeDepo);
        this.depoKode = kodeDepo;
        lblDepo.setText(bangsal.getNama());
        model.removeAllElements();
        if (kategoriObat.equals("K01")) {
            model.add(ObatDao.getObatByCategory(kodeDepo, kategoriObat, jenisPasien));
        } else {
            model.add(ObatDao.getObatByDepo(kodeDepo, jenisPasien));
        }

        tblObat.setModel(model);
        rowSorter = new TableRowSorter<>(tblObat.getModel());
        tblObat.setRowSorter(rowSorter);
    }

    public void setPasien(String norawat, String norm, String nmPasien, String jaminan, String jenisPasien, String poli) {
        RegPeriksa reg = RegPeriksaDao.get(norawat);
        txtNamaTemplate.setText(norawat);

        this.jaminan = jaminan;
        this.kdJaminan = reg.getKdPj();
        this.jenisPasien = jenisPasien;
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

    public void setToEdit(ResepTemplate template) {
        txtNamaTemplate.setText(template.getNamaTemplate());
        txtKodeDokter.setText(template.getKdDokter());
        txtNamaDokter.setText(template.getNamaDokter());
        this.templateEdit = template;
        if (template.getObatTemplateDetail() != null && template.getObatTemplateDetail().size() > 0) {
            modelPilihan.add(template.getObatTemplateDetail());
            for (ObatResep o : template.getObatTemplateDetail()) {
                if (o.getKategori().equals("Formularium Nasional")) {
                    rdoFornas.setSelected(true);
                    break;
                }
            }
            for (ObatResep o : template.getObatTemplateDetail()) {
                double marginPersen = 28;
                if (rdoFornas.isSelected()) {
                    MarginBpjs marginBpjs = MarginDao.getMarginBpjs(o.getKodeObat());
                    if (marginBpjs != null) {
                        marginPersen = marginBpjs.getRalan();
                    }

                } else {
                    MarginObatNonBpjs marginNon = MarginDao.getMarginNonBpjs(kdJaminan);
                    if (marginNon != null) {
                        marginPersen = marginNon.getMargin();
                    }

                }
                double margin = (o.getHargaBeli() * marginPersen) / 100;
                double hpp = margin + o.getHargaBeli();
                total = total + (hpp * o.getJumlah());
                lblTotal.setText(Utils.format(total, 2));
            }

        }

        if (template.getObatTemplateRacikanDetail() != null && template.getObatTemplateRacikanDetail().size() > 0) {
            modelPilihan.add(template.getObatTemplateRacikanDetail());
            for (ObatResep o : template.getObatTemplateRacikanDetail()) {
                if (!Utils.isBlank(o.getKategori())) {
                    if (o.getKategori().equals("Formularium Nasional")) {
                        rdoFornas.setSelected(true);
                        break;
                    }
                }

            }

            for (ObatResep o : template.getObatTemplateRacikanDetail()) {
                double marginPersen = 28;
                if (rdoFornas.isSelected()) {
                    MarginBpjs marginBpjs = MarginDao.getMarginBpjs(o.getKodeObat());
                    if (marginBpjs != null) {
                        marginPersen = marginBpjs.getRalan();
                    }

                } else {
                    MarginObatNonBpjs marginNon = MarginDao.getMarginNonBpjs(kdJaminan);
                    if (marginNon != null) {
                        marginPersen = marginNon.getMargin();
                    }

                }
                double margin = (o.getHargaBeli() * marginPersen) / 100;
                double hpp = margin + o.getHargaBeli();
                total = total + (hpp * o.getJumlah());
                lblTotal.setText(Utils.format(total, 2));
            }

        }
        if (rdoFornas.isSelected()) {
            this.jaminan = Konstan.PASIEN_BPJS_KESEHATAN;
            setData(pro.getProperty("DEPOOBAT"), "K01", Konstan.PASIEN_RALAN);
        } else {
            this.jaminan = Konstan.PASIEN_UMUM;
            setData(pro.getProperty("DEPOOBAT"), "K02", Konstan.PASIEN_RALAN);
        }
        tblPilihan.setModel(modelPilihan);
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
        popObatPilihan = new javax.swing.JPopupMenu();
        mnEditObat = new javax.swing.JMenuItem();
        mnHapusItem = new javax.swing.JMenuItem();
        grupKatergori = new javax.swing.ButtonGroup();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        BtnTambah = new widget.Button();
        label13 = new widget.Label();
        btnEdit = new widget.Button();
        btnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        label21 = new widget.Label();
        jLabel10 = new widget.Label();
        txtNamaTemplate = new widget.TextBox();
        jLabel21 = new widget.Label();
        lblTotal = new widget.Label();
        label22 = new widget.Label();
        txtKodeDokter = new widget.TextBox();
        txtNamaDokter = new widget.TextBox();
        BtnPilihDokter = new widget.Button();
        rdoFornas = new javax.swing.JRadioButton();
        rdoNonFornas = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        tblPilihan = new widget.Table();
        panelBiasa1 = new widget.PanelBiasa();
        panelBiasa2 = new widget.PanelBiasa();
        label1 = new widget.Label();
        txtCari = new widget.TextBox();
        panelBiasa3 = new widget.PanelBiasa();
        label2 = new widget.Label();
        lblDepo = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        tblObat = new widget.Table();

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

        popObatPilihan.setName("popObatPilihan"); // NOI18N

        mnEditObat.setBackground(new java.awt.Color(255, 255, 255));
        mnEditObat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mnEditObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/EDIT2.png"))); // NOI18N
        mnEditObat.setText("Edit Obat Resep");
        mnEditObat.setName("mnEditObat"); // NOI18N
        mnEditObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEditObatActionPerformed(evt);
            }
        });
        popObatPilihan.add(mnEditObat);

        mnHapusItem.setBackground(new java.awt.Color(255, 255, 255));
        mnHapusItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mnHapusItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/101.png"))); // NOI18N
        mnHapusItem.setText("Hapus Obat");
        mnHapusItem.setName("mnHapusItem"); // NOI18N
        mnHapusItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnHapusItemActionPerformed(evt);
            }
        });
        popObatPilihan.add(mnHapusItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Master Template E-Resep Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        BtnTambah.setForeground(new java.awt.Color(0, 0, 0));
        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setText("Racikan");
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(500, 23));
        panelisi3.add(label13);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/EDIT2.png"))); // NOI18N
        btnEdit.setText("Ganti");
        btnEdit.setName("btnEdit"); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        panelisi3.add(btnEdit);

        btnSimpan.setForeground(new java.awt.Color(0, 0, 0));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        btnSimpan.setText("Simpan");
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
        FormInput.setLayout(null);

        label21.setForeground(new java.awt.Color(0, 0, 0));
        label21.setText("Kategori : ");
        label21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(20, 70, 140, 23);

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Nama Template :");
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel10);
        jLabel10.setBounds(10, 10, 150, 23);

        txtNamaTemplate.setForeground(new java.awt.Color(0, 0, 0));
        txtNamaTemplate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNamaTemplate.setName("txtNamaTemplate"); // NOI18N
        txtNamaTemplate.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(txtNamaTemplate);
        txtNamaTemplate.setBounds(170, 10, 410, 23);

        jLabel21.setForeground(new java.awt.Color(0, 153, 0));
        jLabel21.setText("Total  :");
        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(790, 80, 60, 23);

        lblTotal.setForeground(new java.awt.Color(0, 153, 0));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotal.setText("0.0");
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotal.setName("lblTotal"); // NOI18N
        FormInput.add(lblTotal);
        lblTotal.setBounds(860, 80, 290, 20);

        label22.setForeground(new java.awt.Color(0, 0, 0));
        label22.setText("Dokter : ");
        label22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(10, 40, 150, 23);

        txtKodeDokter.setEditable(false);
        txtKodeDokter.setForeground(new java.awt.Color(0, 0, 0));
        txtKodeDokter.setName("txtKodeDokter"); // NOI18N
        txtKodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(txtKodeDokter);
        txtKodeDokter.setBounds(170, 40, 100, 23);

        txtNamaDokter.setEditable(false);
        txtNamaDokter.setForeground(new java.awt.Color(0, 0, 0));
        txtNamaDokter.setName("txtNamaDokter"); // NOI18N
        txtNamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(txtNamaDokter);
        txtNamaDokter.setBounds(270, 40, 197, 23);

        BtnPilihDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Doctor.png"))); // NOI18N
        BtnPilihDokter.setMnemonic('2');
        BtnPilihDokter.setToolTipText("Alt+2");
        BtnPilihDokter.setName("BtnPilihDokter"); // NOI18N
        BtnPilihDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPilihDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPilihDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnPilihDokter);
        BtnPilihDokter.setBounds(470, 40, 28, 23);

        rdoFornas.setBackground(new java.awt.Color(255, 255, 255));
        grupKatergori.add(rdoFornas);
        rdoFornas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdoFornas.setText("Formularium Nasional");
        rdoFornas.setName("rdoFornas"); // NOI18N
        rdoFornas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoFornasActionPerformed(evt);
            }
        });
        FormInput.add(rdoFornas);
        rdoFornas.setBounds(167, 70, 160, 23);

        rdoNonFornas.setBackground(new java.awt.Color(255, 255, 255));
        grupKatergori.add(rdoNonFornas);
        rdoNonFornas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdoNonFornas.setText("Non ForNas");
        rdoNonFornas.setName("rdoNonFornas"); // NOI18N
        rdoNonFornas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNonFornasActionPerformed(evt);
            }
        });
        FormInput.add(rdoNonFornas);
        rdoNonFornas.setBounds(330, 70, 140, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(2, 1, 1, 0));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: Daftar Template Resep ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tblPilihan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblPilihan.setComponentPopupMenu(popObatPilihan);
        tblPilihan.setName("tblPilihan"); // NOI18N
        tblPilihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPilihanMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tblPilihan);

        jPanel2.add(scrollPane1);

        jPanel1.add(jPanel2);

        panelBiasa1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, ".:: DATA OBAT ::.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(new java.awt.BorderLayout());

        panelBiasa2.setName("panelBiasa2"); // NOI18N

        label1.setText("Cari Obat :");
        label1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label1.setName("label1"); // NOI18N
        panelBiasa2.add(label1);

        txtCari.setForeground(new java.awt.Color(0, 0, 0));
        txtCari.setName("txtCari"); // NOI18N
        txtCari.setPreferredSize(new java.awt.Dimension(400, 24));
        panelBiasa2.add(txtCari);

        panelBiasa3.setBorder(null);
        panelBiasa3.setName("panelBiasa3"); // NOI18N
        panelBiasa3.setPreferredSize(new java.awt.Dimension(400, 30));
        panelBiasa3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        label2.setText("Lokasi Obat : ");
        label2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label2.setName("label2"); // NOI18N
        panelBiasa3.add(label2);

        lblDepo.setText("Depo Obat");
        lblDepo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDepo.setName("lblDepo"); // NOI18N
        panelBiasa3.add(lblDepo);

        panelBiasa2.add(panelBiasa3);

        panelBiasa1.add(panelBiasa2, java.awt.BorderLayout.PAGE_START);

        scrollPane2.setName("scrollPane2"); // NOI18N

        tblObat.setModel(new javax.swing.table.DefaultTableModel(
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
        tblObat.setName("tblObat"); // NOI18N
        tblObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblObatMouseClicked(evt);
            }
        });
        scrollPane2.setViewportView(tblObat);

        panelBiasa1.add(scrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelBiasa1);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        dlgRacikan.setData(racikanList.size() + 1,true);
        dlgRacikan.setVisible(true);

    }//GEN-LAST:event_BtnTambahActionPerformed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed

}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        label13.setPreferredSize(new Dimension(65, 23));
    }//GEN-LAST:event_formWindowActivated

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed

    }//GEN-LAST:event_ppStokActionPerformed

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed

    }//GEN-LAST:event_ppStok1ActionPerformed

    private void tblObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblObatMouseClicked
        // TODO add your handling code here:
        int row = tblObat.getSelectedRow();
        if (row > -1) {
            Obat obtTbl = model.get(tblObat.convertRowIndexToModel(row));
            Obat obat = ObatDao.getObat(depoKode, obtTbl.getKodeObat());
            if (obat != null) {
                obtTbl.setHargaBeli(obat.getHargaBeli());
                addQty.setData(obtTbl, racikanList);
                addQty.setVisible(true);
            }
        }

    }//GEN-LAST:event_tblObatMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        if (txtKodeDokter.getText().trim().equals("") || txtKodeDokter.getText().trim().equals("xxx")) {
            Valid.textKosong(txtKodeDokter, "Dokter");
        } else {
            int oke = JOptionPane.showConfirmDialog(null, "Apakah data sudah benar, silahkan cek kembali sebelum disimpan?", "Perhatian", JOptionPane.YES_NO_OPTION);
            if (oke == 0) {
                ResepTemplate resep = new ResepTemplate();
                resep.setKdDokter(txtKodeDokter.getText());
                resep.setCode(Utils.AutoID(new Date()));
                resep.setNamaDokter(txtNamaDokter.getText());
                resep.setKdJaminan(rdoFornas.isSelected() ? "K01" : "K02");
                resep.setNamaTemplate(txtNamaTemplate.getText());

                List<ObatResep> biasas = new LinkedList<>();
                List<ObatResep> racikans = new LinkedList<>();
                for (ObatResep o : modelPilihan.getAll()) {
                    if (o.getJenisObat().equals(Obat.OBAT_RACIKAN)) {
                        racikans.add(o);
                    } else {
                        biasas.add(o);
                    }
                }
                resep.setObatTemplateRacikanDetail(racikans);
                resep.setObatTemplateDetail(biasas);
                boolean sukses = false;
                sukses = ResepTemplateDao.save(resep);
                if (sukses) {
                    clean();

                }
            }
        }

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tblPilihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPilihanMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblPilihanMouseClicked

    private void mnHapusItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnHapusItemActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int wkwkw = JOptionPane.showConfirmDialog(null, "Serius mau menghapus obat ini..?", "Perhatian", dialogButton);
        if (wkwkw == 0) {
            int baris = tblPilihan.convertRowIndexToModel(tblPilihan.getSelectedRow());

            if (baris > -1) {
                ObatResep oresep = modelPilihan.get(tblPilihan.convertRowIndexToModel(baris));
                double marginPersen = 28;
                if (jaminan.equals(Konstan.PASIEN_BPJS_KESEHATAN)) {
                    MarginBpjs marginBpjs = MarginDao.getMarginBpjs(oresep.getKodeObat());
                    if (marginBpjs != null) {
                        marginPersen = marginBpjs.getRalan();
                    }

                } else {
                    MarginObatNonBpjs marginNon = MarginDao.getMarginNonBpjs(kdJaminan);
                    if (marginNon != null) {
                        marginPersen = marginNon.getMargin();
                    }

                }
                double margin = (oresep.getHargaBeli() * marginPersen) / 100;
                double hpp = margin + oresep.getHargaBeli();
                total = total - (hpp * oresep.getJumlah());
                modelPilihan.remove(baris);
                lblTotal.setText(Utils.format(total, 2));
            } else {
                JOptionPane.showMessageDialog(null, "Silahkan pilih baris obat yang mau di hapus..");
            }
        }
    }//GEN-LAST:event_mnHapusItemActionPerformed

    private void mnEditObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEditObatActionPerformed
        // TODO add your handling code here:
        int row = tblPilihan.getSelectedRow();
        if (row > -1) {
            ObatResep obatDetail = modelPilihan.get(tblPilihan.convertRowIndexToModel(row));
            if (obatDetail.isParent()) {
                dlgRacikan.setEditData(obatDetail);
                dlgRacikan.setVisible(true);
            } else {
                if(!obatDetail.getRacikan().equals("-")){
                   for(ObatResep o:modelPilihan.getAll()){
                       if(o.isParent() && o.getRacikan().equals(obatDetail.getRacikan())){
                           racikanList.add(o);
                           break;
                       }
                   } 
                }
                addQty.setDataEdit(obatDetail, racikanList);
                addQty.setVisible(true);
            }

        }

    }//GEN-LAST:event_mnEditObatActionPerformed

    private void BtnPilihDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPilihDokterActionPerformed
        // TODO add your handling code here:
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnPilihDokterActionPerformed

    private void rdoFornasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoFornasActionPerformed
        // TODO add your handling code here:
        this.jaminan = Konstan.PASIEN_BPJS_KESEHATAN;
        setData(pro.getProperty("DEPOOBAT"), "K01", Konstan.PASIEN_RALAN);
    }//GEN-LAST:event_rdoFornasActionPerformed

    private void rdoNonFornasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNonFornasActionPerformed
        // TODO add your handling code here:
        this.jaminan = Konstan.PASIEN_UMUM;
        setData(pro.getProperty("DEPOOBAT"), "K02", Konstan.PASIEN_RALAN);
    }//GEN-LAST:event_rdoNonFornasActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if (txtKodeDokter.getText().trim().equals("") || txtKodeDokter.getText().trim().equals("xxx")) {
            Valid.textKosong(txtKodeDokter, "Dokter");
        } else {
            int oke = JOptionPane.showConfirmDialog(null, "Apakah data sudah benar, silahkan cek kembali sebelum disimpan?", "Perhatian", JOptionPane.YES_NO_OPTION);
            if (oke == 0) {
                ResepTemplate resep = new ResepTemplate();
                resep.setKdDokter(txtKodeDokter.getText());
                resep.setCode(templateEdit.getCode());
                resep.setNamaDokter(txtNamaDokter.getText());
                resep.setKdJaminan(rdoFornas.isSelected() ? "K01" : "K02");
                resep.setNamaTemplate(txtNamaTemplate.getText());

                List<ObatResep> biasas = new LinkedList<>();
                List<ObatResep> racikans = new LinkedList<>();
                for (ObatResep o : modelPilihan.getAll()) {
                    if (o.getJenisObat().equals(Obat.OBAT_RACIKAN)) {
                        if (Utils.isBlank(o.getKodeObat())) {
                            o.setKodeObat(o.getKodeRacikan());
                        }
                        racikans.add(o);
                    } else {
                        biasas.add(o);
                    }
                }
                resep.setObatTemplateRacikanDetail(racikans);
                resep.setObatTemplateDetail(biasas);
                boolean sukses = false;
                sukses = ResepTemplateDao.update(resep);
                if (sukses) {
                    clean();
                    dispose();
                }
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void clean() {
        racikanList = new LinkedList<>();
        model.removeAllElements();
        modelPilihan.removeAllElements();
        txtNamaTemplate.setText(null);
        total = 0;
        lblTotal.setText(null);
        this.jaminan = null;
        txtKodeDokter.setText(null);
        txtNamaDokter.setText(null);
        rdoFornas.setSelected(false);
        rdoNonFornas.setSelected(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                DlgInputTemplateResepDokter dialog = new DlgInputTemplateResepDokter(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DlgInputTemplateResepDokter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnKeluar;
    private widget.Button BtnPilihDokter;
    private widget.Button BtnTambah;
    private widget.PanelBiasa FormInput;
    private javax.swing.JPopupMenu Popup;
    private widget.Button btnEdit;
    private widget.Button btnSimpan;
    private javax.swing.ButtonGroup grupKatergori;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private widget.Label label1;
    private widget.Label label13;
    private widget.Label label2;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label lblDepo;
    private widget.Label lblTotal;
    private javax.swing.JMenuItem mnEditObat;
    private javax.swing.JMenuItem mnHapusItem;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.PanelBiasa panelBiasa3;
    private widget.panelisi panelisi3;
    private javax.swing.JPopupMenu popObatPilihan;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private javax.swing.JMenuItem ppStok1;
    private javax.swing.JRadioButton rdoFornas;
    private javax.swing.JRadioButton rdoNonFornas;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tblObat;
    private widget.Table tblPilihan;
    private widget.TextBox txtCari;
    private widget.TextBox txtKodeDokter;
    private widget.TextBox txtNamaDokter;
    private widget.TextBox txtNamaTemplate;
    // End of variables declaration//GEN-END:variables

}

/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package inventory;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import simrskhanza.DlgCariBangsal;
import com.herinoid.rsi.model.DetailPemberianObat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import com.herinoid.rsi.table.DefaultTableCellRender;
import com.herinoid.rsi.table.TabelObatPilihan;

/**
 *
 * @author dosen
 */
public final class DlgCariObatNonFornas extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private riwayatobat Trackobat = new riwayatobat();
    private PreparedStatement psobat, pscarikapasitas, psstok, psrekening, ps2, psbatch;
    private ResultSet rsobat, carikapasitas, rsstok, rsrekening, rs2, rsbatch;
    private Jurnal jur = new Jurnal();
    private double h_belicari = 0, hargacari = 0, sisacari = 0, x = 0, y = 0, embalase = Sequel.cariIsiAngka("select embalase_per_obat from set_embalase"),
            tuslah = Sequel.cariIsiAngka("select tuslah_per_obat from set_embalase"), kenaikan, stokbarang, ttlhpp, ttljual;
    private int jml = 0, i = 0, z = 0, row = 0;
    private boolean[] pilih;
    private double[] jumlah, harga, eb, ts, stok, beli, kapasitas, kandungan;
    private String[] no, kodebarang, namabarang, kodesatuan, letakbarang, namajenis, industri, aturan, kategori, golongan, nobatch, nofaktur, kadaluarsa;
    private DlgBarang barang = new DlgBarang(null, false);
    private String signa1 = "1", signa2 = "1", kdObatSK = "", requestJson = "", nokunjungan = "", URL = "", otorisasi, sql = "", no_batchcari = "", tgl_kadaluarsacari = "", no_fakturcari = "", aktifkanbatch = "no", aktifpcare = "no", noresep = "", Suspen_Piutang_Obat_Ranap = "", Obat_Ranap = "", HPP_Obat_Rawat_Inap = "", Persediaan_Obat_Rawat_Inap = "";
    private WarnaTable2 warna = new WarnaTable2();
    private DlgCariBangsal caribangsal = new DlgCariBangsal(null, false);
    public DlgAturanPakai aturanpakai = new DlgAturanPakai(null, false);
    private DlgMetodeRacik metoderacik = new DlgMetodeRacik(null, false);
    private WarnaTable2 warna2 = new WarnaTable2();
    private WarnaTable2 warna3 = new WarnaTable2();
    private final Properties prop = new Properties();
    private ObjectMapper mapper = new ObjectMapper();
    private String[] arrSplit;
    private boolean sukses = true;
    private final Set<DetailPemberianObat> detailObats;
    private final TabelObatPilihan modelPilihan;
    private String ejam, menite, detike, jamKomplit;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgCariObatNonFornas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(656, 250);

        Object[] row = {"K", "Jumlah", "Kode", "Nama Barang", "Satuan", "Letak Barang",
            "Harga(Rp)", "Jenis Obat", "Emb", "Tslh", "Stok", "I.F.", "H.Beli",
            "Aturan Pakai", "Kategori", "Golongan", "No.Batch", "No.Faktur", "Kadaluarsa"
        };
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 1) || (colIndex == 8) || (colIndex == 9) || (colIndex == 13) || (colIndex == 16)) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbObat.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 19; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(45);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 6) {
                column.setPreferredWidth(70);
            } else if (i == 7) {
                column.setPreferredWidth(85);
            } else if (i == 8) {
                column.setPreferredWidth(40);
            } else if (i == 9) {
                column.setPreferredWidth(40);
            } else if (i == 10) {
                column.setPreferredWidth(40);
            } else if (i == 11) {
                column.setPreferredWidth(80);
            } else if (i == 12) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 13) {
                column.setPreferredWidth(100);
            } else if (i == 14) {
                column.setPreferredWidth(80);
            } else if (i == 15) {
                column.setPreferredWidth(80);
            } else if (i == 16) {
                column.setPreferredWidth(75);
            } else if (i == 17) {
                column.setPreferredWidth(75);
            } else if (i == 18) {
                column.setPreferredWidth(65);
            }
        }
        warna.kolom = 1;
        tbObat.setDefaultRenderer(Object.class, warna);

        jam();

        caribangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (caribangsal.getTable().getSelectedRow() != -1) {
                    kdgudang.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(), 0).toString());
                    nmgudang.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(), 1).toString());
                }
                kdgudang.requestFocus();
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
                    tbObat.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tbObat.getSelectedRow(), 13);
                }
                tbObat.requestFocus();
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

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifkanbatch = prop.getProperty("AKTIFKANBATCHOBAT");
            if (aktifkanbatch.equals("no")) {
                ppStok.setVisible(true);
            } else {
                ppStok.setVisible(false);
            }
            otorisasi = koneksiDB.USERPCARE() + ":" + koneksiDB.PASSPCARE() + ":095";
            URL = prop.getProperty("URLAPIPCARE");
        } catch (Exception e) {
            System.out.println("E : " + e);
            aktifkanbatch = "no";
            ppStok.setVisible(true);
        }

        try {
            psrekening = koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening = psrekening.executeQuery();
                while (rsrekening.next()) {
                    Suspen_Piutang_Obat_Ranap = rsrekening.getString("Suspen_Piutang_Obat_Ranap");
                    Obat_Ranap = rsrekening.getString("Obat_Ranap");
                    HPP_Obat_Rawat_Inap = rsrekening.getString("HPP_Obat_Rawat_Inap");
                    Persediaan_Obat_Rawat_Inap = rsrekening.getString("Persediaan_Obat_Rawat_Inap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : " + e);
            } finally {
                if (rsrekening != null) {
                    rsrekening.close();
                }
                if (psrekening != null) {
                    psrekening.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        FormInput.setPreferredSize(new Dimension(864, 108));
        detailObats = new HashSet<>();
        modelPilihan = new TabelObatPilihan();
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
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSeek5 = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnTambah1 = new widget.Button();
        BtnHapus = new widget.Button();
        label13 = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        ChkNoResep = new widget.CekBox();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        jLabel10 = new widget.Label();
        LblNoRawat = new widget.TextBox();
        jLabel11 = new widget.Label();
        LblNoRM = new widget.TextBox();
        jLabel12 = new widget.Label();
        LblNamaPasien = new widget.TextBox();
        jPanel1 = new javax.swing.JPanel();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        tblPilihan = new widget.Table();

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
        KdPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPjKeyPressed(evt);
            }
        });

        kelas.setHighlighter(null);
        kelas.setName("kelas"); // NOI18N
        kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat, Alkes & BHP Medis Non Fornas BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setWarnaAtas(new java.awt.Color(255, 204, 255));
        panelisi3.setWarnaBawah(new java.awt.Color(255, 204, 255));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setForeground(new java.awt.Color(0, 0, 0));
        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setForeground(new java.awt.Color(255, 255, 255));
        TCari.setToolTipText("Alt+C");
        TCari.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(285, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('5');
        BtnSeek5.setToolTipText("Alt+5");
        BtnSeek5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek5);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnTambah1.setMnemonic('3');
        BtnTambah1.setToolTipText("Alt+3");
        BtnTambah1.setName("BtnTambah1"); // NOI18N
        BtnTambah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah1);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelisi3.add(BtnHapus);

        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label13);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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
        FormInput.setWarnaAtas(new java.awt.Color(255, 204, 255));
        FormInput.setWarnaBawah(new java.awt.Color(255, 204, 255));
        FormInput.setLayout(null);

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tanggal :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(10, 40, 68, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-05-2020" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(80, 40, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(180, 40, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(250, 40, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(320, 40, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setOpaque(false);
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(390, 40, 30, 23);

        label12.setForeground(new java.awt.Color(0, 0, 0));
        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(label12);
        label12.setBounds(410, 40, 50, 23);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP", "Beli Luar", "Karyawan" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(100, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        FormInput.add(Jeniskelas);
        Jeniskelas.setBounds(460, 40, 110, 23);

        ChkNoResep.setForeground(new java.awt.Color(0, 0, 0));
        ChkNoResep.setSelected(true);
        ChkNoResep.setText("No.Resep   ");
        ChkNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkNoResep.setName("ChkNoResep"); // NOI18N
        ChkNoResep.setOpaque(false);
        ChkNoResep.setPreferredSize(new java.awt.Dimension(85, 23));
        ChkNoResep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkNoResepItemStateChanged(evt);
            }
        });
        FormInput.add(ChkNoResep);
        ChkNoResep.setBounds(580, 40, 100, 23);

        label21.setForeground(new java.awt.Color(0, 0, 0));
        label21.setText("Depol :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(10, 70, 68, 23);

        kdgudang.setEditable(false);
        kdgudang.setForeground(new java.awt.Color(255, 255, 255));
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        FormInput.add(kdgudang);
        kdgudang.setBounds(80, 70, 55, 23);

        nmgudang.setEditable(false);
        nmgudang.setForeground(new java.awt.Color(255, 255, 255));
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmgudang);
        nmgudang.setBounds(140, 70, 197, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        FormInput.add(BtnGudang);
        BtnGudang.setBounds(340, 70, 28, 23);

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel10);
        jLabel10.setBounds(10, 10, 65, 23);

        LblNoRawat.setEditable(false);
        LblNoRawat.setForeground(new java.awt.Color(255, 255, 255));
        LblNoRawat.setName("LblNoRawat"); // NOI18N
        LblNoRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(LblNoRawat);
        LblNoRawat.setBounds(80, 10, 123, 23);

        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("No.RM :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel11);
        jLabel11.setBounds(190, 10, 65, 23);

        LblNoRM.setEditable(false);
        LblNoRM.setForeground(new java.awt.Color(255, 255, 255));
        LblNoRM.setName("LblNoRM"); // NOI18N
        LblNoRM.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(LblNoRM);
        LblNoRM.setBounds(260, 10, 90, 23);

        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nama Pasien :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel12);
        jLabel12.setBounds(370, 10, 80, 23);

        LblNamaPasien.setEditable(false);
        LblNamaPasien.setForeground(new java.awt.Color(255, 255, 255));
        LblNamaPasien.setName("LblNamaPasien"); // NOI18N
        LblNamaPasien.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(LblNamaPasien);
        LblNamaPasien.setBounds(450, 10, 237, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.GridLayout(2, 1, 1, 0));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        jPanel1.add(Scroll);
        Scroll.getAccessibleContext().setAccessibleParent(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OBAT TERPILIH", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(153, 0, 153))); // NOI18N
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
        tblPilihan.setName("tblPilihan"); // NOI18N
        scrollPane1.setViewportView(tblPilihan);

        jPanel2.add(scrollPane1);

        jPanel1.add(jPanel2);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbObat.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        BtnCariActionPerformed(evt);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        int intRow = tbObat.getSelectedRow();
        if (tbObat.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 2) {
                if (akses.getform().equals("DlgPemberianObat")) {
                    dispose();
                    
                }
            }
            setData(intRow);
           

        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (kdgudang.getText().equals("")) {
            Valid.textKosong(TCari, "Lokasi");
        } else if (tbObat.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    getData();
                    i = tbObat.getSelectedColumn();
                    if (i == 8) {
                        try {
                            if (tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0.0") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0,0")) {
                                tbObat.setValueAt(embalase, tbObat.getSelectedRow(), 8);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 8);
                        }
                    } else if (i == 9) {
                        try {
                            if (tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0.0") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0,0")) {
                                tbObat.setValueAt(tuslah, tbObat.getSelectedRow(), 9);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 9);
                        }

                        TCari.setText("");
                        TCari.requestFocus();
                    } else if ((i == 10) || (i == 3)) {
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                if (tbObat.getSelectedRow() != -1) {
                    tbObat.setValueAt("", tbObat.getSelectedRow(), 1);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.requestFocus();
            } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                i = tbObat.getSelectedColumn();
                if (i == 2) {
                    try {
                        getData();

                        try {
                            if (tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0.0") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0,0")) {
                                tbObat.setValueAt(embalase, tbObat.getSelectedRow(), 8);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 8);
                        }

                        try {
                            if (tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0.0") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0,0")) {
                                tbObat.setValueAt(tuslah, tbObat.getSelectedRow(), 9);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 9);
                        }

                    } catch (Exception e) {
                        tbObat.setValueAt(0, tbObat.getSelectedRow(), 10);
                    }
                } else if (i == 13) {
                    aturanpakai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //barang.setModal(true);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        barang.setLocationRelativeTo(internalFrame1);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if (TNoRw.getText().trim().equals("")) {
        Valid.textKosong(TCari, "Data");
    } else if (kdgudang.getText().equals("")) {
        Valid.textKosong(TCari, "Lokasi");
    } else {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                ChkJln.setSelected(false);
                Sequel.AutoComitFalse();
                sukses = true;
                ttlhpp = 0;
                ttljual = 0;
                for (DetailPemberianObat obat : detailObats) {
                    pscarikapasitas = koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");
                    try {
                        pscarikapasitas.setString(1, obat.getKodeBrng());
                        carikapasitas = pscarikapasitas.executeQuery();
                        if (carikapasitas.next()) {
                            if (Sequel.menyimpantf2("detail_pemberian_obat", "?,?,?,?,?,?,?,?,?,?,?,?,?,?", "data", 14, new String[]{
                                obat.getTglPerawatan(), obat.getJam(), TNoRw.getText(), obat.getKodeBrng(), "" + obat.gethBeli(),
                                "" + obat.getBiayaObat(), "" + obat.getJml(),
                                "" + obat.getEmbalase(), "" + obat.getTuslah(),"" + obat.getHargaJual(),
                                obat.getStatus(), obat.getKdBangsal(), obat.getNoBatch(), obat.getNoFaktur()
                            }) == true) {
                                if (!obat.getAturanPakai().equals("")) {
                                    Sequel.menyimpan("aturan_pakai", "?,?,?,?,?", 5, new String[]{
                                        Valid.SetTgl(DTPTgl.getSelectedItem() + ""), obat.getJam(), TNoRw.getText(), obat.getKodeBrng(), obat.getAturanPakai()
                                    });
                                }
                                if (aktifpcare.equals("yes")) {
                                    arrSplit = obat.getAturanPakai().toLowerCase().split("x");
                                    signa1 = "1";
                                    try {
                                        if (!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa1 = arrSplit[0].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa1 = "1";
                                    }
                                    signa2 = "1";
                                    try {
                                        if (!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa2 = arrSplit[1].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa2 = "1";
                                    }

                                }
                            } else {
                                sukses = false;
                            }
                        } else {
                            if (Sequel.menyimpantf2("detail_pemberian_obat", "?,?,?,?,?,?,?,?,?,?,?,?,?,?", "data", 14, new String[]{
                                obat.getTglPerawatan(), obat.getJam(), TNoRw.getText(), obat.getKodeBrng(), "" + obat.gethBeli(),
                                "" + obat.getBiayaObat(), "" + obat.getJml(),
                                "" + obat.getEmbalase(), "" + obat.getTuslah(), "" + obat.getHargaJual(),
                                obat.getStatus(), obat.getKdBangsal(), obat.getNoBatch(), obat.getNoFaktur()
                            }) == true) {
                                if (!obat.getAturanPakai().equals("")) {
                                    Sequel.menyimpan("aturan_pakai", "?,?,?,?,?", 5, new String[]{
                                        Valid.SetTgl(DTPTgl.getSelectedItem() + ""), obat.getJam(), TNoRw.getText(), obat.getKodeBrng(), obat.getAturanPakai()
                                    });
                                }
                                if (aktifpcare.equals("yes")) {
                                    arrSplit = obat.getAturanPakai().toLowerCase().split("x");
                                    signa1 = "1";
                                    try {
                                        if (!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa1 = arrSplit[0].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa1 = "1";
                                    }
                                    signa2 = "1";
                                    try {
                                        if (!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa2 = arrSplit[1].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa2 = "1";
                                    }

                                }
                            } else {
                                sukses = false;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Kapasitas : " + e);
                    } finally {
                        if (carikapasitas != null) {
                            carikapasitas.close();
                        }
                        if (pscarikapasitas != null) {
                            pscarikapasitas.close();
                        }
                    }
                }

                if (!noresep.equals("")) {
                    Sequel.mengedit("resep_obat", "no_resep='" + noresep + "'", "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',jam='" + ejam + ":" + menite + ":" + detike + "'");
                }

                if (sukses == true) {
                    Sequel.Commit();
                    detailObats.clear();
                } else {
                    sukses = false;
                    JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
                ChkJln.setSelected(true);

                if (sukses == true) {
                    if (ChkNoResep.isSelected() == true) {
                        DlgResepObat resep = new DlgResepObat(null, false);
                        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        resep.setLocationRelativeTo(internalFrame1);
                        resep.emptTeks();
                        resep.isCek();
                        resep.setNoRm(TNoRw.getText(), DTPTgl.getDate(), DTPTgl.getDate(), ejam, menite, detike, "ranap");
                        resep.tampil();
                        //resep.setAlwaysOnTop(true);
                        resep.dokter.setAlwaysOnTop(true);
                        resep.setVisible(true);
                    }
                    dispose();
                }
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Kemungkinan ada data yang sama dimasukkan sebelumnya?\nKapasitas belum dimasukkan...!");
            }
        }
    }
    ejam = null;
    menite = null;
    detike = null;
    jamKomplit = null;
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    DlgCariKonversi carikonversi = new DlgCariKonversi(null, false);
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek5KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for (i = 0; i < tbObat.getRowCount(); i++) {
        tbObat.setValueAt("", i, 1);
        tbObat.setValueAt(0, i, 10);
        tbObat.setValueAt(0, i, 9);
        tbObat.setValueAt(0, i, 8);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
    if (this.isVisible() == true) {
        tampil();
    }
}//GEN-LAST:event_JeniskelasItemStateChanged

private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
    Valid.pindah(evt, cmbDtk, TCari);
}//GEN-LAST:event_JeniskelasKeyPressed

private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
    Valid.pindah(evt, BtnKeluar, cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
    Valid.pindah(evt, DTPTgl, cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
    Valid.pindah(evt, cmbJam, cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
    Valid.pindah(evt, cmbMnt, Jeniskelas);
}//GEN-LAST:event_cmbDtkKeyPressed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        BtnTambah1.setVisible(false);
        BtnHapus.setVisible(false);
        label13.setPreferredSize(new Dimension(65, 23));
    }//GEN-LAST:event_formWindowActivated

    private void ChkNoResepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkNoResepItemStateChanged
        if (ChkNoResep.isSelected() == true) {
            DlgResepObat resep = new DlgResepObat(null, false);
            resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resep.setLocationRelativeTo(internalFrame1);
            resep.emptTeks();
            resep.isCek();
            resep.setNoRm(TNoRw.getText(), DTPTgl.getDate(), DTPTgl.getDate(), cmbJam.getSelectedItem().toString(), cmbMnt.getSelectedItem().toString(), cmbDtk.getSelectedItem().toString(), "ranap");
            resep.tampil();
            resep.setVisible(true);
        }
    }//GEN-LAST:event_ChkNoResepItemStateChanged

    private void KdPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPjKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPjKeyPressed

    private void kelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasKeyPressed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        if (kdgudang.getText().equals("")) {
            Valid.textKosong(TCari, "Lokasi");
        } else {
            for (i = 0; i < tbObat.getRowCount(); i++) {
                try {
                    stokbarang = 0;
                    psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                    try {
                        psstok.setString(1, kdgudang.getText());
                        psstok.setString(2, tbObat.getValueAt(i, 2).toString());
                        rsstok = psstok.executeQuery();
                        if (rsstok.next()) {
                            stokbarang = rsstok.getDouble(1);
                        }
                    } catch (Exception e) {
                        stokbarang = 0;
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsstok != null) {
                            rsstok.close();
                        }
                        if (psstok != null) {
                            psstok.close();
                        }
                    }
                    tbObat.setValueAt(stokbarang, i, 10);
                } catch (Exception e) {
                    tbObat.setValueAt(0, i, 10);
                }
            }
        }
    }//GEN-LAST:event_ppStokActionPerformed

    private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
                break;
            case KeyEvent.VK_PAGE_UP:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
                TCari.requestFocus();
                break;
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
                BtnSimpan.requestFocus();
                break;
            case KeyEvent.VK_UP:
                BtnGudangActionPerformed(null);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_kdgudangKeyPressed

    private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
        caribangsal.isCek();
        caribangsal.emptTeks();
        caribangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        caribangsal.setLocationRelativeTo(internalFrame1);
        caribangsal.setAlwaysOnTop(false);
        caribangsal.setVisible(true);
    }//GEN-LAST:event_BtnGudangActionPerformed

    private void BtnTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambah1ActionPerformed
//        i=tabModeObatRacikan.getRowCount()+1;
//        if(i==99){
//            JOptionPane.showMessageDialog(null,"Maksimal 98 Racikan..!!");
//        }else{
//            tabModeObatRacikan.addRow(new Object[]{""+i,"","","","","",""});
//        }
    }//GEN-LAST:event_BtnTambah1ActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
//        if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),5).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),6).equals("")){
//            tabModeObatRacikan.removeRow(tbObatRacikan.getSelectedRow());
//        }else{
//            JOptionPane.showMessageDialog(null,"Maaf sudah terisi, gak boleh dihapus..!!");
//        }

    }//GEN-LAST:event_BtnHapusActionPerformed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if (this.isVisible() == true) {
            getData();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCekStok ceksetok = new DlgCekStok(null, false);
        ceksetok.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ceksetok.setLocationRelativeTo(internalFrame1);
        ceksetok.setAlwaysOnTop(false);
        ceksetok.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppStok1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariObatNonFornas dialog = new DlgCariObatNonFornas(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambah1;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkNoResep;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox KdPj;
    private widget.TextBox LblNamaPasien;
    private widget.TextBox LblNoRM;
    private widget.TextBox LblNoRawat;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private widget.TextBox kdgudang;
    private widget.TextBox kelas;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label21;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private javax.swing.JMenuItem ppStok1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbObat;
    private widget.Table tblPilihan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        tabMode.setRowCount(0);
        jml = 0;
        for (i = 0; i < tbObat.getRowCount(); i++) {
            if (!tbObat.getValueAt(i, 0).toString().equals("")) {
                jml++;
            }
        }

        pilih = null;
        pilih = new boolean[jml];
        jumlah = null;
        jumlah = new double[jml];
        eb = null;
        eb = new double[jml];
        ts = null;
        ts = new double[jml];
        stok = null;
        stok = new double[jml];
        harga = null;
        harga = new double[jml];
        kodebarang = null;
        kodebarang = new String[jml];
        namabarang = null;
        namabarang = new String[jml];
        kodesatuan = null;
        kodesatuan = new String[jml];
        letakbarang = null;
        letakbarang = new String[jml];
        namajenis = null;
        namajenis = new String[jml];
        industri = null;
        industri = new String[jml];
        beli = null;
        beli = new double[jml];
        aturan = null;
        aturan = new String[jml];
        kategori = null;
        kategori = new String[jml];
        golongan = null;
        golongan = new String[jml];
        nobatch = new String[jml];
        nofaktur = new String[jml];
        kadaluarsa = new String[jml];

        jml = 0;
        for (i = 0; i < tbObat.getRowCount(); i++) {
            if (!tbObat.getValueAt(i, 1).toString().equals("")) {
                pilih[jml] = Boolean.parseBoolean(tbObat.getValueAt(i, 0).toString());
                try {
                    jumlah[jml] = Double.parseDouble(tbObat.getValueAt(i, 1).toString());
                } catch (Exception e) {
                    jumlah[jml] = 0;
                }
                kodebarang[jml] = tbObat.getValueAt(i, 2).toString();
                namabarang[jml] = tbObat.getValueAt(i, 3).toString();
                kodesatuan[jml] = tbObat.getValueAt(i, 4).toString();
                letakbarang[jml] = tbObat.getValueAt(i, 5).toString();
                try {
                    harga[jml] = Double.parseDouble(tbObat.getValueAt(i, 6).toString());
                } catch (Exception e) {
                    harga[jml] = 0;
                }
                namajenis[jml] = tbObat.getValueAt(i, 7).toString();
                try {
                    eb[jml] = Double.parseDouble(tbObat.getValueAt(i, 8).toString());
                } catch (Exception e) {
                    eb[jml] = 0;
                }
                try {
                    ts[jml] = Double.parseDouble(tbObat.getValueAt(i, 9).toString());
                } catch (Exception e) {
                    ts[jml] = 0;
                }
                try {
                    stok[jml] = Double.parseDouble(tbObat.getValueAt(i, 10).toString());
                } catch (Exception e) {
                    stok[jml] = 0;
                }
                industri[jml] = tbObat.getValueAt(i, 11).toString();
                try {
                    beli[jml] = Double.parseDouble(tbObat.getValueAt(i, 12).toString());
                } catch (Exception e) {
                    beli[jml] = 0;
                }
                aturan[jml] = tbObat.getValueAt(i, 13).toString();
                kategori[jml] = tbObat.getValueAt(i, 14).toString();
                golongan[jml] = tbObat.getValueAt(i, 15).toString();
                nobatch[jml] = tbObat.getValueAt(i, 16).toString();
                nofaktur[jml] = tbObat.getValueAt(i, 17).toString();
                kadaluarsa[jml] = tbObat.getValueAt(i, 18).toString();
                jml++;
            }
        }

//        Valid.tabelKosong(tabMode);
//        tabMode.setRowCount(0);
//        for (i = 0; i < jml; i++) {
//            tabMode.addRow(new Object[]{pilih[i], jumlah[i], kodebarang[i], namabarang[i],
//                kodesatuan[i], letakbarang[i], harga[i], namajenis[i], eb[i], ts[i], stok[i], industri[i],
//                beli[i], aturan[i], kategori[i], golongan[i], nobatch[i], nofaktur[i], kadaluarsa[i]
//            });
//        }
        try {

            if (kenaikan > 0) {
                if (aktifkanbatch.equals("yes")) {
                    if (aktifpcare.equals("yes")) {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                + " and data_batch.kode_brng=databarang.kode_brng and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    } else {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "
                                + " and data_batch.kode_brng=databarang.kode_brng and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where data_batch.sisa>0 and databarang.kode_brng like ? or "
                            + " data_batch.sisa>0 and databarang.nama_brng like ? or "
                            + " data_batch.sisa>0 and kategori_barang.nama like ? or "
                            + " data_batch.sisa>0 and golongan_barang.nama like ? or "
                            + " data_batch.sisa>0 and data_batch.no_batch like ? or "
                            + " data_batch.sisa>0 and jenis.nama like ? order by data_batch.tgl_kadaluarsa");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        psobat.setString(7, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    if (aktifpcare.equals("yes")) {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    } else {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "
                                + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where databarang.status='1' and databarang.kode_brng like ? or "
                            + " databarang.status='1' and databarang.nama_brng like ? or "
                            + " databarang.status='1' and kategori_barang.nama like ? or "
                            + " databarang.status='1' and golongan_barang.nama like ? or "
                            + " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            } else {
                if (aktifkanbatch.equals("yes")) {
                    if (aktifpcare.equals("yes")) {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.kelas1,"
                                + " data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                                + " inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and data_batch.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  ";
                    } else {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.kelas1,"
                                + " data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                                + " on data_batch.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where data_batch.sisa>0 and databarang.kode_brng like ? or "
                            + " data_batch.sisa>0 and databarang.nama_brng like ? or "
                            + " data_batch.sisa>0 and kategori_barang.nama like ? or "
                            + " data_batch.sisa>0 and golongan_barang.nama like ? or "
                            + " data_batch.sisa>0 and data_batch.no_batch like ? or "
                            + " data_batch.sisa>0 and jenis.nama like ? order by data_batch.tgl_kadaluarsa");
                    try {
                        psobat.setString(1, "%" + TCari.getText().trim() + "%");
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    if (aktifpcare.equals("yes")) {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                                + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                                + " inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  ";
                    } else {

                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                                + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                                + " on databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where databarang.status='1' and databarang.kode_brng like ? or "
                            + " databarang.status='1' and databarang.nama_brng like ? or "
                            + " databarang.status='1' and kategori_barang.nama like ? or "
                            + " databarang.status='1' and golongan_barang.nama like ? or "
                            + " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1, "%" + TCari.getText().toUpperCase() + "%");
                        psobat.setString(2, "%" + TCari.getText().toUpperCase() + "%");
                        psobat.setString(3, "%" + TCari.getText().toUpperCase() + "%");
                        psobat.setString(4, "%" + TCari.getText().toUpperCase() + "%");
                        psobat.setString(5, "%" + TCari.getText().toUpperCase() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
//                            System.out.println("datane dapet ngga ");
                            if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tabMode.addRow(new Object[]{false, "0", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            }
                        }
                        tbObat.setModel(tabMode);
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void emptTeks() {
        TCari.requestFocus();
    }

    private void setData(int baris) {
        if (jamKomplit == null) {
            jamKomplit = cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem();
            ejam = cmbJam.getSelectedItem().toString();
            menite = cmbMnt.getSelectedItem().toString();
            detike = cmbDtk.getSelectedItem().toString();
        }
        DetailPemberianObat det = new DetailPemberianObat();
        det.setTglPerawatan(Valid.SetTgl(DTPTgl.getSelectedItem() + ""));
        det.setJam(jamKomplit);
        det.setNoRawat(TNoRw.getText());
        det.setKodeBrng(tbObat.getValueAt(baris, 2).toString());
        det.sethBeli(Double.parseDouble(tbObat.getValueAt(baris, 12).toString()));
        det.setBiayaObat(Double.parseDouble(tbObat.getValueAt(baris, 6).toString()));
        det.setJml(0);
        det.setEmbalase(Double.parseDouble(tbObat.getValueAt(baris, 8).toString()));
        det.setTuslah(Double.parseDouble(tbObat.getValueAt(baris, 9).toString()));
        det.setTotal(0);
        det.setStatus("Ranap");
        det.setKdBangsal(kdgudang.getText());
        det.setNoBatch(tbObat.getValueAt(baris, 16).toString());
        det.setNoFaktur(tbObat.getValueAt(baris, 17).toString());
        det.setAturanPakai(tbObat.getValueAt(baris, 13).toString());
        det.setNamaObat(tbObat.getValueAt(baris, 3).toString());
        det.setSatuan(tbObat.getValueAt(baris, 4).toString());
        if (tbObat.getValueAt(baris, 0).toString().equals("true")) {
            if (!detailObats.contains(det)) {     
                detailObats.add(det);
            }            
        } else {
            if (detailObats.contains(det)) {                
                detailObats.remove(det);
            }
        }

        try {
            modelPilihan.removeAllElements();
            modelPilihan.add(detailObats);
            tblPilihan.setModel(modelPilihan);
            for (int i = 0; i < tblPilihan.getColumnCount(); i++) {
                tblPilihan.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRender());
            }
        } catch (Exception ex) {
            Logger.getLogger(DlgCariObatNonFornas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void getData() {
        if (nmgudang.getText().trim().equals("")) {
            Valid.textKosong(kdgudang, "Lokasi");
        } else {
            if (tbObat.getSelectedRow() != -1) {
                row = tbObat.getSelectedRow();
                if (!tbObat.getValueAt(row, 1).toString().equals("")) {
                    try {
                        if (Double.parseDouble(tbObat.getValueAt(row, 1).toString()) > 0) {
                            if (aktifkanbatch.equals("no")) {
                                stokbarang = 0;
                                psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                                try {
                                    psstok.setString(1, kdgudang.getText());
                                    psstok.setString(2, tbObat.getValueAt(row, 2).toString());
                                    rsstok = psstok.executeQuery();
                                    if (rsstok.next()) {
                                        stokbarang = rsstok.getDouble(1);
                                    }
                                } catch (Exception e) {
                                    stokbarang = 0;
                                    System.out.println("Notifikasi : " + e);
                                } finally {
                                    if (rsstok != null) {
                                        rsstok.close();
                                    }
                                    if (psstok != null) {
                                        psstok.close();
                                    }
                                }
                                tbObat.setValueAt(stokbarang, row, 10);
                            } else {
                                stokbarang = 0;
                                psstok = koneksi.prepareStatement("select ifnull(sisa,'0') from data_batch where no_batch=? and kode_brng=?");
                                try {
                                    psstok.setString(1, tbObat.getValueAt(row, 16).toString());
                                    psstok.setString(2, tbObat.getValueAt(row, 2).toString());
                                    rsstok = psstok.executeQuery();
                                    if (rsstok.next()) {
                                        stokbarang = rsstok.getDouble(1);
                                    }
                                } catch (Exception e) {
                                    stokbarang = 0;
                                    System.out.println("Notifikasi : " + e);
                                } finally {
                                    if (rsstok != null) {
                                        rsstok.close();
                                    }
                                    if (psstok != null) {
                                        psstok.close();
                                    }
                                }

                                tbObat.setValueAt(stokbarang, row, 10);
                            }

                            y = 0;
                            try {
                                if (tbObat.getValueAt(row, 0).toString().equals("true")) {
                                    pscarikapasitas = koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");
                                    try {
                                        pscarikapasitas.setString(1, tbObat.getValueAt(row, 2).toString());
                                        carikapasitas = pscarikapasitas.executeQuery();
                                        if (carikapasitas.next()) {
                                            y = Double.parseDouble(tbObat.getValueAt(row, 1).toString()) / carikapasitas.getDouble(1);
                                        } else {
                                            y = Double.parseDouble(tbObat.getValueAt(row, 1).toString());
                                        }
                                    } catch (Exception e) {
                                        y = Double.parseDouble(tbObat.getValueAt(row, 1).toString());
                                        System.out.println("Kapasitasmu masih kosong broooh : " + e);
                                    } finally {
                                        if (carikapasitas != null) {
                                            carikapasitas.close();
                                        }
                                        if (pscarikapasitas != null) {
                                            pscarikapasitas.close();
                                        }
                                    }
                                } else {
                                    y = Double.parseDouble(tbObat.getValueAt(row, 1).toString());
                                }
                            } catch (Exception e) {
                                y = 0;
                            }

                            stokbarang = 0;
                            try {
                                stokbarang = Double.parseDouble(tbObat.getValueAt(row, 10).toString());
                            } catch (Exception e) {
                                stokbarang = 0;
                            }

                            if (stokbarang < y) {
                                JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                                tbObat.setValueAt("", row, 1);
                            }
                        }
                        if ((tbObat.getSelectedColumn() == 16) || (tbObat.getSelectedColumn() == 17)) {
                            cariBatch();
                            //getData2();
                        }
                    } catch (Exception e) {
                        tbObat.setValueAt("", row, 1);
                        tbObat.setValueAt(0, row, 10);
                    }
                } else {
                    tbObat.setValueAt(0, row, 10);
                }
            }
        }
    }

    private void getDataobat(int data) {
        try {
            if (aktifkanbatch.equals("no")) {
                stokbarang = 0;
                psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                try {
                    psstok.setString(1, kdgudang.getText());
                    psstok.setString(2, tbObat.getValueAt(data, 2).toString());
                    rsstok = psstok.executeQuery();
                    if (rsstok.next()) {
                        stokbarang = rsstok.getDouble(1);
                    }
                } catch (Exception e) {
                    stokbarang = 0;
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rsstok != null) {
                        rsstok.close();
                    }
                    if (psstok != null) {
                        psstok.close();
                    }
                }

                tbObat.setValueAt(stokbarang, data, 10);
                y = 0;
                try {
                    if (tbObat.getValueAt(data, 0).toString().equals("true")) {
                        pscarikapasitas = koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");
                        try {
                            pscarikapasitas.setString(1, tbObat.getValueAt(data, 2).toString());
                            carikapasitas = pscarikapasitas.executeQuery();
                            if (carikapasitas.next()) {
                                y = Double.parseDouble(tbObat.getValueAt(data, 1).toString()) / carikapasitas.getDouble(1);
                            } else {
                                y = Double.parseDouble(tbObat.getValueAt(data, 1).toString());
                            }
                        } catch (Exception e) {
                            y = Double.parseDouble(tbObat.getValueAt(data, 1).toString());
                            System.out.println("Kapasitasmu masih kosong broooh : " + e);
                        } finally {
                            if (carikapasitas != null) {
                                carikapasitas.close();
                            }
                            if (pscarikapasitas != null) {
                                pscarikapasitas.close();
                            }
                        }
                    } else {
                        y = Double.parseDouble(tbObat.getValueAt(data, 1).toString());
                    }
                } catch (Exception e) {
                    y = 0;
                }
                if (stokbarang < y) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                }
            }
        } catch (Exception e) {
            tbObat.setValueAt(0, data, 10);
        }
    }

    public JTable getTable() {
        return tbObat;
    }

    public void isCek() {
        kdgudang.setText(akses.getkdbangsal());
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
        BtnTambah.setEnabled(akses.getobat());
        TCari.requestFocus();

        if (Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", kdgudang.getText()).equals("")) {
            kdgudang.setEditable(true);
            nmgudang.setEditable(true);
            BtnGudang.setEnabled(true);
        } else {
            if (akses.getakses_depo_obat() == true) {
                kdgudang.setEditable(true);
                nmgudang.setEditable(true);
                BtnGudang.setEnabled(true);
            } else {
                kdgudang.setEditable(false);
                nmgudang.setEditable(false);
                BtnGudang.setEnabled(false);
            }
        }

        if (akses.getkode().equals("Admin Utama")) {
            kdgudang.setEditable(true);
            nmgudang.setEditable(true);
            BtnGudang.setEnabled(true);
        }
    }

    public void setNoRm(String norwt, Date tanggal, String jam, String menit, String detik, boolean status) {
        aktifpcare = "no";
        TNoRw.setText(norwt);
        DTPTgl.setDate(tanggal);
        cmbJam.setSelectedItem(jam);
        cmbMnt.setSelectedItem(menit);
        cmbDtk.setSelectedItem(detik);
        ChkJln.setSelected(status);
        this.noresep = "";
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norwt));
        kelas.setText(Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "
                + "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", norwt));
        if (kelas.getText().equals("Kelas 1")) {
            Jeniskelas.setSelectedItem("Kelas 1");
        } else if (kelas.getText().equals("Kelas 2")) {
            Jeniskelas.setSelectedItem("Kelas 2");
        } else if (kelas.getText().equals("Kelas 3")) {
            Jeniskelas.setSelectedItem("Kelas 3");
        } else if (kelas.getText().equals("Kelas Utama")) {
            Jeniskelas.setSelectedItem("Utama/BPJS");
        } else if (kelas.getText().equals("Kelas VIP")) {
            Jeniskelas.setSelectedItem("VIP");
        } else if (kelas.getText().equals("Kelas VVIP")) {
            Jeniskelas.setSelectedItem("VVIP");
        }
        kenaikan = Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ranap where kd_pj='" + KdPj.getText() + "' and kelas='" + kelas.getText() + "'");
        TCari.requestFocus();
    }

    public void setNoRm(String norwt, Date tanggal, String noRm, String nmPasien) {
        aktifpcare = "no";
        TNoRw.setText(norwt);
        DTPTgl.setDate(tanggal);
        LblNoRM.setText(noRm);
        LblNoRawat.setText(norwt);
        LblNamaPasien.setText(nmPasien);
        ChkJln.setSelected(true);
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norwt));
        kelas.setText(Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "
                + "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", norwt));
        if (kelas.getText().equals("Kelas 1")) {
            Jeniskelas.setSelectedItem("Kelas 1");
        } else if (kelas.getText().equals("Kelas 2")) {
            Jeniskelas.setSelectedItem("Kelas 2");
        } else if (kelas.getText().equals("Kelas 3")) {
            Jeniskelas.setSelectedItem("Kelas 3");
        } else if (kelas.getText().equals("Kelas Utama")) {
            Jeniskelas.setSelectedItem("Utama/BPJS");
        } else if (kelas.getText().equals("Kelas VIP")) {
            Jeniskelas.setSelectedItem("VIP");
        } else if (kelas.getText().equals("Kelas VVIP")) {
            Jeniskelas.setSelectedItem("VVIP");
        }
        kenaikan = Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ranap where kd_pj='" + KdPj.getText() + "' and kelas='" + kelas.getText() + "'");
        TCari.requestFocus();
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkJln.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkJln.isSelected() == false) {
                    nilai_jam = cmbJam.getSelectedIndex();
                    nilai_menit = cmbMnt.getSelectedIndex();
                    nilai_detik = cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    public void tampilobat2(String no_resep) {
        this.noresep = no_resep;
        try {
            Valid.tabelKosong(tabMode);
            if (kenaikan > 0) {
                if (aktifkanbatch.equals("yes")) {
                    psobat = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                            + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"
                            + " resep_dokter.jml, resep_dokter.aturan_pakai from databarang inner join jenis inner join industrifarmasi inner join golongan_barang "
                            + " inner join kategori_barang inner join resep_dokter on databarang.kdjns=jenis.kdjns "
                            + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "
                            + " and resep_dokter.kode_brng=databarang.kode_brng where "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.kode_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.nama_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and kategori_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and golongan_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, no_resep);
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, no_resep);
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, no_resep);
                        psobat.setString(7, "%" + TCari.getText().trim() + "%");
                        psobat.setString(8, no_resep);
                        psobat.setString(9, "%" + TCari.getText().trim() + "%");
                        psobat.setString(10, no_resep);
                        psobat.setString(11, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            no_batchcari = "";
                            tgl_kadaluarsacari = "";
                            no_fakturcari = "";
                            h_belicari = 0;
                            hargacari = 0;
                            sisacari = 0;
                            psbatch = koneksi.prepareStatement(
                                    "select no_batch, kode_brng, tgl_beli, tgl_kadaluarsa, asal, no_faktur, "
                                    + "h_beli,(h_beli+(h_beli*?)) as harga, sisa from data_batch where "
                                    + "sisa>0 and kode_brng=? order by tgl_kadaluarsa desc limit 1");
                            try {
                                psbatch.setDouble(1, kenaikan);
                                psbatch.setString(2, rsobat.getString("kode_brng"));
                                rsbatch = psbatch.executeQuery();
                                while (rsbatch.next()) {
                                    no_batchcari = rsbatch.getString("no_batch");
                                    tgl_kadaluarsacari = rsbatch.getString("tgl_kadaluarsa");
                                    no_fakturcari = rsbatch.getString("no_faktur");
                                    h_belicari = rsbatch.getDouble("h_beli");
                                    hargacari = rsbatch.getDouble("harga");
                                    sisacari = rsbatch.getDouble("sisa");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : " + e);
                            } finally {
                                if (rsbatch != null) {
                                    rsbatch.close();
                                }
                                if (psbatch != null) {
                                    psbatch.close();
                                }
                            }
                            if (rsobat.getDouble("jml") > sisacari) {
                                JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                                tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(hargacari, 100),
                                    rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                    h_belicari, rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    no_batchcari, no_fakturcari, tgl_kadaluarsacari
                                });
                            } else {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(hargacari, 100),
                                    rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                    h_belicari, rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    no_batchcari, no_fakturcari, tgl_kadaluarsacari
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    psobat = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                            + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"
                            + " resep_dokter.jml, resep_dokter.aturan_pakai from databarang inner join jenis inner join industrifarmasi inner join golongan_barang "
                            + " inner join kategori_barang inner join resep_dokter on databarang.kdjns=jenis.kdjns "
                            + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "
                            + " and resep_dokter.kode_brng=databarang.kode_brng where "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.kode_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.nama_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and kategori_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and golongan_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, no_resep);
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, no_resep);
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, no_resep);
                        psobat.setString(7, "%" + TCari.getText().trim() + "%");
                        psobat.setString(8, no_resep);
                        psobat.setString(9, "%" + TCari.getText().trim() + "%");
                        psobat.setString(10, no_resep);
                        psobat.setString(11, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            sisacari = 0;
                            psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                            try {
                                psstok.setString(1, kdgudang.getText());
                                psstok.setString(2, rsobat.getString("kode_brng"));
                                rsstok = psstok.executeQuery();
                                if (rsstok.next()) {
                                    sisacari = rsstok.getDouble(1);
                                }
                            } catch (Exception e) {
                                sisacari = 0;
                                System.out.println("Notifikasi : " + e);
                            } finally {
                                if (rsstok != null) {
                                    rsstok.close();
                                }
                                if (psstok != null) {
                                    psstok.close();
                                }
                            }
                            if (rsobat.getDouble("jml") > sisacari) {
                                JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                                tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                    rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                    rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            } else {
                if (aktifkanbatch.equals("yes")) {
                    psobat = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                            + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                            + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,"
                            + " golongan_barang.nama as golongan,resep_dokter.jml,resep_dokter.aturan_pakai "
                            + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join resep_dokter "
                            + " on databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "
                            + " and databarang.kode_kategori=kategori_barang.kode and resep_dokter.kode_brng=databarang.kode_brng "
                            + " where resep_dokter.no_resep=? and databarang.status='1' and databarang.kode_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.nama_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and kategori_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and golongan_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1, no_resep);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, no_resep);
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, no_resep);
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        psobat.setString(7, no_resep);
                        psobat.setString(8, "%" + TCari.getText().trim() + "%");
                        psobat.setString(9, no_resep);
                        psobat.setString(10, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            no_batchcari = "";
                            tgl_kadaluarsacari = "";
                            no_fakturcari = "";
                            h_belicari = 0;
                            hargacari = 0;
                            sisacari = 0;
                            psbatch = koneksi.prepareStatement(
                                    "select no_batch, kode_brng, tgl_beli, tgl_kadaluarsa, asal, no_faktur, "
                                    + "h_beli, ralan, kelas1, kelas2, kelas3, utama, vip, vvip, beliluar, "
                                    + "jualbebas, karyawan, jumlahbeli, sisa from data_batch where sisa>0 and kode_brng=? order by tgl_kadaluarsa desc limit 1");
                            try {
                                psbatch.setString(1, rsobat.getString("kode_brng"));
                                rsbatch = psbatch.executeQuery();
                                while (rsbatch.next()) {
                                    no_batchcari = rsbatch.getString("no_batch");
                                    tgl_kadaluarsacari = rsbatch.getString("tgl_kadaluarsa");
                                    no_fakturcari = rsbatch.getString("no_faktur");
                                    h_belicari = rsbatch.getDouble("h_beli");
                                    if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                        hargacari = rsbatch.getDouble("kelas1");
                                    } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                        hargacari = rsbatch.getDouble("kelas2");
                                    } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                        hargacari = rsbatch.getDouble("kelas3");
                                    } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                        hargacari = rsbatch.getDouble("utama");
                                    } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                        hargacari = rsbatch.getDouble("vip");
                                    } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                        hargacari = rsbatch.getDouble("vvip");
                                    } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                        hargacari = rsbatch.getDouble("beliluar");
                                    } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                        hargacari = rsbatch.getDouble("karyawan");
                                    }
                                    sisacari = rsbatch.getDouble("sisa");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : " + e);
                            } finally {
                                if (rsbatch != null) {
                                    rsbatch.close();
                                }
                                if (psbatch != null) {
                                    psbatch.close();
                                }
                            }
                            if (rsobat.getDouble("jml") > sisacari) {
                                JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                                tabMode.addRow(new Object[]{
                                    false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(hargacari, 100),
                                    rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                    h_belicari, rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    no_batchcari, no_fakturcari, tgl_kadaluarsacari
                                });
                            } else {
                                tabMode.addRow(new Object[]{
                                    false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(hargacari, 100),
                                    rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                    h_belicari, rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    no_batchcari, no_fakturcari, tgl_kadaluarsacari
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    psobat = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                            + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                            + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,"
                            + " golongan_barang.nama as golongan,resep_dokter.jml,resep_dokter.aturan_pakai "
                            + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join resep_dokter "
                            + " on databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "
                            + " and databarang.kode_kategori=kategori_barang.kode and resep_dokter.kode_brng=databarang.kode_brng "
                            + " where resep_dokter.no_resep=? and databarang.status='1' and databarang.kode_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.nama_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and kategori_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and golongan_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1, no_resep);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, no_resep);
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, no_resep);
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        psobat.setString(7, no_resep);
                        psobat.setString(8, "%" + TCari.getText().trim() + "%");
                        psobat.setString(9, no_resep);
                        psobat.setString(10, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            sisacari = 0;
                            psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                            try {
                                psstok.setString(1, kdgudang.getText());
                                psstok.setString(2, rsobat.getString("kode_brng"));
                                rsstok = psstok.executeQuery();
                                if (rsstok.next()) {
                                    sisacari = rsstok.getDouble(1);
                                }
                            } catch (Exception e) {
                                sisacari = 0;
                                System.out.println("Notifikasi : " + e);
                            } finally {
                                if (rsstok != null) {
                                    rsstok.close();
                                }
                                if (psstok != null) {
                                    psstok.close();
                                }
                            }
                            if (rsobat.getDouble("jml") > sisacari) {
                                JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                                if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                    tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                    tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                    tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                    tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                    tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                    tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                    tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                    tabMode.addRow(new Object[]{false, sisacari, rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                }
                            } else {
                                if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                    tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                    tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                    tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                    tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                    tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                    tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                    tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                    tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                        rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                        rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                        rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                    });
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            }
            for (i = 0; i < tbObat.getRowCount(); i++) {
                getDataobat(i);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void cariBatch() {
        try {
            ps2 = koneksi.prepareStatement("select * from data_batch where no_batch=? and kode_brng=?");
            try {
                ps2.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
                ps2.setString(2, tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
                rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    tbObat.setValueAt(rs2.getString("no_faktur"), tbObat.getSelectedRow(), 17);
                    tbObat.setValueAt(rs2.getString("tgl_kadaluarsa"), tbObat.getSelectedRow(), 18);
                    if (aktifkanbatch.equals("yes")) {
                        if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                            tbObat.setValueAt(rs2.getDouble("karyawan"), tbObat.getSelectedRow(), 6);
                        } else if (Jeniskelas.getSelectedItem().equals("Rawat Jalan")) {
                            tbObat.setValueAt(rs2.getDouble("ralan"), tbObat.getSelectedRow(), 6);
                        } else if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                            tbObat.setValueAt(rs2.getDouble("kelas1"), tbObat.getSelectedRow(), 6);
                        } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                            tbObat.setValueAt(rs2.getDouble("kelas2"), tbObat.getSelectedRow(), 6);
                        } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                            tbObat.setValueAt(rs2.getDouble("kelas3"), tbObat.getSelectedRow(), 6);
                        } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                            tbObat.setValueAt(rs2.getDouble("utama"), tbObat.getSelectedRow(), 6);
                        } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                            tbObat.setValueAt(rs2.getDouble("vip"), tbObat.getSelectedRow(), 6);
                        } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                            tbObat.setValueAt(rs2.getDouble("vvip"), tbObat.getSelectedRow(), 6);
                        }

                        try {
                            stokbarang = rs2.getDouble("sisa");
                            tbObat.setValueAt(stokbarang, tbObat.getSelectedRow(), 10);
                            y = 0;
                            try {
                                y = Double.parseDouble(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
                            } catch (Exception e) {
                                y = 0;
                            }
                            if (stokbarang < y) {
                                JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                                tbObat.setValueAt("", tbObat.getSelectedRow(), 1);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 10);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }

    }

    public void setPCare(String aktif, String nokunjung) {
        aktifpcare = aktif;
        nokunjungan = nokunjung;
    }

}

/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;


import com.herinoid.rsi.model.RekapPembelianObat;
import com.herinoid.rsi.model.RekapPembelianObat;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelRekapPembelianObat extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<RekapPembelianObat> row;

    public TabelRekapPembelianObat() {
        super();
        column = new Vector<String>();
        column.add("No. Faktur");
        column.add("Tgl Faktur");
        column.add("Suplier");
        column.add("Jumlah");
        column.add("Diskon");
        column.add("PPN");
        column.add("Total");
        column.add("Lokasi");
        column.add("Petugas");
        row = new Vector<RekapPembelianObat>();
    }

    public synchronized void add(Set<RekapPembelianObat> list) {
        for (RekapPembelianObat o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }

    public synchronized RekapPembelianObat set(int index, RekapPembelianObat element) {
        RekapPembelianObat o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized RekapPembelianObat remove(int index) {
        RekapPembelianObat o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }

    public synchronized RekapPembelianObat get(int index) {
        return row.get(index);
    }

    public synchronized boolean add(RekapPembelianObat e) {
        int index = row.size();
        boolean b = row.add(e);
        fireTableRowsInserted(index, index);
        return b;
    }

    @Override
    public String getColumnName(int column) {
        return this.column.get(column);
    }

    @Override
    public int getRowCount() {
        return row.size();
    }

    @Override
    public int getColumnCount() {
        return column.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return row.get(rowIndex).getNoFaktur();
        } else if (columnIndex == 1) {
            return row.get(rowIndex).getTanggalBeli();
        } else if (columnIndex == 2) {
            return row.get(rowIndex).getNamaSuplier();
        }else if (columnIndex == 3) {            
            return row.get(rowIndex).getJumlah();
        } else if (columnIndex == 4) {            
            return row.get(rowIndex).getDiskon();
        } else if (columnIndex == 5) {
            return row.get(rowIndex).getPpn();
        }else if (columnIndex == 6) {
            return row.get(rowIndex).getTotal();
        }else if (columnIndex == 7) {
            return row.get(rowIndex).getLokasi();
        }else if (columnIndex == 8) {
            return row.get(rowIndex).getNamaPetugas();
        }
        return null;
    }

}

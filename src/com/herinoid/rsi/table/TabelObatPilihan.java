/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;


import com.herinoid.rsi.model.DetailPemberianObat;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelObatPilihan extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<DetailPemberianObat> row;

    public TabelObatPilihan() {
        super();
        column = new Vector<String>();
        column.add("Kode Obat");
        column.add("Nama Obat");
        column.add("Satuan");
        column.add("Harga Beli");
        column.add("Harga Jual");
        column.add("Tuslah");
        column.add("Embalase");
        column.add("No.  Batch"); 
        column.add("No.  Faktur");
        column.add("Aturan Pakai");
        row = new Vector<DetailPemberianObat>();
    }

    public synchronized void add(Set<DetailPemberianObat> list) {
        for (DetailPemberianObat o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }

    public synchronized DetailPemberianObat set(int index, DetailPemberianObat element) {
        DetailPemberianObat o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized DetailPemberianObat remove(int index) {
        DetailPemberianObat o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }

    public synchronized DetailPemberianObat get(int index) {
        return row.get(index);
    }

    public synchronized boolean add(DetailPemberianObat e) {
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
            return row.get(rowIndex).getKodeBrng();
        } else if (columnIndex == 1) {
            return row.get(rowIndex).getNamaObat();
        } else if (columnIndex == 2) {
            return row.get(rowIndex).getSatuan();
        } else if (columnIndex == 3) {
            return row.get(rowIndex).gethBeli();
        }else if (columnIndex == 4) {
            return row.get(rowIndex).getBiayaObat();
        }else if (columnIndex == 5) {
            return row.get(rowIndex).getTuslah();
        }else if (columnIndex == 6) {
            return row.get(rowIndex).getEmbalase();
        }else if (columnIndex == 7) {
            return row.get(rowIndex).getNoBatch();
        }else if (columnIndex == 8) {
            return row.get(rowIndex).getNoFaktur();
        } else if (columnIndex == 9) {
            return row.get(rowIndex).getAturanPakai();
        } 
        return null;
    }

}

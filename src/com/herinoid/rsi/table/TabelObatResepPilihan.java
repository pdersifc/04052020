/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;


import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.ObatResep;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelObatResepPilihan extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<ObatResep> row;

    public TabelObatResepPilihan() {
        super();
        column = new Vector<String>();
        column.add("Racikan");
        column.add("Nama Obat");         
        column.add("Jumlah");
        column.add("Satuan");
        column.add("Jenis Obat");
        column.add("Kategori");
        column.add("Aturan Pakai");
        row = new Vector<ObatResep>();
    }

    public synchronized void add(List<ObatResep> list) {
        for (ObatResep o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }
    
    public synchronized void addRow(ObatResep o) {
        row.add(o);
        fireTableDataChanged();
    }

    public synchronized ObatResep set(int index, ObatResep element) {
        ObatResep o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized ObatResep remove(int index) {
        ObatResep o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }
    
    public synchronized List<ObatResep> getAll() {
        return row;
    }

    public synchronized ObatResep get(int index) {
        return row.get(index);
    }

    public synchronized boolean add(ObatResep e) {
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
            return row.get(rowIndex).getRacikan();
        } else if (columnIndex == 1) {
            return row.get(rowIndex).getNamaObat();
        } else if (columnIndex == 2) {
            return row.get(rowIndex).getJumlah();
        } else if (columnIndex == 3) {
            return row.get(rowIndex).getSatuan();
        }else if (columnIndex == 4) {
            return row.get(rowIndex).getJenisObat();
        }else if (columnIndex == 5) {
            return row.get(rowIndex).getKategori();
        }else if (columnIndex == 6) {
            return row.get(rowIndex).getAturanPakai();
        } 
        return null;
    }

}

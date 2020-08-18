/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;


import com.herinoid.rsi.model.Obat;
import com.herinoid.rsi.model.Obat;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelCariObatResep extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<Obat> row;

    public TabelCariObatResep() {
        super();
        column = new Vector<String>();
        column.add("Kode Obat");
        column.add("Nama Obat");
        column.add("Satuan");
        column.add("Jenis Obat");
        column.add("Kategori");
        row = new Vector<Obat>();
    }

    public synchronized void add(List<Obat> list) {
        for (Obat o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }

    public synchronized Obat set(int index, Obat element) {
        Obat o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized Obat remove(int index) {
        Obat o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }

    public synchronized Obat get(int index) {
        return row.get(index);
    }
    
    public synchronized List<Obat> getAll() {
        return row;
    }

    public synchronized boolean add(Obat e) {
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
            return row.get(rowIndex).getKodeObat();
        } else if (columnIndex == 1) {
            return row.get(rowIndex).getNamaObat();
        } else if (columnIndex == 2) {
            return row.get(rowIndex).getSatuan();
        } else if (columnIndex == 3) {
            return row.get(rowIndex).getJenisObat();
        }else if (columnIndex == 4) {
            return row.get(rowIndex).getKategori();
        }
        return null;
    }

}

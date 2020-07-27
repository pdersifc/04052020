/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;


import com.herinoid.rsi.model.DokterPoli;
import com.herinoid.rsi.model.DokterPoli;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelRuangPoli extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<DokterPoli> row;

    public TabelRuangPoli() {
        super();
        column = new Vector<String>();
        column.add("Kode Poliklinik");
        column.add("Nama Poliklinik");
        column.add("Kode Dokter");
        column.add("Nama Dokter");
        column.add("Ruang");
        row = new Vector<DokterPoli>();
    }

    public synchronized void add(Set<DokterPoli> list) {
        for (DokterPoli o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }

    public synchronized DokterPoli set(int index, DokterPoli element) {
        DokterPoli o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized DokterPoli remove(int index) {
        DokterPoli o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }

    public synchronized DokterPoli get(int index) {
        return row.get(index);
    }

    public synchronized boolean add(DokterPoli e) {
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
            return row.get(rowIndex).getKodePoli();
        } else if (columnIndex == 1) {
            return row.get(rowIndex).getNamaPoli();
        } else if (columnIndex == 2) {
            return row.get(rowIndex).getKodeDokter();
        } else if (columnIndex == 3) {
            return row.get(rowIndex).getNamaDokter();
        }else if (columnIndex == 4) {
            return row.get(rowIndex).getRuang();
        }
        return null;
    }

}

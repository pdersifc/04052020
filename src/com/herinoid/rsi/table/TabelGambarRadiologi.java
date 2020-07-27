/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;



import com.herinoid.rsi.model.GambarRadiologi;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelGambarRadiologi extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<GambarRadiologi> row;

    public TabelGambarRadiologi() {
        super();
        column = new Vector<String>();
        column.add("Nomer Rawat");
        column.add("Tanggal Periksa");
        column.add("Jam Periksa");
        column.add("Nama File Gambar");
        row = new Vector<GambarRadiologi>();
    }

    public synchronized void add(Set<GambarRadiologi> list) {
        for (GambarRadiologi o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }

    public synchronized GambarRadiologi set(int index, GambarRadiologi element) {
        GambarRadiologi o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized GambarRadiologi remove(int index) {
        GambarRadiologi o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }

    public synchronized GambarRadiologi get(int index) {
        return row.get(index);
    }

    public synchronized boolean add(GambarRadiologi e) {
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
            return row.get(rowIndex).getNoRawat();
        } else if (columnIndex == 1) {
            return row.get(rowIndex).getTanggal();
        } else if (columnIndex == 2) {
            return row.get(rowIndex).getJam();
        } else if (columnIndex == 3) {
            return row.get(rowIndex).getLokasiGambar();
        }
        return null;
    }

}

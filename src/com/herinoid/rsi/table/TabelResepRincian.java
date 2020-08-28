/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;

import com.herinoid.rsi.model.RincianResepVerifikasi;
import com.herinoid.rsi.model.RincianResepVerifikasi;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelResepRincian extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<RincianResepVerifikasi> row;

    public TabelResepRincian() {
        super();
        column = new Vector<String>();
        column.add("Racikan");
        column.add("Nama Obat");
        column.add("( jml * harga) + embalase + tuslah = total");
        column.add("Aturan Pakai");
        row = new Vector<RincianResepVerifikasi>();
    }

    public synchronized void add(List<RincianResepVerifikasi> list) {
        for (RincianResepVerifikasi o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }

    public synchronized RincianResepVerifikasi set(int index, RincianResepVerifikasi element) {
        RincianResepVerifikasi o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized RincianResepVerifikasi remove(int index) {
        RincianResepVerifikasi o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }

    public synchronized RincianResepVerifikasi get(int index) {
        return row.get(index);
    }
    
    public synchronized List<RincianResepVerifikasi> getAll() {
        return row;
    }

    public synchronized boolean add(RincianResepVerifikasi e) {
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
            return row.get(rowIndex).getRincian();
        } else if (columnIndex == 3) {
            return row.get(rowIndex).getAturanPakai();
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex > -1) {
            RincianResepVerifikasi i = row.get(rowIndex);
            switch (columnIndex) {
                case 3:
                    i.setAturanPakai(aValue.toString());
                    break;
                default:
                    break;
            }
        }
    }

}

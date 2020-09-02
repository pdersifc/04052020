/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;


import com.herinoid.rsi.model.DataEResep;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelDataResep extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<DataEResep> row;

    public TabelDataResep() {
        super();
        column = new Vector<String>();
        column.add("No. Resep");
        column.add("Tgl Resep");         
        column.add("Poli/Unit");
        column.add("Status");
        column.add("Pasien");
        column.add("Dokter Peresep");
        column.add("Jaminan");
        column.add("Waktu Validasi");
        column.add("Dispansing ");
        column.add("Sampai Pasien");
        column.add("Status Bayar");
        row = new Vector<DataEResep>();
    }

    public synchronized void add(List<DataEResep> list) {
        for (DataEResep o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }
    
    public synchronized void addRow(DataEResep o) {
        row.add(o);
        fireTableDataChanged();
    }

    public synchronized DataEResep set(int index, DataEResep element) {
        DataEResep o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized DataEResep remove(int index) {
        DataEResep o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }
    
    public synchronized List<DataEResep> getAll() {
        return row;
    }

    public synchronized DataEResep get(int index) {
        return row.get(index);
    }

    public synchronized boolean add(DataEResep e) {
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
            return row.get(rowIndex).getNoResep();
        } else if (columnIndex == 1) {
            return row.get(rowIndex).getTglResep();
        } else if (columnIndex == 2) {
            return row.get(rowIndex).getPoli();
        } else if (columnIndex == 3) {
            return row.get(rowIndex).getStatus();
        }else if (columnIndex == 4) {
            return row.get(rowIndex).getPasien();
        }else if (columnIndex == 5) {
            return row.get(rowIndex).getDokter();
        }else if (columnIndex == 6) {
            return row.get(rowIndex).getJaminan();
        } else if (columnIndex == 7) {
            return row.get(rowIndex).getValidasi();
        } else if (columnIndex == 8) {
            return row.get(rowIndex).getPacking();
        } else if (columnIndex == 9) {
            return row.get(rowIndex).getDiterima();
        } else if (columnIndex == 10) {
            return row.get(rowIndex).getStatusBayar();
        } 
        return null;
    }

}

/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;


import com.herinoid.rsi.model.BillingSimulator;
import com.herinoid.rsi.model.DokterPoli;
import com.herinoid.rsi.model.DokterPoli;
import com.herinoid.rsi.util.Utils;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelBillingSimulator extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
//    private Vector<DataPasien> row;
    private List<BillingSimulator> row;

    public TabelBillingSimulator() {
        super();
        column = new Vector<String>();
        column.add("No.");
        column.add("Uraian");
        column.add("Jumlah");
        column.add("Kelas Sekarang");
        column.add("Kelas Pembanding");
        column.add("IUR Pasien");
        row = new Vector<BillingSimulator>();
    }

    public synchronized void add(List<BillingSimulator> list) {
        for (BillingSimulator o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }

    public synchronized BillingSimulator set(int index, BillingSimulator element) {
        BillingSimulator o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized BillingSimulator remove(int index) {
        BillingSimulator o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }

    public synchronized BillingSimulator get(int index) {
        return row.get(index);
    }

    public synchronized boolean add(BillingSimulator e) {
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
            return row.get(rowIndex).getNomer();
        } else if (columnIndex == 1) {
            return " "+row.get(rowIndex).getUraian();
        } else if (columnIndex == 2) {
            return Utils.format(row.get(rowIndex).getJumlah(), 0) ;
        } else if (columnIndex == 3) {
            return " "+Utils.format(row.get(rowIndex).getTagihan(), 0) ;
        }else if (columnIndex == 4) {
            return " "+Utils.format(row.get(rowIndex).getPembanding(), 0) ;
        }else if (columnIndex == 5) {
            return " "+Utils.format(row.get(rowIndex).getIurPasien(), 0) ;
        }
        return null;
    }

}

/*
 * Aplikasi ini di buat di Ponpes Imam bukhari Solo
 * Oleh Herinoid deSanto
 */
package com.herinoid.rsi.table;

import com.herinoid.rsi.model.ResepTemplate;
import java.util.EnumSet;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Herinoid deSanto
 */
public class TabelListTemplateResep extends AbstractTableModel {

    /*
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    private Vector<String> column;
    private List<ResepTemplate> row;

    public TabelListTemplateResep() {
        super();
        column = new Vector<String>();
        column.add("Racikan");
        column.add("Nama Dokter");
        column.add("Nama Template");
        column.add("Action");
        row = new Vector<ResepTemplate>();
    }

    public synchronized void add(List<ResepTemplate> list) {
        for (ResepTemplate o : list) {
            row.add(o);
        }
        fireTableDataChanged();
    }

    public synchronized void addRow(ResepTemplate o) {
        row.add(o);
        fireTableDataChanged();
    }

    public synchronized ResepTemplate set(int index, ResepTemplate element) {
        ResepTemplate o = row.set(index, element);
        fireTableRowsUpdated(index, index);
        return o;
    }

    public synchronized void removeAllElements() {
        row.clear();
        fireTableDataChanged();
    }

    public synchronized ResepTemplate remove(int index) {
        ResepTemplate o = row.remove(index);
        fireTableRowsDeleted(index, index);
        return o;
    }

    public synchronized List<ResepTemplate> getAll() {
        return row;
    }

    public synchronized ResepTemplate get(int index) {
        return row.get(index);
    }

    public synchronized boolean add(ResepTemplate e) {
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
            return row.get(rowIndex).isRacikan() ? "Racikan" : "";
        } else if (columnIndex == 1) {
            return row.get(rowIndex).getNamaDokter();
        } else if (columnIndex == 2) {
            return row.get(rowIndex).getNamaTemplate();
        } else if (columnIndex == 3) {
            return null;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }

}



    

    


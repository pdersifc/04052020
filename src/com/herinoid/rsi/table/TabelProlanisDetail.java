package com.herinoid.rsi.table;

import com.herinoid.rsi.model.ProlanisDetail;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TabelProlanisDetail extends AbstractTableModel {

    private List<ProlanisDetail> list = new ArrayList<ProlanisDetail>();

    public void setList(List<ProlanisDetail> list) {
        this.list = list;
        fireTableDataChanged();
    }

    public int getRowCount() {
        return list.size();
    }

    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Kode Barang";
            case 1:
                return "Nama Barang";
            case 2:
                return "Satuan";
            case 3:
                return "Jumlah";
            case 4:
                return "Harga (Rp)";
            case 5:
                return "Diskon (Rp)";
            case 6:
                return "Sub Total (Rp)";
            default:
                return null;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getKodeObat();
            case 1:
                return list.get(rowIndex).getNamaObat();
            case 2:
                return list.get(rowIndex).getSatuan();
            case 3:
                return list.get(rowIndex).getJumlah();
            case 4:
                return list.get(rowIndex).getHarga();
            case 5:
                return list.get(rowIndex).getDiskon();
            case 6:
                return list.get(rowIndex).getTotal();
            default:
                return null;
        }
    }
}

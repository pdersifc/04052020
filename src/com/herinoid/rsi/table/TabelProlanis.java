package com.herinoid.rsi.table;

import com.herinoid.rsi.model.DataProlanis;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TabelProlanis extends AbstractTableModel {

    private List<DataProlanis> list;

    public TabelProlanis(List<DataProlanis> list) {
        this.list = list;
    }

    public List<DataProlanis> getList() {
        return list;
    }

    public int getRowCount() {
        return list.size();
    }

    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "No. Nota";
            case 1:
                return "No. Rawat";
            case 2:
                return "Nama Pasien";
            case 3:
                return "Tanggal Piutang";
            case 4:
                return "Jatuh Tempo";
            case 5:
                return "Lokasi";
            default:
                return null;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getNoNota();
            case 1:
                return list.get(rowIndex).getNoRekamMedis();
            case 2:
                return list.get(rowIndex).getNamaPasien();
            case 3:
                return list.get(rowIndex).getTglPiutang();
            case 4:
                return list.get(rowIndex).getJatuhTempo();
            case 5:
                return list.get(rowIndex).getLokasi();
            default:
                return null;
        }
    }
}

package app.model;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabelPembelian extends AbstractTableModel {
    List<Pembelian> listPembelian;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public ModelTabelPembelian(List<Pembelian> listPembelian) {
        this.listPembelian = listPembelian;
    }

    @Override
    public int getRowCount() {
        return listPembelian.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Kode Pembelian";
            case 1: return "Tanggal";
            case 2: return "Nama Barang";
            case 3: return "Jumlah";
            case 4: return "Total Beli";
            case 5: return "User";
            default: return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return listPembelian.get(rowIndex).getKd_pembelian();
            case 1: return dateFormat.format(listPembelian.get(rowIndex).getTgl_pembelian());
            case 2: return listPembelian.get(rowIndex).getNama_barang();
            case 3: return listPembelian.get(rowIndex).getJmlh_beli();
            case 4: return listPembelian.get(rowIndex).getTotal_beli();
            case 5: return listPembelian.get(rowIndex).getNama_user();
            default: return null;
        }
    }
}
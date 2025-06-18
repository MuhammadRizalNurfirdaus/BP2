package app.model;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabelPenjualan extends AbstractTableModel {
    List<Penjualan> listPenjualan;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public ModelTabelPenjualan(List<Penjualan> listPenjualan) {
        this.listPenjualan = listPenjualan;
    }

    @Override
    public int getRowCount() {
        return listPenjualan.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "No. Nota";
            case 1: return "Tanggal";
            case 2: return "Nama Barang";
            case 3: return "Jumlah";
            case 4: return "Total Jual";
            case 5: return "Kasir";
            default: return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return listPenjualan.get(rowIndex).getNota();
            case 1: return dateFormat.format(listPenjualan.get(rowIndex).getTgl_penjualan());
            case 2: return listPenjualan.get(rowIndex).getNama_barang();
            case 3: return listPenjualan.get(rowIndex).getJmlh_barang_jual();
            case 4: return listPenjualan.get(rowIndex).getTotal_jual();
            case 5: return listPenjualan.get(rowIndex).getNama_user();
            default: return null;
        }
    }
}
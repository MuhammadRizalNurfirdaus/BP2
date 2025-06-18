package app.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabelBarang extends AbstractTableModel {
    List<Barang> listBarang;

    public ModelTabelBarang(List<Barang> listBarang) {
        this.listBarang = listBarang;
    }

    @Override
    public int getRowCount() {
        return listBarang.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Kode Barang";
            case 1: return "Nama Barang";
            case 2: return "Harga Jual";
            case 3: return "Harga Beli";
            case 4: return "Stok";
            default: return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return listBarang.get(rowIndex).getKd_barang();
            case 1: return listBarang.get(rowIndex).getNama_barang();
            case 2: return listBarang.get(rowIndex).getHarga_jual();
            case 3: return listBarang.get(rowIndex).getHarga_beli();
            case 4: return listBarang.get(rowIndex).getStok();
            default: return null;
        }
    }
}
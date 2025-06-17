package app.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabelSupplier extends AbstractTableModel {
    List<Supplier> listSupplier;

    public ModelTabelSupplier(List<Supplier> listSupplier) {
        this.listSupplier = listSupplier;
    }

    @Override
    public int getRowCount() {
        return listSupplier.size();
    }

    @Override
    public int getColumnCount() {
        return 4; // Kode, Nama, No. HP, Alamat
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Kode Supplier";
            case 1: return "Nama Supplier";
            case 2: return "No. HP";
            case 3: return "Alamat";
            default: return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return listSupplier.get(rowIndex).getKd_supplier();
            case 1: return listSupplier.get(rowIndex).getNama_supplier();
            case 2: return listSupplier.get(rowIndex).getNope_supplier();
            case 3: return listSupplier.get(rowIndex).getAlamat();
            default: return null;
        }
    }
}
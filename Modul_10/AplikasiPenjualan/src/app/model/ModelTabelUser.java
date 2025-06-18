package app.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTabelUser extends AbstractTableModel {
    List<User> listUser;

    public ModelTabelUser(List<User> listUser) {
        this.listUser = listUser;
    }

    @Override
    public int getRowCount() {
        return listUser.size();
    }

    @Override
    public int getColumnCount() {
        // ID User, Nama, Jenis Kelamin, No HP, Username, Level
        return 6;
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "ID User";
            case 1: return "Nama User";
            case 2: return "Jenis Kelamin";
            case 3: return "No. HP";
            case 4: return "Username";
            case 5: return "Level";
            default: return null;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return listUser.get(rowIndex).getId_user();
            case 1: return listUser.get(rowIndex).getNama_user();
            case 2: return listUser.get(rowIndex).getJk().equals("L") ? "Laki-laki" : "Perempuan";
            case 3: return listUser.get(rowIndex).getNope();
            case 4: return listUser.get(rowIndex).getUsername();
            case 5: return listUser.get(rowIndex).getLevel(); // Menampilkan nama level
            default: return null;
        }
    }
}
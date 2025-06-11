/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package anggota.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author Muhammad Rizal Nur F
 */
public class DataModelAnggota extends AbstractTableModel {

    List<DataAnggota> lb;

    public DataModelAnggota(List<DataAnggota> lb) {
        this.lb = lb;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return lb.size();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Nama";
            case 2:
                return "Alamat";
            case 3:
                return "NoPe";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return lb.get(row).getId();
            case 1:
                return lb.get(row).getNama();
            case 2:
                return lb.get(row).getAlamat();
            case 3:
                return lb.get(row).getNomer();
            default:
                return null;
        }
    }
}

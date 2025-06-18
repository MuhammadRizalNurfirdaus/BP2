package app.controller;

import app.dao.DaoBarang;
import app.dao.ImplementBarang;
import app.model.Barang;
import app.model.ModelTabelBarang;
import app.view.FormBarang;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerBarang {
    FormBarang frame;
    ImplementBarang implBarang;
    List<Barang> listBarang;

    public ControllerBarang(FormBarang frame) {
        this.frame = frame;
        implBarang = new DaoBarang();
        listBarang = implBarang.getAll();
    }

    public void reset() {
        frame.getTxtKdBarang().setText("");
        frame.getTxtNamaBarang().setText("");
        frame.getTxtHargaJual().setText("");
        frame.getTxtHargaBeli().setText("");
        frame.getTxtStok().setText("");
        frame.getTxtKdBarang().setEditable(true);
    }

    public void isiTabel() {
        listBarang = implBarang.getAll();
        ModelTabelBarang mtb = new ModelTabelBarang(listBarang);
        frame.getTabelBarang().setModel(mtb);
    }
    
    public void isiField(int row) {
        frame.getTxtKdBarang().setText(listBarang.get(row).getKd_barang());
        frame.getTxtNamaBarang().setText(listBarang.get(row).getNama_barang());
        frame.getTxtHargaJual().setText(String.valueOf(listBarang.get(row).getHarga_jual()));
        frame.getTxtHargaBeli().setText(String.valueOf(listBarang.get(row).getHarga_beli()));
        frame.getTxtStok().setText(String.valueOf(listBarang.get(row).getStok()));
        // Kode barang tidak boleh diubah setelah dipilih
        frame.getTxtKdBarang().setEditable(false);
    }
    
    public void insert() {
        if (frame.getTxtKdBarang().getText().trim().isEmpty() || 
            frame.getTxtNamaBarang().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Kode dan Nama Barang tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Barang b = new Barang();
        b.setKd_barang(frame.getTxtKdBarang().getText());
        b.setNama_barang(frame.getTxtNamaBarang().getText());
        b.setHarga_jual(Integer.parseInt(frame.getTxtHargaJual().getText()));
        b.setHarga_beli(Integer.parseInt(frame.getTxtHargaBeli().getText()));
        b.setStok(Integer.parseInt(frame.getTxtStok().getText()));
        implBarang.insert(b);
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
    }
    
    public void update() {
        if (frame.getTxtKdBarang().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Pilih data yang akan diupdate dari tabel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Barang b = new Barang();
        b.setKd_barang(frame.getTxtKdBarang().getText());
        b.setNama_barang(frame.getTxtNamaBarang().getText());
        b.setHarga_jual(Integer.parseInt(frame.getTxtHargaJual().getText()));
        b.setHarga_beli(Integer.parseInt(frame.getTxtHargaBeli().getText()));
        b.setStok(Integer.parseInt(frame.getTxtStok().getText()));
        implBarang.update(b);
        JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui");
    }
    
    public void delete() {
        if (frame.getTxtKdBarang().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Pilih data yang akan dihapus dari tabel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String kd_barang = frame.getTxtKdBarang().getText();
        implBarang.delete(kd_barang);
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
    }
    
    public void cari() {
        String nama = frame.getTxtCari().getText();
        listBarang = implBarang.getCari(nama);
        ModelTabelBarang mtb = new ModelTabelBarang(listBarang);
        frame.getTabelBarang().setModel(mtb);
    }
}
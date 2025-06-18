package app.controller;

import app.dao.DaoBarang;
import app.dao.DaoPembelian;
import app.dao.ImplementBarang;
import app.dao.ImplementPembelian;
import app.model.Barang;
import app.model.Pembelian;
import app.model.ModelTabelPembelian;
import app.model.User;
import app.view.FormPembelian;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerPembelian {
    FormPembelian frame;
    User userLogin;
    ImplementPembelian implPembelian;
    ImplementBarang implBarang;
    List<Barang> listBarang;
    List<Pembelian> listPembelian;

    public ControllerPembelian(FormPembelian frame, User user) {
        this.frame = frame;
        this.userLogin = user;
        implPembelian = new DaoPembelian();
        implBarang = new DaoBarang();
    }
    
    public void reset() {
        frame.getTxtKdPembelian().setText("PB" + System.currentTimeMillis());
        frame.getLblUser().setText(userLogin.getNama_user());
        frame.getCmbBarang().setSelectedIndex(0);
        // Panggil tampilkanHarga() saat reset agar harga barang pertama muncul
        tampilkanHarga(); 
        frame.getTxtJumlah().setText("");
        // Panggil hitungTotal() agar total kembali ke 0
        hitungTotal(); 
    }
    
    public void isiComboBarang() {
        listBarang = implBarang.getAll();
        frame.getCmbBarang().removeAllItems();
        if (listBarang != null) {
            for (Barang b : listBarang) {
                frame.getCmbBarang().addItem(b.getNama_barang());
            }
        }
    }
    
    public void isiTabel() {
        try {
            listPembelian = implPembelian.getAll();
            if (listPembelian == null) {
                listPembelian = Collections.emptyList();
            }
            ModelTabelPembelian mtp = new ModelTabelPembelian(listPembelian);
            frame.getTabelRiwayat().setModel(mtp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Gagal memuat riwayat pembelian: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void tampilkanHarga() {
        int index = frame.getCmbBarang().getSelectedIndex();
        // Pastikan ada item yang terpilih
        if (index >= 0 && index < listBarang.size()) {
            Barang b = listBarang.get(index);
            frame.getTxtHargaBeli().setText(String.valueOf(b.getHarga_beli()));
            // Setiap kali harga berubah, panggil hitungTotal() lagi
            hitungTotal();
        }
    }
    
    public void hitungTotal() {
        try {
            // Periksa jika field jumlah tidak kosong
            if (!frame.getTxtJumlah().getText().trim().isEmpty()) {
                int harga = Integer.parseInt(frame.getTxtHargaBeli().getText());
                int jumlah = Integer.parseInt(frame.getTxtJumlah().getText());
                long total = (long) harga * jumlah;
                frame.getTxtTotal().setText(String.valueOf(total));
            } else {
                // Jika field jumlah kosong, set total menjadi 0
                frame.getTxtTotal().setText("0");
            }
        } catch (NumberFormatException e) {
            // Jika input jumlah bukan angka, set total menjadi 0
            frame.getTxtTotal().setText("0");
        }
    }
    
    public void prosesPembelian() {
        // Validasi
        if (frame.getTxtJumlah().getText().trim().isEmpty() || Integer.parseInt(frame.getTxtJumlah().getText()) <= 0) {
            JOptionPane.showMessageDialog(frame, "Jumlah beli harus diisi dan lebih dari 0!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Pembelian p = new Pembelian();
        p.setKd_pembelian(frame.getTxtKdPembelian().getText());
        p.setTgl_pembelian(new Date());
        
        int selectedIndex = frame.getCmbBarang().getSelectedIndex();
        p.setKd_barang(listBarang.get(selectedIndex).getKd_barang());
        p.setId_user(userLogin.getId_user());
        
        p.setJmlh_beli(Integer.parseInt(frame.getTxtJumlah().getText()));
        p.setTotal_beli(Long.parseLong(frame.getTxtTotal().getText()));
        
        implPembelian.insert(p);
        JOptionPane.showMessageDialog(null, "Transaksi pembelian berhasil diproses!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        
        reset();
        isiTabel();
    }
}
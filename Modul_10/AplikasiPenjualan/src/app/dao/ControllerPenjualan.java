package app.controller;

import app.dao.DaoBarang;
import app.dao.DaoPenjualan;
import app.dao.ImplementBarang;
import app.dao.ImplementPenjualan;
import app.model.Barang;
import app.model.Penjualan;
import app.model.ModelTabelPenjualan;
import app.model.User;
import app.view.FormPenjualan;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerPenjualan {
    FormPenjualan frame;
    User userLogin;
    ImplementPenjualan implPenjualan;
    ImplementBarang implBarang;
    List<Barang> listBarang;
    List<Penjualan> listPenjualan;

    public ControllerPenjualan(FormPenjualan frame, User user) {
        this.frame = frame;
        this.userLogin = user;
        implPenjualan = new DaoPenjualan();
        implBarang = new DaoBarang();
    }
    
    public void reset() {
        frame.getTxtNota().setText("NT" + System.currentTimeMillis());
        frame.getLblKasir().setText(userLogin.getNama_user());
        if (listBarang != null && !listBarang.isEmpty()) {
            frame.getCmbBarang().setSelectedIndex(0);
        }
        tampilkanDetailBarang();
        frame.getTxtJumlah().setText("");
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
            listPenjualan = implPenjualan.getAll();
            if (listPenjualan == null) {
                listPenjualan = Collections.emptyList();
            }
            ModelTabelPenjualan mtp = new ModelTabelPenjualan(listPenjualan);
            frame.getTabelRiwayat().setModel(mtp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Gagal memuat riwayat penjualan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void tampilkanDetailBarang() {
        int index = frame.getCmbBarang().getSelectedIndex();
        if (index >= 0 && index < listBarang.size()) {
            Barang b = listBarang.get(index);
            frame.getTxtHargaJual().setText(String.valueOf(b.getHarga_jual()));
            frame.getTxtStok().setText(String.valueOf(b.getStok()));
            hitungTotal();
        }
    }
    
    public void hitungTotal() {
        try {
            if (!frame.getTxtJumlah().getText().trim().isEmpty()) {
                int harga = Integer.parseInt(frame.getTxtHargaJual().getText());
                int jumlah = Integer.parseInt(frame.getTxtJumlah().getText());
                long total = (long) harga * jumlah;
                frame.getTxtTotal().setText(String.valueOf(total));
            } else {
                frame.getTxtTotal().setText("0");
            }
        } catch (NumberFormatException e) {
            frame.getTxtTotal().setText("0");
        }
    }
    
    public void prosesPenjualan() {
        int jumlahBeli;
        try {
            jumlahBeli = Integer.parseInt(frame.getTxtJumlah().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (jumlahBeli <= 0) {
            JOptionPane.showMessageDialog(frame, "Jumlah jual harus lebih dari 0!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int stokTersedia = Integer.parseInt(frame.getTxtStok().getText());
        if (jumlahBeli > stokTersedia) {
            JOptionPane.showMessageDialog(frame, "Stok tidak mencukupi! Stok saat ini: " + stokTersedia, "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Penjualan p = new Penjualan();
        p.setNota(frame.getTxtNota().getText());
        p.setTgl_penjualan(new Date());
        
        int selectedIndex = frame.getCmbBarang().getSelectedIndex();
        p.setKd_barang(listBarang.get(selectedIndex).getKd_barang());
        p.setId_user(userLogin.getId_user());
        
        p.setJmlh_barang_jual(jumlahBeli);
        p.setTotal_jual(Long.parseLong(frame.getTxtTotal().getText()));
        
        implPenjualan.insert(p);
        JOptionPane.showMessageDialog(null, "Transaksi penjualan berhasil diproses!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        
        // Refresh combo, tabel, dan reset form
        isiComboBarang();
        reset();
        isiTabel();
    }
}
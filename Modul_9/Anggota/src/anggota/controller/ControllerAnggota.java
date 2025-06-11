package anggota.controller;

import anggota.dao.DaoAnggota;
import anggota.dao.ImplementAnggota;
import anggota.model.DataAnggota;
import anggota.model.DataModelAnggota;
import anggota.view.FAnggota;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerAnggota {
    FAnggota frame;
    ImplementAnggota implAnggota;
    List<DataAnggota> lb;

    public ControllerAnggota(FAnggota frame) {
        this.frame = frame;
        implAnggota = new DaoAnggota();
        lb = implAnggota.getAll();
    }

    // Mengosongkan field
    public void reset() {
        frame.getTxtID().setText("");
        frame.getTxtNama().setText("");
        frame.getTxtAlamat().setText("");
        frame.getTxtNoTelp().setText("");
    }

    // Menampilkan data ke dalam tabel
    public void isiTable() {
        lb = implAnggota.getAll();
        DataModelAnggota tmb = new DataModelAnggota(lb);
        frame.getTabelData().setModel(tmb);
    }

    // Mengisi field saat data di tabel di-klik
    public void isiField(int row) {
        frame.getTxtID().setText(String.valueOf(lb.get(row).getId()));
        frame.getTxtNama().setText(lb.get(row).getNama());
        frame.getTxtAlamat().setText(lb.get(row).getAlamat());
        frame.getTxtNoTelp().setText(lb.get(row).getNomer());
    }

    // ----- PERUBAHAN UTAMA ADA DI SINI -----
    
    // Proses insert data
    public void insert() {
        DataAnggota b = new DataAnggota();
        b.setNama(frame.getTxtNama().getText());
        b.setAlamat(frame.getTxtAlamat().getText());
        b.setNomer(frame.getTxtNoTelp().getText());
        implAnggota.insert(b);
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        // Setelah insert, panggil isiTable() dan reset() dari sini
        isiTable();
        reset();
    }
    
    // Proses update data
    public void update() {
        DataAnggota b = new DataAnggota();
        b.setId(Integer.parseInt(frame.getTxtID().getText()));
        b.setNama(frame.getTxtNama().getText());
        b.setAlamat(frame.getTxtAlamat().getText());
        b.setNomer(frame.getTxtNoTelp().getText());
        implAnggota.update(b);
        JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui");
        // Setelah update, panggil isiTable() dan reset() dari sini
        isiTable();
        reset();
    }
    
    // Proses delete data
    public void delete() {
        if (!frame.getTxtID().getText().trim().isEmpty()) {
            int id = Integer.parseInt(frame.getTxtID().getText());
            implAnggota.delete(id);
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            // Setelah delete, panggil isiTable() dan reset() dari sini
            isiTable();
            reset();
        } else {
            JOptionPane.showMessageDialog(frame, "Pilih data yang akan dihapus");
        }
    }
    
    // Proses cari data
    public void carinama() {
        String nama = frame.getTxtCariNama().getText();
        lb = implAnggota.getCariNama(nama);
        DataModelAnggota tmb = new DataModelAnggota(lb);
        frame.getTabelData().setModel(tmb);
    }
}
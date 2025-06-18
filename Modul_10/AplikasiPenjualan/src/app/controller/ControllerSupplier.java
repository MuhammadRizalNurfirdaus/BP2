package app.controller;

import app.dao.DaoSupplier;
import app.dao.ImplementSupplier;
import app.koneksi.Koneksi;
import app.model.Supplier;
import app.model.ModelTabelSupplier;
import app.view.FormSupplier;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class ControllerSupplier {
    FormSupplier frame;
    ImplementSupplier implSupplier;
    List<Supplier> listSupplier;

    public ControllerSupplier(FormSupplier frame) {
        this.frame = frame;
        implSupplier = new DaoSupplier();
    }
    
    public void reset() {
        frame.getTxtKdSupplier().setText("");
        frame.getTxtNamaSupplier().setText("");
        frame.getTxtNope().setText("");
        frame.getTxtAlamat().setText("");
        frame.getTxtCari().setText("");
        frame.getTxtKdSupplier().setEditable(true);
        isiTabel();
    }
    
    public void isiTabel() {
        try {
            listSupplier = implSupplier.getAll();
            if (listSupplier == null) {
                // Jika DAO mengembalikan null, inisialisasi dengan list kosong
                listSupplier = Collections.emptyList();
            }
            ModelTabelSupplier mts = new ModelTabelSupplier(listSupplier);
            frame.getTabelSupplier().setModel(mts);
        } catch (Exception e) {
            // Tangkap error jika terjadi saat mengambil data
            JOptionPane.showMessageDialog(frame, "Gagal memuat data supplier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void isiField(int row) {
        // Pastikan row yang diklik valid
        if (row >= 0 && row < listSupplier.size()) {
            frame.getTxtKdSupplier().setText(listSupplier.get(row).getKd_supplier());
            frame.getTxtNamaSupplier().setText(listSupplier.get(row).getNama_supplier());
            frame.getTxtNope().setText(listSupplier.get(row).getNope_supplier());
            frame.getTxtAlamat().setText(listSupplier.get(row).getAlamat());
            frame.getTxtKdSupplier().setEditable(false);
        }
    }
    
    public void insert() {
        if (frame.getTxtKdSupplier().getText().trim().isEmpty() ||
            frame.getTxtNamaSupplier().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Kode dan Nama Supplier tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Supplier s = new Supplier();
        s.setKd_supplier(frame.getTxtKdSupplier().getText());
        s.setNama_supplier(frame.getTxtNamaSupplier().getText());
        s.setNope_supplier(frame.getTxtNope().getText());
        s.setAlamat(frame.getTxtAlamat().getText());
        
        implSupplier.insert(s);
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        reset();
    }
    
    public void update() {
        if (frame.getTxtKdSupplier().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Pilih data yang akan diupdate dari tabel.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Supplier s = new Supplier();
        s.setKd_supplier(frame.getTxtKdSupplier().getText());
        s.setNama_supplier(frame.getTxtNamaSupplier().getText());
        s.setNope_supplier(frame.getTxtNope().getText());
        s.setAlamat(frame.getTxtAlamat().getText());
        
        implSupplier.update(s);
        JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        reset();
    }
    
    public void delete() {
        if (frame.getTxtKdSupplier().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Pilih data yang akan dihapus dari tabel.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(frame, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            implSupplier.delete(frame.getTxtKdSupplier().getText());
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            reset();
        }
    }
    
    public void cari() {
        listSupplier = implSupplier.getCari(frame.getTxtCari().getText());
        ModelTabelSupplier mts = new ModelTabelSupplier(listSupplier);
        frame.getTabelSupplier().setModel(mts);
    }
    
    public void printReport() {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/app/report/report_supplier.jrxml");
            if (reportStream == null) {
                JOptionPane.showMessageDialog(frame, "File laporan tidak ditemukan: /app/report/report_supplier.jrxml", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), Koneksi.connection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Gagal menampilkan laporan: " + e.getMessage(), "Error Laporan", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
package app.controller;

import app.dao.DaoUser;
import app.dao.ImplementUser;
import app.model.User;
import app.model.ModelTabelUser;
import app.view.FormUser;
import java.util.List;
import javax.swing.JOptionPane;
import app.koneksi.Koneksi;
import java.io.InputStream;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class ControllerUser {
    FormUser frame;
    ImplementUser implUser;
    List<User> listUser;

    public ControllerUser(FormUser frame) {
        this.frame = frame;
        implUser = new DaoUser();
        listUser = implUser.getAll();
    }
    
    public void reset() {
        frame.getTxtIdUser().setText("");
        frame.getTxtNamaUser().setText("");
        frame.getTxtNope().setText("");
        frame.getTxtUsername().setText("");
        frame.getTxtPassword().setText("");
        frame.getCmbLevel().setSelectedIndex(0);
        frame.getRbLaki().setSelected(true);
        frame.getTxtIdUser().setEditable(true);
    }
    
    public void isiTabel() {
        listUser = implUser.getAll();
        ModelTabelUser mtu = new ModelTabelUser(listUser);
        frame.getTabelUser().setModel(mtu);
    }
    
    public void isiComboLevel() {
        frame.getCmbLevel().removeAllItems();
        // Item diisi sesuai urutan ID di tbl_level
        frame.getCmbLevel().addItem("Admin");  // Index 0, ID 1
        frame.getCmbLevel().addItem("Kasir");  // Index 1, ID 2
        frame.getCmbLevel().addItem("Gudang"); // Index 2, ID 3
    }
    
    public void isiField(int row) {
        frame.getTxtIdUser().setText(listUser.get(row).getId_user());
        frame.getTxtNamaUser().setText(listUser.get(row).getNama_user());
        frame.getTxtNope().setText(listUser.get(row).getNope());
        frame.getTxtUsername().setText(listUser.get(row).getUsername());
        frame.getTxtPassword().setText(listUser.get(row).getPassword());
        
        if (listUser.get(row).getJk().equals("L")) {
            frame.getRbLaki().setSelected(true);
        } else {
            frame.getRbPerempuan().setSelected(true);
        }
        
        // Mengatur ComboBox berdasarkan ID level
        // ID 1 -> index 0, ID 2 -> index 1, dst.
        frame.getCmbLevel().setSelectedIndex(listUser.get(row).getId_level() - 1);
        
        frame.getTxtIdUser().setEditable(false);
    }
    
    public void insert() {
        // ... (Validasi input kosong) ...
        User u = new User();
        u.setId_user(frame.getTxtIdUser().getText());
        u.setNama_user(frame.getTxtNamaUser().getText());
        u.setJk(frame.getRbLaki().isSelected() ? "L" : "P");
        u.setNope(frame.getTxtNope().getText());
        u.setUsername(frame.getTxtUsername().getText());
        u.setPassword(new String(frame.getTxtPassword().getPassword()));
        // Mengambil ID Level dari index ComboBox
        u.setId_level(frame.getCmbLevel().getSelectedIndex() + 1);
        
        implUser.insert(u);
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        reset();
        isiTabel();
    }
    
    public void update() {
        // ... (Validasi ID tidak kosong) ...
        User u = new User();
        u.setId_user(frame.getTxtIdUser().getText());
        u.setNama_user(frame.getTxtNamaUser().getText());
        u.setJk(frame.getRbLaki().isSelected() ? "L" : "P");
        u.setNope(frame.getTxtNope().getText());
        u.setUsername(frame.getTxtUsername().getText());
        u.setPassword(new String(frame.getTxtPassword().getPassword()));
        u.setId_level(frame.getCmbLevel().getSelectedIndex() + 1);
        
        implUser.update(u);
        JOptionPane.showMessageDialog(null, "Data Berhasil Diperbarui");
        reset();
        isiTabel();
    }
    
    public void delete() {
        // ... (Validasi ID tidak kosong) ...
        implUser.delete(frame.getTxtIdUser().getText());
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        reset();
        isiTabel();
    }
    
    public void cari() {
        listUser = implUser.getCari(frame.getTxtCari().getText());
        ModelTabelUser mtu = new ModelTabelUser(listUser);
        frame.getTabelUser().setModel(mtu);
    }
   public void printReport() {
        try {
            // 1. Dapatkan file report sebagai InputStream
            InputStream reportStream = getClass().getResourceAsStream("/app/report/report_user.jrxml");
            if (reportStream == null) {
                JOptionPane.showMessageDialog(frame, "File laporan tidak ditemukan: report_user.jrxml", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Kompilasi file .jrxml
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // 3. Buat parameter (opsional)
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("NAMA_APLIKASI", "Aplikasi Penjualan Toko");

            // 4. Isi laporan dengan data menggunakan koneksi yang ada
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, Koneksi.connection());

            // 5. Tampilkan laporan
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Gagal menampilkan laporan: " + e.getMessage(), "Error Laporan", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
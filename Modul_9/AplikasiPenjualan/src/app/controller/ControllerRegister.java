package app.controller;

import app.dao.DaoUser;
import app.dao.ImplementUser;
import app.model.User;
import app.view.FormLogin;
import app.view.FormRegister;
import javax.swing.JOptionPane;

public class ControllerRegister {
    FormRegister frame;
    ImplementUser implUser;

    public ControllerRegister(FormRegister frame) {
        this.frame = frame;
        implUser = new DaoUser();
        generateUserId();
    }
    
    private void generateUserId() {
        long timestamp = System.currentTimeMillis() / 1000;
        String newUserId = "USER" + timestamp;
        frame.getTxtIdUser().setText(newUserId);
    }
    
    public void register() {
        // TAHAP 1: Validasi Input Kosong
        if (frame.getTxtNama().getText().trim().isEmpty() ||
            frame.getTxtNope().getText().trim().isEmpty() ||
            frame.getTxtUsername().getText().trim().isEmpty() ||
            new String(frame.getTxtPassword().getPassword()).trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(frame, "Semua kolom harus diisi!", "Error: Input Kosong", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TAHAP 2: Validasi Username Unik
        if (implUser.isUsernameExists(frame.getTxtUsername().getText().trim())) {
            JOptionPane.showMessageDialog(frame, "Username '" + frame.getTxtUsername().getText() + "' sudah digunakan.\nSilakan pilih username lain.", "Error: Username Duplikat", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // TAHAP 3: Pengumpulan Data dari Form
        User u = new User();
        u.setId_user(frame.getTxtIdUser().getText());
        u.setNama_user(frame.getTxtNama().getText());
        
        // Menggunakan ternary operator untuk menentukan jenis kelamin
        String jenisKelamin = frame.getRbLaki().isSelected() ? "L" : "P";
        u.setJk(jenisKelamin);
        
        u.setNope(frame.getTxtNope().getText());
        u.setUsername(frame.getTxtUsername().getText());
        u.setPassword(new String(frame.getTxtPassword().getPassword()));
        
        // Atur level default untuk user yang baru mendaftar (Level 2 = Kasir)
        u.setId_level(2); 

        // TAHAP 4: Interaksi dengan DAO
        implUser.register(u);
        
        // TAHAP 5: Umpan Balik dan Navigasi
        JOptionPane.showMessageDialog(null, "Registrasi Berhasil! Silakan login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        
        backToLogin();
    }
    
    public void backToLogin() {
        new FormLogin().setVisible(true);
        frame.dispose();
    }
}
package app.controller;

import app.dao.DaoUser;
import app.dao.ImplementUser;
import app.model.User;
import app.view.FormLogin;
import app.view.FormMenuUtama;
import app.view.FormRegister;
import javax.swing.JOptionPane;

/**
 * Controller untuk FormLogin.
 * Mengelola semua logika bisnis untuk login dan navigasi ke form register.
 *
 * @author [Nama Anda]
 */
public class ControllerLogin {
    FormLogin frame;
    ImplementUser implUser;

    /**
     * Konstruktor HANYA SATU yang benar untuk ControllerLogin.
     * @param frame Instance dari FormLogin yang akan dikontrol.
     */
    public ControllerLogin(FormLogin frame) {
        this.frame = frame;
        implUser = new DaoUser();
    }
    
    // HAPUS KONSTRUKTOR LAIN YANG MUNGKIN ADA DI SINI
    
    /**
     * Aksi yang dijalankan saat tombol 'Login' ditekan.
     */
    public void login() {
        // Validasi input tidak boleh kosong
        if (frame.getTxtUsername().getText().trim().isEmpty() ||
            new String(frame.getTxtPassword().getPassword()).trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(frame, "Username dan Password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Ambil data dari form
        String username = frame.getTxtUsername().getText();
        String password = new String(frame.getTxtPassword().getPassword());
        
        // Panggil DAO untuk mengecek ke database
        User user = implUser.checkLogin(username, password);
        
        // Cek hasil dari database
        if (user != null) {
            // Jika user ditemukan (login berhasil)
            JOptionPane.showMessageDialog(frame, "Login Berhasil! Selamat Datang, " + user.getNama_user(), "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            // Buka Form Menu Utama dan kirim objek user yang sedang login
            FormMenuUtama menu = new FormMenuUtama(user); 
            menu.setVisible(true);
            
            // Tutup form login
            frame.dispose(); 
        } else {
            // Jika user adalah null (login gagal)
            JOptionPane.showMessageDialog(frame, "Login Gagal! Username atau Password salah.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Membuka form registrasi dan menutup form login.
     */
    public void openRegisterForm() {
        new FormRegister().setVisible(true);
        frame.dispose();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication5;

/**
 *
 * @author Muhammad Rizal Nur F
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class koneksi { // Nama kelas 'koneksi' dengan k kecil (sesuai pemanggilan di FMahasiswa)
    public Connection con;

    public koneksi() { // Konstruktor dengan k kecil
        String id = "root";
        String pass = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/db_kampus_app"; // Ubah ke db_kampus_app

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, id, pass);
            // Sebaiknya jangan tampilkan JOptionPane di sini jika kelas ini akan digunakan oleh banyak form
            // Cukup System.out.println atau biarkan exception yang ditangkap oleh pemanggil.
            // Jika dijalankan langsung via main(), baru tampilkan JOptionPane.
            // JOptionPane.showMessageDialog(null, "Koneksi Berhasil");
            System.out.println("Koneksi ke " + url + " berhasil (dari konstruktor kelas koneksi).");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: Driver tidak ditemukan! " + e.getMessage()
                + "\nPastikan MySQL Connector/J JAR sudah ada di Libraries.", "Error Driver", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage()
                + "\nPastikan server database berjalan dan detail koneksi (URL, user, pass, nama DB) benar.", "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Metode main untuk tes mandiri (opsional)
    public static void main(String[] args) {
        koneksi k = new koneksi();
        if (k.con != null) {
            JOptionPane.showMessageDialog(null, "Tes Koneksi via main(): Berhasil terhubung ke " + k.con.getMetaData().getURL(), "Koneksi Sukses", JOptionPane.INFORMATION_MESSAGE);
            try {
                k.con.close(); // Tutup koneksi setelah tes
            } catch (SQLException | NullPointerException ex) {
                // Abaikan jika con sudah null atau error saat menutup
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tes Koneksi via main(): Gagal terhubung.", "Koneksi Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}
package app.main;

import app.view.FormLogin;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* 
         * Mengatur Look and Feel (tampilan) aplikasi agar terlihat lebih modern.
         * Ini adalah praktik yang baik untuk aplikasi Swing.
         * Blok try-catch ini menangani error jika Look and Feel Nimbus tidak tersedia.
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* 
         * Ini adalah cara yang benar untuk memulai aplikasi Swing.
         * java.awt.EventQueue.invokeLater memastikan bahwa GUI dibuat dan 
         * diperbarui pada thread yang benar (Event Dispatch Thread),
         * yang mencegah potensi masalah threading.
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Membuat instance dari form pertama yang ingin ditampilkan
                new FormLogin().setVisible(true);
            }
        });
    }
}
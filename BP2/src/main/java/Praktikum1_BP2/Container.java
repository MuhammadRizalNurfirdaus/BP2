package Praktikum1_BP2;

import javax.swing.*;
import java.awt.*;

public class Container extends javax.swing.JFrame {

    public Container() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // ================= TAB 1 - Data Barang ===================
        jToolBar1.setRollover(true);

        JLabel lblNamaBarang = new JLabel("Nama Barang:");
        JTextField tfNamaBarang = new JTextField(15);
        JLabel lblJumlah = new JLabel("Jumlah:");
        JTextField tfJumlah = new JTextField(5);
        JButton btnSimpanBarang = new JButton("Simpan");

        btnSimpanBarang.addActionListener(evt -> {
            String nama = tfNamaBarang.getText();
            String jumlah = tfJumlah.getText();
            JOptionPane.showMessageDialog(null,
                    "Data Barang:\nNama: " + nama + "\nJumlah: " + jumlah,
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        jPanel1.setLayout(new GridLayout(4, 2, 10, 10));
        jPanel1.add(lblNamaBarang);
        jPanel1.add(tfNamaBarang);
        jPanel1.add(lblJumlah);
        jPanel1.add(tfJumlah);
        jPanel1.add(new JLabel("")); // spacer
        jPanel1.add(btnSimpanBarang);

        jToolBar1.add(jPanel1);
        jTabbedPane1.addTab("Data Barang", jToolBar1);

        // ================= TAB 2 - Data Supplier ===================
        jToolBar2.setRollover(true);

        JLabel jLabel2 = new JLabel("Ini Tab Data Supplier");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int close = JOptionPane.showOptionDialog(Container.this,
                        "Ini Tab Data Supplier? Keluar Aplikasi?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (close == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(jLabel2, BorderLayout.CENTER);

        jToolBar2.add(jPanel2);
        jTabbedPane1.addTab("Data Supplier", jToolBar2);

        // ================= TAB 3 - Data Konsumen ===================
        jToolBar3.setRollover(true);

        JLabel lblNamaKonsumen = new JLabel("Nama Konsumen:");
        JTextField tfNamaKonsumen = new JTextField(15);
        JLabel lblAlamat = new JLabel("Alamat:");
        JTextField tfAlamat = new JTextField(15);
        JButton btnSimpanKonsumen = new JButton("Simpan");

        btnSimpanKonsumen.addActionListener(evt -> {
            String nama = tfNamaKonsumen.getText();
            String alamat = tfAlamat.getText();
            JOptionPane.showMessageDialog(null,
                    "Data Konsumen:\nNama: " + nama + "\nAlamat: " + alamat,
                    "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        jPanel3.setLayout(new GridLayout(4, 2, 10, 10));
        jPanel3.add(lblNamaKonsumen);
        jPanel3.add(tfNamaKonsumen);
        jPanel3.add(lblAlamat);
        jPanel3.add(tfAlamat);
        jPanel3.add(new JLabel(""));
        jPanel3.add(btnSimpanKonsumen);

        jToolBar3.add(jPanel3);
        jTabbedPane1.addTab("Data Konsumen", jToolBar3);

        // ======== Layout Utama ========
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Container.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new Container().setVisible(true);
        });
    }

    // Variables declaration
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration
}

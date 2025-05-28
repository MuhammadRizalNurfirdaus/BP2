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

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection; // Import Connection

public class FMahasiswa extends javax.swing.JFrame {

    Statement st; // Lebih baik membuat statement baru setiap kali dibutuhkan atau gunakan PreparedStatement
    ResultSet rs; // Sama seperti statement
    koneksi koneksi; // Nama kelas 'Koneksi' dengan K besar
    DefaultTableModel modelTabelMahasiswa;
    Connection connDB; // Variabel untuk menyimpan koneksi yang aktif

    public FMahasiswa() {
        koneksi = new koneksi(); // Instansiasi kelas Koneksi
        initComponents(); // Inisialisasi komponen GUI dari NetBeans Designer

        this.connDB = koneksi.con; // Dapatkan objek koneksi

        if (this.connDB == null) {
            JOptionPane.showMessageDialog(this,
                "Tidak dapat terhubung ke database. Aplikasi mungkin tidak berfungsi dengan benar.",
                "Error Koneksi Kritis", JOptionPane.ERROR_MESSAGE);
            // Menonaktifkan tombol-tombol jika koneksi gagal
            BSimpan.setEnabled(false);
            BHapus.setEnabled(false);
            BBersih.setEnabled(false);
        } else {
            System.out.println("Koneksi database siap digunakan di FMahasiswa.");
            setupTable();
            load_data();
            load_prodi();
            bersih();
        }
        setLocationRelativeTo(null); // Form di tengah layar
    }

    private void setupTable() {
        Object header[] = {"NIM", "NAMA", "JENIS KELAMIN", "PRODI", "NO HP", "ALAMAT"};
        modelTabelMahasiswa = new DefaultTableModel(null, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat sel tabel tidak bisa diedit langsung
            }
        };
        TabelMahasiswa.setModel(modelTabelMahasiswa);
    }

    private void load_data() {
        if (connDB == null) return; // Jangan lanjutkan jika tidak ada koneksi

        modelTabelMahasiswa.setRowCount(0);
        String sql = "SELECT nim, nama, jenis_kelamin, prodi, no_hp, alamat FROM tbl_mahasiswa ORDER BY nim ASC"; // Tambahkan ORDER BY
        try (Statement st = connDB.createStatement(); // try-with-resources untuk Statement
             ResultSet rs = st.executeQuery(sql)) {   // try-with-resources untuk ResultSet

            while (rs.next()) {
                String nim = rs.getString("nim");
                String nama = rs.getString("nama");
                String jk = rs.getString("jenis_kelamin");
                String prodi = rs.getString("prodi");
                String nohp = rs.getString("no_hp");
                String alamat = rs.getString("alamat");

                String data[] = {nim, nama, jk, prodi, nohp, alamat};
                modelTabelMahasiswa.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data mahasiswa: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void load_prodi() {
        CProdi.removeAllItems();
        CProdi.addItem("Pilih Prodi"); // Item default
        CProdi.addItem("Teknik Informatika");
        CProdi.addItem("Sistem Informasi");
        CProdi.addItem("Manajemen Informatika");
        CProdi.addItem("Desain Komunikasi Visual");
        CProdi.addItem("Teknik Sipil");
        CProdi.addItem("Akuntansi");
        // Tambahkan prodi lain jika perlu
    }

    private void bersih() {
        INim.setText("");
        INama.setText("");
        GJenisKelamin.clearSelection();
        RBLaki.setSelected(true); // Default Laki-laki (atau sesuaikan)
        CProdi.setSelectedIndex(0); // Pilih "Pilih Prodi"
        INoHp.setText("");
        IAlamat.setText("");
        INim.setEnabled(true); // Atau setEditable(true)
        BSimpan.setText("SIMPAN");
        BHapus.setEnabled(false);
        INim.requestFocus();
        TabelMahasiswa.clearSelection(); // Hapus seleksi pada tabel
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GJenisKelamin = new javax.swing.ButtonGroup();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        INim = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        INama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        RBLaki = new javax.swing.JRadioButton();
        RBPerempuan = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        CProdi = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        INoHp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        IAlamat = new javax.swing.JTextArea();
        BSimpan = new javax.swing.JButton();
        BHapus = new javax.swing.JButton();
        BBersih = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelMahasiswa = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form Data Mahasiswa");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DATA MAHASISWA");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("FORM INPUT DATA MAHASISWA");

        jLabel2.setText("NIM");

        jLabel3.setText("Nama Mahasiswa");

        jLabel4.setText("Jenis Kelamin");

        GJenisKelamin.add(RBLaki);
        RBLaki.setText("Laki-laki");

        GJenisKelamin.add(RBPerempuan);
        RBPerempuan.setText("Perempuan");

        jLabel5.setText("Program Studi");

        CProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Prodi" }));

        jLabel6.setText("No. HP");

        jLabel7.setText("Alamat");

        IAlamat.setColumns(20);
        IAlamat.setRows(5);
        jScrollPane2.setViewportView(IAlamat);

        BSimpan.setText("SIMPAN");
        BSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSimpanActionPerformed(evt);
            }
        });

        BHapus.setText("HAPUS");
        BHapus.setEnabled(false);
        BHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BHapusActionPerformed(evt);
            }
        });

        BBersih.setText("BERSIH");
        BBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BBersihActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BBersih))
                            .addComponent(INim)
                            .addComponent(INama)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(RBLaki)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RBPerempuan))
                            .addComponent(CProdi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(INoHp)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(INim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(INama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(RBLaki)
                    .addComponent(RBPerempuan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(CProdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(INoHp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BSimpan)
                    .addComponent(BHapus)
                    .addComponent(BBersih))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("TABEL DATA MAHASISWA");

        TabelMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "NIM", "Nama", "Jenis Kelamin", "Prodi", "No HP", "Alamat"
            }
        ));
        TabelMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelMahasiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelMahasiswa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBersihActionPerformed
        bersih();
    }//GEN-LAST:event_BBersihActionPerformed

    private void BSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSimpanActionPerformed
        if (connDB == null) {
            JOptionPane.showMessageDialog(this, "Koneksi database tidak tersedia.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nim = INim.getText().trim();
        String nama = INama.getText().trim();
        String jenisKelamin = "";
        if (RBLaki.isSelected()) {
            jenisKelamin = "Laki-laki";
        } else if (RBPerempuan.isSelected()) {
            jenisKelamin = "Perempuan";
        }
        // Ambil nilai prodi, pastikan tidak null
        String prodi = CProdi.getSelectedItem() != null ? CProdi.getSelectedItem().toString() : "";
        String noHp = INoHp.getText().trim();
        String alamat = IAlamat.getText().trim();

        if (nim.isEmpty() || nama.isEmpty() || jenisKelamin.isEmpty() || prodi.equals("Pilih Prodi") || prodi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM, Nama, Jenis Kelamin, dan Prodi wajib diisi/dipilih!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql;
        boolean isUpdate = BSimpan.getText().equals("UPDATE");

        try {
            PreparedStatement pstmt;
            if (isUpdate) { // Mode Update
                sql = "UPDATE tbl_mahasiswa SET nama=?, jenis_kelamin=?, prodi=?, no_hp=?, alamat=? WHERE nim=?";
                pstmt = connDB.prepareStatement(sql);
                pstmt.setString(1, nama);
                pstmt.setString(2, jenisKelamin);
                pstmt.setString(3, prodi);
                pstmt.setString(4, noHp);
                pstmt.setString(5, alamat);
                pstmt.setString(6, nim); // NIM untuk klausa WHERE
            } else { // Mode Simpan
                 // Cek dulu apakah NIM sudah ada
                try (Statement stmtCek = connDB.createStatement();
                     ResultSet rsCek = stmtCek.executeQuery("SELECT nim FROM tbl_mahasiswa WHERE nim='" + nim + "'")) {
                    if (rsCek.next()) {
                        JOptionPane.showMessageDialog(this, "NIM " + nim + " sudah terdaftar!", "Error Simpan", JOptionPane.ERROR_MESSAGE);
                        return; // Hentikan proses simpan jika NIM sudah ada
                    }
                } // stmtCek dan rsCek otomatis tertutup di sini

                sql = "INSERT INTO tbl_mahasiswa (nim, nama, jenis_kelamin, prodi, no_hp, alamat) VALUES (?, ?, ?, ?, ?, ?)";
                pstmt = connDB.prepareStatement(sql);
                pstmt.setString(1, nim);
                pstmt.setString(2, nama);
                pstmt.setString(3, jenisKelamin);
                pstmt.setString(4, prodi);
                pstmt.setString(5, noHp);
                pstmt.setString(6, alamat);
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil " + (isUpdate ? "diupdate!" : "disimpan!"), "Sukses", JOptionPane.INFORMATION_MESSAGE);
                load_data();
                bersih();
            } else {
                 JOptionPane.showMessageDialog(this, "Data gagal " + (isUpdate ? "diupdate (NIM tidak ditemukan?)." : "disimpan."), "Gagal", JOptionPane.ERROR_MESSAGE);
            }
            pstmt.close(); // Tutup PreparedStatement
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal " + (isUpdate ? "mengupdate" : "menyimpan") + " data: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_BSimpanActionPerformed

    private void TabelMahasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelMahasiswaMouseClicked
        int baris = TabelMahasiswa.getSelectedRow();
        if (baris != -1) { // Pastikan ada baris yang dipilih
            INim.setText(modelTabelMahasiswa.getValueAt(baris, 0).toString());
            INama.setText(modelTabelMahasiswa.getValueAt(baris, 1).toString());

            String jk = modelTabelMahasiswa.getValueAt(baris, 2).toString();
            if (jk.equalsIgnoreCase("Laki-laki")) { // Gunakan equalsIgnoreCase untuk perbandingan string
                RBLaki.setSelected(true);
            } else if (jk.equalsIgnoreCase("Perempuan")) {
                RBPerempuan.setSelected(true);
            } else {
                GJenisKelamin.clearSelection(); // Jika tidak L atau P, bersihkan pilihan
            }

            // Hati-hati jika item prodi di tabel tidak ada di ComboBox
            String prodiDiTabel = modelTabelMahasiswa.getValueAt(baris, 3).toString();
            boolean prodiDitemukan = false;
            for (int i = 0; i < CProdi.getItemCount(); i++) {
                if (CProdi.getItemAt(i).equalsIgnoreCase(prodiDiTabel)) {
                    CProdi.setSelectedIndex(i);
                    prodiDitemukan = true;
                    break;
                }
            }
            if (!prodiDitemukan && !prodiDiTabel.isEmpty()) {
                 // Jika prodi dari tabel tidak ada di ComboBox, bisa tambahkan ke ComboBox (opsional)
                 // CProdi.addItem(prodiDiTabel);
                 // CProdi.setSelectedItem(prodiDiTabel);
                 // Atau set ke default "Pilih Prodi" jika tidak ingin menambah
                 CProdi.setSelectedIndex(0); // Atau tampilkan pesan
                 JOptionPane.showMessageDialog(this, "Program studi '" + prodiDiTabel + "' tidak ditemukan dalam pilihan.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }


            Object noHpObj = modelTabelMahasiswa.getValueAt(baris, 4);
            INoHp.setText(noHpObj != null ? noHpObj.toString() : "");

            Object alamatObj = modelTabelMahasiswa.getValueAt(baris, 5);
            IAlamat.setText(alamatObj != null ? alamatObj.toString() : "");

            INim.setEnabled(false); // Atau setEditable(false)
            BSimpan.setText("UPDATE");
            BHapus.setEnabled(true);
        }
    }//GEN-LAST:event_TabelMahasiswaMouseClicked

    private void BHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BHapusActionPerformed
        if (connDB == null) {
             JOptionPane.showMessageDialog(this, "Koneksi database tidak tersedia.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String nim = INim.getText();
        if (nim.isEmpty() || !BHapus.isEnabled()) { // Cek juga apakah tombol hapus aktif
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Anda yakin ingin menghapus data mahasiswa dengan NIM '" + nim + "'?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tbl_mahasiswa WHERE nim = ?";
            try (PreparedStatement pstmt = connDB.prepareStatement(sql)) { // try-with-resources
                pstmt.setString(1, nim);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    load_data();
                    bersih();
                } else {
                    JOptionPane.showMessageDialog(this, "Data gagal dihapus (NIM tidak ditemukan).", "Gagal", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_BHapusActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FMahasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FMahasiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BBersih;
    private javax.swing.JButton BHapus;
    private javax.swing.JButton BSimpan;
    private javax.swing.JComboBox<String> CProdi;
    private javax.swing.ButtonGroup GJenisKelamin;
    private javax.swing.JTextArea IAlamat;
    private javax.swing.JTextField INama;
    private javax.swing.JTextField INim;
    private javax.swing.JTextField INoHp;
    private javax.swing.JRadioButton RBLaki;
    private javax.swing.JRadioButton RBPerempuan;
    private javax.swing.JTable TabelMahasiswa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
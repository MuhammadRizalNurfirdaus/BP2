package jdbc;

// FLevel.java
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;

public class FLevel extends javax.swing.JFrame {

    Koneksi koneksi;
    Statement st;
    ResultSet rs;
    DefaultTableModel dataTable;

    public FLevel() {
        koneksi = new Koneksi();
        initComponents();
        try {
            BTambah.setIcon(new ImageIcon(getClass().getResource("/icons/save_icon.png")));
            BEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit_icon.png")));
            BHapus.setIcon(new ImageIcon(getClass().getResource("/icons/delete_icon.png")));
            BBersih.setIcon(new ImageIcon(getClass().getResource("/icons/clear_icon.png")));
        } catch (Exception e) {
            System.err.println("Gagal memuat ikon untuk FLevel: " + e.getMessage());
        }

        load_data_level();
        bersih_form_level();
        // IIdLevel.setEditable(false); // Dihapus, ID Level diinput manual saat baru
    }

    private void load_data_level() {
        Object header[] = {"ID LEVEL", "NAMA LEVEL"};
        dataTable = new DefaultTableModel(null, header);
        TblLevel.setModel(dataTable);

        String sql = "SELECT * FROM tbl_level ORDER BY id_level ASC;";

        try {
            if (koneksi.con == null) {
                JOptionPane.showMessageDialog(this, "Koneksi ke database gagal!", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            st = koneksi.con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("ID_LEVEL"); // Sesuai nama kolom di DB
                String nama = rs.getString("LEVEL");  // Sesuai nama kolom di DB
                String k[] = {id, nama};
                dataTable.addRow(k);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat memuat data level: " + e.getMessage(), "Error Load Data", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void bersih_form_level() {
        IIdLevel.setText("");
        INamaLevel.setText("");
        IIdLevel.setEditable(true); // ID Level bisa diinput saat baru
        IIdLevel.requestFocus();    // Fokus ke ID Level dulu

        BTambah.setEnabled(true);
        BEdit.setEnabled(false);
        BHapus.setEnabled(false);
    }

    private boolean isIdLevelExists(String idLevel) {
        // Cek apakah ID Level sudah ada
        String sql = "SELECT COUNT(*) FROM tbl_level WHERE ID_LEVEL = ?";
        try (PreparedStatement ps = koneksi.con.prepareStatement(sql)) {
            ps.setString(1, idLevel);
            ResultSet rsCheck = ps.executeQuery();
            if (rsCheck.next()) {
                return rsCheck.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat cek duplikasi ID level: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private boolean isNamaLevelExists(String namaLevel, String currentIdLevel) {
        String sql = "SELECT COUNT(*) FROM tbl_level WHERE LEVEL = ? AND (ID_LEVEL != ? OR ? IS NULL)";
        try (PreparedStatement ps = koneksi.con.prepareStatement(sql)) {
            ps.setString(1, namaLevel);
            ps.setString(2, currentIdLevel);
            ps.setString(3, currentIdLevel);
            ResultSet rsCheck = ps.executeQuery();
            if (rsCheck.next()) {
                return rsCheck.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat cek duplikasi nama level: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        IIdLevel = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        INamaLevel = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblLevel = new javax.swing.JTable();
        BTambah = new javax.swing.JButton();
        BEdit = new javax.swing.JButton();
        BHapus = new javax.swing.JButton();
        BBersih = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kelola Data Level");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("KELOLA DATA LEVEL");

        jLabel2.setText("ID Level");

        jLabel3.setText("Nama Level");

        TblLevel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID Level", "Nama Level"
            }
        ));
        TblLevel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblLevelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TblLevel);

        BTambah.setText("TAMBAH");
        BTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTambahActionPerformed(evt);
            }
        });

        BEdit.setText("EDIT");
        BEdit.setEnabled(false);
        BEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEditActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IIdLevel)
                            .addComponent(INamaLevel)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BBersih, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 40, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BBersih, BEdit, BHapus, BTambah});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(IIdLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(INamaLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTambah)
                    .addComponent(BEdit)
                    .addComponent(BHapus)
                    .addComponent(BBersih))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTambahActionPerformed
        String idLevel = IIdLevel.getText().trim();
        String namaLevel = INamaLevel.getText().trim();

        if (idLevel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Level harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            IIdLevel.requestFocus();
            return;
        }
        // Validasi ID Level (misalnya harus angka)
        try {
            Integer.parseInt(idLevel);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Level harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            IIdLevel.requestFocus();
            return;
        }

        if (namaLevel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Level harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            INamaLevel.requestFocus();
            return;
        }

        if (isIdLevelExists(idLevel)) {
            JOptionPane.showMessageDialog(this, "ID Level '" + idLevel + "' sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
            IIdLevel.requestFocus();
            return;
        }
        if (isNamaLevelExists(namaLevel, null)) {
            JOptionPane.showMessageDialog(this, "Nama Level '" + namaLevel + "' sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
            INamaLevel.requestFocus();
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Simpan data level baru?", "Konfirmasi Simpan", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            String sql = "INSERT INTO tbl_level (ID_LEVEL, LEVEL) VALUES (?, ?)";
            try (PreparedStatement ps = koneksi.con.prepareStatement(sql)) {
                ps.setString(1, idLevel);
                ps.setString(2, namaLevel);
                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data level berhasil disimpan.");
                    load_data_level();
                    bersih_form_level();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error saat menyimpan data level: " + e.getMessage(), "Error Simpan", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_BTambahActionPerformed

    private void BEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditActionPerformed
        String idLevel = IIdLevel.getText(); // ID tidak diubah, jadi tidak perlu trim
        String namaLevel = INamaLevel.getText().trim();

        if (idLevel.isEmpty()) { // Seharusnya tidak terjadi karena tombol edit aktif jika ID ada
            JOptionPane.showMessageDialog(this, "ID Level tidak boleh kosong.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (namaLevel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Level harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            INamaLevel.requestFocus();
            return;
        }

        if (isNamaLevelExists(namaLevel, idLevel)) {
            JOptionPane.showMessageDialog(this, "Nama Level '" + namaLevel + "' sudah digunakan oleh level lain!", "Error", JOptionPane.ERROR_MESSAGE);
            INamaLevel.requestFocus();
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Update data level ini?", "Konfirmasi Edit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            String sql = "UPDATE tbl_level SET LEVEL = ? WHERE ID_LEVEL = ?";
            try (PreparedStatement ps = koneksi.con.prepareStatement(sql)) {
                ps.setString(1, namaLevel);
                ps.setString(2, idLevel);
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Data level berhasil diupdate.");
                    load_data_level();
                    bersih_form_level();
                } else {
                    JOptionPane.showMessageDialog(this, "Data level tidak ditemukan atau tidak ada perubahan.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error saat mengupdate data level: " + e.getMessage(), "Error Edit", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_BEditActionPerformed

    private void BHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BHapusActionPerformed
        String idLevel = IIdLevel.getText();
        if (idLevel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data level dari tabel terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus data level ini ("+idLevel+")?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                String checkSql = "SELECT COUNT(*) FROM tbl_user WHERE id_level = ?";
                PreparedStatement checkPs = koneksi.con.prepareStatement(checkSql);
                checkPs.setString(1, idLevel);
                ResultSet rsCheck = checkPs.executeQuery();
                if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Level ini tidak dapat dihapus karena masih digunakan oleh user.", "Error Hapus", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error saat cek penggunaan level: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            String sql = "DELETE FROM tbl_level WHERE ID_LEVEL = ?";
            try (PreparedStatement ps = koneksi.con.prepareStatement(sql)) {
                ps.setString(1, idLevel);
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Data level berhasil dihapus.");
                    load_data_level();
                    bersih_form_level();
                } else {
                    JOptionPane.showMessageDialog(this, "Data level tidak ditemukan.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error saat menghapus data level: " + e.getMessage(), "Error Hapus", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_BHapusActionPerformed

    private void BBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBersihActionPerformed
        bersih_form_level();
    }//GEN-LAST:event_BBersihActionPerformed

    private void TblLevelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblLevelMouseClicked
        int baris = TblLevel.getSelectedRow();
        if (baris != -1) {
            IIdLevel.setText(TblLevel.getValueAt(baris, 0).toString());
            INamaLevel.setText(TblLevel.getValueAt(baris, 1).toString());

            IIdLevel.setEditable(false); // ID tidak boleh diedit setelah dipilih
            BTambah.setEnabled(false);
            BEdit.setEnabled(true);
            BHapus.setEnabled(true);
        }
    }//GEN-LAST:event_TblLevelMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FLevel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BBersih;
    private javax.swing.JButton BEdit;
    private javax.swing.JButton BHapus;
    private javax.swing.JButton BTambah;
    private javax.swing.JTextField IIdLevel;
    private javax.swing.JTextField INamaLevel;
    private javax.swing.JTable TblLevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
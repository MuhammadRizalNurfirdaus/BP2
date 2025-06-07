package jdbc;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;

/**
 *
 * @author Muhammad Rizal Nur F (dimodifikasi berdasarkan permintaan)
 */
public class FUser extends javax.swing.JFrame {

    Statement st;
    ResultSet rs;
    Koneksi koneksi;

    public FUser() {
        koneksi = new Koneksi();
        initComponents();
        load_data();
        IDOtomatis();
        level();
        bersih();
        IIdUser.setEditable(false);
    }

    private void load_data() {
        Object header[] = {"ID USER", "LEVEL", "NAMA USER", "JENIS KELAMIN",
            "NO HANDPHONE", "USERNAME", "PASSWORD"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        TUser.setModel(data);

        String sql = "SELECT tbl_user.id_user, tbl_level.level, tbl_user.nama_user, "
                + "tbl_user.jk, tbl_user.NOPE, tbl_user.username, tbl_user.password "
                + "FROM tbl_user INNER JOIN tbl_level ON tbl_user.id_level = tbl_level.id_level ORDER BY tbl_user.id_user ASC;";

        try {
            if (koneksi.con == null) {
                JOptionPane.showMessageDialog(null, "Koneksi ke database gagal!");
                return;
            }
            st = koneksi.con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String k1 = rs.getString(1);
                String k2 = rs.getString(2);
                String k3 = rs.getString(3);
                String k4 = rs.getString(4);
                String k5 = rs.getString(5);
                String k6 = rs.getString(6);
                String k7 = rs.getString(7);
                String k[] = {k1, k2, k3, k4, k5, k6, k7};
                data.addRow(k);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saat load data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void IDOtomatis() {
        try {
            if (koneksi.con == null) {
                return;
            }
            st = koneksi.con.createStatement();
            String sql = "SELECT * FROM tbl_user order by id_user desc";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String idUser = rs.getString("id_user").substring(4);
                String AN = "" + (Integer.parseInt(idUser) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "00";
                } else if (AN.length() == 2) {
                    Nol = "0";
                } else if (AN.length() == 3) {
                    Nol = "";
                }
                IIdUser.setText("USER" + Nol + AN);
            } else {
                IIdUser.setText("USER001");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error ID Otomatis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void level() {
        if (CLevel.getItemCount() > 0) {
            CLevel.setSelectedIndex(0);
        }
    }

    private void bersih() {
        IDOtomatis();
        if (CLevel.getItemCount() > 0) {
            CLevel.setSelectedIndex(0);
        }
        INamaUser.setText("");
        Gjk.clearSelection();
        INope.setText("");
        IUser.setText("");
        IPassword.setText("");
        INamaUser.requestFocus();

        BSimpan.setEnabled(true);
        BEdit.setEnabled(false);
        BHapus.setEnabled(false);
        IIdUser.setEditable(false);
    }

    private int getIdLevelByName(String levelName) {
        try {
            if (koneksi.con == null) return -1;
            String sql = "SELECT id_level FROM tbl_level WHERE level = ?";
            PreparedStatement ps = koneksi.con.prepareStatement(sql);
            ps.setString(1, levelName);
            ResultSet rsLevel = ps.executeQuery();
            if (rsLevel.next()) {
                return rsLevel.getInt("id_level");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mendapatkan ID Level: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // --- Method untuk Edit Data (dengan PreparedStatement dan konfirmasi) ---
    private void EditData() { // Diubah namanya dari Edit() agar lebih deskriptif
        // Validasi dasar sebelum konfirmasi
        String namaUser = INamaUser.getText();
        String jk = "";
        if (JkL.isSelected()) {
            jk = "L";
        } else if (JkP.isSelected()) {
            jk = "P";
        }
        String nope = INope.getText();
        String username = IUser.getText();
        String password = new String(IPassword.getPassword());

        if (namaUser.isEmpty() || jk.isEmpty() || nope.isEmpty() || username.isEmpty() || password.isEmpty() || CLevel.getSelectedIndex() == -1) {
             JOptionPane.showMessageDialog(this, "Data Harus Diisi !!", "Peringatan", JOptionPane.WARNING_MESSAGE);
             return;
        }

        // --- POST TEST 1: YesNoQuestion Edit Data ---
        int konfirmasiEdit = JOptionPane.showConfirmDialog(this,
                "Apakah Data Akan Di Edit?", // Pesan sesuai gambar
                "Edit Data", // Judul dialog sesuai gambar
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE); // Icon question mark

        if (konfirmasiEdit == JOptionPane.YES_OPTION) {
            try {
                if (koneksi.con == null) {
                    JOptionPane.showMessageDialog(this, "Koneksi Database Gagal!");
                    return;
                }

                int idLevel = getIdLevelByName(CLevel.getSelectedItem().toString());
                if (idLevel <= 0) {
                    JOptionPane.showMessageDialog(this, "Level tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String idUser = IIdUser.getText();

                // Cek duplikasi username saat edit (kecuali untuk user itu sendiri)
                String checkSql = "SELECT COUNT(*) FROM tbl_user WHERE username = ? AND id_user != ?";
                PreparedStatement checkPs = koneksi.con.prepareStatement(checkSql);
                checkPs.setString(1, username);
                checkPs.setString(2, idUser);
                ResultSet checkRs = checkPs.executeQuery();
                if(checkRs.next() && checkRs.getInt(1) > 0){
                    JOptionPane.showMessageDialog(this, "Username '" + username + "' sudah digunakan oleh user lain!", "Error", JOptionPane.ERROR_MESSAGE);
                    IUser.requestFocus();
                    return;
                }


                String sql_edit = "UPDATE tbl_user SET id_level = ?, nama_user = ?, jk = ?, "
                                + "nope = ?, username = ?, password = ? WHERE id_user = ?";
                PreparedStatement ps = koneksi.con.prepareStatement(sql_edit);
                ps.setInt(1, idLevel);
                ps.setString(2, namaUser);
                ps.setString(3, jk);
                ps.setString(4, nope);
                ps.setString(5, username);
                ps.setString(6, password);
                ps.setString(7, idUser);

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil Di Update");
                    // --- POST TEST 4: Setelah di Edit/Hapus, maka JTable akan melakukan Load data ---
                    load_data();
                    // --- POST TEST 3: Ketika Di Edit/Hapus, Form Input Data Kembali Kosong seperti kondisi awal ---
                    bersih();
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan atau tidak ada perubahan.");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error saat update data: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // --- Method untuk Hapus Data (dengan konfirmasi sesuai POST TEST) ---
    private void HapusData() { // Diubah namanya dari Hapus()
        // --- POST TEST 2: YesNoQuestion Hapus Data ---
        // (Dialog konfirmasi sudah ada, pastikan pesannya sesuai jika perlu)
        // Pesan dari gambar untuk konfirmasi Simpan adalah "Apakah Data Sudah Benar? SIMPAN?"
        // Untuk Hapus, kita gunakan pesan yang lebih umum atau sesuai kebutuhan.
        // Instruksi "Message Dialog YesNoQuestion Hapus Data" bisa diartikan perlu ada dialog
        // seperti pada Simpan. Untuk Hapus, pesan "Apakah Anda yakin ingin menghapus data ini?"
        // sudah cukup baik dan umum. Jika harus persis seperti dialog simpan, bisa diubah.
        // Untuk sekarang, saya akan pertahankan dialog konfirmasi hapus yang sudah ada karena lebih spesifik untuk hapus.
        // Jika instruksi menghendaki dialog persis seperti yang diilustrasikan untuk SIMPAN (tapi untuk HAPUS),
        // maka pesannya bisa diubah menjadi "Apakah Data Akan Di Hapus?" dengan judul "Hapus Data".
        // Kita akan gunakan itu untuk konsistensi dengan Poin 1.

        int konfirmasiHapus = JOptionPane.showConfirmDialog(this,
                "Apakah Data Akan Di Hapus?", // Sesuai pola POST TEST 1 & 2
                "Hapus Data", // Judul dialog
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE); // Icon question mark

        if (konfirmasiHapus == JOptionPane.YES_OPTION) {
            try {
                if (koneksi.con == null) {
                    JOptionPane.showMessageDialog(this, "Koneksi Database Gagal!");
                    return;
                }

                String sql_delete = "DELETE FROM tbl_user WHERE id_user = ?";
                PreparedStatement ps = koneksi.con.prepareStatement(sql_delete);
                ps.setString(1, IIdUser.getText());

                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil Di Hapus");
                    // --- POST TEST 4: Setelah di Edit/Hapus, maka JTable akan melakukan Load data ---
                    load_data();
                    // --- POST TEST 3: Ketika Di Edit/Hapus, Form Input Data Kembali Kosong seperti kondisi awal ---
                    bersih();
                } else {
                     JOptionPane.showMessageDialog(null, "Data tidak ditemukan.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error saat hapus data: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Gjk = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        IIdUser = new javax.swing.JTextField();
        INamaUser = new javax.swing.JTextField();
        INope = new javax.swing.JTextField();
        IUser = new javax.swing.JTextField();
        CLevel = new javax.swing.JComboBox<>();
        JkL = new javax.swing.JRadioButton();
        JkP = new javax.swing.JRadioButton();
        IPassword = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TUser = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        BSimpan = new javax.swing.JButton();
        BEdit = new javax.swing.JButton();
        BHapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form User");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel1.setText("FORM INPUT DATA");

        jLabel2.setText("ID USER");

        jLabel3.setText("LEVEL");

        jLabel4.setText("NAMA USER");

        jLabel5.setText("JENIS KELAMIN");

        jLabel6.setText("NO HP");

        jLabel7.setText("USERNAME");

        jLabel8.setText("PASSWORD");

        CLevel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ADMIN", "GUDANG", "KASIR" }));

        Gjk.add(JkL);
        JkL.setText("L");
        JkL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JkLActionPerformed(evt);
            }
        });

        Gjk.add(JkP);
        JkP.setText("P");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel9.setText("DATA USER APLIKASI KASIR");

        TUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TUser);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel10.setText("DATA USER");

        BSimpan.setText("SIMPAN");
        BSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSimpanActionPerformed(evt);
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

        try {
            BSimpan.setIcon(new ImageIcon(getClass().getResource("/icons/save_icon.png")));
            BEdit.setIcon(new ImageIcon(getClass().getResource("/icons/edit_icon.png")));
            BHapus.setIcon(new ImageIcon(getClass().getResource("/icons/delete_icon.png")));
        } catch (NullPointerException e) {
            System.err.println("Warning: Icon file not found. Check paths in initComponents().");
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(236, 236, 236))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BHapus)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(JkL)
                                        .addGap(18, 18, 18)
                                        .addComponent(JkP))
                                    .addComponent(CLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(IIdUser, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                    .addComponent(INope)
                                    .addComponent(IUser)
                                    .addComponent(IPassword)
                                    .addComponent(INamaUser))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 34, Short.MAX_VALUE))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(350, 350, 350))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel10))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(IIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(CLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(INamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(JkL)
                                    .addComponent(JkP))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addGap(6, 6, 6))
                            .addComponent(INope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(IUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(IPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BSimpan)
                    .addComponent(BEdit)
                    .addComponent(BHapus))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JkLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JkLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JkLActionPerformed

    private void BSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSimpanActionPerformed
        if (koneksi.con == null) {
            JOptionPane.showMessageDialog(this, "Koneksi Database Gagal!");
            return;
        }

        String idUser = IIdUser.getText();
        String levelName = CLevel.getSelectedItem().toString();
        String namaUser = INamaUser.getText();
        String jk = "";
        if (JkL.isSelected()) {
            jk = "L";
        } else if (JkP.isSelected()) {
            jk = "P";
        }
        String nope = INope.getText();
        String username = IUser.getText();
        String password = new String(IPassword.getPassword());

        if (namaUser.isEmpty() || jk.isEmpty() || nope.isEmpty() || username.isEmpty() || password.isEmpty() || CLevel.getSelectedIndex() == -1 || (CLevel.getSelectedItem() != null && CLevel.getSelectedItem().toString().equals("- Pilih Level -")) ) {
            JOptionPane.showMessageDialog(this, "Data Harus Diisi !!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Konfirmasi Simpan (Sudah ada sebelumnya, sesuai gambar "Simpan Data")
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Apakah Data Sudah Benar? SIMPAN?",
                "Simpan Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            int idLevel = getIdLevelByName(levelName);
            if (idLevel <= 0) {
                JOptionPane.showMessageDialog(this, "Level tidak valid atau tidak ditemukan di database.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String checkSql = "SELECT COUNT(*) FROM tbl_user WHERE username = ? AND id_user != ?";
                PreparedStatement checkPs = koneksi.con.prepareStatement(checkSql);
                checkPs.setString(1, username);
                checkPs.setString(2, idUser);
                ResultSet checkRs = checkPs.executeQuery();
                if (checkRs.next() && checkRs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "Username '" + username + "' sudah digunakan!", "Error", JOptionPane.ERROR_MESSAGE);
                    IUser.requestFocus();
                    return;
                }

                String sql = "INSERT INTO tbl_user (id_user, id_level, nama_user, jk, NOPE, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = koneksi.con.prepareStatement(sql);
                ps.setString(1, idUser);
                ps.setInt(2, idLevel);
                ps.setString(3, namaUser);
                ps.setString(4, jk);
                ps.setString(5, nope);
                ps.setString(6, username);
                ps.setString(7, password);

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data Barang Berhasil Dimasukan"); // Pesan bisa disesuaikan "Data User"
                    load_data();
                    bersih();
                }
            } catch (SQLException e) {
                if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                    JOptionPane.showMessageDialog(this, "ID User '" + idUser + "' sudah ada atau terjadi kesalahan integritas data.\nDetail: " + e.getMessage(), "Error Simpan", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error saat menyimpan data: " + e.getMessage(), "Error Simpan", JOptionPane.ERROR_MESSAGE);
                }
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_BSimpanActionPerformed

    private void BEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditActionPerformed
        EditData(); // Panggil method EditData yang sudah dimodifikasi
    }//GEN-LAST:event_BEditActionPerformed

    private void BHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BHapusActionPerformed
        HapusData(); // Panggil method HapusData yang sudah dimodifikasi
    }//GEN-LAST:event_BHapusActionPerformed

    private void TUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TUserMouseClicked
        int bar = TUser.getSelectedRow();
        if (bar != -1) {
            String a = TUser.getValueAt(bar, 0).toString();
            String b = TUser.getValueAt(bar, 1).toString();
            String c = TUser.getValueAt(bar, 2).toString();
            String d = TUser.getValueAt(bar, 3).toString();
            String e = TUser.getValueAt(bar, 4).toString();
            String f = TUser.getValueAt(bar, 5).toString();
            String g = TUser.getValueAt(bar, 6).toString();

            IIdUser.setText(a);
            for (int i = 0; i < CLevel.getItemCount(); i++) {
                if (CLevel.getItemAt(i).toString().equalsIgnoreCase(b)) {
                    CLevel.setSelectedIndex(i);
                    break;
                }
            }
            INamaUser.setText(c);
            INope.setText(e);
            IUser.setText(f);
            IPassword.setText(g);

            if ("L".equalsIgnoreCase(d)) {
                JkL.setSelected(true);
            } else if ("P".equalsIgnoreCase(d)) {
                JkP.setSelected(true);
            } else {
                Gjk.clearSelection();
            }

            BSimpan.setEnabled(false);
            BEdit.setEnabled(true);
            BHapus.setEnabled(true);
            IIdUser.setEditable(false);
        }
    }//GEN-LAST:event_TUserMouseClicked

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
            java.util.logging.Logger.getLogger(FUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BEdit;
    private javax.swing.JButton BHapus;
    private javax.swing.JButton BSimpan;
    private javax.swing.JComboBox<String> CLevel;
    private javax.swing.ButtonGroup Gjk;
    private javax.swing.JTextField IIdUser;
    private javax.swing.JTextField INamaUser;
    private javax.swing.JTextField INope;
    private javax.swing.JPasswordField IPassword;
    private javax.swing.JTextField IUser;
    private javax.swing.JRadioButton JkL;
    private javax.swing.JRadioButton JkP;
    private javax.swing.JTable TUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
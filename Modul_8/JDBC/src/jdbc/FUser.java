package jdbc;

// Import yang benar dan dibutuhkan
import java.io.File; // Perbaikan import
import java.io.InputStream;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException; // Untuk catch di finally block
import net.sf.jasperreports.engine.JRException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Muhammad Rizal Nur F (dimodifikasi berdasarkan permintaan)
 */
public class FUser extends javax.swing.JFrame {

    Statement st;
    ResultSet rs;
    Koneksi koneksi;

    // Variabel Report
    JasperReport JasRep;
    JasperPrint JasPri;
    Map<String, Object> param = new HashMap<>(); // Menggunakan generic type
    JasperDesign JasDes;

    // Deklarasi variabel komponen GUI yang akan diakses dari method lain
    // Nama-nama ini harus sesuai dengan yang ada di initComponents
    // atau sebaliknya, initComponents harus menggunakan nama-nama ini.
    // Saya akan mengganti nama di initComponents agar sesuai dengan ini.
    private javax.swing.JButton BSimpan;
    private javax.swing.JButton BEdit;
    private javax.swing.JButton BHapus;
    private javax.swing.JTextField INamaUser; // Menggantikan jTextField2


    public FUser() {
        koneksi = new Koneksi();
        initComponents(); // Pastikan nama variabel komponen di sini sudah benar
        load_data();
        IDOtomatis();
        level();
        bersih();
        IIdUser.setEditable(false); // IIdUser harus dideklarasikan di Variables (sudah)
        this.setTitle("Form User Data"); // Menambahkan judul window
        this.setLocationRelativeTo(null); // Menengahkan window
    }

    private void load_data() {
        Object header[] = {"ID USER", "LEVEL", "NAMA USER", "JENIS KELAMIN",
            "NO HANDPHONE", "USERNAME", "PASSWORD"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        TUser.setModel(data); // TUser harus dideklarasikan di Variables (sudah)

        String sql = "SELECT tbl_user.id_user, tbl_level.level, tbl_user.nama_user, "
                + "tbl_user.jk, tbl_user.NOPE, tbl_user.username, tbl_user.password "
                + "FROM tbl_user INNER JOIN tbl_level ON tbl_user.id_level = tbl_level.id_level ORDER BY tbl_user.id_user ASC;";

        try {
            if (koneksi.con == null) {
                JOptionPane.showMessageDialog(this, "Koneksi ke database gagal! Pastikan database server berjalan.", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Gunakan try-with-resources untuk Statement dan ResultSet
            try (Statement stmt = koneksi.con.createStatement();
                 ResultSet rset = stmt.executeQuery(sql)) {
                while (rset.next()) {
                    String k1 = rset.getString(1);
                    String k2 = rset.getString(2);
                    String k3 = rset.getString(3);
                    String k4 = rset.getString(4);
                    String k5 = rset.getString(5);
                    String k6 = rset.getString(6);
                    String k7 = rset.getString(7);
                    String k[] = {k1, k2, k3, k4, k5, k6, k7};
                    data.addRow(k);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat load data: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void IDOtomatis() {
        if (koneksi.con == null) return;
        try (Statement stmt = koneksi.con.createStatement()) {
            String sql = "SELECT id_user FROM tbl_user ORDER BY id_user DESC LIMIT 1"; // Lebih efisien
            try (ResultSet rset = stmt.executeQuery(sql)) {
                if (rset.next()) {
                    String idUserTerakhir = rset.getString("id_user");
                    // Asumsi format ID_USER adalah "USER" diikuti 3 digit angka
                    if (idUserTerakhir != null && idUserTerakhir.startsWith("USER") && idUserTerakhir.length() == 7) {
                        String angkaBagian = idUserTerakhir.substring(4); // Ambil "001"
                        int angka = Integer.parseInt(angkaBagian);
                        angka++; // Increment
                        IIdUser.setText("USER" + String.format("%03d", angka)); // Format menjadi USER002, dst.
                    } else {
                         IIdUser.setText("USER001"); // Jika format tidak sesuai atau tabel kosong
                    }
                } else {
                    IIdUser.setText("USER001"); // Jika tabel kosong
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error ID Otomatis: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error parsing ID User untuk ID Otomatis: " + e.getMessage(), "Error Format", JOptionPane.ERROR_MESSAGE);
            IIdUser.setText("USER001"); // Fallback jika ada error parsing
            e.printStackTrace();
        }
    }


    private void level() {
        if (CLevel.getItemCount() > 0) { // CLevel harus dideklarasikan di Variables (sudah)
            CLevel.setSelectedIndex(0);
        }
    }

    private void bersih() {
        IDOtomatis();
        if (CLevel.getItemCount() > 0) {
            CLevel.setSelectedIndex(0);
        }
        if (INamaUser != null) INamaUser.setText(""); // Periksa null jika komponen belum diinisialisasi penuh
        if (Gjk != null) Gjk.clearSelection(); // Gjk harus dideklarasikan di Variables (sudah)
        if (INope != null) INope.setText("");   // INope harus dideklarasikan di Variables (sudah)
        if (IUser != null) IUser.setText("");   // IUser harus dideklarasikan di Variables (sudah)
        if (IPassword != null) IPassword.setText(""); // IPassword harus dideklarasikan di Variables (sudah)
        if (INamaUser != null) INamaUser.requestFocus();

        if (BSimpan != null) BSimpan.setEnabled(true);
        if (BEdit != null) BEdit.setEnabled(false);
        if (BHapus != null) BHapus.setEnabled(false);
        if (IIdUser != null) IIdUser.setEditable(false);
    }

    private int getIdLevelByName(String levelName) {
        if (koneksi.con == null) return -1; // atau 0 tergantung cara Anda handle error
        String sql = "SELECT id_level FROM tbl_level WHERE level = ?";
        try (PreparedStatement ps = koneksi.con.prepareStatement(sql)) {
            ps.setString(1, levelName);
            try (ResultSet rsLevel = ps.executeQuery()) {
                if (rsLevel.next()) {
                    return rsLevel.getInt("id_level");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mendapatkan ID Level: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return 0; // Indikasi tidak ditemukan atau error
    }

    private void EditData() {
        String namaUserVal = INamaUser.getText();
        String jk = "";
        if (JkL.isSelected()) { // JkL, JkP harus dideklarasikan di Variables (sudah)
            jk = "L";
        } else if (JkP.isSelected()) {
            jk = "P";
        }
        String nopeVal = INope.getText();
        String usernameVal = IUser.getText();
        String passwordVal = new String(IPassword.getPassword());

        if (namaUserVal.isEmpty() || jk.isEmpty() || nopeVal.isEmpty() || usernameVal.isEmpty() || passwordVal.isEmpty() || CLevel.getSelectedIndex() == -1) {
             JOptionPane.showMessageDialog(this, "Data Harus Diisi !!", "Peringatan", JOptionPane.WARNING_MESSAGE);
             return;
        }

        int konfirmasiEdit = JOptionPane.showConfirmDialog(this,
                "Apakah Data Akan Di Edit?",
                "Edit Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (konfirmasiEdit == JOptionPane.YES_OPTION) {
            if (koneksi.con == null) { JOptionPane.showMessageDialog(this, "Koneksi Database Gagal!"); return; }
            try {
                int idLevel = getIdLevelByName(CLevel.getSelectedItem().toString());
                if (idLevel <= 0) {
                    JOptionPane.showMessageDialog(this, "Level tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String idUserVal = IIdUser.getText();

                String checkSql = "SELECT COUNT(*) FROM tbl_user WHERE username = ? AND id_user != ?";
                try (PreparedStatement checkPs = koneksi.con.prepareStatement(checkSql)) {
                    checkPs.setString(1, usernameVal);
                    checkPs.setString(2, idUserVal);
                    try (ResultSet checkRs = checkPs.executeQuery()) {
                        if(checkRs.next() && checkRs.getInt(1) > 0){
                            JOptionPane.showMessageDialog(this, "Username '" + usernameVal + "' sudah digunakan oleh user lain!", "Error Duplikasi", JOptionPane.ERROR_MESSAGE);
                            IUser.requestFocus();
                            return;
                        }
                    }
                }

                String sql_edit = "UPDATE tbl_user SET id_level = ?, nama_user = ?, jk = ?, "
                                + "nope = ?, username = ?, password = ? WHERE id_user = ?";
                try (PreparedStatement ps = koneksi.con.prepareStatement(sql_edit)) {
                    ps.setInt(1, idLevel);
                    ps.setString(2, namaUserVal);
                    ps.setString(3, jk);
                    ps.setString(4, nopeVal);
                    ps.setString(5, usernameVal);
                    ps.setString(6, passwordVal);
                    ps.setString(7, idUserVal);

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil Di Update");
                        load_data();
                        bersih();
                    } else {
                        JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau tidak ada perubahan.");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error saat update data: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void HapusData() {
        int konfirmasiHapus = JOptionPane.showConfirmDialog(this,
                "Apakah Data Akan Di Hapus?",
                "Hapus Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (konfirmasiHapus == JOptionPane.YES_OPTION) {
            if (koneksi.con == null) { JOptionPane.showMessageDialog(this, "Koneksi Database Gagal!"); return; }
            try {
                String sql_delete = "DELETE FROM tbl_user WHERE id_user = ?";
                try (PreparedStatement ps = koneksi.con.prepareStatement(sql_delete)) {
                    ps.setString(1, IIdUser.getText());
                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil Di Hapus");
                        load_data();
                        bersih();
                    } else {
                         JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error saat hapus data: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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
        jButton4 = new javax.swing.JButton();

        Gjk.add(JkL);
        Gjk.add(JkP);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        JkL.setText("L");
        JkL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JkLActionPerformed(evt);
            }
        });

        JkP.setText("P");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel9.setText("DATA USER APLIKASI KASIR");

        TUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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

        jButton4.setText("REPORT DATA USER");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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
                            .addComponent(jLabel2)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(320, 320, 320)
                .addComponent(jLabel9)
                .addGap(24, 24, 24))
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BSimpan)
                        .addComponent(jButton4))
                    .addComponent(BEdit)
                    .addComponent(BHapus))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JkLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JkLActionPerformed
        // Tidak ada aksi spesifik yang perlu dilakukan di sini biasanya
    }//GEN-LAST:event_JkLActionPerformed

    private void BSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSimpanActionPerformed
        if (koneksi.con == null) { JOptionPane.showMessageDialog(this, "Koneksi Database Gagal!"); return; }

        String idUserVal = IIdUser.getText();
        String levelName = CLevel.getSelectedItem().toString();
        String namaUserVal = INamaUser.getText();
        String jk = "";
        if (JkL.isSelected()) {
            jk = "L";
        } else if (JkP.isSelected()) {
            jk = "P";
        }
        String nopeVal = INope.getText();
        String usernameVal = IUser.getText();
        String passwordVal = new String(IPassword.getPassword());

        if (namaUserVal.isEmpty() || jk.isEmpty() || nopeVal.isEmpty() || usernameVal.isEmpty() || passwordVal.isEmpty() || CLevel.getSelectedIndex() == -1 || (CLevel.getSelectedItem() != null && CLevel.getSelectedItem().toString().equals("- Pilih Level -")) ) {
            JOptionPane.showMessageDialog(this, "Data Harus Diisi !!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

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
                // Cek duplikasi ID User sebelum insert (karena ID Otomatis bisa saja bentrok jika ada input manual atau error)
                String checkIdSql = "SELECT COUNT(*) FROM tbl_user WHERE id_user = ?";
                try (PreparedStatement checkIdPs = koneksi.con.prepareStatement(checkIdSql)) {
                    checkIdPs.setString(1, idUserVal);
                    try (ResultSet rsId = checkIdPs.executeQuery()) {
                        if (rsId.next() && rsId.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(this, "ID User '" + idUserVal + "' sudah digunakan!", "Error Duplikasi", JOptionPane.ERROR_MESSAGE);
                            IIdUser.requestFocus(); // Atau panggil IDOtomatis() lagi untuk generate ID baru
                            return;
                        }
                    }
                }


                String checkUserSql = "SELECT COUNT(*) FROM tbl_user WHERE username = ?";
                try (PreparedStatement checkUserPs = koneksi.con.prepareStatement(checkUserSql)) {
                    checkUserPs.setString(1, usernameVal);
                    try (ResultSet rsUser = checkUserPs.executeQuery()) {
                        if (rsUser.next() && rsUser.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(this, "Username '" + usernameVal + "' sudah digunakan!", "Error Duplikasi", JOptionPane.ERROR_MESSAGE);
                            IUser.requestFocus();
                            return;
                        }
                    }
                }


                String sql = "INSERT INTO tbl_user (id_user, id_level, nama_user, jk, NOPE, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = koneksi.con.prepareStatement(sql)) {
                    ps.setString(1, idUserVal);
                    ps.setInt(2, idLevel);
                    ps.setString(3, namaUserVal);
                    ps.setString(4, jk);
                    ps.setString(5, nopeVal);
                    ps.setString(6, usernameVal);
                    ps.setString(7, passwordVal);

                    int rowsInserted = ps.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "Data User Berhasil Dimasukan");
                        load_data();
                        bersih();
                    }
                }
            } catch (SQLException e) {
                // Error SQLState 23xxx biasanya untuk integrity constraint violation (mis. PK duplicate)
                if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                    JOptionPane.showMessageDialog(this, "ID User atau Username mungkin sudah ada, atau terjadi kesalahan integritas data.\nDetail: " + e.getMessage(), "Error Simpan", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error saat menyimpan data: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
                }
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_BSimpanActionPerformed

    private void BEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEditActionPerformed
        EditData();
    }//GEN-LAST:event_BEditActionPerformed

    private void BHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BHapusActionPerformed
        HapusData();
    }//GEN-LAST:event_BHapusActionPerformed

    private void TUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TUserMouseClicked
        int bar = TUser.getSelectedRow();
        if (bar != -1) { // Pastikan ada baris yang terpilih
            // Ambil data dari model tabel untuk konsistensi
            DefaultTableModel model = (DefaultTableModel)TUser.getModel();
            IIdUser.setText(model.getValueAt(bar, 0).toString());

            String levelDiTabel = model.getValueAt(bar, 1).toString();
            for (int i = 0; i < CLevel.getItemCount(); i++) {
                if (CLevel.getItemAt(i).toString().equalsIgnoreCase(levelDiTabel)) {
                    CLevel.setSelectedIndex(i);
                    break;
                }
            }
            INamaUser.setText(model.getValueAt(bar, 2).toString());

            String jenisKelamin = model.getValueAt(bar, 3).toString();
            if ("L".equalsIgnoreCase(jenisKelamin)) {
                JkL.setSelected(true);
            } else if ("P".equalsIgnoreCase(jenisKelamin)) {
                JkP.setSelected(true);
            } else {
                Gjk.clearSelection();
            }

            INope.setText(model.getValueAt(bar, 4).toString());
            IUser.setText(model.getValueAt(bar, 5).toString());
            IPassword.setText(model.getValueAt(bar, 6).toString());

            BSimpan.setEnabled(false);
            BEdit.setEnabled(true);
            BHapus.setEnabled(true);
            IIdUser.setEditable(false);
        }
    }//GEN-LAST:event_TUserMouseClicked

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Cek Koneksi
        if (koneksi == null || koneksi.con == null) {
            JOptionPane.showMessageDialog(this, "Objek Koneksi belum diinisialisasi atau koneksi ke database gagal! Tidak dapat mencetak report.", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
            System.err.println("jButton4ActionPerformed: Koneksi.con is null.");
            return;
        }
        try {
            // Cek status koneksi sekali lagi untuk memastikan
            if (koneksi.con.isClosed()) {
                 JOptionPane.showMessageDialog(this, "Koneksi ke database tertutup! Tidak dapat mencetak report.", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
                 System.err.println("jButton4ActionPerformed: Koneksi.con is closed.");
                 return;
            }
            System.out.println("jButton4ActionPerformed: Koneksi.con status valid.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat memeriksa status koneksi: " + e.getMessage(), "Error Koneksi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        // 2. Memuat File Laporan (.jrxml)
        InputStream reportStream = null;
        String namaFileLaporan = "/jdbc/LaporanUserKasir.jrxml"; // Path resource relatif terhadap classpath root

        try {
            System.out.println("Mencoba memuat laporan dari: " + namaFileLaporan);
            reportStream = getClass().getResourceAsStream(namaFileLaporan);

            if (reportStream == null) {
                // Jika gagal sebagai resource, coba sebagai file (kurang direkomendasikan untuk deployment)
                System.err.println("Gagal memuat laporan sebagai resource stream. Mencoba sebagai file...");
                File fileReport = new File("src/jdbc/LaporanUserKasir.jrxml"); // Path relatif terhadap root proyek
                if (!fileReport.exists()) {
                    JOptionPane.showMessageDialog(this, "File laporan LaporanUserKasir.jrxml tidak ditemukan di:\n" + fileReport.getAbsolutePath() + "\nAtaupun sebagai resource: " + namaFileLaporan, "Error File Laporan", JOptionPane.ERROR_MESSAGE);
                    System.err.println("File laporan tidak ditemukan di path file: " + fileReport.getAbsolutePath() + " maupun sebagai resource.");
                    return;
                }
                System.out.println("Berhasil menemukan file laporan di: " + fileReport.getAbsolutePath());
                JasDes = JRXmlLoader.load(fileReport.getAbsolutePath());
            } else {
                System.out.println("Berhasil memuat laporan sebagai resource stream.");
                JasDes = JRXmlLoader.load(reportStream);
            }

            // 3. Mengkompilasi dan Mengisi Laporan
            System.out.println("Mengompilasi laporan...");
            param.clear(); // Bersihkan parameter
            JasRep = JasperCompileManager.compileReport(JasDes);
            System.out.println("Laporan berhasil dikompilasi. Mengisi laporan...");
            JasPri = JasperFillManager.fillReport(JasRep, param, koneksi.con);
            System.out.println("Laporan berhasil diisi.");

            // 4. Menampilkan Laporan
            if (JasPri.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Laporan berhasil dibuat, tetapi tidak ada data yang sesuai dengan kriteria (Level Kasir).", "Informasi Laporan", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Laporan kosong (tidak ada data kasir).");
                // Tetap tampilkan viewer agar pengguna tahu proses berhasil tapi data kosong
            }

            System.out.println("Menampilkan JasperViewer...");
            JasperViewer viewer = new JasperViewer(JasPri, false);
            viewer.setTitle("Laporan Data Pengguna (Kasir)");
            viewer.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Pastikan viewer bisa ditutup tanpa menutup aplikasi utama
            viewer.setVisible(true);
            System.out.println("JasperViewer seharusnya sudah tampil.");

        } catch (JRException e) {
            JOptionPane.showMessageDialog(this, "Gagal memproses laporan JasperReports: " + e.getMessage(), "Error Laporan JRE", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan umum saat membuat laporan: " + e.getMessage(), "Error Umum", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (reportStream != null) {
                try {
                    reportStream.close();
                    System.out.println("Resource stream laporan ditutup.");
                } catch (IOException e) {
                    System.err.println("Error menutup resource stream laporan:");
                    e.printStackTrace();
                }
            }
        }
    }
                              

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
    private javax.swing.JComboBox<String> CLevel;
    private javax.swing.ButtonGroup Gjk;
    private javax.swing.JTextField IIdUser;
    private javax.swing.JTextField INope;
    private javax.swing.JPasswordField IPassword;
    private javax.swing.JTextField IUser;
    private javax.swing.JRadioButton JkL;
    private javax.swing.JRadioButton JkP;
    private javax.swing.JTable TUser;
    private javax.swing.JButton jButton4;
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
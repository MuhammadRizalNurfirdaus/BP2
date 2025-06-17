package app.view;

// Import yang dibutuhkan
import app.koneksi.Koneksi;
import app.model.User;
import java.io.InputStream;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

// Import untuk semua form internal
import app.view.FormBarang;
import app.view.FormSupplier;
import app.view.FormUser;
import app.view.FormPembelian;
import app.view.FormPenjualan;

public class FormMenuUtama extends javax.swing.JFrame {

    User user;

    public FormMenuUtama(User user) {
        initComponents();
        this.user = user;
        this.setExtendedState(MAXIMIZED_BOTH);

        this.setTitle("Aplikasi Penjualan - Selamat Datang, " + user.getNama_user());
        lblInfoUser.setText("User: " + user.getNama_user() + " | Level: " + user.getLevel());

        aturHakAkses();
    }

    private void aturHakAkses() {
        String level = user.getLevel();

        switch (level) {
            case "Kasir":
                menuDataMaster.setEnabled(false);
                menuItemPembelian.setEnabled(false);
                menuItemLaporanPembelian.setEnabled(false);
                menuItemLaporanStok.setEnabled(false);
                break;
            case "Gudang":
                menuItemDataUser.setEnabled(false);
                menuItemPenjualan.setEnabled(false);
                menuItemLaporanPenjualan.setEnabled(false);
                break;
            default: // Admin
                // Semua menu aktif
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        lblInfoUser = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemLogout = new javax.swing.JMenuItem();
        menuItemExit = new javax.swing.JMenuItem();
        menuDataMaster = new javax.swing.JMenu();
        menuItemDataUser = new javax.swing.JMenuItem();
        menuItemDataBarang = new javax.swing.JMenuItem();
        menuItemDataSupplier = new javax.swing.JMenuItem();
        menuTransaksi = new javax.swing.JMenu();
        menuItemPenjualan = new javax.swing.JMenuItem();
        menuItemPembelian = new javax.swing.JMenuItem();
        menuLaporan = new javax.swing.JMenu();
        menuItemLaporanPenjualan = new javax.swing.JMenuItem();
        menuItemLaporanPembelian = new javax.swing.JMenuItem();
        menuItemLaporanStok = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi Penjualan");

        lblInfoUser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblInfoUser.setText("User: Nama User | Level: Admin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblInfoUser, javax.swing.GroupLayout.PREFERRED_SIZE, 388,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(402, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblInfoUser)
                                .addContainerGap()));

        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        jDesktopPane1Layout.setVerticalGroup(
                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                jDesktopPane1Layout.createSequentialGroup()
                                        .addContainerGap(501, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)));

        menuFile.setText("File");

        menuItemLogout.setText("Logout");
        menuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLogoutActionPerformed(evt);
            }
        });
        menuFile.add(menuItemLogout);

        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemExit);

        jMenuBar1.add(menuFile);

        menuDataMaster.setText("Data Master");

        menuItemDataUser.setText("Data User");
        menuItemDataUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDataUserActionPerformed(evt);
            }
        });
        menuDataMaster.add(menuItemDataUser);

        menuItemDataBarang.setText("Data Barang");
        menuItemDataBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDataBarangActionPerformed(evt);
            }
        });
        menuDataMaster.add(menuItemDataBarang);

        menuItemDataSupplier.setText("Data Supplier");
        menuItemDataSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDataSupplierActionPerformed(evt);
            }
        });
        menuDataMaster.add(menuItemDataSupplier);

        jMenuBar1.add(menuDataMaster);

        menuTransaksi.setText("Transaksi");

        menuItemPenjualan.setText("Penjualan");
        menuItemPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPenjualanActionPerformed(evt);
            }
        });
        menuTransaksi.add(menuItemPenjualan);

        menuItemPembelian.setText("Pembelian");
        menuItemPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPembelianActionPerformed(evt);
            }
        });
        menuTransaksi.add(menuItemPembelian);

        jMenuBar1.add(menuTransaksi);

        menuLaporan.setText("Laporan");

        menuItemLaporanPenjualan.setText("Laporan Penjualan");
        menuItemLaporanPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLaporanPenjualanActionPerformed(evt);
            }
        });
        menuLaporan.add(menuItemLaporanPenjualan);

        menuItemLaporanPembelian.setText("Laporan Pembelian");
        menuItemLaporanPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLaporanPembelianActionPerformed(evt);
            }
        });
        menuLaporan.add(menuItemLaporanPembelian);

        menuItemLaporanStok.setText("Laporan Stok Barang");
        menuItemLaporanStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLaporanStokActionPerformed(evt);
            }
        });
        menuLaporan.add(menuItemLaporanStok);

        jMenuBar1.add(menuLaporan);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jDesktopPane1));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jDesktopPane1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemLogoutActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin logout?", "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            new FormLogin().setVisible(true);
            this.dispose();
        }
    }// GEN-LAST:event_menuItemLogoutActionPerformed

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemExitActionPerformed
        System.exit(0);
    }// GEN-LAST:event_menuItemExitActionPerformed

    private void menuItemDataBarangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemDataBarangActionPerformed
        FormBarang formBarang = new FormBarang();
        jDesktopPane1.add(formBarang);
        formBarang.setVisible(true);
    }// GEN-LAST:event_menuItemDataBarangActionPerformed

    private void menuItemDataUserActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemDataUserActionPerformed
        FormUser formUser = new FormUser();
        jDesktopPane1.add(formUser);
        formUser.setVisible(true);
    }// GEN-LAST:event_menuItemDataUserActionPerformed

    private void menuItemDataSupplierActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemDataSupplierActionPerformed
        FormSupplier formSupplier = new FormSupplier();
        jDesktopPane1.add(formSupplier);
        formSupplier.setVisible(true);
    }// GEN-LAST:event_menuItemDataSupplierActionPerformed

    private void menuItemPembelianActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemPembelianActionPerformed
        FormPembelian formBeli = new FormPembelian(this.user);
        jDesktopPane1.add(formBeli);
        formBeli.setVisible(true);
    }// GEN-LAST:event_menuItemPembelianActionPerformed

    private void menuItemLaporanPembelianActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemLaporanPembelianActionPerformed
        try {
            InputStream reportStream = getClass().getResourceAsStream("/app/report/report_pembelian.jrxml");
            if (reportStream == null) {
                JOptionPane.showMessageDialog(this, "File laporan tidak ditemukan: /app/report/report_pembelian.jrxml",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("USER_CETAK", this.user.getNama_user());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, Koneksi.connection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan pembelian: " + e.getMessage(),
                    "Error Laporan", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_menuItemLaporanPembelianActionPerformed

    private void menuItemPenjualanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemPenjualanActionPerformed
        FormPenjualan formJual = new FormPenjualan(this.user);
        jDesktopPane1.add(formJual);
        formJual.setVisible(true);
    }// GEN-LAST:event_menuItemPenjualanActionPerformed

    private void menuItemLaporanStokActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemLaporanStokActionPerformed
        try {
            InputStream reportStream = getClass().getResourceAsStream("/app/report/report_barang.jrxml");
            if (reportStream == null) {
                JOptionPane.showMessageDialog(this, "File laporan tidak ditemukan: /app/report/report_barang.jrxml",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            HashMap<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, Koneksi.connection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan stok barang: " + e.getMessage(),
                    "Error Laporan", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_menuItemLaporanStokActionPerformed

    private void menuItemLaporanPenjualanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_menuItemLaporanPenjualanActionPerformed
        try {
            InputStream reportStream = getClass().getResourceAsStream("/app/report/report_penjualan.jrxml");
            if (reportStream == null) {
                JOptionPane.showMessageDialog(this, "File laporan tidak ditemukan: /app/report/report_penjualan.jrxml",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("USER_CETAK", this.user.getNama_user());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, Koneksi.connection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan penjualan: " + e.getMessage(),
                    "Error Laporan", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_menuItemLaporanPenjualanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblInfoUser;
    private javax.swing.JMenu menuDataMaster;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuItemDataBarang;
    private javax.swing.JMenuItem menuItemDataSupplier;
    private javax.swing.JMenuItem menuItemDataUser;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemLaporanPembelian;
    private javax.swing.JMenuItem menuItemLaporanPenjualan;
    private javax.swing.JMenuItem menuItemLaporanStok;
    private javax.swing.JMenuItem menuItemLogout;
    private javax.swing.JMenuItem menuItemPembelian;
    private javax.swing.JMenuItem menuItemPenjualan;
    private javax.swing.JMenu menuLaporan;
    private javax.swing.JMenu menuTransaksi;
    // End of variables declaration//GEN-END:variables
}
package app.dao;

import app.koneksi.Koneksi;
import app.model.Pembelian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPembelian implements ImplementPembelian {
    Connection connection;
    final String insert = "INSERT INTO tbl_pembelian (kd_pembelian, kd_barang, id_user, tgl_pembelian, jmlh_beli, total_beli) VALUES (?,?,?,?,?,?);";
    final String updateStok = "UPDATE tbl_barang SET stok = stok + ? WHERE kd_barang = ?;";
    final String select = "SELECT p.*, b.nama_barang, u.nama_user FROM tbl_pembelian p JOIN tbl_barang b ON p.kd_barang = b.kd_barang JOIN tbl_user u ON p.id_user = u.id_user ORDER BY p.tgl_pembelian DESC;";

    public DaoPembelian() {
        connection = Koneksi.connection();
    }

    @Override
    public void insert(Pembelian p) {
        PreparedStatement statementInsert = null;
        PreparedStatement statementUpdate = null;
        try {
            // Mengatur agar kedua query dieksekusi sebagai satu transaksi
            connection.setAutoCommit(false);
            
            // 1. Insert ke tabel pembelian
            statementInsert = connection.prepareStatement(insert);
            statementInsert.setString(1, p.getKd_pembelian());
            statementInsert.setString(2, p.getKd_barang());
            statementInsert.setString(3, p.getId_user());
            statementInsert.setDate(4, new java.sql.Date(p.getTgl_pembelian().getTime()));
            statementInsert.setInt(5, p.getJmlh_beli());
            statementInsert.setLong(6, p.getTotal_beli());
            statementInsert.executeUpdate();
            
            // 2. Update stok di tabel barang
            statementUpdate = connection.prepareStatement(updateStok);
            statementUpdate.setInt(1, p.getJmlh_beli());
            statementUpdate.setString(2, p.getKd_barang());
            statementUpdate.executeUpdate();
            
            // Jika kedua query berhasil, commit transaksi
            connection.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoPembelian.class.getName()).log(Level.SEVERE, null, ex);
            try {
                // Jika salah satu query gagal, batalkan semua perubahan (rollback)
                connection.rollback();
            } catch (SQLException e) {
                Logger.getLogger(DaoPembelian.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                // Kembalikan ke mode auto-commit
                connection.setAutoCommit(true);
                if (statementInsert != null) statementInsert.close();
                if (statementUpdate != null) statementUpdate.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoPembelian.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Pembelian> getAll() {
        List<Pembelian> listPembelian = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                Pembelian p = new Pembelian();
                p.setKd_pembelian(rs.getString("kd_pembelian"));
                p.setTgl_pembelian(rs.getDate("tgl_pembelian"));
                p.setNama_barang(rs.getString("nama_barang"));
                p.setJmlh_beli(rs.getInt("jmlh_beli"));
                p.setTotal_beli(rs.getLong("total_beli"));
                p.setNama_user(rs.getString("nama_user"));
                listPembelian.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoPembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPembelian;
    }
}
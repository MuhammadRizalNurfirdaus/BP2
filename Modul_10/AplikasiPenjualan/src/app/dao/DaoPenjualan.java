package app.dao;

import app.koneksi.Koneksi;
import app.model.Penjualan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoPenjualan implements ImplementPenjualan {
    Connection connection;
    final String insert = "INSERT INTO tbl_penjualan (nota, kd_barang, id_user, tgl_penjualan, jmlh_barang_jual, total_jual) VALUES (?,?,?,?,?,?);";
    final String updateStok = "UPDATE tbl_barang SET stok = stok - ? WHERE kd_barang = ?;";
    final String select = "SELECT p.*, b.nama_barang, u.nama_user FROM tbl_penjualan p JOIN tbl_barang b ON p.kd_barang = b.kd_barang JOIN tbl_user u ON p.id_user = u.id_user ORDER BY p.tgl_penjualan DESC;";

    public DaoPenjualan() {
        connection = Koneksi.connection();
    }

    @Override
    public void insert(Penjualan p) {
        PreparedStatement statementInsert = null;
        PreparedStatement statementUpdate = null;
        try {
            connection.setAutoCommit(false);
            
            // 1. Insert ke tabel penjualan
            statementInsert = connection.prepareStatement(insert);
            statementInsert.setString(1, p.getNota());
            statementInsert.setString(2, p.getKd_barang());
            statementInsert.setString(3, p.getId_user());
            statementInsert.setDate(4, new java.sql.Date(p.getTgl_penjualan().getTime()));
            statementInsert.setInt(5, p.getJmlh_barang_jual());
            statementInsert.setLong(6, p.getTotal_jual());
            statementInsert.executeUpdate();
            
            // 2. Update (kurangi) stok di tabel barang
            statementUpdate = connection.prepareStatement(updateStok);
            statementUpdate.setInt(1, p.getJmlh_barang_jual());
            statementUpdate.setString(2, p.getKd_barang());
            statementUpdate.executeUpdate();
            
            connection.commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                Logger.getLogger(DaoPenjualan.class.getName()).log(Level.SEVERE, null, e);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                if (statementInsert != null) statementInsert.close();
                if (statementUpdate != null) statementUpdate.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoPenjualan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Penjualan> getAll() {
        List<Penjualan> listPenjualan = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                Penjualan p = new Penjualan();
                p.setNota(rs.getString("nota"));
                p.setTgl_penjualan(rs.getDate("tgl_penjualan"));
                p.setNama_barang(rs.getString("nama_barang"));
                p.setJmlh_barang_jual(rs.getInt("jmlh_barang_jual"));
                p.setTotal_jual(rs.getLong("total_jual"));
                p.setNama_user(rs.getString("nama_user"));
                listPenjualan.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPenjualan;
    }
}
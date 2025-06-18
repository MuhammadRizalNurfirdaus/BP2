package app.dao;

import app.koneksi.Koneksi;
import app.model.Barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoBarang implements ImplementBarang {
    Connection connection;
    final String insert = "INSERT INTO tbl_barang (kd_barang, nama_barang, harga_jual, harga_beli, stok) VALUES (?, ?, ?, ?, ?);";
    final String update = "UPDATE tbl_barang SET nama_barang=?, harga_jual=?, harga_beli=?, stok=? WHERE kd_barang=?;";
    final String delete = "DELETE FROM tbl_barang WHERE kd_barang=?;";
    final String select = "SELECT * FROM tbl_barang;";
    final String carinama = "SELECT * FROM tbl_barang WHERE nama_barang LIKE ?;";

    public DaoBarang() {
        connection = Koneksi.connection();
    }

    @Override
    public void insert(Barang b) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert);
            statement.setString(1, b.getKd_barang());
            statement.setString(2, b.getNama_barang());
            statement.setInt(3, b.getHarga_jual());
            statement.setInt(4, b.getHarga_beli());
            statement.setInt(5, b.getStok());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Barang b) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setString(1, b.getNama_barang());
            statement.setInt(2, b.getHarga_jual());
            statement.setInt(3, b.getHarga_beli());
            statement.setInt(4, b.getStok());
            statement.setString(5, b.getKd_barang());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(String kd_barang) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setString(1, kd_barang);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoBarang.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Barang> getAll() {
        List<Barang> listBarang = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                Barang b = new Barang();
                b.setKd_barang(rs.getString("kd_barang"));
                b.setNama_barang(rs.getString("nama_barang"));
                b.setHarga_jual(rs.getInt("harga_jual"));
                b.setHarga_beli(rs.getInt("harga_beli"));
                b.setStok(rs.getInt("stok"));
                listBarang.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listBarang;
    }

    @Override
    public List<Barang> getCari(String nama) {
        List<Barang> listBarang = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(carinama);
            st.setString(1, "%" + nama + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Barang b = new Barang();
                b.setKd_barang(rs.getString("kd_barang"));
                b.setNama_barang(rs.getString("nama_barang"));
                b.setHarga_jual(rs.getInt("harga_jual"));
                b.setHarga_beli(rs.getInt("harga_beli"));
                b.setStok(rs.getInt("stok"));
                listBarang.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listBarang;
    }
}
package app.dao;

import app.koneksi.Koneksi;
import app.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoSupplier implements ImplementSupplier {
    Connection connection;
    final String insert = "INSERT INTO tbl_supplier (kd_supplier, nama_supplier, nope_supplier, alamat) VALUES (?, ?, ?, ?);";
    final String update = "UPDATE tbl_supplier SET nama_supplier=?, nope_supplier=?, alamat=? WHERE kd_supplier=?;";
    final String delete = "DELETE FROM tbl_supplier WHERE kd_supplier=?;";
    final String select = "SELECT * FROM tbl_supplier;";
    final String carinama = "SELECT * FROM tbl_supplier WHERE nama_supplier LIKE ?;";

    public DaoSupplier() {
        connection = Koneksi.connection();
    }

    @Override
    public void insert(Supplier s) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert);
            statement.setString(1, s.getKd_supplier());
            statement.setString(2, s.getNama_supplier());
            statement.setString(3, s.getNope_supplier());
            statement.setString(4, s.getAlamat());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoSupplier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException ex) {}
        }
    }

    @Override
    public void update(Supplier s) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setString(1, s.getNama_supplier());
            statement.setString(2, s.getNope_supplier());
            statement.setString(3, s.getAlamat());
            statement.setString(4, s.getKd_supplier());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoSupplier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException ex) {}
        }
    }

    @Override
    public void delete(String kd_supplier) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setString(1, kd_supplier);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoSupplier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException ex) {}
        }
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> listSupplier = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setKd_supplier(rs.getString("kd_supplier"));
                s.setNama_supplier(rs.getString("nama_supplier"));
                s.setNope_supplier(rs.getString("nope_supplier"));
                s.setAlamat(rs.getString("alamat"));
                listSupplier.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSupplier;
    }

    @Override
    public List<Supplier> getCari(String nama) {
        List<Supplier> listSupplier = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(carinama);
            st.setString(1, "%" + nama + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setKd_supplier(rs.getString("kd_supplier"));
                s.setNama_supplier(rs.getString("nama_supplier"));
                s.setNope_supplier(rs.getString("nope_supplier"));
                s.setAlamat(rs.getString("alamat"));
                listSupplier.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSupplier;
    }
}
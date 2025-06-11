/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package anggota.dao;

import anggota.koneksi.koneksi;
import anggota.model.DataAnggota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Muhammad Rizal Nur F
 */
public class DaoAnggota implements ImplementAnggota {
    Connection connection;

    final String insert = "INSERT INTO tbl_anggota (nama, alamat, nomer) VALUES (?, ?, ?);";
    final String update = "UPDATE tbl_anggota SET nama=?, alamat=?, nomer=? WHERE id=?;";
    final String delete = "DELETE FROM tbl_anggota WHERE id=?;";
    final String select = "SELECT * FROM tbl_anggota;";
    final String carinama = "SELECT * FROM tbl_anggota WHERE nama LIKE ?;";

    public DaoAnggota() {
        connection = koneksi.connection();
    }

    @Override
    public void insert(DataAnggota b) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, b.getNama());
            statement.setString(2, b.getAlamat());
            statement.setString(3, b.getNomer());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                b.setId(rs.getInt(1));
            }
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(DataAnggota b) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setString(1, b.getNama());
            statement.setString(2, b.getAlamat());
            statement.setString(3, b.getNomer());
            statement.setInt(4, b.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<DataAnggota> getAll() {
        List<DataAnggota> lb = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                DataAnggota b = new DataAnggota();
                b.setId(rs.getInt("id"));
                b.setNama(rs.getString("nama"));
                b.setAlamat(rs.getString("alamat"));
                b.setNomer(rs.getString("nomer"));
                lb.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoAnggota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lb;
    }

    @Override
    public List<DataAnggota> getCariNama(String nama) {
        List<DataAnggota> lb = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(carinama);
            st.setString(1, "%" + nama + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                DataAnggota b = new DataAnggota();
                b.setId(rs.getInt("id"));
                b.setNama(rs.getString("nama"));
                b.setAlamat(rs.getString("alamat"));
                b.setNomer(rs.getString("nomer"));
                lb.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoAnggota.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lb;
    }
}
package app.dao;

import app.koneksi.Koneksi;
import app.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUser implements ImplementUser {
    Connection connection;
    // Query untuk mengambil data user beserta nama levelnya menggunakan JOIN
    final String checkLogin = "SELECT user.*, level.level FROM tbl_user user JOIN tbl_level level ON user.id_level = level.id_level WHERE user.username=? AND user.password=?";
    final String register = "INSERT INTO tbl_user (id_user, id_level, nama_user, jk, nope, username, password) VALUES (?, ?, ?, ?, ?, ?, ?);";
    final String checkUsername = "SELECT * FROM tbl_user WHERE username = ?;";
    final String insert = "INSERT INTO tbl_user (id_user, id_level, nama_user, jk, nope, username, password) VALUES (?,?,?,?,?,?,?);";
    final String update = "UPDATE tbl_user SET id_level=?, nama_user=?, jk=?, nope=?, username=?, password=? WHERE id_user=?;";
    final String delete = "DELETE FROM tbl_user WHERE id_user=?;";
    final String select = "SELECT user.*, level.level FROM tbl_user user JOIN tbl_level level ON user.id_level = level.id_level;";
    final String carinama = "SELECT user.*, level.level FROM tbl_user user JOIN tbl_level level ON user.id_level = level.id_level WHERE user.nama_user LIKE ?;";
    public DaoUser() {
        connection = Koneksi.connection();
    }
    
    @Override
    public User checkLogin(String username, String password) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        User user = null; // Inisialisasi user sebagai null
        
        try {
            statement = connection.prepareStatement(checkLogin);
            statement.setString(1, username);
            statement.setString(2, password);
            rs = statement.executeQuery();

            // Jika query menghasilkan satu baris, artinya user ditemukan
            if (rs.next()) {
                user = new User(); // Buat objek User baru
                user.setId_user(rs.getString("id_user"));
                user.setNama_user(rs.getString("nama_user"));
                user.setUsername(rs.getString("username"));
                user.setId_level(rs.getInt("id_level"));
                user.setLevel(rs.getString("level")); // Ambil nama level dari hasil JOIN
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "SQL Error", ex);
        } finally {
            // Selalu tutup ResultSet dan PreparedStatement untuk menghindari memory leak
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }
        return user; // Kembalikan objek User (atau null jika tidak ditemukan)
    }
      
    public void register(User u) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(register);
            statement.setString(1, u.getId_user());
            statement.setInt(2, u.getId_level()); // Default level untuk registrasi
            statement.setString(3, u.getNama_user());
            statement.setString(4, u.getJk());
            statement.setString(5, u.getNope());
            statement.setString(6, u.getUsername());
            statement.setString(7, u.getPassword());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   
    public boolean isUsernameExists(String username) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(checkUsername);
            statement.setString(1, username);
            rs = statement.executeQuery();
            return rs.next(); // Jika ada baris, return true
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                // handle exception
            }
        }
    }
  
    @Override
    public void insert(User u) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert);
            statement.setString(1, u.getId_user());
            statement.setInt(2, u.getId_level());
            statement.setString(3, u.getNama_user());
            statement.setString(4, u.getJk());
            statement.setString(5, u.getNope());
            statement.setString(6, u.getUsername());
            statement.setString(7, u.getPassword());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException ex) {}
        }
    }

    @Override
    public void update(User u) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setInt(1, u.getId_level());
            statement.setString(2, u.getNama_user());
            statement.setString(3, u.getJk());
            statement.setString(4, u.getNope());
            statement.setString(5, u.getUsername());
            statement.setString(6, u.getPassword());
            statement.setString(7, u.getId_user());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException ex) {}
        }
    }

    @Override
    public void delete(String id_user) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setString(1, id_user);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException ex) {}
        }
    }

    @Override
    public List<User> getAll() {
        List<User> listUser = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                User u = new User();
                u.setId_user(rs.getString("id_user"));
                u.setNama_user(rs.getString("nama_user"));
                u.setJk(rs.getString("jk"));
                u.setNope(rs.getString("nope"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setId_level(rs.getInt("id_level"));
                u.setLevel(rs.getString("level")); // Ambil nama level dari JOIN
                listUser.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUser;
    }

    @Override
    public List<User> getCari(String nama) {
        List<User> listUser = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(carinama);
            st.setString(1, "%" + nama + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId_user(rs.getString("id_user"));
                u.setNama_user(rs.getString("nama_user"));
                u.setJk(rs.getString("jk"));
                u.setNope(rs.getString("nope"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setId_level(rs.getInt("id_level"));
                u.setLevel(rs.getString("level")); // Ambil nama level dari JOIN
                listUser.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUser;
    }
}
package app.koneksi;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Koneksi {
    static Connection con;

    public static Connection connection() {
        if (con == null) {
            MysqlDataSource data = new MysqlDataSource();
            data.setServerName("localhost"); // Terhubung ke komputer sendiri
            data.setPortNumber(3306); // Port yang diekspos oleh Docker
            data.setDatabaseName("db_penjualan_toko"); // Nama DB dari init.sql
            data.setUser("root"); // User dari docker-compose.yml
            data.setPassword("root"); // Password dari docker-compose.yml

            try {
                con = data.getConnection();
                System.out.println("Koneksi berhasil!");
            } catch (SQLException e) {
                System.err.println("Koneksi Gagal! Error: " + e.getMessage());
                e.printStackTrace(); // Cetak detail error untuk debugging
            }
        }
        return con;
    }
}
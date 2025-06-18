package app.model;

import java.util.Date;

public class Penjualan {
    private String nota;
    private String kd_barang;
    private String id_user;
    private Date tgl_penjualan;
    private int jmlh_barang_jual;
    private long total_jual;

    // Field tambahan untuk ditampilkan di tabel
    private String nama_barang;
    private String nama_user;

    // --- Getters and Setters ---
    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }
    public String getKd_barang() { return kd_barang; }
    public void setKd_barang(String kd_barang) { this.kd_barang = kd_barang; }
    public String getId_user() { return id_user; }
    public void setId_user(String id_user) { this.id_user = id_user; }
    public Date getTgl_penjualan() { return tgl_penjualan; }
    public void setTgl_penjualan(Date tgl_penjualan) { this.tgl_penjualan = tgl_penjualan; }
    public int getJmlh_barang_jual() { return jmlh_barang_jual; }
    public void setJmlh_barang_jual(int jmlh_barang_jual) { this.jmlh_barang_jual = jmlh_barang_jual; }
    public long getTotal_jual() { return total_jual; }
    public void setTotal_jual(long total_jual) { this.total_jual = total_jual; }
    public String getNama_barang() { return nama_barang; }
    public void setNama_barang(String nama_barang) { this.nama_barang = nama_barang; }
    public String getNama_user() { return nama_user; }
    public void setNama_user(String nama_user) { this.nama_user = nama_user; }
}
package app.model;

import java.util.Date;

public class Pembelian {
    private String kd_pembelian;
    private String kd_barang;
    private String id_user;
    private Date tgl_pembelian;
    private int jmlh_beli;
    private long total_beli;
    
    // Field tambahan untuk ditampilkan di tabel
    private String nama_barang;
    private String nama_user;

    // --- Getters and Setters ---
    public String getKd_pembelian() { return kd_pembelian; }
    public void setKd_pembelian(String kd_pembelian) { this.kd_pembelian = kd_pembelian; }
    public String getKd_barang() { return kd_barang; }
    public void setKd_barang(String kd_barang) { this.kd_barang = kd_barang; }
    public String getId_user() { return id_user; }
    public void setId_user(String id_user) { this.id_user = id_user; }
    public Date getTgl_pembelian() { return tgl_pembelian; }
    public void setTgl_pembelian(Date tgl_pembelian) { this.tgl_pembelian = tgl_pembelian; }
    public int getJmlh_beli() { return jmlh_beli; }
    public void setJmlh_beli(int jmlh_beli) { this.jmlh_beli = jmlh_beli; }
    public long getTotal_beli() { return total_beli; }
    public void setTotal_beli(long total_beli) { this.total_beli = total_beli; }
    public String getNama_barang() { return nama_barang; }
    public void setNama_barang(String nama_barang) { this.nama_barang = nama_barang; }
    public String getNama_user() { return nama_user; }
    public void setNama_user(String nama_user) { this.nama_user = nama_user; }
}
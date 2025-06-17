package app.model;

public class Barang {
    private String kd_barang;
    private String nama_barang;
    private int harga_jual;
    private int harga_beli;
    private int stok;

    // --- Getters and Setters ---
    public String getKd_barang() { return kd_barang; }
    public void setKd_barang(String kd_barang) { this.kd_barang = kd_barang; }
    public String getNama_barang() { return nama_barang; }
    public void setNama_barang(String nama_barang) { this.nama_barang = nama_barang; }
    public int getHarga_jual() { return harga_jual; }
    public void setHarga_jual(int harga_jual) { this.harga_jual = harga_jual; }
    public int getHarga_beli() { return harga_beli; }
    public void setHarga_beli(int harga_beli) { this.harga_beli = harga_beli; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}
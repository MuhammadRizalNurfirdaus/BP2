package app.dao;

import app.model.Pembelian;
import java.util.List;

public interface ImplementPembelian {
    // Metode ini akan menangani INSERT ke tbl_pembelian dan UPDATE stok di tbl_barang
    public void insert(Pembelian p);
    public List<Pembelian> getAll();
}
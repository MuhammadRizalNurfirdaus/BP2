package app.dao;

import app.model.Penjualan;
import java.util.List;

public interface ImplementPenjualan {
    // Metode ini akan menangani INSERT ke tbl_penjualan dan UPDATE stok di tbl_barang
    public void insert(Penjualan p);
    public List<Penjualan> getAll();
}
package app.dao;

import app.model.Barang;
import java.util.List;

public interface ImplementBarang {
    public void insert(Barang b);
    public void update(Barang b);
    public void delete(String kd_barang);
    public List<Barang> getAll();
    public List<Barang> getCari(String nama);
}
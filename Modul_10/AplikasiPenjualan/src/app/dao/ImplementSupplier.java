package app.dao;

import app.model.Supplier;
import java.util.List;

public interface ImplementSupplier {
    public void insert(Supplier s);
    public void update(Supplier s);
    public void delete(String kd_supplier);
    public List<Supplier> getAll();
    public List<Supplier> getCari(String nama);
}
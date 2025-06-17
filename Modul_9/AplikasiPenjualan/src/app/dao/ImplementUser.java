package app.dao;

import app.model.User;
import java.util.List;

public interface ImplementUser {
    // Metode yang sudah ada
    public User checkLogin(String username, String password);
    public void register(User u);
    public boolean isUsernameExists(String username);
    
    // ===========================================
    // == TAMBAHKAN METODE CRUD DI BAWAH INI ======
    // ===========================================
    public void insert(User u);
    public void update(User u);
    public void delete(String id_user);
    public List<User> getAll();
    public List<User> getCari(String nama);
}
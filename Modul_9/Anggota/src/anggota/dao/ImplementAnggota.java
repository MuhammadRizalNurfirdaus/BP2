/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package anggota.dao;

import anggota.model.DataAnggota;
import java.util.List;
/**
 *
 * @author Muhammad Rizal Nur F
 */
public interface ImplementAnggota {
    public void insert(DataAnggota b);
    public void update(DataAnggota b);
    public void delete(int id);
    public List<DataAnggota> getAll(); 
    public List<DataAnggota> getCariNama(String nama);
}
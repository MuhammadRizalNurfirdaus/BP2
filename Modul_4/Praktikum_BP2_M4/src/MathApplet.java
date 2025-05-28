import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Muhammad Rizal Nurfirdaus
 */
public class MathApplet extends Applet implements ActionListener {
    TextField num1, num2, hasil;
    Button tombolTambah, tombolKurang, tombolKali, tombolBagi;

    @Override
    public void init() {
        // Atur tata letak untuk applet
        setLayout(new GridLayout(5, 2, 10, 10));
        
        // Buat kolom input
        num1 = new TextField();
        num2 = new TextField();
        hasil = new TextField();
        hasil.setEditable(false);
        
        // Buat tombol untuk setiap operasi
        tombolTambah = new Button("Tambah");
        tombolKurang = new Button("Kurang");
        tombolKali = new Button("Kali");
        tombolBagi = new Button("Bagi");
        
        // Tambahkan action listener ke tombol
        tombolTambah.addActionListener(this);
        tombolKurang.addActionListener(this);
        tombolKali.addActionListener(this);
        tombolBagi.addActionListener(this);
        
        // Tambahkan komponen ke applet
        add(new Label("Angka 1:"));
        add(num1);
        add(new Label("Angka 2:"));
        add(num2);
        add(tombolTambah);
        add(tombolKurang);
        add(tombolKali);
        add(tombolBagi);
        add(new Label("Hasil:"));
        add(hasil);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double angka1 = Double.parseDouble(num1.getText());
            double angka2 = Double.parseDouble(num2.getText());
            double hasilPerhitungan = 0;
            if (e.getSource() == tombolTambah) {
                hasilPerhitungan = angka1 + angka2;
            } else if (e.getSource() == tombolKurang) {
                hasilPerhitungan = angka1 - angka2;
            } else if (e.getSource() == tombolKali) {
                hasilPerhitungan = angka1 * angka2;
            } else if (e.getSource() == tombolBagi) {
                if (angka2 != 0) {
                    hasilPerhitungan = angka1 / angka2;
                } else {
                    hasil.setText("Error: Tidak bisa membagi dengan nol!");
                    return;
                }
            }
            hasil.setText(String.valueOf(hasilPerhitungan));
        } catch (NumberFormatException ex) {
            hasil.setText("Error: Masukkan angka yang valid!");
        }
    }
}

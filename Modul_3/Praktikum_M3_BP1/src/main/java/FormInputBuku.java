import javax.swing.*;
import java.awt.event.*;

public class FormInputBuku extends JFrame {
    private JTextField tfJudul, tfPengarang;
    private JComboBox<String> cbKategori;
    private JTextArea taDeskripsi;
    private JButton btnSimpan;

    public FormInputBuku() {
        setTitle("Form Input Buku Perpustakaan");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblJudul = new JLabel("Judul Buku:");
        lblJudul.setBounds(20, 20, 100, 25);
        add(lblJudul);

        tfJudul = new JTextField();
        tfJudul.setBounds(130, 20, 220, 25);
        add(tfJudul);

        JLabel lblPengarang = new JLabel("Pengarang:");
        lblPengarang.setBounds(20, 60, 100, 25);
        add(lblPengarang);

        tfPengarang = new JTextField();
        tfPengarang.setBounds(130, 60, 220, 25);
        add(tfPengarang);

        JLabel lblKategori = new JLabel("Kategori:");
        lblKategori.setBounds(20, 100, 100, 25);
        add(lblKategori);

        cbKategori = new JComboBox<>(new String[] {
            "Fiksi", "Non-Fiksi", "Komik", "Biografi", "Teknologi", "Sejarah"
        });
        cbKategori.setBounds(130, 100, 220, 25);
        add(cbKategori);

        JLabel lblDeskripsi = new JLabel("Deskripsi:");
        lblDeskripsi.setBounds(20, 140, 100, 25);
        add(lblDeskripsi);

        taDeskripsi = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(taDeskripsi);
        scrollPane.setBounds(130, 140, 220, 80);
        add(scrollPane);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(130, 240, 100, 30);
        add(btnSimpan);

        // Event tombol simpan
        btnSimpan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String judul = tfJudul.getText();
                String pengarang = tfPengarang.getText();
                String kategori = (String) cbKategori.getSelectedItem();
                String deskripsi = taDeskripsi.getText();

                String pesan = "Data Buku:\n" +
                               "Judul: " + judul + "\n" +
                               "Pengarang: " + pengarang + "\n" +
                               "Kategori: " + kategori + "\n" +
                               "Deskripsi: " + deskripsi;

                JOptionPane.showMessageDialog(null, pesan, "Data Buku Tersimpan", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FormInputBuku();
    }
}

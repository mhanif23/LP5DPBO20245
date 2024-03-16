import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        Menu window = new Menu();
        window.setSize(480, 560);
        window.setLocationRelativeTo(null);
        window.setContentPane(window.mainPanel);
        window.getContentPane().setBackground(Color.white);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JTextField jurusanField;
    private JLabel jurusanLabel;

    // constructor
    public Menu() {
        listMahasiswa = new ArrayList<>();

        // Populate the list of students
        populateList();

        // Set the model for a table of students
        mahasiswaTable.setModel(getTableModel());

        // Style the title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // Fill the combo box with gender options
        String[] jenisKelaminData = {"Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(jenisKelaminData));

        // Hide the delete button by default
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    // Insert data if no selection
                    insertData();
                } else {
                    // Update data if a selection exists
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    if (selectedIndex >= 0) { // Pastikan index valid
                        deleteData();
                    }
                }
            }
        });


        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // saat tombol
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ubah selected index menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // Simpan value dari tabel
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedJurusan = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString(); // Ambil data jurusan

                // Ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                jurusanField.setText(selectedJurusan); // Set text field jurusan

                // Ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");
                // Tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel getTableModel() {
        // tentukan kolom tabel, tambahkan "Jurusan"
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Jurusan"};

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        // isi tabel dengan listMahasiswa
        for (int i = 0; i < listMahasiswa.size(); i++) {
            Object[] row = new Object[5]; // Ada 5 kolom sekarang
            row[0] = i + 1;
            row[1] = listMahasiswa.get(i).getNim();
            row[2] = listMahasiswa.get(i).getNama();
            row[3] = listMahasiswa.get(i).getJenisKelamin();
            row[4] = listMahasiswa.get(i).getJurusan(); // Tambahkan data jurusan

            temp.addRow(row);
        }

        return temp;
    }

    public void insertData() {
        // Ambil value dari textfield dan combobox, termasuk jurusan
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String jurusan = jurusanField.getText(); // Ambil dari JTextField jurusan

        // Tambahkan data ke dalam list
        listMahasiswa.add(new Mahasiswa(nim, nama, jenisKelamin, jurusan)); // Tambahkan jurusan sebagai argumen

        // Update tabel
        mahasiswaTable.setModel(getTableModel());

        // Bersihkan form
        clearForm();

        // Feedback
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    public void updateData() {
        // Sama seperti insertData, tapi untuk objek yang sudah ada
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String jurusan = jurusanField.getText(); // Ambil dari JTextField jurusan

        // Update objek Mahasiswa yang dipilih
        Mahasiswa mhs = listMahasiswa.get(selectedIndex);
        mhs.setNim(nim);
        mhs.setNama(nama);
        mhs.setJenisKelamin(jenisKelamin);
        mhs.setJurusan(jurusan); // Set jurusan

        // Update tabel
        mahasiswaTable.setModel(getTableModel());

        // Bersihkan form
        clearForm();

        // Feedback
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    public void deleteData() {
        // hapus data dari list
        listMahasiswa.remove(selectedIndex);

        // update tabel
        mahasiswaTable.setModel(getTableModel());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua textfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", "Teknik Informatika"));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", "Teknik Elektro"));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", "Teknik Mesin"));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", "Manajemen Informatika"));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", "Akuntansi"));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", "Sistem Informasi"));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", "Psikologi"));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", "Ilmu Komunikasi"));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki", "Pendidikan Dokter"));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", "Ilmu Politik"));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", "Hukum"));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", "Desain Komunikasi Visual"));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", "Teknik Pertambangan"));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", "Teknik Industri"));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", "Biologi"));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", "Fisika"));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", "Matematika"));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", "Kimia"));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", "Teknik Lingkungan"));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", "Farmasi"));
    }

}

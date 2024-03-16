public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String jurusan; // Atribut baru

    public Mahasiswa(String nim, String nama, String jenisKelamin, String jurusan) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.jurusan = jurusan; // Inisialisasi atribut baru
    }

    // Setter baru untuk jurusan
    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    // Getter baru untuk jurusan
    public String getJurusan() {
        return this.jurusan;
    }

    // Update method setNim, setNama, dan setJenisKelamin seperti sebelumnya

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }
}

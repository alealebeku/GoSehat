package model;

public class RiwayatMedis {
    private int id;
    private int id_rawat_jalan;
    private int id_pasien;
    private String tanggal;
    private String nama_klinik;
    private String nama_dokter;
    private String diagnosa;
    private String pengobatan;
    private String perkembangan;

    public RiwayatMedis() {
    }

    public RiwayatMedis(int id, int id_pasien, int id_rawat_jalan, String tanggal, String nama_klinik, String nama_dokter, String diagnosa, String pengobatan, String perkembangan) {
        this.id = id;
        this.id_pasien = id_pasien;
        this.id_rawat_jalan = id_rawat_jalan;
        this.tanggal = tanggal;
        this.nama_klinik = nama_klinik;
        this.nama_dokter = nama_dokter;
        this.diagnosa = diagnosa;
        this.pengobatan = pengobatan;
        this.perkembangan = perkembangan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(int id_pasien) {
        this.id_pasien = id_pasien;
    }

    public int getId_rawat_jalan() {
        return id_rawat_jalan;
    }

    public void setId_rawat_jalan(int id_rawat_jalan) {
        this.id_rawat_jalan = id_rawat_jalan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama_klinik() {
        return nama_klinik;
    }

    public void setNama_klinik(String nama_klinik) {
        this.nama_klinik = nama_klinik;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getPengobatan() {
        return pengobatan;
    }

    public void setPengobatan(String pengobatan) {
        this.pengobatan = pengobatan;
    }

    public String getPerkembangan() {
        return perkembangan;
    }

    public void setPerkembangan(String perkembangan) {
        this.perkembangan = perkembangan;
    }
}

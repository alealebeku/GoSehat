package model;

import java.io.Serializable;

public class Dokter implements Serializable {
    private int id_dokter;
    private String nama_dokter;
    private int umur;
    private String alamat ;
    private String jenis_kelamin;
    private int id_spesialis;
    private String nama_spesialis;
    private int id_klinik;
    private String nama_klinik;
    private int status;


    public Dokter() {

    }

    public Dokter(int id_dokter, String nama_dokter, int umur, String alamat, String jenis_kelamin, int id_spesialis, int id_klinik, int status) {
        this.id_dokter = id_dokter;
        this.nama_dokter = nama_dokter;
        this.umur = umur;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
        this.id_spesialis = id_spesialis;
        this.id_klinik = id_klinik;
        this.status = status;
    }

    public int getId_klinik() {
        return id_klinik;
    }

    public void setId_klinik(int id_klinik) {
        this.id_klinik = id_klinik;
    }

    public String getNama_klinik() {
        return nama_klinik;
    }

    public void setNama_klinik(String nama_klinik) {
        this.nama_klinik = nama_klinik;
    }

    public String getNama_spesialis() {
        return nama_spesialis;
    }

    public void setNama_spesialis(String nama_spesialis) {
        this.nama_spesialis = nama_spesialis;
    }

    public int getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(int id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public int getId_spesialis() {
        return id_spesialis;
    }

    public void setId_spesialis(int id_spesialis) {
        this.id_spesialis = id_spesialis;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

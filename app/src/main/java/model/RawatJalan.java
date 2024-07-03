package model;

import java.io.Serializable;

public class RawatJalan implements Serializable {
    private int id;
    private int id_pasien;
    private int id_dokter;
    private int id_klinik;
    private int id_jadwal;
    private String tanggal;
    private String nomor_antrian;
    private int status;

    public RawatJalan(){
    }

    public RawatJalan(int id, int id_pasien, int id_dokter, int id_klinik, int id_jadwal, String tanggal, String nomor_antrian, int status) {
        this.id = id;
        this.id_pasien = id_pasien;
        this.id_dokter = id_dokter;
        this.id_klinik = id_klinik;
        this.id_jadwal = id_jadwal;
        this.tanggal = tanggal;
        this.nomor_antrian = nomor_antrian;
        this.status = status;
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

    public int getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(int id_dokter) {
        this.id_dokter = id_dokter;
    }

    public int getId_klinik() {
        return id_klinik;
    }

    public void setId_klinik(int id_klinik) {
        this.id_klinik = id_klinik;
    }

    public int getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(int id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNomor_antrian() {
        return nomor_antrian;
    }

    public void setNomor_antrian(String nomor_antrian) {
        this.nomor_antrian = nomor_antrian;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

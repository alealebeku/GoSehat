package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JadwalDokter implements Serializable {
    private int id_jadwal;
    private int PK_id_dokter;
    private String hari;
    private String waktu_mulai;
    private String waktu_berakhir;
    private int status;

    public JadwalDokter(int id_jadwal, int PK_id_dokter, String hari, String waktu_mulai, String waktu_berakhir, int status) {
        this.id_jadwal = id_jadwal;
        this.PK_id_dokter = PK_id_dokter;
        this.hari = hari;
        this.waktu_mulai = waktu_mulai;
        this.waktu_berakhir = waktu_berakhir;
        this.status = status;
    }

    public JadwalDokter(){}

    public int getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(int id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public int getPK_id_dokter() {
        return PK_id_dokter;
    }

    public void setPK_id_dokter(int PK_id_dokter) {
        this.PK_id_dokter = PK_id_dokter;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(String waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public String getWaktu_berakhir() {
        return waktu_berakhir;
    }

    public void setWaktu_berakhir(String waktu_berakhir) {
        this.waktu_berakhir = waktu_berakhir;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

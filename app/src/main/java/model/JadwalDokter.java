package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JadwalDokter implements Serializable {
    private int id_jadwal;
    private LocalDate tanggal;
    private LocalDateTime waktu_mulai;
    private LocalDateTime waktu_berakhir;
    private String status;

    public JadwalDokter(int id_jadwal, LocalDate tanggal, LocalDateTime waktu_mulai, LocalDateTime waktu_berakhir, String status) {
        this.id_jadwal = id_jadwal;
        this.tanggal = tanggal;
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

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public LocalDateTime getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(LocalDateTime waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public LocalDateTime getWaktu_berakhir() {
        return waktu_berakhir;
    }

    public void setWaktu_berakhir(LocalDateTime waktu_berakhir) {
        this.waktu_berakhir = waktu_berakhir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

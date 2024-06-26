package model;

import java.io.Serializable;

public class DetailDokterJadwal implements Serializable {
    private int id_jadwal;
    private int id_dokter;

    public DetailDokterJadwal(int id_jadwal, int id_dokter) {
        this.id_jadwal = id_jadwal;
        this.id_dokter = id_dokter;
    }
    public DetailDokterJadwal(){}
    public int getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(int id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public int getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(int id_dokter) {
        this.id_dokter = id_dokter;
    }
}

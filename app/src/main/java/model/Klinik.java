package model;

import java.io.Serializable;

public class Klinik  implements Serializable {
    private int id;
    private String namaklinik;
    private String alamat;
    private int status;

    public Klinik(int id, String namaklinik, String alamat, int status) {
        this.id = id;
        this.namaklinik = namaklinik;
        this.alamat = alamat;
        this.status = status;
    }
    public Klinik(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaklinik() {
        return namaklinik;
    }

    public void setNamaklinik(String namaklinik) {
        this.namaklinik = namaklinik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

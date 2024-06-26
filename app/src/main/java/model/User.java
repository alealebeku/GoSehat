package model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String nama;
    private String role;
    private String email;
    private String username;
    private String password;

    public User(int id, String nama, String role, String email, String username, String password) {
        this.id = id;
        this.nama = nama;
        this.role = role;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public User(String nama, String role, String email, String username, String password) {
        this.nama = nama;
        this.role = role;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

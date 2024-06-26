package com.example.gosehat.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardAdmin;
import com.example.gosehat.dashboard.DashboardPasien;
import com.example.gosehat.registrasi.Registrasi;

import java.util.ArrayList;

import db.DbHelper;
import model.User;

public class Login extends AppCompatActivity {
    private DbHelper dbHelper;
    private EditText uname, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dbHelper = new DbHelper(this); // Inisialisasi dbHelper
        uname = findViewById(R.id.username);
        pw = findViewById(R.id.password);

        Button login = findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uname.getText().toString().trim();
                String password = pw.getText().toString().trim();

                if (isValidUser(username, password)) {
                    String role = getUserRole(username);

                    if ("admin".equalsIgnoreCase(role)) {
                        Intent intent = new Intent(Login.this, DashboardAdmin.class);
                        startActivity(intent);
                    } else if ("pasien".equalsIgnoreCase(role)) {
                        Intent intent = new Intent(Login.this, DashboardPasien.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Login.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView daftar = findViewById(R.id.daftartext);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registrasi.class);
                startActivity(intent);
            }
        });
    }
    private boolean isValidUser(String username, String password) {
        ArrayList<User> users = dbHelper.getUsernamePassword();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    private String getUserRole(String username) {
        ArrayList<User> users = dbHelper.getUsernamePassword();

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getRole();
            }
        }
        return null;
    }
}

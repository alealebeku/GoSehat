package com.example.gosehat.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardPasien;
import com.example.gosehat.rawatjalan.AddRawatJalan;

import db.DbHelper;
import model.User;

public class ViewUser extends AppCompatActivity {
    private DbHelper dbHelper;
    private User user;
    private int userId;
    private TextView namaPasien, jenisKelamin, tanggalLahir, email, alamat;
    private Button editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profil);
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id_user", -1);
        user =  dbHelper.getUserById(userId);

        namaPasien = findViewById(R.id.nama_pasien);
        jenisKelamin = findViewById(R.id.gender_pasien);
        tanggalLahir = findViewById(R.id.tanggal_lahir_pasien);
        email = findViewById(R.id.email_pasien);
        alamat = findViewById(R.id.alamat_pasien);
        editButton = findViewById(R.id.btnedit);

        if (user != null) {
            namaPasien.setText(user.getNama() != null ? user.getNama() : "-");
            jenisKelamin.setText(user.getJenisKelamin() != null ? user.getJenisKelamin() : "-");
            tanggalLahir.setText(user.getTanggalLahir() != null ? user.getTanggalLahir() : "-");
            email.setText(user.getEmail() != null ? user.getEmail() : "-");
            alamat.setText(user.getAlamat() != null ? user.getAlamat() : "-");
        } else {
            Toast.makeText(this, "Data user tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewUser.this, DashboardPasien.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewUser.this, UpdateUser.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
            }
        });
    }
}

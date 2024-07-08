package com.example.gosehat.dashboard;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.rawatjalan.AddRawatJalan;
import com.example.gosehat.spesialis.ViewSpesialis;
import com.example.gosehat.user.ViewUser;

import db.DbHelper;
import model.User;

public class DashboardPasien extends AppCompatActivity {
    LinearLayout monitorAntrian1, monitorAntrian2;
    TextView namaUser;
    ImageView profilPasien;
    private DbHelper dbHelper;
    private User user;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_pasien);
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id_user", -1);
        user =  dbHelper.getUserById(userId);
        namaUser = findViewById(R.id.textViewNamaUser);
        namaUser.setText("Selamat Datang, " + user.getNama() + "!");

        monitorAntrian1 = findViewById(R.id.monitorantrian1);
        monitorAntrian2 = findViewById(R.id.monitorantrian2);

        monitorAntrian1.setVisibility(GONE);
        monitorAntrian2.setVisibility(GONE);

        profilPasien = findViewById(R.id.imageViewProfil);
        profilPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, ViewUser.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
                finish();
            }
        });

        LinearLayout daftar = findViewById(R.id.pendaftaran);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, AddRawatJalan.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
                finish();
            }
        });
        LinearLayout Rmedis = findViewById(R.id.riwayatmedis);
        Rmedis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, AddRawatJalan.class);
                startActivity(intent);
            }
        });
        LinearLayout antrian = findViewById(R.id.monitorantrian);
        antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, ViewSpesialis.class);
                startActivity(intent);
            }
        });
    }
}

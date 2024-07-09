package com.example.gosehat.rawatjalan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardPasien;

import db.DbHelper;
import model.RawatJalan;
import model.User;

public class ViewAntrian extends AppCompatActivity {
    private DbHelper dbHelper;
    private User user;
    private RawatJalan rawatJalan;
    private int userId;
    private int jumlahAntrianDiDepan;
    private TextView nomorRawatJalan, tanggal, waktu, antrian, nomorAntrian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_monitoring_antrian);
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id_user", -1);
        user =  dbHelper.getUserById(userId);

        rawatJalan = dbHelper.getRawatJalanByIdPasien(userId);

        nomorRawatJalan = findViewById(R.id.nomorrawatjalan);
        tanggal = findViewById(R.id.tanggal);
        waktu = findViewById(R.id.waktu);
        antrian = findViewById(R.id.antrian);
        nomorAntrian = findViewById(R.id.nomorantrian);

        if (rawatJalan != null) {
            nomorRawatJalan.setText(String.valueOf(rawatJalan.getId()) != null ? String.valueOf(rawatJalan.getId()) : "-");
            tanggal.setText(rawatJalan.getTanggal() != null ? rawatJalan.getTanggal() : "-");
            nomorAntrian.setText(rawatJalan.getNomor_antrian() != null ? rawatJalan.getNomor_antrian() : "-");
        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        jumlahAntrianDiDepan = dbHelper.getJumlahAntrianDepan(rawatJalan.getNomor_antrian(), rawatJalan.getNomor_antrian());
        antrian.setText(String.valueOf(jumlahAntrianDiDepan));

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAntrian.this, DashboardPasien.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
                finish();
            }
        });
    }
}

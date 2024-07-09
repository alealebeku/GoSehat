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
import model.JadwalDokter;
import model.RawatJalan;
import model.User;

public class ViewAntrian extends AppCompatActivity {
    private DbHelper dbHelper;
    private User user;
    private JadwalDokter jadwalDokter;
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
        jadwalDokter = dbHelper.getJadwalById(rawatJalan.getId_jadwal());

        nomorRawatJalan = findViewById(R.id.nomorrawatjalan);
        tanggal = findViewById(R.id.tanggal);
        antrian = findViewById(R.id.antrian);
        nomorAntrian = findViewById(R.id.nomorantrian);

        if (rawatJalan != null && jadwalDokter != null) {
            nomorRawatJalan.setText("Nomor Pendaftaran : " + String.valueOf(rawatJalan.getId()) != null ? "Nomor Pendaftaran : " + String.valueOf(rawatJalan.getId()) : "-");
            String waktuJadwal = jadwalDokter.getWaktu_mulai() + "-" + jadwalDokter.getWaktu_berakhir();
            tanggal.setText(rawatJalan.getTanggal() + "  " + waktuJadwal != null ? rawatJalan.getTanggal() + "  " + waktuJadwal : "-");
            nomorAntrian.setText(rawatJalan.getNomor_antrian() != null ? rawatJalan.getNomor_antrian() : "-");
            jumlahAntrianDiDepan = dbHelper.getJumlahAntrianBelumDilayani(rawatJalan.getId_dokter(), rawatJalan.getId_jadwal(), rawatJalan.getTanggal());
            antrian.setText(String.valueOf(jumlahAntrianDiDepan - 1));
        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

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

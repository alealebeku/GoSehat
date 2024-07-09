package com.example.gosehat.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.gosehat.R;
import com.example.gosehat.dokter.ViewDokter;
import com.example.gosehat.jadwaldokter.AddJadwal;
import com.example.gosehat.jadwaldokter.ViewJadwal;
import com.example.gosehat.klinik.ViewKlinik;
import com.example.gosehat.riwayatmedis.AddRiwayatMedis;
import com.example.gosehat.spesialis.ViewSpesialis;

public class DashboardAdmin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_admin);

        LinearLayout daftar = findViewById(R.id.menuklinik);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardAdmin.this, ViewKlinik.class);
                startActivity(intent);
            }
        });
        LinearLayout Rmedis = findViewById(R.id.menudokter);
        Rmedis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardAdmin.this, ViewDokter.class);
                startActivity(intent);
            }
        });
        LinearLayout antrian = findViewById(R.id.menuspesialis);
        antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardAdmin.this, ViewSpesialis.class);
                startActivity(intent);
            }
        });
        LinearLayout jadwal = findViewById(R.id.menujadwaldokter);
        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardAdmin.this, ViewJadwal.class);
                startActivity(intent);
            }
        });
        LinearLayout laporan = findViewById(R.id.menulaporanriwaya);
        laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardAdmin.this, AddRiwayatMedis.class);
                startActivity(intent);
            }
        });

        ImageView imageView = findViewById(R.id.animatedVector);
        Glide.with(this)
                .asGif()
                .load(R.drawable.gifcoba) // Ganti dengan path ke file GIF Anda
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

    }
}

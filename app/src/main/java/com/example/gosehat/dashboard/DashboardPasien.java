package com.example.gosehat.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.rawatjalan.RawatJalan;
import com.example.gosehat.spesialis.ViewSpesialis;

public class DashboardPasien extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_pasien);

        LinearLayout daftar = findViewById(R.id.pendaftaran);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, RawatJalan.class);
                startActivity(intent);
            }
        });
        LinearLayout Rmedis = findViewById(R.id.riwayatmedis);
        Rmedis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, RawatJalan.class);
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

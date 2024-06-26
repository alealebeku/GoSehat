package com.example.gosehat.rawatjalan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardPasien;

public class RawatJalan extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_rawat_jalan);

        ImageView daftar = findViewById(R.id.iconback);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RawatJalan.this, DashboardPasien.class);
                startActivity(intent);
            }
        });
    }
}

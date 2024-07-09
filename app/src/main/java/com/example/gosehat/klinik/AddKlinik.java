package com.example.gosehat.klinik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import db.DbHelper;
import model.Klinik;

public class AddKlinik extends AppCompatActivity {
    FloatingActionButton fab;
    private ListView listView;
    private DbHelper dbHelper;
    private ArrayList<Klinik> kliniks;
    private ImageView back;
    private EditText nama_klinik, alamat_klinik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_klinik);

        nama_klinik = findViewById(R.id.nama_klinik);
        alamat_klinik = findViewById(R.id.alamat_klinik);

        back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddKlinik.this, ViewKlinik.class);
                startActivity(intent);
                finish();
            }
        });
        Button save = findViewById(R.id.btnsimpanklinik);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    private void saveData() {
        String klinik = nama_klinik.getText().toString().trim();
        String alamat = alamat_klinik.getText().toString().trim();

        if (klinik.isEmpty() || alamat.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            Klinik klinik1 = new Klinik();
            klinik1.setNamaklinik(klinik);
            klinik1.setAlamat(alamat);
            klinik1.setStatus(1);

            DbHelper dbHelper = new DbHelper(this);
            boolean isInserted = dbHelper.insertKlinik(klinik1);

            if (isInserted) {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                resetInputFields();
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
            }
        }

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddKlinik.this, DashboardAdmin.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void resetInputFields() {
        nama_klinik.setText("");
        alamat_klinik.setText("");
    }
}

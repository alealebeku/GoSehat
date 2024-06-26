package com.example.gosehat.klinik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;

import db.DbHelper;
import model.Klinik;

public class UpdateKlinik extends AppCompatActivity {

    private ImageView back;
    private EditText klinik, alamat;
    private DbHelper dbHelper;
    private int klikID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_update_klinik);

        klinik = findViewById(R.id.updatenama_klinik); // Sesuaikan ID sesuai dengan layout XML Anda
        alamat = findViewById(R.id.updatealamat_klinik); // Sesuaikan ID sesuai dengan layout XML Anda
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        klikID = intent.getIntExtra("id_klinik", -1);
        String namaklinik = intent.getStringExtra("nama_klinik");
        String alamatklinik = intent.getStringExtra("alamat_klinik");

        if (klikID != -1) {
            klinik.setText(namaklinik);
            alamat.setText(alamatklinik);
        }

        Button save = findViewById(R.id.btnupdateklinik);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateKlinik.this, ViewKlinik.class);
                startActivity(intent);
            }
        });
    }
    private void updateData() {
        String namaklinik = klinik.getText().toString().trim();
        String alamatklinik = alamat.getText().toString().trim();

        if (namaklinik.isEmpty() || alamatklinik.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            Klinik klinik1 = new Klinik();
            klinik1.setId(klikID);
            klinik1.setNamaklinik(namaklinik);
            klinik1.setAlamat(alamatklinik);

            boolean isUpdated = dbHelper.updateKlinik(klinik1);

            if (isUpdated) {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateKlinik.this, ViewKlinik.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

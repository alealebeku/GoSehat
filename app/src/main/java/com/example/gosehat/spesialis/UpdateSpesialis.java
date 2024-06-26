package com.example.gosehat.spesialis;

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
import model.Spesialis;

public class UpdateSpesialis extends AppCompatActivity {
    private EditText editNama, editDeskripsi;
    private DbHelper dbHelper;
    private int spesialisId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_update_spesialis);

        editNama = findViewById(R.id.updatenama); // Sesuaikan ID sesuai dengan layout XML Anda
        editDeskripsi = findViewById(R.id.updatedeskripsi); // Sesuaikan ID sesuai dengan layout XML Anda
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        spesialisId = intent.getIntExtra("id_spesialis", -1);
        String namaSpesialis = intent.getStringExtra("nama_spesialis");
        String deskripsiSpesialis = intent.getStringExtra("deskripsi_spesialis");

        if (spesialisId != -1) {
            editNama.setText(namaSpesialis);
            editDeskripsi.setText(deskripsiSpesialis);
        }

        Button save = findViewById(R.id.btnupdatespesialis);
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
                Intent intent = new Intent(UpdateSpesialis.this, ViewSpesialis.class);
                startActivity(intent);
            }
        });
    }

    private void updateData() {
        String namaSpesialis = editNama.getText().toString().trim();
        String deskripsiSpesialis = editDeskripsi.getText().toString().trim();

        if (namaSpesialis.isEmpty() || deskripsiSpesialis.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            model.Spesialis spesialis = new Spesialis();
            spesialis.setId(spesialisId);
            spesialis.setNama(namaSpesialis);
            spesialis.setDeskripsi(deskripsiSpesialis);

            boolean isUpdated = dbHelper.updateSpesialis(spesialis);

            if (isUpdated) {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateSpesialis.this, ViewSpesialis.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

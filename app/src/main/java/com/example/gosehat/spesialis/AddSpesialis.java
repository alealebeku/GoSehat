package com.example.gosehat.spesialis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;

import db.DbHelper;

public class AddSpesialis extends AppCompatActivity {

    private ImageView back;
    private EditText nama_spesialis, deskripsi_spesialis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_spesialis);

        nama_spesialis = findViewById(R.id.nama_spesialis);
        deskripsi_spesialis = findViewById(R.id.deskripsi_spesialis);

        back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddSpesialis.this, ViewSpesialis.class);
                startActivity(intent);
            }
        });

        Button save = findViewById(R.id.btnsimpan);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    private void saveData() {
        String spesialis = nama_spesialis.getText().toString().trim();
        String deskripsi = deskripsi_spesialis.getText().toString().trim();

        if (spesialis.isEmpty() || deskripsi.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            model.Spesialis spesialis1 = new model.Spesialis();
            spesialis1.setNama(spesialis);
            spesialis1.setDeskripsi(deskripsi);

            DbHelper dbHelper = new DbHelper(this);
            boolean isInserted = dbHelper.insertSpesialis(spesialis1);

            Log.d("SaveData", "Nama: " + spesialis + ", Deskripsi: " + deskripsi + ", Inserted: " + isInserted);

            if (isInserted) {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                resetInputFields();
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void resetInputFields() {
        nama_spesialis.setText("");
        deskripsi_spesialis.setText("");
    }
}

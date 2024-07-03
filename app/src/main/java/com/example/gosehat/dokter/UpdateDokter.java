package com.example.gosehat.dokter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;

import java.util.ArrayList;

import db.DbHelper;
import model.Dokter;
import model.Klinik;
import model.Spesialis;

public class UpdateDokter extends AppCompatActivity {
    private EditText nama, umur, alamat;

    private Spinner jk, idsps, idklinik;

    private DbHelper dbHelper;

    private ArrayList<Spesialis> spesialisList;
    private ArrayList<Klinik> klinikList;
    private int dokterID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_update_dokter);

        nama = findViewById(R.id.updatenama);
        umur = findViewById(R.id.updateumur);
        alamat = findViewById(R.id.updatealamat);
        jk = findViewById(R.id.updatespinnerJenisKelamin);
        idsps = findViewById(R.id.updatespinnerSpesialis);
        idklinik = findViewById(R.id.updatespinnerklinik);

        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        dokterID = intent.getIntExtra("id_dokter", -1);
        String namadokter = intent.getStringExtra("nama_dokter");
        String umurdokter = intent.getStringExtra("umur");
        String alamatdokter = intent.getStringExtra("alamat");
        String jeniskelamin = intent.getStringExtra("jenis_kelamin");
        int spesialisdokter = intent.getIntExtra("id_sps", -1);
        int klinikdokter = intent.getIntExtra("id_klinikk", -1);

        dokterID = intent.getIntExtra("id_dokter", -1);

        if (dokterID != -1) {
            // Get dokter data from DBHelper
            Dokter dokter = dbHelper.getDokterById(dokterID);

            if (dokter != null) {
                // Set EditText values
                nama.setText(dokter.getNama_dokter());
                umur.setText(String.valueOf(dokter.getUmur()));
                alamat.setText(dokter.getAlamat());

                // Set jenis kelamin spinner
                ArrayAdapter<CharSequence> adapterJenisKelamin = ArrayAdapter.createFromResource(this, R.array.jenis_kelamin_array, android.R.layout.simple_spinner_item);
                adapterJenisKelamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                jk.setAdapter(adapterJenisKelamin);
                if (dokter.getJenis_kelamin() != null) {
                    int spinnerPosition = adapterJenisKelamin.getPosition(dokter.getJenis_kelamin());
                    jk.setSelection(spinnerPosition);
                }

                // Menginisialisasi Spinner Spesialis
                spesialisList = dbHelper.getAllSpesialis(); // Inisialisasi spesialisList di sini
                ArrayList<String> namaSpesialisList = new ArrayList<>();
                for (Spesialis spesialis : spesialisList) {
                    namaSpesialisList.add(spesialis.getNama());
                }

                ArrayAdapter<String> adapterSpesialis = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namaSpesialisList);
                adapterSpesialis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                idsps.setAdapter(adapterSpesialis);

                // Memilih spesialis yang sesuai dengan data dokter yang akan di-update
                for (int i = 0; i < spesialisList.size(); i++) {
                    if (spesialisList.get(i).getId() == spesialisdokter) {
                        idsps.setSelection(i);
                        break;
                    }
                }

                klinikList = dbHelper.getAllKlinik(); // Inisialisasi spesialisList di sini
                ArrayList<String> namaklinik = new ArrayList<>();
                for (Klinik klinik : klinikList) {
                    namaklinik.add(klinik.getNamaklinik());
                }

                ArrayAdapter<String> klinikadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namaklinik);
                klinikadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                idklinik.setAdapter(klinikadapter);

                for (int i = 0; i < klinikList.size(); i++) {
                    if (klinikList.get(i).getId() == klinikdokter) {
                        idklinik.setSelection(i);
                        break;
                    }
                }

            } else {
                Toast.makeText(this, "Dokter tidak ditemukan", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "ID Dokter tidak valid", Toast.LENGTH_SHORT).show();
            finish();
        }


        Button save = findViewById(R.id.updatedokter);
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
                Intent intent = new Intent(UpdateDokter.this, ViewDokter.class);
                startActivity(intent);
            }
        });
    }
    private void updateData() {
        String namadokter = nama.getText().toString().trim();
        String umurdokter = umur.getText().toString().trim();
        String alamatdokter = alamat.getText().toString().trim();
        String jeniskelamin = jk.getSelectedItem().toString();
        String sps = idsps.getSelectedItem().toString();
        int spesialisId = spesialisList.get(idsps.getSelectedItemPosition()).getId();
        String kln = idklinik.getSelectedItem().toString();
        int klinikid = klinikList.get(idklinik.getSelectedItemPosition()).getId();

        if (namadokter.isEmpty() || umurdokter.isEmpty() || alamatdokter.isEmpty()|| sps.isEmpty()|| kln.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            Dokter dokter = new Dokter();
            dokter.setId_dokter(dokterID);
            dokter.setNama_dokter(namadokter);
            dokter.setUmur(Integer.parseInt(umurdokter));
            dokter.setAlamat(alamatdokter);
            dokter.setJenis_kelamin(jeniskelamin);
            dokter.setId_spesialis(spesialisId);
            dokter.setId_klinik(klinikid);

            boolean isUpdated = dbHelper.updateDokter(dokter);

            if (isUpdated) {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateDokter.this, ViewDokter.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

package com.example.gosehat.dokter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;

import java.util.ArrayList;

import db.DbHelper;
import model.Dokter;
import model.Klinik;
import model.Spesialis;

public class AddDokter extends AppCompatActivity {
    private TextView nama, umur, alamat;

    private Spinner jeniskelamin;

    private Button buttonSave;

    private Spinner spes, kln;
    private ArrayList<Spesialis> spesialisList;
    private ArrayList<Klinik> klinikArrayList;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_dokter);

        spes = findViewById(R.id.spinnerSpesialis);
        kln = findViewById(R.id.spinnerKlinik);

        dbHelper = new DbHelper(this);
        spesialisList = dbHelper.getAllSpesialis();
        klinikArrayList = dbHelper.getAllKlinik();

        ArrayList<String> spesialisNames = new ArrayList<>();
        for (Spesialis spesialis : spesialisList) {
            spesialisNames.add(spesialis.getNama());
        }

        ArrayAdapter<String> spesialisAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spesialisNames);
        spesialisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spes.setAdapter(spesialisAdapter);


        ArrayList<String> klinikname = new ArrayList<>();
        for (Klinik klinik : klinikArrayList) {
            klinikname.add(klinik.getNamaklinik());
        }

        ArrayAdapter<String> klinikadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, klinikname);
        klinikadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kln.setAdapter(klinikadapter);

        nama = findViewById(R.id.nmdokter);
        umur = findViewById(R.id.umrdokter);
        alamat = findViewById(R.id.almtdokter);
        jeniskelamin = findViewById(R.id.spinnerJenisKelamin);
        spes = findViewById(R.id.spinnerSpesialis);
        kln = findViewById(R.id.spinnerKlinik);

        buttonSave = findViewById(R.id.btnsimpan);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {
        String namadokter = nama.getText().toString().trim();
        String umurdokter = umur.getText().toString().trim();
        String alamatdokter = alamat.getText().toString().trim();
        String jeniskel = jeniskelamin.getSelectedItem().toString();
        String sps = spes.getSelectedItem().toString();
        String klnik = kln.getSelectedItem().toString();

        int spesialisId = spesialisList.get(spes.getSelectedItemPosition()).getId();
        int klinikId = klinikArrayList.get(kln.getSelectedItemPosition()).getId();

        if (namadokter.isEmpty() || umurdokter.isEmpty() || alamatdokter.isEmpty() || jeniskel.isEmpty() || sps.isEmpty() || klnik.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            Dokter dokter = new Dokter();
            dokter.setNama_dokter(namadokter);
            dokter.setUmur(Integer.parseInt(umurdokter));
            dokter.setAlamat(alamatdokter);
            dokter.setJenis_kelamin(jeniskel);
            dokter.setId_spesialis(spesialisId);
            dokter.setId_klinik(klinikId);

            DbHelper dbHelper = new DbHelper(this);
            boolean isInserted = dbHelper.insertDokter(dokter);

            if (isInserted) {
                Toast.makeText(this, "Data dokter berhasil disimpan", Toast.LENGTH_SHORT).show();
                resetInputFields();
            } else {
                Toast.makeText(this, "Gagal menyimpan data dokter", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetInputFields() {
        nama.setText("");
        umur.setText("");
        alamat.setText("");
        jeniskelamin.setSelection(0);
        spes.setSelection(0);
        kln.setSelection(0);
    }
}

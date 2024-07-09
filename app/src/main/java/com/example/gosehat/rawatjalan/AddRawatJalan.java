package com.example.gosehat.rawatjalan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardPasien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import db.DbHelper;
import model.Dokter;
import model.JadwalDokter;
import model.Klinik;
import model.RawatJalan;
import model.User;

public class AddRawatJalan extends AppCompatActivity {
    private Spinner klinik, dokter, jadwalDokter, tanggal;
    private EditText namaPasien;
    private Button buttonSave;
    private ArrayList<Klinik> klinikList;
    private ArrayList<Dokter> dokterList;
    private ArrayList<JadwalDokter> jadwalDokterList;
    private DbHelper dbHelper;
    private User user;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_rawat_jalan);
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id_user", -1);
        user =  dbHelper.getUserById(userId);
        namaPasien = findViewById(R.id.namapasien);
        namaPasien.setText(user.getNama());

        klinik = findViewById(R.id.spinner_klinik);
        dokter = findViewById(R.id.spinner_dokter);
        jadwalDokter = findViewById(R.id.spinner_jadwal_dokter);
        tanggal = findViewById(R.id.spinner_tanggal);

        //inisialisasi data klinik
        klinikList = dbHelper.getAllKlinik();
        ArrayList<String> namaKlinik = new ArrayList<>();
        for (Klinik klinik : klinikList) {
            namaKlinik.add(klinik.getNamaklinik());
        }
        ArrayAdapter<String> klinikAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namaKlinik);
        klinikAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        klinik.setAdapter(klinikAdapter);

        klinik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Klinik selectedKlinik = klinikList.get(position);
                dokterList = dbHelper.getDokterByKlinik(selectedKlinik.getId());
                ArrayList<String> namaDokter = new ArrayList<>();
                for (Dokter dokter : dokterList) {
                    dbHelper.getNamaSpesialisById(dokter.getId_spesialis());
                    namaDokter.add(dokter.getNama_dokter() + " - " + dbHelper.getNamaSpesialisById(dokter.getId_spesialis()));
                }

                ArrayAdapter<String> updatedDokterAdapter = new ArrayAdapter<>(AddRawatJalan.this, android.R.layout.simple_spinner_item, namaDokter);
                updatedDokterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dokter.setAdapter(updatedDokterAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        dokter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Update jadwalDokter spinner based on selected dokter
                Dokter selectedDokter = dokterList.get(position);
                jadwalDokterList = dbHelper.getJadwalByDokter(selectedDokter.getId_dokter());
                ArrayList<String> jadwal = new ArrayList<>();
                ArrayList<String> hari = new ArrayList<>();
                for (JadwalDokter jadwalDokter : jadwalDokterList) {
                    jadwal.add(transformasiHari(jadwalDokter.getHari()) + " " + jadwalDokter.getWaktu_mulai() + "-" + jadwalDokter.getWaktu_berakhir());
                    String[] hariArray = jadwalDokter.getHari().split(",");
                    for (String h : hariArray) {
                        if (!hari.contains(h.trim())) {
                            hari.add(h.trim());
                        }
                    }
                }
                Calendar calendar = Calendar.getInstance();
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentYear = calendar.get(Calendar.YEAR);
                ArrayList<String> tanggalHari = new ArrayList<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());

                while (calendar.get(Calendar.MONTH) == currentMonth) {
                    String dayOfWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
                    if (hari.contains(dayOfWeek.toLowerCase())) {
                        tanggalHari.add(dateFormat.format(calendar.getTime()));
                    }
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }

                ArrayAdapter<String> updatedJadwalDokterAdapter = new ArrayAdapter<>(AddRawatJalan.this, android.R.layout.simple_spinner_item, jadwal);
                updatedJadwalDokterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                jadwalDokter.setAdapter(updatedJadwalDokterAdapter);

                ArrayAdapter<String> updatedTanggalAdapter = new ArrayAdapter<>(AddRawatJalan.this, android.R.layout.simple_spinner_item, tanggalHari);
                updatedTanggalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tanggal.setAdapter(updatedTanggalAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        buttonSave = findViewById(R.id.btnsimpan);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRawatJalan.this, DashboardPasien.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
                finish();
            }
        });
    }
    private void saveData() {
        //set tanggal rawat jalan
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        ArrayList<String> tanggalHari = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
        String tanggalRawatJalan = dateFormat.format(calendar.getTime());

        //generate nomor antrian
        int idDokter = dokterList.get(dokter.getSelectedItemPosition()).getId_dokter();
        int idJadwalDokter = jadwalDokterList.get(jadwalDokter.getSelectedItemPosition()).getId_jadwal();
        int jumlahData = dbHelper.getNomorUrut(idDokter, idJadwalDokter, tanggalRawatJalan);
        int nomorUrut = jumlahData + 1;
        String nomorAntrian =  String.valueOf(idDokter) + String.valueOf(idJadwalDokter) + String.format("%03d", nomorUrut);

        int pasienId = user.getId();
        int klinikId = klinikList.get(klinik.getSelectedItemPosition()).getId();
        int dokterId = dokterList.get(dokter.getSelectedItemPosition()).getId_dokter();
        int jadwalDokterId = jadwalDokterList.get(jadwalDokter.getSelectedItemPosition()).getId_jadwal();

        if (pasienId <= 0 || klinikId <= 0 || dokterId <= 0 || jadwalDokterId <= 0 || tanggalRawatJalan.isEmpty() || nomorAntrian.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            RawatJalan rawatJalan = new RawatJalan();
            rawatJalan.setId_pasien(pasienId);
            rawatJalan.setId_klinik(klinikId);
            rawatJalan.setId_dokter(dokterId);
            rawatJalan.setId_jadwal(jadwalDokterId);
            rawatJalan.setTanggal(tanggalRawatJalan);
            rawatJalan.setNomor_antrian(nomorAntrian);
            rawatJalan.setStatus(1);

            DbHelper dbHelper = new DbHelper(this);
            boolean isInserted = dbHelper.insertRawatJalan(rawatJalan);

            if (isInserted) {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddRawatJalan.this, DashboardPasien.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String transformasiHari(String hari) {
        if ("senin,selasa,rabu,kamis,jumat,sabtu,minggu".equals(hari)) {
            return "setiap hari";
        }
        // Tambahkan spasi setelah setiap koma
        hari = hari.replaceAll(",", ", ");

        // Ubah tanda koma terakhir menjadi ' & '
        int lastCommaIndex = hari.lastIndexOf(",");
        if (lastCommaIndex != -1) {
            hari = new StringBuilder(hari).replace(lastCommaIndex, lastCommaIndex + 1, " &").toString();
        }
        return hari;
    }


}

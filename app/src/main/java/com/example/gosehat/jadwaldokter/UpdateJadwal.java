package com.example.gosehat.jadwaldokter;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dokter.UpdateDokter;
import com.example.gosehat.dokter.ViewDokter;

import java.util.ArrayList;
import java.util.Calendar;

import db.DbHelper;
import model.Dokter;
import model.JadwalDokter;
import model.Klinik;
import model.Spesialis;

public class UpdateJadwal extends AppCompatActivity {
    private TextView waktuMulai, waktuBerakhir;
    private CheckBox senin, selasa, rabu, kamis, jumat, sabtu, minggu;
    private Spinner dokter;

    private DbHelper dbHelper;

    private ArrayList<Dokter> dokterList;
    private int jadwalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_update_jadwal);

        senin = findViewById(R.id.senin);
        selasa = findViewById(R.id.selasa);
        rabu = findViewById(R.id.rabu);
        kamis = findViewById(R.id.kamis);
        jumat = findViewById(R.id.jumat);
        sabtu = findViewById(R.id.sabtu);
        minggu = findViewById(R.id.minggu);
        dokter = findViewById(R.id.spinnerDokter);
        waktuMulai = findViewById(R.id.editTextWaktuMulai);
        waktuBerakhir = findViewById(R.id.editTextWaktuBerakhir);

        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        jadwalID = intent.getIntExtra("id_jadwal", -1);
        int idDokter = intent.getIntExtra("id_dokter", -1);

        jadwalID = intent.getIntExtra("id_jadwal", -1);

        if (jadwalID != -1) {
            // Get dokter data from DBHelper
            JadwalDokter jadwalDokter = dbHelper.getJadwalById(jadwalID);

            if (jadwalDokter != null) {
                if(jadwalDokter.getHari().contains("senin")) {
                    senin.setChecked(true);
                }
                if(jadwalDokter.getHari().contains("selasa")) {
                    selasa.setChecked(true);
                }
                if(jadwalDokter.getHari().contains("rabu")) {
                    rabu.setChecked(true);
                }
                if(jadwalDokter.getHari().contains("kamis")) {
                    kamis.setChecked(true);
                }
                if(jadwalDokter.getHari().contains("jumat")) {
                    jumat.setChecked(true);
                }
                if(jadwalDokter.getHari().contains("sabtu")) {
                    sabtu.setChecked(true);
                }
                if(jadwalDokter.getHari().contains("minggu")) {
                    minggu.setChecked(true);
                }

                // Set EditText values
                waktuMulai.setText(jadwalDokter.getWaktu_mulai());
                waktuBerakhir.setText(jadwalDokter.getWaktu_berakhir());

                // Menginisialisasi Spinner Spesialis
                dokterList = dbHelper.getAllDokter(); // Inisialisasi spesialisList di sini
                ArrayList<String> namaDokterList = new ArrayList<>();
                for (Dokter dokter : dokterList) {
                    namaDokterList.add(dokter.getNama_dokter());
                }

                ArrayAdapter<String> adapterDokter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, namaDokterList);
                adapterDokter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dokter.setAdapter(adapterDokter);

                // Memilih spesialis yang sesuai dengan data dokter yang akan di-update
                for (int i = 0; i < dokterList.size(); i++) {
                    if (dokterList.get(i).getId_dokter() == idDokter) {
                        dokter.setSelection(i);
                        break;
                    }
                }

            } else {
                Toast.makeText(this, "Jadwal dokter tidak ditemukan", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "ID Jadwal dokter tidak valid", Toast.LENGTH_SHORT).show();
            finish();
        }

        waktuMulai.setOnClickListener(view -> showTimePickerDialog(waktuMulai));
        waktuBerakhir.setOnClickListener(view -> showTimePickerDialog(waktuBerakhir));


        Button save = findViewById(R.id.updatejadwal);
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
                Intent intent = new Intent(UpdateJadwal.this, ViewJadwal.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateData() {
        String checkedHari = "";

        if (senin.isChecked()) {
            checkedHari += "senin,";
        }
        if (selasa.isChecked()) {
            checkedHari += "selasa,";
        }
        if (rabu.isChecked()) {
            checkedHari += "rabu,";
        }
        if (kamis.isChecked()) {
            checkedHari += "kamis,";
        }
        if (jumat.isChecked()) {
            checkedHari += "jumat,";
        }
        if (sabtu.isChecked()) {
            checkedHari += "sabtu,";
        }
        if (minggu.isChecked()) {
            checkedHari += "minggu";
        }

        // Menghapus koma terakhir jika ada
        if (checkedHari.endsWith(",")) {
            checkedHari = checkedHari.substring(0, checkedHari.length() - 1);
        }

        String waktu_mulai = waktuMulai.getText().toString().trim();
        String waktu_berakhir = waktuBerakhir.getText().toString().trim();

        String selected_dokter = dokter.getSelectedItem().toString();
        int dokterId = dokterList.get(dokter.getSelectedItemPosition()).getId_dokter();

        if (waktu_mulai.isEmpty() || waktu_berakhir.isEmpty() || selected_dokter.isEmpty() || checkedHari.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            JadwalDokter jadwalDokter = new JadwalDokter();
            jadwalDokter.setHari(checkedHari);
            jadwalDokter.setPK_id_dokter(dokterId);
            jadwalDokter.setWaktu_mulai(waktu_mulai);
            jadwalDokter.setWaktu_berakhir(waktu_berakhir);
            jadwalDokter.setStatus(1);

            boolean isUpdated = dbHelper.updateJadwal(jadwalDokter);

            if (isUpdated) {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateJadwal.this, ViewJadwal.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showTimePickerDialog(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateJadwal.this,
                (TimePicker view, int hourOfDay, int minuteOfHour) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minuteOfHour);
                    textView.setText(time);
                }, hour, minute, true);
        timePickerDialog.show();
    }
}

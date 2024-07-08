package com.example.gosehat.jadwaldokter;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.example.gosehat.dashboard.DashboardAdmin;
import com.example.gosehat.klinik.ViewKlinik;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import db.DbHelper;
import model.Dokter;
import model.JadwalDokter;
import model.Klinik;
import model.Spesialis;

public class AddJadwal  extends AppCompatActivity {
    private TextView waktuMulai, waktuBerakhir, hari;

    private Spinner dokter;

    private Button buttonSave;

    private CheckBox senin, selasa, rabu, kamis, jumat, sabtu, minggu;
    private ArrayList<Dokter> dokterList;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_jadwal);

        dokter = findViewById(R.id.spinnerDokter);

        dbHelper = new DbHelper(this);
        dokterList = dbHelper.getAllDokter();

        ArrayList<String> doktersNames = new ArrayList<>();
        for (Dokter dokter : dokterList) {
            doktersNames.add(dokter.getNama_dokter());
        }

        ArrayAdapter<String> dokterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doktersNames);
        dokterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dokter.setAdapter(dokterAdapter);

        senin = findViewById(R.id.senin);
        selasa = findViewById(R.id.selasa);
        rabu = findViewById(R.id.rabu);
        kamis = findViewById(R.id.kamis);
        jumat = findViewById(R.id.jumat);
        sabtu = findViewById(R.id.sabtu);
        minggu = findViewById(R.id.minggu);

        waktuMulai = findViewById(R.id.editTextWaktuMulai);
        waktuBerakhir = findViewById(R.id.editTextWaktuBerakhir);

        waktuMulai.setOnClickListener(view -> showTimePickerDialog(waktuMulai));
        waktuBerakhir.setOnClickListener(view -> showTimePickerDialog(waktuBerakhir));

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
                Intent intent = new Intent(AddJadwal.this, DashboardAdmin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveData() {
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

            DbHelper dbHelper = new DbHelper(this);
            boolean isInserted = dbHelper.insertJadwal(jadwalDokter);

            if (isInserted) {
                Toast.makeText(this, "Data jadwal berhasil disimpan", Toast.LENGTH_SHORT).show();
                resetInputFields();
            } else {
                Toast.makeText(this, "Gagal menyimpan data jadwal", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetInputFields() {
        waktuMulai.setText("");
        waktuBerakhir.setText("");
        dokter.setSelection(0);
        senin.setChecked(false);
        selasa.setChecked(false);
        rabu.setChecked(false);
        kamis.setChecked(false);
        jumat.setChecked(false);
        sabtu.setChecked(false);
        minggu.setChecked(false);
    }

    private void showTimePickerDialog(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddJadwal.this,
                (TimePicker view, int hourOfDay, int minuteOfHour) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minuteOfHour);
                    textView.setText(time);
                }, hour, minute, true);
        timePickerDialog.show();
    }
}

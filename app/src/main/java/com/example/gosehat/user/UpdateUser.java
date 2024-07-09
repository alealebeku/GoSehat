package com.example.gosehat.user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardPasien;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import db.DbHelper;
import model.User;

public class UpdateUser extends AppCompatActivity {
    private final Calendar myCalendar = Calendar.getInstance();
    private DbHelper dbHelper;
    private User user;
    private int userId;
    private EditText namaPasien, tanggalLahir, alamat;
    private Spinner jenisKelamin;
    private Button simpanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_edit_profil);
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id_user", -1);
        user = dbHelper.getUserById(userId);

        namaPasien = findViewById(R.id.nama_pasien);
        tanggalLahir = findViewById(R.id.tanggal_lahir_pasien);
        jenisKelamin = findViewById(R.id.spinnerJenisKelamin);
        alamat = findViewById(R.id.alamat_pasien);
        simpanBtn = findViewById(R.id.btnsimpan);

        if (user != null) {
            namaPasien.setText(user.getNama() != null ? user.getNama() : "");
            tanggalLahir.setText(user.getTanggalLahir() != null && !user.getTanggalLahir().isEmpty() ? user.getTanggalLahir() : "");
            alamat.setText(user.getAlamat() != null && !user.getAlamat().isEmpty() ? user.getAlamat() : "");

            ArrayAdapter<CharSequence> adapterJenisKelamin = ArrayAdapter.createFromResource(this, R.array.jenis_kelamin_array, android.R.layout.simple_spinner_item);
            adapterJenisKelamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            jenisKelamin.setAdapter(adapterJenisKelamin);
            if (user.getJenisKelamin() != null) {
                int spinnerPosition = adapterJenisKelamin.getPosition(user.getJenisKelamin());
                jenisKelamin.setSelection(spinnerPosition);
            }
        } else {
            Toast.makeText(this, "Data user tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        tanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateUser.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUser.this, ViewUser.class);
                intent.putExtra("id_user", userId);
                startActivity(intent);
                finish();
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd MMMM yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        tanggalLahir.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateData() {
        String namaLengkap = namaPasien.getText().toString().trim();
        String jeniskelaminPasien = jenisKelamin.getSelectedItem().toString();
        String tanggalLahirPasien = tanggalLahir.getText().toString().trim();
        String alamatPasien = alamat.getText().toString().trim();

        if (namaLengkap.isEmpty() || tanggalLahirPasien.isEmpty() || alamatPasien.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            user.setNama(namaLengkap);
            user.setJenisKelamin(jeniskelaminPasien);
            user.setTanggalLahir(tanggalLahirPasien);
            user.setAlamat(alamatPasien);

            boolean isUpdated = dbHelper.updateUser(user);

            if (isUpdated) {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateUser.this, ViewUser.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

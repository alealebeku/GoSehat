package com.example.gosehat.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardPasien;

import db.DbHelper;
import model.User;

public class UpdateUser extends AppCompatActivity {
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
            namaPasien.setText(user.getNama());
            if (user.getTanggalLahir().isEmpty()) {
                tanggalLahir.setText("-");
            } else {
                tanggalLahir.setText(user.getTanggalLahir());
            }
            if (user.getAlamat().isEmpty()) {
                alamat.setText("-");
            } else {
                alamat.setText(user.getAlamat());
            }

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

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUser.this, ViewUser.class);
                startActivity(intent);
                finish();
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}

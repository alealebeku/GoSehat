package com.example.gosehat.riwayatmedis;

import static java.lang.Integer.valueOf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardAdmin;
import com.example.gosehat.dashboard.DashboardPasien;

import db.DbHelper;
import model.Dokter;
import model.RawatJalan;
import model.RiwayatMedis;
import model.User;

public class AddRiwayatMedis extends AppCompatActivity {
    private DbHelper dbHelper;
    private TextView nomorPendaftaran, diagnosa, pengobatan, perkembangan;
    private Button btnSimpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_riwayat_medis);
        dbHelper = new DbHelper(this);

        nomorPendaftaran = findViewById(R.id.nomor_pendaftaran);
        diagnosa = findViewById(R.id.diagnosa);
        pengobatan = findViewById(R.id.pengobatan);
        perkembangan = findViewById(R.id.perkembangan);

        btnSimpan = findViewById(R.id.btnsimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRiwayatMedis.this, DashboardAdmin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveData() {
        int rawatJalanId = Integer.parseInt(nomorPendaftaran.getText().toString().trim());
        RawatJalan rawatJalan = dbHelper.getRawatJalanById(rawatJalanId);
        if (rawatJalan == null) {
            Toast.makeText(this, "Data rawat jalan tidak ditemukan dengan id : " + rawatJalanId, Toast.LENGTH_SHORT).show();
            return;
        }

        String diagnosaPasien = diagnosa.getText().toString().trim();
        String pengobatanPasien = pengobatan.getText().toString().trim();
        String perkembanganPasien = perkembangan.getText().toString().trim();

        if (diagnosaPasien.isEmpty() || pengobatanPasien.isEmpty() || perkembanganPasien.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua data", Toast.LENGTH_SHORT).show();
        } else {
            RiwayatMedis riwayatMedis = new RiwayatMedis();
            riwayatMedis.setId_rawat_jalan(rawatJalanId);
            riwayatMedis.setId_pasien((rawatJalan.getId_pasien()));
            riwayatMedis.setDiagnosa(diagnosaPasien);
            riwayatMedis.setPengobatan(pengobatanPasien);
            riwayatMedis.setPerkembangan(perkembanganPasien);

            DbHelper dbHelper = new DbHelper(this);
            boolean isInserted = dbHelper.insertRiwayatMedis(riwayatMedis);

            if (isInserted) {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                resetInputFields();
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetInputFields(){
        nomorPendaftaran.setText("");
        diagnosa.setText("");
        pengobatan.setText("");
        perkembangan.setText("");
    }
}
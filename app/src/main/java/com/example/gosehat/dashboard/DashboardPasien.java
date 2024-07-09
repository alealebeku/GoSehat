package com.example.gosehat.dashboard;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.rawatjalan.AddRawatJalan;
import com.example.gosehat.rawatjalan.ViewAntrian;
import com.example.gosehat.spesialis.ViewSpesialis;
import com.example.gosehat.user.ViewUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.time.LocalDate;

import db.DbHelper;
import model.JadwalDokter;
import model.RawatJalan;
import model.User;

public class DashboardPasien extends AppCompatActivity {
    LinearLayout monitorAntrian1, monitorAntrian2;
    TextView namaUser, nomorAntrian, antrian;
    ImageView profilPasien;
    private DbHelper dbHelper;
    private User user;
    private JadwalDokter jadwalDokter;
    private RawatJalan rawatJalan;
    private int userId;
    private int jumlahAntrianDiDepan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_pasien);
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id_user", -1);
        user =  dbHelper.getUserById(userId);
        rawatJalan = dbHelper.getRawatJalanByIdPasien(user.getId());
        jadwalDokter = dbHelper.getJadwalById(rawatJalan.getId_jadwal());

        namaUser = findViewById(R.id.textViewNamaUser);
        monitorAntrian1 = findViewById(R.id.monitorantrian1);
        monitorAntrian2 = findViewById(R.id.monitorantrian2);
        nomorAntrian = findViewById(R.id.nomorantrian);
        antrian = findViewById(R.id.antrian);

        //cek rawat jalan
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
        String tanggalHariIni = dateFormat.format(currentDate);

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // Jam dalam format 24 jam
        int currentMinute = calendar.get(Calendar.MINUTE);
        String waktuSekarang = String.format(Locale.getDefault(), "%02d:%02d", currentHour, currentMinute);

        String waktuMulai = jadwalDokter.getWaktu_mulai();
        String waktuBerakhir = jadwalDokter.getWaktu_berakhir();

        boolean isWaktuSesuai = isWaktuSesuai(waktuSekarang, waktuMulai, waktuBerakhir);

        if (tanggalHariIni.equals(rawatJalan.getTanggal()) && isWaktuSesuai) {
            jumlahAntrianDiDepan = dbHelper.getJumlahAntrianBelumDilayani(rawatJalan.getId_dokter(), rawatJalan.getId_jadwal(), rawatJalan.getTanggal());

            monitorAntrian1.setVisibility(View.VISIBLE);
            monitorAntrian2.setVisibility(View.VISIBLE);
            nomorAntrian.setText("Nomor antrian anda : " + rawatJalan.getNomor_antrian() != null ? "Nomor antrian anda : " + rawatJalan.getNomor_antrian() : "-");
            antrian.setText("Menunggu : " + String.valueOf(jumlahAntrianDiDepan - 1));
        } else {
            monitorAntrian1.setVisibility(View.GONE);
            monitorAntrian2.setVisibility(View.GONE);
        }
        //end

        namaUser.setText("Selamat Datang, " + user.getNama() + "!");

        profilPasien = findViewById(R.id.imageViewProfil);
        profilPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, ViewUser.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
            }
        });

        LinearLayout daftar = findViewById(R.id.pendaftaran);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, AddRawatJalan.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
            }
        });
        LinearLayout Rmedis = findViewById(R.id.riwayatmedis);
        Rmedis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardPasien.this, AddRawatJalan.class);
                startActivity(intent);
            }
        });
        LinearLayout antrian = findViewById(R.id.monitorantrian);
        antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rawatJalan = dbHelper.getRawatJalanByIdPasien(userId);
                if (rawatJalan != null) {
                    Intent intent = new Intent(DashboardPasien.this, ViewAntrian.class);
                    intent.putExtra("id_user", user.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(DashboardPasien.this, "Anda sedang tidak di dalam antrian", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isWaktuSesuai(String waktuSekarang, String waktuMulai, String waktuBerakhir) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date waktuSekarangDate = format.parse(waktuSekarang);
            Date waktuMulaiDate = format.parse(waktuMulai);
            Date waktuBerakhirDate = format.parse(waktuBerakhir);

            return waktuSekarangDate.after(waktuMulaiDate) && waktuSekarangDate.before(waktuBerakhirDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}

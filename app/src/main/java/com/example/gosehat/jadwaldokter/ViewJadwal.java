package com.example.gosehat.jadwaldokter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.klinik.AddKlinik;
import com.example.gosehat.klinik.KlinikAdapter;
import com.example.gosehat.klinik.ViewKlinik;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import db.DbHelper;
import model.JadwalDokter;
import model.Klinik;

public class ViewJadwal extends AppCompatActivity {
    FloatingActionButton fab;
    private ListView listView;
    private DbHelper dbHelper;
    private ArrayList<JadwalDokter> jadwalDokterArrayList;
    private JadwalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_jadwal);

        dbHelper = new DbHelper(this);
        listView = findViewById(R.id.listviewjadwal);
        fab = findViewById(R.id.fab);

        // Fetch data from database
        jadwalDokterArrayList = dbHelper.getAllJadwal();

        // Initialize adapter and set it to ListView
        adapter = new JadwalAdapter(this, jadwalDokterArrayList, dbHelper);
        listView.setAdapter(adapter);

        // Set listener for FAB to open AddSpesialisActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewJadwal.this, AddJadwal.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        jadwalDokterArrayList.clear();
        jadwalDokterArrayList.addAll(dbHelper.getAllJadwal());
        adapter.notifyDataSetChanged();
    }
}

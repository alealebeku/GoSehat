package com.example.gosehat.dokter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardAdmin;
import com.example.gosehat.klinik.ViewKlinik;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import db.DbHelper;
import model.Dokter;

public class ViewDokter extends AppCompatActivity {
    FloatingActionButton fab;
    private ListView listView;
    private DbHelper dbHelper;
    private ArrayList<Dokter> dokterArrayList;
    private DokterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dokter);

        dbHelper = new DbHelper(this);
        listView = findViewById(R.id.listviewdokter);
        fab = findViewById(R.id.fab);

        // Fetch data from database
        dokterArrayList = dbHelper.getAllDokter();

        // Initialize adapter and set it to ListView
        adapter = new DokterAdapter(this, dokterArrayList, dbHelper);
        listView.setAdapter(adapter);

        // Set listener for FAB to open AddSpesialisActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDokter.this, AddDokter.class);
                startActivity(intent);
            }
        });

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDokter.this, DashboardAdmin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dokterArrayList.clear();
        dokterArrayList.addAll(dbHelper.getAllDokter());
        adapter.notifyDataSetChanged();
    }
}

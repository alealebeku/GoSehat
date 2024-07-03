package com.example.gosehat.klinik;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardAdmin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import db.DbHelper;
import model.Klinik;

public class ViewKlinik extends AppCompatActivity {
    FloatingActionButton fab;
    private ListView listView;
    private DbHelper dbHelper;
    private ArrayList<Klinik> klinikArrayList;
    private KlinikAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewklinik);

        dbHelper = new DbHelper(this);
        listView = findViewById(R.id.listviewklinikk);
        fab = findViewById(R.id.fab);

        // Initialize adapter
        klinikArrayList = new ArrayList<>();
        adapter = new KlinikAdapter(this, klinikArrayList, dbHelper);
        listView.setAdapter(adapter);

        // Initialize adapter and set it to ListView
        adapter = new KlinikAdapter(this, klinikArrayList, dbHelper);
        listView.setAdapter(adapter);

        // Set listener for FAB to open AddSpesialisActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewKlinik.this, AddKlinik.class);
                startActivity(intent);
            }
        });

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void reloadData() {
        klinikArrayList.clear(); // Clear existing data
        klinikArrayList.addAll(dbHelper.getAllKlinik()); // Reload data from database
        adapter.notifyDataSetChanged(); // Notify adapter that data has changed
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Load data from database and update ListView
        reloadData();
    }
}

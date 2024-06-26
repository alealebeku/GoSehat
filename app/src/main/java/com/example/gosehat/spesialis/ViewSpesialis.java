package com.example.gosehat.spesialis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import db.DbHelper;
import model.Spesialis;

public class ViewSpesialis extends AppCompatActivity {
    FloatingActionButton fab;
    private ListView listView;
    private DbHelper dbHelper;
    private ArrayList<Spesialis> spesialisList;
    private SpesialisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_spesialis);

        dbHelper = new DbHelper(this);
        listView = findViewById(R.id.listviewspesialis);
        fab = findViewById(R.id.fab);

        // Fetch data from database
        spesialisList = dbHelper.getAllSpesialis();

        // Initialize adapter and set it to ListView
        adapter = new SpesialisAdapter(this, spesialisList, dbHelper);
        listView.setAdapter(adapter);

        // Set listener for FAB to open AddSpesialisActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSpesialis.this, AddSpesialis.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        spesialisList.clear();
        spesialisList.addAll(dbHelper.getAllSpesialis());
        adapter.notifyDataSetChanged();
    }
}

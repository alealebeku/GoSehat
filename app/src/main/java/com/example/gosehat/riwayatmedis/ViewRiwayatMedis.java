package com.example.gosehat.riwayatmedis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardPasien;
import com.example.gosehat.rawatjalan.ViewAntrian;

import db.DbHelper;
import model.User;

public class ViewRiwayatMedis extends AppCompatActivity {
    private DbHelper dbHelper;
    private User user;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_riwayat_medis);
        dbHelper = new DbHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id_user", -1);
        user =  dbHelper.getUserById(userId);

        ImageView back = findViewById(R.id.iconback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRiwayatMedis.this, DashboardPasien.class);
                intent.putExtra("id_user", user.getId());
                startActivity(intent);
                finish();
            }
        });
    }
}

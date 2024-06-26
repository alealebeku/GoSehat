package com.example.gosehat.registrasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.login.Login;

import db.DbHelper;
import model.User;

public class Registrasi extends AppCompatActivity {
    private EditText namalengkap, email, uname, konfirpw, pw;
    private Spinner role;
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi);
        dbHelper = new DbHelper(this);

        namalengkap = findViewById(R.id.namalengkap);
        email = findViewById(R.id.Email);
        role = findViewById(R.id.roleSpinner);
        uname = findViewById(R.id.username);
        pw = findViewById(R.id.katasandi);
        konfirpw = findViewById(R.id.confirmkatasandi);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);

        // Tentukan layout yang digunakan saat daftar pilihan muncul
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter);


        TextView daftar = findViewById(R.id.masuktxt);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrasi.this, Login.class);
                startActivity(intent);
            }
        });

        Button save = findViewById(R.id.btnregistrasi);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaLengkap = namalengkap.getText().toString().trim();
                String emailUser = email.getText().toString().trim();
                String username = uname.getText().toString().trim();
                String password = pw.getText().toString().trim();
                String roleUser = role.getSelectedItem().toString();
                String konfir = konfirpw.getText().toString();

                if (namaLengkap.isEmpty() || emailUser.isEmpty() || username.isEmpty() || password.isEmpty() || roleUser.isEmpty()) {
                    Toast.makeText(Registrasi.this, "Harap mengisi semua bidang", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(konfir)) {
                    Toast.makeText(Registrasi.this, "Kata sandi dan konfirmasi kata sandi tidak cocok", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User();
                    user.setNama(namaLengkap);
                    user.setEmail(emailUser);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRole(roleUser);

                    boolean insertSuccess = dbHelper.insertUser(user);
                    if (insertSuccess) {
                        Toast.makeText(Registrasi.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registrasi.this, Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Registrasi.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

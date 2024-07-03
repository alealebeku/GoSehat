package com.example.gosehat.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gosehat.R;
import com.example.gosehat.dashboard.DashboardAdmin;
import com.example.gosehat.dashboard.DashboardPasien;
import com.example.gosehat.registrasi.Registrasi;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import db.DbHelper;
import model.User;

public class Login extends AppCompatActivity {
    private DbHelper dbHelper;
    private EditText pw, email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Inisialisasi Firebase
        FirebaseApp.initializeApp(this);

        dbHelper = new DbHelper(this); // Inisialisasi dbHelper
        email = findViewById(R.id.email);
        pw = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();

        Button login = findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String akunemail = email.getText().toString().trim();
                String password = pw.getText().toString().trim();

                // Debugging: Log email and password
                Log.d("LoginDebug", "Password: " + password);
                Log.d("LoginDebug", "Email: " + akunemail);

                // Validate that the email is in a valid email format
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(akunemail).matches()) {
                    Toast.makeText(Login.this, "Masukkan email yang valid.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isValidUser(akunemail, password)) {
                    auth.signInWithEmailAndPassword(akunemail, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                                            String role = getUserRole(akunemail);

                                            // Debugging: Log user role
                                            Log.d("LoginDebug", "User Role: " + role);

                                            if ("admin".equalsIgnoreCase(role)) {
                                                Intent intent = new Intent(Login.this, DashboardAdmin.class);
                                                startActivity(intent);
                                                finish(); // Tutup activity saat ini
                                            } else if ("pasien".equalsIgnoreCase(role)) {
                                                Intent intent = new Intent(Login.this, DashboardPasien.class);
                                                startActivity(intent);
                                                finish(); // Tutup activity saat ini
                                            } else {
                                                Log.d("LoginDebug", "Role not found, navigating back to login");
                                                Toast.makeText(Login.this, "Peran pengguna tidak ditemukan.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // Debugging: Log email verification issue
                                            Log.d("LoginDebug", "Email not verified");
                                            Toast.makeText(Login.this, "Email belum diverifikasi.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Debugging: Log authentication failure
                                        Log.d("LoginDebug", "Authentication failed: " + task.getException().getMessage());
                                        Toast.makeText(Login.this, "Email atau password salah.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Debugging: Log invalid user
                    Log.d("LoginDebug", "Invalid user credentials");
                    Toast.makeText(Login.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView daftar = findViewById(R.id.daftartext);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registrasi.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidUser(String email, String password) {
        ArrayList<User> users = dbHelper.getUsernameEmail();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private String getUserRole(String email) {
        ArrayList<User> users = dbHelper.getUsernameEmail();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user.getRole();
            }
        }
        return null;
    }
}
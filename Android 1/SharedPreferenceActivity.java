package com.example.healthcareapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SharedPreferenceActivity extends AppCompatActivity {
    TextView email, password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shared_preference);
        email= findViewById(R.id.email);
       password= findViewById(R.id.password);

        PreferenceManager prefManager = new PreferenceManager(this);
        String savedEmail = prefManager.getEmail();
        String savedPassword = prefManager.getPassword();

        email.setText("Email: " + (savedEmail != null ? savedEmail : "Not found"));
        password.setText("Password: " + (savedPassword != null ? savedPassword : "Not found"));
    }
}
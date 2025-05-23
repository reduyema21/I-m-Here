package com.example.healthcareapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgetActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonSend;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget);
        editTextEmail= findViewById(R.id.editTextForgotPasswordEmail);
        buttonSend= findViewById(R.id.buttonSend);
        setupClickListeners();
    }
    private void setupClickListeners() {
        buttonSend.setOnClickListener(v-> {
            String email = editTextEmail.getText().toString().trim();
            if (validateEmail(email)) {
                sendResetLink(email);
            }
        });
    }
    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            return false;
        }
        return true;
    }
    private void sendResetLink(String email) {

    }
}
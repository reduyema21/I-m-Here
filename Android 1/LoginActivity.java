package com.example.healthcareapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import com.example.healthcareapplication.PreferenceManager;

public class LoginActivity extends AppCompatActivity {
    EditText emailInput, passwordInput;
    Button loginButton;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
            emailInput = findViewById(R.id.emailInput);
            passwordInput = findViewById(R.id.passwordInput);
            loginButton = findViewById(R.id.loginButton);
            forgotPassword = findViewById(R.id.forgotPasswordText);
        setupClickListeners();
    }
    private void setupClickListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        forgotPassword.setOnClickListener(v -> startActivity(new Intent(this, ForgetActivity.class)));
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInput(email, password)) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);

            if (dbHelper.checkUserCredentials(email, password)) {
                saveLoginState();
                navigateToHome();
            } else {
                showError("Invalid email or password");
            }
        }
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email");
            return false;
        }
        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            return false;
        }
        return true;
    }

    private void saveLoginState() {
        PreferenceManager prefManager = new PreferenceManager(this);
        prefManager.saveLoginState(true);
        // Optional: Save email if needed elsewhere
        prefManager.saveUser(emailInput.getText().toString(), passwordInput.getText().toString());
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}

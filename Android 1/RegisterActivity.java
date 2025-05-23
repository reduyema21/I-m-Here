package com.example.healthcareapplication;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Patterns;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;
import android.content.Intent;
import com.example.healthcareapplication.PreferenceManager;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(

                    "^" +               // Start of line
                    "(?=.*[0-9])" +      // At least 1 digit
                    "(?=.*[a-z])" +      // At least 1 lowercase letter
                    "(?=.*[A-Z])" +       // At least 1 uppercase letter
                    "(?=.*[@#$%^&+=])" +  // At least 1 special character
                    "(?=\\S+$)" +         // No whitespace
                    ".{8,}" +             // At least 8 characters
                    "$"                    // End of line
    );
    EditText editTextname, editTextemail, editTextphone, editTextpassword, editTextconfirmpassword;
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        editTextname = findViewById(R.id.usernaame);
        editTextemail = findViewById(R.id.email);
        editTextphone = findViewById(R.id.phone);
        editTextpassword = findViewById(R.id.password);
        editTextconfirmpassword = findViewById(R.id.confirm);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        setupClickListeners();

        // used to scroll
        final ScrollView scrollView = findViewById(R.id.main);
        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                scrollView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = scrollView.getRootView().getHeight();
                int keypadHeight = screenHeight - rect.bottom;

                if (keypadHeight > screenHeight * 0.15) { // Keyboard is visible
                    scrollView.post(() -> {
                        View focusedView = getCurrentFocus();
                        if (focusedView != null) {
                            int scrollY = focusedView.getBottom() + 200; // Add padding
                            scrollView.smoothScrollTo(0, scrollY);
                        }
                    });
                }
            }
        });
    }
    private void setupClickListeners() {
        register.setOnClickListener(v -> {
            if (validateInput()) {
                boolean registrationSuccess = performRegistration();
                if (registrationSuccess) {
                    navigateToLoginScreen();
                }
            }
        });
        login.setOnClickListener(v -> navigateToLoginScreen());
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateInput() {
        String name = editTextname.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();
        String phone = editTextphone.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String confirmPassword = editTextconfirmpassword.getText().toString().trim();
        boolean isValid = true;
        // Name validation
        if (name.isEmpty()) {
            editTextname.setError("Name is required");
            isValid = false;
        } else if (name.length() < 2) {
            editTextname.setError("Name must be at least 2 characters");
            isValid = false;
        } else if (!name.matches("[a-zA-Z\\s]+")) {
            editTextname.setError("Only letters and spaces allowed");
            isValid = false;
        }
        // Email validation
        if (email.isEmpty()) {
            editTextemail.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Enter a valid email address");
            isValid = false;
        }
        // Phone validation
        if (phone.isEmpty()) {
            editTextphone.setError("Phone number is required");
            return false;
        } else if (!phone.matches("^09\\d{8}$")) {
            editTextphone.setError("Invalid phone number format");
            return false;
        }
        // Password validation
        if (password.isEmpty()) {
            editTextpassword.setError("Password is required");
            isValid = false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            editTextpassword.setError("Password must contain:\n- 8+ characters\n- 1 uppercase\n- 1 lowercase\n- 1 digit\n- 1 special character");
            isValid = false;
        }

        // Confirm password validation
        if (confirmPassword.isEmpty()) {
            editTextconfirmpassword.setError("Please confirm password");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            editTextconfirmpassword.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    private boolean performRegistration() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String name = editTextname.getText().toString().trim();
        String email = editTextemail.getText().toString().trim();
        String phone = editTextphone.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        long result = dbHelper.addUser(name, email, phone, password);
        if (result == -1) {
            Toast.makeText(this, "Account already exists. Please log in.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}

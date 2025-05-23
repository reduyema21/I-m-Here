package com.example.healthcareapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CheckoutActivity extends AppCompatActivity {
    private EditText etFullName, etCardNumber, etBrttDay;
    private String selectedDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);
        etFullName = findViewById(R.id.etFullName);
        etCardNumber = findViewById(R.id.etCardNumber);
        etBrttDay = findViewById(R.id.etDobDay);
        setupListeners();
        findViewById(R.id.btnConfirmPayment).setOnClickListener(v -> {
            if(validateInputs()) {
                processPayment();
            }
        });
    }
    private void setupListeners() {
        etBrttDay.setOnClickListener(v -> showDatePicker());
    }
    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            selectedDate = format.format(calendar.getTime());
            etBrttDay.setText(selectedDate);
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
    private boolean validateInputs() {
        String fullName = etFullName.getText().toString().trim();
        String cardNumber = etCardNumber.getText().toString().trim();
        String dob = etBrttDay.getText().toString().trim();
        boolean isValid = true;
        // Validate Full Name
        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            isValid = false;
        } else if (!fullName.matches("[a-zA-Z\\s]+")) {
            etFullName.setError("Invalid characters in name");
            isValid = false;
        } else {
            etFullName.setError(null);
        }
        // Validate Card Number
        if (cardNumber.isEmpty()) {
            etCardNumber.setError("Card number is required");
            isValid = false;
        } else {
            String cleanCard = cardNumber.replaceAll("[^0-9]", "");
            if (cleanCard.length() != 8) {
                etCardNumber.setError("Invalid card number (8 digits required)");
                isValid = false;
            } else {
                etCardNumber.setError(null);
            }
        }
        // Validate Date of Birth
        if (selectedDate == null || selectedDate.isEmpty()) {
            etBrttDay.setError("Please select a date");
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                sdf.setLenient(false);
                Date birthDate = sdf.parse(dob);
                if (birthDate.after(new Date())) {
                    etBrttDay.setError("Date cannot be in future");
                    isValid = false;
                } else {
                    etBrttDay.setError(null);
                }
            } catch (ParseException e) {
                etBrttDay.setError("Invalid date");
                isValid = false;
            }
        }

        return isValid;
    }
    private void processPayment() {
        // Process payment logic here
        CartManager.getInstance().clearCart();
        startActivity(new Intent(this, OrderConfirmationActivity.class));
        finish();
    }
}

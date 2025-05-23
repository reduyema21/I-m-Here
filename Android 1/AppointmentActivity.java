package com.example.healthcareapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AppointmentActivity extends AppCompatActivity {
    private EditText nameInput, phoneInput, dateInput, timeInput, messageInput;
    private Spinner departmentSpinner,doctorSpinner,serviceSpinner;
    private Button addAppointmentButton;
    private String selectedDate,selectedTime;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment);
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        messageInput = findViewById(R.id.messageInput);
        departmentSpinner = findViewById(R.id.departmentSpinner);
        doctorSpinner = findViewById(R.id.doctorSpinner);
        serviceSpinner = findViewById(R.id.serviceSpinner);
        addAppointmentButton = findViewById(R.id.addAppointmentButton);
        setupSpinners();
        setupListeners();
    }
    private void setupSpinners() {
        // Department Spinner
        ArrayAdapter<CharSequence> departmentAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.departments,
                android.R.layout.simple_spinner_item
        );
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(departmentAdapter);

        // Doctor Spinner
        ArrayAdapter<CharSequence> doctorAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.doctors,
                android.R.layout.simple_spinner_item
        );
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorSpinner.setAdapter(doctorAdapter);

        // Service Spinner
        ArrayAdapter<CharSequence> serviceAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.services,
                android.R.layout.simple_spinner_item
        );
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(serviceAdapter);
        addAppointmentButton.setOnClickListener(v -> {
            Toast.makeText(this, "Appointment Added Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
    private void setupListeners() {
        dateInput.setOnClickListener(v -> showDatePicker());
        timeInput.setOnClickListener(v -> showTimePicker());
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
            dateInput.setText(selectedDate);
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
    private void showTimePicker() {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select time")
                .build();

        timePicker.addOnPositiveButtonClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            timeInput.setText(selectedTime);
        });

        timePicker.show(getSupportFragmentManager(), "TIME_PICKER");
    }
    private void saveAppointment() {
        if (!validateInputs()) return;

        // Get values directly from UI components
        String patientName = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String date = selectedDate;
        String time = selectedTime;
        String message = messageInput.getText().toString().trim();
        String specialization = departmentSpinner.getSelectedItem().toString();
        String doctor = doctorSpinner.getSelectedItem().toString();
        String service = serviceSpinner.getSelectedItem().toString();
        String status = "PENDING";

        // Insert directly into database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        long result = dbHelper.addAppointment(patientName, phone, date,time,message,specialization, doctor, service, status);

        if(result == -1) {
            Toast.makeText(this, "Failed to save appointment", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    private boolean validateInputs() {
        if (nameInput.getText().toString().trim().isEmpty()) {
            nameInput.setError("Name is required");
            return false;
        }
        if (phoneInput.getText().toString().trim().isEmpty()) {
            phoneInput.setError("Phone number is required");
            return false;
        }
        if (selectedDate == null || selectedDate.isEmpty()) {
            dateInput.setError("Please select a date");
            return false;
        }
        if (selectedTime == null || selectedTime.isEmpty()) {
            timeInput.setError("Please select a time");
            return false;
        }
        return true;
    }
}
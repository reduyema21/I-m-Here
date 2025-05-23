package com.example.healthcareapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MedicationReminderActivity extends AppCompatActivity {
    private EditText etMedName, etDosage;
    private Button btnSave, btnTimePicker;
    private Spinner spFrequency;
    private DatabaseHelper dbHelper;
    private ReminderManager reminderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medication_reminder);
        dbHelper = new DatabaseHelper(this);
        reminderManager = new ReminderManager(this);

        etMedName = findViewById(R.id.etMedName);
        etDosage = findViewById(R.id.etDosage);
        spFrequency = findViewById(R.id.spFrequency);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.frequency_options, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrequency.setAdapter(adapter);

        btnSave.setOnClickListener(v ->saveMedication());
    }
    private void saveMedication() {
        long id = dbHelper.addMedication(
                etMedName.getText().toString(),
                etDosage.getText().toString(),
                spFrequency.getSelectedItem().toString(),
                "08:00,20:00", // Replace with actual time picker logic
                "Take with food", // Replace with actual input
                System.currentTimeMillis(),
                System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000,
                "after_meal",
                20
        );

        // Schedule reminders
        reminderManager.setReminder((int) id, "08:00");
        reminderManager.setReminder((int) id, "20:00");

        finish();
    }
}

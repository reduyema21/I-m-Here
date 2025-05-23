package com.example.healthcareapplication;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    private CardView Buy_medicine, Emergency, Appiontment, cardrecord,MedicationReminder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        // Initialize views
        Buy_medicine = findViewById(R.id.cardBuymedicine);
        Emergency = findViewById(R.id.cardEmergency);
        Appiontment = findViewById(R.id.cardappointment);
        cardrecord = findViewById(R.id.cardRecord);
        MedicationReminder= findViewById(R.id.cardmedicationreminder);
        // Set click listeners
        setupClickListeners();
    }
    private void setupClickListeners() {
        View.OnClickListener cardClickListener = view -> {
            Intent intent = null;
            String service = "";

            if(view.getId() == R.id.cardappointment){
                service = "appointment";
                try {
                     intent = new Intent(HomeActivity.this, DoctorListActivity.class);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "Unable to open Doctor List", Toast.LENGTH_SHORT).show();
                }
            }
            else if (view.getId() == R.id.cardRecord) {
                service = "record";
                intent = new Intent(this, MedicalrecordActivity.class);
            }
            else if (view.getId() == R.id.cardBuymedicine) {
                service = "buyMedication";
                intent = new Intent(this, BuymedicineActivity.class);
            }
            else if (view.getId() == R.id.cardEmergency) {
                service = "Emergency";
                intent = new Intent(this, EmergencyActivity.class);
            }
            else if (view.getId() == R.id.cardmedicationreminder) {
                service = "reminder";
                intent = new Intent(this, MedicationReminderActivity.class);
            }
            Toast.makeText(this, service + " service selected", Toast.LENGTH_SHORT).show();

            // Navigate to the selected activity
            if (intent != null) {
                startActivity(intent);
            }
        };

        Appiontment.setOnClickListener(cardClickListener);
        cardrecord.setOnClickListener(cardClickListener);
        Buy_medicine.setOnClickListener(cardClickListener);
        Emergency.setOnClickListener(cardClickListener);
        MedicationReminder.setOnClickListener(cardClickListener);
    }
}
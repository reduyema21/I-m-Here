package com.example.healthcareapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class DoctorDetailActivity extends AppCompatActivity {
    private int doctorId,imageResId;
    private String doctorName,specialized,descriptionn,service,workingHour;
    private float rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        // Initialize views
        ImageView doctorImage = findViewById(R.id.doctorImage);
        TextView Name = findViewById(R.id.doctorName);
        TextView specialization = findViewById(R.id.specialization);
        TextView description = findViewById(R.id.servicesDescription);
        TextView services = findViewById(R.id.services);
        TextView workingHours = findViewById(R.id.workingHours);
        RatingBar ratingBar= findViewById(R.id.ratingBar);
        Button bookAppointmentButton = findViewById(R.id.btnBookAppointment);

        // Get doctor data from intent
        Doctor doctor = getIntent().getParcelableExtra("DOCTOR_DATA");


        if (doctor != null) {
            doctorImage.setImageResource(doctor.getImageResId());
            Name.setText(String.valueOf(doctor.getDoctorName()));
            specialization.setText(String.valueOf(doctor.getSpecialization()));
            description.setText(String.valueOf(doctor.getDescription()));
            services.setText(String.valueOf(doctor.getService()));
            workingHours.setText(String.valueOf(doctor.getWorkingHour()));
            ratingBar.setRating(doctor.getRating());
        }
         else {
            // Handle null doctor case
            Toast.makeText(this, "Doctor data not available", Toast.LENGTH_SHORT).show();
            finish();
        }

        bookAppointmentButton.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorDetailActivity.this, AppointmentActivity.class);
            intent.putExtra("DOCTOR_DATA", doctor);
            startActivity(intent);

        });
    }
}
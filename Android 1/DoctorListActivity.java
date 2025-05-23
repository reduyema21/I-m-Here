package com.example.healthcareapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorListActivity extends AppCompatActivity {
    private List<Doctor> doctorList;
    private DatabaseHelper dbHelper;
    private RecyclerView doctorRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        dbHelper = new DatabaseHelper(this);

        doctorRecyclerView = findViewById(R.id.doctorRecyclerView);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        insertSampleDoctors();
        // Fetch again from database
        doctorList = dbHelper.getAllDoctors();
        doctorRecyclerView.setAdapter(new DoctorAdapter(doctorList));
    }
    private void insertSampleDoctors() {
        // Check if database is empty
        if (dbHelper.getAllDoctors().isEmpty()) {
            dbHelper.insertDoctor("Dr. John Doe", "Cardiologist",
                    "Expert in heart health", 4.5f, "Mon-Fri, 9 AM - 5 PM",
                    "Consultation, ECG", R.drawable.doctor1);

            dbHelper.insertDoctor("Dr. Jane Smith", "Dermatologist",
                    "Skin care specialist", 4.8f, "Tue-Sat, 10 AM - 6 PM",
                    "Acne Treatment", R.drawable.doctor);
            dbHelper.insertDoctor("Dr. Brown", "Pediatrician", "Child Wellness Specialist",
                    4.9f, "Mon-Thu, Sat, 8 AM - 4 PM", "Vaccinations", R.drawable.doctor2);
            dbHelper.insertDoctor("Dr. Ali", "Dentist", "Oral Health Expert", 4.8f,
                    "Mon-Fri, 8 AM - 5 PM", "Teeth Cleaning", R.drawable.doctor3);

        }
    }
    class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
        private final List<Doctor> doctors;

        public DoctorAdapter(List<Doctor> doctors) {
            this.doctors = doctors;
        }

        @NonNull
        @Override
        public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.doctor_item, parent, false);
            return new DoctorViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
            Doctor doctor = doctors.get(position);
            // Set doctor image
            holder.ivDoctorImage.setImageResource(doctor.getImageResId());
            // Set text fields
            holder.nameTextView.setText(doctor.getDoctorName());
            holder.specializationTextView.setText(doctor.getSpecialization());
            holder.tvWorkingHours.setText(doctor.getWorkingHour());
            // Set rating
            holder.ratingBar.setRating(doctor.getRating());

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(DoctorListActivity.this, DoctorDetailActivity.class);
                intent.putExtra("DOCTOR_DATA", doctor);
                startActivity(intent);
            });
        }
        @Override
        public int getItemCount() {
            return doctors.size();
        }
        class DoctorViewHolder extends RecyclerView.ViewHolder {
            ImageView ivDoctorImage;
            TextView  nameTextView, specializationTextView,tvWorkingHours;
            RatingBar ratingBar;

            public DoctorViewHolder(@NonNull View itemView) {
                super(itemView);
                ivDoctorImage = itemView.findViewById(R.id.ivDoctorImage);
                nameTextView = itemView.findViewById(R.id.tvDoctorName);
                specializationTextView = itemView.findViewById(R.id.tvSpecialization);
                tvWorkingHours = itemView.findViewById(R.id.tvWorkingHours);
                ratingBar = itemView.findViewById(R.id.ratingBar);
            }
        }
    }
}
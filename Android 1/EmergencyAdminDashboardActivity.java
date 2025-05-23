package com.example.healthcareapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.inappmessaging.model.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmergencyAdminDashboardActivity extends AppCompatActivity {

    private RecyclerView incidentsRecyclerView;
    private DatabaseReference alertsRef;
    private AlertsAdapter alertsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_admin_dashboard);

        incidentsRecyclerView = findViewById(R.id.incidents_recycler);
        incidentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        alertsAdapter = new AlertsAdapter(new ArrayList<>());
        incidentsRecyclerView.setAdapter(alertsAdapter);

        alertsRef = FirebaseDatabase.getInstance().getReference("alerts");

        alertsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<EmergencyAlert> alerts = new ArrayList<>();
                for (DataSnapshot alertSnapshot : snapshot.getChildren()) {
                    EmergencyAlert alert = alertSnapshot.getValue(EmergencyAlert.class);
                    if (alert != null) {
                        alert.setKey(alertSnapshot.getKey());  // Set the Firebase key
                        alerts.add(alert);
                    }
                }
                alertsAdapter.updateData(alerts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmergencyAdminDashboardActivity.this,
                        "Error loading alerts: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private class AlertsAdapter extends RecyclerView.Adapter<AlertViewHolder> {
        private List<EmergencyAlert> alerts;

        public AlertsAdapter(List<EmergencyAlert> alerts) {
            this.alerts = alerts;
        }

        public void updateData(List<EmergencyAlert> newAlerts) {
            this.alerts = newAlerts;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_alert, parent, false);
            return new AlertViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
            EmergencyAlert alert = alerts.get(position);
            holder.bind(alert);
        }

        @Override
        public int getItemCount() {
            return alerts.size();
        }
    }

    private static class AlertViewHolder extends RecyclerView.ViewHolder {
        private final TextView emergencyType;
        private final TextView timestamp;
        private final TextView location;
        private final TextView userIdView;
        private final View actionButton;
        private final View typeIndicator;
        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            emergencyType = itemView.findViewById(R.id.emergency_type);
            timestamp = itemView.findViewById(R.id.timestamp);
            location = itemView.findViewById(R.id.location);
            userIdView = itemView.findViewById(R.id.user_id);
            actionButton = itemView.findViewById(R.id.action_button);
            typeIndicator = itemView.findViewById(R.id.type_indicator);
        }
        public void bind(EmergencyAlert alert) {
            // Set emergency type display name from enum
            emergencyType.setText(alert.getEmergencyType().getDisplayName());

            // Format timestamp
            timestamp.setText(formatTimestamp(alert.getTimestamp()));

            // Format location coordinates
            location.setText(formatLocation(alert.getLatitude(), alert.getLongitude()));

            // Show user ID
            userIdView.setText("User: " + alert.getUserId());

            // Set indicator color based on emergency type
            switch (alert.getEmergencyType()) {
                case MEDICAL:
                    typeIndicator.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.green));
                    break;
                case FIRE:
                    typeIndicator.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.red_500));
                    break;
                case CRIME:
                    typeIndicator.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.Magenta));
                    break;
            }

            // Handle assign button click
            actionButton.setOnClickListener(v -> {
                // Implement assignment logic
                assignEmergencyToResponder(alert);
            });
        }
        private void assignEmergencyToResponder(EmergencyAlert alert) {
            // Update alert status in Firebase
            DatabaseReference alertRef = FirebaseDatabase.getInstance()
                    .getReference("alerts")
                    .child(alert.getKey());

            alertRef.child("assigned").setValue(true)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(itemView.getContext(),
                                "Alert assigned successfully",
                                Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(itemView.getContext(),
                                "Assignment failed: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    });
        }
        private static String formatTimestamp(long timestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }
        private static String formatLocation(double lat, double lng) {
            return String.format(Locale.getDefault(), "Lat: %.4f, Lng: %.4f", lat, lng);
        }
    }
}
package com.example.healthcareapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmergencyActivity extends AppCompatActivity {
    private ActivityResultLauncher<String[]> locationPermissionRequest;
    private LocationProvider locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_emergency);
        //Create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("location_channel",
                    "Location Tracking",
                    NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Initialize location permission request
        locationPermissionRequest = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    Boolean fineLocationGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                    Boolean coarseLocationGranted = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);
                    if ((fineLocationGranted != null && fineLocationGranted) ||
                            (coarseLocationGranted != null && coarseLocationGranted)) {
                        startLocationService();
                    } else {
                        Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
                    }
                });

        locationProvider = new LocationProvider(this);
        Button sosButton = findViewById(R.id.btnSos);

        checkPermissions();
        sosButton.setOnClickListener(v -> requestAndSendEmergency());
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            startLocationService();
        }
    }

    private void requestAndSendEmergency() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
            return;
        }

        locationProvider.getCurrentLocation(new LocationProvider.LocationResultCallback() {
            @Override
            public void onLocationReceived(Location location) {
                // Convert Location to latitude/longitude for Firebase
                EmergencyAlert alert = new EmergencyAlert(
                        getCurrentUserId(),
                        EmergencyAlert.EmergencyType.MEDICAL,
                        location.getLatitude(),
                        location.getLongitude()
                );
                sendAlertToServer(alert);
                showConfirmation();
            }

            @Override
            public void onLocationError(String error) {
                showError("Location Error: " + error);
            }
        });
    }

    private void showConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Alert Sent!")
                .setMessage("Help is on the way!")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        locationProvider.stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LocationProvider.LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestAndSendEmergency();
            }
        }
    }

    private String getCurrentUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void sendAlertToServer(EmergencyAlert alert) {
        DatabaseReference alertsRef = FirebaseDatabase.getInstance().getReference("alerts");
        // Create new reference first
        DatabaseReference newAlertRef = alertsRef.push();

        // Set the generated key in the alert
        alert.setKey(newAlertRef.getKey());

        newAlertRef.setValue(alert)
                .addOnSuccessListener(aVoid -> {
                    sendPushNotifications();
                    showConfirmation();
                })
                .addOnFailureListener(e -> showError("Failed to send alert: " + e.getMessage()));
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void startLocationService() {
        Intent serviceIntent = new Intent(this, LocationForegroundService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void sendPushNotifications() {
        // Implement FCM notification logic here
        // This would typically involve sending a message to your server
        // or using Firebase Cloud Messaging directly
        Toast.makeText(this, "Notifications sent to responders", Toast.LENGTH_SHORT).show();
    }
}
package com.example.healthcareapplication;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LocationForegroundService extends Service {
//    private FusedLocationProviderClient fusedLocationClient;
//    private LocationCallback locationCallback;

    @Override
    public void onCreate() {
        super.onCreate();
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = createNotification();
//        startForeground(1, notification);
        startLocationUpdates();
        return START_STICKY;
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, "location_channel")
                .setContentTitle("Location Tracking")
                .setContentText("Sharing your location with emergency services")
                .setSmallIcon(R.drawable.ic_location)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    private void startLocationUpdates() {
//        LocationRequest locationRequest = LocationRequest.create()
//                .setInterval(10000)
//                .setFastestInterval(5000)
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult != null) {
//                    Location location = locationResult.getLastLocation();
//                    updateLocationInFirebase(location);
//                }
//            }
//        };

//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.requestLocationUpdates(locationRequest,
//                    locationCallback,
//                    Looper.getMainLooper());
//        }
    }

    private void updateLocationInFirebase(Location location) {
        String userId = FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getUid() : null;

        if (userId != null && location != null) {
            FirebaseDatabase.getInstance().getReference("users")
                    .child(userId)
                    .child("location")
                    .setValue(new double[]{location.getLatitude(), location.getLongitude()});
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (fusedLocationClient != null && locationCallback != null) {
//            fusedLocationClient.removeLocationUpdates(locationCallback);
//        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
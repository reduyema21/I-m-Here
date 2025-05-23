package com.example.healthcareapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class LocationProvider {
    public static final int LOCATION_PERMISSION_CODE = 1001;
//    private final FusedLocationProviderClient fusedLocationClient;
//    private final Context context;
//    private LocationCallback locationCallback;

    public interface LocationResultCallback {
        void onLocationReceived(Location location);
        void onLocationError(String error);
    }

    public LocationProvider(Context context) {
//        this.context = context;
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void getCurrentLocation(LocationResultCallback callback) {
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                        PackageManager.PERMISSION_GRANTED) {
//
//            requestLocationPermission();
//            callback.onLocationError("Location permission not granted");
//            return;
//        }
//
//        LocationRequest locationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(10000)
//                .setFastestInterval(5000)
//                .setNumUpdates(1);
//
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                if (locationResult == null) {
//                    callback.onLocationError("Null location result");
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    if (location != null) {
//                        callback.onLocationReceived(location);
//                        fusedLocationClient.removeLocationUpdates(this);
//                        return;
//                    }
//                }
//                callback.onLocationError("No location received");
//            }
//        };
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//    }
//
//    private void requestLocationPermission() {
//        ActivityCompat.requestPermissions((Activity) context,
//                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                LOCATION_PERMISSION_CODE);
//    }
//
//    public void stopLocationUpdates() {
//        if (locationCallback != null) {
//            fusedLocationClient.removeLocationUpdates(locationCallback);
//        }
//    }

//    // Optional: Get last known location
//    public void getLastKnownLocation(LocationResultCallback callback) {
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                        PackageManager.PERMISSION_GRANTED) {
//
//            callback.onLocationError("Location permission not granted");
//            return;
//        }
//
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(location -> {
//                    if (location != null) {
//                        callback.onLocationReceived(location);
//                    } else {
//                        callback.onLocationError("Last known location not available");
//                    }
//                });
  }
}

package com.example.healthcareapplication;

import android.location.Location;

import com.google.firebase.database.Exclude;

import java.util.Date;

public class EmergencyAlert {
    private String userId;
    private EmergencyType emergencyType;
    private double latitude;
    private double longitude;
    private long timestamp;
    private String key;

    // Enum definition
    public enum EmergencyType {
        MEDICAL(0, "Medical Emergency"),
        FIRE(1, "Fire Emergency"),
        CRIME(2, "Crime Emergency");

        private final int code;
        private final String displayName;

        EmergencyType(int code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public int getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Default constructor (required for Firebase)
    public EmergencyAlert() {
    }

    // Constructor with enum type
    public EmergencyAlert(String userId, EmergencyType emergencyType, Location location) {
        this.userId = userId;
        this.emergencyType = emergencyType;
        this.timestamp = System.currentTimeMillis();

        if(location != null) {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
        }
    }

    // Constructor with coordinates
    public EmergencyAlert(String userId, EmergencyType emergencyType,
                          double latitude, double longitude) {
        this.userId = userId;
        this.emergencyType = emergencyType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    public String getUserId() { return userId; }
    public EmergencyType getEmergencyType() { return emergencyType; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public long getTimestamp() { return timestamp; }

    // Setters
    public void setUserId(String userId) { this.userId = userId; }
    public void setEmergencyType(EmergencyType emergencyType) { this.emergencyType = emergencyType; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    // Helper methods for Date conversion
    public Date getTimestampDate() {
        return new Date(timestamp);
    }
    public void setTimestampDate(Date date) {
        this.timestamp = date.getTime();
    }
    // Helper method to get location
    public Location getLocation() {
        Location location = new Location("EmergencyAlert");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
    @Override
    public String toString() {
        return "EmergencyAlert{" +
                "userId='" + userId + '\'' +
                ", emergencyType=" + emergencyType +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp +
                '}';
    }
    @Exclude
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
}
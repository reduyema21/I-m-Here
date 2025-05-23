// Doctor.java
package com.example.healthcareapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {
    private int did;
    private int imageResId;
    private String doctorName;
    private String specialization;
    private String description;
    private String service;
    private String workingHour;
    private float rating;

    public Doctor(int did, String doctorName, String specialization, String description,
                  float rating, String workingHour, int imageResId, String service) {
        this.did = did;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.description = description;
        this.rating = rating;
        this.workingHour = workingHour;
        this.imageResId = imageResId;
        this.service = service;
    }

    protected Doctor(Parcel in) {
        did = in.readInt();
        imageResId = in.readInt();
        doctorName = in.readString();
        specialization = in.readString();
        description = in.readString();
        workingHour = in.readString();
        service = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(did);
        dest.writeInt(imageResId);
        dest.writeString(doctorName);
        dest.writeString(specialization);
        dest.writeString(description);
        dest.writeString(workingHour);
        dest.writeString(service);
        dest.writeFloat(rating);
    }

    // Getters
    public int getDid() { return did; }
    public String getDoctorName() { return doctorName; }
    public String getSpecialization() { return specialization; }
    public String getDescription() { return description; }
    public float getRating() { return rating; }
    public String getWorkingHour() { return workingHour; }
    public int getImageResId() { return imageResId; }
    public String getService() { return service; }
}
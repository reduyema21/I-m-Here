package com.example.healthcareapplication;

import java.io.Serializable;

public class Medicine implements Serializable {
    private String name;
    private String dosage;
    private int imageResId;
    private double price;
    private boolean availability;
    private String description;

    public Medicine(String name, String dosage, int imageResId, double price, boolean availability, String description) {
        this.name = name;
        this.dosage = dosage;
        this.imageResId = imageResId;
        this.price = price;
        this.availability = availability;
        this.description = description;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getDosage() { return dosage; }
    public int getImageResId() { return imageResId; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return availability; }
    public String getDescription() { return description;}
    }

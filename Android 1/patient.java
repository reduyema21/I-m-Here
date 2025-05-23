package com.example.healthcareapplication;


import java.io.Serializable;
import java.util.regex.Pattern;

public class patient implements Serializable {
    private static String name, phone, email,dob, address, gender;
    private static int card;
    public patient(String name, String phone, String email, String dob, int card,String address, String gender) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
        this.card = card;
        this.address = address;
        this.gender = gender;
    }

    // Getters
    public static String getName() {
        return name;
    }
    public static String getPhone() {
        return phone;
    }
    public static String getEmail() {
        return email;
    }
    public static String getDob() {
        return dob;
    }
    public static int getCard() {
        return card;
    }
    public static String getAddress() {
        return address;
    }
    public static String getGender() {
        return gender;
    }
}
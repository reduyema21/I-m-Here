package com.example.healthcareapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }
    public void saveUser(String email, String password) {
        editor.putString("userEmail", email);
        editor.putString("userPassword", password);
        editor.apply();
    }
    public void saveLoginState(boolean isLoggedIn) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("IS_LOGGED_IN", isLoggedIn);
        editor.apply();
    }

    public String getEmail() {

        return prefs.getString("userEmail", null);
    }

    public String getPassword() {

        return prefs.getString("userPassword", null);
    }
}

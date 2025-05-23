package com.example.healthcareapplication;

import android.os.Bundle;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PatientRecordActivity extends AppCompatActivity {
    private EditText etSearchPhone;
    private Button btnSearch;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_record);
        etSearchPhone = findViewById(R.id.etSearchPhone);
        btnSearch = findViewById(R.id.btnSearch);
        dbHelper = new DatabaseHelper(this);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPatient();
            }
        });
    }
    private void searchPatient() {
        String phone = etSearchPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            Toast.makeText(this, "Please enter phone number!", Toast.LENGTH_SHORT).show();
            return;
        }
        patient existingPatient = dbHelper.getPatient(phone);

        if (existingPatient != null) {
            showPatientDetails(existingPatient);
        } else {
            Toast.makeText(this, "No patient found with this phone number!", Toast.LENGTH_SHORT).show();
        }
    }
    private void showPatientDetails(patient patient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Patient Record")
                .setMessage(
                        "Name: " + patient.getName() + "\n" +
                                "Phone: " + patient.getPhone() + "\n" +
                                "Email: " + patient.getEmail() + "\n" +
                                "DOB: " + patient.getDob() + "\n" +
                                "CNumber: " + patient.getCard() + "\n" +
                                "Address: " + patient.getAddress() + "\n" +
                                "Gender: " + patient.getGender()
                )
                .setPositiveButton("OK", null)
                .show();
    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

}
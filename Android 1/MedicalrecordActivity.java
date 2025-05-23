package com.example.healthcareapplication;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
public class MedicalrecordActivity extends AppCompatActivity  {
    private EditText etName, etPhone, etEmail, etDob, etcardNumber,etAddress;
    private TextView haveAccount;
    private Button btnSubmit;
    private Spinner genderSpinner;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<CharSequence> genderAdapter;

    @SuppressLint({"Range", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicalrecord);
        etName    = findViewById(R.id.patientName);
        etPhone   = findViewById(R.id.etPhone);
        etEmail   = findViewById(R.id.etPatientEmail);
        etDob     = findViewById(R.id.etPatientDob);
        etAddress = findViewById(R.id.etPatientAddress);
        genderSpinner  = findViewById(R.id.etPatientsGender);
        etcardNumber   = findViewById(R.id.cardNumber);
        btnSubmit = findViewById(R.id.btnSubmit);
        haveAccount = findViewById(R.id.teHaveAccount);

        dbHelper = new DatabaseHelper(this);

        setupDatePicker();
        setupGenderSpinner();
        setupSubmitButton();
        haveAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MedicalrecordActivity.this, PatientRecordActivity.class);
            startActivity(intent);
        });
    }
    private void setupDatePicker() {
        etDob.setOnClickListener(v -> showDatePickerDialog());
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) -> {
            String selectedDate = day + "/" + (month + 1) + "/" + year;
            etDob.setText(selectedDate);}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void setupGenderSpinner() {
        genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.sex, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
    }
    private void setupSubmitButton() {
        btnSubmit.setOnClickListener(v -> handlePatientData());
    }
    private void handlePatientData() {
        String phone = etPhone.getText().toString().trim();

        // Check if patient exists
        patient existingPatient = dbHelper.getPatient(phone);
        if (existingPatient != null) {
            showPatientDetails(existingPatient);
        } else {
            if (validateInput()) {
                registerNewPatient();
            }
        }
    }
    private boolean validateInput() {
        boolean valid = true;
        String emailInput = etEmail.getText().toString().trim();

        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Name required");
            valid = false;
        }

        if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setError("Phone required");
            valid = false;
        }

        if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmail.setError("Valid email required");
            valid = false;
        }

        if (etDob.getText().toString().trim().isEmpty()) {
            etDob.setError("Date of birth required");
            valid = false;
        }
        if (etcardNumber.getText().toString().isEmpty()){
            etcardNumber.setError("card number is required");
        }
        if (etAddress.getText().toString().trim().isEmpty()){
            etAddress.setError("Address is required");
        }
        if (genderSpinner.getSelectedItemPosition() == 0) {
            TextView errorText = (TextView) genderSpinner.getSelectedView();
            errorText.setError("Gender is required");
            errorText.setTextColor(Color.RED);
            errorText.setText("Gender is required");
            valid = false;
        }
        return valid;
    }
    private void registerNewPatient() {
        // Validate general input first
        if (!validateInput()) {
            return;
        }

        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String card = etcardNumber.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();

        // Validate card number format
        if (card.isEmpty()) {
            etcardNumber.setError("Card number required");
            return;
        }

        try {
            int cardNumber = Integer.parseInt(card);

            // Insert into database
            long result = dbHelper.addPatient(name, phone, email, dob, cardNumber, address, gender);

            if (result != -1) {
                // Success case
                Toast.makeText(this, "Patient registered successfully!", Toast.LENGTH_SHORT).show();

                // Navigate to patient records
                Intent intent = new Intent(this, PatientRecordActivity.class);
                startActivity(intent);

                // Clear form fields
                clearForm();
            } else {
                // Database error
                Toast.makeText(this, "Registration failed. Patient may already exist!", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            // Handle invalid card number format
            etcardNumber.setError("Invalid card number format");
            etcardNumber.requestFocus();
        } catch (Exception e) {
            // General error handling
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void showPatientDetails(patient patient) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Existing Patient Record")
                .setMessage("Name: " + patient.getName() + "\n\n" +
                        "Phone: " + patient.getPhone() + "\n\n" +
                        "Email: " + patient.getEmail() + "\n\n" +
                        "DOB: " + patient.getDob() + "\n\n" +
                        "CNumber: " + patient.getCard() + "\n\n" +
                        "Address: " + patient.getAddress() + "\n\n" +
                        "Gender: " + patient.getGender())
                .setPositiveButton("OK", null)
                .show();
    }
    private void clearForm() {
        etName.setText("");
        etPhone.setText("");
        etEmail.setText("");
        etDob.setText("");
        etcardNumber.setText("");
        etAddress.setText("");
        genderSpinner.setSelection(0);
    }
    @Override
    protected void onDestroy() {
        if(dbHelper != null) {
            dbHelper.close();
        }
        super.onDestroy();
    }

}

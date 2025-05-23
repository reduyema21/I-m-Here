package com.example.healthcareapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class ReportActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report);
        EditText description = findViewById(R.id.description);
        Button attachPhoto = findViewById(R.id.attach_photo);
        Button submit = findViewById(R.id.submit_report);
        attachPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

//        submit.setOnClickListener(v -> {
//            EmergencyReport report = new EmergencyReport(
//                    description.getText().toString(),
//                    imageUri != null ? imageUri.toString() : null,
//                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
//                    new Date()
//            );
//
//            FirebaseDatabase.getInstance().getReference("reports")
//                    .push().setValue(report)
//                    .addOnSuccessListener(aVoid -> finish());
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            // Show preview of selected image
        }
    }
}
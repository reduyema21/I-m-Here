package com.example.healthcareapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.time.Instant;

public class MedicineDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);

        Medicine medicine = (Medicine) getIntent().getSerializableExtra("medicine");

        ImageView imageView = findViewById(R.id.medicineImage);
        TextView name = findViewById(R.id.medicineName);
        TextView dosage = findViewById(R.id.medicineDosage);
        TextView price = findViewById(R.id.medicinePrice);
        TextView description = findViewById(R.id.medicineDescription);

        imageView.setImageResource(medicine.getImageResId());
        name.setText(medicine.getName());
        dosage.setText(medicine.getDosage());
        price.setText(String.format("$%.2f", medicine.getPrice()));
        description.setText(medicine.getDescription());

        findViewById(R.id.addToCartButton).setOnClickListener(v -> {
            CartManager.getInstance().addToCart(medicine);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CartActivity.class));
        });
    }
}
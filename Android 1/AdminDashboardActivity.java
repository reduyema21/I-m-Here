package com.example.healthcareapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        findViewById(R.id.btnManageInventory).setOnClickListener(v -> {
//            startActivity(new Intent(this, InventoryManagementActivity.class));
        });

        findViewById(R.id.btnManageOrders).setOnClickListener(v -> {
//            startActivity(new Intent(this, OrderManagementActivity.class));
        });
    }
}
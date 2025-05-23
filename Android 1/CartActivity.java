package com.example.healthcareapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cartRecyclerView);
        totalPrice = findViewById(R.id.totalPrice);

        setupRecyclerView();
        updateTotal();

        findViewById(R.id.checkoutButton).setOnClickListener(v -> {
            startActivity(new Intent(this, CheckoutActivity.class));
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(CartManager.getInstance().getCartItems());
        recyclerView.setAdapter(adapter);
    }

    private void updateTotal() {
        double total = CartManager.getInstance().getTotalPrice();
        totalPrice.setText(String.format("Total: $%.2f", total));
    }
}
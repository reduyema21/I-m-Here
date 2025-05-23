package com.example.healthcareapplication;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartManager {
    private static CartManager instance;
    private List<Medicine> cartItems = new ArrayList<>();

    private CartManager() {}

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(@NonNull Medicine medicine) {
        cartItems.add(medicine);
    }

    public List<Medicine> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        return cartItems.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Medicine::getPrice)
                .sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}

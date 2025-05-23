package com.example.healthcareapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final List<Medicine> cartItems;

    public CartAdapter(List<Medicine> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine medicine = cartItems.get(position);
        holder.medicineName.setText(medicine.getName());
        holder.medicinePrice.setText(String.format("$%.2f", medicine.getPrice()));
        holder.medicineQuantity.setText("Qty: 1");
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicineName, medicinePrice, medicineQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.cartItemName);
            medicinePrice = itemView.findViewById(R.id.cartItemPrice);
            medicineQuantity = itemView.findViewById(R.id.cartItemQuantity);
        }
    }
}

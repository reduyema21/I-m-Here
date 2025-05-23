package com.example.healthcareapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private List<Medicine> medicines;
    private OnMedicineListener onMedicineListener;

    public interface OnMedicineListener {
        void onMedicineClick(int position);
    }

    public MedicineAdapter(List<Medicine> medicines, OnMedicineListener onMedicineListener) {
        this.medicines = medicines;
        this.onMedicineListener = onMedicineListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine, parent, false);
        return new ViewHolder(view, onMedicineListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine medicine = medicines.get(position);

        // Set medicine data to views
        holder.medicineName.setText(medicine.getName());
        holder.medicineDosage.setText(medicine.getDosage());
        holder.medicinePrice.setText(String.format("$%.2f", medicine.getPrice()));

//        // Load image using Glide
//        Glide.with(holder.itemView.getContext())
//                .load(medicine.getImageUrl())
//                .placeholder(R.drawable.ic_placeholder)
//                .into(holder.medicineImage);

    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView medicineImage;
        TextView medicineName, medicineDosage, medicinePrice, stockStatus;
        OnMedicineListener onMedicineListener;

        public ViewHolder(@NonNull View itemView, OnMedicineListener onMedicineListener) {
            super(itemView);
            this.onMedicineListener = onMedicineListener;

            // Initialize views
            medicineImage = itemView.findViewById(R.id.medicineImage);
            medicineName = itemView.findViewById(R.id.medicineName);
            medicineDosage = itemView.findViewById(R.id.medicineDosage);
            medicinePrice = itemView.findViewById(R.id.medicinePrice);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onMedicineListener != null) {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    onMedicineListener.onMedicineClick(position);
                }
            }
        }
    }
}
//package com.example.healthcareapplication;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.RatingBar;
//import android.widget.TextView;
//
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
//    private final List<Doctor> doctorList;
//    private final Context context;
//
//    public DoctorAdapter(Context context, List<Doctor> doctorList) {
//        this.context = context;
//        this.doctorList = doctorList;
//    }
//
//    @NonNull
//    @Override
//    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.doctor_item, parent, false);
//        return new DoctorViewHolder(view);
//    }
//    @Override
//    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
//        Doctor doctor = doctorList.get(position);
//
//        // Set doctor image
//        holder.ivDoctorImage.setImageResource(doctor.getImageResId());
//
//        // Set text fields
//        holder.tvDoctorName.setText(doctor.getDoctorName());
//        holder.tvSpecialization.setText(doctor.getSpecialization());
//        holder.tvWorkingHours.setText(doctor.getWorkingHour());
//
//        // Set rating
//        holder.ratingBar.setRating(doctor.getRating());
//
//        // Handle item click
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DoctorDetailActivity.class);
//            intent.putExtra("DOCTOR_DATA", doctor);
//            context.startActivity(intent);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return doctorList.size();
//    }
//
//    static class DoctorViewHolder extends RecyclerView.ViewHolder {
//        ImageView ivDoctorImage;
//        TextView tvDoctorName, tvSpecialization, tvWorkingHours;
//        RatingBar ratingBar;
//
//        public DoctorViewHolder(@NonNull View itemView) {
//            super(itemView);
//            ivDoctorImage = itemView.findViewById(R.id.ivDoctorImage);
//            tvDoctorName = itemView.findViewById(R.id.tvDoctorName);
//            tvSpecialization = itemView.findViewById(R.id.tvSpecialization);
//            tvWorkingHours = itemView.findViewById(R.id.tvWorkingHours);
//            ratingBar = itemView.findViewById(R.id.ratingBar);
//        }
//    }
//}

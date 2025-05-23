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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BuymedicineActivity extends AppCompatActivity implements MedicineAdapter.OnMedicineListener {

    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buymedicine);

        // Initialize sample data
        medicineList = BuymedicineActivity.getSampleMedicines();

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicineAdapter(medicineList, this);
        recyclerView.setAdapter(adapter);

        // Cart button
        findViewById(R.id.cartButton).setOnClickListener(v -> {
            startActivity(new Intent(BuymedicineActivity.this, CartActivity.class));
        });

        // Admin access (hidden button for demo)
        findViewById(R.id.adminButton).setOnClickListener(v -> {
            startActivity(new Intent(BuymedicineActivity.this, AdminDashboardActivity.class));
        });
    }

    public static List<Medicine> getSampleMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine("Paracetamol", "500mg", R.drawable.ic_parastamol, 1.50, true,"Pain reliever and fever reducer. For temporary relief of minor aches and pains."));
        medicines.add(new Medicine("Ibuprofen", "200mg", R.drawable.ic_ibuprofen, 2.00, true,"NSAID for pain relief, reduces inflammation, and lowers fever."));
        medicines.add(new Medicine("Amoxicillin", "250mg", R.drawable.ic_amoxicillin, 3.00, false,"Treats bacterial infections (ear, throat, skin). Penicillin-type; ineffective against viral infections. Common side effect: stomach upset."));
        medicines.add(new Medicine("Cetirizine", "10mg", R.drawable.ic_cetirizine, 1.20, true,"Relieves allergy symptoms (sneezing, itching, hives). Non-drowsy formula; taken once daily. Avoid alcohol (may enhance drowsiness)."));
        medicines.add(new Medicine("Metformin", "500mg", R.drawable.ic_metformin, 2.50, true,"Manages type 2 diabetes by lowering blood sugar. Improves insulin sensitivity. Common side effect: mild GI discomfort (usually temporary)."));
        return medicines;
    }
    @Override
    public void onMedicineClick(int position) {
        Intent intent = new Intent(this, MedicineDetailActivity.class);
        intent.putExtra("medicine", medicineList.get(position));
        startActivity(intent);
    }
}

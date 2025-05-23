package com.example.healthcareapplication;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static List<Medicine> getSampleMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        medicines.add(new Medicine("Paracetamol", "500mg", R.drawable.ic_parastamol, 1.50, true,"Pain reliever and fever reducer. For temporary relief of minor aches and pains."));
        medicines.add(new Medicine("Ibuprofen", "200mg", R.drawable.ic_ibuprofen, 2.00, true,"NSAID for pain relief, reduces inflammation, and lowers fever."));
        medicines.add(new Medicine("Amoxicillin", "250mg", R.drawable.ic_amoxicillin, 3.00, false,"Treats bacterial infections (ear, throat, skin). Penicillin-type; ineffective against viral infections. Common side effect: stomach upset."));
        medicines.add(new Medicine("Cetirizine", "10mg", R.drawable.ic_cetirizine, 1.20, true,"Relieves allergy symptoms (sneezing, itching, hives). Non-drowsy formula; taken once daily. Avoid alcohol (may enhance drowsiness)."));
        medicines.add(new Medicine("Metformin", "500mg", R.drawable.ic_metformin, 2.50, true,"Manages type 2 diabetes by lowering blood sugar. Improves insulin sensitivity. Common side effect: mild GI discomfort (usually temporary)."));
        return medicines;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DrugModel {
    private ArrayList<Drug> drugList;

    public DrugModel() {
        this.drugList = new ArrayList<>();
        // Adding dummy data for testing
        drugList.add(new Drug("Paracetamol", 100, "PharmaCo", 5.0));
        drugList.add(new Drug("Amoxicillin", 45, "BioHealth", 12.00));
        drugList.add(new Drug("Ibuprofen", 200, "MediLine", 8.25));
        drugList.add(new Drug("Cetirizine", 80, "GlobalCare", 4.10));
        drugList.add(new Drug("Metformin", 150, "LifePharma", 15.00));
        drugList.add(new Drug("Atorvastatin", 60, "PureMeds", 22.50));
        drugList.add(new Drug("Omeprazole", 95, "HealthFirst", 10.75));
        drugList.add(new Drug("Amlodipine", 110, "CureAll", 9.30));
        drugList.add(new Drug("Azithromycin", 30, "BioHealth", 18.20));
        drugList.add(new Drug("Losartan", 75, "PharmaCo", 11.40));
        
    }

    // Create
    public void addDrug(Drug drug) {
        drugList.add(drug);
    }

    // Read
    public ArrayList<Drug> getAllDrugs() {
        return drugList;
    }

    // Update
    public void updateDrug(int index, Drug updatedDrug) {
        if (index >= 0 && index < drugList.size()) {
            drugList.set(index, updatedDrug);
        }
    }

    // Delete
    public void deleteDrug(int index) {
        if (index >= 0 && index < drugList.size()) {
            drugList.remove(index);
        }
    }
}

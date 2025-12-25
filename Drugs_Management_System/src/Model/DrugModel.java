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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;

/**
 * Structured DrugModel containing inventory data with image paths and descriptions.
 * @author ASUS
 */
public class DrugModel {
    private ArrayList<Drug> drugList;

    public DrugModel() {
        this.drugList = new ArrayList<>();

       

        drugList.add(new Drug(
                "Amoxicillin",
                45,
                "BioHealth",
                12.00,
                "/images/Amoxicillin.jpg",
                "Amoxicillin is an antibiotic used to treat bacterial infections."
        ));

        drugList.add(new Drug(
                "Ibuprofen",
                200,
                "MediLine",
                8.25,
                "/images/Ibuprofen.jpg",
                "Ibuprofen helps reduce pain, inflammation, and fever."
        ));

        drugList.add(new Drug(
                "Cetirizine",
                80,
                "GlobalCare",
                4.10,
                "/images/Cetirizine.jpg",
                "Cetirizine is an antihistamine used to relieve allergy symptoms."
        ));

        drugList.add(new Drug(
                "Metformin",
                150,
                "LifePharma",
                15.00,
                "/images/Metformin.jpg",
                "Metformin is used to control blood sugar levels in type 2 diabetes."
        ));

        drugList.add(new Drug(
                "Atorvastatin",
                60,
                "PureMeds",
                22.50,
                "/images/Atorvastain.jpg",
                "Atorvastatin helps lower cholesterol and reduce the risk of heart disease."
        ));

        drugList.add(new Drug(
                "Omeprazole",
                95,
                "HealthFirst",
                10.75,
                "/images/Omeprazole.jpg",
                "Omeprazole reduces stomach acid and treats acid reflux."
        ));

        drugList.add(new Drug(
                "Amlodipine",
                110,
                "CureAll",
                9.30,
                "/images/Amlodipine.jpg",
                "Amlodipine is used to treat high blood pressure and chest pain."
        ));

        drugList.add(new Drug(
                "Azithromycin",
                30,
                "BioHealth",
                18.20,
                "/images/Azithromycin.jpg",
                "Azithromycin is an antibiotic used for respiratory and skin infections."
        ));

        drugList.add(new Drug(
                "Losartan",
                75,
                "PharmaCo",
                11.40,
                "/images/Losartan.jpg",
                "Losartan helps treat high blood pressure and protect the kidneys."
        ));
    }

    // CRUD Methods

    /**
     * Adds a new drug to the list.
     */
    public void addDrug(Drug drug) {
        drugList.add(drug);
    }

    /**
     * Returns the entire list of drugs.
     */
    public ArrayList<Drug> getAllDrugs() {
        return drugList;
    }

    /**
     * Updates an existing drug at a specific index.
     */
    public void updateDrug(int index, Drug updatedDrug) {
        if (index >= 0 && index < drugList.size()) {
            drugList.set(index, updatedDrug);
        }
    }

    /**
     * Deletes a drug from the list by index.
     */
    public void deleteDrug(int index) {
        if (index >= 0 && index < drugList.size()) {
            drugList.remove(index);
        }
    }
}
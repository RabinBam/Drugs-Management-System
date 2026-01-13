/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Drug;
import Model.DrugModel;
import java.util.ArrayList;

public class LinearSearch {

 
    public static ArrayList<Drug> search(DrugModel model, String keyword) {

        ArrayList<Drug> results = new ArrayList<>();

        if (model == null || keyword == null || keyword.isEmpty()) {
            return results;
        }

        String searchKey = keyword.toLowerCase();

        for (Drug drug : model.getAllDrugs()) {

            // Safe null checks
            String name = drug.getName() != null ? drug.getName().toLowerCase() : "";
            String description = drug.getDescription() != null ? drug.getDescription().toLowerCase() : "";

            if (name.contains(searchKey) || description.contains(searchKey)) {
                results.add(drug);
            }
        }

        return results;
    }
}

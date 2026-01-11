/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Drug;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class SortByName {
    
    public static void SortByName(ArrayList<Drug> drugList) {
        int n = drugList.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                // Compare drug names (case-insensitive)
                if (drugList.get(j).getName()
                        .compareToIgnoreCase(drugList.get(minIndex).getName()) < 0) {
                    minIndex = j;
                }
            }

            // Swap
            Drug temp = drugList.get(minIndex);
            drugList.set(minIndex, drugList.get(i));
            drugList.set(i, temp);
        }
    }
}

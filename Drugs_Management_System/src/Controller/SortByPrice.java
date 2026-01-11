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
public class SortByPrice {
    
       public static void sortByPrice(ArrayList<Drug> drugList) {
        int n = drugList.size();

        for (int i = 1; i < n; i++) {
            Drug key = drugList.get(i);
            double keyPrice = key.getUnitCost();

            int j = i - 1;

            
            while (j >= 0 && drugList.get(j).getUnitCost() > keyPrice) {
                drugList.set(j + 1, drugList.get(j));
                j--;
            }

           
            drugList.set(j + 1, key);
        }
    }
    
}

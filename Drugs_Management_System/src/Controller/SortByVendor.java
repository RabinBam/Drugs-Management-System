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
public class SortByVendor {
    
     public static void sort(ArrayList<Drug> drugList) {
        if (drugList == null || drugList.size() <= 1) {
            return;
        }
        mergeSort(drugList, 0, drugList.size() - 1);
    }

    // Merge Sort recursive method
    private static void mergeSort(ArrayList<Drug> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);

            merge(list, left, mid, right);
        }
    }

    // Merge two sorted halves
    private static void merge(ArrayList<Drug> list, int left, int mid, int right) {
        ArrayList<Drug> temp = new ArrayList<>();

        int i = left;
        int j = mid + 1;

        while (i <= mid && j <= right) {
            String vendor1 = list.get(i).getVendor();
            String vendor2 = list.get(j).getVendor();

            if (vendor1.compareToIgnoreCase(vendor2) <= 0) {
                temp.add(list.get(i));
                i++;
            } else {
                temp.add(list.get(j));
                j++;
            }
        }

        while (i <= mid) {
            temp.add(list.get(i));
            i++;
        }

        while (j <= right) {
            temp.add(list.get(j));
            j++;
        }

        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }
}

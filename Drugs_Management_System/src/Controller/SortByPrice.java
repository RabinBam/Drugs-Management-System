package Controller;

import Model.Drug;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author ASUS
 */
public class SortByPrice {

    /**
     * Existing ArrayList implementation
     */
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

    public static void sortByPriceLinkedList(LinkedList<Drug> drugList) {
        if (drugList == null || drugList.size() <= 1) return;

        LinkedList<Drug> sortedList = new LinkedList<>();

        for (Drug current : drugList) {
            if (sortedList.isEmpty()) {
                sortedList.add(current);
            } else {
                // Find position to insert 'current' drug
                ListIterator<Drug> it = sortedList.listIterator();
                boolean inserted = false;
                while (it.hasNext()) {
                    if (it.next().getUnitCost() > current.getUnitCost()) {
                        it.previous(); // step back to correct position
                        it.add(current);
                        inserted = true;
                        break;
                    }
                }
                if (!inserted) {
                    sortedList.addLast(current);
                }
            }
        }

        // Reflect changes back to the original list without breaking references
        drugList.clear();
        drugList.addAll(sortedList);
    }
}
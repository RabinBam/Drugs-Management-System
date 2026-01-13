package Controller;

import Model.Drug;
import java.util.ArrayList;
import java.util.Comparator;

public class SearchController {
    
    /**
     * Performs Binary Search on the drug list by name.
     * Note: List must be sorted by name first.
     */
    public static ArrayList<Drug> binarySearchByName(ArrayList<Drug> originalList, String query) {
        if (query == null || query.isEmpty()) {
            return originalList;
        }

        // 1. Sort the list (Requirement for Binary Search)
        ArrayList<Drug> sortedList = new ArrayList<>(originalList);
        sortedList.sort(Comparator.comparing(Drug::getName, String.CASE_INSENSITIVE_ORDER));

        ArrayList<Drug> results = new ArrayList<>();
        String searchKey = query.toLowerCase();

        int low = 0;
        int high = sortedList.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            Drug midDrug = sortedList.get(mid);
            String midName = midDrug.getName().toLowerCase();

            if (midName.contains(searchKey)) {
                // Since Binary Search finds one instance, we expand outwards 
                // to find all partial matches (common in search bars)
                results.add(midDrug);
                
                // Look left
                int left = mid - 1;
                while (left >= 0 && sortedList.get(left).getName().toLowerCase().contains(searchKey)) {
                    results.add(sortedList.get(left));
                    left--;
                }
                // Look right
                int right = mid + 1;
                while (right < sortedList.size() && sortedList.get(right).getName().toLowerCase().contains(searchKey)) {
                    results.add(sortedList.get(right));
                    right++;
                }
                break;
            } else if (midName.compareTo(searchKey) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return results;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import Controller.DrugStack;
/**
 *
 * @author ASUS
 */
public class History {
    
        private DrugStack soldDrugHistory;

    public History() {
        soldDrugHistory = new DrugStack();
    }

    // Record a sold drug
    public void addSoldDrug(Drug drug) {
        soldDrugHistory.push(drug);
    }

    // Get most recently sold drug
    public Drug getLastSoldDrug() {
        return soldDrugHistory.peek();
    }

    // Undo last sale
    public Drug undoLastSale() {
        return soldDrugHistory.pop();
    }

    // Check if there is any sales history
    public boolean isEmpty() {
        return soldDrugHistory.isEmpty();
    }

    // Total number of sales
    public int getTotalSales() {
        return soldDrugHistory.size();
    }

    // Clear sales history
    public void clearHistory() {
        soldDrugHistory.clear();
    }

    // Access full history (for UI or reports)
    public DrugStack getHistoryStack() {
        return soldDrugHistory;
    }
}

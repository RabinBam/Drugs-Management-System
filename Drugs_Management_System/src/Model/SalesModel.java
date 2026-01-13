package Model;

import Controller.DrugQueue;
import java.util.ArrayList;

/**
 * Handles drug sales using a queue
 * @author ASUS
 */
public class SalesModel {

    private DrugQueue salesQueue;

    public SalesModel() {
        salesQueue = new DrugQueue();
    }

    /**
     * Add drug to sales queue
     */
    public void addToSales(Drug drug) {
        salesQueue.enqueue(drug);
    }

    /**
     * Process next sale (FIFO)
     */
    public Drug processNextSale() {
        return salesQueue.dequeue();
    }

    /**
     * View next drug to be sold
     */
    public Drug viewNextSale() {
        return salesQueue.peek();
    }

    /**
     * Check if sales queue is empty
     */
    public boolean isSalesQueueEmpty() {
        return salesQueue.isEmpty();
    }

    /**
     * Get all drugs currently in sales queue
     */
    public ArrayList<Drug> getAllSales() {
        return salesQueue.getAllQueuedDrugs();
    }

    /**
     * Get number of pending sales
     */
    public int getSalesCount() {
        return salesQueue.size();
    }

    /**
     * Clear all sales
     */
    public void clearSales() {
        salesQueue.clear();
    }
}

package Controller;

import Model.Drug;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DrugQueue {

    private ArrayList<Drug> queue;

    public DrugQueue() {
        queue = new ArrayList<>();
    }

    /**
     * Add drug to the queue (ENQUEUE)
     */
    public void enqueue(Drug drug) {
        if (drug != null) {
            queue.add(drug);
        }
    }

    /**
     * Remove and return first drug (DEQUEUE)
     */
    public Drug dequeue() {
        if (!isEmpty()) {
            return queue.remove(0); // FIFO
        }
        return null;
    }

    /**
     * View first drug without removing
     */
    public Drug peek() {
        if (!isEmpty()) {
            return queue.get(0);
        }
        return null;
    }

    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Get queue size
     */
    public int size() {
        return queue.size();
    }

    /**
     * Clear queue
     */
    public void clear() {
        queue.clear();
    }

    /**
     * Get all queued drugs
     */
    public ArrayList<Drug> getAllQueuedDrugs() {
        return queue;
    }
}

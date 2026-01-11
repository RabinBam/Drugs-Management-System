/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.util.ArrayList;
import Model.Drug;
/**
 *
 * @author ASUS
 */
public class DrugStack  {
   

    private ArrayList<Drug> stack;
    private int top;

    public DrugStack() {
        stack = new ArrayList<>();
        top = -1; // stack starts empty
    }

    // Push a sold drug into history
    public void push(Drug drug) {
        if (drug != null) {
            stack.add(drug);
            top++;
        }
    }

    // Remove and return the last sold drug
    public Drug pop() {
        if (!isEmpty()) {
            Drug removedDrug = stack.remove(top);
            top--;
            return removedDrug;
        }
        return null;
    }

    // View the last sold drug without removing it
    public Drug peek() {
        if (!isEmpty()) {
            return stack.get(top);
        }
        return null;
    }

    // Check if history is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Number of sold drugs in history
    public int size() {
        return top + 1;
    }

    // Clear entire history
    public void clear() {
        stack.clear();
        top = -1;
    }

    // Get all sold drugs (for JTable / display)
    public ArrayList<Drug> getAll() {
        return stack;
    }

}

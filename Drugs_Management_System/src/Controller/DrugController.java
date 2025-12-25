/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Drug;
import Model.DrugModel;
import java.util.ArrayList;

public class DrugController {
    private DrugModel model;

    public DrugController(DrugModel model) {
        this.model = model;
    }

    public void addDrug(String name, int stock, String vendor, double cost) {
        Drug drug = new Drug(name, stock, vendor, cost);
        model.addDrug(drug);
    }

    public ArrayList<Drug> getDrugs() {
        return model.getAllDrugs();
    }

    public void removeDrug(int index) {
        model.deleteDrug(index);
    }
    
    public void editDrug(int index, String name, int stock, String vendor, double cost) {
        model.updateDrug(index, new Drug(name, stock, vendor, cost));
    }
}
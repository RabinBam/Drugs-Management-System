/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.LinkedList;
/**
 *
 * @author ASUS
 */
public class SimilarDrugs {
    
    private String disease;
    private LinkedList<Drug> similarDrugList;
    
      public SimilarDrugs(String disease) {
        this.disease = disease;
        this.similarDrugList = new LinkedList<>();
    }

    public void addDrug(Drug drug) {
        similarDrugList.add(drug);
    }

    public LinkedList<Drug> getSimilarDrugList() {
        return similarDrugList;
    }

    public String getDisease() {
        return disease;
    }
    
}

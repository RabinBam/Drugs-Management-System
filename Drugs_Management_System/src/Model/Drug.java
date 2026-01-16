/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */
public class Drug {
    private String name;
    private int stock;
    private String vendor;
    private double unitCost;
    private String picture;
    private String description;
    private String disease;

    public Drug(String name, int stock, String vendor, double unitCost) {
        this.name = name;
        this.stock = stock;
        this.vendor = vendor;
        this.unitCost = unitCost;
    }
  /**
 * Constructs a Drug object with the specified details.
 *
 * @param name the name of the drug
 * @param stock the available quantity in stock
 * @param vendor the vendor or supplier of the drug
 * @param unitCost the cost per unit of the drug
 * @param picture the file path or URL of the drug's image
 * @param description a brief description of the drug
 */
    
    public Drug(String name, int stock, String vendor, double unitCost, String picture, String description) {
        this.name = name;
        this.stock = stock;
        this.vendor = vendor;
        this.unitCost = unitCost;
        this.picture = picture;
        this.description = description;
    }
    // Getters and Setters
    public String getName() { return name; }
    public int getStock() { return stock; }
    public String getVendor() { return vendor; }
    public double getUnitCost() { return unitCost; }
    public String getPicture() { return picture; }
    public String getDescription() {return description; }
    public String getDisease() { return disease; }

    
    public void setName(String name) { this.name = name; }
    public void setStock(int stock) { this.stock = stock; }
    public void setVendor(String vendor) { this.vendor = vendor; }
    public void setUnitCost(double unitCost) { this.unitCost = unitCost; }
    public void setPicture(String picture){ this.picture = picture; }
    public void setDescription(String description){ this.description = description;}
    public void setDisease(String disease) { this.disease = disease; }
}
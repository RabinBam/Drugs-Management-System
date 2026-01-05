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

    public Drug(String name, int stock, String vendor, double unitCost) {
        this.name = name;
        this.stock = stock;
        this.vendor = vendor;
        this.unitCost = unitCost;
    }
    
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
    
    public void setName(String name) { this.name = name; }
    public void setStock(int stock) { this.stock = stock; }
    public void setVendor(String vendor) { this.vendor = vendor; }
    public void setUnitCost(double unitCost) { this.unitCost = unitCost; }
    public void setPicture(String picture){ this.picture = picture; }
    public void setDescription(String description){ this.description = description;}
}
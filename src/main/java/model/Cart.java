package model;

import java.io.Serializable;

import dao.ItemDAO;

public class Cart implements Serializable {
    private String userID;
    private int itemID;
    private int quantity;
    private double price;
    
public Cart() {}
    
    public Cart(String userID, int itemID, double price,int quantity) {
		this.userID = userID;
		this.itemID = itemID;
		this.price = price;
		this.quantity = quantity;
		}
    
    public String getItemName(int itemID) {
        
    	ItemDAO itemDAO = new ItemDAO(); 
        String itemName = itemDAO.getNameById(itemID);
        return itemName;
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
    
    public int getQuantity() {
    	return quantity;
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }
    
    public double getPrice() {
    	return price;
    }
    public void setPrice(double price) {
    	this.price = price;
    }
}
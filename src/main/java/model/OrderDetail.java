package model;

import java.io.Serializable;

public class OrderDetail implements Serializable{
	private int OrderDetailID;
	private int ItemID;
	private int Quantity;
	private double UnitPrice;
	private String UserID;
	
	public OrderDetail() {};
	
	public OrderDetail(int OrderDetailID,int ItemID, 
			int Quantity, double UnitPrice, String UserID) {
		this.OrderDetailID = OrderDetailID;
		this.ItemID = ItemID;
		this.Quantity = Quantity;
		this.UnitPrice = UnitPrice;
		this.UserID = UserID;
	}
	
	public int getOrderDetailID() {
		return OrderDetailID;
	}
	
	public void setOrderDetailID(int OrderDetailID) {
		this.OrderDetailID = OrderDetailID;
	}
	
	public int getItemID() {
		return ItemID;
	}
	
	public void setItemID(int ItemID) {
		this.ItemID = ItemID;
	}
	
	public int getQuantity() {
		return Quantity;
	}
	
	public void setQuantity(int Quantity) {
		this.Quantity = Quantity;
	}
	
	public double getUnitPrice() {
		return UnitPrice;
	}
	
	public void setUnitPrice(double UnitPrice) {
		this.UnitPrice = UnitPrice;
	}

	public String getUserID() {
		return UserID;
	}
	
	public void setUserID(String UserID) {
		this.UserID = UserID;
	}
}

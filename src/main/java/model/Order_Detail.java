package model;

import java.io.Serializable;

public class Order_Detail implements Serializable{
	private int UniqueDetailID;
	private String OrderID;
	private int OrderDetailID;
	
	public Order_Detail() {};
	
	public Order_Detail(int UniqueDetailID, String OrderID, int OrderDetailID) {
		this.UniqueDetailID = UniqueDetailID;
		this.OrderID = OrderID;
		this.OrderDetailID = OrderDetailID;
	}
	
	public String getOrderID() {
		return OrderID;
	}
	
	public void setOrderID(String OrderID) {
		this.OrderID = OrderID;
	}
	
	public int getOrder_DetailID() {
		return OrderDetailID;
	}
	
	public void setOrder_DetailID(int OrderDetailID) {
		this.OrderDetailID = OrderDetailID;
	}
	
	public int getUniqueDetailID() {
		return UniqueDetailID;
	}
	public void setUniqueDetailID(int UniqueDetailID) {
		this.UniqueDetailID = UniqueDetailID;
	}
	
}


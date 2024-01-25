package model;

import java.io.Serializable;
import java.sql.Date;

public class Order implements Serializable {
	private String OrderID;
	private String UserID;
	private Date OrderDate;
	private double TotalAmount;
	private String Address;
	private String payment_method;
	private String Status;//Pending,Completed,Cancelled
	
public Order() {}
	
	public Order(String OrderID,String UserID, Date OrderDate,
			double TotalAmount,String Address, String payment_method, String Status) {
		this.OrderID = OrderID;
		this.UserID = UserID;
		this.OrderDate = OrderDate;
		this.TotalAmount = TotalAmount;
		this.Address = Address;
		this.payment_method = payment_method;
		this.Status = Status;
	}
	
	public String getOrderID() {
		return OrderID;
	}
	
	public void setOrderID(String OrderID) {
		this.OrderID = OrderID;
	}
	
	public String getUserID() {
		return UserID;
	}
	
	public void setUserID(String UserID) {
		this.UserID = UserID;
	}
	
	public Date getOrderDate() {
		return OrderDate;
	}
	
	public void setOrderDate(Date OrderDate) {
		this.OrderDate = OrderDate;
	}
	
	public double getTotalAmount() {
		return TotalAmount;
	}
	
	public void setTotalAmount(double TotalAmount) {
		this.TotalAmount = TotalAmount;
	}
	
	public String getAddress() {
		return Address;
	}
	
	public void setAddress(String Address) {
		this.Address = Address;
	}
	
	public String getpayment_method() {
		return payment_method;
	}
	
	public void setpayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String Status) {
		this.Status = Status;
	}

}

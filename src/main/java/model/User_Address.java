package model;

public class User_Address {
	private String userid;
	private String Address;
	
	public User_Address() {}
	
	public User_Address(String userid,String Address) {
		this.userid = userid;
		this.Address = Address;
	}
	
	public String getUserID() {
		return userid;
	}
	
	public void setUserID(String userid) {
		this.userid = userid;
	}
	
	public String getAddress() {
		return Address;
	}
	
	public void setAddress(String Address) {
		this.Address = Address;
	}
	
}

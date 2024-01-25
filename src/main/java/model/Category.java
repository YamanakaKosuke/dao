package model;

public class Category {
	private int category_id;
	private String name;
	
	public Category() {}
	
	public Category(int category_id, String name) {
		this.category_id = category_id;
		this.name = name;
	}
	
	public int getcategory_id() {
		return category_id;
	}
	
	public void setcategory_id(int category_id) {
		this.category_id = category_id;
	}
	
	public String getname() {
		return name;
	}
	
	public void setname(String name) {
		this.name = name;
	}

}

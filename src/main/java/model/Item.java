package model;

import java.io.Serializable;

public class Item implements Serializable{
	private int id;
	private String name;
	private double price;
	private int category_id;
	private int stock;
	private int quantity;

	public Item() {}

	public Item(String name, double price, int category_id, int stock) {
		this.name = name;
		this.price = price;
		this.category_id = category_id;
		this.stock = stock;
	}

	public Item(int id, String name, double price, int category_id, int stock) {
		this(name,price,category_id,stock);
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getCategory_ID() {
		return category_id;
	}
	public void setCategory(int category_id) {
		this.category_id = category_id;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
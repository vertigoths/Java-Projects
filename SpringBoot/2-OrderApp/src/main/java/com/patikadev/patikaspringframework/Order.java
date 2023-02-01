package com.patikadev.patikaspringframework;

public abstract class Order 
{
	private String name;
	private int price;
	private int amount;
	
	public Order(String name, int price, int amount) {
		super();
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract String getServingManual();
	
	@Override
	public String toString() 
	{
		return "Name of the order: " + name + ", with a price of: " + price + ", while amount is: " + amount;
	}
}

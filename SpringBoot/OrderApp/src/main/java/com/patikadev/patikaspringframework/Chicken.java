package com.patikadev.patikaspringframework;

public class Chicken extends Order 
{
	private String origin;
	private final String name = "Chicken";
	
	public Chicken(String origin, int price, int amount) 
	{
		super(null, price, amount);
		this.setName(name);
		
		this.origin = origin;
	}

	@Override
	public String getServingManual() 
	{
		return "This explains how to serve chicken...";
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}

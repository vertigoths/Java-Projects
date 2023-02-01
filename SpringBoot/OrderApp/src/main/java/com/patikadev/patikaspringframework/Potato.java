package com.patikadev.patikaspringframework;

public class Potato extends Order
{
	private final String name = "Potato";
	
	public Potato(int price, int amount) {
		super(null, price, amount);
		this.setName(name);
	}

	@Override
	public String getServingManual() {
		return "Potatoes can be served like this...";
	}
}

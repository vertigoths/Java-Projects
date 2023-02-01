package com.patikadev.patikaspringframework;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Receipt 
{
	private int customerID;
	private List<Order> orders;
	private int totalCharge;
	private LocalDate date;
	
	public Receipt(int customerID) 
	{
		this.customerID = customerID;
		
		orders = new LinkedList<Order>();
		
		setDate(LocalDate.of(1970, Month.JANUARY, 1));
	}
	
	public Receipt AddOrder(Order order) 
	{
		setTotalCharge(getTotalCharge() + (order.getAmount() * order.getPrice()));
		orders.add(order);
		return this;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(int totalCharge) {
		this.totalCharge = totalCharge;
	}
	
	public void DisplayOrders()
	{
		orders.stream()
			.forEach(order -> {
				System.out.println(order.toString());
			});
		
		System.out.println("Total charge: " + getTotalCharge());
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}

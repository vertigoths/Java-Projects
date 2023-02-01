package com.patikadev.patikaspringframework;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Customer 
{
	public static int customerCount;
	private String name;
	private int customerID;
	private Set<Receipt> receipts;
	private LocalDate joiningDate;
	
	public Customer(LocalDate date, String name) 
	{
		this.setJoiningDate(date);
		this.setName(name);
		this.customerID = customerCount;
		
		receipts = new HashSet<Receipt>();
		
		customerCount++;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public void AddReceipt(Receipt receipt) 
	{
		receipts.add(receipt);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}
	
	@Override
	public String toString() 
	{
		return "My name is: " + name + " with an ID of " + customerID + ", where I've joined " + joiningDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
	}
	
	public int getChargeOfReceipts() 
	{
		int total = 0;
		
		for(Receipt receipt : receipts) 
		{
			total += receipt.getTotalCharge();
		}
		
		return total;
	}
	
	public List<Receipt> getReceiptsOver(int value)
	{
		return receipts.stream()
			    .filter(receipt -> receipt.getTotalCharge() > value)
			    .collect(Collectors.toList());
	}
	
	public void DisplayReceipts() 
	{
		AtomicInteger receiptCount = new AtomicInteger(0);
		
		receipts.stream()
			.forEach(receipt -> 
			{
				System.out.println("Receipt " + receiptCount.incrementAndGet() + ": with date of: " 
						+ receipt.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				
				receipt.DisplayOrders();
			});
	}
	
	public void DisplayReceipts(int value) 
	{
		AtomicInteger receiptCount = new AtomicInteger(0);
		
		receipts.stream()
			.forEach(receipt -> 
			{
				if(receipt.getTotalCharge() > value) 
				{
					System.out.println("Receipt " + receiptCount.incrementAndGet() + ": with date of: " 
							+ receipt.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					
					receipt.DisplayOrders();
				}
			});
	}
	
	public boolean HasReceiptUnder(int value) 
	{
		for(Receipt receipt : receipts)
		{
			if(receipt.getTotalCharge() < value) 
			{
				return true;
			}
		}
		
		return false;
	}
	
	public float getAverageOfReceiptsInMonth(Month month)
	{
		int total = 0;
		int receiptCount = 0;
		
		for (Receipt receipt : receipts) 
	    {
			if(receipt.getDate().getMonth() == month) 
			{
				total += receipt.getTotalCharge();
				receiptCount++;
			}
	    }
		
		return (float)total / receiptCount;
	}
}

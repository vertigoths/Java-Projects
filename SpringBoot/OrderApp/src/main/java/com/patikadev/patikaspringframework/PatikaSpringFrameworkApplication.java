package com.patikadev.patikaspringframework;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class PatikaSpringFrameworkApplication {

    public static void main(String[] args)
    {
        HashMap<Character, List<Customer>> customers = new HashMap<Character, List<Customer>>();
        AddCustomer(customers, LocalDate.of(2019, Month.JANUARY, 14), "Ceylin", 4, 1);
        AddCustomer(customers, LocalDate.of(2020, Month.JUNE, 15), "Cafer", 9, 15);
        AddCustomer(customers, LocalDate.of(2021, Month.APRIL, 16), "Ceyda", 2, 3);
        AddCustomer(customers, LocalDate.of(2022, Month.JUNE, 17), "Aslan", 26, 9);

        AddReceiptToCustomer(customers, 1, LocalDate.of(1971, Month.JUNE, 2));
        AddReceiptToCustomer(customers, 1, LocalDate.of(1972, Month.JUNE, 3));
        AddReceiptToCustomer(customers, 1, LocalDate.of(1973, Month.JUNE, 4));
        AddReceiptToCustomer(customers, 2, LocalDate.of(1974, Month.JUNE, 5));

        // DisplayAllCustomers(customers);
        // DisplayCustomersContainsSequence(customers, String.valueOf('C'));
        // DisplayCustomersChargeRegisteredOn(customers, Month.JUNE);
        // DisplayAllReceipts(customers);
        // DisplayReceiptsOver(customers, 1500);
        // System.out.println("Averages for receipts over: " + CalculateAverageOfReceiptsOver(customers, 1500));
        // DisplayNamesOfCustomersWithReceiptUnder(customers, 500);

        // DisplayNamesOfCustomersWithReceiptUnder(customers, 750, Month.JUNE);
    }

    public static void AddCustomer(HashMap<Character, List<Customer>> customers, LocalDate joinDate, String customerName, int chickenAmount, int potatoAmount)
    {
        Customer customer = new Customer(joinDate, customerName);
        Receipt receipt = new Receipt(customer.getCustomerID());

        Order chicken = new Chicken("Circassia", chickenAmount, 100);
        Order potato = new Potato(potatoAmount, 50);
        receipt.AddOrder(chicken)
                .AddOrder(potato);

        customer.AddReceipt(receipt);

        char firstCharacter = customer.getName().toLowerCase().charAt(0);

        if(!customers.containsKey(firstCharacter))
        {
            customers.put(firstCharacter, new LinkedList<Customer>());
        }

        customers.get(firstCharacter).add(customer);
    }

    public static void DisplayAllCustomers(HashMap<Character, List<Customer>> customers)
    {
        customers.entrySet().stream()
                .forEach(entry -> {
                    entry.getValue().stream()
                            .forEach(customer -> System.out.println(customer));
                });
    }

    public static void DisplayCustomersContainsSequence(HashMap<Character, List<Customer>> customers, CharSequence ch)
    {
        customers.entrySet().stream()
                .forEach(entry -> {
                    entry.getValue().stream()
                            .filter(customer -> customer.getName().contains(ch))
                            .forEach(customer -> System.out.println(customer));
                });
    }

    public static void DisplayCustomersChargeRegisteredOn(HashMap<Character, List<Customer>> customers, Month month)
    {
        customers.entrySet().stream()
                .forEach(entry -> {
                    entry.getValue().stream()
                            .filter(customer -> customer.getJoiningDate().getMonth() == month)
                            .forEach(customer -> System.out.println("Name: " + customer.getName() + ", Receipts Charge: " + customer.getChargeOfReceipts()));
                });
    }

    public static void AddReceiptToCustomer(HashMap<Character, List<Customer>> customers, int customerID, LocalDate receiptDate)
    {
        customers.entrySet().stream()
                .forEach(entry -> {
                    entry.getValue().stream()
                            .filter(customer -> customer.getCustomerID() == customerID)
                            .forEach(customer -> {
                                Receipt receipt = new Receipt(customer.getCustomerID());

                                Order chicken = new Chicken("Indian", 4, 10);
                                receipt.AddOrder(chicken);
                                receipt.setDate(receiptDate);

                                customer.AddReceipt(receipt);});
                });
    }

    public static void DisplayAllReceipts(HashMap<Character, List<Customer>> customers)
    {
        customers.entrySet().stream()
                .forEach(entry -> {
                    entry.getValue().stream()
                            .forEach(customer ->
                            {
                                System.out.println(customer.toString());
                                customer.DisplayReceipts();
                                System.out.println("-----------------");
                            });
                });
    }

    public static void DisplayReceiptsOver(HashMap<Character, List<Customer>> customers, int value)
    {
        customers.entrySet().stream()
                .forEach(entry -> {
                    entry.getValue().stream()
                            .forEach(customer ->
                            {
                                System.out.println(customer.toString());
                                customer.DisplayReceipts(value);
                                System.out.println("-----------------");
                            });
                });
    }

    public static float CalculateAverageOfReceiptsOver(HashMap<Character, List<Customer>> customers, int value)
    {
        int total = 0;
        int receiptCount = 0;

        for (Map.Entry<Character, List<Customer>> entry : customers.entrySet())
        {
            for (Customer customer : entry.getValue())
            {
                List<Receipt> receipts = customer.getReceiptsOver(value);
                receiptCount += receipts.size();

                for (Receipt receipt : receipts)
                {
                    total += receipt.getTotalCharge();
                }
            }
        }

        return (float) total / receiptCount;
    }

    public static void DisplayNamesOfCustomersWithReceiptUnder(HashMap<Character, List<Customer>> customers, int value)
    {
        customers.entrySet().stream()
                .forEach(entry -> {
                    entry.getValue().stream()
                            .forEach(customer ->
                            {
                                if(customer.HasReceiptUnder(value))
                                {
                                    System.out.println(customer.getName());
                                }
                            });
                });
    }

    public static void DisplayNamesOfCustomersWithReceiptUnder(HashMap<Character, List<Customer>> customers, int value, Month month)
    {
        customers.entrySet().stream()
                .forEach(entry -> {
                    entry.getValue().stream()
                            .forEach(customer ->
                            {
                                if(customer.getAverageOfReceiptsInMonth(month) < value)
                                {
                                    System.out.println(customer.getName());
                                }
                            });
                });
    }

}

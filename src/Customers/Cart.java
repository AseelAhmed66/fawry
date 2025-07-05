package Customers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import system.Expirable;
import system.Product;
import system.Shippable;

public class Cart {
	
	private Customer customer;
	private Map<Product, Integer> items;

    public Cart(Customer customer) {
    	this.customer=customer;
        this.items = new HashMap<>();
    }
    
    private void printShipmentNotice() {
        System.out.println("\n** Shipment notice **");
        double totalWeight = 0;
        
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.print(qty + "x " + p.getName());
            
            if (p instanceof Shippable) {
                double itemWeight = ((Shippable) p).getWeight() * qty;
                System.out.printf("  %.0fg%n", itemWeight * 1000);
                totalWeight += itemWeight;
            }
            
        }
        
        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }

    private void printReceipt(double subtotal, double shipping, double total) {
        System.out.println("\n** Checkout receipt **");
        
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.printf("%dx %-15s %.0f%n", qty, p.getName(), p.getPrice() * qty);
        }
        
        System.out.println("----------------------");
        System.out.println("Subtotal  "+ subtotal);
        System.out.println("Shipping    "+ shipping);
        System.out.println("Amount  "+ total);
    }
    
    private double calculateSubtotal() {
        return items.entrySet().stream()
                   .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                   .sum();
    }


    public void add(Product product, int quantity) {
    	
        if (product.getQuantity() >= quantity) 
        {
            items.merge(product, quantity, Integer::sum);
            product.setQuantity(product.getQuantity()-1);
        }
        else
        {
            System.out.println("Error: Not enough " + product.getName() + " in stock");
        }
    }
    
    public void checkout(Customer customer) {
    	
        if (items.isEmpty())
        {
            System.out.println("Error: Cart is empty");
            return;
        }

        // Check for expired products
        for (Product p : items.keySet()) {
        	boolean isexpired=false;
        	if(p instanceof Expirable)
        	{
                isexpired=((Expirable) p).getExpiryDate().before(new Date());
            }
            
            if(isexpired) 
            {
                System.out.println("Error: " + p.getName() + " is expired");
                return;
            }
        }

        double subtotal = calculateSubtotal();
        double shipping = 30;
        double total = subtotal + shipping;

      
        if (!customer.hasSufficientBalance(total)) {
            System.out.println("Error: Insufficient balance");
            return;
        }

        printShipmentNotice();
        printReceipt(subtotal, shipping, total);
        
        customer.deductBalance(total);
        items.clear();
    }

}

package main;

import java.util.Date;

import Customers.Cart;
import Customers.Customer;
import system.ExpirableProduct;
import system.ExpirableShippableProduct;
import system.Product;
import system.ShippableProduct;
import system.StandardProduct;

public class Tester {
	   public static void main(String[] args) {
		   
		   Product cheese = new ExpirableShippableProduct(
		            "Cheese", 5.99, 10, 
		            new Date(System.currentTimeMillis() + 86400000 * 7), // expires in 7 days
		            0.5
		        );
		        
		   Product tv = new ShippableProduct("TV", 499.99, 5, 15.0);
		        
		        // Create customer with sufficient balance
		   Customer customer = new Customer("John Doe", 1000.00);
		        
		        // Create shopping cart
           Cart cart = new Cart(customer);
		        
		        // Add products to cart (quantities within stock limits)
            cart.add(cheese, 2);  // Adds 2 cheeses
	        cart.add(tv, 1);      // Adds 1 TV
		        
		        // Checkout - this will succeed with no errors
		        cart.checkout(customer);
		    
	    }

}

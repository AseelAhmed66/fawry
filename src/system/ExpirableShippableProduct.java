package system;

import java.util.Date;

public class ExpirableShippableProduct extends Product implements Expirable,Shippable {

	private Date expiryDate;
    private double weight;

    public ExpirableShippableProduct(String name, double price, int quantity, Date expiryDate, double weight) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }
	@Override
	public double getWeight() {
		return weight;
	}
	
	
	@Override
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	

}

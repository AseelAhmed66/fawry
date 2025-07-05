package system;

import java.util.Date;

public class ExpirableProduct extends Product implements Expirable {

	private Date expiryDate;

    public ExpirableProduct(String name, double price, int quantity, Date expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }
    
    
	@Override
	public Date getExpiryDate() {
		return expiryDate;
	}

	

}

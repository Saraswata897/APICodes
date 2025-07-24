package question9;

public class RestaurantPartner extends ServicePartner{
	private double discountPercentage;
	private double taxPercentage;
	public RestaurantPartner(String partnerID, String partnerName, String contactNumber, double orderAmount,
			double discountPercentage, double taxPercentage) {
		super(partnerID, partnerName, contactNumber, orderAmount);
		this.discountPercentage = discountPercentage;
		this.taxPercentage = taxPercentage;
	}
	public double getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public double getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	public double calculateOrderCost() {
		double discount=getOrderAmount()*discountPercentage/100.0;
		double tax=getOrderAmount()*taxPercentage/100.0;
		if (getOrderAmount()>500) {
			return getOrderAmount()-discount+tax;
		}
		else {
			return getOrderAmount()+tax;
		}
	}
	
}

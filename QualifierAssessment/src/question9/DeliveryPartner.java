package question9;

public class DeliveryPartner extends ServicePartner{
	private double distanceInKm;
	private double deliveryRatePerKm;
	public DeliveryPartner(String partnerID, String partnerName, String contactNumber, double orderAmount,
			double distanceInKm, double deliveryRatePerKm) {
		super(partnerID, partnerName, contactNumber, orderAmount);
		this.distanceInKm = distanceInKm;
		this.deliveryRatePerKm = deliveryRatePerKm;
	}
	public double getDistanceInKm() {
		return distanceInKm;
	}
	public void setDistanceInKm(double distanceInKm) {
		this.distanceInKm = distanceInKm;
	}
	public double getDeliveryRatePerKm() {
		return deliveryRatePerKm;
	}
	public void setDeliveryRatePerKm(double deliveryRatePerKm) {
		this.deliveryRatePerKm = deliveryRatePerKm;
	}
	public double calculateDeliverCharge() {
		if (getOrderAmount()>20000) {
			return 0;
		}
		else {
			if (distanceInKm<=10) {
				return distanceInKm*deliveryRatePerKm;
			}
			else {
				return (distanceInKm*deliveryRatePerKm)+50;
			}
		}
	}
	
}

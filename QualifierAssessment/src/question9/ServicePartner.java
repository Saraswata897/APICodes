package question9;

public class ServicePartner {
	private String partnerID;
	private String partnerName;
	private String contactNumber;
	private double orderAmount;
	
	public ServicePartner() {
	}
	public ServicePartner(String partnerID, String partnerName, String contactNumber, double orderAmount) {
		this.partnerID = partnerID;
		this.partnerName = partnerName;
		this.contactNumber = contactNumber;
		this.orderAmount = orderAmount;
	}
	public String getPartnerID() {
		return partnerID;
	}
	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	
}

package question7;

public class EVChargingStation {
	private String stationName;
	private String providerName;
	private String city;
	private int totalChargingPoints;
	public EVChargingStation(String stationName, String providerName, String city, int totalChargingPoints) {
		super();
		this.stationName = stationName;
		this.providerName = providerName;
		this.city = city;
		this.totalChargingPoints = totalChargingPoints;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getTotalChargingPoints() {
		return totalChargingPoints;
	}
	public void setTotalChargingPoints(int totalChargingPoints) {
		this.totalChargingPoints = totalChargingPoints;
	}
	public double calculateChargingCost(String evType, int count) {
		if (count<0 || totalChargingPoints<0) {
			return -1;
		}
		double pricePerPoint=0;
		if (evType.equalsIgnoreCase("Small")) {
			pricePerPoint=150;
		}
		else if (evType.equalsIgnoreCase("Large")) {
			pricePerPoint=300;
		}
		else {
			return -1;
		}
		return count*totalChargingPoints*pricePerPoint;
	}
}

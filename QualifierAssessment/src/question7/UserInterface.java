package question7;

import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the station name");
		String stationName=scanner.nextLine();
		System.out.println("Enter the provider name");
		String providerName=scanner.nextLine();
		System.out.println("Enter the city");
		String city=scanner.nextLine();
		System.out.println("Enter the total charging points");
		int totalChargingPoints=scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter the EV type and count");
		String evType=scanner.nextLine();
		int count=scanner.nextInt();
		scanner.nextLine();
		EVChargingStation evObj= new EVChargingStation(stationName, providerName, city, totalChargingPoints);
		double cost = evObj.calculateChargingCost(evType, count);
		if (cost>0) {
			System.out.println("Station Name: "+evObj.getStationName());
			System.out.println("Provider Name: "+evObj.getProviderName());
			System.out.println("City: "+evObj.getCity());
			System.out.println("Total Charging Points: "+evObj.getTotalChargingPoints());
			System.out.println("Estimated Charging Cost: "+cost);
		}
		else {
			System.out.println("Unable to calculate charging cost due to invalid EV type or count.");
		}
		scanner.close();
		
	}

}

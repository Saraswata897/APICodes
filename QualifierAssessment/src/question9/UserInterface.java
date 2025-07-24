package question9;

import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter partner details");
		String[] details=scanner.nextLine().split(":");
		String partnerType=details[0];
		String partnerId=details[1];
		String partnerName=details[2];
		String contactNumber=details[3];
		double orderAmount=Double.parseDouble(details[4]);
		if (partnerType.equalsIgnoreCase("Restaurant")) {
			double discount=Double.parseDouble(details[5]);
			double tax=Double.parseDouble(details[6]);
			RestaurantPartner rPartner = new RestaurantPartner(partnerId, partnerName, contactNumber, orderAmount, discount, tax);
			double total=rPartner.calculateOrderCost();
			System.out.println("Calculated order cost for Restaurant Partner "+rPartner.getPartnerName()+" is "+total);
		}
		else if (partnerType.equalsIgnoreCase("Delivery")) {
			double distance=Double.parseDouble(details[5]);
			double rate=Double.parseDouble(details[6]);
			DeliveryPartner dPartner = new DeliveryPartner(partnerId, partnerName, contactNumber, orderAmount, distance, rate);
			double cost=dPartner.calculateDeliverCharge();
			System.out.println("Calculated delivery charge for Delivery Partner "+dPartner.getPartnerName()+" is "+cost);
		}
		else {
			System.out.println("Invalid partner type.Please enter either 'Restaurant' or 'Delivery'");
		}
		scanner.close();
	}

}

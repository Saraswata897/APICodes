package question4;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of hotels to be added");
		int n=scanner.nextInt();
		scanner.nextLine();
		HotelManager hObj = new HotelManager();
		System.out.println("Enter hotel details (name:rating)");
		for (int i=0;i<n;i++) {
			String inputString = scanner.nextLine();
			hObj.addHotelDetails(inputString);
			
		}
		System.out.println("Enter the minimum rating");
		double minRating = scanner.nextDouble();
		scanner.nextLine();
		List<String> outputString = hObj.minRatings(minRating);
		if (outputString.isEmpty()) {
			System.out.println("No hotels were found with minimum rating of "+minRating);
		}
		else {
			for (String string : outputString) {
				System.out.println(string);
			}
		}
		scanner.close();
	}

}

package question6;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of details to be added");
		int n =scanner.nextInt();
		scanner.nextLine();
		FireExtuingisher extuingisher = new FireExtuingisher();
		System.out.println("Enter Extinguisher Details (Product:Type:Price):");
		for(int i =0;i<n;i++) {
			String input=scanner.nextLine();
			extuingisher.addExtinguisherDetails(input);
		}
		System.out.println("Enter the minimum Price");
		int minPrice = scanner.nextInt();
		System.out.println("Enter the maximum Price");
		int maxPrice = scanner.nextInt();
		scanner.nextLine();
		List<String> res=extuingisher.findExtinguisherProductRange(minPrice, maxPrice);
		
		if (res.isEmpty()) {
			System.out.println("No products in inventory");
		}
		else {
			System.out.println("Fire Extinguisher Inventory");
			for (String string : res) {
				System.out.println(string);
			}
		}
		scanner.close();
	}

}

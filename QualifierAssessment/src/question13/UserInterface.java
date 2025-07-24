package question13;

import java.util.TreeSet;
import java.util.Scanner;
import java.util.Set;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of pets to be add:");
		int n=scanner.nextInt();
		scanner.nextLine();
		PetInfo pInfo=new PetInfo();
		System.out.println("Enter pet details in the format (Name:Breed:AgeInMonth)");
		for (int i = 0; i < n; i++) {
			String input=scanner.nextLine();
			pInfo.addPetDetails(input);;
		}
		for (String string : pInfo.getPetSet()) {
			System.out.println(string);
		}
		System.out.println("Enter the maximum age in months to filter");
		int maxAge = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter the breed to filter");
		String breed=scanner.nextLine();
		Set<String> res= new TreeSet<String>();
		res=pInfo.filterPetsByAgeAndBreed(maxAge, breed);
		if (res.isEmpty()) {
			System.out.println("No pets found within the criteria");
		} else {
			System.out.println("Pets matching the criteria");
			for (String string : res) {
				System.out.println(string);
			}
		}
		scanner.close();
	}

}

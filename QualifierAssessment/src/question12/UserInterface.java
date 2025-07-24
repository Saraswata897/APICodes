package question12;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of cloth sizes to be added:");
		int n=scanner.nextInt();
		scanner.nextLine();
		StockInfo sInfo=new StockInfo();
		System.out.println("Enter size details in the format (Cloth item with size:Quantity)");
		for (int i = 0; i < n; i++) {
			String input=scanner.nextLine();
			sInfo.addClothStockDetails(input);
		}
		System.out.println("Enter the minimum stock quantity");
		int minQuantity = scanner.nextInt();
		System.out.println("Enter the maximum stock quantity");
		int maxQuantity = scanner.nextInt();
		scanner.nextLine();
		Set<String> res= new HashSet<String>();
		res=sInfo.filterClothItemsWithinStockRange(minQuantity, maxQuantity);
		if (res.isEmpty()) {
			System.out.println("No cloth items found within the specified quantity range");
		} else {
			System.out.println("Cloth sizes with quantity between "+minQuantity+" and "+maxQuantity);
			for (String string : res) {
				System.out.println(string);
			}
		}
		scanner.close();
	}

}

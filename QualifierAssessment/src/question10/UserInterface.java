package question10;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of universities records to be added");
		int n = scanner.nextInt();
		scanner.nextLine();
		University university=new University();
		System.out.println("Enter the university records (University Name : Total Study Expense)");
		for (int i = 0; i < n; i++) {
			String[] part=scanner.nextLine().split(":");
			String name=part[0];
			double expense=Double.parseDouble(part[1]);
			university.addUniversityDetails(name, expense);
		}
		System.out.println("Enter the student budget");
		double budget=scanner.nextDouble();
		scanner.nextLine();
		if(budget<0) {
			System.out.println("Budget should be greater than 0");
		}
		else {
			List<String> res=university.filterBudgetUniversities(budget);
			if (res.isEmpty()) {
				System.out.println("No universities were found for the given budget "+budget);
			}
			else {
				System.out.println("Universities found for the budget "+budget);
				for (String string : res) {
					System.out.println(string);
				}
			}
		}
		
		
		scanner.close();
	}

}

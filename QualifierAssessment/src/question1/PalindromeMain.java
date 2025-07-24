package question1;

import java.util.ArrayList;
import java.util.Scanner;

public class PalindromeMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the size of the array");
		int n=scanner.nextInt();
		scanner.nextLine();
		if (n<=0) {
			System.out.println(n+" is an invalid array size");
			return;
		}
		System.out.println("Enter the elements of the array");
		char arr[] = scanner.nextLine().toCharArray();
		ArrayList<Character> newArr = new ArrayList<Character>();
		for(char c:arr) {
			if(!(c=='0')) {
				newArr.add(c);
			}
		}
		StringBuilder res = new StringBuilder();
		StringBuilder newRes = new StringBuilder();
		for(char c : arr) {
			res.append(c);
		}
		for (char c : newArr) {
			newRes.append(c);
		}
		String fullString = res.toString().trim();
		String nonZeroString = newRes.toString().trim();
		String nonZeroStringReversed = newRes.reverse().toString().trim();
		if(nonZeroString.equalsIgnoreCase(nonZeroStringReversed)) {
			System.out.println(nonZeroString+" is a palindrome array");
		}
		else {
			System.out.println(fullString+" is not a palindrome array");
		}
		scanner.close();
	}

}

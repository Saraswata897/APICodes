package question11;

import java.util.Scanner;

public class UserInterface {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the string");
		String sentence=scanner.nextLine();
		if(!(sentence.matches("[a-zA-Z ]+"))) {
			System.out.println(sentence+" is an invalid input");
			return;
		}
		System.out.println("Enter the character to be searched");
		char ch = scanner.nextLine().charAt(0);
		if (!(sentence.contains(Character.toString(ch)))) {
			System.out.println(ch+" is not found in the string");
			return;
		}
		System.out.println("Enter the character to replace");
		String xd = scanner.nextLine();
		if(!(xd.matches("[a-zA-Z ]"))) {
			System.out.println(xd+" is an invalid input");
			return;
		}
		char temp=xd.charAt(0);
		
		String result=sentence.replaceFirst(Character.toString(ch), Character.toString(temp));
		System.out.println(result);
		scanner.close();
	}

}

package question8;

import java.util.Scanner;

public class UserInterface {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the string");
		String word=scanner.nextLine().toLowerCase();
		int wordLength=word.length();
		if (wordLength<2 || !(word.matches("[a-zA-Z]+"))) {
			System.out.println(word+" is an invalid string");
			return;
		}
		
		if (wordLength%2==0) {
			int mid=wordLength/2;
			int sumOne=0;
			int sumTwo=0;
			for(int i=0;i<mid;i++) {
				char ch=word.charAt(i);
				int t=(int)ch - 96;
				sumOne+=t;
			}
			for(int i=mid;i<wordLength;i++) {
				char ch=word.charAt(i);
				int t=(int)ch - 96;
				sumTwo+=t;
			}
			if (sumOne==sumTwo) {
				System.out.println(word+" is a balanced word");
				
			}
			else {
				System.out.println(word+" is not a balanced word");
				
			}
		}
		else {
			int mid=wordLength/2;
			int sumOne=0;
			int sumTwo=0;
			for(int i=0;i<mid;i++) {
				char ch=word.charAt(i);
				int t=(int)ch - 96;
				sumOne+=t;
			}
			for(int i=mid+1;i<wordLength;i++) {
				char ch=word.charAt(i);
				int t=(int)ch - 96;
				sumTwo+=t;
			}
			if (sumOne==sumTwo) {
				System.out.println(word+" is a balanced word");
				
			}
			else {
				System.out.println(word+" is not a balanced word");
				
			}
			
		}
	    scanner.close();
	}

}

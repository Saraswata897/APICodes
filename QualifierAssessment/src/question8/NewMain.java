package question8;

import java.util.Scanner;

public class NewMain {

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
		int mid=wordLength/2;
		int sumOne=0;
		int sumTwo=0;
		// int reStart=(wordLength%2==0)?mid:mid+1;
		for(int i=0;i<mid;i++) {
			char ch1=word.charAt(i);
			char ch2=word.charAt(wordLength-i-1);
			int x=ch1 - 96;
			// System.out.println(x);
			int y=ch2 - 96;
			// System.out.println(y);
			sumOne+=x;
			sumTwo+=y;
		}
		if (sumOne==sumTwo) {
			System.out.println(word+" is a balanced word");
			
		}
		else {
			System.out.println(word+" is not a balanced word");
			
		}
		scanner.close();
		
	}

}

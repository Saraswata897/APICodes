package question2;

import java.util.Arrays;
import java.util.Scanner;

public class MovieScheduler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the number of movies:");
		int numberOfMovies = scanner.nextInt();
		scanner.nextLine();
		if (numberOfMovies<1) {
			System.out.println("The number of movies should be atleast 1");
			return;
		}
		int startTime[]=new int[numberOfMovies];
		int endTime[]=new int[numberOfMovies];
		System.out.println("Enter the start and end times of each movie:");
		for (int i=0;i<numberOfMovies;i++) {
			System.out.println("Movie "+(i+1)+" start and end time");
			String[] parts = scanner.nextLine().trim().split("\\s+");
			if (!(parts.length==2)) {
				System.out.println("Invalid Input");
				return;
			}
			int start = Integer.parseInt(parts[0]);
			int end = Integer.parseInt(parts[1]);
			if (start>=end) {
				System.out.println("Start time must be lower than end time");
				return;
			}
			startTime[i]=start;
			endTime[i]=end;
		}
		Arrays.sort(startTime);
		Arrays.sort(endTime);
		int screensNeeded=0;
		int maxScreens=0;
		int i=0,j=0;
		while (i<numberOfMovies && j<numberOfMovies) {
			if (startTime[i]<endTime[j]) {
				screensNeeded++;
				maxScreens=Math.max(maxScreens, screensNeeded);
				i++;
			}else {
				screensNeeded--;
				j++;
			}
		}
		System.out.println("Minimum number of screens needed is "+maxScreens);
		scanner.close();
	}

}

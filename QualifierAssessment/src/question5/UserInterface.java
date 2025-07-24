package question5;

import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the APP Details");
		String appDetail = scanner.nextLine();
		try {
			AppInfo app= AppInfo.verifyAppDetails(appDetail);
			System.out.println("App Details");
			System.out.println("App id: "+app.getAppId());
			System.out.println("Developer: "+app.getDeveloper());
			System.out.println("Size in MB: "+app.getSizeInMB());
			if(app.getIsFree()) {
				System.out.println("Free for use");
			}
			else {
				System.out.println("Not free for use");
			}
		} catch (InvalidAppInfoException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		scanner.close();
	}

}

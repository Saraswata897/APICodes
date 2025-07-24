package question3;

import java.util.Scanner;

public class UserInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter student details");
		String[] details = scanner.nextLine().split(":");
		String studentType = details[0];
		long studentId = Long.parseLong(details[1]);
		String studentName = details[2];
		String courseName = details[3];
		int yearOfStudy = Integer.parseInt(details[4]);
		double baseFee = Double.parseDouble(details[5]);
		double courseFee = Double.parseDouble(details[6]);
		if (studentType.equalsIgnoreCase("Regular")) {
			RegularStudent regularStudent = new RegularStudent(studentId, studentName, courseName, yearOfStudy, baseFee, courseFee);
			double regularStudentFee = regularStudent.calculateRegularStudentFees();
			if (regularStudentFee>0) {
			System.out.println("Calculated regular mode fees for student "+regularStudent.getStudentName()+" is "+regularStudentFee);}
			else {
				System.out.println("You have provided invalid year of study as "+regularStudent.getYearOfStudy()+" for regular student "+regularStudent.getStudentName());
			}
		}
		else if (studentType.equalsIgnoreCase("Distance")) {
			DistanceStudent distanceStudent = new DistanceStudent(studentId, studentName, courseName, yearOfStudy, baseFee, courseFee);
			double distanceStudentFee = distanceStudent.calculateDistanceStudentFees();
			if (distanceStudentFee>0) {
			System.out.println("Calculated distance mode fees for student "+distanceStudent.getStudentName()+" is "+distanceStudentFee);}
			else {
				System.out.println("You have provided invalid year of study as "+distanceStudent.getYearOfStudy()+" for regular student "+distanceStudent.getStudentName());
			}
		}
		
		scanner.close();
	}

}

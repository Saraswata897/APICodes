package question3;

public class RegularStudent extends Student{
	private double mentorshipFee;

	public RegularStudent(long studentId, String studentName, String courseName, int yearOfStudy, double baseFee,
			double mentorshipFee) {
		super(studentId, studentName, courseName, yearOfStudy, baseFee);
		this.mentorshipFee = mentorshipFee;
	}

	public double getMentorshipFee() {
		return mentorshipFee;
	}

	public void setMentorshipFee(double mentorshipFee) {
		this.mentorshipFee = mentorshipFee;
	}

	public double calculateRegularStudentFees() {
		if (getYearOfStudy()>0 && getYearOfStudy()<5) {
			return (getYearOfStudy()*getBaseFee())+mentorshipFee;
		}else {
			return -1;
		}
	}
	
}

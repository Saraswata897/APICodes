package question3;

public class DistanceStudent extends Student{
	private double materialFee;

	public DistanceStudent(long studentId, String studentName, String courseName, int yearOfStudy, double baseFee,
			double materialFee) {
		super(studentId, studentName, courseName, yearOfStudy, baseFee);
		this.materialFee = materialFee;
	}

	public double getMaterialFee() {
		return materialFee;
	}

	public void setMaterialFee(double materialFee) {
		this.materialFee = materialFee;
	}
	public double calculateDistanceStudentFees() {
		if (getYearOfStudy()>0 && getYearOfStudy()<7) {
			return (getYearOfStudy()*getBaseFee())+materialFee;
		}else {
			return -1;
		}
	}
}

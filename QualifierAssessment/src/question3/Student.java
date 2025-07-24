package question3;

public class Student {
	protected long studentId;
	protected String studentName;
	protected String courseName;
	protected int yearOfStudy;
	protected double baseFee;
	public Student(long studentId, String studentName, String courseName, int yearOfStudy, double baseFee) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.courseName = courseName;
		this.yearOfStudy = yearOfStudy;
		this.baseFee = baseFee;
	}
	public Student() {
		super();
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getYearOfStudy() {
		return yearOfStudy;
	}
	public void setYearOfStudy(int yearOfStudy) {
		this.yearOfStudy = yearOfStudy;
	}
	public double getBaseFee() {
		return baseFee;
	}
	public void setBaseFee(double baseFee) {
		this.baseFee = baseFee;
	}
	
	
	
}

package library;
/**
 * A Student is a User that has a major and class year, as well as a 
 * a borrow limit of 5 resources, a renew limit of 1 period, and a borrow period of 4 weeks. It extends
 * the User class.
 *
 */
public class Student extends User {
	
	public static final int STUDENT_BORROW_LIMIT = 3;
	public static final int STUDENT_BORROW_PERIOD = 4; //in weeks
	public static final int STUDENT_RENEW_LIMIT = 1;

	private int year;	// The year the student will graduate
	private String major;	// The major the student is in
	

	/**
	 * Non - Default Constructor 
	 * @param n - Name
	 * @param id - Identification
	 * @param email - Email
	 * @param phoneNumber - Phone Number
	 * @param year - Year 
	 * @param major - Major
	 */
	public Student(String n, String id, String email, String phoneNumber, int year, String major)
	{
		super(n, id, email,phoneNumber, STUDENT_RENEW_LIMIT, STUDENT_BORROW_LIMIT, STUDENT_BORROW_PERIOD);
		this.year = year;
		this.major = major;
	}


	/**
	 * Getter for the year
	 * @return the year
	 */
	public int getClassYear() 
	{
		return year;
	}

	/**
	 * Getter for the major
	 * @return the major
	 */
	public String getMajor() 
	{
		return major;
	}

	/***
	 * toString returns a nicely formatted String with all the Student's field information.
	 */
	public String toString()
	{
		return "STUDENT:\n" + super.toString() + "\nYEAR: " + year + "\nMAJOR: " + major;
	}
}
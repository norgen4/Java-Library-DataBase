package library;
/**
 * A Professor is a User that has a department, 
 * a borrow limit of 5 resources, a renew limit of 2 periods. It extends
 * the User class.
 *
 */
public class Professor extends User {

	public static final int PROF_BORROW_LIMIT = 5;  
	public static final int PROF_BORROW_PERIOD = 12; //in weeks
	public static final int PROF_RENEW_LIMIT = 2;

	
	private String department; //professor's department
	

	/**
	 * Constructor. Sets borrow limit to 5 resources, renew limit to 2 renewal periods, and a borrow limit to 12 weeks.
	 * @param id - id
	 * @param n - name
	 * @param email - email
	 * @param phoneNumber - phone number
	 * @param department - department
	 */
	public Professor(String n, String id, String email, String phoneNumber, String department)
	{
		super(n, id, email, phoneNumber, PROF_RENEW_LIMIT, PROF_BORROW_LIMIT, PROF_BORROW_PERIOD);
		this.department = department;
	}
	

	/**
	 * Getter for the department
	 * @return String the department
	 */
	public String getDepartment()
	{
		return department;
	}
	
	/***
	 * toString returns a nicely formatted String with all the Professor's field information.
	 */
	public String toString()
	{
		return "PROFESSOR:\n" + super.toString() + "\nDEPARTMENT: " + department;
	}
}
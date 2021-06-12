package library;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A (Library) User has a id, name, email, and phoneNumber, as well as a limit of how many times
 * the user can renew, a limit of how many resources can be checked out at a time, and a
 * number of weeks in a borrow period.
 * 
 */
public class User {

	private String id;  			// ID of the Library user
	private String name;			// Name of the Library user
	private String email;			// Email of the Library user
	private String phoneNumber;		// Phone Number of the Library user

	private ArrayList<Resource> checkedOutResources; // Collection of Resources of the Library user

	private int renewLimit;		// Sets a limit for how long a library user can renew a resoucr
	private int borrowLimit;	// Sets a limit for how long a library user can borrow a resource
	private int borrowPeriodInWeeks;	// Sets a limit for how long a library user can borrow a resource



	/**
	 * User Constructor
	 * @param id - ID of the Library user
	 * @param n - Name of the Library user
	 * @param email - Email of the Library user
	 * @param phoneNumber - Phone Number of the Library user
	 */
	public User(String n, String id, String email, String phoneNumber, int renewLimit, int borrowLimit, int borrowPeriodInWeeks)
	{
		this.id = id;
		this.name = n;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.renewLimit = renewLimit;
		this.borrowLimit = borrowLimit;
		this.borrowPeriodInWeeks = borrowPeriodInWeeks;
		this.checkedOutResources = new ArrayList<Resource>(); //An empty resource list
	}

	//***********************
	// GETTERS
	//***********************	
	/**
	 * Getter
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Getter
	 * @return the email
	 */
	public String getEmail() 
	{
		return email;
	}

	/**
	 * Getter
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}

	/**
	 * Getter
	 * @return the id
	 */
	public String getId() 
	{
		return id;
	}

	/**
	 * Getter
	 * @return the collection
	 */
	public ArrayList<Resource> getCheckedOutResources() 
	{
		return checkedOutResources;
	}
	/**
	 * Getter
	 * @return the month
	 */
	public int getBorrowPeriod() 
	{
		return borrowPeriodInWeeks;
	}

	/**
	 * Getter
	 * @return the renewLimit
	 */
	public int getRenewLimit() 
	{
		return renewLimit;
	}

	/**
	 * Getter 
	 * @return the borrowLimit
	 */
	public int getBorrowLimit()
	{
		return borrowLimit;
	}


	/**
	 * Setter
	 * @param borrowLimit the borrowLimit to set
	 */
	public void getBorrowLimit(int borrowLimit) 
	{
		this.borrowLimit = borrowLimit;
	}

	//*************************
	//Other methods
	//*************************

	/**
	 * The checkOut method checks a resource out to its owner.
	 * If the resource is already checked out or the user has too
	 * many checked out resources, the checkOut will not be allowed.
	 * 
	 * @param r - Resource to check out
	 * @param boolean - True if the checkout is successful; false otherwise. 
	 */
	public boolean checkOut(Resource r)
	{
		//If the user has room to checkout the book, check it out.
		if (checkedOutResources.size() < this.borrowLimit)
		{
			boolean success = r.checkOut(this);
			if (success)
				checkedOutResources.add(r);
			return true;
		}
		else //Otherwise, give error.
		{
			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("Cannot check out resource - User has too many resources checked out.");
			a.showAndWait();
			return false;
		}



	}
	/**
	 * The checkOut method checks a resource out to its owner.
	 * If the resource is already checked out or the user has too
	 * many checked out resources, the checkOut will not be allowed.
	 * 
	 * @param r - Resource to check out
	 * @param date - A string (like "2021-05-15") to set the due date to.
	 * @param boolean - True if the checkout is successful; false otherwise. 
	 */
	public boolean checkOut(Resource r, String date)
	{
		boolean success = checkOut(r);
		if (success)
			r.setDueDate(date);
		return success;
	}
	/**
	 * The checkIn method removes the resource from the user's checked out resources
	 * and checks the book back in.
	 * 
	 * @param r - Resource
	 * @return False if the resource was not checked out to the user in the first place;
	 * True otherwise 
	 */
	public boolean checkIn(Resource r)
	{
		//As long as the user has the reource, check it in.
		if (checkedOutResources.contains(r))
		{
			checkedOutResources.remove(r);
			r.checkIn();
			return true;
		}
		else {
			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("Cannot check in resource - Not checked out to user.");
			a.showAndWait();
			return false;		
		}

	}

	/***
	 * The renew method renews a resource that has already been checked out to the user.
	 * If the resource has been checkedOut too long or if the max number of renewal periods has been 
	 * accrued, then the renew is not allowed.
	 * 
	 * @param r - The resource to renew.
	 * @return True if the resource was allowed to be renewd; False otehrwise.
	 */
	public boolean renew (Resource r)
	{

		//Check to make sure the resource is held by this user.
		if (checkedOutResources.contains(r))
		{
			boolean renewSuccessful = r.renew();  //Renew method will print warning
			return renewSuccessful;
		}
		else {
			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("Cannot renew resource - Not checked out to user.");
			a.showAndWait();
			return false;		
		}
	}

	/**
	 *  toString returns a nicely formatted string of all the User's info.
	 */
	@Override
	public String toString() {

		String resources = "\n***********************************\n"
				+ "CHECKED OUT RESOURCES:";
		if (checkedOutResources.size() == 0)
		{

			for (int i = 0; i < checkedOutResources.size(); i++)
			{
				resources += "\n" + checkedOutResources.get(i).toString();
			}
			resources += "\n";
		}
		else {
			resources += "NONE\n";
		}
		resources += "***********************************";		

		return "ID: " + id + "\nNAME: " + name + "\nEMAIL: " + email + "\nPHONE: " + phoneNumber
				+ "\nRENEW LIMIT: " + renewLimit + "\nNORROW LIMIT: "
				+ borrowLimit + "\nBORROW PERIOD: " + borrowPeriodInWeeks + resources;
	}

}
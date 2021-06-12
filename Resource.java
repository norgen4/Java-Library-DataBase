package library;
import java.time.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class represents a Resource that can be borrowed at a library.
 * It has a title, a dueDate and borrower (both of which are null if the
 * Resource is not checked out), a boolean flag indicating if it is
 * renewable, and a renewCounter to determine how many times it has been
 * renwed by the borrower.
 *
 * Note that checkIn, checkOut, and renew functionalities are provided.
 * Also note that the overloads of the checkOut and renew methods 
 * are provided and work as follows.  If a user does not
 * specify a borrow period, then the borrowerPeriod on the borrower's field
 * is used to set the dueDate.  
 *
 */
public abstract class Resource {

	private String title; //Title of the resource 
	private LocalDate dueDate; //Date the resource is due (null if not checked out)
	private boolean renewable; //If the resource can be renewed
	private User borrower; //Who has the resource is checked out to (null if not checked out)
	private int renewCounter; //How many times the Resource has been renewed by the current user
	private int year; //year of production

	/**
	 * Constructor
	 * @param title - title of resource
	 * @param year - year of production
	 * @param renewable - if the resource is renewable or not
	 */
	public Resource(String title, int year, boolean renewable)
	{
		this.title = title;
		this.year = year;
		this.renewable = renewable;
		this.dueDate = null;   //set due date to null until checked out
		this.borrower = null;    //set borrower to null until checked out
		this.renewCounter = 0; //set the renew counter to 0 until renewed
	}

	//******************************
	// GETTERS
	//******************************

	/**
	 * Getter for title
	 * @return title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Getter for the year
	 * @return year
	 */
	public int getYear() 
	{
		return year;
	}

	/**
	 * Getter for renewable
	 * @return True if the resource is renewable.  False otherwise.
	 */
	public boolean isRenewable() 
	{
		return renewable;
	}

	/**
	 * Getter for the borrower (null if not checked out)
	 * @return user who has checked out this resource
	 */
	public User getBorrower()
	{
		return borrower;
	}

	/**
	 * Getter for the due date
	 * @param due date
	 */
	public LocalDate getDueDate() 
	{	
		return this.dueDate;
	}

	/**
	 * Getter for the number of times a resource has been renewed
	 * @return The number of times the resource has been renewed
	 */
	public int getNumTimesRenewed()
	{
		return this.renewCounter;
	}

	//******************************
	// SETTERS
	//******************************


	/**
	 * Sets the due date based on a string, , like "2021-01-01".
	 * This method has mainly been provided so that the coder
	 * can easily create test data.
	 * 
	 * @param date String indicating a date, like "2021-01-01".
	 */
	public void setDueDate(String s) 
	{	
		this.dueDate = LocalDate.parse(s);
	}


	/**
	 * Sets the due date based on a LocalDate object.
	 * This method has mainly been provided so that the coder
	 * can easily create test data.
	 * @param date A LocalDate object set to a date.
	 */
	public void setDueDate(LocalDate date) 
	{	
		this.dueDate = date;
	}


	//******************************
	// Other methods
	//******************************


	/**
	 * Method to determine if a resource is checked out.
	 * @return True if the resource is checked out.  False otherwise.
	 */
	public boolean isCheckedOut() 
	{
		//If dueDate is null, the item is not checked out.
		//If due Date is not null, it is checked out.
		return (dueDate != null);
	}

	/**
	 * Method to return if the resource is late
	 * @return True if today's date is after the due date.  False otherwise.
	 */
	public boolean isLate()
	{
		return LocalDate.now().isAfter(dueDate);
	}

	/**
	 * Checks out a resource to a borrower.  The due date of the resource
	 * will be based on the borrower's allowed borrow period.
	 * If the resource is checked out already, the check out is not allowed
	 * and false is returned to indicate this.
	 * 
	 * @param borrower The borrower to check the resource out to.
	 * @param wksInBorrowPeriod  The weeks it should be checked out for.
	 * @return True if the check out was successful.  False if there was an error.
	 */
	public boolean checkOut(User borrower)
	{ 
		return checkOut(borrower, borrower.getBorrowPeriod());
	}

	/**
	 * Checks out a resource to a borrower for a certain number of weeks.
	 * If the resource is checked out already, the check out is not allowed
	 * and a boolean flag of false is returned.
	 * 
	 * @param borrower The borrower to check the resource out to.
	 * @param wksInBorrowPeriod  The weeks it should be checked out for.
	 * @return True if the check out was successful.  False if there was an error.
	 */
	public boolean checkOut(User borrower, int wksInBorrowPeriod)
	{ 
		if (!isCheckedOut())
		{
			this.borrower = borrower;
			this.dueDate = LocalDate.now().plusWeeks(wksInBorrowPeriod);
			return true;
		}
		else
		{
			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("Resource checked out already - cannot check out.");
			a.showAndWait();
			return false;
		}
	}


	/**
	 * Checks in the current resource..
	 */
	public void checkIn() 
	{
		//Reset dueDate and borrower to null to signify it is not checked in.
		//Set renewCounter to 0.
		this.dueDate = null;
		this.borrower = null;
		this.renewCounter = 0;
	}


	
	/**
	 * The renew method checks to see if the user is allowed to renew the book.
	 * If the user has checked the book out for too long or if the resource is 
	 * not renewable, then the renewal is not allowed.  Note that this method 
	 * does not automatically check in the resource if the renewal is not allowed.
	 * Sets the due date based on the borrower's borrow period.
	 * 
	 * @return True if the renew was allowed.  False otherwise.
	 */
	public boolean renew()
	{
		return renew(borrower.getBorrowPeriod());
	}
	
	/**
	 * The renew method checks to see if the user is allowed to renew the book.
	 * If the user has checked the book out for too long or if the resource is 
	 * not renewable, then the renewal is not allowed.  Note that this method 
	 * does not automatically check in the resource if the renewal is not allowed.
	 * 
	 * @param weeksInBorrowPeriod - How many weeks the resource can be renewed for
	 * @return True if the renew was allowed.  False otherwise.
	 */
	public boolean renew(int weeksInBorrowPeriod)
	{
		//If the resource is renewable, procceed.
		if (isRenewable())
		{
			//Say a user is trying to renew a late book.  Determine how many 
			//renewal periods they have essentially used while being late.  
			//See explanation on the calcRenewalPeriodsForLateBook method for details.
			int lateRenewalDelta = calcRenewalPeriodsForLateBook(weeksInBorrowPeriod);

			//If the user has not renewed the book too often, let them renew it.
			if (renewCounter + lateRenewalDelta < borrower.getRenewLimit())
			{
				dueDate = dueDate.plusWeeks(weeksInBorrowPeriod * (1 + lateRenewalDelta));
				renewCounter = renewCounter + 1 + lateRenewalDelta;
				return true;
			}
			else //The book cannot be renewed so warn the user.
			{	
				Alert a = new Alert(AlertType.INFORMATION);
				a.setContentText("The borrower has borrowed this resource for too long.  It cannot be renwed again.");
				a.showAndWait();
	
				return false;
			}
		}
		else //The resource is not renewable.
		{
			Alert a = new Alert(AlertType.INFORMATION);
			a.setContentText("The resource is not renewable.");
			a.showAndWait();
	
			return false;			
		}
	}


	/**
	 * Helper method to Determine how many renewal periods a borrower has essentially 
	 * used up while being late.  
	 * For example: 
	 *    if the book was due at 4 weeks and it is not 6 weeks, then the 
	 *       lateRenewalDelta would be set to 1.  
	 *    If it was due at 4 weeks and it is now 10 weeks, then the
	 *        lateRenewalDelta would be set to 2. 
	 *    If the book is not late, it will be set to 0.
	 * @param wksInBorrowPeriod  How many weeks are in the user's borrow period
	 * @return how many renewal periods a borrower has essentially used up while being late.
	 */
	private int calcRenewalPeriodsForLateBook(int wksInBorrowPeriod)
	{		
		int counter = 0;
		LocalDate origDueDate = dueDate;

		//Continue adding on borrow periods until the late resource is no longer late.
		while (origDueDate != null && LocalDate.now().isAfter(origDueDate)) 
		{
			origDueDate = origDueDate.plusWeeks(wksInBorrowPeriod);
			counter++;
		}

		return counter;
	}


	/**
	 * toSring method
	 * @return A nicely formatted string with the resource's details.
	 */
	@Override
	public String toString() {
		return "TITLE: " + title + "\nDUE DATE: " + dueDate + "\nRENEWABLE: " + renewable + 
				"\nRENEW COUNTER: "  + renewCounter + "\nBORROWER: " + borrower.toString();
	}


}
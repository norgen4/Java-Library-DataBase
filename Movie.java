package library;

/**
 * This class extends from the Resource class.
 * 
 * The Movie class is a Resource  with a production company.  
 * The important thing to note is that the checkOut method is designed
 * to only allow the Resource to be checked out for 1 week.
 * 
 */
public class Movie extends Resource{
	
	public static final int MOVIE_BORROW_PERIOD = 1; //in weeks
	
	private String productionCompany; //productionCompany of movie
	
	/**
	 * Movie Constructor
	 * 
	 * @param title  Title of a movie
	 * @param year  Year the movie released
	 * @param productionCompany  The company who created the movie
	 */
	public Movie (String title, int year, String productionCompany) 
	{
		super(title, year, false);  //Set isRenewable to false
		this.productionCompany = productionCompany;
	}
	


	/**
	 * Getter for productionCompany.
	 * @return the productionCompany
	 */
	public String getProductionCompany() 
	{
		return productionCompany;
	}
	
	/**
	 * 
	 * The checkOut method checks a movie out to a borrower
	 * for exactly 1 week.
	 * 
	 * @param User The borrower to check the movie out to.
	 */
	@Override
	public boolean checkOut(User borrower)
	{
		//Call the parent checkOut method, but override the checkout period.
		return super.checkOut(borrower, MOVIE_BORROW_PERIOD);
	}	

	/**
	 * ToString Method
	 * @return String with all Movie field information.
	 */
	public String toString()
	{
		String str = "Movie:\n" + super.toString() + 
				"\nProduction Company: " + productionCompany;
		
		return str;
	}

}

package library;
import java.time.LocalDate;
import java.util.*;

/**
 * This class is a helper/utility class that populates
 * resource and user arraylists in order to simulate
 * a functioning library
 * @author Luke Cossman and Emanuel Luna
 *
 */
public class LibraryDatabase {
	
	public static ArrayList<Resource> resources = new ArrayList<Resource>();
	
	public static ArrayList<User> users = new ArrayList<User>();

	/**
	 * Populates a resource arraylist with different Books and Movies.
	 * Populates a users arraylist with different students and teachers.
	 */
	public static void populateCollection() 
	{
		//Thank you to Manny and Luke - I stoke their DB.
		
		//Add Books 
		resources.add(new Book("1234567890", "Book of Revelations", "King Arthur", 999));
		resources.add(new Book("0553274252", "Jesus An Interview Across Time", "Andrew G. Hodges", 1988));
		resources.add(new Book("32072000217100", "The Man of Others", "Ben F. Meyer", 1970));
		resources.add(new Book("32072001810937", "The Mighty Hippodrome", "Norman Clarke", 1968));
		resources.add(new Book("32072001823328", "Chicago's Awful Theater Horror","Bishop Fallows", 1904));
		resources.add(new Book("9780767905923", "Tuesdays with Morrie","Mitch Albom", 1997));
		
		// Add Movies
		resources.add(new Movie("The Wizard", 1989, "Universal Studios"));
		resources.add(new Movie("Pulp Fiction", 1994, "Miramax"));
		resources.add(new Movie("Star Wars: The Phantom Menace", 1999, "Lucasfilms"));
		resources.add(new Movie("Captain America: Civil War", 2016, "Marvel Studios"));
		resources.add(new Movie("Ratatouile", 2007, "Pixar"));
		
		// Add Students
		users.add(new Student("Luke Patrick Cossmann", "1262027","lc2027@desales.edu", "215-896-9842",2024, "Computer Science"));
		users.add(new Student("Emanuel Luna", "1267027","el7027273@desales.edu","484-587-0165", 2024, "Computer Science"));
		users.add(new Student("Joseph Traglia", "1265834","jt5834@desales.edu","345-936-9665", 2024, "Computer Science"));
		users.add(new Student("Lindsey Gordon","1264758", "lg6475@desales.edu","675-982-0012", 2024, "Theater"));
		users.add(new Student("Kimberly Ciara Lemke", "1267585","kl7585@desales.edu", "484-854-9332", 2024, "English"));
		
		// Add Professors
		users.add(new Professor("Kathleen Ryan", "1234567", "kr6845@desales.edu", "345-976-0030", "Computer Science"));
		users.add(new Professor("Pranshu Gupta", "1265577", "pg5577@desales.edu", "456-987-0020", "Computer Science"));
		users.add(new Professor("Karen Ruggles", "1111111", "kr1111@desales.edu", "456-987-0023", "Computer Science"));
		users.add(new Professor("Joe Walsh", "22222222", "jw22222@desales.edu", "919-000-0001", "Computer Science"));
		users.add(new Professor("Catlin Owens", "1265748", "co5748@desales.edu", "484-987-3665", "Math"));
		users.add(new Professor("Ronnee Moyer", "1264778", "rm4778@desales.edu", "484-995-7743", "English"));
		users.add(new Professor("John Francois", "1264563", "jf4563@desales.edu", "787-009-3478", "French"));
		
		//Check out resources to some students
		users.get(1).checkOut(resources.get(3), "2021-04-01"); // The Mighty Hippodrome is checked out by Emanuel
		users.get(0).checkOut(resources.get(7), "2021-03-01"); // Star Wars; The Phantom Menace is checked out by Luke
		
		//Professors who checked out a book or movie
		users.get(5).checkOut(resources.get(4), "2021-04-07"); // Check out resoruces to Dr. Ryan
		users.get(5).checkOut(resources.get(5), "2021-03-07"); // Check out resoruces to Dr. Ryan
		users.get(5).checkOut(resources.get(6)); // Check out resoruces to Dr. Ryan
		users.get(5).checkOut(resources.get(1)); // Check out resoruces to Dr. Ryan
		users.get(5).checkOut(resources.get(8)); // Check out resoruces to Dr. Ryan
		users.get(8).checkOut(resources.get(2), "2020-04-01"); // The Man of Others is checked out by Prof. Moyer
	}
	
	/**
	 * Finds a Resource based on its title.
	 * @param title Title of Resource
	 * @return The resource with the given title, or NULL if none is found.
	 */
	public static Resource findResource(String title)
	{
		for (int i = 0; i < resources.size(); i++)
		{
			if (resources.get(i).getTitle().equals(title))
				return resources.get(i);
		}
		
		return null;
	}

	/**
	 * Finds a User based on the user's email.
	 * @param email The user's email
	 * @return The user with the given email, or NULL if none is found.
	 */
	public static User findUserWithEmail(String email)
	{
		for (int i = 0; i < users.size(); i++)
		{
			if (users.get(i).getEmail().equals(email))
				return users.get(i);
		}
		
		return null;
	}

	/**
	 * Finds a User based on the user's id.
	 * @param email The user's id
	 * @return The user with the given id, or NULL if none is found.
	 */
	public static User findUserWithID(String id)
	{
		for (int i = 0; i < users.size(); i++)
		{
			if (users.get(i).getId().equals(id))
				return users.get(i);
		}
		
		return null;
	}

	/**
	 * Adds a user to the Library.
	 * @param u - User to add
	 */
	public static void addUser(User u)
	{
		users.add(u);
	}
	
	/**
	 * Adds a resource to the Library.
	 * @param r - Resource to add
	 */
	public static void addResources(Resource r)
	{
		resources.add(r);
	}
	
	/**
	 * Gets the list of all resources.
	 * @return An array lit of all resources in the library.
	 */
	public static ArrayList<Resource> getResources()
	{
		return resources;
	}

	/**
	 * Gets the list of all Users.
	 * @return An array lit of all users in the library.
	 */
	public static ArrayList<User> getUsers()
	{
		return users;
	}

}

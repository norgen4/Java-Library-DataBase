package library;

/**
 * This class extends from the Resource class.
 * 
 * The Book class is a Resource with an ISBN and an author.
 * 
 */
public class Book extends Resource {

	private String isbn; //ISBN number of book
	private String author; //Author of book

	/**
	 * Constructor
	 * @param isbnNumber The ISBN on the book
	 * @param title Title of the book
	 * @param author  Author of the book
	 * @param publicationYear  Year of publication
	 */
	public Book(String isbnNumber, String title, String author, int year) 
	{
		super(title, year, true); //Set the renewable flag to true
		this.isbn = isbnNumber;
		this.author = author;
	}

	/**
	 * Getter for the ISBN
	 * @return A string with the ISBN Number
	 */
	public String getISBN() {
		return isbn;
	}

	/**
	 * Getter for the author
	 * @return The author of the book
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * ToString Method
	 * @return String with all the Book's field information.
	 */
	public String toString()
	{
		String str = "BOOK:\n" + super.toString() + 
				"\nISBN: " + isbn +
				"\nAuthor: " + author;
		
		return str;
	}
}
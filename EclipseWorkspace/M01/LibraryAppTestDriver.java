/**
 * 
 */


public class LibraryAppTestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Book book1 = new Book("Joy Buolamwini", "Unmasking AI", 2023);
		Book book2 = new Book("Hannah Fry", "Hello World", 2018);
		
		// System.out.printIn

	}

}


class Book {
	// Book instance variables
	private String author;
	private String title;
	private int pubYear;
	
	// Book constructor
	public Book(String author, String title, int pubYear) {
		this.author = author;
		this.title = title;
		this.pubYear = pubYear;
	}
	
	// getter methods
	public String getAuthor() {
		return author;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getPubYear() {
		return pubYear;
	}
}


/**
 * Library App Test Driver
 * Contains Book and Library Classes to be tested
 * 
 */

import java.util.Scanner;

public class LibraryAppTestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//  Null error - how to prevent this?  
		// --- unit test checks for Book ---
		System.out.println("Unit Test Book Class");
		Book unitTestBook; = new PrintBook("Unmasking AI", "Joy Buolamwini", 2023);
		
		System.out.println("getTitle():   " + unitTestBook.getTitle());
		System.out.println("getAuthor():  " + unitTestBook.getAuthor());
		System.out.println("getYear():    " + unitTestBook.getPubYear());
		System.out.println("stringOfBookDetails():   " + unitTestBook.toString());
		System.out.println();
		System.out.println("Setting up Test Library");
		int numberOfShelves = 3;
		int shelfCapacity = 4;
		System.out.println("Shelves (rows): " + numberOfShelves);
		System.out.println("Slots per shelf (columns): " + shelfCapacity);
		System.out.println("Total capacity: " + (numberOfShelves * shelfCapacity));
		System.out.println();
		Library library = new Library("Test Library", numberOfShelves, shelfCapacity);
		library.displayCountPerShelf();
		library.printAllBooks();
		library.displayOldest();
		// Row 0
		library.addBook(null);
		library.addBook(new EBook("Unmasking AI", "Joy Buolamwini", 2023));
		library.addBook(new EBook("Hello World", "Hannah Fry", 2018));
		library.addBook(new EBook("Race After Technology", "Ruha Benjamin", 2019));
		library.addBook(new EBook("Deep Learning", "Ian Goodfellow", 2016));
		library.displayCountPerShelf();
		library.printAllBooks();
		library.displayOldest();
		// Row 1
		library.addBook(new PrintBook("Algorithms to Live By", "Brian Christian", 2016));
		library.addBook(new PrintBook("Weapons of Math Destruction", "Cathy O'Neil", 2016));
		library.addBook(new PrintBook("The Mythical Man-Month", "Fred Brooks", 1975));
		library.addBook(new PrintBook("Refactoring", "Martin Fowler", 1999));
		// Row 2
		library.addBook(new PrintBook("The Pragmatic Programmer", "Andrew Hunt & David Thomas", 1999));
		library.addBook(new PrintBook("Peopleware", "Tom DeMarco & Tim Lister", 1987));
		library.addBook(new PrintBook("Computer Lib / Dream Machines", "Ted Nelson", 1975));
		library.displayCountPerShelf();
		library.printAllBooks();
		library.displayOldest();
		System.out.println();
		System.out.println("Test add more books than capacity...");
		library.addBook(new EBook("Extra Title", "Extra Author", 2024)); // should trigger "full" message
		library.displayCountPerShelf();
		library.printAllBooks();
		library.displayOldest();
	    
	}
}

abstract class Book {
	// Book instance variables
	private String author;
	private String title;
	private int pubYear;
	
	public Book() {
	}
	
	/**
	 * Book constructor
	 * @param author
	 * @param title
	 * @param pubYear
	 */
	public Book(String author, String title, int pubYear) {
		this.author = author;
		this.title = title;
		this.pubYear = pubYear;
	}
	
	// getter methods
	public String getAuthor() {
		// returns the author
		return author;
	}
	
	public String getTitle() {
		// returns book title 
		return title;
	}
	
	public int getPubYear() {
		// returns year published
		return pubYear;
	}
//	force subclasses to implement individual bookType getters
	public abstract String getBookType();
	
//	setter methods
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setPubYear(int pubYear) {
		this.pubYear = pubYear;
	}
	
//	toString override to print book details:
	@Override
	public String toString() {
		// return formatted string of book title, author, and year published
		return this.getBookType() + "\t\"" + title + "\" by " + author + " (" + pubYear + ") [" 
				+ this.getLoanDays() + " days, $" + this.getDailyLateFee() + "/day]";
	}
	
	
	/**
	 * final - cannot be overridden
	 * @param daysLate
	 * @return
	 */
	public final double calculateLateFee(int daysLate) {
		double lateFee = 0;
		if (daysLate > 0) {
			lateFee = daysLate * getDailyLateFee();
		}
		return lateFee;
	}
// abstract classes must be implemented by sub-class
	public abstract int getLoanDays();
	
	public abstract double getDailyLateFee();
	
}

class PrintBook extends Book {
	private static final String bookType = "PRINT"; 
//	has its own constructor which implements the parent class methods
	public PrintBook(String title, String author, int pubYear) {
		setTitle(title);
		setAuthor(author);
		setPubYear(pubYear);
	}
	
	@Override
	public String getBookType() {
		return bookType;
	}
	
	@Override
	public int getLoanDays() {
		return 21;
	}
//	overrides the empty method in the parent class
	@Override
	public double getDailyLateFee() {
		return 0.25;
	}
}

class EBook extends Book {
	private static final String bookType = "EBOOK"; 
	
	public EBook(String title, String author, int pubYear) {
		setTitle(title);
		setAuthor(author);
		setPubYear(pubYear);
	}
	
	@Override
	public String getBookType() {
		return bookType;
	}
	
	@Override
	public int getLoanDays() {
		return 14;
	}
	
	@Override
	public double getDailyLateFee() {
		return 0.10;
	}
}

class Library {
	private String name;
	private Book[][] bookShelf;
	private int numberOfShelves;
	private int shelfCapacity;
	private int currentShelf;
	private int currentSlot;
	private int currentTotalBooks;
	private int totalBookCapacity;
	private boolean isFull;
	private static final int MAX_SHELF_CAPACITY = 6;
	
	// Library constructor
	public Library(String name, int shelves, int shelfCapacity) {
		// check if details have been entered correctly
		if (name == null || name.isEmpty()) {
			this.name = "Library";
		} 
		else {
			this.name = name;
		}
		if (shelves <= 0 || shelfCapacity <= 0) {
			this.numberOfShelves = 1;
			this.shelfCapacity = 1;
		} 
		else {
			this.numberOfShelves = shelves;
			this.shelfCapacity = shelfCapacity;
		}
		// set default values
		this.totalBookCapacity = numberOfShelves * shelfCapacity;
		this.bookShelf = new Book[numberOfShelves][shelfCapacity];
		this.currentTotalBooks = 0;
		this.currentShelf = 0;
		this.currentSlot = 0;
		this.isFull = false;
	}
	// check the number of books on a given shelf
	// i think the length method won't do what I want with a 2D array
	public int getBooksOnShelf(Book[][] bookShelf) {
		int bookCount = 0;
		// loop over the outer array
		for (int i = 0; i < bookShelf.length; i++) {
			// if there is something in the slot, add to the bookCount
			if (bookShelf[i] != null) {
				bookCount += 1;
			}
		}
		return bookCount;
	}
	
	public String getName() {
		return name;
	}
	
	public int countBooks() {
		return currentTotalBooks;
	}
	
	public boolean addBook(Book book) {
		// check that details are entered in the correct way
		if (book == null){
			System.out.println("Please enter a book title.");
			return false;
		}
		if (isFull){
			System.out.println("Library is full. Couldn't add " + book.toString());
			return false;
		}
		// warn if there is not enough capacity on the shelf and move to next shelf
		if (getBooksOnShelf(bookShelf) > MAX_SHELF_CAPACITY) {
			System.out.println("Not enough space on this shelf.");
			currentShelf += 1;
		}
		
		bookShelf[currentShelf][currentSlot] = book;
		System.out.println("Added " + book.toString() + " at shelf " + (currentShelf + 1) + ", slot "
				+ (currentSlot + 1));
		currentTotalBooks = currentTotalBooks + 1;

		// iterate shelf currentShelf and currentSlot
		if (currentTotalBooks >= totalBookCapacity){
			isFull = true;
		} else{
			int nextIndex = currentTotalBooks; 
			currentShelf = nextIndex / shelfCapacity;
			currentSlot = nextIndex % shelfCapacity;
		}
		return true;
	}

	/**
	* Prints the number of books on each shelf and returns the total.
	*
	* @return total number of books across all shelves
	*/
	public void displayCountPerShelf()
	{
		int fullRows = currentTotalBooks / shelfCapacity;
		int remainder = currentTotalBooks % shelfCapacity;
		for (int rowIndex = 0; rowIndex < numberOfShelves; rowIndex++)
		{
			int booksOnThisShelf;
			if (rowIndex < fullRows)
			{
				booksOnThisShelf = shelfCapacity;
			} else if (rowIndex == fullRows)
			{
				booksOnThisShelf = remainder;
			} else
			{
				booksOnThisShelf = 0;
			}
			System.out.println("Shelf " + (rowIndex + 1) + " has " + booksOnThisShelf + " books");
		}
	}

	/**
	* @param shelf      an array of Book objects for one shelf
	* @param shelfIndex shelf number  and not array index number
	*/
	private void printListofBooks(Book[] shelf, int shelfIndex)
	{
		for (int columnIndex = 0; columnIndex < shelf.length; columnIndex++)
		{
			Book currentBook = shelf[columnIndex];
			if (currentBook != null)
			{
				System.out.printf("%5d\t%5d\t%s \n", shelfIndex,
						columnIndex + 1, currentBook.toString());
			}
		}
	}
	
	/**
	* Prints all books in the library by calling printShelf for each shelf.
	*/
	public void printAllBooks() {
		System.out.println("------------------------------------------------------------");
		System.out.println("All books in " + getName());
		System.out.println("\nShelf\tSlot\tBook Type\tBook Details");
		System.out.println("------------------------------------------------------------");

	    // If no books were found, display appropriate message

		for (int rowIndex = 0; rowIndex < numberOfShelves; rowIndex++) {
			// Reuse helper method for each shelf (row)
			printListofBooks(bookShelf[rowIndex], rowIndex + 1);
		}
		System.out.println();
		System.out.println("(" + currentTotalBooks + " of " + (numberOfShelves * shelfCapacity) + " slots filled)\n");
	}
	
	/**
	* Converts all currently stored books into a  1D array.
	*
	* @return Book[] containing exactly the books in the library, in order added.
	*/
	private Book[] convertToOneDimension()
	{
		Book[] oneDimension = new Book[currentTotalBooks];
		int index = 0;
		for (int shelfIndex = 0; shelfIndex < numberOfShelves; shelfIndex++)
		{
			for (int slotIndex = 0; slotIndex < shelfCapacity; slotIndex++)
			{
				if (bookShelf[shelfIndex][slotIndex] != null)
				{
					oneDimension[index] = bookShelf[shelfIndex][slotIndex];
					index = index + 1;
					if (index >= currentTotalBooks)
					{
						// early exit once all books copied
						return oneDimension;
					}
				}
			}
		}
		return oneDimension;
	}
	
	public void displayOldest()
	{
		Book[] allBooks = convertToOneDimension();
		if (allBooks.length == 0)
		{
			System.out.println("Display Oldest: Library is empty.");
			return;
		}
		// Pass 1: find min year
		int earliestYear = allBooks[0].getPubYear();
		for (int i = 1; i < allBooks.length; i++)
		{
			if (allBooks[i].getPubYear() < earliestYear)
			{
				earliestYear = allBooks[i].getPubYear();
			}
		}
		// Pass 2: print all matches
		System.out.println("------------------------------------------------------------");
		System.out.println("Oldest books in " + getName());
		System.out.println("Earliest publication year: " + earliestYear);
		System.out.println();
		for (int i = 0; i < allBooks.length; i++)
		{
			if (allBooks[i].getPubYear() == earliestYear)
			{
				System.out.println(allBooks[i].toString());
			}
		}
	}

	
}

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
  Book nullBook = null;
  System.out.println(nullBook.getTitle());

  Scanner input = new Scanner(System.in);

  System.out.print("Enter the year: ");
  // read int from keyboard for year
  int year = input.nextInt();
//  Use .hasNextInt()!

  Book book1 = new Book("Joy Buolamwini", "Unmasking AI", 2023);
		Book book2 = new Book("Hannah Fry", "Hello World", 2018);
		Book book3 = new Book("Ruha Benjamin", "Race After Technology", 2019);
		
//		book1.stringOfBookDetails();
//		book2.stringOfBookDetails();

	    // Create a library and add books
	    Library myLibrary = new Library("Test Library", 3, 4);
	    myLibrary.addBook(book1);
	    myLibrary.addBook(book2);
	    myLibrary.addBook(book3);
	    
	    // Print all books in the library
	    myLibrary.printAllBooks();
	    
	}

}


abstract class Book {
	// Book instance variables
	private String author;
	private String title;
	private int pubYear;
	
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
	
	@Override
	public String toString() {
		// return formatted string of book title, author, and year published
		return "\"" + title + "\" by " + author + " (" + pubYear + ")";
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

	public abstract int getLoanDays();
	
	public abstract double getDailyLateFee();
	
}

class PrintBook extends Book {
	
	public PrintBook() {
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

	// nested for loop to iterate over the bookShelf object
	public void printAllBooks() {
		boolean hasBooks = false;
		
		System.out.println("All Books in Test Library");
	    System.out.println("Shelf\tSlot\tBook Details");
	    System.out.println("----------------------------");
	    	    
	    // Nested loop to iterate through all shelves and slots
	    for (int shelf = 0; shelf < bookShelf.length; shelf++) {
	        for (int slot = 0; slot < bookShelf[shelf].length; slot++) {
	            // Check if there's a book in this position
	            if (bookShelf[shelf][slot] != null) {
	                hasBooks = true;
	                System.out.println((shelf + 1) + (slot + 1) + ": " + 
	                                 bookShelf[shelf][slot].toString());
	            }
	        }
	    }
	    
	    // If no books were found, display appropriate message
	    if (!hasBooks) {
	        System.out.println("No books in the library.");
	    }
	    
	    System.out.println("Total books: " + currentTotalBooks);
	}
	
	
}

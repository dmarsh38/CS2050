/**
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 
 */
public class SongLibraryManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		Song newSong = new Song("Test", "Tester", 300);
//		System.out.println(newSong);
//		Song newSong2 = new Song("Test2", "Tester", 300);
//		System.out.println(newSong2);
//		System.out.println(newSong);
		
//		PlayList testPlayList = new PlayList();
//		testPlayList.loadFromCsv("test_playlist.csv");
//		testPlayList.displayAll();

//		String prodList = "playlist.csv";
//		Scanner scanner = new Scanner(System.in);
//        PlayList newPlayList = new PlayList();
//        boolean running = true;

        final int MENU_END = 9;
		final String[] menuItems =
		{ "Load Songs from CSV", // 1
				"Display Playlist", // 2
				"Play Song by Index", // 3
				"Add Song to Up-Next Queue", // 4
				"Show Up-Next Queue", // 5
				"Play Next Song in Up-Next Queue", // 6
				"Search Songs (by ID or Artist)", // 7 (User Story 8)
				"View Playlist Sorted", // 8 (User Story 9)
				"Exit" // 9
		};
		final String menuPrompt = "Enter your choice (1–" + MENU_END + "): ";
		Scanner keyboardScanner = new Scanner(System.in);
		PlayList manager = new PlayList();
		int choice;
		do
		{
			System.out.println("\n=== Music Playlist Menu ===");
			for (int i = 0; i < menuItems.length; i++)
			{
				System.out.println((i + 1) + ". " + menuItems[i]);
			}
			choice = getValidInt(keyboardScanner, menuPrompt, 1, MENU_END);
			switch (choice)
			{
			case 1:
			{
				// User Story 1 — Load my music (UPDATED with IDs in Iteration 02)
				System.out.print("Enter CSV filename: ");
				String filename = keyboardScanner.next();
				boolean loaded = manager.loadFromCsv(filename);
				if (!loaded)
				{
					System.out.println("No songs were loaded.");
					System.out.println("Working directory: " + java.nio.file.Paths.get("").toAbsolutePath());
				} else
				{
					// Iteration 02 UPDATE (User Story 8): build HashMap for ID lookup after loading
//					manager.buildByIdMap();
				}
				break;
			}
			case 2:
			{
				// User Story 2 — See what’s in the playlist
				manager.displayAll();
				break;
			}
			case 3:
			{
				// User Story 3 — Play a specific song now (by index, as in Iteration 01)
				if (manager.size() == 0)
				{
					System.out.println("Playlist is empty.");
				} else
				{
					int index = getValidInt(keyboardScanner, "Enter index to play: ", 0, manager.size() - 1);
					Song song = manager.get(index);
					if (song == null)
					{
						System.out.println("Invalid index.");
					} else
					{
						System.out.println("Now playing: " + song);
					}
				}
				break;
			}
			case 4:
			{
				// User Story 4 — Add songs to the Up-Next linked list queue
				if (manager.size() == 0)
				{
					System.out.println("Playlist is empty.");
				} else
				{
					int addIdx = getValidInt(keyboardScanner, "Enter song number to add to Up-Next Queue: ", 0,
							manager.size() - 1);
					boolean ok = manager.enqueue(addIdx);
					if (ok)
					{
						System.out.println(manager.get(addIdx) + " added to Up-Next Queue.");
					} else
					{
						System.out.println("Invalid index. Nothing added.");
					}
				}
				break;
			}
			case 5:
			{
				// User Story 5 — See what’s coming up in Up-Next queue
				manager.showUpNext();
				break;
			}
			case 6:
			{
				// User Story 6 — Play the next song in Up-Next queue
				Song next = manager.playNext();
				if (next == null)
				{
					System.out.println("Up-Next Queue is empty (head is null).");
				} else
				{
					System.out.println("Now playing: " + next);
				}
				break;
			}
			case 7:
			{
				// User Story 8 — Unique Song IDs & Fast Lookup
				System.out.println("\nSearch Options");
				System.out.println("1. Find song by ID");
				System.out.println("2. List songs by artist");
				int searchChoice = getValidInt(keyboardScanner, "Enter search choice: ", 1, 2);
				if (searchChoice == 1)
				{
					System.out.print("Enter song ID (e.g., S1000): ");
					String id = keyboardScanner.next();
					manager.playSongById(id);
				} else
				{
					keyboardScanner.nextLine();
					System.out.print("Enter artist name: ");
					String artist = keyboardScanner.nextLine();
					manager.displaySongsByArtist(artist);
				}
				break;
			}
			case 8:
			{
				// User Story 9 — Sort the Playlist
				System.out.println("\nSort Options");
				System.out.println("1. Sort by title A–Z");
				System.out.println("2. Sort by duration longest first");
				int sortChoice = getValidInt(keyboardScanner, "Enter sort choice: ", 1, 2);
				manager.displayPlaylistSorted(sortChoice);
				break;
			}
			case 9:
			{
				System.out.println("Goodbye!");
				break;
			}
			}
		} while (choice != MENU_END);
		keyboardScanner.close();
	}
	/**
	 * Prompt for an integer in [min, max] and keep prompting until valid
	 *
	 * @param scanner source of input
	 * @param prompt  message to display each time
	 * @param min     minimum allowed value (inclusive)
	 * @param max     maximum allowed value (inclusive)
	 * @return a valid integer in the specified range
	 */
	public static int getValidInt(Scanner scanner, String prompt, int min, int max)
	{
		int value = min - 1; // start invalid for boolean flag
		boolean valid = false;
		while (!valid)
		{
			System.out.print(prompt);
			if (scanner.hasNextInt())
			{
				value = scanner.nextInt();
				if (value >= min && value <= max)
				{
					valid = true; // exit condition
				} else
				{
					System.out.println("Please enter a number between " + min + " and " + max + ".");
				}
			} else
			{
				System.out.println("Invalid input. Please enter a whole number.");
				scanner.next(); // clear if not valid int
			}
		}
		
		return value;
	}
}


/**
 * PlayList class. Manages songs with a song "Library" and a manipulable queue.
 * Songs can be added to the library only via csv input.
 */
class PlayList {
	private ArrayList<Song> songLibrary;
	private SinglyLinkedList<Song> upNextQueue;
	private Map<String, Song> songByID;
	private Map<String, Song> songByArtist;
	private static int count;
//	private Song nowPlaying;
	
	public PlayList() {
//		queue = new ArrayList<>();
//		head = null;
		songLibrary = new ArrayList<>();
		upNextQueue = new SinglyLinkedList<>();
		songByID = new HashMap<>();
		songByArtist = new HashMap<>();
	}
	
	/**
	 * @return ArrayList of Song objects
	 * Returns the songLibrary instance variable
	 */
	public ArrayList<Song> getLibrary() {
		return songLibrary;
	}
	
	/**
	 * @return SinglyLinkedList of Song objects
	 * Returns the SinglyLinkedList of queued songs
	 */
	public SinglyLinkedList<Song> getQueue() {
		return upNextQueue;
	}
	
	/**
	 * @return int of ArrayList.size()
	 * Returns the size of the songLibrary instance variable
	 */
	public int size() {
		return songLibrary.size();
	}
	
	/**
	 * @param location in the songLibrary ArrayList
	 * @return Song object from ArrayList
	 * Returns song based on its location in the array
	 */
	public Song get(int id) {
		return songLibrary.get(id);
	}
	
	/**
	 * @param songID unique String assigned to a Song
	 * Takes a song's unique ID as input and searches for it in the library.
	 * Converts library ArrayList into HashMap for dynamic searching.
	 */
	public void playSongById(String songID) {
		if (songByID.containsKey(songID)) {
			System.out.println("Now playing: " + songByID.get(songID));
		} else {
			System.out.println("ID not found: " + songID);
		}
	}
	
	/**
	 * @param artist String of the artist name
	 * Displays songs from the playlist from the selected artist
	 */
	public void displaySongsByArtist(String artist) {
		List<Song> results = new ArrayList<>();
		for (Song currentSong : songLibrary) {
//			IgnoreCase to make the String comparison case-insensitive 
			if (currentSong.getArtist().equalsIgnoreCase(artist)) {
				results.add(currentSong);
			}
		}
		if (results.isEmpty()) {
			System.out.println("No songs by " + artist + " found in playlist.");
		} else {
			System.out.println("Songs by artist: " + artist);
			for (Song result : results) {
				System.out.println(result);
			}
		}
	}
	
	/**
	 * @param sortChoice int determining the sorting method
	 * Displays the playlist sorted either by title or duration (descending).
	 * This does not affect the original playlist.
	 */
	public void displayPlaylistSorted(int sortChoice) {
		List<Song> songs = new ArrayList<>(songLibrary);
		if (sortChoice == 1) {
			songs.sort(Comparator.comparing(Song::getTitle, String.CASE_INSENSITIVE_ORDER));
		} else if (sortChoice == 2) {
			songs.sort(Comparator.comparing(Song::getDuration).reversed());
//			TODO: implement ascending duration sort
		} else {
			System.out.println("Invalid sort choice");
			return;
		}
		
		for (Song currentSong : songs) {
			System.out.println(currentSong);
		}
	}
	
    /**
     * @param scanner 
     * @param print
     * @return
     * Plays a song by index. (this is now redundant)
     */
    public Song selectSong(Scanner scanner, boolean print) {
        int songChoice = 0;
        boolean validInput = false;

        while (!validInput) {
            if (scanner.hasNextInt()) {
                songChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (songChoice >= 0 && songChoice <= songLibrary.size()-1) {
                    validInput = true;
                } else {
                    System.out.println("Please enter an index between 0 and " + (songLibrary.size()-1));
                }
            } else {
                System.out.println("Invalid input! Please enter an index between 0 and " + (songLibrary.size()-1));
                scanner.next(); // Clear invalid input
            }
        }
//        	result if playing song by index
//        	nowPlaying = songLibrary.get(songChoice);
    	System.out.println("Now playing: " + songLibrary.get(songChoice));
    	return null;
    }
	
	/**
	 * Displays all objects in the songLibrary playlist
	 */
	public void displayAll() {
		if (songLibrary.isEmpty()) {
			System.out.println("Playlist is empty.");
		} else {
			for (int song = 0; song < songLibrary.size(); song++) {
				System.out.println("[" + song + "] " + songLibrary.get(song).toString());
			}
		}
	}
	
	/**
	 * Prints all Song objects in the upNextQueue
	 */
	public void showUpNext() {
		upNextQueue.showUpNext();
	}
	
	/**
	 * @param id ArrayList location in songLibrary to be added to upNextQueue
	 * Adds a song to the upNextQueue
	 */
	public boolean enqueue(int id) {
		upNextQueue.addLast(songLibrary.get(id));
		return true;
	}
	
	/**
	 * "Plays" the next song in the upNextQueue (if available) and pops it off the queue 
	 */
	public Song playNext() {
		return upNextQueue.removeFirst();
	}
	
	/**
	 * @param filename Name of the file to be parsed
	 * @return boolean of whether playlist loading was successful
	 * Utilizes parseSongLine to add Song objects to SongLibrary
	 */
	public boolean loadFromCsv(String filename) {
		try (Scanner fileScan = new Scanner(new File(filename))) {
			int lineNumber = 0;
			while (fileScan.hasNextLine()){
				String line = fileScan.nextLine();
				lineNumber++;
				Song parsed = parseSongLine(line, lineNumber);
				if (parsed != null) {
					boolean added = songLibrary.add(parsed);
					if (!added) {
						System.out.println("Line " + lineNumber + ": Invalid song.");
					} else {
//						loads songs into HashMaps with uniqueID and artist as keys, respectively
						songByID.put(parsed.getUniqueID(), parsed);
						songByArtist.put(parsed.getArtist(), parsed);
						count++;
					}
				}
			}
			System.out.println("Loaded " + songLibrary.size() + " songs.");
			return true;
		} catch (FileNotFoundException ex) {
			System.out.println("Could not open file: " + filename);
			return false;
		}
	}
	/**
	* Parses one CSV line into a Song or returns null if invalid. Expected:
	* title,artist,duration
	*/
	private Song parseSongLine(String line, int lineNumber) {
		if (line == null || line.isEmpty() || line.isBlank()) {
			System.out.println("Line " + lineNumber + ": empty line.");
			return null; // early return
		}
		String[] parts = line.split(",");
//		check number of fields in the line
		if (parts.length != 3) {
		      System.out.println("Line " + lineNumber + ": wrong number of fields → " + line + " → skipping line.");
		      return null; // early return
		}
//		TODO: check for duplicate items in parts ^^, warn but do not skip
//		trim whitespace from each field separately
		String title = parts[0].trim();
		String artist = parts[1].trim();
		String durationText = parts[2].trim();
//		check if any of the fields in the current line are empty (no data between commas)
		if (title.isEmpty() || artist.isEmpty() || durationText.isEmpty()) {
			System.out.println("Line " + lineNumber + ": empty field → " + line + " → skipping line.");
			return null;
		}
		int duration;
		try {
			duration = Integer.parseInt(durationText);
		} catch (NumberFormatException ex) {
		     System.out.println("Line " + lineNumber + ": invalid duration \"" + durationText + "\" → skipping line.");
		     return null; // early return
		}
		return new Song(title, artist, duration);
	}
	
}
	
class Song {
	private String title;
	private String artist;
	private int duration;
	private String uniqueID;
	private static int count;
	
	public Song(String title, String artist, int duration) {
//		add if block to ensure no null
		this.uniqueID = String.format("S1%03d", count);
		this.title = title;
		this.artist = artist;
		this.duration = duration;
		count++;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public String getUniqueID() {
		return this.uniqueID;
	}
	
	@Override
	public String toString() {
		// return formatted string of song title, artist, and duration
		int mins = this.duration / 60;
		int sec = this.duration % 60;
		return this.uniqueID + " | \"" + this.title + "\" by " + this.artist + " (" + mins + ":" + 
				String.format("%02d", sec) + ")";
	}
}

/**
 * @param <T>
 * Generic class for Singly Linked Lists. Enables quick queueing and de-queueing.
 */
class SinglyLinkedList<T> {
	private Node<T> head;
	private Node<T> tail;
	private int count;
	
	public SinglyLinkedList() {
		head = null;
		tail = null;
		count = 0;
	}
	
//	private recursive helper class for creating nodes
	private static class Node<T> {
		T data;
		Node<T> next;
		Node(T v) {
			data = v;
			next = null;
		}
	}
	
//	returns the current tail/last item in list
	public T getTail() {
		return tail.data;
	}
	
//	returns the size of the list
	public int size() {
		return count;
	}
	
//	returns true if there are no items queued
	public boolean isEmpty() {
		return count == 0;
	}
	
	/**
	 * @param data
	 * 
	 * Implements FIFO (First in first out). When an item is queued,
	 * it is added to the end of the list (tail).
	 */
	public void addLast(T data) {
	    Node<T> newNode = new Node<T>(data);
	    if (head == null) {
	    	head = tail = newNode;
	    }
	    else {
	        tail.next = newNode;
	        tail = newNode;
	    }
	    count++;
	}
	
	/**
	 * @return
	 * 
	 * Implements FIFO (first in first out) - when an item is de-queued,
	 * it is taken from the front of the queue.
	 */
	public T removeFirst() {
		if (head == null) {
			return null;
		} 
		
		T value = head.data;
		head = head.next;
		count--;
		
		if (head == null) {
			tail = null;
		}
		
		return value;
	}
	
//	TODO: implement error handling in PlayList class instead
//	Prints all items currently in the queue, then the "currently playing" item
//	which is the first in the queue (head node)
	public void showUpNext() {
		Node<T> current = head;
		
		if (current == null) {
			System.out.println("Queue is empty.");
		} else {
			int nodeIndex = 0;
			while (current != null) {
				System.out.println("[" + nodeIndex + "] " + current.data);
				nodeIndex++;
				current = current.next;
			}
			System.out.println("\nNow playing: " + head.data);
		}
	}
}

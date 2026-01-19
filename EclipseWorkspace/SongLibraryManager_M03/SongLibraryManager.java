/**
 * 
 */
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 */
public class SongLibraryManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PlayList testPlayList = new PlayList();
		testPlayList.loadFromCsv("test_playlist.csv");
		testPlayList.displayList(testPlayList.getLibrary());

//		String prodList = "playlist.csv";
		Scanner scanner = new Scanner(System.in);
        PlayList newPlayList = new PlayList();
        boolean running = true;

        while (running) {
            // Display menu options
            System.out.println("\n--- Music Playlist Menu ---");
            System.out.println("1. Load Songs from CSV");
            System.out.println("2. Diplay Playlist");
            System.out.println("3. Play a Song by Index");
            System.out.println("4. Add Song to Up-Next Queue");
            System.out.println("5. Show Up-Next Queue");
            System.out.println("6. Play Next from Queue");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
//                    	load from CSV
                    	System.out.println("Enter CSV filename: ");
                    	String csvFile = scanner.next();
                    	newPlayList.loadFromCsv(csvFile);
                    	break;

                    case 2:
                        // Display Playlist
                    	newPlayList.displayList(newPlayList.getLibrary());
                    	break;

                    case 3:
                        // Play song by index
                    	System.out.println("Enter index to play: ");
                    	newPlayList.selectSong(scanner, true);
                    	break;

                    case 4:
                        // Adding a song to the queue
                    	System.out.println("Enter song number to add to Up-Next List (queue): ");
                    	Song queueSong = newPlayList.selectSong(scanner, false);
                    	newPlayList.enqueue(queueSong);
                    	System.out.println(newPlayList.getTail() + " added to Up-Next.");
                        break;

                    case 5:
                        // Show upcoming songs in queue
                        newPlayList.peek();
                        break;
                        
                    case 6:
//                    	Play next from queue
                    	newPlayList.dequeue();
                    	break;
                    	
                    case 7:
//                    	Exit
                    	System.out.println("Song Library System closing...");
                    	running = false;
                        break;

                    default:
                        System.out.println("Invalid choice! Please enter a number between 1-7.");
                }
            } else {
                System.out.println("Invalid input! Please enter a number between 1-7.");
                scanner.next(); // Clear invalid input
            }
        }

        scanner.close();
    }

}

class PlayList {
	private ArrayList<Song> songLibrary;
	private Node head;
	private Node tail;
//	private Song nowPlaying;
	
	public PlayList() {
//		queue = new ArrayList<>();
		head = null;
		songLibrary = new ArrayList<>();
	}
//	Node helper class
	private static class Node {
		Song data;
		Node next;
		public Node(Song data) {
			this.data = data;
			this.next = null;
		}
	}
	
	public ArrayList<Song> getLibrary() {
		return this.songLibrary;
	}
	
	public Song getTail() {
		return tail.data;
	}
	
//	TODO: change enqueue + dequeue to Node methods
	public void enqueue(Song song) {
	    Node newNode = new Node(song);
	    if (head == null) {
	    	head = tail = newNode;
	    }
	    else {
	        tail.next = newNode;
	        tail = newNode;
	    }
//	    if (nowPlaying == null) {
//	    	nowPlaying = song;
//	    }
	}
	
	public void dequeue() {
//		nowPlaying = head.data;
		if (head == null) {
			System.out.println("Queue is empty.");
		} else if (head.next == null) {
//			TODO: print nowPlaying here? might be redundant; possibly reorg control structure
			head = null;
			System.out.println("Queue is empty.");
		} else {
			head = head.next;
			System.out.println("Now playing: " + head.data);
		}
	}
	
	public void peek() {
		Node current = head;
		
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
	
    // Method to play selected song
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
        if (print) {
//        	result if playing song by index
//        	nowPlaying = songLibrary.get(songChoice);
        	System.out.println("Now playing: " + songLibrary.get(songChoice));
        	return null;
        } else {
//        	result if enqueueing song
//        	feels janky using "print" var to determine what function the method serves
//        	currently
        	return songLibrary.get(songChoice);
        }
    }
	
	public void displayList(ArrayList<Song> list) {
		if (list.isEmpty()) {
			System.out.println("Playlist is empty.");
		} else {
			for (int song = 0; song < list.size(); song++) {
				System.out.println("[" + song + "] " + list.get(song).toString());
			}
		}
	}
	
	public void loadFromCsv(String filename) {
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
					}
				}
			}
			System.out.println("Loaded " + songLibrary.size() + " songs.");
		} catch (FileNotFoundException ex) {
			System.out.println("Could not open file: " + filename);
		}
	}
	/**
	* Parses one CSV line into a Song or returns null if invalid. Expected:
	* title,artist,duration
	*/
	private static Song parseSongLine(String line, int lineNumber) {
		if (line == null || line.isEmpty() || line.isBlank()) {
			System.out.println("Line " + lineNumber + ": empty line.");
			return null; // early return
		}
		String[] parts = line.split(",");
		if (parts.length != 3) {
		      System.out.println("Line " + lineNumber + ": wrong number of fields → " + line + " → skipping line.");
		      return null; // early return
		}
//		TODO: check for duplicate items in parts ^^, warn but do not skip
		String title = parts[0].trim();
		String artist = parts[1].trim();
		String durationText = parts[2].trim();
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
	
	public Song(String title, String artist, int duration) {
//		add if block to ensure no null
		this.title = title;
		this.artist = artist;
		this.duration = duration;
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
	
	@Override
	public String toString() {
		// return formatted string of song title, artist, and duration
		int mins = this.duration / 60;
		int sec = this.duration % 60;
		return "\"" + this.title + "\" by " + this.artist + " (" + mins + ":" + 
				String.format("%02d", sec) + ")";
	}
}

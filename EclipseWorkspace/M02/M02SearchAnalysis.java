
/**
 * 
 */

public class M02SearchAnalysis
{

	public static void main(String[] args)
	{
		// Array sizes to test
		int[] sizes =
		{ 10, 100, 1000, 10000 };

		for (int n : sizes)
		{
			// Build a sorted array [1, 2, 3, ..., n]
			int[] data = new int[n];
			for (int i = 0; i < n; i++)
			{
				data[i] = i + 1;
			}

			System.out.println("\n=== Array size " + n + " ===");

			// Pick keys for test cases
			int firstKey = data[0]; // first element
			int middleKey = data[n / 2]; // middle element
			int lastKey = data[n - 1]; // last element
			int absentKey = n + 10; // guaranteed not present

			// Run Linear Search
			System.out.println("-- Linear Search --");
			linearSearch(data, firstKey);
			linearSearch(data, middleKey);
			linearSearch(data, lastKey);
			linearSearch(data, absentKey);

			// Run Binary Search
			System.out.println("-- Binary Search --");
			binarySearch(data, firstKey);
			binarySearch(data, middleKey);
			binarySearch(data, lastKey);
			binarySearch(data, absentKey);
		}
	}

	// ---------- Linear Search with comparison counting ----------
//	linear search checks every element in sequence
	public static int linearSearch(int[] data, int key)
	{
		int comparisons = 0;
//		loop through array starting at index 0
		for (int i = 0; i < data.length; i++)
		{
			comparisons++;
//			check if current index matches the "key"
			if (data[i] == key)
			{
//				if matching, print the result of the search
				System.out.println(
						"Linear: key " + key + " found at index " + i + " after " + comparisons + " comparisons.");
				return i;
			}
		}
//		after loop finishes, print message with failure result
		System.out.println("Linear: key " + key + " not found after " + comparisons + " comparisons.");
		return -1;
	}

	// ---------- Binary Search with comparison counting ----------
//	Binary search continually divides the array in half
	public static int binarySearch(int[] data, int key)
	{
		int low = 0;
//		last index in the array
		int high = data.length - 1;
		int comparisons = 0;

		while (low <= high)
		{
//			find middle index
			int mid = (low + high) / 2;
			comparisons++;
//			check middle index against key
			if (data[mid] == key)
			{
//				middle index is key, success
				System.out.println(
						"Binary: key " + key + " found at index " + mid + " after " + comparisons + " comparisons.");
				return mid;
//			choose which half of the remaining data to test next (if mid is less than key, search lower half)
//			otherwise, search upper half
			} else if (data[mid] < key)
			{
				comparisons++;
				low = mid + 1;
			} else
			{
				comparisons++;
				high = mid - 1;
			}
		}
		System.out.println("Binary: key " + key + " not found after " + comparisons + " comparisons.");
		return -1;
	}

}

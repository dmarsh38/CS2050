
public class SelectionSortTDD
{
	public static void main(String[] args)
	{
		
		System.out.println("Testing Selection Sort");

		int[][] testCases = { 
				{ 4, 2, 7, 1, 5 }, // Regular case
				{}, // Empty array
				{ 5 }, // Single element
				{ 1, 2, 3, 4, 5 }, // Already sorted
				{ 9, 7, 5, 3, 1 }, // Reverse sorted
				{ 4, 2, 7, 2, 5 } // Array with duplicates
		};

		for (int i = 0; i < testCases.length; i++)
		{
			System.out.println("Test Case " + (i + 1) + ": Before Sorting:");
			printArray(testCases[i]); 
			System.out.println("Smallest index: " + selectionSort);
//			selectionSort(testCases[i]);
//			System.out.println("After Sorting:");
//			printArray(testCases[i]);
//			System.out.println();
		}
	}


	public static int selectionSort(int[] array)
	{
		int smallestIndex;
		int smallest;
		for (int i = 0; i < array.length; i++)
	    {
	    	if (array[i] < smallest) 
	    	{
	    		smallest = array[i]
	    		int smallestIndex = i
	    	}
	    }
		return array[smallestIndex];
//		int swap = array[0];
//		array[0] = array[smallestIndex];
//		array[smallestIndex] = swap;
	}

	public static void printArray(int[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}


}
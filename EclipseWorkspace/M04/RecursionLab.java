/**
 * 
 */

/**
 * 
 */
public class RecursionLab {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

public static void mystery(int n) {
	if (n <= 0) {
		return;
	}
	System.out.print(n + " ");
	mystery(n - 1);
	System.out.print(n + " ");
}
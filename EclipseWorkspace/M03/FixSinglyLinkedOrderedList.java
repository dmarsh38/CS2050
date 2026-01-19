
/**
 * Lab: Fix Singly Linked Ordered List ----------------------------------- The
 * insertNode() and deleteNode() methods contain logic bugs. 1. Predict what
 * each method should do (draw before/after pictures). 2. Use the debugger or
 * print statements to trace previous/current. 3. Fix the code so the list stays
 * in sorted order after insertions and nodes are correctly deleted when found.
 *
 * Add comments above your fixes explaining what was wrong and why.
 */

public class FixSinglyLinkedOrderedList
{

	// Test the Singly Linked List
	public static void main(String[] args)
	{

		SinglyLinkedListFix list = new SinglyLinkedListFix();
		list.printList();

		// Use your unit testing to ensure it handles all cases
		list.insertNode(4);

		list.printList();

		list.printList();
		list.deleteNode(0);

		list.printList();

	}

}

class SinglyLinkedListFix
{
	NodeFix head;

	public void insertNode(int number)
	{
//		create new node, current and previous references
		NodeFix newNode = new NodeFix(number);
		NodeFix current = head;
		NodeFix previous = null;
//		iterate if current has data in it and that data is less than parameter 
		while (current != null && current.data < number)
		{
//			assign previous node with the current node
			previous = current;
//			move to next node
			current = current.next;
		}
//		if no previous node exists
		if (previous == null)
		{
			newNode.next = head;
			head = newNode;
		} else
		{
			previous.next = newNode;

		}
	}

	public void deleteNode(int number)
	{
//		save previous and current
		NodeFix current = head;
		NodeFix previous = null;
//		while loop only stops when number param is unequal
		while (current.next != null && current.data != number)
		{
			previous = current;
			current = current.next;
		}

		if (previous == null)
		{
			head = current.next;
		} else
		{
			previous.next = current.next; // Bug #5: Should be previous.next = current.next
		}
	}

	public void printList()
	{
		NodeFix current = head;
		while (current != null)
		{
			System.out.print(current.data + " → ");
			current = current.next;
		}
		System.out.println("null");
	}

	private static class NodeFix
	{
		int data;
		NodeFix next;

		public NodeFix(int data)
		{
			this.data = data;
			this.next = null;
		}
	}
}

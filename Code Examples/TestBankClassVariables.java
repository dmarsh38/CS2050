
public class TestBankClassVariables
{

	public static void main(String[] args)
	{
		// Create two accounts
		BankAccount acc1 = new BankAccount("Alice", 1000.00);
		BankAccount acc2 = new BankAccount("Bob", 2000.00);

		System.out.println("Initial Interest Rate: " + BankAccount.getInterestRate());

		System.out.println(acc1.getOwner() + " balance with interest: " + acc1.getBalanceWithInterest());
		System.out.println(acc2.getOwner() + " balance with interest: " + acc2.getBalanceWithInterest());

		// Change the interest rate for ALL accounts
		BankAccount.setInterestRate(0.07);

		System.out.println("\nAfter interest rate update:");
		System.out.println("New Interest Rate: " + BankAccount.getInterestRate());
		System.out.println(acc1.getOwner() + " balance with interest: " + acc1.getBalanceWithInterest());
		System.out.println(acc2.getOwner() + " balance with interest: " + acc2.getBalanceWithInterest());
	}

}// end test class

class BankAccount
{
	private String owner;
	private double balance;

	// static = shared by all accounts
	private static double interestRate = 0.05; // 5%

	public BankAccount(String owner, double balance)
	{
		this.owner = owner;
		this.balance = balance;
	}

	public String getOwner()
	{
		return owner;
	}

	public double getBalance()
	{
		return balance;
	}

	public double getBalanceWithInterest()
	{
		return balance + (balance * interestRate);
	}

	// static setter to change the shared policy
	public static void setInterestRate(double rate)
	{
		interestRate = rate;
	}

	public static double getInterestRate()
	{
		return interestRate;
	}
}

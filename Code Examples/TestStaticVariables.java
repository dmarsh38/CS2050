
public class TestStaticVariables
{

	public static void main(String[] args)
	{
		Student student1 = new Student("Alice");
		Student student2 = new Student("Bob");

		System.out.println(student1.getName() + " has id " + student1.getId());
		System.out.println(student2.getName() + " has id " + student2.getId());
		System.out.println("Total students = " + Student.getTotalStudents());
	}

}

class Student
{
	private String name;
	private int id;
	private static int totalStudents = 0;

	public Student(String name)
	{
		this.name = name;
		totalStudents++;
		this.id = totalStudents; // unique ID assigned sequentially
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public static int getTotalStudents()
	{
		return totalStudents;
	}
}

package application.utils;

public class Person {
	
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	
	public Person() {
		this(99, "", "", 0);
	}
	
	public Person(int id, String fName, String lName, int a) {
		this.id = id;
		this.firstName = fName;
		this.lastName = lName;
		this.age = a;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}


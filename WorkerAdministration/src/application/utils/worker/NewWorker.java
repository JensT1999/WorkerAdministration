package application.utils.worker;

public class NewWorker {
	
	private String firstName;
	private String lastName;
	private String birthday;

	public NewWorker(String firstName, String lastName, String birthday) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	
}

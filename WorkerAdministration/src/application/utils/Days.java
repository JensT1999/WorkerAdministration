package application.utils;

public enum Days {
	
	MONDAY("Montag", 0),
	TUESDAY("Dienstag", 1),
	WEDNESDAY("Mittwoch", 2),
	THURSDAY("Donnerstag", 3),
	FRIDAY("Freitag", 4),
	SATURDAY("Samstag", 5),
	SUNDAY("Sonntag", 6);
	
	private String name;
	private int id;
	
	private Days(String n, int i) {
		this.name = n;
		this.id = i;
	}
	
	public String toString() {
		return name;
	}
	
	public int getID() {
		return id;
	}
}

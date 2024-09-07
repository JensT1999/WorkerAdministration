package application.utils;

public enum SortType {
	
	BY_ID("ID's"),
	BY_AGE("Alter"),
	ALPHABETICALLY_BY_FIRSTNAME("Vorname'n"),
	ALPHABETICALLY_BY_LASTNAME("Nachname'n"),
	NEG_HOURS("Minusstunden"),
	POS_HOURS("Plusstunden");
	
	private String name;

	private SortType(String string) {
		this.name = string;
	}
	
	public String toString() {
		return name;
	}

}

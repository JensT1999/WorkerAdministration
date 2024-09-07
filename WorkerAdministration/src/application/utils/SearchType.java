package application.utils;

public enum SearchType {
	
	ID(0),
	FIRSTNAME(1),
	LASTNAME(2),
	AGE(3),
	NEG_HOURS(4);
	
	private int searchTypeId;
	
	private SearchType(int id) {
		this.searchTypeId = id;
	}
	
	public int getSearchTypeId() {
		return searchTypeId;
	}

}

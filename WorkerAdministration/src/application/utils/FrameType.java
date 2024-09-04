package application.utils;

public enum FrameType {
	
	MAIN_FRAME("Hauptmenü"),
	PTV_FRAME("MitarbeiterTabelle"),
	WPC_FRAME("ArbeitsplanErstellung"),
	NONE("Platzhalter");
	
	
	private String name;
	
	private FrameType(String n) {
		this.name = n;
	}
	
	public String toString() {
		return name;
	}

}

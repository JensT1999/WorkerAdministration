package application.utils.worker;

import application.utils.FileManager;

public class WorkerFile {
	
	private Worker w;
	private String dirPath;
	private String path;
	
	private int id;
	private String firstName;
	private String lastName;
	private String birthday;
	private double posH;
	private double negH;
	
	public WorkerFile(int id) {
		this.dirPath = 
				"C:\\Users\\ctrap\\Desktop\\test\\workadministration\\worker\\" + String.valueOf(id);
		this.path = this.dirPath + "\\" + String.valueOf(id) + ".yml";
		
		this.loadData();
	}
	
	public WorkerFile(Worker w) {
		this.w = w;
		this.path = this.w.getFolder().getWorkerFilePath();
		
		this.loadData();
	}

	private void loadData() {
		if(!FileManager.exists(this.path)) {
			FileManager.createFile(this.path);
			
			String[] standardFile = new String[6];
			
			standardFile[0] = "ID: " + String.valueOf(this.w.getId());
			standardFile[1] = "Vorname: " + this.w.getFirstName();
			standardFile[2] = "Nachname: " + this.w.getLastName();
			standardFile[3] = "Geburtstag: " + this.w.getBirthDay().getDate();
			standardFile[4] = "PlusStunden: 0";
			standardFile[5] = "MinusStunden: 0";
			
			FileManager.writeToFile(this.path, standardFile);
			
			this.readFile();
		} else {
			this.readFile();
		}
	}
	
	private void readFile() {
		if(FileManager.exists(this.path)) {
			String[] file = FileManager.readFileAsArray(this.path);
			
			for(int i = 0; i < file.length; i++) {
				if(file[i] != null && file[i] != "") {
					String[] split = file[i].split(": ");
					switch (split[0].toLowerCase()) {
						case "id" -> this.id = Integer.valueOf(split[1]);
						case "vorname" -> this.firstName = split[1];
						case "nachname" -> this.lastName = split[1];
						case "geburtstag" -> this.birthday = split[1];
						case "plusstunden" -> this.posH = Double.valueOf(split[1]);
						case "minusstunden" -> this.negH = Double.valueOf(split[1]);
					}
				}
			}
			
			if(this.w == null) {
				this.w = new Worker(this.id, this.lastName, this.firstName, this.birthday);
			}
		}
	}
	
	public void saveData() {
		if(FileManager.exists(this.path)) {
			FileManager.clearFile(this.path);
			
			String[] file = new String[6];
			
			file[0] = "ID: " + String.valueOf(this.id);
			file[1] = "Vorname: " + this.firstName;
			file[2] = "Nachname: " + this.lastName;
			file[3] = "Geburtstag: " + this.birthday;
			file[4] = "PlusStunden: " + String.valueOf(this.posH);
			file[5] = "MinusStunden: " + String.valueOf(this.negH);
			
			FileManager.writeToFile(this.path, file);
		}
	}
	
	public int getID() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String date) {
		this.birthday = date;
	}
	
	public double getPosHours() {
		return posH;
	}
	
	public void setPosHours(double h) {
		this.posH = h;
	}
	
	public double getNegHours() {
		return negH;
	}
	
	public void setNegHours(double h) {
		this.negH = h;
	}
}

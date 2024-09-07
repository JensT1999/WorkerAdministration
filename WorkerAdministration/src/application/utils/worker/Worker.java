package application.utils.worker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import application.utils.Utils;

public class Worker {
	
	private int id;
	private String firstName;
	private String lastName;
	private Birthday birthday;
	private double pHours;
	private double nHours;
	private WorkerFolder wF;
	private WorkerFile wFi;
//	private WorkCalculator wHw;
//	private HourCalc hc;
	
	public Worker(WorkerFile file) {
		this.wFi = file;
		this.id = this.wFi.getID();
		this.firstName = this.wFi.getFirstName();
		this.lastName = this.wFi.getLastName();
		this.birthday = new Birthday(this.wFi.getBirthday());
		this.wF = new WorkerFolder(this);
//		this.hc = new HourCalc(this.wF);
	}
	
	public Worker(int id, String fName, String lName, String bd) {
		this.id = id;
		this.firstName = fName;
		this.lastName = lName;
		this.birthday = new Birthday(bd);
		this.wF = new WorkerFolder(this);
	}
	
	public void create() {
		this.wFi = new WorkerFile(this);
//		this.hc = new HourCalc(this.wF);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return birthday.getAgeInYears();
	}
	
	public Birthday getBirthDay() {
		return birthday;
	}

	public int getId() {
		return id;
	}

	public WorkerFolder getFolder() {
		return wF;
	}
	
	public double getPosHours() {
		return this.wFi.getPosHours();
	}
	
	public double getNegHours() {
		return this.wFi.getNegHours();
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		this.wFi.setFirstName(firstName);
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
		this.wFi.setLastName(lastName);
	}
	
	public void setBirthday(String bd) {
		if(Utils.isValidDate(bd)) {
			this.birthday = new Birthday(bd);
			this.wFi.setBirthday(bd);
		}
	}
	
	public void setPosHours(double h) {
		this.wFi.setPosHours(h);
	}
	
	public void setNegHours(double h) {
		this.wFi.setNegHours(h);
	}
	
	public void saveFileData() {
		this.wFi.saveData();
	}
	
	public class Birthday {
		
		private String day;
		
		private DateTimeFormatter formatter;
		private LocalDate date;
		
		public Birthday(String birthday) {
			this.day = birthday;
			this.formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			
			this.date = LocalDate.parse(birthday, this.formatter);			
		}
		
		public String getDate() {
			return this.day;
		}
		
		public int getAgeInYears() {
			Period p = Period.between(this.date, LocalDate.now());
			return p.getYears();
		}
	}
}


package application.utils.worker;

import application.utils.Days;

public class WorkCalculator {
	
	private double alrWH;
	private double[] workedHours;
	private double minHoursPW;
	private double posHours;
	private double negHours;
	
	public WorkCalculator(double a, double[] wh, double minH, double pH, double nH) {
		this.alrWH = a;
		this.workedHours = wh;
		this.minHoursPW = minH;
		this.posHours = pH;
		this.negHours = nH;
	}
	
	public void calculatePosAndNegHours() {
		if(this.workedHours != null) {
			double nH = 0;
			double pH = 0;
			for(int i = 0; i < this.workedHours.length; i++) {
				if(this.workedHours[i] < this.minHoursPW) {
					double o = this.minHoursPW - this.workedHours[i];
					nH += o;
				} else if(this.workedHours[i] > this.minHoursPW) {
					double o = this.workedHours[i] - this.minHoursPW;
					pH += o;
				}
			}
			
			if(nH > pH) {
				this.negHours += (nH - pH);
			} else if(pH > nH){
				this.posHours += (pH - nH);
			}
		}
	}
	
	public void addWHToAlrWH() {
		if(this.workedHours != null) {
			for(int i = 0; i < this.workedHours.length; i++) {
				if(this.workedHours[i] != 0) {
					this.alrWH += this.workedHours[i];
				}
			}
		}
	}
	
	public void setAlreadyWorkedHours(double set) {
		this.alrWH = set;
	}
	
	public double getAlreadyWorkedHours() {
		return alrWH;
	}
	
	public void addWorkedHours(Days day, double add) {
		this.workedHours[day.getID()] += add;
	}
	
	public void subWorkedHours(Days day, double sub) {
		this.workedHours[day.getID()] -= sub;
	}
	
	public void setWorkedHoursOfDay(Days day, double set) {
		this.workedHours[day.getID()] = set;
	}
	
	public void resetWH() {
		this.workedHours = new double[7];
	}
	
	public void resetWHOfDay(Days day) {
		this.workedHours[day.getID()] = 0;
	}
	
	public double getWHOfDay(Days day) {
		return workedHours[day.getID()];
	}
	
	public double[] getWH() {
		return workedHours;
	}
	
	public void setMinH(double set) {
		this.minHoursPW = set;
	}
	
	public double getMinH() {
		return minHoursPW;
	}
	
	public void setNegHours(double set) {
		this.negHours = set;
	}
	
	public double getNegHours() {
		return negHours;
	}
	
	public void setPosHours(double set) {
		this.posHours = set;
	}
	
	public double getPosHours() {
		return posHours;
	}
}

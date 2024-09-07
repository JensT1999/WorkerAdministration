package application.utils.worker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import application.utils.Days;
import application.utils.FileManager;
import application.utils.test.TestMonth;

public class HourCalc {
	
	private LinkedHashMap<String, double[]> weeks;
	private LinkedHashMap<String, double[]> weekends;
	
	private String path;
	
	private Days tD = Days.MONDAY;
	private TestMonth tM = TestMonth.MARCH;
	
	public HourCalc(WorkerFolder wf) {
		this.weeks = new LinkedHashMap<String, double[]>();
		this.weekends = new LinkedHashMap<String, double[]>();
		
		this.path = wf.getHourPath();
		
		this.loadData();
	}
	
	public void loadData() {
		if(!FileManager.exists(path)) {
			FileManager.createFile(path);
		} else {
			ArrayList<String> list = FileManager.readFileAsList(path);
						
			list.forEach(e -> {
				if(e != null && e != "") {
					String text = e.toLowerCase();
					if(text.contains("jry_week_")) {
						this.fillMonth(text);	
					} else if(text.contains("feb_week_")) {
						this.fillMonth(text);
					} else if(text.contains("mar_week_")) {
						this.fillMonth(text);
					} else if(text.contains("apr_week_")) {
						this.fillMonth(text);
					} else if(text.contains("may_week_")) {
						this.fillMonth(text);
					} else if(text.contains("jun_week_")) {
						this.fillMonth(text);
					} else if(text.contains("jul_week_")) {
						this.fillMonth(text);
					} else if(text.contains("aug_week_")) {
						this.fillMonth(text);
					} else if(text.contains("sep_week_")) {
						this.fillMonth(text);
					} else if(text.contains("oct_week_")) {
						this.fillMonth(text);
					} else if(text.contains("nov_week_")) {
						this.fillMonth(text);
					} else if(text.contains("dec_week_")) {
						this.fillMonth(text);
					}
				}
			});			
		}
	}
	
	public void fillMonth(String s) {
		if(s != null && s != "" && this.weeks != null) {
			String[] split = s.split(" : ");
			double[] workedHours = new double[5];
			double[] workedHoursOnWe = new double[2];
			
			String[] split1 = split[1].split("/");

			if(split1[0].contains("[") && split1[0].contains("]")) {
				String weekHours = split1[0].replace("[", "").replace("]", "");
				
				String[] split2 = weekHours.split(",");
				
				for(int i = 0; i < split2.length; i++) {
					if(split2[i] != null) {
						workedHours[i] = Double.valueOf(split2[i]);
					}
				}
			}
			
			if(split1[1].contains("[") && split1[1].contains("]")) {
				String weHours = split1[1].replace("[", "").replace("]", "");
				String[] split2 = weHours.split(",");
				for(int i = 0; i < split2.length; i++) {
					if(split2[i] != null) {
						workedHoursOnWe[i] = Double.valueOf(split2[i]);
					}
				}
			}
			
			this.weeks.put(split[0], workedHours);
			this.weekends.put(split[0], workedHoursOnWe);
		}
	}
	
	public void saveData() {
		if(FileManager.exists(this.path)) {
			FileManager.clearFile(path);
			ArrayList<String> list = new ArrayList<String>();
									
			for(Map.Entry<String, double[]> entry : this.weeks.entrySet()) {
				if(entry != null && entry.getKey() != null && entry.getKey() != "" &&
						entry.getValue() != null) {
					String weekString = entry.getKey().toUpperCase();
					String weekHours = "[" + this.doubleArrayToString(entry.getValue()) +
							"]";
					double[] weHours = this.weekends.get(entry.getKey());
					
					String weString = "[" + this.doubleArrayToString(weHours) + "]";
					
					String finishedString = weekString + " : " + weekHours + "/" + weString;
					
					list.add(finishedString);
				}
			}
			
			FileManager.writeToFile(this.path, list);
		}
	}
	
	public String getLatestWeek() {
		if(this.weeks != null) {
			int o = 1;
			for(Map.Entry<String, double[]> entry : this.weeks.entrySet()) {
				if(o == this.weeks.size()) {
					return entry.getKey();
				} else if(o < this.weeks.size()) {
					o++;
				}
			}
		}	
		return null;
	}
	
	public void addNewData(double wH) {
		if(this.weeks != null) {
			if(this.getLatestWeek() != null && this.weeks.size() > 0) {
				String lastWeek = this.getLatestWeek();
				String[] split = lastWeek.split("_");
				
				switch (split[0].toLowerCase()) {
					case "jry" -> this.setData(TestMonth.JANUARY, lastWeek, wH);
					case "feb" -> this.setData(TestMonth.FEBRUARY, lastWeek, wH);
					case "mar" -> this.setData(TestMonth.MARCH, lastWeek, wH);
					case "apr" -> this.setData(TestMonth.APRIL, lastWeek, wH);
					case "may" -> this.setData(TestMonth.MAY, lastWeek, wH);
					case "jun" -> this.setData(TestMonth.JUNE, lastWeek, wH);
					case "jul" -> this.setData(TestMonth.JULY, lastWeek, wH);
					case "aug" -> this.setData(TestMonth.AUGUST, lastWeek, wH);
					case "sep" -> this.setData(TestMonth.SEPTEMBER, lastWeek, wH);
					case "oct" -> this.setData(TestMonth.OCTOBER, lastWeek, wH);
					case "nov" -> this.setData(TestMonth.NOVEMBER, lastWeek, wH);
					case "dec" -> this.setData(TestMonth.DEZEMBER, lastWeek, wH);
				}
			} else {
				this.printFirstWeek(tM, wH);
			}
		}
	}
	
	public void setData(TestMonth m, String latestWeek, double wH) {
		if(this.weeks != null && wH != 0 && this.weeks.size() > 0 && latestWeek != null) {
			String[] s = latestWeek.split("_");
//			Month tM = LocalDate.now().getMonth();
//			DayOfWeek tD = LocalDate.now().getDayOfWeek();
			
			if(m.equals(tM)) {
				if(tD.equals(Days.SATURDAY) || tD.equals(Days.SUNDAY)) {
					System.out.println("test");
					if(this.weekends.get(latestWeek) != null) {
						double[] weHours = this.weekends.get(latestWeek);
						weHours[tD.getID()] = wH;
						this.weekends.put(latestWeek, weHours);
					} else {
						double[] weHours = new double[2];
						weHours[tD.getID()] = wH;
						this.weekends.put(latestWeek, weHours);
					}
				} else {					
					if(tD.equals(Days.MONDAY)) {
						double[] weekHours = new double[5];
						double[] weHours = new double[2];
						String newWeek = s[0] + "_" + s[1] + "_" + (Integer.valueOf(s[2]) + 1);
						weekHours[tD.getID()] = wH;
						this.weeks.put(newWeek, weekHours);
						this.weekends.put(newWeek, weHours);
					} else {
						double[] weekHours = this.weeks.get(latestWeek);
						double[] weHours = this.weekends.get(latestWeek);
						weekHours[tD.getID()] = wH;
						this.weeks.put(latestWeek, weekHours);
						this.weekends.put(latestWeek, weHours);
					}
				}
			} else if(tM.equals(this.getNextMonth(m))) {
				if(!this.weeks.containsKey(this.getWeekString(tM, 1)) &&
						!this.weekends.containsKey(this.getWeekString(tM, 1))) {
					double[] weekHours = new double[5];
					double[] weHours = new double[2];
					String newWeek = this.getWeekString(tM, 1);
					if(!tD.equals(Days.SATURDAY) || !tD.equals(Days.SUNDAY)) {
						weekHours[tD.getID()] = wH;
					} else {
						weHours[tD.getID()] = wH;
					}
					this.weeks.put(newWeek, weekHours);
					this.weekends.put(newWeek, weHours);
				} else {
					double[] weekHours = this.weeks.get(latestWeek);
					double[] weHours = this.weekends.get(latestWeek);
					if(!tD.equals(Days.SATURDAY) || !tD.equals(Days.SUNDAY)) {
						weekHours[tD.getID()] = wH;
					} else {
						weHours[tD.getID()] = wH;
					}
					this.weeks.put(latestWeek, weekHours);
					this.weekends.put(latestWeek, weHours);
				}
			}
		}
	}
	
	public void printFirstWeek(TestMonth m, double wH) {
		if(this.weeks != null && this.weeks.size() <= 0 &&
				this.weekends != null && this.weekends.size() <= 0) {
//			Month tM = LocalDate.now().getMonth();
//			DayOfWeek tD = LocalDate.now().getDayOfWeek();
			
			String firstWeek = this.getWeekString(m, 1);
			double[] weekHours = new double[5];
			double[] weHours = new double[2];
			
			if(!tD.equals(Days.SATURDAY) || !tD.equals(Days.SUNDAY)) {
				weekHours[tD.getID()] = wH;
			} else {
				weHours[tD.getID()] = wH;
			}
			
			this.weeks.put(firstWeek, weekHours);
			this.weekends.put(firstWeek, weHours);
		}
	}
	
	public void setDataForDayInWeek(TestMonth m, int week, Days d, double wH) {
		if(m != null && week != 0 && wH != 0) {
			if(this.weeks != null && this.weeks.size() > 0 &&
					this.weekends != null && this.weekends.size() > 0) {
				if(this.weeks.containsKey(this.getWeekString(m, week).toLowerCase()) &&
						this.weekends.containsKey(this.getWeekString(m, week).toLowerCase())) {
					String weekString = this.getWeekString(m, week).toLowerCase();
					
					if(this.weeks.get(weekString) != null && this.weekends.get(weekString) != null) {
						if(d.equals(Days.SATURDAY) || d.equals(Days.SUNDAY)) {
							double[] weHours = this.weekends.get(weekString);
							weHours[d.getID()] = wH;
							this.weekends.put(weekString, weHours);
						} else {
							double[] weekHours = this.weeks.get(weekString);
							weekHours[d.getID()] = wH;
							this.weeks.put(weekString, weekHours);
						}
					}
				}
			}
		}
	}
	
	public double calculateHoursForMonth(TestMonth m) {
		double hours = switch (m) {
			case TestMonth.JANUARY -> this.getMonthHours("jry");
			case TestMonth.FEBRUARY -> this.getMonthHours("feb");
			case TestMonth.MARCH -> this.getMonthHours("mar");
			case TestMonth.APRIL -> this.getMonthHours("apr");
			case TestMonth.MAY -> this.getMonthHours("may");
			case TestMonth.JUNE -> this.getMonthHours("jun");
			case TestMonth.JULY -> this.getMonthHours("jul");
			case TestMonth.AUGUST -> this.getMonthHours("aug");
			case TestMonth.SEPTEMBER -> this.getMonthHours("sep");
			case TestMonth.OCTOBER -> this.getMonthHours("oct");
			case TestMonth.NOVEMBER -> this.getMonthHours("nov");
			case TestMonth.DEZEMBER -> this.getMonthHours("dec");
		};
		return hours;
	}
	
	public double getMonthHours(String month) {
		double completeHours = 0;
		
		if(this.weeks != null && this.weeks.size() > 0 &&
				this.weekends != null && this.weekends.size() > 0) {
			
			for(Map.Entry<String, double[]> entry : this.weeks.entrySet()) {
				if(entry.getKey() != null && entry.getKey() != "" &&
						entry.getValue() != null) {
					if(entry.getKey().contains(month + "_week_")) {
						double[] weekHours = entry.getValue();
						double[] weHours = this.weekends.get(entry.getKey());
						
						for(int i = 0; i < weekHours.length; i++) {
							if(weekHours[i] != 0) {
								completeHours += weekHours[i];
							}
						}
						
						for(int i = 0; i < weHours.length; i++) {
							if(weHours[i] != 0) {
								completeHours += weHours[i];
							}
						}
					}
				}
			}
		}
		
		return completeHours;
	}
	
	public double calculateHoursOfYear() {
		double completeHours = 0;
		if(this.weeks != null && this.weeks.size() > 0 &&
				this.weekends != null && this.weekends.size() > 0) {
			
			for(Map.Entry<String, double[]> entry : this.weeks.entrySet()) {
				if(entry.getKey() != null && entry.getKey() != "" &&
						entry.getValue() != null) {
					double[] weekHours = entry.getValue();
					double[] weHours = this.weekends.get(entry.getKey());
					
					for(int i = 0; i < weekHours.length; i++) {
						if(weekHours[i] != 0) {
							completeHours += weekHours[i];
						}
					}
					
					for(int i = 0; i < weHours.length; i++) {
						if(weHours[i] != 0) {
							completeHours += weHours[i];
						}
					}
				}
			}
		}
		return completeHours;
	}
	
	public TestMonth getNextMonth(TestMonth m) {
		TestMonth tM = switch (m) {
			case TestMonth.JANUARY -> TestMonth.FEBRUARY;
			case TestMonth.FEBRUARY -> TestMonth.MARCH;
			case TestMonth.MARCH -> TestMonth.APRIL;
			case TestMonth.APRIL -> TestMonth.MAY;
			case TestMonth.MAY -> TestMonth.JUNE;
			case TestMonth.JUNE -> TestMonth.JULY;
			case TestMonth.JULY -> TestMonth.AUGUST;
			case TestMonth.AUGUST -> TestMonth.SEPTEMBER;
			case TestMonth.SEPTEMBER -> TestMonth.OCTOBER;
			case TestMonth.OCTOBER -> TestMonth.NOVEMBER;
			case TestMonth.NOVEMBER -> TestMonth.DEZEMBER;
			case TestMonth.DEZEMBER -> TestMonth.JANUARY;
		};
		return tM;
	}
	
	public double getHoursOfWeek(TestMonth m, int week) {
		double completeHours = 0;
		if(m != null && week != 0) {
			if(this.weeks != null && this.weeks.size() > 0 && 
					this.weekends != null && this.weekends.size() > 0) {
				String weekString = this.getWeekString(m, week).toLowerCase();
				
				if(this.weeks.containsKey(weekString) && 
						this.weekends.containsKey(weekString)) {
					double[] weekHours = this.weeks.get(weekString);
					double[] weHours = this.weekends.get(weekString);
					
					for(int i = 0; i < weekHours.length; i++) {
						if(weekHours[i] != 0) {
							completeHours += weekHours[i];
						}
					}
					
					for(int i = 0; i < weHours.length; i++) {
						if(weHours[i] != 0) {
							completeHours += weHours[i];
						}
					}
				}
			}
		}
		
		return completeHours;
	}
	
	public double getHoursOfDayInWeek(TestMonth m, int week, Days d) {
		if(m != null && week != 0 && d != null) {
			if(this.weeks != null && this.weeks.size() > 0 &&
					this.weekends != null && this.weekends.size() > 0) {
				String weekString = this.getWeekString(tM, week).toLowerCase();
				if(d.equals(Days.SATURDAY) || d.equals(Days.SUNDAY)) {
					return this.weekends.get(weekString)[d.getID()];
				} else {
					return this.weeks.get(weekString)[d.getID()];
				}
			}
		}
		return 0;
	}
	
	public double[] getHoursAsArray(TestMonth m, int week, CalcType type) {
		double[] hours = switch (type) {
			case CalcType.WEEK -> this.weeks.get(this.getWeekString(m, week).toLowerCase());
			case CalcType.WEEKEND -> this.weekends.get(this.getWeekString(m, week).toLowerCase());
		};
		
		return hours;
	}
	
	public String getWeekString(TestMonth tM, int weekNumber) {		
		String str = switch (tM) {
			case TestMonth.JANUARY -> "JRY_WEEK_" + weekNumber;
			case TestMonth.FEBRUARY -> "FEB_WEEK_" + weekNumber;
			case TestMonth.MARCH -> "MAR_WEEK_" + weekNumber;
			case TestMonth.APRIL -> "APR_WEEK_" + weekNumber;
			case TestMonth.MAY -> "MAY_WEEK_" + weekNumber;
			case TestMonth.JUNE -> "JUN_WEEK_" + weekNumber;
			case TestMonth.JULY -> "JUL_WEEK_" + weekNumber;
			case TestMonth.AUGUST -> "AUG_WEEK_" + weekNumber;
			case TestMonth.SEPTEMBER -> "SEP_WEEK_" + weekNumber;
			case TestMonth.OCTOBER -> "OCT_WEEK_" + weekNumber;
			case TestMonth.NOVEMBER -> "NOV_WEEK_" + weekNumber;
			case TestMonth.DEZEMBER -> "DEC_WEEK_" + weekNumber;
		};
		return str;
	}
	
	public int getIDOfTodaysDay() {
		DayOfWeek d = LocalDate.now().getDayOfWeek();		
		int id = switch (d) {
			case DayOfWeek.MONDAY -> Days.MONDAY.getID();
			case DayOfWeek.TUESDAY -> Days.TUESDAY.getID();
			case DayOfWeek.WEDNESDAY -> Days.WEDNESDAY.getID();
			case DayOfWeek.THURSDAY -> Days.THURSDAY.getID();
			case DayOfWeek.FRIDAY -> Days.FRIDAY.getID();
			case DayOfWeek.SATURDAY -> Days.SATURDAY.getID();
			case DayOfWeek.SUNDAY ->  Days.SUNDAY.getID();
		};
		
		return id;
	}
	
	public String doubleArrayToString(double[] input) {
		String str = "";
		if(input != null) {
			for(int i = 0; i < input.length; i++) {
				if(i + 1 < input.length) {
					str += String.valueOf(input[i]) + ",";
				} else {
					str += String.valueOf(input[i]);
				}
			}
		}
		return str;
	}
	
	enum CalcType {
		WEEK,
		WEEKEND;
	}

}

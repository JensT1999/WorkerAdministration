package application.utils.worker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import application.utils.Days;
import application.utils.FileManager;
import application.utils.MapStringConverter;
import application.utils.test.TestMonth;

public class HourCalc {
	
	private Worker worker;
		
	private Map<String, String[]> dateOfWeeks;
	private Map<String, double[]> weeks;
	private Map<String, String[]> dateOfWeekends;
	private Map<String, double[]> weekends;
	
	private MapStringConverter msc;
	
	private String path;
	
	private Days tD = Days.TUESDAY;
	private TestMonth tM = TestMonth.MARCH;
	
	public HourCalc(Worker w) {
		this.worker = w;
		
		this.dateOfWeeks = Collections.synchronizedMap(new LinkedHashMap<String, String[]>());
		this.weeks = Collections.synchronizedMap(new LinkedHashMap<String, double[]>());
		this.dateOfWeekends = Collections.synchronizedMap(new LinkedHashMap<String, String[]>());
		this.weekends = Collections.synchronizedMap(new LinkedHashMap<String, double[]>());
		
		this.msc = new MapStringConverter();
		
		this.path = this.worker.getFolder().getHourPath();
		
		this.loadData();
	}
	
	public void saveData() {
		if(FileManager.exists(this.path)) {
			FileManager.clearFile(path);
			ArrayList<String> dateData = this.msc.convertMapWithString(this.dateOfWeeks,
					this.dateOfWeekends);
			ArrayList<String> hourData = this.msc.convertMapWithDouble(this.weeks,
					this.weekends);
			ArrayList<String> list = (ArrayList<String>) 
					this.msc.combineLists(hourData, dateData);
			
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
	
	public double calculateHoursOfWeek(TestMonth m, int week) {
		if(m != null && week != 0) {
			String weekString = this.getWeekString(m, week);
			return this.calculateHoursOfWeek(weekString);
		}
		return 0;
	}
	
	public double calculateHoursOfDayInWeek(TestMonth m, int week, Days d) {
		if(m != null && week != 0 && d != null) {
			if(this.weeks != null && this.weeks.size() > 0 &&
					this.weekends != null && this.weekends.size() > 0) {
				String weekString = this.getWeekString(m, week).toLowerCase();
				if(d.equals(Days.SATURDAY) || d.equals(Days.SUNDAY)) {
					return this.weekends.get(weekString)[d.getID()];
				} else {
					return this.weeks.get(weekString)[d.getID()];
				}
			}
		}
		return 0;
	}
	
	public double[] getHoursOfLastThreeWeeks() {
		if(this.weeks != null && this.weekends != null && this.dateOfWeeks != null &&
				this.dateOfWeekends != null) {
			if(this.weeks.size() > 0 && this.weekends.size() > 0 && this.dateOfWeeks.size() > 0 &&
					this.dateOfWeekends.size() > 0) {
				double[] d = new double[3];
				int i = 1;
				for(Map.Entry<String, double[]> entry : this.weeks.entrySet()) {
					if(entry.getKey() != "" && entry.getValue() != null) {
						if(i == this.weeks.size() - 2) {
							String weekString = entry.getKey();
							d[0] = this.calculateHoursOfWeek(weekString);
						} else if(i == this.weeks.size() - 1){
							String weekString = entry.getKey();
							d[1] = this.calculateHoursOfWeek(weekString);
						} else if(i == this.weeks.size()) {
							String weekString = entry.getKey();
							d[2] = this.calculateHoursOfWeek(weekString);
						}
						i++;
					}					
				}
				return d;
			}
		}
		
		return null;
	}
	
	public double getHoursByDate(String date) {
		if(date != null && date != "") {
			if(this.weeks != null && this.weekends != null
					&& this.dateOfWeeks != null && this.dateOfWeekends != null) {
				boolean b = false;
				for(Map.Entry<String, String[]> entry : this.dateOfWeeks.entrySet()) {
					if(entry.getKey() != "" && entry.getValue() != null) {
						String[] dates = entry.getValue();
						for(int i = 0; i < dates.length; i++) {
							if(dates[i].equalsIgnoreCase(date) && 
									!dates[i].equalsIgnoreCase("x")) {
								String week = entry.getKey();
								if(this.weeks.get(week) != null) {
									double[] weekHours = this.weeks.get(week);
									b = true;
									return weekHours[i];
								}
							}
						}
					}
				}
				if(!b) {
					for(Map.Entry<String, String[]> entry : this.dateOfWeekends.entrySet()) {
						if(entry.getKey() != "" && entry.getValue() != null) {
							String[] dates = entry.getValue();
							for(int i = 0; i < dates.length; i++) {
								if(dates[i].equalsIgnoreCase(date) &&
										!dates[i].equalsIgnoreCase("x")) {
									String week = entry.getKey();
									if(this.weekends.get(week) != null) {
										double[] weHours = this.weekends.get(week);
										return weHours[i];
									}
								}
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	public String[] getDatesOfLastThreeWeeks() {
		if(this.weeks != null && this.weekends != null && this.dateOfWeeks != null &&
				this.dateOfWeekends != null) {
			if(this.weeks.size() > 0 && this.weekends.size() > 0 && this.dateOfWeeks.size() > 0 &&
					this.dateOfWeekends.size() > 0) {
				String[] str = new String[3];
				int i = 1;
				for(Map.Entry<String, String[]> entry : this.dateOfWeeks.entrySet()) {
					if(entry.getKey() != "" && entry.getValue() != null) {
						if(i == this.dateOfWeeks.size() - 2) {
							String weekString = entry.getKey();
							String[] weekDates = entry.getValue();
							String[] weDates = this.dateOfWeekends.get(weekString);
							boolean sD = false;
							String dateRange = "";
							
							for(int i1 = 0; i1 < weekDates.length; i1++) {
								if(!weekDates[i1].equalsIgnoreCase("x") && weekDates[i1] != "") {
									if(!sD) {
										String startDate = weekDates[i1];
										dateRange = startDate + "-";
										sD = true;
									} else {
										if((i1 + 1) <= weekDates.length &&
												weekDates[i1 + 1].equalsIgnoreCase("x")) {
											String endDate = weekDates[i1];
											dateRange = dateRange + endDate;
										}
									}
								} else {
									break;
								}
							}
							
							str[0] = dateRange;
						} else if(i == this.dateOfWeeks.size() - 1) {
							
						} else if(i == this.dateOfWeeks.size()) {
							
						}
						i++;
					}
				}
			}
		}
		return null;
	}
	
	public double[] getHoursAsArray(TestMonth m, int week, CalcType type) {
		double[] hours = switch (type) {
			case CalcType.WEEK -> this.weeks.get(this.getWeekString(m, week).toLowerCase());
			case CalcType.WEEKEND -> this.weekends.get(this.getWeekString(m, week).toLowerCase());
		};
		
		return hours;
	}
	
	private void loadData() {
		if(!FileManager.exists(path)) {
			FileManager.createFile(path);
		} else {
			ArrayList<String> list = FileManager.readFileAsList(path);
						
			list.forEach(e -> {
				if(e != null && e != "") {
					String text = e.toLowerCase();
					if(text.contains("-")) {
						this.fillMonthWithDates(text);
					} else if(text.contains("_")) {
						this.fillMonthWithHours(text);
					}
				}
			});			
		}
	}
	
	private void fillMonthWithDates(String s) {
		if(s != null && s != "" && this.dateOfWeeks != null &&
				this.dateOfWeekends != null) {
			String[] split = s.split(" : ");
			String[] weekDates = new String[5];
			String[] weDates = new String[2];
			
			String[] split1 = split[1].split("/");
			
			if(split1[0].contains("[") && split1[0].contains("]")) {
				String weekDa = split1[0].replace("[", "").replace("]", "");
				
				String[] split2 = weekDa.split(",");
				
				for(int i = 0; i < split2.length; i++) {
					if(split2[i] != String.valueOf(0) && split2[i] != null) {
						weekDates[i] = split2[i];
					}
				}
			}
			
			if(split1[1].contains("[") && split1[1].contains("]")) {
				String weDa = split1[1].replace("[", "").replace("]", "");
				
				String[] split2 = weDa.split(",");
				
				for(int i = 0; i < split2.length; i++) {
					if(split2[i] != String.valueOf(0) && split2[i] != null) {
						weDates[i] = split2[i];
					}
				}
			}
			
			this.dateOfWeeks.put(split[0].replace("-", "_"), weekDates);
			this.dateOfWeekends.put(split[0].replace("-", "_"), weDates);
		}
	}
	
	private void fillMonthWithHours(String s) {
		if(s != null && s != "" && this.weeks != null &&
				this.weekends != null) {
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
	
	private void setData(TestMonth m, String latestWeek, double wH) {
		if(this.weeks != null && wH != 0 && latestWeek != null && this.weekends != null 
				&& this.dateOfWeeks != null && this.dateOfWeekends != null) {
			String[] s = latestWeek.split("_");
			LocalDate date = LocalDate.now();
//			Month tM = LocalDate.now().getMonth();
//			DayOfWeek tD = LocalDate.now().getDayOfWeek();
			
			if(m.equals(tM)) {
				if(tD.equals(Days.SATURDAY) || tD.equals(Days.SUNDAY)) {
					if(this.weekends.get(latestWeek) != null &&
							this.dateOfWeekends.get(latestWeek) != null) {
						String[] weDates = this.dateOfWeekends.get(latestWeek);
						double[] weHours = this.weekends.get(latestWeek);
						weDates[tD.getID()] = String.valueOf(date.getDayOfMonth() + 
								"." + date.getMonthValue());
						weHours[tD.getID()] = wH;
						this.dateOfWeekends.put(latestWeek, weDates);
						this.weekends.put(latestWeek, weHours);
					} else {
						String[] weDates = { "x", "x" };
						double[] weHours = new double[2];
						weDates[tD.getID()] = String.valueOf(date.getDayOfMonth() + 
								"." + date.getMonthValue());
						weHours[tD.getID()] = wH;
						this.dateOfWeekends.put(latestWeek, weDates);
						this.weekends.put(latestWeek, weHours);
					}
				} else {					
					if(tD.equals(Days.MONDAY)) {
						String[] weekDates = this.createWeekWithDates(tD);
						double[] weekHours = new double[5];
						String[] weDates = { "x", "x" };
						double[] weHours = new double[2];
						String newWeek = s[0] + "_" + s[1] + "_" + (Integer.valueOf(s[2]) + 1);
//						weekDates[tD.getID()] = String.valueOf(date.getDayOfMonth() +
//								"." + date.getMonthValue());
						weekHours[tD.getID()] = wH;
						this.dateOfWeeks.put(newWeek, weekDates);
						this.weeks.put(newWeek, weekHours);
						this.dateOfWeekends.put(newWeek, weDates);
						this.weekends.put(newWeek, weHours);
					} else {
						String[] weekDates = this.dateOfWeeks.get(latestWeek);
						double[] weekHours = this.weeks.get(latestWeek);
//						weekDates[tD.getID()] = String.valueOf(date.getDayOfMonth() +
//								"." + date.getMonthValue());
						weekHours[tD.getID()] = wH;
						this.dateOfWeeks.put(latestWeek, weekDates);
						this.weeks.put(latestWeek, weekHours);
					}
				}
				
			} else if(tM.equals(this.getNextMonth(m))) {
				if(!this.weeks.containsKey(this.getWeekString(tM, 1)) &&
						!this.weekends.containsKey(this.getWeekString(tM, 1)) &&
						!this.dateOfWeeks.containsKey(this.getWeekString(tM, 1)) &&
						!this.dateOfWeekends.containsKey(this.getWeekString(tM, 1))) {
					String[] weekDates = this.createWeekWithDates(tD);
					double[] weekHours = new double[5];
					String[] weDates = { "x", "x" };
					double[] weHours = new double[2];
					String newWeek = this.getWeekString(tM, 1);
					if(!tD.equals(Days.SATURDAY) || !tD.equals(Days.SUNDAY)) {
//						weekDates[tD.getID()] = String.valueOf(date.getDayOfMonth() +
//								"." + date.getMonthValue());
						weekHours[tD.getID()] = wH;
					} else {
						weDates[tD.getID()] = String.valueOf(date.getDayOfMonth() +
								"." + date.getMonthValue());
						weHours[tD.getID()] = wH;
					}
					this.dateOfWeeks.put(newWeek, weekDates);
					this.weeks.put(newWeek, weekHours);
					this.dateOfWeekends.put(newWeek, weDates);
					this.weekends.put(newWeek, weHours);
				} else {
					String[] weekDates = this.dateOfWeeks.get(latestWeek);
					double[] weekHours = this.weeks.get(latestWeek);
					String[] weDates = this.dateOfWeekends.get(latestWeek);
					double[] weHours = this.weekends.get(latestWeek);
					if(!tD.equals(Days.SATURDAY) || !tD.equals(Days.SUNDAY)) {
//						weekDates[tD.getID()] = String.valueOf(date.getDayOfMonth() +
//								"." + date.getMonthValue());
						weekHours[tD.getID()] = wH;
					} else {
						weDates[tD.getID()] = String.valueOf(date.getDayOfMonth() +
								"." + date.getMonthValue());
						weHours[tD.getID()] = wH;
					}
					this.dateOfWeeks.put(latestWeek, weekDates);
					this.weeks.put(latestWeek, weekHours);
					this.dateOfWeekends.put(latestWeek, weDates);
					this.weekends.put(latestWeek, weHours);
				}
			}
		}
	}
	
	private void printFirstWeek(TestMonth m, double wH) {
		if(this.weeks != null && this.weeks.size() <= 0 &&
				this.weekends != null && this.weekends.size() <= 0) {
			LocalDate date = LocalDate.now();
//			Month tM = LocalDate.now().getMonth();
//			DayOfWeek tD = LocalDate.now().getDayOfWeek();
			
			String firstWeek = this.getWeekString(m, 1).toLowerCase();
			String[] weekDates = { "x", "x", "x", "x", "x" };
			double[] weekHours = new double[5];
			String[] weDates = { "x", "x" };
			double[] weHours = new double[2];
			
			if(!tD.equals(Days.SATURDAY) || !tD.equals(Days.SUNDAY)) {
				weekDates[tD.getID()] = String.valueOf(date.getDayOfMonth() +
						"." + date.getMonthValue());
				weekHours[tD.getID()] = wH;
			} else {
				weDates[tD.getID()] = String.valueOf(date.getDayOfMonth() +
						"."+ date.getMonthValue());
				weHours[tD.getID()] = wH;
			}
			
			this.dateOfWeeks.put(firstWeek, weekDates);
			this.weeks.put(firstWeek, weekHours);
			this.dateOfWeekends.put(firstWeek, weDates);
			this.weekends.put(firstWeek, weHours);
		}
	}
	
	private double getMonthHours(String month) {
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
	
	private double calculateHoursOfWeek(String weekString) {
		double completeHours = 0;
		if(weekString != "") {
			if(this.weeks != null && this.weeks.size() > 0 && 
					this.weekends != null && this.weekends.size() > 0) {
				
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
	
	private String[] createWeekWithDates(Days current) {
		if(current != null) {
			LocalDate ld = LocalDate.now();
			YearMonth ym = YearMonth.now();
			String[] result = new String[5];
			int currentDay = ld.getDayOfMonth();
			int currentMonth = ld.getMonthValue();
			switch (current) {
				case Days.MONDAY -> {
					result[current.getID()] = String.valueOf(ld.getDayOfMonth() + "." + 
							ld.getMonthValue());
					for(int i = 1; i < 5; i++) {
						if((currentDay + i) < ym.atEndOfMonth().getDayOfMonth()) {
							result[current.getID() + i] = String.valueOf((currentDay + i) + "." +
									currentMonth);
						} else {
							result[current.getID() + i] = "x";
						}
					}
				}
				case Days.TUESDAY -> {
					if((currentDay - 1) > 0) {
						result[current.getID() - 1] = String.valueOf((currentDay - 1) + "." + 
								currentMonth);
					} else {
						result[current.getID() - 1] = "x";
					}
					result[current.getID()] = String.valueOf(currentDay + "." + currentMonth);
					for(int i = 1; i < 4; i++) {
						if((currentDay + i) < ym.atEndOfMonth().getDayOfMonth()) {
							result[current.getID() + i] = String.valueOf((currentDay + i) + "." +
									currentMonth);
						} else {
							result[current.getID() + i] = "x";
						}
					}
				}
				case Days.WEDNESDAY -> {
					for(int i = 1; i > 3; i++) {
						if((currentDay - i) > 0) {
							result[current.getID() - i] = String.valueOf((currentDay - i) + "." + 
									currentMonth);
						} else {
							result[current.getID() - i] = "x";
						}
					}
					result[current.getID()] = String.valueOf(currentDay + "." + currentMonth);
					
					for(int i = 1; i < 3; i++) {						
						if((currentDay + i) < ym.atEndOfMonth().getDayOfMonth()) {
							result[current.getID() + i] = String.valueOf((currentDay + i) + "." +
									currentMonth);
						} else {
							result[current.getID() + i] = "x";
						}
					}
					
				}
				case Days.THURSDAY -> {
					for(int i = 1; i < 4; i++) {
						if((currentDay - i) > 0) {
							result[current.getID() - i] = String.valueOf((currentDay - i) + "." +
									currentMonth);
						} else {
							result[current.getID() - i] = "x";
						}
					}
					result[current.getID()] = String.valueOf(currentDay + "." + currentMonth);
					
					if((currentDay + 1) < ym.atEndOfMonth().getDayOfMonth()) {
						result[current.getID() + 1] = String.valueOf((currentDay + 1) + "." +
								currentMonth);
					} else {
						result[current.getID() + 1] = "x";
					}
				}
				case Days.FRIDAY -> {
					for(int i = 1; i < 5; i++) {
						if((currentDay - i) > 0) {
							result[current.getID() - i] = String.valueOf((currentDay - i) + "." + 
									currentMonth);
						} else {
							result[current.getID() - i] = "x";
						}
					}
					result[current.getID()] = String.valueOf(currentDay + "." + currentMonth);
				}
			}
			return result;
//			case Days.SATURDAY ->
//			case Days.SUNDAY ->
		}
		return null;
	}
	
	private TestMonth getNextMonth(TestMonth m) {
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
	
	private String getWeekString(TestMonth tM, int weekNumber) {		
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
	
	private int getIDOfTodaysDay() {
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
	
	//Testing
	
	public void setTestDate(TestMonth m, Days d) {
		this.tM = m;
		this.tD = d;
	}
	
	public TestMonth getTestMonth() {
		return tM;
	}
	
	public Days getTestDays() {
		return tD;
	}
	
	enum CalcType {
		WEEK,
		WEEKEND;
	}

}

package application.utils.test;

import application.utils.Days;
import application.utils.worker.HourCalc;
import application.utils.worker.Worker;

public class HourCalcTest {
	
	private Worker w;
	private HourCalc hc;
	
	public HourCalcTest() {
		this.w = new Worker(0, "Jens", "Robert", "25.04.1999");
		this.hc = new HourCalc(this.w);
	}
	
	public void testWriting() {
		if(this.w != null && this.hc != null) {
			this.hc.setTestDate(TestMonth.JANUARY, Days.MONDAY);
			for(int i = 0; i < 20; i++) {
				this.hc.addNewData(8);
				this.changeTestDay();
			}
			
			this.changeTestMonth();
			for(int i = 0; i < 20; i++) {
				this.hc.addNewData(8);
				this.changeTestDay();
			}
			
			this.changeTestMonth();
			for(int i = 0; i < 20; i++) {
				this.hc.addNewData(8);
				this.changeTestDay();
			}
			
			this.changeTestMonth();
			for(int i = 0; i < 20; i++) {
				this.hc.addNewData(8);
				this.changeTestDay();
			}
			
			this.changeTestMonth();
			for(int i = 0; i < 20; i++) {
				this.hc.addNewData(8);
				this.changeTestDay();
			}
			
			this.changeTestMonth();
			for(int i = 0; i < 20; i++) {
				this.hc.addNewData(8);
				this.changeTestDay();
			}
			
			this.changeTestMonth();
			for(int i = 0; i < 20; i++) {
				this.hc.addNewData(8);
				this.changeTestDay();
			}
			
			this.hc.saveData();
		}
	}
	
	public void testReading() {
		if(this.w != null && this.hc != null) {
			System.out.println(this.hc.calculateHoursOfDayInWeek(TestMonth.JANUARY, 1, Days.WEDNESDAY));
			System.out.println(this.hc.calculateHoursOfWeek(TestMonth.JANUARY, 1));
			System.out.println(this.hc.calculateHoursForMonth(TestMonth.JANUARY));
			System.out.println(this.hc.calculateHoursOfYear());
		}
	}
	
	public void changeTestDay() {
		TestMonth m = this.hc.getTestMonth();
		switch (this.hc.getTestDays()) {
			case Days.MONDAY -> this.hc.setTestDate(m, Days.TUESDAY);
			case Days.TUESDAY -> this.hc.setTestDate(m, Days.WEDNESDAY);
			case Days.WEDNESDAY -> this.hc.setTestDate(m, Days.THURSDAY);
			case Days.THURSDAY -> this.hc.setTestDate(m, Days.FRIDAY);
			case Days.FRIDAY -> this.hc.setTestDate(m, Days.MONDAY);
//			case Days.SATURDAY -> this.hc.setTestDate(m, Days.SUNDAY);
//			case Days.SUNDAY -> this.hc.setTestDate(m, Days.MONDAY);
		}
	}
	
	public void changeTestMonth() {
		TestMonth m = this.hc.getTestMonth();
		Days d = this.hc.getTestDays();
		switch (m) {
			case TestMonth.JANUARY -> this.hc.setTestDate(TestMonth.FEBRUARY, d);
			case TestMonth.FEBRUARY -> this.hc.setTestDate(TestMonth.MARCH, d);
			case TestMonth.MARCH -> this.hc.setTestDate(TestMonth.APRIL, d);
			case TestMonth.APRIL -> this.hc.setTestDate(TestMonth.MAY, d);
			case TestMonth.MAY -> this.hc.setTestDate(TestMonth.JUNE, d);
			case TestMonth.JUNE -> this.hc.setTestDate(TestMonth.JULY, d);
			case TestMonth.JULY -> this.hc.setTestDate(TestMonth.AUGUST, d);
			case TestMonth.AUGUST -> this.hc.setTestDate(TestMonth.SEPTEMBER, d);
			case TestMonth.SEPTEMBER -> this.hc.setTestDate(TestMonth.OCTOBER, d);
			case TestMonth.OCTOBER -> this.hc.setTestDate(TestMonth.NOVEMBER, d);
			case TestMonth.NOVEMBER -> this.hc.setTestDate(TestMonth.DEZEMBER, d);
			case TestMonth.DEZEMBER -> this.hc.setTestDate(TestMonth.JANUARY, d);
		}
	}
}

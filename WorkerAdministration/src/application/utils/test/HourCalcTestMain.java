package application.utils.test;

public class HourCalcTestMain {
	
	private static HourCalcTest hct;
	
	public static void main(String[] args) {
		test();
	}
	
	private static void test() {
		hct = new HourCalcTest();
		
		hct.testWriting();
		hct.testReading();
	}

}

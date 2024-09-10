package application.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MapStringConverter {
	
	private ExecutorService exe;
	
	public MapStringConverter() {
		this.exe = Executors.newCachedThreadPool();
	}
	
	public ArrayList<String> convertMapWithDouble(Map<String, double[]> weeks,
			Map<String, double[]> weekends){
		try {
			Future<ArrayList<String>> future = this.exe.submit(new Callable<ArrayList<String>>() {

				@Override
				public ArrayList<String> call() throws Exception {
					ArrayList<String> list = new ArrayList<String>();
					for(Map.Entry<String, double[]> entry : weeks.entrySet()) {
						if(entry != null && entry.getKey() != null && entry.getKey() != "" &&
								entry.getValue() != null) {
							String weekString = entry.getKey().toUpperCase();
							String weekHours = "[" + Utils.doubleArrayToString(entry.getValue()) +
									"]";
							double[] weHours = weekends.get(entry.getKey());
							
							String weString = "[" + Utils.doubleArrayToString(weHours) + "]";
							
							String finishedString = weekString + " : " + weekHours + "/" + weString;
							
							list.add(finishedString);
						}
					}
					return list;
				}
			});
			
			return future.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> convertMapWithString(Map<String, String[]> dateOfWeeks, 
			Map<String, String[]> dateOfWeekends) {
		
		try {
			Future<ArrayList<String>> future = this.exe.submit(new Callable<ArrayList<String>>() {

				@Override
				public ArrayList<String> call() throws Exception {
					ArrayList<String> list = new ArrayList<String>();
					for(Map.Entry<String, String[]> entry : dateOfWeeks.entrySet()) {
						if(entry != null && entry.getKey() != null && entry.getKey() != "" &&
								entry.getValue() != null) {
							String weekString = entry.getKey().toUpperCase().replace("_", "-");
							String weekDates = "[" + Utils.stringArrayToString(entry.getValue()) + "]";
							String weDates = "[" + Utils.stringArrayToString(
									dateOfWeekends.get(entry.getKey())) + "]";
							
							String finishedString = weekString + " : " + weekDates + "/" + weDates;
							
							list.add(finishedString);
						}
					}
					return list;
				}
			});
			return future.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public synchronized List<String> combineLists(ArrayList<String> l1, ArrayList<String> l2){
		try {
			Future<ArrayList<String>> future = this.exe.submit(new Callable<ArrayList<String>>() {

				@Override
				public ArrayList<String> call() throws Exception {
					ArrayList<String> list = new ArrayList<String>();
					boolean b = true;
					int i = 0;
					int o = l1.size() * 2;
					
					while(list.size() < o) {
						if(b) {
							list.add(l2.get(i));
							b = false;
						} else {
							list.add(l1.get(i));
							i++;
							b = true;
						}
					}
					
					return list;
				}
			});
			return future.get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

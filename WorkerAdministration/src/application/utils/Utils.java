package application.utils;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.utils.worker.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class Utils {
	
	private static ExecutorService exe = Executors.newCachedThreadPool();
	
	public static ObservableList<Worker> sortPersons(SortType type, 
			ObservableList<Worker> input) {
		try {
			Future<ObservableList<Worker>> future = 
					exe.submit(new Callable<ObservableList<Worker>>() {

				@Override
				public ObservableList<Worker> call() throws Exception {
					ObservableList<Worker> list = FXCollections.observableArrayList();
					SortedList<Worker> list1 = new SortedList<Worker>(input);
					
					if(type != null && input != null && input.size() > 0) {
						switch (type) {
							case SortType.BY_ID -> {
								list1.setComparator(new Comparator<Worker>() {

									@Override
									public int compare(Worker o1, Worker o2) {
										Integer id1 = o1.getId();
										Integer id2 = o2.getId();
										return id1.compareTo(id2);
									}
								});
							}
							case SortType.BY_AGE -> {
								list1.setComparator(new Comparator<Worker>() {

									@Override
									public int compare(Worker o1, Worker o2) {
										Integer age1 = o1.getAge();
										Integer age2 = o2.getAge();
										return age1.compareTo(age2);
									}
								});
							}
							case SortType.ALPHABETICALLY_BY_FIRSTNAME -> {
								list1.setComparator(new Comparator<Worker>() {

									@Override
									public int compare(Worker o1, Worker o2) {
										String firstName1 = o1.getFirstName();
										String firstName2 = o2.getFirstName();
										return firstName1.compareTo(firstName2);
									}
								});
							}
							case SortType.ALPHABETICALLY_BY_LASTNAME -> {
								list1.setComparator(new Comparator<Worker>() {

									@Override
									public int compare(Worker o1, Worker o2) {
										String lastName1 = o1.getLastName();
										String lastName2 = o2.getLastName();
										return lastName1.compareTo(lastName2);
									}
								});
							}
							case SortType.POS_HOURS -> {
								list1.setComparator(new Comparator<Worker>() {

									@Override
									public int compare(Worker o1, Worker o2) {
										Double posH1 = o1.getPosHours();
										Double posH2 = o2.getPosHours();
										return posH1.compareTo(posH2);
									}
								});
							}
							case SortType.NEG_HOURS -> {
								list1.setComparator(new Comparator<Worker>() {

									@Override
									public int compare(Worker o1, Worker o2) {
										Double negH1 = o1.getNegHours();
										Double negH2 = o2.getNegHours();
										return negH1.compareTo(negH2);
									}
								});
							}
						}
					}
					
					Iterator<Worker> it = list1.iterator();
					
					while(it.hasNext()) {
						list.add(it.next());
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
	
	public static boolean isValidDate(String date) {
		if(date != null && date != "") {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			
			try {
				format.parse(date);
				return true;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
}

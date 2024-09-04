package application.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class Utils {
	
	private static ExecutorService exe = Executors.newCachedThreadPool();
	
	public static ObservableList<Person> sortPersons(SortType type, 
			ObservableList<Person> input) {
		try {
			Future<ObservableList<Person>> future = 
					exe.submit(new Callable<ObservableList<Person>>() {

				@Override
				public ObservableList<Person> call() throws Exception {
					ObservableList<Person> list = FXCollections.observableArrayList();
					SortedList<Person> list1 = new SortedList<Person>(input);
					
					if(type != null && input != null && input.size() > 0) {
						switch (type) {
							case SortType.BY_ID -> {
								list1.setComparator(new Comparator<Person>() {

									@Override
									public int compare(Person o1, Person o2) {
										Integer id1 = o1.getId();
										Integer id2 = o2.getId();
										return id1.compareTo(id2);
									}
								});
							}
							case SortType.BY_AGE -> {
								list1.setComparator(new Comparator<Person>() {

									@Override
									public int compare(Person o1, Person o2) {
										Integer age1 = o1.getAge();
										Integer age2 = o2.getAge();
										return age1.compareTo(age2);
									}
								});
							}
							case SortType.ALPHABETICALLY_BY_FIRSTNAME -> {
								list1.setComparator(new Comparator<Person>() {

									@Override
									public int compare(Person o1, Person o2) {
										String firstName1 = o1.getFirstName();
										String firstName2 = o2.getFirstName();
										return firstName1.compareTo(firstName2);
									}
								});
							}
							case SortType.ALPHABETICALLY_BY_LASTNAME -> {
								list1.setComparator(new Comparator<Person>() {

									@Override
									public int compare(Person o1, Person o2) {
										String lastName1 = o1.getLastName();
										String lastName2 = o2.getLastName();
										return lastName1.compareTo(lastName2);
									}
								});
							}
						}
					}
					
					Iterator<Person> it = list1.iterator();
					
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
}

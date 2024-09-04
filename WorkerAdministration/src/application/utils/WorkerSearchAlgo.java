package application.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkerSearchAlgo {
	
	private LoadedPathManager lpm;
	
	private ObservableList<Person> loadedPersons;
	private String[] lastSearchData;
	private int currentSearchMatches;
	
	private SortType currentSortType;
	
	private ExecutorService exe;
		
	public WorkerSearchAlgo(LoadedPathManager l) {
		this.lpm = l;
		
		this.lastSearchData = new String[4];
		this.currentSearchMatches = 0;
		
		this.currentSortType = SortType.BY_ID;
		
		this.loadedPersons = Utils.sortPersons(this.currentSortType,
				this.lpm.getDataOutOfAllFiles());
		
		this.exe = Executors.newCachedThreadPool();
	}
	
	public ObservableList<Person> searchForData() {
		try {
			Future<ObservableList<Person>> future = 
					this.exe.submit(new Callable<ObservableList<Person>>() {
				
					@Override
					public ObservableList<Person> call() throws Exception {
						
						ObservableList<Person> tempList = FXCollections.observableArrayList();
						HashMap<Person, Integer> tempMap = new HashMap<Person, Integer>();
							
						ArrayList<Person> list1 = new ArrayList<Person>();
						ArrayList<Person> list2 = new ArrayList<Person>();
						ArrayList<Person> list3 = new ArrayList<Person>();
						ArrayList<Person> list4 = new ArrayList<Person>();
							
						ArrayList<Person> noMatch = new ArrayList<Person>();
							
						// data String 0 = id; 1 = vorname; 2 = nachname; 3 = alter
							
						if(lastSearchData != null) {
							for(int i1 = 0; i1 < loadedPersons.size(); i1++) {
								Person p = loadedPersons.get(i1);
									
								if(!tempMap.containsKey(p)) {
										
									String[] dataCheck = {String.valueOf(p.getId()), 
											p.getFirstName(), p.getLastName(), 
											String.valueOf(p.getAge())};
										
									int[] compares = compareInInt(dataCheck, lastSearchData);
									int o = 0;
										
									for(int i = 0; i < compares.length; i++) {
											
										if(compares[i] == 0) {
											o++;
										} else if(compares[i] == 2) {
											noMatch.add(p);
										}
									}
									tempMap.put(p, o);
								}
							}
								
							noMatch.forEach(e -> {
								if(tempMap.containsKey(e)) {
									tempMap.remove(e);
								}
							});
								
							for(Map.Entry<Person, Integer> entry : tempMap.entrySet()) {
								if(entry.getValue() == 4) {
									list1.add(entry.getKey());
								} else if(entry.getValue() == 3) {
									list2.add(entry.getKey());
								} else if(entry.getValue() == 2) {
									list3.add(entry.getKey());
								} else if(entry.getValue() == 1) {
									list4.add(entry.getKey());
								}
							}
								
							if(list1.size() > 0) {
								list1.forEach(e -> tempList.add(e));
							} else {
								if(list2.size() > 0) {
									list2.forEach(e -> tempList.add(e));
								} else {
									if(list3.size() > 0) {
										list3.forEach(e -> tempList.add(e));
									} else {
										if(list4.size() > 0) {
											list4.forEach(e -> tempList.add(e));
										}
									}
								}
							}
						}
											
						return Utils.sortPersons(currentSortType, tempList);
					}
			});
			
			return future.get();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int[] compareInInt(String[] d1, String[] d2) {
		int[] o = new int[4];
		int i = 0;
		
		while(i < d1.length) {
			if(d2[i] != "" && d2[i] != null) {
				if(d1[i].toLowerCase().contains(d2[i].toLowerCase())) {
					o[i] = 0;
					i++;
				} else {
					o[i] = 2;
					i++;
				}
			} else {
				o[i] = 1;
				i++;
			}
		}
		
		return o;
	}
	
	public boolean hasSearchData() {
		boolean b = false;
		int i = 0;
		
		while(i < this.lastSearchData.length) {
			if(this.lastSearchData[i] == null || this.lastSearchData[i] == "") {
				i++;
			} else {
				b = true;
				break;
			}
		}
		
		return b;
	}
	
	public ObservableList<Person> sortTable() {
		return this.sortTable(this.currentSortType);
	}
	
	public ObservableList<Person> sortTable(SortType type) {
		ObservableList<Person> list = FXCollections.observableArrayList();
		
		if(type != null) {
			this.currentSortType = type;
			
			if(this.hasSearchData()) {
				list = this.searchForData();
			} else {
				list = Utils.sortPersons(this.currentSortType,
						this.loadedPersons);
			}
		}
		
		return list;
	}
	
	public void updateSearchMatches() {
		if(!this.hasSearchData()) {
			this.currentSearchMatches = this.loadedPersons.size();
		} else {
			this.currentSearchMatches = this.searchForData().size();
		}
	}
	
	public void addPAsList(ObservableList<Person> list) {
		if(list != null) {
			if(this.loadedPersons.size() > 0) {
				Iterator<Person> it = list.iterator();
				
				while(it.hasNext()) {
					if(!this.loadedPersons.contains(it.next())) {
						this.loadedPersons.add(it.next());
					}
				}
			} else {
				this.loadedPersons = list;
			}
			
			this.updateSearchMatches();
		}
	}
	
	public void addP(Person p) {
		if(!this.loadedPersons.contains(p)) {
			this.loadedPersons.add(p);
			this.updateSearchMatches();
		}
	}
	
	public void removePAsList(ObservableList<Person> list) {
		if(list != null && this.loadedPersons != null && this.loadedPersons.size() > 0) {
			Iterator<Person> it = this.loadedPersons.iterator();
			
			while(it.hasNext()) {
				if(list.contains(it.next())) {
					it.remove();
				}
			}
			
			this.updateSearchMatches();
		}
	}
	
	public void removeP(Person p) {
		if(this.loadedPersons.contains(p)) {
			for(int i = 0; i < this.loadedPersons.size(); i++) {
				if(this.loadedPersons.get(i).equals(p)) {
					this.loadedPersons.remove(i);
				}
			}
			
			this.updateSearchMatches();
		}
	}
	
	public void setSearchData(SearchType type, String newSearchData) {
		if(type != null) {
			this.lastSearchData[type.getSearchTypeId()] = newSearchData;
		}
	}
	
	public String getLastSearchData(SearchType type) {
		return lastSearchData[type.getSearchTypeId()];
	}
	
	public String[] getLastSearchData() {
		return lastSearchData;
	}
	
	public void setCurrentSortType(SortType type) {
		this.currentSortType = type;
	}
	
	public SortType getCurrentSortType() {
		return currentSortType;
	}
	
	public ObservableList<Person> getLoadedPersons(){
		return loadedPersons;
	}
	
	public int getSearchMatches() {
		return currentSearchMatches;
	}
}

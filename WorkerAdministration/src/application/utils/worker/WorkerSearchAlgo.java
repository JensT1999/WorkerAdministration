package application.utils.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import application.utils.SearchType;
import application.utils.SortType;
import application.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WorkerSearchAlgo {
	
	private WorkerManager wm;
	
	private ObservableList<Worker> loadedPersons;
	private String[] lastSearchData;
	private int currentSearchMatches;
	
	private SortType currentSortType;
	
	private ExecutorService exe;
		
	public WorkerSearchAlgo(WorkerManager wm) {
		this.wm = wm;
		
		this.lastSearchData = new String[5];
		this.currentSearchMatches = 0;
		
		this.currentSortType = SortType.BY_ID;
		
		this.loadedPersons = Utils.sortPersons(this.currentSortType,
				this.wm.getWorkers());
		
		this.exe = Executors.newCachedThreadPool();
	}
	
	public ObservableList<Worker> searchForData() {
		try {
			Future<ObservableList<Worker>> future = 
					this.exe.submit(new Callable<ObservableList<Worker>>() {
				
					@Override
					public ObservableList<Worker> call() throws Exception {
						
						ObservableList<Worker> tempList = FXCollections.observableArrayList();
						HashMap<Worker, Integer> tempMap = new HashMap<Worker, Integer>();
							
						ArrayList<Worker> list1 = new ArrayList<Worker>();
						ArrayList<Worker> list2 = new ArrayList<Worker>();
						ArrayList<Worker> list3 = new ArrayList<Worker>();
						ArrayList<Worker> list4 = new ArrayList<Worker>();
						ArrayList<Worker> list5 = new ArrayList<Worker>();
							
						ArrayList<Worker> noMatch = new ArrayList<Worker>();
							
						// data String 0 = id; 1 = vorname; 2 = nachname; 3 = alter 4 = minusstd
							
						if(lastSearchData != null) {
							for(int i1 = 0; i1 < loadedPersons.size(); i1++) {
								if(loadedPersons.get(i1) != null) {
									Worker w = loadedPersons.get(i1);
									
									if(!tempMap.containsKey(w)) {
											
										String[] dataCheck = {String.valueOf(w.getId()), 
												w.getFirstName(), w.getLastName(), 
												String.valueOf(w.getAge()), 
												String.valueOf(w.getNegHours())};
											
										int[] compares = compareInInt(dataCheck, lastSearchData);
										int o = 0;
											
										for(int i = 0; i < compares.length; i++) {
												
											if(compares[i] == 0) {
												o++;
											} else if(compares[i] == 2) {
												noMatch.add(w);
											}
										}
										tempMap.put(w, o);
									}
								}
							}
								
							noMatch.forEach(e -> {
								if(tempMap.containsKey(e)) {
									tempMap.remove(e);
								}
							});
								
							for(Map.Entry<Worker, Integer> entry : tempMap.entrySet()) {
								if(entry.getValue() == 5) {
									list1.add(entry.getKey());
								} else if(entry.getValue() == 4) {
									list2.add(entry.getKey());
								} else if(entry.getValue() == 3) {
									list3.add(entry.getKey());
								} else if(entry.getValue() == 2) {
									list4.add(entry.getKey());
								} else if(entry.getValue() == 1) {
									list5.add(entry.getKey());
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
										} else {
											if(list5.size() > 0) {
												list5.forEach(e -> tempList.add(e));
											}
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
		int[] o = new int[5];
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
	
	public ObservableList<Worker> sortTable() {
		return this.sortTable(this.currentSortType);
	}
	
	public ObservableList<Worker> sortTable(SortType type) {
		ObservableList<Worker> list = FXCollections.observableArrayList();
		
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
	
	public void updateWorkerData() {
		if(this.wm.getWorkers() != null && this.wm.getWorkers().size() > 0) {
			synchronized (this.wm.getWorkers()) {
				this.loadedPersons = this.wm.getWorkers();
				this.updateSearchMatches();
			}
		}
	}
	
	public void updateSearchMatches() {
		if(!this.hasSearchData()) {
			this.currentSearchMatches = this.loadedPersons.size();
		} else {
			this.currentSearchMatches = this.searchForData().size();
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
	
	public ObservableList<Worker> getLoadedPersons(){
		return loadedPersons;
	}
	
	public int getSearchMatches() {
		return currentSearchMatches;
	}
}

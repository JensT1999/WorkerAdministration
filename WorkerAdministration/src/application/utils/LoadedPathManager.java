package application.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.ptv.TableViewConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoadedPathManager {
	
	private HashMap<String, ObservableList<Person>> loadedPaths;
	private TableViewConfig cfg;
	
	public LoadedPathManager(TableViewConfig c) {
		this.loadedPaths = new HashMap<String, ObservableList<Person>>();
		this.cfg = c;
	}
	
	public void loadPathsOutOfCfg() {
		if(this.cfg != null) {
			if(this.cfg.getLoadedPaths() != null &&
					this.cfg.getLoadedPaths().size() > 0) {
				ArrayList<String> lP = this.cfg.getLoadedPaths();
				
				for(int i = 0; i < lP.size(); i++) {
					if(lP.get(i) != null && lP.get(i) != "") {
						this.addLoadedPath(lP.get(i),
								this.convertFileIntoTable(lP.get(i)));
					}
				}
			}			
		}
	}
	
	public ObservableList<Person> convertFileIntoTable(String path) {
		ObservableList<Person> tempList = FXCollections.observableArrayList();
		if(FileManager.exists(path)) {
			ArrayList<String> file = FileManager.readFileAsList(path);
			
			if(file != null && file.size() > 0) {
				file.forEach(e -> {
					if(e != null && e != "") {
						String[] data = e.split(this.cfg.getDataSeparator());
						
						Person p = new Person();
						
						if(data[0] != null && data[0] != "") {
							p.setId(Integer.valueOf(data[0]));
						} else {
							p.setId(Integer.valueOf("N/A"));
						}
						
						if(data[1] != null && data[1] != "") {
							p.setFirstName(data[1]);
						} else {
							p.setFirstName("N/A");
						}
						
						if(data[2] != null && data[2] != "") {
							p.setLastName(data[2]);
						} else {
							p.setLastName("N/A");
						}
						
						if(data[3] != null && data[3] != "") {
							p.setAge(Integer.valueOf(data[3]));
						} else {
							p.setAge(Integer.valueOf("N/A"));
						}
						
						if(p != null) {
							if(!tempList.contains(p)) {
								tempList.add(p);
							}
						}
					}
				});
			}
		}
		return tempList;
	}
	
	public ObservableList<Person> convertTableIntoFile(String path) {
		ObservableList<Person> tempList = FXCollections.observableArrayList();
		if(path != null && path != "") {
			if(!FileManager.exists(path)) {
				FileManager.createFile(path);
			} else {
				FileManager.clearFile(path);
			}
			
			if(this.loadedPaths.containsKey(path)) {
				if(this.loadedPaths.get(path) != null &&
						this.loadedPaths.get(path).size() > 0) {
					ObservableList<Person> list = this.loadedPaths.get(path);
					
					ArrayList<String> list1 = new ArrayList<String>();
					
					for(int i = 0; i < list.size(); i++) {
						if(list.get(i) != null) {
							Person p = list.get(i);
							
							String data = String.valueOf(p.getId()) + this.cfg.getDataSeparator() +
									p.getFirstName() + this.cfg.getDataSeparator() + p.getLastName() +
									this.cfg.getDataSeparator() + String.valueOf(p.getAge());
							
							list1.add(data);
						}
					}
					
					if(list1 != null && list1.size() > 0) {
						FileManager.writeToFile(path, list1);
					}
					
					tempList = list;
				}
			}
		}
		return tempList;
	}
	
	public ObservableList<Person> getDataOutOfAllFiles() {
		ObservableList<Person> list = FXCollections.observableArrayList();
		if(this.loadedPaths != null && this.loadedPaths.size() > 0) {
			for(Map.Entry<String, ObservableList<Person>> entry : this.loadedPaths.entrySet()) {
				if(entry.getValue() != null && entry.getValue().size() > 0) {
					for(int i = 0; i < entry.getValue().size(); i++) {
						if(entry.getValue().get(i) != null) {
							list.add(entry.getValue().get(i));
						}
					}
				}
			}
		}
		return list;
	}
	
	public void addPersonToLoadedPath(String path, Person p) {
		if(path != null && path != "" && p != null) {
			if(this.loadedPaths.containsKey(path)) {
				ObservableList<Person> tempList = this.loadedPaths.get(path);
				
				tempList.add(p);
				
				this.loadedPaths.put(path, tempList);
			}
		}
	}
	
	public void removePersonOutOfLoadedPath(String path, Person p) {
		if(path != null && path != "" && p != null) {
			if(this.loadedPaths.containsKey(path)) {
				ObservableList<Person> tempList = this.loadedPaths.get(path);
				
				if(tempList.contains(p)) {
					for(int i = 0; i < tempList.size(); i++) {
						if(tempList.get(i).equals(p)) {
							tempList.remove(i);
						}
					}
				}
				
				this.loadedPaths.put(path, tempList);
			}
		}
	}
	
	public void addLoadedPath(String path, ObservableList<Person> data) {
		if(!this.loadedPaths.containsKey(path)) {
			this.loadedPaths.put(path, data);
		}
	}
	
	public void removeLoadedPath(String path) {
		if(this.loadedPaths.containsKey(path) &&
				this.cfg.getLoadedPaths().contains(path)) {
			this.loadedPaths.remove(path);
			
			for(int i = 0; i < this.cfg.getLoadedPaths().size(); i++) {
				if(this.cfg.getLoadedPaths().get(i).equals(path)) {
					this.cfg.getLoadedPaths().remove(i);
				}
			}
			
			this.cfg.save();
		}
	}
	
	public ObservableList<String> updateLoadedPaths() {
		ObservableList<String> list = FXCollections.observableArrayList();
		if(this.loadedPaths != null && this.loadedPaths.size() > 0) {
			
			for(Map.Entry<String, ObservableList<Person>> entry : this.loadedPaths.entrySet()) {
				if(entry.getKey() != null && entry.getKey() != "") {
					if(!list.contains(entry.getKey())) {
						list.add(entry.getKey());
					}
				}
			}
		}
		
		return list;
	}
	
	public void saveLoadedPaths() {
		if(this.loadedPaths != null && this.loadedPaths.size() > 0) {
			for(Map.Entry<String, ObservableList<Person>> entry : this.loadedPaths.entrySet()) {
				if(entry.getKey() != null && entry.getValue() != null) {
					if(!this.cfg.getLoadedPaths().contains(entry.getKey())) {
						this.cfg.getLoadedPaths().add(entry.getKey());
					}
				}
			}
			
			this.cfg.save();
		}
	}
	
	public HashMap<String, ObservableList<Person>> getLoadedPaths(){
		return loadedPaths;
	}
}

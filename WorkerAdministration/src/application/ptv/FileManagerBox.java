package application.ptv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.utils.FileManager;
import application.utils.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FileManagerBox extends VBox {
	
	private PersonTableView ptv;
	
	private Label l1;
	private ListView<String> lv;
	private TextField pathField;
	private HBox hBox;
	private Button b1;
	private Button b2;
	private Button b3;
	
	private HashMap<String, ObservableList<Person>> loadedPaths;
	
	public FileManagerBox(PersonTableView tv) {
		this.ptv = tv;
		
		this.l1 = new Label("Datei Pfade");
		this.lv = new ListView<String>();
		this.pathField = new TextField();
		this.hBox = new HBox();
		this.b1 = new Button("Auslesen");
		this.b2 = new Button("Speichern");
		this.b3 = new Button("Entfernen");
				
		this.loadedPaths = new HashMap<String, ObservableList<Person>>();
		
		this.buildBox();
	}
	
	private void buildBox() {
		this.l1.setMaxWidth(Double.MAX_VALUE);
		this.pathField.setMaxWidth(Double.MAX_VALUE);
		this.b3.setMaxWidth(Double.MAX_VALUE);
		
		VBox.setMargin(l1, new Insets(10));
		VBox.setMargin(lv, new Insets(10));
		VBox.setMargin(pathField, new Insets(10));
		VBox.setMargin(b3, new Insets(10));
		
		this.pathField.setOnKeyPressed(e -> {
			if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
				if(this.pathField.getText() != null && this.pathField.getText() != "") {
					String path = this.pathField.getText();
					this.addLoadedPath(path, this.convertFileIntoTable(path));
					this.saveLoadedPaths();
					this.ptv.updateComplete();
					this.pathField.clear();
				}
			}
		});
		
		this.b3.setOnMouseClicked(e -> {
			if(this.lv.getSelectionModel() != null &&
					this.lv.getSelectionModel().getSelectedItem() != null &&
					this.lv.getSelectionModel().getSelectedItem() != "") {
				String path = this.lv.getSelectionModel().getSelectedItem();
				
				this.removeLoadedPath(path);
				
				this.ptv.updateComplete();
			}
		});
		
		this.b1.setMaxWidth(Double.MAX_VALUE);
		this.b2.setMaxWidth(Double.MAX_VALUE);
		
		HBox.setMargin(b1, new Insets(10));
		HBox.setMargin(b2, new Insets(10));
		
		this.b1.setOnMouseClicked(e -> {
			if(this.pathField.getText() != null && this.pathField.getText() != "") {
				String path = this.pathField.getText();
				this.addLoadedPath(path, this.convertFileIntoTable(path));
				this.saveLoadedPaths();
				this.ptv.updateComplete();
				this.pathField.clear();
			}
		});
		
		this.b2.setOnMouseClicked(e -> {
			if(this.lv.getSelectionModel() != null && 
					this.lv.getSelectionModel().getSelectedItem() != null) {
				String path = this.lv.getSelectionModel().getSelectedItem();
				
				if(this.loadedPaths.containsKey(path)) {
					this.addLoadedPath(path, this.convertTableIntoFile(path));
					this.saveLoadedPaths();
					this.ptv.updateComplete();
				}
			}
			
			if(this.pathField.getText() != null && this.pathField.getText() != "") {
				String path = this.pathField.getText();
				this.addLoadedPath(path, this.convertTableIntoFile(path));
				this.saveLoadedPaths();
				this.ptv.updateComplete();
				this.pathField.clear();
			}
		});
		
		this.hBox.getChildren().addAll(b1, b2);
		this.getChildren().addAll(l1, lv, pathField, hBox, b3);
		
		this.loadPathsOutOfCfg();
	}
	
	private void loadPathsOutOfCfg() {
		if(this.ptv.getConfig() != null) {
			if(this.ptv.getConfig().getLoadedPaths() != null &&
					this.ptv.getConfig().getLoadedPaths().size() > 0) {
				ArrayList<String> lP = this.ptv.getConfig().getLoadedPaths();
				
				for(int i = 0; i < lP.size(); i++) {
					if(lP.get(i) != null && lP.get(i) != "") {
						this.addLoadedPath(lP.get(i),
								this.convertFileIntoTable(lP.get(i)));
					}
				}
				
				this.updateLoadedPaths();
			}			
		}
	}
	
	private ObservableList<Person> convertFileIntoTable(String path) {
		ObservableList<Person> tempList = FXCollections.observableArrayList();
		if(FileManager.exists(path)) {
			ArrayList<String> file = FileManager.readFileAsList(path);
			
			if(file != null && file.size() > 0) {
				file.forEach(e -> {
					if(e != null && e != "") {
						String[] data = e.split(this.ptv.getConfig().getDataSeparator());
						
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
	
	private ObservableList<Person> convertTableIntoFile(String path) {
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
							
							String data = String.valueOf(p.getId()) + this.ptv.getConfig().getDataSeparator() +
									p.getFirstName() + this.ptv.getConfig().getDataSeparator() + p.getLastName() +
									this.ptv.getConfig().getDataSeparator() + String.valueOf(p.getAge());
							
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
			this.updateLoadedPaths();
		}
	}
	
	public void removeLoadedPath(String path) {
		if(this.loadedPaths.containsKey(path) &&
				this.ptv.getConfig().getLoadedPaths().contains(path)) {
			this.loadedPaths.remove(path);
			this.updateLoadedPaths();
			
			for(int i = 0; i < this.ptv.getConfig().getLoadedPaths().size(); i++) {
				if(this.ptv.getConfig().getLoadedPaths().get(i).equals(path)) {
					this.ptv.getConfig().getLoadedPaths().remove(i);
				}
			}
			
			this.ptv.getConfig().save();
		}
	}
	
	public void updateLoadedPaths() {
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
		
		this.lv.setItems(list);
	}
	
	public void saveLoadedPaths() {
		if(this.loadedPaths != null && this.loadedPaths.size() > 0) {
			for(Map.Entry<String, ObservableList<Person>> entry : this.loadedPaths.entrySet()) {
				if(entry.getKey() != null && entry.getValue() != null) {
					if(!this.ptv.getConfig().getLoadedPaths().contains(entry.getKey())) {
						this.ptv.getConfig().getLoadedPaths().add(entry.getKey());
					}
				}
			}
			
			this.ptv.getConfig().save();
		}
	}

	public PersonTableView getPtv() {
		return ptv;
	}

	public void setPtv(PersonTableView ptv) {
		this.ptv = ptv;
	}

}

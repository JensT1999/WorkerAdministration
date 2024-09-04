package application.ptv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.utils.Person;
import application.utils.SortType;
import application.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PersonSearchBox extends HBox {
	
	private PersonTableView ptv;
	private TableView<Person> tv;
	
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField tf4;
	
	private MenuButton mb;
	private MenuItem mi1;
	private MenuItem mi2;
	private MenuItem mi3;
	private MenuItem mi4;
	
	private String[] lastSearchData;
	private int currentSearchMatches;
	
	private SortType currentSortType;
	
	private ObservableList<Person> loadedPersons;
	
	public PersonSearchBox(PersonTableView tv) {
		this.ptv = tv;
		this.tv = this.ptv;
		
		this.tf1 = new TextField();
		this.tf2 = new TextField();
		this.tf3 = new TextField();
		this.tf4 = new TextField();
		
		this.mb = new MenuButton("Sortierung");
		this.mi1 = new MenuItem("Nach " + SortType.BY_ID.toString());
		this.mi2 = new MenuItem("Nach " + SortType.ALPHABETICALLY_BY_FIRSTNAME.toString());
		this.mi3 = new MenuItem("Nach " + SortType.ALPHABETICALLY_BY_LASTNAME.toString());
		this.mi4 = new MenuItem("Nach " + SortType.BY_AGE.toString());
		
		this.lastSearchData = new String[4];
		this.currentSearchMatches = 0;
		
		this.currentSortType = SortType.BY_ID;
		
		this.loadedPersons = Utils.sortPersons(this.currentSortType,
				this.ptv.getFrame().getPathManager().getDataOutOfAllFiles());
		
		this.updateCurrentSearchMatches();
		
		this.buildBox();
	}
	
	private void buildBox() {
		this.tf1.setPromptText("ID");
		this.tf2.setPromptText("Vorname");
		this.tf3.setPromptText("Nachname");
		this.tf4.setPromptText("Alter");
		
		this.tf1.setMaxWidth(150);
		this.tf2.setMaxWidth(150);
		this.tf3.setMaxWidth(150);
		this.tf4.setMaxWidth(150);
		this.mb.setMaxWidth(150);
		
		HBox.setMargin(tf1, new Insets(10));
		HBox.setMargin(tf2, new Insets(10));
		HBox.setMargin(tf3, new Insets(10));
		HBox.setMargin(tf4, new Insets(10));
		HBox.setMargin(mb, new Insets(10));
		HBox.setHgrow(tf1, Priority.ALWAYS);
		HBox.setHgrow(tf2, Priority.ALWAYS);
		HBox.setHgrow(tf3, Priority.ALWAYS);
		HBox.setHgrow(tf4, Priority.ALWAYS);
		HBox.setHgrow(mb, Priority.ALWAYS);

		
		this.mb.getItems().addAll(mi1, mi2, mi3, mi4);
		this.getChildren().addAll(tf1, tf2, tf3, tf4, mb);
		
		this.tf1.setOnKeyPressed(e -> {
			if(e.getText() != "" && e.getText() != null) {
				if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
					if(this.tf1.getText() != "" && this.tf1.getText() != null) {
						this.lastSearchData[SearchType.ID.getSearchTypeId()] = this.tf1.getText();
					}
				} else {
					if(this.lastSearchData[SearchType.ID.getSearchTypeId()] != null) {
						this.lastSearchData[SearchType.ID.getSearchTypeId()] += e.getText();
					} else {
						this.lastSearchData[SearchType.ID.getSearchTypeId()] = e.getText();
					}
				}
				
				if(this.hasSearchData()) {
					this.tv.setItems(this.searchForData(this.loadedPersons,
							this.lastSearchData));
				}
				
				if(e.getCode().equals(KeyCode.BACK_SPACE)) {
					if(this.tf1.getText() != "" && this.tf1.getText() != null) {
						String data = this.lastSearchData[SearchType.ID.getSearchTypeId()];
						this.lastSearchData[SearchType.ID.getSearchTypeId()] = 
								data.substring(0, data.length() - 1);
						this.tv.setItems(this.searchForData(this.loadedPersons,
								this.lastSearchData));
					} else {
						this.lastSearchData[SearchType.ID.getSearchTypeId()] = "";
						
						if(this.hasSearchData()) {
							this.tv.setItems(this.searchForData(this.loadedPersons,
									this.lastSearchData));
						} else {
							this.tv.setItems(Utils.sortPersons(this.currentSortType,
									this.loadedPersons));
						}
					}
				}
				this.updateCurrentSearchMatches();
				this.ptv.getFrame().getDataBox().setCurrentSearchMatches();
			}
		});
		
		this.tf2.setOnKeyPressed(e -> {
			if(e.getText() != "" && e.getText() != null) {
				if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
					if(this.tf2.getText() != "" && this.tf2.getText() != null) {
						this.lastSearchData[SearchType.FIRSTNAME.getSearchTypeId()] = 
								this.tf2.getText();
					}
				} else {
					if(this.lastSearchData[SearchType.FIRSTNAME.getSearchTypeId()] != null) {
						this.lastSearchData[SearchType.FIRSTNAME.getSearchTypeId()] += e.getText();
					} else {
						this.lastSearchData[SearchType.FIRSTNAME.getSearchTypeId()] = e.getText();
					}
				}
				
				if(this.hasSearchData()) {
					this.tv.setItems(this.searchForData(this.loadedPersons,
							this.lastSearchData));
				}
				
				if(e.getCode().equals(KeyCode.BACK_SPACE)) {
					if(this.tf2.getText() != "" && this.tf2.getText() != null) {
						String data = this.lastSearchData[SearchType.FIRSTNAME.getSearchTypeId()];
						this.lastSearchData[SearchType.FIRSTNAME.getSearchTypeId()] = 
								data.substring(0, data.length() - 1);
						this.tv.setItems(this.searchForData(this.loadedPersons,
								this.lastSearchData));
					} else {

						this.lastSearchData[SearchType.FIRSTNAME.getSearchTypeId()] = "";
						
						if(this.hasSearchData()) {
							this.tv.setItems(this.searchForData(this.loadedPersons,
									this.lastSearchData));
						} else {
							this.tv.setItems(Utils.sortPersons(this.currentSortType,
									this.loadedPersons));
						}
					}
				}
				this.updateCurrentSearchMatches();
				this.ptv.getFrame().getDataBox().setCurrentSearchMatches();
			}
		});
		
		this.tf3.setOnKeyPressed(e -> {
			if(e.getText() != "" && e.getText() != null) {
				if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
					if(this.tf3.getText() != "" && this.tf3.getText() != null) {
						this.lastSearchData[SearchType.LASTNAME.getSearchTypeId()] = 
								this.tf3.getText();
					}
				} else {
					if(this.lastSearchData[SearchType.LASTNAME.getSearchTypeId()] != null) {
						this.lastSearchData[SearchType.LASTNAME.getSearchTypeId()] += e.getText();
					} else {
						this.lastSearchData[SearchType.LASTNAME.getSearchTypeId()] = e.getText();
					}
				}
				
				if(this.hasSearchData()) {
					this.tv.setItems(this.searchForData(this.loadedPersons,
							this.lastSearchData));
				}
				
				if(e.getCode().equals(KeyCode.BACK_SPACE)) {
					if(this.tf3.getText() != "" && this.tf3.getText() != null) {
						String data = this.lastSearchData[SearchType.LASTNAME.getSearchTypeId()];
						this.lastSearchData[SearchType.LASTNAME.getSearchTypeId()] = 
								data.substring(0, data.length() - 1);
						this.tv.setItems(this.searchForData(this.loadedPersons,
								this.lastSearchData));
					} else {

						this.lastSearchData[SearchType.LASTNAME.getSearchTypeId()] = "";
						
						if(this.hasSearchData()) {
							this.tv.setItems(this.searchForData(this.loadedPersons,
									this.lastSearchData));
						} else {
							this.tv.setItems(Utils.sortPersons(this.currentSortType,
									this.loadedPersons));
						}
					}
				}
				this.updateCurrentSearchMatches();
				this.ptv.getFrame().getDataBox().setCurrentSearchMatches();
			}
		});
		
		this.tf4.setOnKeyPressed(e -> {
			if(e.getText() != "" && e.getText() != null) {
				if(e.getCode().equals(KeyCode.ENTER) || e.getCode().equals(KeyCode.TAB)) {
					if(this.tf4.getText() != "" && this.tf4.getText() != null) {
						this.lastSearchData[SearchType.AGE.getSearchTypeId()] = this.tf4.getText();
					}
				} else {
					if(this.lastSearchData[SearchType.AGE.getSearchTypeId()] != null) {
						this.lastSearchData[SearchType.AGE.getSearchTypeId()] += e.getText();
					} else {
						this.lastSearchData[SearchType.AGE.getSearchTypeId()] = e.getText();
					}
				}
				
				if(this.hasSearchData()) {
					this.tv.setItems(this.searchForData(this.loadedPersons,
							this.lastSearchData));
				}
				
				if(e.getCode().equals(KeyCode.BACK_SPACE)) {
					if(this.tf4.getText() != "" && this.tf4.getText() != null) {
						String data = this.lastSearchData[SearchType.AGE.getSearchTypeId()];
						this.lastSearchData[SearchType.AGE.getSearchTypeId()] = 
								data.substring(0, data.length() - 1);
						this.tv.setItems(this.searchForData(this.loadedPersons, 
								this.lastSearchData));
					} else {

						this.lastSearchData[SearchType.AGE.getSearchTypeId()] = "";
						
						if(this.hasSearchData()) {
							this.tv.setItems(this.searchForData(this.loadedPersons,
									this.lastSearchData));
						} else {
							this.tv.setItems(Utils.sortPersons(this.currentSortType,
									this.loadedPersons));
						}
					}
				}
				this.updateCurrentSearchMatches();
				this.ptv.getFrame().getDataBox().setCurrentSearchMatches();
			}
		});
		
		this.mi1.setOnAction(e -> {
			if(this.tv != null && this.ptv.getLoadedPersons() != null) {
				this.sortTable(SortType.BY_ID);
				this.mb.setText(this.mi1.getText());
			}
		});
		
		this.mi2.setOnAction(e -> {
			if(this.tv != null && this.ptv.getLoadedPersons() != null) {
				this.sortTable(SortType.ALPHABETICALLY_BY_FIRSTNAME);
				this.mb.setText(this.mi2.getText());
			}
		});
		
		this.mi3.setOnAction(e -> {
			if(this.tv != null && this.ptv.getLoadedPersons() != null) {
				this.sortTable(SortType.ALPHABETICALLY_BY_LASTNAME);
				this.mb.setText(this.mi3.getText());
			}
		});
		
		this.mi4.setOnAction(e -> {
			if(this.tv != null && this.ptv.getLoadedPersons() != null) {
				this.sortTable(SortType.BY_AGE);
				this.mb.setText(this.mi4.getText());
			}
		});
	}
	
	private ObservableList<Person> searchForData(ObservableList<Person> list, String[] data) {
		return this.searchForData(this.currentSortType, list, data);
	}
	
	private ObservableList<Person> searchForData(SortType type, 
			ObservableList<Person> list, String[] data) {
		ObservableList<Person> tempList = FXCollections.observableArrayList();
		HashMap<Person, Integer> tempMap = new HashMap<Person, Integer>();
		
		ArrayList<Person> list1 = new ArrayList<Person>();
		ArrayList<Person> list2 = new ArrayList<Person>();
		ArrayList<Person> list3 = new ArrayList<Person>();
		ArrayList<Person> list4 = new ArrayList<Person>();
		
		ArrayList<Person> noMatch = new ArrayList<Person>();
		
		// data String 0 = id; 1 = vorname; 2 = nachname; 3 = alter
		
		if(data != null) {
			for(int i1 = 0; i1 < list.size(); i1++) {
				Person p = list.get(i1);
				
				if(!tempMap.containsKey(p)) {
					
					String[] dataCheck = {String.valueOf(p.getId()), p.getFirstName(),
							p.getLastName(), String.valueOf(p.getAge())};
					
					int[] compares = this.compareInInt(dataCheck, data);
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
				
		return Utils.sortPersons(type, tempList);
	}
	
	private int[] compareInInt(String[] d1, String[] d2) {
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
	
	public void updateSearchData() {
		if(this.loadedPersons != null && this.lastSearchData != null && this.hasSearchData()) {
			this.tv.setItems(this.searchForData(this.loadedPersons, this.lastSearchData));
		}
	}
	
	public void sortTable(SortType type) {
		if(type != null) {
			this.currentSortType = type;
					
			if(this.hasSearchData()) {
				this.tv.setItems(this.searchForData(this.loadedPersons, 
						this.lastSearchData));
			} else {
				this.tv.setItems(Utils.sortPersons(type, this.loadedPersons));
			}
		}
	}
	
	public void addP(Person p) {
		if(!this.loadedPersons.contains(p)) {
			this.loadedPersons.add(p);
			this.updateCurrentSearchMatches();
			this.ptv.getFrame().getDataBox().setCurrentSearchMatches();
		}
	}
	
	public void removeP(Person p) {
		if(this.loadedPersons.contains(p)) {
			for(int i = 0; i < this.loadedPersons.size(); i++) {
				if(this.loadedPersons.get(i).equals(p)) {
					this.loadedPersons.remove(i);
				}
			}
			
			this.updateCurrentSearchMatches();
			this.ptv.getFrame().getDataBox().setCurrentSearchMatches();
		}
	}
	
	public void resetSearch() {
		if(this.loadedPersons != null && this.loadedPersons.size() > 0) {
			this.tv.setItems(this.loadedPersons);
			this.currentSearchMatches = this.loadedPersons.size();
		}
	}
	
	public void setCurrentSortType(SortType type) {
		this.currentSortType = type;
	}
	
	public void updateCurrentSearchMatches() {
		this.currentSearchMatches = this.tv.getItems().size();
	}
	
	public void setLoadedPersons(ObservableList<Person> list) {
		this.loadedPersons = list;
	}
	
	public int getCurrentSearchMatches() {
		return currentSearchMatches;
	}
	
	public void setCurrentSearchMatches(int i) {
		this.currentSearchMatches = i;
	}
	
	enum SearchType {
		ID(0),
		FIRSTNAME(1),
		LASTNAME(2),
		AGE(3);
		
		private int searchTypeId;
		
		SearchType(int id) {
			this.searchTypeId = id;
		}
		
		public int getSearchTypeId() {
			return searchTypeId;
		}
	}

}

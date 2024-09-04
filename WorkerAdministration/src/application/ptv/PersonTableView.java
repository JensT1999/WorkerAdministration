package application.ptv;

import application.PersonTableFrame;
import application.utils.Person;
import application.utils.SortType;
import application.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class PersonTableView extends TableView<Person> {
	
	private PersonTableFrame ptf;
	
	private TableColumn<Person, Integer> tc1;
	private TableColumn<Person, String> tc2;
	private TableColumn<Person, String> tc3;
	private TableColumn<Person, Integer> tc4;
	
	private boolean editable;
	
	private ObservableList<Person> loadedPersons;
	
	private TableViewConfig cfg;
	
	public PersonTableView(PersonTableFrame ptf) {
		this.ptf = ptf;
		this.tc1 = new TableColumn<Person, Integer>("ID");
		this.tc2 = new TableColumn<Person, String>("Vorname");
		this.tc3 = new TableColumn<Person, String>("Nachname");
		this.tc4 = new TableColumn<Person, Integer>("Alter");
		
		this.editable = true;
		
		this.cfg = new TableViewConfig(this, "C:\\Users\\ctrap\\Desktop\\test", "config.txt");
		this.cfg.load();
		
		this.loadedPersons = Utils.sortPersons(SortType.BY_ID, 
				this.ptf.getFileManagerBox().getDataOutOfAllFiles());
		
		this.setItems(this.loadedPersons);
		
		this.buildTable();
	}
	
	private void buildTable() {
		this.setEditable(this.editable);
		
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		this.tc1.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
		this.tc1.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		this.tc1.setOnEditCommit(e -> {
			if(e.getRowValue() != null) {
				Person p = e.getRowValue();
				
				this.removeP(p);
				
				p.setId(e.getNewValue());
				this.addP(p);
				
				this.ptf.getPersonSearchBox().updateSearchData();
				
				this.ptf.getDataBox().setData(p);
			}
		});
		
		
		this.tc2.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		this.tc2.setCellFactory(TextFieldTableCell.forTableColumn());
		this.tc2.setOnEditCommit(e -> {
			if(e.getRowValue() != null) {
				Person p = e.getRowValue();
				
				this.removeP(p);
				
				p.setFirstName(e.getNewValue());
				this.addP(p);
				
				this.ptf.getPersonSearchBox().updateSearchData();
				
				this.ptf.getDataBox().setData(p);
			}
		});
		
		this.tc3.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		this.tc3.setCellFactory(TextFieldTableCell.forTableColumn());
		this.tc3.setOnEditCommit(e -> {
			if(e.getRowValue() != null) {
				Person p = e.getRowValue();
				
				this.removeP(p);
				
				p.setLastName(e.getNewValue());
				this.addP(p);
				
				this.ptf.getPersonSearchBox().updateSearchData();
				
				this.ptf.getDataBox().setData(p);
			}
		});
		
		this.tc4.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
		this.tc4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		this.tc4.setOnEditCommit(e -> {
			if(e.getRowValue() != null) {
				Person p = e.getRowValue();
				
				this.removeP(p);
				
				p.setAge(e.getNewValue());
				this.addP(p);
				
				this.ptf.getPersonSearchBox().updateSearchData();
				
				this.ptf.getDataBox().setData(p);
			}
		});
		
		this.setOnMouseClicked(e -> {
			if(this.getSelectionModel().getSelectedItem() != null) {
				Person p = this.getSelectionModel().getSelectedItem();
				this.ptf.getDataBox().setData(p);
			}
		});
		
		this.setOnSort(e -> {
			if(this.ptf.getPersonSearchBox().hasSearchData()) {
				this.ptf.getPersonSearchBox().updateSearchData();
			}
		});
		
		this.getColumns().addAll(tc1, tc2, tc3, tc4);
		
		this.ptf.getPersonSearchBox().updateCurrentSearchMatches();
		
		this.ptf.getDataBox().setCurrentSearchMatches();
		
	}
	
	public void changeEditable() {
		if(this.editable) {
			this.editable = false;
		} else {
			this.editable = true;
		}
		this.setEditable(this.editable);
	}
	
	public void addP(Person p) {
		if(!this.loadedPersons.contains(p)) {
			this.loadedPersons.add(p);
			this.ptf.getPersonSearchBox().addP(p);
		}
	}
	
	public void removeP(Person p) {
		for(int i = 0 ; i < this.loadedPersons.size(); i++) {
			if(this.loadedPersons.get(i).equals(p)) {
				this.loadedPersons.remove(i);
			}
		}
	}
	
	public void updateComplete() {
		if(this.getConfig().getLoadedPaths() != null && 
				this.getConfig().getLoadedPaths().size() > 0) {
			if(this.ptf.getFileManagerBox().getDataOutOfAllFiles() != null && 
					this.ptf.getFileManagerBox().getDataOutOfAllFiles().size() > 0) {
				this.loadedPersons = this.ptf.getFileManagerBox().getDataOutOfAllFiles();
				this.ptf.getPersonSearchBox().setLoadedPersons(this.loadedPersons);
				this.setItems(this.loadedPersons);
			}
		} else {
			ObservableList<Person> list = FXCollections.observableArrayList();
			this.ptf.getPersonSearchBox().setLoadedPersons(list);
			this.setItems(list);
		}
		
		this.ptf.getPersonSearchBox().updateCurrentSearchMatches();
		this.ptf.getDataBox().setCurrentSearchMatches();
	}
	
	public ObservableList<Person> getLoadedPersons(){
		return loadedPersons;
	}
	
	public PersonTableFrame getFrame() {
		return ptf;
	}
	
	public TableViewConfig getConfig() {
		return cfg;
	}
	
	public ObservableList<Person> getItemsOfTable() {
		return this.getItems();
	}
}

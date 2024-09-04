package application.ptv;

import application.PersonTableFrame;
import application.utils.Person;
import application.utils.SortType;
import application.utils.Utils;
import application.utils.WorkerSearchAlgo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class PersonTableView extends TableView<Person> {
	
	private PersonTableFrame ptf;
	private WorkerSearchAlgo wsa;
	
	private TableColumn<Person, Integer> tc1;
	private TableColumn<Person, String> tc2;
	private TableColumn<Person, String> tc3;
	private TableColumn<Person, Integer> tc4;
	
	private boolean editable;
		
	private TableViewConfig cfg;
	
	public PersonTableView(PersonTableFrame ptf) {
		this.ptf = ptf;
		this.wsa = this.ptf.getWSA();
		
		this.tc1 = new TableColumn<Person, Integer>("ID");
		this.tc2 = new TableColumn<Person, String>("Vorname");
		this.tc3 = new TableColumn<Person, String>("Nachname");
		this.tc4 = new TableColumn<Person, Integer>("Alter");
		
		this.editable = true;
		
		this.cfg = this.ptf.getConfig();
		
		this.setItems(this.wsa.getLoadedPersons());
		
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
			if(this.ptf.getPersonSearchBox().getWSA().hasSearchData()) {
				this.ptf.getPersonSearchBox().updateSearchData();
			}
		});
		
		this.getColumns().addAll(tc1, tc2, tc3, tc4);
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
		if(!this.wsa.getLoadedPersons().contains(p)) {
			this.wsa.addP(p);
			this.updateComplete();
		}
	}
	
	public void removeP(Person p) {
		if(this.wsa.getLoadedPersons().contains(p)) {
			this.wsa.removeP(p);
		}
	}
	
	public void updateComplete() {
		if(this.wsa.getLoadedPersons() != null && this.wsa.getLoadedPersons().size() > 0) {
			if(this.wsa.hasSearchData()) {
				this.setItems(this.wsa.searchForData());
			} else {
				this.setItems(this.wsa.sortTable());
			}
		} else {
			ObservableList<Person> list = FXCollections.observableArrayList();
			this.setItems(list);
		}
		
		this.wsa.updateSearchMatches();
		this.ptf.getDataBox().setCurrentSearchMatches();
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
	
	public WorkerSearchAlgo getWSA() {
		return wsa;
	}
}

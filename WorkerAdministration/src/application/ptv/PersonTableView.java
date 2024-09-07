package application.ptv;

import application.utils.SortType;
import application.utils.Utils;
import application.utils.worker.Worker;
import application.utils.worker.WorkerSearchAlgo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class PersonTableView extends TableView<Worker> {
	
	private PersonTableFrame ptf;
	private WorkerSearchAlgo wsa;
	
	private TableColumn<Worker, Integer> tc1;
	private TableColumn<Worker, String> tc2;
	private TableColumn<Worker, String> tc3;
	private TableColumn<Worker, Integer> tc4;
	private TableColumn<Worker, Double> tc5;
				
	public PersonTableView(PersonTableFrame ptf) {
		this.ptf = ptf;
		this.wsa = this.ptf.getWorkerManager().getWSA();
		
		this.tc1 = new TableColumn<Worker, Integer>("ID");
		this.tc2 = new TableColumn<Worker, String>("Vorname");
		this.tc3 = new TableColumn<Worker, String>("Nachname");
		this.tc4 = new TableColumn<Worker, Integer>("Alter");
		this.tc5 = new TableColumn<Worker, Double>("Minusstunden");
						
		this.setItems(this.wsa.getLoadedPersons());
		
		this.buildTable();
	}
	
	private void buildTable() {
		this.setEditable(false);
		
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		this.tc1.setCellValueFactory(new PropertyValueFactory<Worker, Integer>("id"));
		this.tc2.setCellValueFactory(new PropertyValueFactory<Worker, String>("firstName"));
		this.tc3.setCellValueFactory(new PropertyValueFactory<Worker, String>("lastName"));
		this.tc4.setCellValueFactory(new PropertyValueFactory<Worker, Integer>("age"));
		this.tc5.setCellValueFactory(new PropertyValueFactory<Worker, Double>("negHours"));
		
		this.setOnMouseClicked(e -> {
			if(this.getSelectionModel().getSelectedItem() != null) {
				Worker p = this.getSelectionModel().getSelectedItem();
				this.ptf.getDataBox().setData(p);
				
				if(e.getClickCount() >= 2) {
					this.ptf.getWDB().showBoxFor(this.getSelectionModel().getSelectedItem());
				}
			}
		});
				
		this.setOnSort(e -> {
			if(this.wsa.hasSearchData()) {
				this.ptf.getPersonSearchBox().updateSearchData();
			}
		});
		
		this.getColumns().addAll(tc1, tc2, tc3, tc4, tc5);
	}
	
	public void updateComplete() {
		if(this.wsa.getLoadedPersons() != null && this.wsa.getLoadedPersons().size() > 0) {
			if(this.wsa.hasSearchData()) {
				this.setItems(this.wsa.searchForData());
			} else {
				this.setItems(this.wsa.sortTable());
			}
		} else {
			ObservableList<Worker> list = FXCollections.observableArrayList();
			this.setItems(list);
		}
		
		this.wsa.updateSearchMatches();
		this.ptf.getDataBox().setCurrentSearchMatches();
	}
	
	public PersonTableFrame getFrame() {
		return ptf;
	}
	
	
	public ObservableList<Worker> getItemsOfTable() {
		return this.getItems();
	}
	
	public WorkerSearchAlgo getWSA() {
		return wsa;
	}
}

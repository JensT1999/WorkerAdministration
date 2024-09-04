package application.ptv;

import application.utils.LoadedPathManager;
import application.utils.Person;
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
	
	private LoadedPathManager lpm;
	
	private Label l1;
	private ListView<String> lv;
	private TextField pathField;
	private HBox hBox;
	private Button b1;
	private Button b2;
	private Button b3;
		
	public FileManagerBox(PersonTableView tv) {
		this.ptv = tv;
		
		this.lpm = this.ptv.getFrame().getPathManager();
		
		this.l1 = new Label("Datei Pfade");
		this.lv = new ListView<String>();
		this.pathField = new TextField();
		this.hBox = new HBox();
		this.b1 = new Button("Auslesen");
		this.b2 = new Button("Speichern");
		this.b3 = new Button("Entfernen");
				
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
					ObservableList<Person> list = this.lpm.convertFileIntoTable(path);
					this.lpm.addLoadedPath(path, list);
					this.lpm.saveLoadedPaths();
					this.updateLoadedPaths();
					this.ptv.getWSA().addPAsList(list);
					
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
				
				ObservableList<Person> list = this.lpm.getListFromPath(path);
				this.lpm.removeLoadedPath(path);
				this.updateLoadedPaths();
				
				this.ptv.getWSA().removePAsList(list);
				
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
				ObservableList<Person> list = this.lpm.convertFileIntoTable(path);
				this.lpm.addLoadedPath(path, list);
				this.lpm.saveLoadedPaths();
				this.updateLoadedPaths();
				this.ptv.getWSA().addPAsList(list);
				
				this.ptv.updateComplete();
				this.pathField.clear();
			}
		});
		
		this.b2.setOnMouseClicked(e -> {
			if(this.lv.getSelectionModel() != null && 
					this.lv.getSelectionModel().getSelectedItem() != null) {
				String path = this.lv.getSelectionModel().getSelectedItem();
				
				if(this.lpm.getLoadedPaths().containsKey(path)) {
					this.lpm.addLoadedPath(path, this.lpm.convertTableIntoFile(path));
					this.lpm.saveLoadedPaths();
					this.updateLoadedPaths();
					this.ptv.updateComplete();
				}
			}
			
			if(this.pathField.getText() != null && this.pathField.getText() != "") {
				String path = this.pathField.getText();
				this.lpm.addLoadedPath(path, this.lpm.convertTableIntoFile(path));
				this.lpm.saveLoadedPaths();
				this.updateLoadedPaths();
				this.ptv.updateComplete();
				this.pathField.clear();
			}
		});
		
		this.hBox.getChildren().addAll(b1, b2);
		this.getChildren().addAll(l1, lv, pathField, hBox, b3);
		
		this.updateLoadedPaths();
	}
	
	private void updateLoadedPaths() {
		this.lv.setItems(this.lpm.updateLoadedPaths());
	}

	public PersonTableView getPtv() {
		return ptv;
	}

	public void setPtv(PersonTableView ptv) {
		this.ptv = ptv;
	}

}

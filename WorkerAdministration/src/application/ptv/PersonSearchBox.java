package application.ptv;

import application.utils.SearchType;
import application.utils.SortType;
import application.utils.WorkerSearchAlgo;
import javafx.geometry.Insets;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PersonSearchBox extends HBox {
	
	private PersonTableView ptv;
	private WorkerSearchAlgo wsa;
	
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField tf4;
	
	private MenuButton mb;
	private MenuItem mi1;
	private MenuItem mi2;
	private MenuItem mi3;
	private MenuItem mi4;
	
	public PersonSearchBox(PersonTableView tv, WorkerSearchAlgo wsa) {
		this.ptv = tv;		
		this.wsa = wsa;
		
		this.tf1 = new TextField();
		this.tf2 = new TextField();
		this.tf3 = new TextField();
		this.tf4 = new TextField();
		
		this.mb = new MenuButton("Sortierung");
		this.mi1 = new MenuItem("Nach " + SortType.BY_ID.toString());
		this.mi2 = new MenuItem("Nach " + SortType.ALPHABETICALLY_BY_FIRSTNAME.toString());
		this.mi3 = new MenuItem("Nach " + SortType.ALPHABETICALLY_BY_LASTNAME.toString());
		this.mi4 = new MenuItem("Nach " + SortType.BY_AGE.toString());
		
		this.wsa.updateSearchMatches();
		
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
				this.keyPressed(this.tf1, e.getCode(), SearchType.ID, e.getText());
			}
		});
		
		this.tf2.setOnKeyPressed(e -> {
			if(e.getText() != "" && e.getText() != null) {
				this.keyPressed(this.tf2, e.getCode(), SearchType.FIRSTNAME, e.getText());
			}
		});
		
		this.tf3.setOnKeyPressed(e -> {
			if(e.getText() != "" && e.getText() != null) {
				this.keyPressed(this.tf3, e.getCode(), SearchType.LASTNAME, e.getText());
			}
		});
		
		this.tf4.setOnKeyPressed(e -> {
			if(e.getText() != "" && e.getText() != null) {
				this.keyPressed(this.tf4, e.getCode(), SearchType.AGE, e.getText());
			}
		});
		
		this.mi1.setOnAction(e -> {
			if(this.ptv != null && this.wsa.getLoadedPersons() != null) {
				this.sortTable(SortType.BY_ID);
				this.mb.setText(this.mi1.getText());
			}
		});
		
		this.mi2.setOnAction(e -> {
			if(this.ptv != null && this.wsa.getLoadedPersons() != null) {
				this.sortTable(SortType.ALPHABETICALLY_BY_FIRSTNAME);
				this.mb.setText(this.mi2.getText());
			}
		});
		
		this.mi3.setOnAction(e -> {
			if(this.ptv != null && this.wsa.getLoadedPersons() != null) {
				this.sortTable(SortType.ALPHABETICALLY_BY_LASTNAME);
				this.mb.setText(this.mi3.getText());
			}
		});
		
		this.mi4.setOnAction(e -> {
			if(this.ptv != null && this.wsa.getLoadedPersons() != null) {
				this.sortTable(SortType.BY_AGE);
				this.mb.setText(this.mi4.getText());
			}
		});
	}
	
	private void keyPressed(TextField tf, KeyCode code, SearchType type, String text) {
		if(tf != null && code != null && type != null && text != null) {
			if(code.equals(KeyCode.ENTER) || code.equals(KeyCode.TAB)) {
				if(tf.getText() != "" && tf.getText() != null) {
					this.wsa.setSearchData(type, tf.getText());
				}
			} else {
				if(this.wsa.getLastSearchData(type) != null) {
					String t = this.wsa.getLastSearchData(type) + text;
					this.wsa.setSearchData(type, t);
				} else {
					this.wsa.setSearchData(type, text);
				}
			}
			
			if(this.wsa.hasSearchData()) {
				this.ptv.setItems(this.wsa.searchForData());
			}
			
			if(code.equals(KeyCode.BACK_SPACE)) {
				if(tf.getText() != "" && tf.getText() != null) {
					String data = this.wsa.getLastSearchData(type);
					this.wsa.setSearchData(type, data.substring(0, data.length() - 1));
					this.ptv.setItems(this.wsa.searchForData());
				} else {
					this.wsa.setSearchData(type, "");
					
					if(this.wsa.hasSearchData()) {
						this.ptv.setItems(this.wsa.searchForData());
					} else {
						this.ptv.setItems(this.wsa.sortTable());
					}
				}
			}
			this.wsa.updateSearchMatches();
			this.ptv.getFrame().getDataBox().setCurrentSearchMatches();
		}
	}
	
	public void updateSearchData() {
		if(this.wsa.getLoadedPersons() != null && this.wsa.getLastSearchData() != null &&
				this.wsa.hasSearchData()) {
			
			this.ptv.setItems(this.wsa.searchForData());
		}
	}
	
	public void sortTable(SortType type) {
		if(type != null) {
			this.ptv.setItems(this.wsa.sortTable(type));
		}
	}
	
	public void resetSearch() {
		if(this.wsa.getLoadedPersons() != null 
				&& this.wsa.getLoadedPersons().size() > 0) {
			this.ptv.setItems(this.wsa.getLoadedPersons());
			this.wsa.updateSearchMatches();
		}
	}
	
	public int getCurrentSearchMatches() {
		return wsa.getSearchMatches();
	}
	
	public WorkerSearchAlgo getWSA() {
		return wsa;
	}
}

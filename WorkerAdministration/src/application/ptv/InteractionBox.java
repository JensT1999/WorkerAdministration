package application.ptv;

import application.utils.Person;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class InteractionBox extends VBox {
	
	private PersonTableView ptv;
	private TableView<Person> tv;
	
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField tf4;
	private Button b1;
	private Button b2;
	private Button b3;
	
	public InteractionBox(PersonTableView tv) {
		this.ptv = tv;
		this.tv = this.ptv;
		
		this.tf1 = new TextField();
		this.tf2 = new TextField();
		this.tf3 = new TextField();
		this.tf4 = new TextField();
		this.b1 = new Button("Hinzufügen");
		this.b2 = new Button("Entfernen");
		this.b3 = new Button("Editierbar");
		
		this.buildBox();
	}

	private void buildBox() {
		this.setPrefSize(225, 400);
		this.tf1.setPromptText("ID");
		this.tf2.setPromptText("Vorname");
		this.tf3.setPromptText("Nachname");
		this.tf4.setPromptText("Alter");
		
		this.b1.setMaxWidth(Double.MAX_VALUE);
		this.b2.setMaxWidth(Double.MAX_VALUE);
		this.b3.setMaxWidth(Double.MAX_VALUE);
				
		VBox.setMargin(tf1, new Insets(10));
		VBox.setMargin(tf2, new Insets(10));
		VBox.setMargin(tf3, new Insets(10));
		VBox.setMargin(tf4, new Insets(10));
		VBox.setMargin(b1, new Insets(10));
		VBox.setMargin(b2, new Insets(10));
		VBox.setMargin(b3, new Insets(10));
		
		this.getChildren().addAll(tf1, tf2, tf3, tf4, b1, b2, b3);
		
		this.b1.setOnMouseClicked(e -> {
			if(this.tf1.getText() != "") {
				if(this.tf2.getText() != "") {
					if(this.tf3.getText() != "") {
						if(this.tf4.getText() != "") {
							Person p = new Person(Integer.valueOf(this.tf1.getText()),
									this.tf2.getText(), this.tf3.getText(),
									Integer.valueOf(this.tf4.getText()));
							
							this.ptv.addP(p);
							
							this.ptv.getFrame().getPersonSearchBox().updateSearchData();
							this.ptv.getFrame().getDataBox().setCurrentSearchMatches();
							
							this.tf1.clear();
							this.tf2.clear();
							this.tf3.clear();
							this.tf4.clear();
							this.tf1.requestFocus();
						}
					}
				}
			}
		});
		
		this.b2.setOnMouseClicked(e -> {
			if(this.tv.getSelectionModel() != null && 
					this.tv.getSelectionModel().getSelectedItem() != null) {
				
				Person p = this.tv.getSelectionModel().getSelectedItem();
				
				this.ptv.removeP(p);
				
				this.ptv.getFrame().getPersonSearchBox().updateSearchData();
				
				this.ptv.getFrame().getDataBox().setCurrentSearchMatches();

				this.tv.getSelectionModel().clearSelection();
				
				this.ptv.getFrame().getDataBox().clearData();
			}
		});
		
		this.b3.setOnMouseClicked(e -> {
			if(this.tv != null) {
				this.ptv.changeEditable();
			}
		});
	}	
}

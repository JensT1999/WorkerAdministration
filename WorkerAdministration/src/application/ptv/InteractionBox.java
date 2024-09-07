package application.ptv;

import application.utils.FileManager;
import application.utils.Utils;
import application.utils.worker.NewWorker;
import application.utils.worker.Worker;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class InteractionBox extends VBox {
	
	private PersonTableFrame ptf;
	
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField pathField;
	private Button b1;
	private Button b2;
	private Button b3;
	
	public InteractionBox(PersonTableFrame ptf) {
		this.ptf = ptf;
		
		this.tf1 = new TextField();
		this.tf2 = new TextField();
		this.tf3 = new TextField();
		this.pathField = new TextField();
		this.b1 = new Button("Hinzufügen");
		this.b2 = new Button("Entfernen");
		this.b3 = new Button("Auslesen");
		
		this.buildBox();
	}

	private void buildBox() {
		this.setPrefSize(225, 400);
		this.tf1.setPromptText("Vorname");
		this.tf2.setPromptText("Nachname");
		this.tf3.setPromptText("Geburtstag");
		this.pathField.setPromptText("Datei Pfad");
		
		this.b1.setMaxWidth(Double.MAX_VALUE);
		this.b2.setMaxWidth(Double.MAX_VALUE);
		this.b3.setMaxWidth(Double.MAX_VALUE);
				
		VBox.setMargin(tf1, new Insets(10));
		VBox.setMargin(tf2, new Insets(10));
		VBox.setMargin(tf3, new Insets(10));
		VBox.setMargin(pathField, new Insets(10));
		VBox.setMargin(b1, new Insets(10));
		VBox.setMargin(b2, new Insets(10));
		VBox.setMargin(b3, new Insets(10));
		
		this.getChildren().addAll(tf1, tf2, tf3, b1, b2, pathField, b3);
		
		this.b1.setOnMouseClicked(e -> {
			if(this.tf1.getText() != "") {
				if(this.tf2.getText() != "") {
					if(this.tf3.getText() != "") {
						if(Utils.isValidDate(this.tf3.getText())) {
							String firstName = this.tf1.getText();
							String lastName = this.tf2.getText();
							String birthday = this.tf3.getText();
							
							NewWorker nw = new NewWorker(firstName, lastName, birthday);
							
							this.ptf.getWorkerManager().createWorker(nw);
							this.tf1.clear();
							this.tf2.clear();
							this.tf3.clear();
							this.tf1.requestFocus();
							
							this.ptf.getPtv().updateComplete();
						}
					}
				}
			}
		});
		
		this.b2.setOnMouseClicked(e -> {
			if(this.ptf.getPtv().getSelectionModel() != null) {
				if(this.ptf.getPtv().getSelectionModel().getSelectedItem() != null) {
					Worker w = this.ptf.getPtv().getSelectionModel().getSelectedItem();
					
					this.ptf.getWorkerManager().removeWorker(w, true);
					
					this.ptf.getDataBox().setCurrentSearchMatches();
					
					this.ptf.getPtv().updateComplete();
				}
			}
		});
		
		this.b3.setOnMouseClicked(e -> {
			if(this.pathField != null && this.pathField.getText() != "") {
				String path = this.pathField.getText();
				if(FileManager.exists(path)) {
					this.ptf.getWorkerManager().convertFileIntoData(path);
					this.pathField.clear();
					this.tf1.requestFocus();
					this.ptf.getPtv().updateComplete();
				}
			}
		});
	}	
}

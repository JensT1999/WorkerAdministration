package application.ptv;

import application.utils.Utils;
import application.utils.worker.Worker;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WorkerDataBox extends HBox {
	
	private PersonTableFrame ptf;
	
	private Worker w;
	
	private VBox vbox1;
	private Label l1;
	private Label l2;
	private Label l3;
	private Label l4;
		
	private VBox vbox2;
	private TextField tf1;
	private TextField tf2;
	private TextField tf3;
	private TextField tf4;

	private Button b1;
	
	public WorkerDataBox(PersonTableFrame ptf) {
		this.ptf = ptf;
		
		this.vbox1 = new VBox();
		this.l1 = new Label("Vorname/Nachname: ");
		this.l2 = new Label("Geburtstag (Alter): ");
		this.l3 = new Label("Plusstunden: ");
		this.l4 = new Label("Minusstunden: ");

		
		this.vbox2 = new VBox();
		this.tf1 = new TextField();
		this.tf2 = new TextField();
		this.tf3 = new TextField();
		this.tf4 = new TextField();		
		
		this.b1 = new Button("Speichern");
	}
	
	public void showBoxFor(Worker w) {
		this.w = w;
		this.ptf.setCenter(this);
		
		this.buildBox();
	}

	private void buildBox() {
		this.l1.setMaxWidth(Double.MAX_VALUE);
		this.tf1.setMaxWidth(150);
		
		this.l2.setMaxWidth(Double.MAX_VALUE);
		this.tf2.setMaxWidth(150);
		
		this.l3.setMaxWidth(Double.MAX_VALUE);
		this.tf3.setMaxWidth(150);
		
		this.l4.setMaxWidth(Double.MAX_VALUE);
		this.tf4.setMaxWidth(150);
		
		this.loadData();
		
		this.b1.setMaxWidth(150);
		
		VBox.setMargin(l1, new Insets(24));
		VBox.setMargin(tf1, new Insets(20));
		
		VBox.setMargin(l2, new Insets(24));
		VBox.setMargin(tf2, new Insets(20));

		VBox.setMargin(l3, new Insets(24));
		VBox.setMargin(tf3, new Insets(20));

		VBox.setMargin(l4, new Insets(24));
		VBox.setMargin(tf4, new Insets(20));
		
		VBox.setMargin(b1, new Insets(20));
		
		this.vbox1.getChildren().addAll(l1, l2, l3, l4);
		this.vbox2.getChildren().addAll(tf1, tf2, tf3, tf4, b1);
		
		this.getChildren().addAll(vbox1, vbox2);
		
		this.b1.requestFocus();
		
		this.b1.setOnMouseClicked(e -> {
			if(this.w != null) {
				if(this.tf1.getText() != "" && this.tf1 != null) {
					if(this.tf1.getText().contains(" ")) {
						String[] split = this.tf1.getText().split(" ");
						
						if(!split[0].equalsIgnoreCase(this.w.getFirstName())) {
							this.w.setFirstName(split[0]);
						}
						
						if(!split[1].equalsIgnoreCase(this.w.getLastName())) {
							this.w.setLastName(split[1]);
						}
					} else {
						if(!this.tf1.getText().equalsIgnoreCase(this.w.getFirstName())) {
							this.w.setFirstName(this.tf1.getText());
						}
					}
					this.tf1.clear();
				}
				
				if(this.tf2.getText() != "" && this.tf2 != null) {
					String bd = this.tf2.getText();
					if(Utils.isValidDate(bd)) {
						if(!bd.equalsIgnoreCase(this.w.getBirthDay().getDate())) {
							this.w.setBirthday(bd);
						}
						this.tf2.clear();
					}
				}
				
				if(this.tf3.getText() != "" && this.tf3 != null) {
					this.w.setPosHours(Double.valueOf(this.tf3.getText()));
					this.tf3.clear();
				}
				
				if(this.tf4.getText() != "" && this.tf4 != null) {
					this.w.setNegHours(Double.valueOf(this.tf4.getText()));
					this.tf4.clear();
				}
				
				this.w.saveFileData();
				this.loadData();
			}
		});
	}
	
	private void loadData() {
		this.tf1.setPromptText(this.w.getFirstName() + " " + this.w.getLastName());
		this.tf2.setPromptText(this.w.getBirthDay().getDate() + " (" + 
				String.valueOf(this.w.getAge()) + ")");
		this.tf3.setPromptText(String.valueOf(this.w.getPosHours()));
		this.tf4.setPromptText(String.valueOf(this.w.getNegHours()));
	}

}

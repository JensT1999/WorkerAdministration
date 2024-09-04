package application.ptv;

import application.utils.Person;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DataBox extends HBox {
	
	private PersonTableView ptv;
	
	private Label l1;
	private Label l2;
	private Label l3;
	private Label l4;
	private Label l5;
	
	public DataBox(PersonTableView tv) {
		this.ptv = tv;
		
		this.l1 = new Label();
		this.l2 = new Label();
		this.l3 = new Label();
		this.l4 = new Label();
		this.l5 = new Label();
		
		this.buildBox();
	}

	private void buildBox() {
		this.l1.setMaxWidth(Double.MAX_VALUE);
		this.l2.setMaxWidth(Double.MAX_VALUE);
		this.l3.setMaxWidth(Double.MAX_VALUE);
		this.l4.setMaxWidth(Double.MAX_VALUE);
		this.l5.setMaxWidth(Double.MAX_VALUE);
		
		HBox.setMargin(l1, new Insets(10));
		HBox.setMargin(l2, new Insets(10));
		HBox.setMargin(l3, new Insets(10));
		HBox.setMargin(l4, new Insets(10));
		HBox.setMargin(l5, new Insets(10));
		
		this.getChildren().addAll(l1, l2, l3, l4, l5);
	}
	
	public void setData(Person p) {
		if(p != null) {
			this.l1.setText(String.valueOf(p.getId()));
			this.l2.setText(p.getFirstName());
			this.l3.setText(p.getLastName());
			this.l4.setText(String.valueOf(p.getAge()));
		}
	}
	
	public void clearData() {
		if(this.l1.getText() != "" && this.l2.getText() != "" &&
				this.l3.getText() != "" && this.l4.getText() != "") {
			this.l1.setText("");
			this.l2.setText("");
			this.l3.setText("");
			this.l4.setText("");
		}
	}
	
	public void setCurrentSearchMatches() {
		this.l5.setText("Ergebnisse: " + this.ptv.
				getFrame().getPersonSearchBox().getCurrentSearchMatches());
	}	
}

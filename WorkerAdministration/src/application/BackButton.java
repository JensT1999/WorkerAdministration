package application;

import javafx.scene.control.Button;

public class BackButton extends Button {
	
	private FrameManager fm;
	
	public BackButton(FrameManager fm) {
		this.fm = fm;
		
		this.buildButton();
	}

	public void buildButton() {
		this.setText("Zurück");
		this.setPrefSize(75, 75);
		
		this.setOnMouseClicked(e -> this.buttonClicked());
	}

	private void buttonClicked() {
		if(this != null && this.fm != null) {
			this.fm.jumpBack();
		}
	}
}

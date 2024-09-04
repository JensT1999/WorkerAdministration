package application.ptv;

//import application.BackButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

public class BrowsePane extends TilePane {
	
	private BorderPane root;
	private PersonTableView ptv;
	
	private Button b1;
	private Button b2;
	private Button b3;
	private Button b4;
	
//	private BackButton bb;
	
	private BoxType lastBox;
	
	public BrowsePane(BorderPane r, PersonTableView ptv) {
		this.root = r;
		this.ptv = ptv;
		
		this.b1 = new Button(BoxType.INTERACTION_BOX.toString());
		this.b2 = new Button(BoxType.FILEMANAGER_BOX.toString());
		this.b3 = new Button(BoxType.NONE.toString());
		this.b4 = new Button(BoxType.NONE.toString());
		
//		this.bb = new BackButton(this);
		
		this.lastBox = BoxType.BROWSER;
		
		this.buildPane();
	}

	private void buildPane() {
		this.setPrefSize(225, 400);
		this.b1.setPrefSize(80, 80);
		this.b2.setPrefSize(80, 80);
		this.b3.setPrefSize(80, 80);
		this.b4.setPrefSize(80, 80);
		
		TilePane.setMargin(b1, new Insets(10));
		TilePane.setMargin(b2, new Insets(10));
		TilePane.setMargin(b3, new Insets(10));
		TilePane.setMargin(b4, new Insets(10));
		
//		HBox.setMargin(bb, new Insets(10));
		
		this.setAlignment(Pos.CENTER);
		
//		this.ptv.getFrame().getDataBox().getChildren().add(bb);
		this.getChildren().addAll(b1, b2, b3, b4);
		
		this.b1.setOnMouseClicked(e -> this.buttonClicked(BoxType.INTERACTION_BOX));

		this.b2.setOnMouseClicked(e -> this.buttonClicked(BoxType.FILEMANAGER_BOX));
		
		this.b3.setOnMouseClicked(e -> this.buttonClicked(BoxType.NONE));
		
		this.b4.setOnMouseClicked(e -> this.buttonClicked(BoxType.NONE));
	}
	
	private void buttonClicked(BoxType type) {
		if(type != null) {
			switch (type) {
				case BoxType.INTERACTION_BOX -> {
					if(this.root != null && this.ptv != null &&
							this.ptv.getFrame().getInteractionBox() != null) {
						this.root.setRight(this.ptv.getFrame().getInteractionBox());
//						this.bb.setLastBox(BoxType.BROWSER);
					}
				}
				case BoxType.FILEMANAGER_BOX -> {
					if(this.root != null && this.ptv != null &&
							this.ptv.getFrame().getFileManagerBox() != null) {
						this.root.setRight(this.ptv.getFrame().getFileManagerBox());
//						this.bb.setLastBox(BoxType.BROWSER);
					}
				}
				case BoxType.NONE -> System.out.println("Dies ist ein Platzhalter");
			}
		}
	}
	
	public PersonTableView getPTV() {
		return ptv;
	}
	
	public BorderPane getRoot() {
		return root;
	}
	
	enum BoxType {
		INTERACTION_BOX("Personenverwaltung"),
		FILEMANAGER_BOX("Datei-Manager"),
		BROWSER("Browser"),
		NONE("Platzhalter");
		
		private String name;
		
		private BoxType(String n) {
			this.name = n;
		}
		
		public String toString() {
			return name;
		}
	}
}

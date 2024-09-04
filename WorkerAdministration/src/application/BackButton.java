//package application;
//
//import application.ptv.BrowsePane;
//import javafx.scene.control.Button;
//
//public class BackButton extends Button {
//	
//	private BrowsePane bp;
//	private BrowsePane.BoxType lastBox;
//	
//	public BackButton(BrowsePane p) {
//		this.bp = p;
//		this.lastBox = BrowsePane.BoxType.NONE;
//		
//		this.buildButton();
//	}
//
//	public void buildButton() {
//		this.setText("Zurück");
//		this.setPrefSize(75, 75);
//		
//		this.setOnMouseClicked(e -> this.buttonClicked());
//	}
//
//	private void buttonClicked() {
//		if(this != null && this.lastBox != null) {
//			switch (this.lastBox) {
//				case BrowsePane.BoxType.INTERACTION_BOX -> {
//					if(this.bp != null) {
//						this.bp.getRoot().setRight(this.bp.getPTV().getInteractionBox());
//						this.lastBox = BrowsePane.BoxType.NONE;
//					}
//				}
//				case BrowsePane.BoxType.FILEMANAGER_BOX -> {
//					if(this.bp != null) {
//						this.bp.getRoot().setRight(this.bp.getPTV().getFileManagerBox());
//						this.lastBox = BrowsePane.BoxType.NONE;
//					}
//				}
//				case BrowsePane.BoxType.BROWSER -> {
//					if(this.bp != null) {
//						this.bp.getRoot().setRight(this.bp);
//						this.lastBox = BrowsePane.BoxType.NONE;
//					}
//				}
//				case BrowsePane.BoxType.NONE -> System.out.println("Kein zurück möglich!");
//			}
//		}
//	}
//	
//	public void setLastBox(BrowsePane.BoxType type) {
//		this.lastBox = type;
//	}
//	
//	public BrowsePane.BoxType getLastBox() {
//		return lastBox;
//	}
//
//}

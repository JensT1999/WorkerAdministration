package application;

import application.mf.MainFrame;
import application.ptv.PersonTableFrame;
import application.utils.FrameType;
import javafx.scene.Scene;

public class FrameManager {
		
	private MainFrame mf;
	private PersonTableFrame ptf;
	
	private BackButton bb;
	
	private FrameType lf; // last frame
	private FrameType cf; // current frame
	
	private Scene scene;
	
	public FrameManager(double width, double height) {
		this.mf = new MainFrame();
		this.ptf = new PersonTableFrame(this);
		
		this.bb = new BackButton(this);
		
		this.lf = FrameType.NONE;
		this.cf = FrameType.MAIN_FRAME;
		
		this.scene = new Scene(this.ptf, width, height);
	}
	
	public void switchTo(FrameType type) {
		if(type != null && this.cf != null && this.lf != null && this.scene != null) {
			this.lf = this.cf;
			this.cf = type;
			
			switch (this.cf) {
				case FrameType.MAIN_FRAME -> {
					this.scene.setRoot(this.mf);
				}
				case FrameType.PTV_FRAME -> {
					this.scene.setRoot(this.ptf);
				}
				case FrameType.WPC_FRAME -> {
					
				}
				case FrameType.NONE -> System.out.println("Dies ist immernoch ein Platzhalter!");
			}
		}		
	}
	
	public void jumpBack() {
		if(this.cf != null && this.lf != null && this.scene != null) {
			if(this.lf != FrameType.NONE) {
				this.cf = this.lf;
				this.lf = FrameType.NONE;
				
				switch (this.cf) {
					case FrameType.MAIN_FRAME -> {
						this.scene.setRoot(this.mf);
					}
					case FrameType.PTV_FRAME -> {
						this.scene.setRoot(this.ptf);
					}
					case FrameType.WPC_FRAME -> {
						
					}
					case FrameType.NONE -> System.out.println("Immernoch ein Platzhalter!!");
				}
			}
		}
	}
	
	public Scene getScene() {
		return scene;
	}
}

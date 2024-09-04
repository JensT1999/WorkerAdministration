package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	
	private FrameManager fm;
	private Scene scene;
	
	@Override
	public void init() throws Exception {
		this.fm = new FrameManager(900, 500);
		this.scene = this.fm.getScene();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {			
			primaryStage.setScene(this.scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

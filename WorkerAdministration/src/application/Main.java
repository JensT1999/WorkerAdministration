package application;
	
import application.ptv.BrowsePane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	private PersonTableFrame ptf;
	
	private BrowsePane browser;
	private Scene scene;
	
	@Override
	public void init() throws Exception {
		this.ptf = new PersonTableFrame(null);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.scene = new Scene(this.ptf, 900, 500);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

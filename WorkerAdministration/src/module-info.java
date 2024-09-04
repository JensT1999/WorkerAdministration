module WorkerAdministration {
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens application.utils to javafx.graphics, javafx.fxml, javafx.base;
	opens application.ptv to javafx.graphics, javafx.fxml, javafx.base;

}

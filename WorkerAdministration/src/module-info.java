module WorkerAdministration {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens application.utils to javafx.graphics, javafx.fxml, javafx.base;
	opens application.ptv to javafx.graphics, javafx.fxml, javafx.base;
	opens application.mf to javafx.graphics, javafx.fxml, javafx.base;
	opens application.wpc to javafx.graphics, javafx.fxml, javafx.base;

}

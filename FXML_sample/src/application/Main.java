package application;
	
import controller.AlgorithmController;
import controller.LandingPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/landingPage.fxml"));
	    Parent root = loader.load();

	    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/sample.fxml"));
	    Parent algorithmRoot = loader2.load(); // Bắt buộc phải load để gắn controller

	    AlgorithmController algorithmController = loader2.getController();

	    // Truyền stage và controller
	    LandingPageController controller = loader.getController();
	    controller.setSamplePage(algorithmController);
	    controller.setPrimaryStage(primaryStage);

	    Scene scene = new Scene(root, 800, 600);
	    primaryStage.setTitle("CPU Scheduling Simulator");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}

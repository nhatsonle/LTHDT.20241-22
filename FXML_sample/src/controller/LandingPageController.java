package controller;

import javafx.stage.Stage;
import model.CPUAlgorithm;
import model.FCFS;
import model.RoundRobin;
import model.SJN;
import view.HelpView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LandingPageController {
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    @FXML
    public void loadAlgorithmPage(String algorithmName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainPage.fxml"));
            Parent root = loader.load();

            // Lấy controller của sample (AlgorithmController)
            AlgorithmController algorithmController = loader.getController();
            
            // Truyền tên của thuận toán 
            algorithmController.setTitle(algorithmName);
            algorithmController.setPrimaryStage(this.primaryStage);
            // Hiển thị giao diện
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Xử lý khi chọn thuật toán 
    @FXML
    private MenuItem fcfsMenu;
    @FXML
    private MenuItem sjnMenu;
    @FXML
    private MenuItem rrMenu;
    @FXML
    public void initialize() {
        fcfsMenu.setOnAction(event -> {
            loadAlgorithmPage("FCFS");
        });

        sjnMenu.setOnAction(event -> {
            loadAlgorithmPage("SJN");
        });

        rrMenu.setOnAction(event -> {
            loadAlgorithmPage("Round Robin");
        });
    }
    
    @FXML
    public void handleHelp() {
    	HelpView helpView = new HelpView();
    	helpView.showHelp();
    }
    
    @FXML
    public void handleExit() {
        if (primaryStage != null) {
            primaryStage.close();  
        }
        System.exit(0); 
    }

}

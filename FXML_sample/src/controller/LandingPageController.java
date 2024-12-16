package controller;

import javafx.stage.Stage;
import model.CPUAlgorithm;
import model.FCFS;
import model.RoundRobin;
import model.SJN;
import model.SharedData;
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
	private CPUAlgorithm fcfs = new FCFS();
	private CPUAlgorithm sjn = new SJN();
	private CPUAlgorithm roundrobin = new RoundRobin();
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void loadSamplePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sample.fxml"));
            Parent root = loader.load();
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
            SharedData.setCurrentAlgorithm(new FCFS());
            loadSamplePage();
        });

        sjnMenu.setOnAction(event -> {
            SharedData.setCurrentAlgorithm(new SJN());
            loadSamplePage();
        });

        rrMenu.setOnAction(event -> {
            SharedData.setCurrentAlgorithm(new RoundRobin());
            loadSamplePage();
        });
    }
    
    @FXML 
    public void handleClick() {
    	loadSamplePage();
    }
    
    @FXML
    public void handleHelp() {
        // Tạo cửa sổ mới cho hướng dẫn
        Stage helpStage = new Stage();
        helpStage.setTitle("Hướng dẫn Thuật toán");

        // Sử dụng HBox để chia cửa sổ thành 3 cột cho 3 thuật toán
        HBox hbox = new HBox(20);  // Khoảng cách giữa các cột
        hbox.setPadding(new Insets(20));

        // Tạo các VBox cho từng thuật toán
        VBox fcfsVBox = new VBox(10);
        VBox sjfVBox = new VBox(10);
        VBox rrVBox = new VBox(10);

        // Tạo các Label cho tên và mô tả của từng thuật toán
        Label fcfsTitle = new Label("FCFS (First-Come-First-Served)");
        fcfsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label fcfsDescription = new Label(fcfs.displayHelp());
        fcfsDescription.setWrapText(true);  // Cho phép wrap text

        Label sjfTitle = new Label("SJN (Shortest Job Next)");
        sjfTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label sjfDescription = new Label(sjn.displayHelp());
        sjfDescription.setWrapText(true);

        Label rrTitle = new Label("Round Robin (RR)");
        rrTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label rrDescription = new Label(roundrobin.displayHelp());
        rrDescription.setWrapText(true);

        // Thêm tiêu đề và mô tả vào các VBox
        fcfsVBox.getChildren().addAll(fcfsTitle, fcfsDescription);
        sjfVBox.getChildren().addAll(sjfTitle, sjfDescription);
        rrVBox.getChildren().addAll(rrTitle, rrDescription);

        // Thêm các VBox vào HBox để tạo 3 cột
        hbox.getChildren().addAll(fcfsVBox, sjfVBox, rrVBox);

        // Tạo nút "Thoát" để đóng cửa sổ hướng dẫn
        Button closeButton = new Button("Thoát");
        closeButton.setOnAction(e -> helpStage.close());

        // Thêm nút "Thoát" vào VBox chính
        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(20));
        mainVBox.getChildren().addAll(hbox, closeButton);

        // Tạo Scene cho cửa sổ và hiển thị
        Scene helpScene = new Scene(mainVBox, 900, 600); // Điều chỉnh kích thước tùy ý
        helpStage.setScene(helpScene);
        helpStage.show();
    }
    
    @FXML
    public void handleExit() {
        if (primaryStage != null) {
            primaryStage.close();  
        }
        System.exit(0); 
    }

}

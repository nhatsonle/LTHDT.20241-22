package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;
import model.*;
import model.Process;
public class AlgorithmController implements Initializable {
	

    @FXML
    private Label algorithmLabel;  // Label để hiển thị tên thuật toán
   
    @FXML
    private TableColumn<Process, Integer> colID;

    @FXML
    private TableColumn<Process, Integer> colArrival;

    @FXML
    private TableColumn<Process, Integer> colBurst;

    @FXML
    private TableColumn<Process, Integer> colTurnaround;

    @FXML
    private TableColumn<Process, Integer> colWaiting;

    @FXML
    private TableView<Process> tblOutput;
    
    @FXML
    private Label avgTurnAroundTime;

    @FXML
    private Label avgWaitTime;
    
    @FXML
    private MenuItem helpMenuItem;
    
    @FXML
    void backToMainPage(ActionEvent event) {
    	new Main();       
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Lấy thuật toán hiện tại từ SharedData và cập nhật label
        Object algorithm = SharedData.getCurrentAlgorithm();
        if (algorithm != null) {
            algorithmLabel.setText("Thuật toán: " + ((CPUAlgorithm) algorithm).getName());
        } else {
            algorithmLabel.setText("Chưa chọn thuật toán!");
        }
        
        colID.setCellValueFactory(new PropertyValueFactory<Process, Integer>("id"));
        colArrival.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
        colBurst.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
        colTurnaround.setCellValueFactory(new PropertyValueFactory<Process, Integer>("turnaroundTime"));
        colWaiting.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitingTime"));
        
        // Set alignment of columns
        colID.setStyle("-fx-alignment: CENTER;");
        colArrival.setStyle("-fx-alignment: CENTER;");
        colBurst.setStyle("-fx-alignment: CENTER;");
        colTurnaround.setStyle("-fx-alignment: CENTER;");
        colWaiting.setStyle("-fx-alignment: CENTER;");
       
        tblOutput.setItems(SharedData.getProcesses());
        
        avgTurnAroundTime.setText(Double.toString(SharedData.getTRT()) + " s");
        avgWaitTime.setText(Double.toString(SharedData.getWT()) + " s");
        
    }
    @FXML
    void handleHelp(ActionEvent event) {

    }
    

}

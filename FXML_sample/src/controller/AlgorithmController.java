package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.CPUAlgorithm;
import model.SharedData;


public class AlgorithmController {
	@FXML
    private Button runButton;

    @FXML
    private Label algorithmLabel;  // Label để hiển thị tên thuật toán


    @FXML
    public void initialize() {
        // Lấy thuật toán hiện tại từ SharedData và cập nhật label
        Object algorithm = SharedData.getCurrentAlgorithm();
        if (algorithm != null) {
            algorithmLabel.setText("Thuật toán: " + ((CPUAlgorithm) algorithm).getName());
        } else {
            algorithmLabel.setText("Chưa chọn thuật toán!");
        }
    }

//    @FXML
//    public void onRunButtonClick() {
//        Object algorithm = SharedData.getCurrentAlgorithm();
//        if (algorithm != null) {
//            handler.runAlgorithm(algorithm);  // Chạy thuật toán đã chọn
//        } else {
//            System.out.println("Chưa chọn thuật toán nào!");
//        }
//    }
}

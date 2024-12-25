package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.*;

public class HelpView {
	private CPUAlgorithm fcfs = new FCFS();
	private CPUAlgorithm sjn = new SJN();
	private CPUAlgorithm roundrobin = new RoundRobin();
    public void showHelp() {
    	// Xuất hiện cửa sổ help
        Stage helpStage = new Stage();
        helpStage.setTitle("Hướng dẫn Thuật toán");
        
        HBox hbox = new HBox(20);  
        hbox.setPadding(new Insets(20));

        VBox fcfsVBox = new VBox(10);
        VBox sjfVBox = new VBox(10);
        VBox rrVBox = new VBox(10);

        Label fcfsTitle = new Label("FCFS (First-Come-First-Served)");
        fcfsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label fcfsDescription = new Label(fcfs.displayHelp());
        fcfsDescription.setWrapText(true);  

        Label sjfTitle = new Label("SJN (Shortest Job Next)");
        sjfTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label sjfDescription = new Label(sjn.displayHelp());
        sjfDescription.setWrapText(true);

        Label rrTitle = new Label("Round Robin (RR)");
        rrTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label rrDescription = new Label(roundrobin.displayHelp());
        rrDescription.setWrapText(true);

        fcfsVBox.getChildren().addAll(fcfsTitle, fcfsDescription);
        sjfVBox.getChildren().addAll(sjfTitle, sjfDescription);
        rrVBox.getChildren().addAll(rrTitle, rrDescription);

        hbox.getChildren().addAll(fcfsVBox, sjfVBox, rrVBox);

        Button closeButton = new Button("Thoát");
        closeButton.setOnAction(e -> helpStage.close());

        VBox mainVBox = new VBox(10);
        mainVBox.setPadding(new Insets(20));
        mainVBox.getChildren().addAll(hbox, closeButton);

        Scene helpScene = new Scene(mainVBox, 900, 600); 
        helpStage.setScene(helpScene);
        helpStage.show();
    }
}
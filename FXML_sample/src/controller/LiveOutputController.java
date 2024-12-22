package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Process;

public class LiveOutputController implements Initializable {
	private TableProcessController tableProcess = new TableProcessController(); 
	@FXML
	private TableView<Process> table;
	@FXML
	private TableColumn<Process, Integer> idColumn;
	@FXML
	private TableColumn<Process, Integer> arrivalTimeColumn;
	@FXML
	private TableColumn<Process, Integer> burstTimeColumn;
	@FXML
	private TableColumn<Process, Integer> waitingTimeColumn;
	@FXML
	private TableColumn<Process, Integer> turnAroundTimeColumn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			idColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("id"));
			arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
			burstTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
			waitingTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitingTime"));
			turnAroundTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("turnaroundTime"));
			
	}
	
	public void updateTable(ObservableList<Process> newProcesses) {
		idColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("id"));
		arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
		burstTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
		waitingTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitingTime"));
		turnAroundTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("turnaroundTime"));
		table.setItems(newProcesses);
    }
}

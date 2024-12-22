package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CPUAlgorithm;
import model.FCFS;
import model.Process;
import model.RoundRobin;
import model.SJN;
import model.SharedData;

public class TableProcessController implements Initializable {
	@FXML
	private TableView<Process> table;
	@FXML
	private TableColumn<Process, Integer> idColumn;
	@FXML
	private TableColumn<Process, Integer> arrivalTimeColumn;
	@FXML
	private TableColumn<Process, Integer> burstTimeColumn;
	@FXML
	private TableColumn<Process, Integer> priorityColumn;
	@FXML
	private TextField idText;
	@FXML
	private TextField arrivalTimeText;
	@FXML
	private TextField burstTimeText;
	@FXML
	private TextField priorityText;
	@FXML
	private TextField timeQuantumText;
	@FXML
	private Button proceed;
	
	@FXML
    private Button runButton;
	
	
	private ObservableList<Process> processList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		processList = FXCollections.observableArrayList();
		idColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("id"));
		arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
		burstTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
		priorityColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));
		
		 // Set alignment of columns
        idColumn.setStyle("-fx-alignment: CENTER;");
        arrivalTimeColumn.setStyle("-fx-alignment: CENTER;");
        burstTimeColumn.setStyle("-fx-alignment: CENTER;");
        priorityColumn.setStyle("-fx-alignment: CENTER;");
		table.setItems(processList);
	}
	
	// Thêm process
	public void addProcess(ActionEvent e) {
		Process newProcess = new Process();
		try {
			newProcess.setId(Integer.parseInt(idText.getText()));
			newProcess.setArrivalTime(Integer.parseInt(arrivalTimeText.getText()));
			newProcess.setBurstTime(Integer.parseInt(burstTimeText.getText()));
			newProcess.setPriority(Integer.parseInt(priorityText.getText()));
			
			if(processList.contains(newProcess)) {
				// Id trùng nhau
				Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText("ID bị trùng lặp");
	            alert.setContentText("ID " + newProcess.getId() + " đã tồn tại trong danh sách!");
	            alert.showAndWait();
			} else {
				processList.add(newProcess);
			}
			// Xóa dữ liệu trong các trường nhập sau khi thêm
	        idText.clear();
	        arrivalTimeText.clear();
	        burstTimeText.clear();
	        priorityText.clear();
		} catch (NumberFormatException ex) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi nhập liệu");
	        alert.setHeaderText("Dữ liệu không hợp lệ");
	        alert.setContentText("Vui lòng nhập số nguyên hợp lệ cho tất cả các trường!");
	        alert.showAndWait();
		}
	}
	
	// Xóa process
	public void removeProcess(ActionEvent e) {
	    try {
	        // Lấy process được chọn trong TableView
	        Process removedProcess = table.getSelectionModel().getSelectedItem();

	        if (removedProcess != null) {
	            processList.remove(removedProcess);
	        } else {
	            // Hiển thị cảnh báo nếu không có process nào được chọn
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText("Không có mục nào được chọn");
	            alert.setContentText("Vui lòng chọn một mục trong bảng để xóa!");
	            alert.showAndWait();
	        }
	    } catch (Exception ex) {
	        // Hiển thị lỗi nếu xảy ra ngoại lệ
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText("Đã xảy ra lỗi khi xóa");
	        alert.setContentText("Không thể xóa mục được chọn. Vui lòng thử lại.");
	        alert.showAndWait();
	    }
	}
	
	public void editProcess(ActionEvent e) {
	    try {
	        // Lấy process được chọn trong TableView
	        Process selectedProcess = table.getSelectionModel().getSelectedItem();

	        if (selectedProcess != null) {
	            // Đặt giá trị cho các trường nhập dựa trên thông tin của process đã chọn
	            idText.setText(String.valueOf(selectedProcess.getId()));
	            arrivalTimeText.setText(String.valueOf(selectedProcess.getArrivalTime()));
	            burstTimeText.setText(String.valueOf(selectedProcess.getBurstTime()));
	            priorityText.setText(String.valueOf(selectedProcess.getPriority()));
	            idText.setEditable(false);
	            proceed.setVisible(true);
	        } else {
	            // Hiển thị cảnh báo nếu không có mục nào được chọn
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText("Không có mục nào được chọn");
	            alert.setContentText("Vui lòng chọn một mục trong bảng để chỉnh sửa!");
	            alert.showAndWait();
	        }
	    } catch (Exception ex) {
	        // Hiển thị lỗi nếu có bất kỳ ngoại lệ nào
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText("Đã xảy ra lỗi khi chỉnh sửa");
	        alert.setContentText("Không thể chỉnh sửa mục được chọn. Vui lòng thử lại.");
	        alert.showAndWait();
	    }
	}

	public void updateProcess(ActionEvent e) {
	    try {
	        // Lấy process được chọn trong TableView
	        Process selectedProcess = table.getSelectionModel().getSelectedItem();

	        if (selectedProcess != null) {
	            // Cập nhật thông tin của process đã chọn với dữ liệu từ các trường nhập
	            selectedProcess.setId(Integer.parseInt(idText.getText()));
	            selectedProcess.setArrivalTime(Integer.parseInt(arrivalTimeText.getText()));
	            selectedProcess.setBurstTime(Integer.parseInt(burstTimeText.getText()));
	            selectedProcess.setPriority(Integer.parseInt(priorityText.getText()));
	            
	            // Cập nhật lại bảng (TableView)
	            table.refresh();

	            // Xóa dữ liệu trong các trường nhập sau khi cập nhật
	            idText.clear();
	            arrivalTimeText.clear();
	            burstTimeText.clear();
	            priorityText.clear();
	            proceed.setVisible(false);
	            idText.setEditable(true);
	        } else {
	            // Hiển thị cảnh báo nếu không có mục nào được chọn
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Cảnh báo");
	            alert.setHeaderText("Không có mục nào được chọn");
	            alert.setContentText("Vui lòng chọn một mục trong bảng để cập nhật!");
	            alert.showAndWait();
	        }
	    } catch (Exception ex) {
	        // Hiển thị lỗi nếu có bất kỳ ngoại lệ nào
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText("Đã xảy ra lỗi khi cập nhật");
	        alert.setContentText("Không thể cập nhật mục được chọn. Vui lòng thử lại.");
	        alert.showAndWait();
	    }
	}
	
	public void clearProcessList(ActionEvent e) {
	    try {
	        // Xóa tất cả các process trong danh sách
	        processList.clear();
	        
	        // Nếu có bảng dữ liệu (table) cần cập nhật lại, có thể thêm dòng sau
	        table.setItems(processList);
	        
	        // Hiển thị thông báo khi danh sách đã được xóa
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Thông báo");
	        alert.setHeaderText("Danh sách tiến trình đã được xóa");
	        alert.setContentText("Tất cả các process đã được xóa khỏi danh sách.");
	        alert.showAndWait();
	        
	    } catch (Exception ex) {
	        // Xử lý lỗi nếu có
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Lỗi");
	        alert.setHeaderText("Có lỗi khi xóa dữ liệu");
	        alert.setContentText("Vui lòng thử lại.");
	        alert.showAndWait();
	    }
	}
	
	@FXML
    void runSimulation(ActionEvent event) {
		SharedData.clearProcesses();
		Object algo = SharedData.getCurrentAlgorithm();
		String algoName = ((CPUAlgorithm)algo).getName();
		
		switch(algoName) {
		case "FCFS":
			runFCFS();
			break;
		case "SJN":
			runSJN();
			break;
		case "Round Robin":
			runRR();
			break;
		default:
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Thông báo");
	        alert.setHeaderText("Tên thuật toán không hợp lệ");
	        alert.setContentText("Vui lòng lựa chọn lại thuật toán");
	        alert.showAndWait();
		}
    }

	void runFCFS()
	{
		FCFS fcfs = new FCFS();
		fcfs.schedule(processList);
		for(Process p : processList) {
			SharedData.addProcess(p);
		}
		SharedData.setWT(fcfs.getAvgWaitingTime());
		SharedData.setTRT(fcfs.getAvgTurnAroundTime());
	}

	void runSJN()
	{
		SJN sjn = new SJN();
		sjn.schedule(processList);
		for(Process p : processList) {
			SharedData.addProcess(p);
		}
		SharedData.setWT(sjn.getAvgWaitingTime());
		SharedData.setTRT(sjn.getAvgTurnAroundTime());
	}
	
	void runRR()
	{	
		RoundRobin rr;
		if(timeQuantumText.getText() != null && !timeQuantumText.getText().isEmpty()) {
			rr = new RoundRobin(Integer.parseInt(timeQuantumText.getText()));
		} else {
			rr = new RoundRobin();
		}
		rr.schedule(processList);
		for(Process p : processList) {
			SharedData.addProcess(p);
		}
		SharedData.setWT(rr.getAvgWaitingTime());
		SharedData.setTRT(rr.getAvgTurnAroundTime());
	}

}
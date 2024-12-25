package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.*;
import model.*;
import model.Process;
import view.HelpView;
public class AlgorithmController implements Initializable {
	private String nameAlgorithm;
	@FXML
    private Label algorithmLabel;  // Label để hiển thị tên thuật toán
	
	// Table view process
	@FXML
	private TableView<Process> tableProcesses;
	@FXML
	private TableColumn<Process, Integer> idColumn;
	@FXML
	private TableColumn<Process, Integer> arrivalTimeColumn;
	@FXML
	private TableColumn<Process, Integer> burstTimeColumn;
	@FXML
	private TableColumn<Process, Integer> priorityColumn;
	// Field update table view process
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
	private Button updateEdit;
	@FXML
    private Button runButton;
	
	// Gantt chart
	@FXML
    private Canvas ganttCanvas;
    @FXML
    private Label timeLabel;
    private double currentTime = 0;
	
	// Tạo danh sách process
	private ObservableList<Process> processList = FXCollections.observableArrayList();
	
	// Table live output
	@FXML
    private TableView<Process> tblOutput;
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
  
    // Table Scheduling Metrics
    @FXML
    private Label avgTurnAroundTime;
    @FXML
    private Label avgWaitTime;
    @FXML
    private Label cpuUtilization;
    
	private Stage stage;
	// Set the primaryStage in the controller
    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }
    
    // Back to main page
    @FXML
    void backToMainPage() throws IOException {
    	if (this.stage == null) {
            throw new IllegalStateException("Stage is not initialized");
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/landingPage.fxml"));
        Parent root = loader.load();
        
        LandingPageController controller = loader.getController();
        controller.setPrimaryStage(stage);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);        
    }
    
    // Show help
    @FXML
    void handleHelp() {
    	HelpView helpView = new HelpView();
    	helpView.showHelp();
    }
    
    // Set title cho trang algorithm
    void setTitle(String name) {
    	this.nameAlgorithm = name;
    	if (algorithmLabel != null) {
            algorithmLabel.setText("Thuật toán: " + name);
        } else {
            System.out.println("algorithmLabel chưa được khởi tạo!");
        }
    }
    
    
    // Init cho trang 
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	// Tạo danh sách mới processes
    	processList.add(new Process(1, 0, 5, 1));
    	processList.add(new Process(2, 6, 3, 2));
    	processList.add(new Process(3, 8, 2, 3));
    	// Init table processes 
		idColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("id"));
		arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
		burstTimeColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
		priorityColumn.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));
		
		 // Set alignment of columns
        idColumn.setStyle("-fx-alignment: CENTER;");
        arrivalTimeColumn.setStyle("-fx-alignment: CENTER;");
        burstTimeColumn.setStyle("-fx-alignment: CENTER;");
        priorityColumn.setStyle("-fx-alignment: CENTER;");
		tableProcesses.setItems(processList);
		updateEdit.setVisible(false);
		
   
        // Table live output
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
        tblOutput.setItems(processList);
        
        // Bảng đánh giá CPU
        avgTurnAroundTime.setText("0 s");
        avgWaitTime.setText("0 s");
        cpuUtilization.setText("0 %");
    }
    
    
    // Thêm process
 	public void addProcess(ActionEvent e) {
 		Process newProcess = new Process();
 		try {
 			int newId = Integer.parseInt(idText.getText());
 	        int newArrivalTime = Integer.parseInt(arrivalTimeText.getText());
 	        int newBurstTime = Integer.parseInt(burstTimeText.getText());
 	        int newPriority = Integer.parseInt(priorityText.getText());
 			
 			if (newId < 0 || newArrivalTime < 0 || newBurstTime < 0 || newPriority < 0) {
 	            Alert alert = new Alert(Alert.AlertType.WARNING);
 	            alert.setTitle("Cảnh báo");
 	            alert.setHeaderText("Dữ liệu không hợp lệ");
 	            alert.setContentText("Không được nhập giá trị âm cho các trường dữ liệu!");
 	            alert.showAndWait();
 	            return;
 	        }
 			
 			newProcess.setId(newId);
 	        newProcess.setArrivalTime(newArrivalTime);
 	        newProcess.setBurstTime(newBurstTime);
 	        newProcess.setPriority(newPriority);
 			
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
 			// Lỗi nhập không phải số
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
 	        Process removedProcess = tableProcesses.getSelectionModel().getSelectedItem();
 	        if (removedProcess != null) {
 	            processList.remove(removedProcess);
 	        } else {
 	            // Hiện thị khi chưa chọn process nào
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
 	        // Lấy process được chọn trong bảng
 	        Process selectedProcess = tableProcesses.getSelectionModel().getSelectedItem();

 	        if (selectedProcess != null) {
 	            // Đặt dữ liệu vào cho các trường nhập
 	            idText.setText(String.valueOf(selectedProcess.getId()));
 	            arrivalTimeText.setText(String.valueOf(selectedProcess.getArrivalTime()));
 	            burstTimeText.setText(String.valueOf(selectedProcess.getBurstTime()));
 	            priorityText.setText(String.valueOf(selectedProcess.getPriority()));
 	            idText.setEditable(false); // Không cho sửa id
 	            updateEdit.setVisible(true); // Hiện thị button update sau khi edit
 	        } else {
 	            // Hiển thị cảnh báo nếu không có mục nào được chọn
 	            Alert alert = new Alert(Alert.AlertType.WARNING);
 	            alert.setTitle("Cảnh báo");
 	            alert.setHeaderText("Không có mục nào được chọn");
 	            alert.setContentText("Vui lòng chọn một mục trong bảng để chỉnh sửa!");
 	            alert.showAndWait();
 	        }
 	    } catch (Exception ex) {
 	        // Hiển thị lỗi nếu có trường hợp khác
 	        Alert alert = new Alert(Alert.AlertType.ERROR);
 	        alert.setTitle("Lỗi");
 	        alert.setHeaderText("Đã xảy ra lỗi khi chỉnh sửa");
 	        alert.setContentText("Không thể chỉnh sửa mục được chọn. Vui lòng thử lại.");
 	        alert.showAndWait();
 	    }
 	}

 	public void updateProcess(ActionEvent e) {
 		// Update sau khi edit 1 process được chọn
 	    try {
 	        // Lấy process được chọn trong TableView
 	        Process selectedProcess = tableProcesses.getSelectionModel().getSelectedItem();
 	        int newId = Integer.parseInt(idText.getText());
	        int newArrivalTime = Integer.parseInt(arrivalTimeText.getText());
	        int newBurstTime = Integer.parseInt(burstTimeText.getText());
	        int newPriority = Integer.parseInt(priorityText.getText());
	        if (newId < 0 || newArrivalTime < 0 || newBurstTime < 0 || newPriority < 0) {
 	            Alert alert = new Alert(Alert.AlertType.WARNING);
 	            alert.setTitle("Cảnh báo");
 	            alert.setHeaderText("Dữ liệu không hợp lệ");
 	            alert.setContentText("Không được nhập giá trị âm cho các trường dữ liệu!");
 	            alert.showAndWait();
 	            idText.clear();
	            arrivalTimeText.clear();
	            burstTimeText.clear();
	            priorityText.clear();
	            updateEdit.setVisible(false); // Ẩn button update edit
	            idText.setEditable(true); // set lại idText về mặc định
 	            return;
 	        }
	        else if (selectedProcess != null) {
 	            // Update lại process đã edit
 	            selectedProcess.setId(Integer.parseInt(idText.getText()));
 	            selectedProcess.setArrivalTime(Integer.parseInt(arrivalTimeText.getText()));
 	            selectedProcess.setBurstTime(Integer.parseInt(burstTimeText.getText()));
 	            selectedProcess.setPriority(Integer.parseInt(priorityText.getText()));
 	            
 	            // refresh lại bảng
 	            tableProcesses.refresh();
 	            
 	            //clear các trường nhập
 	            idText.clear();
 	            arrivalTimeText.clear();
 	            burstTimeText.clear();
 	            priorityText.clear();
 	            updateEdit.setVisible(false); // Ẩn button update edit
 	            idText.setEditable(true); // set lại idText về mặc định
 	        } else {
 	            // Hiển thị cảnh báo nếu không có mục nào được chọn
 	            Alert alert = new Alert(Alert.AlertType.WARNING);
 	            alert.setTitle("Cảnh báo");
 	            alert.setHeaderText("Không có mục nào được chọn");
 	            alert.setContentText("Vui lòng chọn một mục trong bảng để cập nhật!");
 	            alert.showAndWait();
 	        }
 	    } catch (Exception ex) {
 	    	// Hiển thị lỗi nếu có trường hợp khác
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
 	        
 	        // Cập nhật lại table process
 	        tableProcesses.setItems(processList);
 	        
 	        // Hiện thị thông báo đã xóa thành công
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
     void runSimulation(ActionEvent event) throws IOException {
 		if (processList == null || processList.isEmpty()) {
 	        // Lỗi nếu process list trống 
 	        Alert alert = new Alert(Alert.AlertType.WARNING);
 	        alert.setTitle("Cảnh báo");
 	        alert.setHeaderText("Danh sách tiến trình trống");
 	        alert.setContentText("Vui lòng thêm ít nhất một tiến trình trước khi chạy mô phỏng.");
 	        alert.showAndWait();
 	        return; 
 	    }
		switch(this.nameAlgorithm) {
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
 		CPUAlgorithm fcfs = new FCFS();
 		fcfs.schedule(processList);
 		// Cập nhật lại bảng live output
 		tblOutput.refresh();
 		// Set giá trị cho bảng đánh giá CPU
 		avgWaitTime.setText(String.format("%.2f s", fcfs.getAvgWaitingTime()));
 	    avgTurnAroundTime.setText(String.format("%.2f s", fcfs.getAvgTurnAroundTime()));
 	   cpuUtilization.setText(String.format("%.2f %%", fcfs.getCpuUtilization()));
 	}

 	void runSJN()
 	{
 		CPUAlgorithm sjn = new SJN();
 		sjn.schedule(processList);
 		// Cập nhật lại bảng live output
 		tblOutput.refresh();
 		// Set giá trị cho bảng đánh giá CPU
 		avgWaitTime.setText(String.format("%.2f s", sjn.getAvgWaitingTime()));
 	    avgTurnAroundTime.setText(String.format("%.2f s", sjn.getAvgTurnAroundTime()));
 	    cpuUtilization.setText(String.format("%.2f %%", sjn.getCpuUtilization()));
 	}
 	
 	void runRR() {
 		try {
 			int getQuantumTime;
 			if(timeQuantumText.getText() != null && !timeQuantumText.getText().isEmpty()) {
 				getQuantumTime = Integer.parseInt(timeQuantumText.getText());
 			} else {
 				// default value quantum time
 				getQuantumTime = 90;
 			}
 			if(getQuantumTime < 0) {
 				Alert alert = new Alert(Alert.AlertType.WARNING);
 	            alert.setTitle("Cảnh báo");
 	            alert.setHeaderText("Dữ liệu không hợp lệ");
 	            alert.setContentText("Không được nhập giá trị âm cho Time Quantum!");
 	            alert.showAndWait();
 	            return;
 			}
 			CPUAlgorithm rr = new RoundRobin(getQuantumTime);
 			rr.schedule(processList);
 			// Cập nhật lại bảng live output
 			tblOutput.refresh();
 			// Set giá trị cho bảng đánh giá CPU
 			System.out.println(getQuantumTime);
 			avgWaitTime.setText(String.format("%.2f s", rr.getAvgWaitingTime()));
 		    avgTurnAroundTime.setText(String.format("%.2f s", rr.getAvgTurnAroundTime())); 
 		   cpuUtilization.setText(String.format("%.2f %%", rr.getCpuUtilization()));
 		}
 		
	    catch (NumberFormatException ex) {
 			// Lỗi nhập không phải số
 			Alert alert = new Alert(Alert.AlertType.ERROR);
 	        alert.setTitle("Lỗi nhập liệu");
 	        alert.setHeaderText("Dữ liệu không hợp lệ");
 	        alert.setContentText("Vui lòng nhập số nguyên cho Time Quantum!");
 	        alert.showAndWait();
 		}
 	}
 	
 	// Gantt chart
 	
}

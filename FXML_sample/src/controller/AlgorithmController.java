package controller;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
	
	private ObservableList<Process> processList = FXCollections.observableArrayList();
   
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
    private Canvas ganttCanvas;
    
    @FXML
    private Label timeLabel;
    
    private double currentTime = 0;
    
    
    

	private Stage stage;
	// Set the primaryStage in the controller
    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }
    
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
    
    // set title cho trang algorithm
    void setTitle(String name) {
    	this.nameAlgorithm = name;
    	System.out.println("Hello " + this.nameAlgorithm);
    	if (algorithmLabel != null) {
            algorithmLabel.setText("Thuật toán: " + name);
        } else {
            System.out.println("algorithmLabel chưa được khởi tạo!");
        }
    }
    
    public String getNameAlgorithm() {
    	return this.nameAlgorithm;
    }
    	
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
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
		
		
        // Lấy thuật toán hiện tại từ SharedData và cập nhật label
    	System.out.println("1");
    	System.out.println(this.nameAlgorithm);
            if (this.nameAlgorithm != null) {
                algorithmLabel.setText("Thuật toán: " + this.nameAlgorithm);
            } else {
                algorithmLabel.setText("Chưa chọn thuật toán!");
            }
            
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
 	        Process removedProcess = tableProcesses.getSelectionModel().getSelectedItem();

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
 	        Process selectedProcess = tableProcesses.getSelectionModel().getSelectedItem();

 	        if (selectedProcess != null) {
 	            // Đặt giá trị cho các trường nhập dựa trên thông tin của process đã chọn
 	            idText.setText(String.valueOf(selectedProcess.getId()));
 	            arrivalTimeText.setText(String.valueOf(selectedProcess.getArrivalTime()));
 	            burstTimeText.setText(String.valueOf(selectedProcess.getBurstTime()));
 	            priorityText.setText(String.valueOf(selectedProcess.getPriority()));
 	            idText.setEditable(false);
 	            updateEdit.setVisible(true);
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
 	        Process selectedProcess = tableProcesses.getSelectionModel().getSelectedItem();

 	        if (selectedProcess != null) {
 	            // Render process đã chọn ra trường nhập
 	            selectedProcess.setId(Integer.parseInt(idText.getText()));
 	            selectedProcess.setArrivalTime(Integer.parseInt(arrivalTimeText.getText()));
 	            selectedProcess.setBurstTime(Integer.parseInt(burstTimeText.getText()));
 	            selectedProcess.setPriority(Integer.parseInt(priorityText.getText()));
 	            
 	            // refresh lại bảng
 	           tableProcesses.refresh();
 	            
 	            //clear
 	            idText.clear();
 	            arrivalTimeText.clear();
 	            burstTimeText.clear();
 	            priorityText.clear();
 	            updateEdit.setVisible(false);
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
 	       tableProcesses.setItems(processList);
 	        
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
     void runSimulation(ActionEvent event) throws IOException {
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
 		avgTurnAroundTime.setText(Double.toString(fcfs.getAvgTurnAroundTime()) + " s");
        avgWaitTime.setText(Double.toString(fcfs.getAvgWaitingTime()) + " s");
 	}

 	void runSJN()
 	{
 		CPUAlgorithm sjn = new SJN();
 		sjn.schedule(processList);
 		avgTurnAroundTime.setText(Double.toString(sjn.getAvgTurnAroundTime()) + " s");
        avgWaitTime.setText(Double.toString(sjn.getAvgWaitingTime()) + " s");
 	}
 	
 	void runRR() {
 		RoundRobin rr;
		if(timeQuantumText.getText() != null && !timeQuantumText.getText().isEmpty()) {
			rr = new RoundRobin(Integer.parseInt(timeQuantumText.getText()));
		} else {
			rr = new RoundRobin();
		}
		rr.schedule(processList);
		avgTurnAroundTime.setText(Double.toString(rr.getAvgTurnAroundTime()) + " s");
        avgWaitTime.setText(Double.toString(rr.getAvgWaitingTime()) + " s");
 	}
 	
 	@FXML
 	public void startGanttChartAnimation(ObservableList<Process> processes)
 	{
 		GraphicsContext gc = ganttCanvas.getGraphicsContext2D();
 		double unitTime = 1000;
 		double unitWidth = ganttCanvas.getWidth() / totalBurstTime(processes);
 		double unitHeight = ganttCanvas.getHeight() / processList.size();
 		
 		Timeline timeline = new Timeline();
 		timeline.setCycleCount(Timeline.INDEFINITE);
 		
 		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(unitTime), event -> {
 			currentTime++;
            System.out.println("Current Time: " + currentTime + "s"); // Debugging statement
 			timeLabel.setText(currentTime + "s");
 			drawGanttChart(gc, processes, currentTime, unitWidth, unitHeight);
 		}));
 		
 		timeline.play();
 	}

 	
 	private void drawGanttChart(GraphicsContext gc,ObservableList<Process> processes, double currentTime, double unitWidth, double unitHeight)
 	{
 		gc.clearRect(0, 0, ganttCanvas.getWidth(), ganttCanvas.getHeight());
 		System.out.println("Drawing Gantt Chart"); // Debugging statement
 		
 		for(Process process : processes)
 		{
 			double startTime = Math.max(currentTime, process.getArrivalTime());
 			double finishTime = startTime + process.getBurstTime();
 			double startX = startTime * unitWidth;
 			double width = process.getBurstTime() * unitWidth;
 			double height = unitHeight;
 			double yPos = process.getId() * unitHeight;
 			
 			gc.setFill(getProcessColor(process.getId()));
 			gc.fillRect(startX, yPos, width, height);
 			gc.setStroke(Color.BLACK);
 			gc.strokeRect(startX, yPos, width, height);
 			System.out.println("Process " + process.getId() + ": startX=" + startX + ", width=" + width); // Debugging statement
 		}
 	}
 	
 	private double totalBurstTime(ObservableList<Process> processes)
 	{
 		return processes.stream().mapToDouble(Process::getBurstTime).sum();
 	}
 	
 	private Color getProcessColor(int pid)
 	{
 		return Color.hsb((pid * 360.0 / 10) % 360, 0.7, 0.9);
 	}
}

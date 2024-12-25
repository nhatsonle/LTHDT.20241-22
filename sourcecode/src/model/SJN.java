package model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

public class SJN extends CPUAlgorithm {
	
	public SJN() {
		this.setName("SJN");
	}
	@Override
	public void schedule(ObservableList<Process> processes) {
		// Tạo danh sách tạm thời 
		List<Process> readyQueue  = new ArrayList<>(processes);
		int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int totalBurstTime = 0;
        while(!readyQueue.isEmpty()) {
        	// Tạo danh sách mới tiến trình đã đến
        	List<Process> availableProcesses = new ArrayList<>();
            for (Process p : readyQueue) {
            	// Với mỗi lần tiến trình đến thêm vào hàng đợi
                if (p.getArrivalTime() <= currentTime) {
                    availableProcesses.add(p);
                }
            }

            // Nếu không có tiến trình nào sẵn sàng, tăng thời gian đến thời điểm tiến trình sớm nhất
            if (availableProcesses.isEmpty()) {
                currentTime = readyQueue.stream()
                        .mapToInt(Process::getArrivalTime)
                        .min()
                        .orElse(currentTime);
                continue;
            }
            
            // Chọn tiến trình có Burst Time nhỏ nhất; nếu trùng thì dựa trên Priority
            Process selectedProcess = availableProcesses.stream()
                    .min((p1, p2) -> {
                        if (p1.getBurstTime() != p2.getBurstTime()) {
                            return Integer.compare(p1.getBurstTime(), p2.getBurstTime());
                        } else {
                            return Integer.compare(p1.getPriority(), p2.getPriority());
                        }
                    })
                    .orElse(null);
            
            // Xử lý tiến trình
            // Tính waiting time cho process
            selectedProcess.calculateWaitingTime(currentTime);
            // Tính turn around time cho process
            selectedProcess.calculateTurnaroundTime();

            int waitingTime = selectedProcess.getWaitingTime();
            int turnaroundTime = selectedProcess.getTurnaroundTime();
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            // Cập nhật thời gian hiện tại
            currentTime += selectedProcess.getBurstTime();
            // Xóa tiến trình đã thực thi
            readyQueue.remove(selectedProcess);
            totalBurstTime += selectedProcess.getBurstTime();
        }

        int totalTime = currentTime;
		int n = processes.size();
		this.setAvgWaitingTime((double)totalWaitingTime / n);
        this.setAvgTurnAroundTime((double)totalTurnaroundTime / n);
        this.setCpuUtilization(((double) totalBurstTime / totalTime) * 100);
        
        }
	@Override
	public String displayHelp()
	{
		return "Giải thích của thuật toán Shortest Job Next";
	}
//	@Override
//	public String displayHelp() {
//	    return """
//	    Thuật toán Shortest Job First (SJN):
//	    - SJN là thuật toán lập lịch dựa trên thời gian xử lý ngắn nhất.
//	    - Tiến trình có Burst Time ngắn nhất sẽ được ưu tiên thực thi trước.
//	    - Nếu có tiến trình đến cùng lúc, thì sắp xếp dựa trên thứ tự Arrival Time.
//	    - Có hai loại: Non-Preemptive (không gián đoạn) và Preemptive (gián đoạn).
//	    
//	    Ưu điểm:
//	    - Giảm thời gian chờ đợi trung bình.
//	    Nhược điểm:
//	    - Dễ dẫn đến Starvation (tiến trình dài bị bỏ qua).
//	    """;
//	}
	
	
}
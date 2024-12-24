package model;

import java.util.List;

import javafx.collections.ObservableList;

public class FCFS extends CPUAlgorithm {
	
	public FCFS() {
		this.setName("FCFS");
	}
	@Override
	public void schedule(ObservableList<Process> processes) {
		// Sắp xếp tiến trình theo thời gian đến || thời gian thực hiện || priority
		processes.sort((p1, p2) -> {
            if (p1.getArrivalTime() != p2.getArrivalTime()) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            } else if (p1.getBurstTime() != p2.getBurstTime()) {
                return Integer.compare(p1.getBurstTime(), p2.getBurstTime());
            } else {
                return Integer.compare(p1.getPriority(), p2.getPriority());
            }
        });

		int currentTime = 0;
		int totalWaitingTime = 0; // Tổng thời gian chờ
		int totalTurnAroundTime = 0; // Tổng thời gian quay vòng
		System.out.println("Tiến trình | Arrival Time | Burst Time | Waiting Time | Turnaround Time");
        System.out.println("-------------------------------------------------------------");
        
		for(Process process: processes) {
			 int startTime = Math.max(currentTime, process.getArrivalTime());
		     int finishTime = startTime + process.getBurstTime();
			// Xử lý tiến trình
			// Trả waiting time cho process
			process.calculateWaitingTime(currentTime);
			// Trả turn around time cho process
			process.calculateTurnaroundTime();
			
			// Xử lý tính WAT và TAT
			int waitingTime = process.getWaitingTime();
	        int turnaroundTime = process.getTurnaroundTime();
			totalWaitingTime += waitingTime;
			totalTurnAroundTime += turnaroundTime;
			currentTime = finishTime;	
			System.out.printf("    P%d     |      %d      |     %d     |      %d      |       %d%n", process.getId(), process.getArrivalTime(), process.getBurstTime(), process.getWaitingTime(), process.getTurnaroundTime());
		}
		int n = processes.size();
		avgWaitingTime = (double)totalWaitingTime / n;
        avgTurnAroundTime = (double)totalTurnAroundTime / n;
		System.out.println("-------------------------------------------------------------");
        System.out.printf("Thời gian chờ trung bình: %.2f%n", avgWaitingTime);
        System.out.printf("Thời gian quay vòng trung bình: %.2f%n", avgTurnAroundTime);
	}
	
	@Override
	public String displayHelp() {
	    return """
	    Thuật toán First Come First Serve (FCFS):
	    - FCFS là thuật toán lập lịch đơn giản nhất.
	    - Các tiến trình được xử lý theo thứ tự đến trước (Arrival Time).
	    - Không có ưu tiên đặc biệt, không có gián đoạn.

	    Quy trình:
	    1. Sắp xếp các tiến trình dựa trên Arrival Time.
	    2. Thực thi tiến trình đến trước, hoàn thành rồi mới thực thi tiến trình tiếp theo.

	    Ưu điểm:
	    - Đơn giản, dễ triển khai.
	    - Không xảy ra Deadlock (khi được thiết kế đúng).

	    Nhược điểm:
	    - Dễ dẫn đến hiệu ứng Convoy Effect (tiến trình ngắn phải chờ tiến trình dài).
	    - Thời gian chờ đợi trung bình có thể cao nếu Arrival Time và Burst Time không tối ưu.
	    """;
	}


}

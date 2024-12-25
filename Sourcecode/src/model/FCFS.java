package model;

import javafx.collections.ObservableList;

public class FCFS extends CPUAlgorithm {
	public FCFS() {
		this.setName("FCFS");
	}
	@Override
	public void schedule(ObservableList<Process> processes) {
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
		int totalWaitingTime = 0; 
		int totalTurnAroundTime = 0; 
		int totalBurstTime = 0;
		
		for(Process process: processes) {
			
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }
			
			process.calculateWaitingTime(currentTime);
			process.calculateTurnaroundTime();
			
			int waitingTime = process.getWaitingTime();
	        int turnaroundTime = process.getTurnaroundTime();
			totalWaitingTime += waitingTime;
			totalTurnAroundTime += turnaroundTime;
			
			totalBurstTime += process.getBurstTime();
			currentTime +=  process.getBurstTime();	
		}
		int totalTime = currentTime;
		int n = processes.size();
		this.setAvgWaitingTime((double)totalWaitingTime / n);
        this.setAvgTurnAroundTime((double)totalTurnAroundTime / n);
        this.setCpuUtilization(((double) totalBurstTime / totalTime) * 100);
	}
	@Override
	public String displayHelp() {
		// TODO Auto-generated method stub
		return "Giải thích của thuật toán FCFS";
	}
	
	
//	@Override
//	public String displayHelp() {
//	    return """
//	    Thuật toán First Come First Serve (FCFS):
//	    - FCFS là thuật toán lập lịch đơn giản nhất.
//	    - Các tiến trình được xử lý theo thứ tự đến trước (Arrival Time).
//	    - Không có ưu tiên đặc biệt, không có gián đoạn.
//
//	    Quy trình:
//	    1. Sắp xếp các tiến trình dựa trên Arrival Time.
//	    2. Thực thi tiến trình đến trước, hoàn thành rồi mới thực thi tiến trình tiếp theo.
//
//	    Ưu điểm:
//	    - Đơn giản, dễ triển khai.
//	    - Không xảy ra Deadlock (khi được thiết kế đúng).
//
//	    Nhược điểm:
//	    - Dễ dẫn đến hiệu ứng Convoy Effect (tiến trình ngắn phải chờ tiến trình dài).
//	    - Thời gian chờ đợi trung bình có thể cao nếu Arrival Time và Burst Time không tối ưu.
//	    """;
//	}


}
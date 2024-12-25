package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.collections.ObservableList;

public class RoundRobin extends CPUAlgorithm {
	
	private int timeQuantum;
	public RoundRobin() {
		this.setName("Round Robin");
		this.timeQuantum = 90;
	}
	// Time Quantum default = 90
	public RoundRobin(int timeQuantum) {
		this.setName("Round Robin");
		this.timeQuantum = timeQuantum;
	}
	public int getTimeQuantum() {
		return timeQuantum;
	}
	public void setTimeQuantum(int timeQuantum) {
		this.timeQuantum = timeQuantum;
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

		Queue<Process> readyQueue = new LinkedList<>(processes); // Hàng đợi sẵn sàng
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int totalBurstTime = 0;
        // Lưu thời gian thực thi còn lại của mỗi tiến trình
        List<Integer> remainingBurstTime = new ArrayList<>();

        // Lưu thời gian burst ban đầu của mỗi tiến trình
        for (Process process : processes) {
            remainingBurstTime.add(process.getBurstTime());
            totalBurstTime += process.getBurstTime();
        }

        while (!readyQueue.isEmpty()) {
            Process currentProcess = readyQueue.poll(); // Lấy và xóa phần tử đầu tiên ra khỏi readyQueue
            int index = processes.indexOf(currentProcess);
            int burstTime = remainingBurstTime.get(index);

            if (currentTime < currentProcess.getArrivalTime()) {
                currentTime = currentProcess.getArrivalTime();
            }
            
            int timeSpent = Math.min(burstTime, timeQuantum); // Xử lý trong Time Quantum
            currentTime += timeSpent;
            remainingBurstTime.set(index, burstTime - timeSpent);

            if (remainingBurstTime.get(index) > 0) {
                // Nếu tiến trình chưa hoàn thành, đưa vào lại hàng đợi
                readyQueue.add(currentProcess);
            } else {
                // Nếu tiến trình hoàn thành
                int finishTime = currentTime;
                int waitingTime = finishTime - currentProcess.getArrivalTime() - currentProcess.getBurstTime();
                int turnaroundTime = finishTime - currentProcess.getArrivalTime();
                
                // Tính waiting time cho process
                currentProcess.calculateWaitingTime(finishTime - currentProcess.getBurstTime());
                // Tính turn around time cho process
                currentProcess.calculateTurnaroundTime();
                
                // Tính WAT và TAT
                totalWaitingTime += waitingTime;
                totalTurnaroundTime += turnaroundTime;
            }
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
		return "Giải thích của thuật toán Round Robin";
	}
	
//	@Override
//	public String displayHelp() {
//	    return """
//	    Thuật toán Round Robin (RR):
//	    - Xử lý tiến trình theo vòng lặp với Time Quantum cố định.
//	    - Mỗi tiến trình được cấp CPU trong Time Quantum hoặc cho đến khi hoàn thành.
//	    - Tiến trình chưa hoàn thành sẽ được đưa lại cuối hàng đợi.
//
//	    Ưu điểm:
//	    - Cân bằng, không có tiến trình chờ lâu.
//	    - Phù hợp cho hệ thống đa người dùng.
//
//	    Nhược điểm:
//	    - Time Quantum quá nhỏ: tăng chi phí chuyển ngữ cảnh.
//	    - Time Quantum quá lớn: giống FCFS, tăng thời gian chờ.
//
//	    Lưu ý: Time Quantum mặc định là 90ms.
//	    """;
//	}


	
}
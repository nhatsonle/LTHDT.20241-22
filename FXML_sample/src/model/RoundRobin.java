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
        // Lưu thời gian thực thi còn lại của mỗi tiến trình
        List<Integer> remainingBurstTime = new ArrayList<>();

        // Lưu thời gian burst ban đầu của mỗi tiến trình
        for (Process process : processes) {
            remainingBurstTime.add(process.getBurstTime());
        }

        System.out.println("Tiến trình | Arrival Time | Burst Time | Waiting Time | Burst Time");
        System.out.println("---------------------------------------------------------------");

        while (!readyQueue.isEmpty()) {
            Process currentProcess = readyQueue.poll();
            int index = processes.indexOf(currentProcess);
            int burstTime = remainingBurstTime.get(index);

            if (currentTime < currentProcess.getArrivalTime()) {
                currentTime = currentProcess.getArrivalTime();
            }

            int startTime = currentTime;
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
                
                // Trả waiting time cho process
                currentProcess.calculateWaitingTime(finishTime - currentProcess.getBurstTime());
                // Trả turn around time cho process
                currentProcess.calculateTurnaroundTime();
                
                // Tính WAT và TAT
                totalWaitingTime += waitingTime;
                totalTurnaroundTime += turnaroundTime;

                System.out.printf("    P%d     |      %d      |     %d     |     %d     |      %d%n",
                        currentProcess.getId(), currentProcess.getArrivalTime(),
                        currentProcess.getBurstTime(), currentProcess.getWaitingTime(), currentProcess.getTurnaroundTime());
            }
        }

        int n = processes.size();
        avgWaitingTime = (double)totalWaitingTime / n;
        avgTurnAroundTime = (double)totalTurnaroundTime / n;
        System.out.println("---------------------------------------------------------------");
        System.out.printf("Thời gian chờ trung bình: %.2f%n", avgWaitingTime);
        System.out.printf("Thời gian quay vòng trung bình: %.2f%n", avgTurnAroundTime);
        System.out.println(timeQuantum);
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

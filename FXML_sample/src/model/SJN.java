package model;

import java.util.ArrayList;
import java.util.List;

public class SJN extends CPUAlgorithm {
	public SJN() {
		this.setName("SJN");
	}
	public void schedule(List<Process> processes) {
		// Tạo danh sách tạm thời 
		List<Process> readyQueue  = new ArrayList<>(processes);
		int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        System.out.println("Tiến trình | Arrival Time | Burst Time | Waiting Time | Turnaround Time");
        System.out.println("-------------------------------------------------------------");
        while(!readyQueue.isEmpty()) {
        	// Tạo danh sách tiến trình đã đến
        	// Với mỗi lần 
        	List<Process> availableProcesses = new ArrayList<>();
            for (Process p : readyQueue) {
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

            // Chọn tiến trình có burst time nhỏ nhất trong số các tiến trình đã đến
            Process selectedProcess = availableProcesses.stream()
                    .min((p1, p2) -> Integer.compare(p1.getBurstTime(), p2.getBurstTime()))
                    .orElse(null);
            
            
            // Xử lý tiến trình
            // Trả waiting time cho process
            selectedProcess.calculateWaitingTime(currentTime);
            // Trả turn around time cho process
            selectedProcess.calculateTurnaroundTime();

            
            int waitingTime = selectedProcess.getWaitingTime();
            int turnaroundTime = selectedProcess.getTurnaroundTime();
            totalWaitingTime += waitingTime;
            totalTurnaroundTime += turnaroundTime;

            System.out.printf("    P%d     |      %d      |     %d     |      %d      |       %d%n",
                    selectedProcess.getId(), selectedProcess.getArrivalTime(),
                    selectedProcess.getBurstTime(), waitingTime, turnaroundTime);

            // Cập nhật thời gian hiện tại và loại bỏ tiến trình đã xử lý khỏi hàng đợi
            currentTime += selectedProcess.getBurstTime();
            readyQueue.remove(selectedProcess);
        }

        int n = processes.size();
        System.out.println("-------------------------------------------------------------");
        System.out.printf("Thời gian chờ trung bình: %.2f%n", (double) totalWaitingTime / n);
        System.out.printf("Thời gian quay vòng trung bình: %.2f%n", (double) totalTurnaroundTime / n);
        }
	@Override
	public String displayHelp() {
	    return """
	    Thuật toán Shortest Job First (SJN):
	    - SJN là thuật toán lập lịch dựa trên thời gian xử lý ngắn nhất.
	    - Tiến trình có Burst Time ngắn nhất sẽ được ưu tiên thực thi trước.
	    - Nếu có tiến trình đến cùng lúc, thì sắp xếp dựa trên thứ tự Arrival Time.
	    - Có hai loại: Non-Preemptive (không gián đoạn) và Preemptive (gián đoạn).
	    
	    Ưu điểm:
	    - Giảm thời gian chờ đợi trung bình.
	    Nhược điểm:
	    - Dễ dẫn đến Starvation (tiến trình dài bị bỏ qua).
	    """;
	}
}
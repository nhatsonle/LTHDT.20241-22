package process;

import java.util.Random;

public class Process {
	private int id;
	private int arrivalTime; 
	private int burstTime; // Thời gian thực hiện
	private int priority;
	private int waitingTime;
	private int turnaroundTime;
	private static final Random random = new Random(); // Khởi tạo Random cho priority
	
	public Process(int id, int arrivalTime, int burstTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = random.nextInt(100);
	}
	
	public Process(int id, int arrivalTime, int burstTime, int priority) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
	}
	// Tính thời gian chờ
	public void calculateWaitingTime(int startTime) {
		waitingTime = startTime - arrivalTime;
	}
	// Tính thời gian quay vòng
	public void calculateTurnaroundTime() {
		turnaroundTime = waitingTime + burstTime;
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getBurstTime() {
		return burstTime;
	}
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	public int getTurnaroundTime() {
		return turnaroundTime;
	}
	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	// Override equals()
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Process process = (Process) obj;
        return id == process.id || (arrivalTime == process.arrivalTime && burstTime == process.burstTime && priority == process.priority);
    }
	
	// Override toString()
	@Override
    public String toString() {
        return String.format(
            "Process(id = %d, arrivalTime = %d, burstTime = %d, priority = %d, waitingTime = %d, turnaroundTime = %d)",
                     id, arrivalTime, burstTime, priority, waitingTime, turnaroundTime
        );
    }
}

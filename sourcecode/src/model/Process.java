package model;

import java.util.Random;

// Thiếu ghi đè equals (giống lab 4 oop thực hành) để kiểm tra khi addProcess và removeProcess
// nếu trùng id || khác id nhưng trùng arrival time, burst time, priority

public class Process {
	private int id;
	private int arrivalTime; 
	private int burstTime; // Thời gian thực hiện
	private int priority;
	private int waitingTime;
	private int turnaroundTime;
	private int startingTime;
	private int finishTime;
	private int remainingTime;
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
	
	public int getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(int startingTime) {
		this.startingTime = startingTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public Process () {
		
	}
	// Tính thời gian chờ
	public void calculateWaitingTime(int startTime) {
		this.waitingTime = startTime - arrivalTime;
	}
	// Tính thời gian quay vòng
	public void calculateTurnaroundTime() {
		this.turnaroundTime = this.waitingTime + burstTime;
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
	
	public int getTurnaroundTime() {
		return turnaroundTime;
	}
	
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Process process = (Process) obj;
        return id == process.id || (arrivalTime == process.arrivalTime && burstTime == process.burstTime && priority == process.priority);
    }

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	
}
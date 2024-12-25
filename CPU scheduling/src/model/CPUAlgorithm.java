package model;

import javafx.collections.ObservableList;

public abstract class CPUAlgorithm {
	private String name;
	private double avgWaitingTime;
	private double avgTurnAroundTime;
	private double cpuUtilization;
	public abstract String displayHelp();
	public abstract void schedule(ObservableList<Process> processList);
	
	// Getter and Setter
	public double getAvgWaitingTime() {
		return avgWaitingTime;
	}
	public double getAvgTurnAroundTime() {
		return avgTurnAroundTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCpuUtilization() {
		return cpuUtilization;
	}
	public void setCpuUtilization(double cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
	public void setAvgWaitingTime(double avgWaitingTime) {
		this.avgWaitingTime = avgWaitingTime;
	}
	public void setAvgTurnAroundTime(double avgTurnAroundTime) {
		this.avgTurnAroundTime = avgTurnAroundTime;
	}
}
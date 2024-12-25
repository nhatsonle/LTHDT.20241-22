package model;

import javafx.collections.ObservableList;

public abstract class CPUAlgorithm {
	private String name;
	public double avgWaitingTime;
	public double avgTurnAroundTime;
	public abstract String displayHelp();
	public abstract void schedule(ObservableList<Process> processList);
	
	// Contructor
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
}

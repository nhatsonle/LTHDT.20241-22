package model;

import javafx.collections.ObservableList;

public abstract class CPUAlgorithm {
	public double avgWaitingTime;
	public double avgTurnAroundTime;
	public double getAvgWaitingTime() {
		return avgWaitingTime;
	}
	
	public double getAvgTurnAroundTime() {
		return avgTurnAroundTime;
	}
	
	private String name;
	public abstract String displayHelp();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public abstract void schedule(ObservableList<Process> processList);
}

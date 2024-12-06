package simulator;

import java.util.ArrayList;
import java.util.List;

import process.Process;
import algorithm.FCFS;
import algorithm.RoundRobin;
import algorithm.SJF;

// Thiếu
// - displayGanttChart()
// - calculateMetrics() có thể lấy dữ liệu từ schedule() của các Algorithm
// - editProcess()
// - thêm addProcess, removeProcess

public class Simulator {
	private String cpuAlgorithmName;
	private List<Process> processes = new ArrayList<Process>();
	public void addProcess(int id, int arrivalTime, int burstTime) {
		// Chưa kiểm tra đã có hay chưa
        processes.add(new Process(id, arrivalTime, burstTime));
    }
	public void removeProcess(int id, int arrivalTime, int burstTime) {
		// Chưa kiểm tra đã có hay chưa
        processes.remove(new Process(id, arrivalTime, burstTime));
    }
	public void runSimulation() {
		switch(cpuAlgorithmName) {
			case "FCFS":
				runFCFS();
				break;
			case "SJF":
				runSJF();
				break;
			case "RoundRobin":
				runRoundRobin();
				break;
			default:
		}
	}
	private void runRoundRobin() {
		RoundRobin roundRobin = new RoundRobin();
		// Nếu có thay đổi giá trị Time Quan Tum thì tạo phương thức RoundRobin(Giá trị khác)
		// Mặc định Time Quan Tum = 90
		roundRobin.schedule(processes);
	}
	private void runSJF() {
		SJF sjf = new SJF();
		sjf.schedule(processes);
	}
	public void runFCFS() {
		FCFS fcfs = new FCFS();
		fcfs.schedule(processes);
	}
	public String getCpuAlgorithmName() {
		return cpuAlgorithmName;
	}
	public void setCpuAlgorithmName(String cpuAlgorithmName) {
		this.cpuAlgorithmName = cpuAlgorithmName;
	}
}

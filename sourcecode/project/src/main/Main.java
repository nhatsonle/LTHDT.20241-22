package main;

import simulator.Simulator;

public class Main {
	public static void main(String[] args) {
		Simulator simula = new Simulator();
		simula.setCpuAlgorithmName("RoundRobin");
		simula.addProcess(1, 0, 60);
		simula.addProcess(2, 20, 80);
		simula.addProcess(3, 30, 120);
		simula.addProcess(4, 60, 200);
		simula.runSimulation();
	}
}

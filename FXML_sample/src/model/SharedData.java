package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SharedData {
    // Biến static để lưu object thuật toán hiện tại
    private static Object currentAlgorithm;
    private static SharedData instance = new SharedData();
    private static ObservableList<Process> processes = FXCollections.observableArrayList();
    private static double avgWT, avgTRT;
    
    public static void setWT(double avgwt)
    {
    	avgWT = avgwt;
    }
    
    public static void setTRT(double avgtrt)
    {
    	avgTRT = avgtrt;
    }
    
    public static double getWT()
    {
    	return avgWT;
    }
    public static double getTRT()
    {
    	return avgTRT;
    }
    
    private SharedData() {}
    
    public static ObservableList<Process> getProcesses() {
        return processes;
    }

    public static void addProcess(Process process) {
        processes.add(process);
    }

    public static void clearProcesses() {
        processes.clear();
    }
   
    public static SharedData getInstance() {
    	return instance;
    }
    // Getter
    public static Object getCurrentAlgorithm() {
        return currentAlgorithm;
    }

    // Setter
    public static void setCurrentAlgorithm(Object algorithm) {
        currentAlgorithm = algorithm;
    }
} 
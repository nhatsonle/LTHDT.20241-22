package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Process;
import model.GanttChart;
import model.GanttChartCell;

public class GanttChartSchedule {
	private static void resetProcess(ObservableList<Process> processes) {
		for (Process x : processes) {
            x.setRemainingTime(x.getBurstTime());
            x.setFinishTime(-1);
            x.setWaitingTime(-1);
            x.setStartingTime(-1);
        }
	}
	
	private void drawGanttChartCell(Process p, double totalTime, HBox ganttChart, boolean last, Scene scene, double timer) {
        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
        
        // Tạo Label cho PID và Timeline
        Label timeline, PID;
        
        // Nếu là ô cuối, chỉ hiển thị finish time, nếu không hiển thị ID và thời gian
        if (last) 
        {
            PID = new Label("");
            PID.setPrefHeight(30);
            PID.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 1))));
            timeline = new Label(Double.toString(p.getFinishTime()));
        } 
        else 
        {
            PID = new Label(Integer.toString(p.getId()));
            PID.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1, 0, 1, 1))));
            PID.setAlignment(Pos.CENTER);
            PID.setPrefHeight(30);
            timeline = new Label(Double.toString(timer));
        }

        // Tính độ rộng của các ô
        PID.setPrefWidth(0.95 * scene.getWidth() * p.getRemainingTime() / totalTime);
        timeline.setPrefWidth(0.95 * scene.getWidth() * p.getRemainingTime() / totalTime);
        
        // Đảm bảo các phần tử con có độ rộng phù hợp
        PID.prefWidthProperty().bind(v.widthProperty());
        timeline.prefWidthProperty().bind(v.widthProperty());
        v.prefWidthProperty().bind(ganttChart.widthProperty());

        
        // Thêm các Label vào VBox
        v.getChildren().addAll(PID, timeline);
        ganttChart.getChildren().addAll(v);
    }
	

    
    public static void sortFCFS(HBox ganttChart, ObservableList<Process> P, Scene scene) {
        ObservableList<Process> processes = FXCollections.observableArrayList(P);
        resetProcess(processes);
        processes.sort((o1, o2) -> {
            return (o1.getArrivalTime() > o2.getArrivalTime()) ? 1 : 0;
        });
        double totalTime = 0;
        ObservableList<Process> arrivedProcesses = FXCollections.observableArrayList();
        for (Process p : processes)
            totalTime += p.getBurstTime();

        int finished = 0;
        int timer = 0;
        GanttChart gantt = new GanttChart();
        for (Process process : processes)
            if (timer >= process.getArrivalTime())
                arrivedProcesses.add(process);
        int i = 0;
        while (arrivedProcesses.size() > 0 || finished < processes.size()) {
            while (arrivedProcesses.size() > 0) {
                processes.get(i).setStartingTime(timer);
                processes.get(i).setFinishTime(timer + processes.get(i).getBurstTime());
                processes.get(i).setWaitingTime(timer - processes.get(i).getArrivalTime());
               
                gantt.addCell(new GanttChartCell(timer, -1,"P" +Integer.toString(arrivedProcesses.get(0).getId())));
                arrivedProcesses.remove(0);
                timer += processes.get(i).getBurstTime();
                i++;
                finished++;
            }
            for (int j = i; j < processes.size(); j++) {
                if (timer >= processes.get(j).getArrivalTime()) {
                    arrivedProcesses.add(processes.get(j));
                }
            }
            if (arrivedProcesses.isEmpty() && finished < processes.size()) {
                gantt.addCell(new GanttChartCell(timer, 0, "IDLE"));
                timer = processes.get(i).getArrivalTime();
            }

        }
        gantt.addCell(new GanttChartCell(timer, -1,"P" +Integer.toString(processes.get(i-1).getId())));

        gantt.draw(ganttChart, scene);
    }
    
    public static void sortSJN(HBox ganttChart, ObservableList<Process> processes, Scene scene) {
        ObservableList<Process> arrivedProcesses = FXCollections.observableArrayList();
        resetProcess(processes);
        
        double totalTime = 0;
        for (Process p : processes)
            totalTime += p.getBurstTime();
        
        int finished = 0;
        
        processes.sort((o1, o2) -> {
            if (o1.getArrivalTime() == o2.getArrivalTime())
                return (o1.getBurstTime() > o2.getBurstTime()) ? 1 : 0;
            return (o1.getArrivalTime() > o2.getArrivalTime()) ? 1 : 0;
        });
        GanttChart gantt = new GanttChart();
        int timer = 0;
        for (Process process : processes)
            if (timer >= process.getArrivalTime())
                arrivedProcesses.add(process);
        int i = 0;
        Process nextProcess = arrivedProcesses.get(0);
        while (arrivedProcesses.size() > 0 || finished < processes.size()) {
            nextProcess = arrivedProcesses.get(0);
            for (int j = 0; j < arrivedProcesses.size(); j++)
                if (arrivedProcesses.get(j).getBurstTime() < nextProcess.getBurstTime())
                    nextProcess = arrivedProcesses.get(j);


            nextProcess.setStartingTime(timer);
            nextProcess.setFinishTime(timer + nextProcess.getBurstTime());
            nextProcess.setWaitingTime(timer - nextProcess.getArrivalTime());
            nextProcess.setRemainingTime(0);

            gantt.addCell(new GanttChartCell(timer, -1,"P" + Integer.toString(nextProcess.getId())));
            arrivedProcesses.remove(nextProcess);

            timer += nextProcess.getBurstTime();
            i++;
            finished++;

            for (int j = 0; j < processes.size(); j++) {
                if (timer >= processes.get(j).getArrivalTime() && processes.get(j).getRemainingTime() > 0 && !arrivedProcesses.contains(processes.get(j))) {
                    arrivedProcesses.add(processes.get(j));
                }
            }
            if (arrivedProcesses.isEmpty() && finished < processes.size()) {
                gantt.addCell(new GanttChartCell(timer, 0, "IDLE"));
                timer = processes.get(i).getArrivalTime();
            }
            for (int j = 0; j < processes.size(); j++) {
                if (timer >= processes.get(j).getArrivalTime() && processes.get(j).getRemainingTime() > 0 && !arrivedProcesses.contains(processes.get(j))) {
                    arrivedProcesses.add(processes.get(j));
                }
            }

        }
        gantt.addCell(new GanttChartCell(timer, -1,"P" + Integer.toString(nextProcess.getId())));
        gantt.draw(ganttChart, scene);
    }
        
    
    
    public static void sortRoundRobin(HBox ganttChart, ObservableList<Process> P, int timeQuantum, Scene scene) {
        ObservableList<Process> processes = FXCollections.observableArrayList(P);
        resetProcess(processes);
        processes.sort((o1, o2) -> {
            return (o1.getArrivalTime() > o2.getArrivalTime()) ? 1 : 0;
        });
        double totalTime = 0;
        for (Process p : processes)
            totalTime += p.getBurstTime();

        Process nextProcess = processes.get(0);
        int nextProcessIndex = 0;
        int timer = 0;
        boolean flag = false;
        int finished = 0;
        boolean firstLoop = true;
        GanttChart gantt = new GanttChart();
        ObservableList<Process> arrivedProcesses = FXCollections.observableArrayList();
        for (int i = 0; i < processes.size(); i++)
            if (processes.get(i).getArrivalTime() <= timer)
                arrivedProcesses.add(processes.get(i));

        while (arrivedProcesses.size() > 0 || finished < processes.size()) {
            for (int j = 0; arrivedProcesses.size() > 0 && j < arrivedProcesses.size(); j++) {
                if (nextProcess == arrivedProcesses.get(j) && j != arrivedProcesses.size() - 1 && !firstLoop) {
                    arrivedProcesses.remove(nextProcess);
                    arrivedProcesses.add(nextProcess);
                    nextProcess = arrivedProcesses.get(j);
                    nextProcessIndex = j;
                } else {
                    nextProcess = arrivedProcesses.get(j);
                    nextProcessIndex = j;
                }
                firstLoop = false;
                if (nextProcess.getRemainingTime() == nextProcess.getBurstTime()) {
                    processes.get(processes.indexOf(nextProcess)).setStartingTime(timer);
                    P.get(processes.indexOf(nextProcess)).setStartingTime(timer);
                }

                if (timeQuantum >= nextProcess.getRemainingTime()) {
                    timer += nextProcess.getRemainingTime();
                    gantt.addCell(new GanttChartCell(timer-nextProcess.getRemainingTime(), -1,"P" + Integer.toString(nextProcess.getId())));
                    nextProcess.setRemainingTime(0);
                    arrivedProcesses.remove(nextProcess);
                    finished++;
                    j--;
                    int index = processes.indexOf(nextProcess);
                    processes.get(index).setFinishTime(timer);
                    processes.get(index).setWaitingTime(processes.get(index).getFinishTime() - processes.get(index).getArrivalTime() - processes.get(index).getBurstTime());
                    P.get(index).setFinishTime(processes.get(index).getFinishTime());
                    P.get(index).setWaitingTime(processes.get(index).getWaitingTime());

                } else {
                    timer += timeQuantum;
                    nextProcess.setRemainingTime(nextProcess.getRemainingTime() - timeQuantum);
                    gantt.addCell(new GanttChartCell(timer-timeQuantum, -1,"P" + Integer.toString(nextProcess.getId())));
                }

            }
            if (arrivedProcesses.size() == processes.size())
                flag = true;

            for (int j = arrivedProcesses.size(); j < processes.size() && !flag; j++)
                if (processes.get(j).getArrivalTime() <= timer && processes.get(j).getRemainingTime() > 0 && !arrivedProcesses.contains(processes.get(j)))
                    arrivedProcesses.add(processes.get(j));

            if (finished < processes.size() && arrivedProcesses.size() == 0) {
                Process nextArrivedProcess = processes.get(0);
                boolean selectedNext = false;
                for (int i = 0; i < processes.size(); i++) {
                    if (processes.get(i).getRemainingTime() > 0) {
                        if (!selectedNext) {
                            nextArrivedProcess = processes.get(i);
                            selectedNext = true;
                        } else {
                            if (nextArrivedProcess.getArrivalTime() > processes.get(i).getArrivalTime()) {
                                nextArrivedProcess = processes.get(i);
                            }
                        }
                    }
                }
                if (nextArrivedProcess.getRemainingTime() == 0) {
                    selectedNext = false;
                }
                if (selectedNext) {
                    gantt.addCell(new GanttChartCell(timer, 0, "IDLE"));
                    timer = nextArrivedProcess.getArrivalTime();
                }
            }
            

            ObservableList<Process> tempProcesses = FXCollections.observableArrayList();
            for (Process p : arrivedProcesses)
                tempProcesses.add(p);


            for (int j = arrivedProcesses.size(); j < processes.size() && !flag; j++)
                if (processes.get(j).getArrivalTime() <= timer && processes.get(j).getRemainingTime() > 0 && !arrivedProcesses.contains(processes.get(j)))
                    arrivedProcesses.add(processes.get(j));

            for (Process p : tempProcesses) {
                arrivedProcesses.add(p);
                arrivedProcesses.remove(0);
            }
            tempProcesses.clear();
        }
        nextProcess.setFinishTime(timer);
        gantt.addCell(new GanttChartCell(timer, -1,"P" + Integer.toString(nextProcess.getId())));
        gantt.drawWithDuplicates(ganttChart, scene);
        for (int i = 0; i < processes.size(); i++) {
            P.get(P.indexOf(processes.get(i))).setStartingTime(processes.get(i).getStartingTime());
            P.get(P.indexOf(processes.get(i))).setFinishTime(processes.get(i).getFinishTime());
            P.get(P.indexOf(processes.get(i))).setWaitingTime(processes.get(i).getWaitingTime());
        }
    }	
}
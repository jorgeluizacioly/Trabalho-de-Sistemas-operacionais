package com.scheduler.core;

public class ProcessResult {
    private String processName;
    private int waitingTime;
    private int turnaroundTime;
    private int completionTime;

    public ProcessResult(String processName, int waitingTime, int turnaroundTime, int completionTime) {
        this.processName = processName;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
        this.completionTime = completionTime;
    }

    // Getters
    public String getProcessName() {
        return processName;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    // Setters
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }
} 
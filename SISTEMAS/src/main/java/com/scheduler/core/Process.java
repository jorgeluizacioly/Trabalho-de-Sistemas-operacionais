package com.scheduler.core;

public class Process {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void decrementRemainingTime() {
        setRemainingTime(getRemainingTime() - 1);
    }

    public boolean isCompleted() {
        return getRemainingTime() <= 0;
    }
} 
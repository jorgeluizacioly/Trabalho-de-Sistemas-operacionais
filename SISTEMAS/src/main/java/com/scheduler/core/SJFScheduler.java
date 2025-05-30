package com.scheduler.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SJFScheduler implements Scheduler {
    @Override
    public List<ProcessResult> schedule(List<Process> processes) {
        List<ProcessResult> results = new ArrayList<>();
        List<Process> readyQueue = new ArrayList<>(processes);
        
        // Ordena por tempo de chegada
        readyQueue.sort(Comparator.comparingInt(Process::getArrivalTime));
        
        int currentTime = 0;
        PriorityQueue<Process> availableProcesses = new PriorityQueue<>(
            Comparator.comparingInt(Process::getBurstTime)
        );

        while (!readyQueue.isEmpty() || !availableProcesses.isEmpty()) {
            // Adiciona processos que chegaram à fila de disponíveis
            while (!readyQueue.isEmpty() && readyQueue.get(0).getArrivalTime() <= currentTime) {
                availableProcesses.add(readyQueue.remove(0));
            }

            if (availableProcesses.isEmpty()) {
                // Avança o tempo até o próximo processo chegar
                currentTime = readyQueue.get(0).getArrivalTime();
                continue;
            }

            // Executa o processo com menor tempo de burst
            Process currentProcess = availableProcesses.poll();
            int startTime = currentTime;
            currentTime += currentProcess.getBurstTime();

            // Calcula métricas
            int waitingTime = startTime - currentProcess.getArrivalTime();
            int turnaroundTime = currentTime - currentProcess.getArrivalTime();

            // Adiciona resultado
            results.add(new ProcessResult(
                currentProcess.getName(),
                waitingTime,
                turnaroundTime,
                currentTime
            ));
        }

        return results;
    }
} 
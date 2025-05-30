package com.scheduler.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SRTScheduler implements Scheduler {
    @Override
    public List<ProcessResult> schedule(List<Process> processes) {
        List<ProcessResult> results = new ArrayList<>();
        List<Process> readyQueue = new ArrayList<>(processes);
        
        // Cria cópias dos processos para não modificar os originais
        List<Process> processCopies = new ArrayList<>();
        for (Process p : processes) {
            Process copy = new Process(p.getName(), p.getArrivalTime(), p.getBurstTime());
            processCopies.add(copy);
        }
        
        // Ordena por tempo de chegada
        processCopies.sort(Comparator.comparingInt(Process::getArrivalTime));
        
        int currentTime = 0;
        PriorityQueue<Process> availableProcesses = new PriorityQueue<>(
            Comparator.comparingInt(Process::getRemainingTime)
        );

        // Mapeia tempos de início e fim para cada processo
        int[] startTimes = new int[processes.size()];
        int[] endTimes = new int[processes.size()];
        boolean[] started = new boolean[processes.size()];

        while (!processCopies.isEmpty() || !availableProcesses.isEmpty()) {
            // Adiciona processos que chegaram à fila de disponíveis
            while (!processCopies.isEmpty() && processCopies.get(0).getArrivalTime() <= currentTime) {
                availableProcesses.add(processCopies.remove(0));
            }

            if (availableProcesses.isEmpty()) {
                // Avança o tempo até o próximo processo chegar
                currentTime = processCopies.get(0).getArrivalTime();
                continue;
            }

            // Executa o processo com menor tempo restante
            Process currentProcess = availableProcesses.poll();
            int processIndex = findProcessIndex(processes, currentProcess.getName());
            
            if (!started[processIndex]) {
                startTimes[processIndex] = currentTime;
                started[processIndex] = true;
            }

            // Executa por 1 unidade de tempo
            currentProcess.decrementRemainingTime();
            currentTime++;

            if (currentProcess.isCompleted()) {
                endTimes[processIndex] = currentTime;
                
                // Calcula métricas
                int waitingTime = (endTimes[processIndex] - startTimes[processIndex]) - 
                                processes.get(processIndex).getBurstTime();
                int turnaroundTime = endTimes[processIndex] - processes.get(processIndex).getArrivalTime();
                
                results.add(new ProcessResult(
                    currentProcess.getName(),
                    waitingTime,
                    turnaroundTime,
                    currentTime
                ));
            } else {
                availableProcesses.add(currentProcess);
            }
        }

        return results;
    }

    private int findProcessIndex(List<Process> processes, String name) {
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
} 
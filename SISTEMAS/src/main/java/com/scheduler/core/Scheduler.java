package com.scheduler.core;

import java.util.List;

public interface Scheduler {
    List<ProcessResult> schedule(List<Process> processes);
} 
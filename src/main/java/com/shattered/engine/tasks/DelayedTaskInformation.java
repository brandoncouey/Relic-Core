package com.shattered.engine.tasks;

import lombok.Data;

/**
 * @author JTlr Frost 11/6/2019 : 6:45 PM
 */
@Data
public class DelayedTaskInformation {

    /**
     * Represents the Delayed Task to execute.
     */
    private DelayedTask task;

    /**
     * Represents the Max / Last Tick
     */
    private int delayTime;
    
    public DelayedTaskInformation(DelayedTask task, int delayTime) {
        setTask(task);
        setDelayTime(delayTime);
    }
    
}

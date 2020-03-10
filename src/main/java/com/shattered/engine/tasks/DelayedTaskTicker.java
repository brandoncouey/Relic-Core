package com.shattered.engine.tasks;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author JTlr Frost 11/6/2019 : 6:44 PM
 */
public class DelayedTaskTicker {

    /**
     * Represents all of the Current Tasks
     */
    private static final List<DelayedTaskInformation> delayedTasks = Collections.synchronizedList(new LinkedList<DelayedTaskInformation>());


    /**
     * Processes all task
     */
    public static void tickTasks() {
        for (DelayedTaskInformation information : delayedTasks.toArray(new DelayedTaskInformation[delayedTasks.size()])) {
            if (information.getDelayTime() > 0) {
                information.setDelayTime(information.getDelayTime() - 1);
                continue;
            }
            information.getTask().run();
            information.getTask().setFinished(true);
            if (information.getTask().isFinished())
                delayedTasks.remove(information);
        }
    }


    /**
     *
     * @param task
     * @param delayAmount
     */
    public static void delayTask(DelayedTask task, int delayAmount) {
        if (task == null || delayAmount < 0) return;
        delayedTasks.add(new DelayedTaskInformation(task, delayAmount));
    }
    
}

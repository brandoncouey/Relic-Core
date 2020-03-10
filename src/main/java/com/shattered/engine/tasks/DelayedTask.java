package com.shattered.engine.tasks;

import lombok.Data;

/**
 * @author JTlr Frost 11/6/2019 : 6:42 PM
 */
@Data
public abstract class DelayedTask implements Runnable {

    /**
     * Represents if the Task needs removed
     */
    protected boolean finished;
    
}

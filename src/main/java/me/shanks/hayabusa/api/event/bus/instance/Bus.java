package me.shanks.hayabusa.api.event.bus.instance;

import me.shanks.hayabusa.api.event.bus.SimpleBus;
import me.shanks.hayabusa.api.event.bus.api.EventBus;

/**
 * Stores an {@link EventBus} instance.
 */
public class Bus
{
    /**
     * An EventBus instance.
     * <p>
     * Note that the implementation used currently
     * is {@link SimpleBus}, which doesn't support
     * posting Anonymous classes.
     */
    public static final EventBus EVENT_BUS = new SimpleBus();
    
}
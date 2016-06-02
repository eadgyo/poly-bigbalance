package org.polytech.polybigbalance.base;

/**
 * Created by ronan-j
 * Flags used for Layer event response
 */
public enum LayerEvent
{
    NO_COLLISION(1),
    COLLISION(1<<1),
    UPDATE(1<<2),
    UPDATE_STATE(1<<3),
    CLOSE(1<<4);

    private final long statusFlagValue;

    LayerEvent(long statusFlagValue)
    {
        this.statusFlagValue = statusFlagValue;
    }

    public long getStatusFlagValue()
    {
        return statusFlagValue;
    }
}

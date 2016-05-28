package org.polytech.polybigbalance.base;

/**
 * Flags used for Interface event response
 */
public enum InterfaceEvent
{
    OK(1),
    RESET(1<<1),
    POP(1<<2),
    MENU(1<<3),
    NEW_GAME(1<<4),
    LOAD_GAME(1<<5),
    SCORE(1<<6),
    CREDIT(1<<7),
    EXIT(1<<8);
    private final long statusFlagValue;

    InterfaceEvent(long statusFlagValue)
    {
        this.statusFlagValue = statusFlagValue;
    }

    public long getStatusFlagValue()
    {
        return statusFlagValue;
    }
}

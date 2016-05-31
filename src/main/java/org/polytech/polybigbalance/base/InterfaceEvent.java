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
    
    PLAY(1<<6),
    HOW_TO(1<<7),
    CREDIT(1<<8),
    EXIT(1<<9);
    
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

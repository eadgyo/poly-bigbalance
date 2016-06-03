package org.polytech.polybigbalance.base;

/**
 * @autor ronan-j
 */

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
    
    PLAY(1<<5),
    HOW_TO(1<<6),
    CREDIT(1<<7),
    EXIT(1<<8),
    
    PAUSE(1<<9);
    
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

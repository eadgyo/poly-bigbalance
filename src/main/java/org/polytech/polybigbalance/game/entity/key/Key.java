package org.polytech.polybigbalance.game.entity.key;

/**
 * Created by ronan-j on 06/06/16.
 */
public abstract class Key
{
    public KeyType type;

    public Key()
    {
        type = KeyType.LINEAR;
    }

    public Key(KeyType type)
    {
        this.type = type;
    }
}

package org.polytech.polybigbalance.game.entity.key;

/**
 * Created by ronan-j on 06/06/16.
 */
public class FloatKey extends Key
{
    public float value;

    public FloatKey(KeyType type, float value)
    {
        super(type);
        this.value = value;
    }

    public FloatKey(float value)
    {
        super();
        this.value = value;
    };
}
package org.polytech.polybigbalance.game.entity.key;

/**
 * Created by ronan-j on 06/06/16.
 */
public class FloatKey1 extends FloatKey
{
    public float f1;

    public FloatKey1(float value, float f1)
    {
        super(value);
        this.f1 = f1;
    }

    public FloatKey1(KeyType type, float value, float f1)
    {
        super(type, value);
        this.f1 = f1;
    }
}
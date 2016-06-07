package org.polytech.polybigbalance.game.entity.key;

/**
 * Created by ronan-j on 06/06/16.
 */
public class FloatKey2 extends FloatKey1
{
    public float f2;

    public FloatKey2(float value, float f1, float f2)
    {
        super(value, f1);
        this.f2 = f2;
    }

    public FloatKey2(KeyType type, float value, float f1, float f2)
    {
        super(type, value, f1);
        this.f2 = f2;
    }
}
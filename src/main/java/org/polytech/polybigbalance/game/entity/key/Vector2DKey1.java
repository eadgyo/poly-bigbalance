package org.polytech.polybigbalance.game.entity.key;

import org.cora.maths.Vector2D;

/**
 * Created by ronan-j on 06/06/16.
 */
public class Vector2DKey1 extends Vector2DKey
{
    public float f1;

    public Vector2DKey1(Vector2D value, float f1)
    {
        super(value);
        this.f1 = f1;
    }

    public Vector2DKey1(KeyType type, Vector2D value, float f1)
    {
        super(type, value);
        this.f1 = f1;
    }
}
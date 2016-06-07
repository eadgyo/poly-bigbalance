package org.polytech.polybigbalance.game.entity.key;

import org.cora.maths.Vector2D;

/**
 * Created by ronan-j on 06/06/16.
 */
public class Vector2DKey2 extends Vector2DKey1
{
    public float f2;

    public Vector2DKey2(Vector2D value, float f1, float f2)
    {
        super(value, f1);
        this.f2 = f2;
    }

    public Vector2DKey2(KeyType type, Vector2D value, float f1, float f2)
    {
        super(type, value, f1);
        this.f2 = f2;
    }
}
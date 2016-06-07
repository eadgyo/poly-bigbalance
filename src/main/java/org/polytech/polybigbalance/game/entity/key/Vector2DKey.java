package org.polytech.polybigbalance.game.entity.key;

import org.cora.maths.Vector2D;

/**
 * Created by ronan-j on 06/06/16.
 */
public class Vector2DKey extends Key
{
    public Vector2D value;

    public Vector2DKey(Vector2D value)
    {
        super();
        this.value = value;
    }

    public Vector2DKey(KeyType type, Vector2D value)
    {
        super(type);
        this.value = value;
    }
}

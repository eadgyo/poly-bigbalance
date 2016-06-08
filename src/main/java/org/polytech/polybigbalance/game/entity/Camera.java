package org.polytech.polybigbalance.game.entity;

import org.cora.graphics.graphics.Graphics;
import org.cora.maths.Vector2D;
import org.cora.maths.sRectangle;

/**
 * Created by ronan-j on 05/06/16. Mamene
 */

/**
 * Class that handle camera and allow scaling and position transitions
 * Init -- left pos
 * Pos -- center pos
 */
public class Camera extends Moveable
{

    public Camera()
    {
        super();
        form = new sRectangle();
    }

    public void initialize(float x, float y, float width, float height)
    {
        ((sRectangle) form).setLeft(x, y, width, height);
    }

    @Override
    public void updateRot(float dt)
    {
    }

    /**
     * Prepare camera pos and scale
     * @param g render tool
     */
    public void set(Graphics g)
    {
        sRectangle rec = (sRectangle) form;

        g.pushMatrix();
        g.translate(rec.getLeft());
        g.scale(rec.getScale());
    }

    /**
     * Remove effects
     *
     * @param g render tool
     */
    public void unset(Graphics g)
    {
        g.popMatrix();
    }

    /**
     * Get camera rec
     *
     * @return rec
     */
    public final sRectangle getRec()
    {
        return (sRectangle) form;
    }

    /**
     * Transform mouse in camera coordinates
     * @param mouse to be transformed
     */
    public void selfTransformMouse(Vector2D mouse)
    {
        sRectangle rec = (sRectangle) form;

        mouse.translate(rec.getLeft().multiply(-1));
        mouse.scale(1/rec.getScale(), new Vector2D());
    }

    /**
     * Transform mouse in camera coordinates
     * @param mouse origin coordinates
     * @return transformed mouse
     */
    public Vector2D transformMouse(Vector2D mouse)
    {
        Vector2D vec = (Vector2D) mouse.clone();
        selfTransformMouse(vec);
        return vec;
    }
}

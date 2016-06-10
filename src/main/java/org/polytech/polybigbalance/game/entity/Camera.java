package org.polytech.polybigbalance.game.entity;

import org.cora.graphics.graphics.Graphics;
import org.cora.maths.Vector2D;
import org.cora.maths.sRectangle;
import org.polytech.polybigbalance.Constants;

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
    private Vector2D vec;

    public Camera()
    {
        super();
        form = new sRectangle();
    }

    public void initialize(float x, float y, float width, float height)
    {
        ((sRectangle) form).set(x, y, width, height);

        vec = new Vector2D(-width*0.5f, -height*0.5f);
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
        float scale = rec.getScale();

        g.pushMatrix();

        Vector2D realCenter = rec.getCenter().multiply(scale);

        g.translateNeg(realCenter);
        g.scale(rec.getScale());
        g.translate(new Vector2D(Constants.WINDOW_WIDTH*0.5f*1/getScale(), Constants.WINDOW_HEIGHT*0.5f*1/getScale()));
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

        mouse.translate(rec.getLeft());
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

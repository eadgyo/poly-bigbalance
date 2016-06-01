package org.polytech.polybigbalance.base;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.cora.maths.Vector2D;
import org.cora.maths.sRectangle;

import java.util.EnumSet;
import java.util.Set;

/**
 * Used in interface to display group of objects
 * Coordinates are stored using float instead of int.
 * Float are used to make fluid motion
 */
public abstract class Layer
{
    private sRectangle bounds;

    public Layer()
    {
        bounds = new sRectangle();
    }

    /**
     * Initialize data of layer
     * @param x top left coordinate
     * @param y top left coordinate
     * @param width length of layer
     * @param height length of layer
     */
    public void initialize(int x, int y, int width, int height)
    {
        initialize((float) x, (float) y, (float) width, (float) height);
    }

    /**
     * Initialize data of layer
     * @param x top left coordinate
     * @param y top left coordinate
     * @param width length of layer
     * @param height length of layer
     */
    public void initialize(float x, float y, float width, float height)
    {
        bounds.set(x, y, width, height);
    }

    /**
     * Render layer on screen
     * @param g tool renderer
     */
    public abstract void render(Graphics g);

    /**
     * Update layer
     * @param dt time since last update
     */
    public abstract void update(float dt);

    /**
     * Change layer's bounds
     * @param x top left coordinate
     * @param y top left coordinate
     * @param width length of layer
     * @param height length of layer
     */
    public void setBounds(int x, int y, int width, int height)
    {
        bounds.set(x, y, width, height);
    }

    /**
     * Change layer's bounds
     * @param x top left coordinate
     * @param y top left coordinate
     * @param width length of layer
     * @param height length of layer
     */
    public void setBounds(float x, float y, float width, float height)
    {
        bounds.set(x, y, width, height);
    }

    /**
     * Test if a point is in layer
     * @param pos point coordinates
     * @return test result
     */
    public boolean isColliding(Vector2D pos)
    {
        return bounds.isInsideBorder(pos);
    }

    /**
     *
     * @param input inputs manager
     * @return events
     */
    public Set<LayerEvent> handleEvent(Input input) {return EnumSet.of(LayerEvent.NO_COLLISION); }

    /**
     *
     * @return layer's bounds
     */
    public sRectangle getBounds() { return bounds; }

    public float getWidth() { return bounds.getWidth(); }

    public float getHeight() { return bounds.getHeight(); }

    public float getLeftX() { return bounds.getLeftX(); }

    public float getLeftY() { return bounds.getLeftY(); }
}

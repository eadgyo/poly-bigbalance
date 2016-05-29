package org.polytech.polybigbalance.base;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;

import java.util.Set;

/**
 * Interface make easier menu creation
 * Interface starts at the origin of the screen
 */
public abstract class Interface
{
    private int width;
    private int height;

    public Interface()
    {
    }

    /**
     * Initialisation of Interface
     * @param width of interface
     * @param height of interface
     */
    public void initialize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public abstract Set<InterfaceEvent> update(float dt);

    public abstract Set<InterfaceEvent> handleEvent(Input input);

    public abstract void render(Graphics g);

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
}

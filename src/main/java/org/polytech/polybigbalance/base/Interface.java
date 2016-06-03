package org.polytech.polybigbalance.base;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.polytech.polybigbalance.Constants;

import java.util.Set;

/**
 * Created by ronan-j Interface make easier menu creation Interface starts at
 * the origin of the screen
 */
public abstract class Interface
{
    private int width;
    private int height;
    private Rectangle background;

    public Interface()
    {
        this.background = new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2), new Vector2D(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT), 0.0f);
    }

    /**
     * Initialisation of Interface
     * 
     * @param width
     *            of interface
     * @param height
     *            of interface
     */
    public void initialize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public abstract Set<InterfaceEvent> update(float dt);

    public abstract Set<InterfaceEvent> handleEvents(Input input);

    public void render(Graphics g)
    {
        g.setColor(Constants.BACKGROUND_COLOR);
        g.fillForm(this.background);
    }

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

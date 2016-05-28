package org.polytech.polybigbalance.layers;

import org.cora.graphics.graphics.Graphics;
import org.cora.maths.Form;
import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.cora.physics.Engine.Engine;
import org.cora.physics.entities.RigidBody;
import org.cora.physics.force.Gravity;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Layer;

import java.util.HashMap;
import java.util.Map;

/**
 * General definition of a level
 */
public abstract class Level extends Layer
{
    private final int BASE;

    protected Map<Rectangle, RigidBody> rectangles;
    private Rectangle groundForm;

    private Rectangle drawingRectangle;
    private Vector2D drawingFirstPoint;

    protected Engine physEngine;
    protected Gravity gravity;

    /**
     * @param base value use to calculate the maximum and minimum sizes of drawn rectangles
     */
    public Level(int base)
    {
        super();
        super.initialize(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.rectangles = new HashMap<>();

        this.BASE = base;

        this.physEngine = new Engine();
        this.gravity = new Gravity(new Vector2D(0, 100.0f));
    }

    /**
     * Adds the starting elements of the level
     */
    public void initialize()
    {
        this.groundForm = new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 25),
                new Vector2D(Constants.WINDOW_WIDTH + 1, 51), 0);
        this.groundForm.endForm();

        RigidBody ground = new RigidBody();
        ground.setForm(this.groundForm);
        ground.setPosition(this.groundForm.getCenter());
        ground.initPhysics();
        this.physEngine.addElement(ground);
    }

    /**
     * Allows the user to draw a rectangle
     * @param position mouse cursor's position
     */
    public void drawRectangle(Vector2D position)
    {
        if(this.drawingRectangle != null)
        {
            Vector2D center = new Vector2D((this.drawingFirstPoint.x + position.x) / 2,
                    (this.drawingFirstPoint.y + position.y) / 2);

            Vector2D length = new Vector2D(Math.abs(this.drawingFirstPoint.x - position.x),
                    Math.abs(this.drawingFirstPoint.y - position.y));

            this.drawingRectangle.set(center, length, 0.0f);
        }
        else
        {
            this.drawingRectangle = new Rectangle(position, new Vector2D(0.0f, 0.0f), 0.0f);
            this.drawingFirstPoint = position;
        }
    }

    /**
     * Ends the drawing of the new rectangle and adds it to the list of existing rectangles
     */
    public void endDrawRectangle()
    {
        if(this.drawingRectangle != null)
        {
            if(this.isRectangleSizeValid(this.drawingRectangle.getWidth(), this.drawingRectangle.getHeight()))
            {
                this.drawingRectangle.endForm();

                RigidBody rect = new RigidBody();
                rect.setMass(100.0f);
                rect.setForm(this.drawingRectangle);
                rect.setPosition(this.drawingRectangle.getCenter());
                rect.initPhysics();

                this.physEngine.addElement(rect);
                this.physEngine.addForce(rect, this.gravity);

                this.rectangles.put(this.drawingRectangle, rect);
            }

            this.drawingRectangle = null;
        }
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(0.2f, 0.8f, 0.2f);
        g.drawForm(this.groundForm);
        g.fillForm(this.groundForm);

        g.setColor(0.2f, 0.2f, 1.0f);
        for(Form r : this.rectangles.keySet())
        {
            g.drawForm(r);
            g.fillForm(r);
        }


        if(this.drawingRectangle != null)
        {
            if(this.isRectangleSizeValid(this.drawingRectangle.getWidth(), this.drawingRectangle.getHeight()))
            {
                g.setColor(0.0f, 1.0f, 1.0f);
            }
            else
            {
                g.setColor(1.0f, 0.2f, 0.2f);
            }

            g.drawForm(this.drawingRectangle);
            g.fillForm(this.drawingRectangle);
        }
    }

    @Override
    public void update(float dt)
    {
        this.physEngine.update(dt);
    }

    private boolean isRectangleSizeValid(float width, float height)
    {
        if(width * height < this.BASE * this.BASE)
        {
            if(width < this.BASE * 2 && height < this.BASE * 2)
            {
                return true;
            }
        }

        return false;
    }
}

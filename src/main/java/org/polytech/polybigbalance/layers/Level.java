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
import org.polytech.polybigbalance.score.HighScores;

import java.util.HashMap;
import java.util.Map;

/**
 * General definition of a level
 */
public abstract class Level extends Layer
{
    private final int BASE;
    private final Vector2D MIN_SIZE = new Vector2D(2.0f, 2.0f);

    protected Map<Rectangle, RigidBody> rectangles;
    private Rectangle groundForm;

    private Rectangle drawingRectangle;
    private Vector2D drawingFirstPoint;

    protected Engine physEngine;
    protected Gravity gravity;

    private HighScores highScores;

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
        this.groundForm.updateCenter();

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
     * @return the drawn rectangle, or null if the rectangles has not been added
     */
    public Rectangle endDrawRectangle()
    {
        Rectangle drawnRectangle = null;

        if(this.drawingRectangle != null)
        {
            if(this.isRectangleSizeValid(this.drawingRectangle.getWidth(), this.drawingRectangle.getHeight()) &&
                    !this.isRectangleColliding(this.drawingRectangle))
            {
                this.drawingRectangle.updateCenter();

                RigidBody rect = new RigidBody();
                rect.setMass(100.0f);
                rect.setForm(this.drawingRectangle);
                rect.setPosition(this.drawingRectangle.getCenter());
                rect.initPhysics();

                this.physEngine.addElement(rect);
                this.physEngine.addForce(rect, this.gravity);

                this.rectangles.put(this.drawingRectangle, rect);

                drawnRectangle = this.drawingRectangle;
            }

            this.drawingRectangle = null;
        }

        return drawnRectangle;
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
            if(this.isRectangleSizeValid(this.drawingRectangle.getWidth(), this.drawingRectangle.getHeight()) &&
                    !this.isRectangleColliding(this.drawingRectangle))
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

    /**
     * Tells if the rectangle is not too large
     * @param width rectangle's width
     * @param height rectangle's height
     * @return true if the rectangle's size is valid
     */
    private boolean isRectangleSizeValid(float width, float height)
    {
        if(width > MIN_SIZE.x && height > MIN_SIZE.y)
        {
            if(width * height < this.BASE * this.BASE)
            {
                if(width < this.BASE * 2 && height < this.BASE * 2)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Tells if the rectangle is colliding with one of the inserted ones
     * @param rect rectangle to check
     * @return true if there is a collision
     */
    private boolean isRectangleColliding(Rectangle rect)
    {
        for(Rectangle r : this.rectangles.keySet())
        {
            if(rect.getLeftX() < r.getLeftX() + r.getWidth() && rect.getLeftX() + rect.getWidth() > r.getLeftX() &&
                    rect.getLeftY() < r.getLeftY() + r.getHeight() && rect.getLeftY() + rect.getHeight() > r.getLeftY())
            {
                return true;
            }
        }

        return false;
    }
}

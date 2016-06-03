/**
 * @author Hugo PIGEON
 */

package org.polytech.polybigbalance.layers;

import org.cora.graphics.graphics.Graphics;
import org.cora.maths.Form;
import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.cora.maths.collision.CollisionDetectorNoT;
import org.cora.physics.Engine.Engine;
import org.cora.physics.entities.RigidBody;
import org.cora.physics.entities.material.MaterialType;
import org.cora.physics.force.Gravity;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Layer;
import org.polytech.polybigbalance.score.HighScores;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * General definition of a level
 */
public abstract class Level extends Layer
{
    private final int BASE;
    private final Vector2D MIN_SIZE = new Vector2D(2.0f, 2.0f);

    // baseRectangles are allowed to touch the ground
    protected Map<Rectangle, RigidBody> baseRectangles;
    protected Map<Rectangle, RigidBody> playerRectangles;
    private Rectangle groundForm;

    private Rectangle drawingRectangle;
    private Vector2D drawingFirstPoint;

    protected Engine physEngine;
    protected Gravity gravity;

    protected MaterialType sticking;

    protected HighScores highScores;

    /**
     * @param base
     *            value use to calculate the maximum and minimum sizes of drawn
     *            playerRectangles
     */
    public Level(int base)
    {
        super();
        super.initialize(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        this.playerRectangles = new HashMap<>();
        this.baseRectangles = new HashMap<>();

        this.BASE = base;

        this.physEngine = new Engine();
        this.physEngine.setMinDt(0.02f);
        this.physEngine.setThresholdSideDetection(0.06411002f);
        this.physEngine.setDefaultFriction(0.24001002f);
        this.gravity = new Gravity(new Vector2D(0, 200.0f));
        this.sticking = new MaterialType(0.01f);
        this.sticking.addMaterialInformation(sticking, 0.0f, 0.24001002f, 1.0f);
    }

    /**
     * Adds the starting elements of the level
     */
    public void initialize()
    {
        this.groundForm = new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 25), new Vector2D(Constants.WINDOW_WIDTH + 1, 51), 0);
        this.groundForm.updateCenter();

        RigidBody ground = new RigidBody();
        ground.setForm(this.groundForm);
        ground.setPosition(this.groundForm.getCenter());
        ground.setMaterialType(sticking);
        this.physEngine.addElement(ground);
    }

    /**
     * Allows the user to draw a rectangle
     * 
     * @param position
     *            mouse cursor's position
     */
    public void drawRectangle(Vector2D position)
    {
        if (this.drawingRectangle != null) {
            Vector2D center = new Vector2D((this.drawingFirstPoint.x + position.x) / 2, (this.drawingFirstPoint.y + position.y) / 2);

            Vector2D length = new Vector2D(Math.abs(this.drawingFirstPoint.x - position.x), Math.abs(this.drawingFirstPoint.y - position.y));

            this.drawingRectangle.set(center, length, 0.0f);
        } else {
            this.drawingRectangle = new Rectangle(position, new Vector2D(0.0f, 0.0f), 0.0f);
            this.drawingFirstPoint = position;
        }
    }

    /**
     * Ends the drawing of the new rectangle and adds it to the list of existing
     * playerRectangles
     * 
     * @return the drawn rectangle, or null if the playerRectangles has not been
     *         added
     */
    public Rectangle endDrawRectangle()
    {
        Rectangle drawnRectangle = null;

        if (this.drawingRectangle != null)
        {
            if (this.isRectangleSizeValid(this.drawingRectangle.getWidth(), this.drawingRectangle.getHeight()) &&
                !this.isRectangleColliding(this.drawingRectangle))
            {
                this.checkRectangleFallen();

                this.drawingRectangle.updateCenter();

                RigidBody rect = new RigidBody();
                rect.setForm(this.drawingRectangle);
                rect.setPosition(this.drawingRectangle.getCenter());
                rect.setMaterialType(sticking);
                rect.initPhysics();

                this.physEngine.addElement(rect);
                this.physEngine.addForce(rect, this.gravity);

                this.playerRectangles.put(this.drawingRectangle, rect);

                drawnRectangle = this.drawingRectangle;
            }

            this.drawingRectangle = null;
        }

        return drawnRectangle;
    }

    @Override
    public void render(Graphics g)
    {
        g.setLineSize(1);

        g.setColor(0.2f, 0.5f, 0.2f);
        g.fillForm(this.groundForm);
        g.setColor(0.0f, 0.0f, 0.0f);
        g.drawForm(this.groundForm);

        for (Form f : this.baseRectangles.keySet()) {
            g.setColor(0.2f, 0.2f, 1.0f);
            g.fillForm(f);
            g.setColor(0.0f, 0.0f, 0.0f);
            g.drawForm(f);
        }

        for (Form f : this.playerRectangles.keySet()) {
            g.setColor(0.2f, 0.2f, 1.0f);
            g.fillForm(f);
            g.setColor(0.0f, 0.0f, 0.0f);
            g.drawForm(f);
        }

        if (this.drawingRectangle != null) {
            if (this.isRectangleSizeValid(this.drawingRectangle.getWidth(), this.drawingRectangle.getHeight()) &&
                !this.isRectangleColliding(this.drawingRectangle))
            {
                g.setColor(0.0f, 1.0f, 1.0f);
            } else {
                g.setColor(1.0f, 0.2f, 0.2f);
            }

            g.fillForm(this.drawingRectangle);
            g.setColor(0.0f, 0.0f, 0.0f);
            g.drawForm(this.drawingRectangle);
        }
        g.setLineSize(1);
    }

    @Override
    public void update(float dt)
    {
        this.physEngine.update(dt);
    }

    /**
     * Looks for rectangles which are touching the ground and put them in base
     * rectangles
     * 
     * @return the number of rectangles that are touching the ground
     */
    public int checkRectangleFallen()
    {
        List<Rectangle> toRemove = new LinkedList<>();

        for (Rectangle r : this.playerRectangles.keySet())
        {
            Form f = r.clone();
            f.scale(1.01f);

            if (CollisionDetectorNoT.isColliding(this.groundForm, f))
            {
                this.baseRectangles.put(r, this.playerRectangles.get(r));
                toRemove.add(r);
            }
        }

        for (Rectangle r : toRemove)
        {
            this.playerRectangles.remove(r);
        }

        return toRemove.size();
    }

    public HighScores getHighScores()
    {
        return highScores;
    }

    public void setHighScores(HighScores hs)
    {
        this.highScores = hs;
    }

    /**
     * Initializes the rectangles created when the game is started
     * 
     * @param rectangles
     *            the rectangles to initialize
     */
    protected void initRectangles(Map<Rectangle, RigidBody> rectangles)
    {
        for (Rectangle rectForm : rectangles.keySet()) {
            rectForm.updateCenter();
            RigidBody rect = rectangles.get(rectForm);

            rect.setForm(rectForm);
            rect.setPosition(rectForm.getCenter());
            rect.setMaterialType(sticking);
            rect.initPhysics(100);
            float mass = rect.getMass();

            this.physEngine.addElement(rect);
            this.physEngine.addForce(rect, this.gravity);
        }
    }

    /**
     * Tells if the rectangle is not too large
     * 
     * @param width
     *            rectangle's width
     * @param height
     *            rectangle's height
     * @return true if the rectangle's size is valid
     */
    private boolean isRectangleSizeValid(float width, float height)
    {
        if (width > MIN_SIZE.x && height > MIN_SIZE.y) {
            if (width * height < this.BASE * this.BASE) {
                if (width < this.BASE * 2 && height < this.BASE * 2) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Tells if the rectangle is colliding with one of the inserted ones
     *
     * @param rect
     *            rectangle to check
     * @return true if there is a collision
     */
    private boolean isRectangleColliding(Rectangle rect)
    {
        for (Rectangle r : this.baseRectangles.keySet()) {
            if (CollisionDetectorNoT.isColliding(rect, r)) {
                return true;
            }
        }

        for (Rectangle r : this.playerRectangles.keySet()) {
            if (CollisionDetectorNoT.isColliding(rect, r)) {
                return true;
            }
        }

        return CollisionDetectorNoT.isColliding(rect, this.groundForm);
    }
}

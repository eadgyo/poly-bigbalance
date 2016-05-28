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
    protected Map<Rectangle, RigidBody> rectangles;
    private Rectangle groundForm;

    protected Engine physEngine;
    protected Gravity gravity;

    public Level()
    {
        super();
        super.initialize(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.rectangles = new HashMap<>();

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

        RigidBody ground = new RigidBody();
        ground.setForm(this.groundForm);
        ground.setPosition(this.groundForm.getCenter());
        ground.initPhysics();
        this.physEngine.addElement(ground);
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
    }

    @Override
    public void update(float dt)
    {
        this.physEngine.update(dt);
    }
}

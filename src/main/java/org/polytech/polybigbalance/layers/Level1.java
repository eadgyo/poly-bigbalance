package org.polytech.polybigbalance.layers;

import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.cora.physics.entities.RigidBody;
import org.polytech.polybigbalance.Constants;

/**
 * Level 1
 */
public class Level1 extends Level
{
    public Level1()
    {
        super(100);
    }

    @Override
    public void initialize()
    {
        super.initialize();

        this.rectangles.put(new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2),
                new Vector2D(200, 100), 0.0f), new RigidBody());

        for(Rectangle rectForm : this.rectangles.keySet())
        {
            rectForm.updateCenter();
            RigidBody rect = this.rectangles.get(rectForm);

            rect.setMass(100.0f);
            rect.setForm(rectForm);
            rect.setPosition(rectForm.getCenter());
            rect.initPhysics();

            this.physEngine.addElement(rect);
            this.physEngine.addForce(rect, this.gravity);
        }
    }
}

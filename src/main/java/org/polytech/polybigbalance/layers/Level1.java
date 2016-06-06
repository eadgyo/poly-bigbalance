/**
 * @author Hugo PIGEON
 */

package org.polytech.polybigbalance.layers;

import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.cora.physics.entities.RigidBody;

import java.util.ArrayList;

/**
 * Level 1
 */
public class Level1 extends LevelGround
{
    public Level1()
    {
        super(100);
    }

    @Override
    public void initialize(float x, float y, float width, float height)
    {
        super.initialize(x, y, width, height);

        ArrayList<Rectangle> recs = new ArrayList<Rectangle>();

        recs.add(new Rectangle(new Vector2D(width / 2, height - 75),
                new Vector2D(50, 50), 0.0f));

        for (Rectangle rec : recs)
        {
            RigidBody r = new RigidBody();
            r.setForm(rec);
            r.setPosition(rec.getCenter());
            r.setMaterialType(sticking);
            r.initPhysics(100);

            engine.addElement(r);
            engine.addForce(r, gravity);
            baseEntities.add(r);
        }

        recs.clear();

        recs.add(new Rectangle(new Vector2D(width / 2, height - 125),
                new Vector2D(50, 50), 0.0f));

        recs.add(new Rectangle(new Vector2D(width / 2, height - 175),
                new Vector2D(50, 50), 0.0f));

        recs.add(new Rectangle(new Vector2D(width / 2, height - 225),
                new Vector2D(300, 50), 0.0f));

        recs.add(new Rectangle(new Vector2D(width / 2, height - 275),
                new Vector2D(50, 50), 0.0f));

        recs.add(new Rectangle(new Vector2D(width / 2, height - 325),
                new Vector2D(100, 50), 0.0f));

        for (Rectangle rec : recs)
        {
            RigidBody r = new RigidBody();
            r.setForm(rec);
            r.setPosition(rec.getCenter());
            r.setMaterialType(sticking);
            r.initPhysics(100);

            engine.addElement(r);
            engine.addForce(r, gravity);
            playerEntities.add(r);
        }
    }
}

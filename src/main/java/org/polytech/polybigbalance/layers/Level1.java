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

        this.baseRectangles.put(new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 75),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 125),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 175),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 225),
                new Vector2D(300, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 275),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(new Vector2D(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT - 325),
                new Vector2D(100, 50), 0.0f), new RigidBody());

        this.initRectangles(this.baseRectangles);
        this.initRectangles(this.playerRectangles);
    }
}

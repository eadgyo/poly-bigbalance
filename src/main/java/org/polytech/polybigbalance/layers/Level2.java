package org.polytech.polybigbalance.layers;

import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.cora.physics.entities.RigidBody;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.score.HighScores;

/**
 * Level 1
 */
public class Level2 extends Level
{
    public Level2()
    {
        super(100);

        // TODO load highscores from file
        this.highScores = new HighScores();
    }

    @Override
    public void initialize()
    {
        super.initialize();

        this.baseRectangles.put(new Rectangle(
                new Vector2D(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT - 75),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(
                new Vector2D(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT - 125),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(
                new Vector2D(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT - 175),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.baseRectangles.put(new Rectangle(
                new Vector2D(Constants.WINDOW_WIDTH / 2 + 200, Constants.WINDOW_HEIGHT - 75),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(
                new Vector2D(Constants.WINDOW_WIDTH / 2 + 200, Constants.WINDOW_HEIGHT - 125),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.playerRectangles.put(new Rectangle(
                new Vector2D(Constants.WINDOW_WIDTH / 2 + 200, Constants.WINDOW_HEIGHT - 175),
                new Vector2D(50, 50), 0.0f), new RigidBody());

        this.initRectangles(this.baseRectangles);
        this.initRectangles(this.playerRectangles);
    }
}

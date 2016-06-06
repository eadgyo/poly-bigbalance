package org.polytech.polybigbalance.layers;

import org.cora.graphics.graphics.Graphics;
import org.cora.maths.Rectangle;
import org.cora.maths.sRectangle;
import org.cora.physics.entities.Particle;
import org.cora.physics.entities.RigidBody;

import java.util.Set;

/**
 * Created by ronan-h on 05/06/16.
 */
public class LevelGround extends Level
{
    private Particle ground;
    private Particle savedGround;

    public LevelGround(float minSurface)
    {
        super(minSurface);

        ground = new RigidBody();
        ground.setForm(new Rectangle());
        ground.setMaterialType(sticking);
        engine.addElement(ground);
    }

    /**
     * Initialize data of layer
     * @param x top left coordinate
     * @param y top left coordinate
     * @param width length of layer
     * @param height length of layer
     */
    @Override
    public void initialize(float x, float y, float width, float height)
    {
        super.initialize(x, y, width, height);

        // Init ground
        Rectangle rec = (Rectangle) ground.getForm();
        rec.setLength(width + 1, 51);
        ground.setPosition(width*0.5f, height - 25);
    }

    @Override
    public void render(Graphics g, Set<Particle> inScreen)
    {
        super.render(g, inScreen);
        if (inScreen.contains(ground))
        {
            g.setColor(0.2f, 0.5f, 0.2f);
            g.fillForm(ground.getForm());
            g.setColor(0.0f, 0.0f, 0.0f);
            g.drawForm(ground.getForm());
        }
    }

    /**
     * @return ground collision test
     */
    @Override
    public boolean checkRectangleFallen()
    {
        sRectangle rec = (sRectangle) engine.getQTBound().clone();

        // Top y
        float groundY = ground.getForm().getY(0);
        float height = rec.getMaxY() - groundY;
        rec.set(rec.getCenterX(), groundY + height*0.5f, rec.getWidth(), Math.abs(height));

        // Don't forget last collision
        Set<Particle> collidings = engine.getWereCollidingWith(ground);
        if (collidings != null)
        {
            collidings.removeAll(baseEntities);
            if (collidings.size() != 0)
                return true;
        }

        // Get all collidings elements
        collidings = engine.getCollidingsSet(rec);
        collidings.remove(ground);
        collidings.removeAll(baseEntities);
        return (collidings.size() != 0);
    }

    /**
     * Save state of level entities
     */
    @Override
    public void saveEntities()
    {
        super.saveEntities();
        savedGround = (Particle) ground.clone();
    }
}

package org.polytech.polybigbalance.layers;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.maths.Form;
import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.cora.physics.Engine.Engine;
import org.cora.physics.entities.Particle;
import org.cora.physics.entities.RigidBody;
import org.cora.physics.entities.material.MaterialType;
import org.cora.physics.force.Gravity;
import org.polytech.polybigbalance.base.Layer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ronan-j on 08/06/16.
 */
public class Explosion extends Layer
{
    private Engine engine;
    private float t;
    private Set<Particle> ps;
    private boolean launched;
    private MaterialType rebound;

    public Explosion()
    {
        launched = false;
        ps = new HashSet();
        engine = new Engine();
        rebound = new MaterialType();
        rebound.addMaterialInformation(rebound, 0.2f, 0.3f, 1.0f);
    }

    @Override
    public void initialize(float x, float y, float width, float height)
    {
        super.initialize(x, y, width, height);
    }

    public void launch()
    {
        float deltaMin = (float) -(Math.PI);
        float deltaMax = (float) (Math.PI);

        float velMin = 300;
        float velMax = 600;

        t = 0;

        RigidBody body = new RigidBody();
        Rectangle rec1 = new Rectangle(this.getWidth()*0.5f, this.getHeight()+5, this.getWidth(), 10);
        body.setForm(rec1);
        body.setMaterialType(rebound);
        engine.addElement(body);

        Gravity gravity = new Gravity(new Vector2D(0, 500));

        for (int i = 0; i < 50; i++)
        {
            float delta = (float) Math.random()*(deltaMax - deltaMin) + deltaMin;
            float vel   = (float) Math.random()*(velMax - velMin) + velMin;

            Particle p = new Particle();

            Vector2D velocity = new Vector2D((float) Math.cos(delta), (float) Math.sin(delta));
            velocity.selfMultiply(vel);
            p.setVelocity(velocity);

            Rectangle rec = new Rectangle(getWidth()/2, getHeight(), 6, 6);
            p.setForm(rec);
            p.setPosition(new Vector2D(getWidth()*0.5f, getHeight()/2));

            p.initPhysics(200);
            p.setMaterialType(rebound);
            engine.addElement(p);
            engine.addForce(p, gravity);
            ps.add(p);
        }

        for (Iterator<Particle> iterator = ps.iterator(); iterator.hasNext(); )
        {
            Particle next =  iterator.next();
            next.addNoCollisionElementsFree(ps);
        }

        launched = true;
    }

    @Override
    public void render(Graphics g)
    {
        if (!launched)
            return;

        Iterator<Particle> it = ps.iterator();

        g.setColor(new myColor(1.0f, 0.95f, 0.1f));
        while (it.hasNext())
        {
            Form f = it.next().getForm();
            g.fillForm(f);
        }
    }

    @Override
    public void update(float dt)
    {
        t += dt;
        if (!launched)
        {
            if (t > 1)
            {
                t = 0;
                launch();
            }
            else
                return;
        }

        if (t > 3 && ps.size() != 0)
        {
            engine.removeElement(ps.iterator().next());
            ps.remove(ps.iterator().next());
            t = 2.9f;
        }

        engine.update(dt);

    }
}

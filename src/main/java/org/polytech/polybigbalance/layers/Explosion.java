package org.polytech.polybigbalance.layers;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.maths.Form;
import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.cora.physics.Engine.Engine;
import org.cora.physics.entities.Particle;
import org.cora.physics.entities.RigidBody;
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

    public Explosion()
    {
        engine = new Engine();
    }

    @Override
    public void initialize(float x, float y, float width, float height)
    {
        super.initialize(x, y, width, height);

        float deltaMin = (float) (Math.PI/6);
        float deltaMax = (float) (5*Math.PI/6);

        float velMin = 300;
        float velMax = 600;

        RigidBody body = new RigidBody();
        Rectangle rec1 = new Rectangle(width*0.5f, height+5, width, 10);
        body.setForm(rec1);
        engine.addElement(body);

        Gravity gravity = new Gravity(new Vector2D(0, 500));

        Set<Particle> ps = new HashSet();
        for (int i = 0; i < 50; i++)
        {
            float delta = (float) Math.random()*(deltaMax - deltaMin) + deltaMin;
            float vel   = (float) Math.random()*(velMax - velMin) + velMin;

            Particle p = new Particle();

            Vector2D velocity = new Vector2D((float) Math.cos(delta), (float) Math.sin(delta));
            velocity.selfMultiply(vel);
            p.setVelocity(velocity);

            Rectangle rec = new Rectangle(width/2, height, 6, 6);
            p.setForm(rec);
            p.setPosition(new Vector2D(width*0.5f, -200));

            p.initPhysics(200);
            engine.addElement(p);
            engine.addForce(p, gravity);
            ps.add(p);
        }

        for (Iterator<Particle> iterator = ps.iterator(); iterator.hasNext(); )
        {
            Particle next =  iterator.next();
            next.addNoCollisionElementsFree(ps);
        }

    }

    @Override
    public void render(Graphics g)
    {
        Set<Particle> r = engine.getAllElements();
        Iterator<Particle> it = r.iterator();

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
        engine.update(dt);
    }
}

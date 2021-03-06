package org.polytech.polybigbalance.layers;

/**
 * Inspired form Level
 */

import org.cora.graphics.graphics.Graphics;
import org.cora.maths.Form;
import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.cora.maths.sRectangle;
import org.cora.physics.Engine.Engine;
import org.cora.physics.Engine.QuadTree;
import org.cora.physics.entities.Particle;
import org.cora.physics.entities.RigidBody;
import org.cora.physics.entities.material.MaterialType;
import org.cora.physics.force.Gravity;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Layer;
import org.polytech.polybigbalance.game.entity.Camera;
import org.polytech.polybigbalance.game.entity.Entity;
import org.polytech.polybigbalance.game.entity.key.*;
import org.polytech.polybigbalance.interfaces.LevelState;
import org.polytech.polybigbalance.score.HighScores;

import java.util.*;

/**
 * General definition of a level
 */
public abstract class Level extends Layer
{
    private static final int DEFAULT_MIN_SIZE = 10;
    private static final long DEFAULT_WAIT_TIME = 1000; // 5 sec

    //private final int MIN_WIDTH;
    private float minSurface;
    private float minSizeWidth = 2.0f;
    private float minSizeheight = 2.0f;

    private Rectangle drawingRectangle;
    private Vector2D drawingFirstPoint;
    private long waitTime;

    protected LevelState levelState;

    // Moveable entities
    protected Set<Entity> decorativeEntities;

    // Base entities on the ground
    protected Set<Particle> baseEntities;
    protected Set<Particle> playerEntities;
    protected Set<Particle> savedBaseEntities;
    protected Set<Particle> savedPlayerEntities;

    protected long waitStartTime;
    protected boolean paused;

    protected QuadTree savedQuadTree;
    protected Engine engine;
    protected Camera camera;

    protected Gravity gravity;
    protected MaterialType sticking;

    protected HighScores highScores;

    public Level(float minSurface)
    {
        super();

        baseEntities = new HashSet<Particle>();
        playerEntities = new HashSet<Particle>();
        decorativeEntities = new HashSet<Entity>();

        engine = new Engine();
        engine.setMinDt(0.015f);
        engine.setThresholdSideDetection(0.06411002f);
        engine.setDefaultFriction(0.24001002f);
        gravity = new Gravity(new Vector2D(0, 200.0f));
        sticking = new MaterialType(0.005f);
        sticking.addMaterialInformation(sticking, 0.0f, 0.24001002f, 1.0f);

        savedBaseEntities = new HashSet<Particle>();
        savedPlayerEntities = new HashSet<Particle>();
        paused = false;

        camera = new Camera();
        waitStartTime = 0;
        waitTime = DEFAULT_WAIT_TIME;

        this.minSurface = minSurface;

        levelState = LevelState.INTRO;
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
        camera.initialize(x + width*0.5f, y + height*0.5f, width, height);
    }

    /**
     * Start camera translating to level
     */
    public void startIntroAnimation()
    {
        camera.clear();

        // Create camera translation
        Vector2DKey1 start = new Vector2DKey1(KeyType.EXP, new Vector2D(getWidth()*0.5f, -1500), -10.0f);
        Vector2DKey end = new Vector2DKey(new Vector2D(getWidth()*0.5f, getHeight()*0.5f));

        // Create camera animation
        camera.addPosKey(0, start);
        camera.addPosKey(1, end);

        // Create decorative elements
        // Create cloud
        Form form = new Form();
        form.addPoint(new Vector2D(0, 100));
        form.addPoint(new Vector2D(20, 50));
        form.addPoint(new Vector2D(150, 0));
        form.addPoint(new Vector2D(250, 5));
        form.addPoint(new Vector2D(300, 40));
        form.addPoint(new Vector2D(260, 180));
        form.addPoint(new Vector2D(160, 220));
        form.addPoint(new Vector2D(20, 170));

        float xs[] = {this.getWidth()/3   , 500};
        float xe[] = {-this.getWidth()/3 , this.getWidth()};

        float ys[] = {-this.getHeight() + 100, -100};
        float ye[] = {-this.getHeight() + 100, -100};

        for (int i = 0; i < xs.length; i++)
        {
            Entity cloud = new Entity((Form) form.clone());
            Vector2DKey startCloud = new Vector2DKey(new Vector2D(xs[i], ys[i]));
            Vector2DKey endCloud = new Vector2DKey(new Vector2D(xe[i], ye[i]));
            cloud.addPosKey(0, startCloud);
            cloud.addPosKey(10, endCloud);
            cloud.getColor().a = 0.6f;
            decorativeEntities.add(cloud);
        }
    }

    public void startEndAnimation()
    {
        camera.clear();

        sRectangle rec = savedQuadTree.getRect();

        camera.clearPos();
        camera.clearScale();

        float scale = (Constants.WINDOW_HEIGHT)/(rec.getHeight()*1.1f);

        // Create camera translation
        Vector2DKey1 start = new Vector2DKey1(KeyType.EXP, camera.getRec().getCenter(), -2.0f);
        Vector2D a = (Vector2D) rec.getCenter().clone();
        a.x = getWidth()*0.5f;
        //a.y -= rec.getHeight()*0.025f;

        Vector2DKey end = new Vector2DKey(a);

        // Create camera animation
        camera.addPosKey(0, start);
        camera.addPosKey(1, end);

        // Create camera scaling
        FloatKey1 startScale = new FloatKey1(KeyType.EXP, camera.getRec().getScale(), -2.0f);
        FloatKey endScale = new FloatKey(scale);

        camera.addScaleKey(0, startScale);
        camera.addScaleKey(1, endScale);

        levelState = LevelState.END;
    }

    public sRectangle getLevelBound()
    {
        return engine.getQTBound();
    }

    /**
     * Check if intro animation is finished
     */
    public void checkFinishedIntro()
    {
        if (levelState == LevelState.INTRO && camera.hasFinishedPos())
        {
            levelState = LevelState.PLAY;
            camera.clear();
        }

        Iterator<Entity> it = decorativeEntities.iterator();
        if (it.next().hasFinishedPos())
        {
            decorativeEntities.clear();
            levelState = LevelState.PLAY;
        }
    }

    public void move(float a)
    {
        if (levelState != LevelState.PLAY)
            return;

        // We first check if we are allowed to move up
        // Just check the highest element from quadTree
        // code...
        camera.finishPos();
        camera.clearPos();

        Vector2D vec = (Vector2D) camera.getRec().getCenter().clone();

        // Create camera translation
        Vector2DKey start = new Vector2DKey1(KeyType.EXP, vec, -50.0f);
        Vector2DKey end = new Vector2DKey(vec.add(new Vector2D(0, - 100 * a)));

        // Create camera animation
        camera.addPosKey(0, start);
        camera.addPosKey(0.10f, end);
    }

    public boolean ready()
    {
        return levelState == LevelState.PLAY;
    }

    public LevelState getLevelState()
    {
        return levelState;
    }

    public void render(Graphics g, Set<Particle> inScreen)
    {
        for (Particle p : inScreen)
        {
            g.setColor(0.2f, 0.2f, 1.0f);
            g.fillForm(p.getForm());
            g.setColor(0.0f, 0.0f, 0.0f);
            g.drawForm(p.getForm());
        }

        if (drawingRectangle != null)
        {
            if (isRectangleSizeValid(drawingRectangle.getWidth(), drawingRectangle.getHeight())
                    && !isRectangleColliding(drawingRectangle))
            {
                if (waitStartTime == 0)
                    g.setColor(0.0f, 1.0f, 1.0f);
                else
                    g.setColor(1.0f, 0.5f, 0.5f);
            }
            else
            {
                g.setColor(1.0f, 0.2f, 0.2f);
            }

            g.fillForm(this.drawingRectangle);
            g.setColor(0.0f, 0.0f, 0.0f);
            g.drawForm(this.drawingRectangle);
        }
        g.setLineSize(1);
    }

    @Override
    public void render(Graphics g)
    {
        camera.set(g);
        Set<Particle> inScreen;
        if (paused)
        {
            inScreen = new HashSet<>();
            savedQuadTree.retrieve(camera.getRec(), inScreen);
        }
        else
        {
            if (decorativeEntities.size() != 0)
            {
                for (Entity e : decorativeEntities)
                {
                    e.render(g);
                }
            }

            inScreen = engine.getCollisionsQTSet(camera.getRec());
        }
        render(g, inScreen);
        camera.unset(g);
    }

    @Override
    public void update(float dt)
    {
        if (!paused)
        {
            engine.update(dt);
            if (decorativeEntities.size() != 0)
            {
                for (Entity e : decorativeEntities)
                {
                    e.update(dt);
                }

                checkFinishedIntro();
            }
        }

        camera.update(dt);
    }

    public void resetRectangle()
    {
        this.drawingRectangle = null;
        this.drawingFirstPoint = null;
    }

    /**
     * @return ground collision test
     */
    public abstract boolean checkRectangleFallen();

    public HighScores getHighScores()
    {
        return highScores;
    }

    public void setHighScores(HighScores hs)
    {
        this.highScores = hs;
    }

    /**
     * Allows the user to draw a rectangle
     *
     * @param mouse
     *            mouse cursor's position
     */
    public void drawRectangle(Vector2D mouse)
    {
        Vector2D position = camera.transformMouse(mouse);

        if (this.drawingRectangle != null)
        {
            Vector2D center = new Vector2D((this.drawingFirstPoint.x + position.x) / 2, (this.drawingFirstPoint.y + position.y) / 2);

            Vector2D length = new Vector2D(Math.abs(this.drawingFirstPoint.x - position.x), Math.abs(this.drawingFirstPoint.y - position.y));

            this.drawingRectangle.set(center, length, 0.0f);
        }
        else
        {
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
        if (drawingRectangle != null)
        {
            if (this.isRectangleSizeValid(drawingRectangle.getWidth(), drawingRectangle.getHeight()) && !isRectangleColliding(this.drawingRectangle))
            {
                saveEntities();

                RigidBody rect = new RigidBody();
                rect.setForm(drawingRectangle);
                rect.setPosition(drawingRectangle.getCenter());
                rect.setMaterialType(sticking);
                rect.initPhysics();

                engine.addElement(rect);
                engine.addForce(rect, gravity);

                playerEntities.add(rect);

                drawnRectangle = drawingRectangle;
            }

            drawingRectangle = null;
        }
        return drawnRectangle;
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
    public boolean isRectangleSizeValid(float width, float height)
    {
        return (width > minSizeWidth && height > minSizeheight) &&
                (width * height < minSurface * minSurface) &&
                (width < minSurface * 2 && height < minSurface * 2);
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
        return engine.isColliding(rect);
    }

    /**
     * Save state of level entities
     */
    public void saveEntities()
    {
        Map<Particle, Particle> changes = new HashMap<>();
        saveEntities(changes);
        savedQuadTree = (QuadTree) engine.getQuadtree().clone(changes);
    }

    /**
     * Save state of level entities
     */
    public void saveEntities(Map<Particle, Particle> changes)
    {
        savedBaseEntities.clear();
        savedPlayerEntities.clear();

        for (Particle p : baseEntities)
        {
            Particle p1 = (Particle) p.clone();
            changes.put(p, p1);
            savedBaseEntities.add(p1);
        }

        for (Particle p : playerEntities)
        {
            Particle p1 = (Particle) p.clone();
            changes.put(p, p1);
            savedPlayerEntities.add(p1);
        }
    }

    /**
     * Restore last state of level
     */
    public void restoreEntities()
    {
        baseEntities.clear();
        playerEntities.clear();

        baseEntities.addAll(savedBaseEntities);
        playerEntities.addAll(savedPlayerEntities);

        paused = true;
    }

    public long getWaitStartTime()
    {
        return waitStartTime;
    }

    public void setWaitStartTime(long waitStartTime)
    {
        this.waitStartTime = waitStartTime;
    }

    public long getWaiTime()
    {
        return waitTime;
    }

    public void setWaitTime(long waitTime)
    {
        this.waitTime = waitTime;
    }

    public boolean hasFinishedWaiting()
    {
        return waitStartTime == 0;
    }

    public long getEndWaiting()
    {
        return waitStartTime + waitTime;
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }
}

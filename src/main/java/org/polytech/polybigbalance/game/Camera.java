package org.polytech.polybigbalance.game;

import org.cora.graphics.graphics.Graphics;
import org.cora.maths.Vector2D;
import org.cora.maths.sRectangle;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static org.cora.maths.utils.Interpolation.*;

/**
 * Created by ronan-j on 05/06/16. Mamene
 */

/**
 * Class that handle camera and allow scaling and position transitions
 * Init --> left pos
 * Pos --> center pos
 */
public class Camera
{
    public enum KeyType
    {
        LINEAR,
        EXP,
        LOG,
        POW,
    }

    private abstract class Key
    {
        public KeyType type;
    }

    public class ScalingKey extends Key
    {
        public float value;
    }

    public class ScalingKey1 extends ScalingKey
    {
        public float f1;
    }

    public class ScalingKey2 extends ScalingKey1
    {
        public float f2;
    }

    public class PosKey extends Key
    {
        public Vector2D value;
    }

    public class PosKey1 extends PosKey
    {
        public float f1;
    }

    public class PosKey2 extends PosKey1
    {
        public float f2;
    }

    private sRectangle rec;

    private NavigableMap<Double, ScalingKey> scalingKeys;
    private NavigableMap<Double, PosKey> posKeys;

    private double tScale;
    private double tPos;

    public Camera()
    {
        rec = new sRectangle();
        scalingKeys = new TreeMap<Double, ScalingKey>();
        posKeys = new TreeMap<Double, PosKey>();
    }

    public void initialize(float x, float y, float width, float height)
    {
        rec.setLeft(x, y, width, height);
    }

    /**
     * Prepare camera pos and scale
     * @param g render tool
     */
    public void set(Graphics g)
    {
        g.pushMatrix();
        g.translate(rec.getLeft());
        g.scale(rec.getScale());
    }

    /**
     * Remove effects
     * @param g render tool
     */
    public void unset(Graphics g)
    {
        g.popMatrix();
    }

    /**
     * Get camera rec
     * @return rec
     */
    public final sRectangle getRec()
    {
        return rec;
    }

    public void update(float dt)
    {
        updateScale(dt);
        updatePos(dt);
    }

    /**
     * Clear all keys
     */
    public void clear()
    {
        clearScale();
        clearPos();
    }

    /**
     * Reset dt to 0 and update
     */
    public void reset()
    {
        resetScale();
        resetPos();
    }

    /**
     * Clear scaling keys
     */
    public void clearScale()
    {
        scalingKeys.clear();
    }

    /**
     * Reset dt to 0 and update scale
     */
    public void resetScale()
    {
        tScale = 0;
        this.updateScale(0);
    }

    /**
     * Update camera scale
     *
     * @param dt time between frame
     */
    public void updateScale(float dt)
    {
        tScale += dt;

        // Get value
        float currentScale = getScaleValue(tScale);
        rec.setScale(currentScale);
    }

    /**
     * Add a key scale
     *
     * @param t time
     * @param s scaling key
     *
     * @return creation success
     */
    public boolean addScalingKey(double t, ScalingKey s)
    {
        if (scalingKeys.containsKey(t))
            return false;

        scalingKeys.put(t, s);
        return true;
    }

    /**
     * Remove a key scale
     *
     * @param t key time
     *
     * @return removed key or null if not exists
     */
    public ScalingKey removePos(double t)
    {
        return scalingKeys.remove(t);
    }

    /**
     * Get scale at given time
     *
     * @param t time
     *
     * @return scale
     */
    public float getScaleValue(double t)
    {
        // Get start and end scale
        Map.Entry<Double, ScalingKey> startE = scalingKeys.floorEntry(tScale);
        Map.Entry<Double, ScalingKey> endE = scalingKeys.higherEntry(tScale);

        if (startE == null)
            return rec.getScale();

        if (endE == null) // Just return start
            return startE.getValue().value;

        // Prepare stuff
        KeyType type = startE.getValue().type;
        float start = startE.getValue().value;
        float end = endE.getValue().value;
        float duration = (float) (endE.getKey() - startE.getKey());
        float time = (float) (t - startE.getKey());

        float f1, f2;
        // Get the right function
        switch (type)
        {
            case LINEAR:
                return linearInterpolation(start, end, duration, time);
            case EXP:
                f1 = ((ScalingKey1) startE.getValue()).f1;
                return expInterpolation(start, end, duration, time, f1);
            case LOG:
                f1 = ((ScalingKey1) startE.getValue()).f1;
                return logInterpolation(start, end, duration, time, f1);
            case POW:
                f1 = ((ScalingKey1) startE.getValue()).f1;
                f2 = ((ScalingKey2) startE.getValue()).f2;
                return powInterpolation(start, end, duration, time, f1, f2);
            default:
                return startE.getValue().value;
        }
    }


    /**
     * Clear pos keys
     */
    public void clearPos()
    {
        posKeys.clear();
    }

    /**
     * Reset dt to 0 and update pos
     */
    public void resetPos()
    {
        tPos = 0;
        this.updatePos(0);
    }

    /**
     * Update camera position
     *
     * @param dt time between frame
     */
    public void updatePos(float dt)
    {
        tPos += dt;

        // Get current pos
        Vector2D pos = getPosValue(tPos);
        rec.setPos(pos);
    }

    /**
     * Add a key position
     *
     * @param t time
     * @param p pos key
     *
     * @return creation success
     */
    public boolean addPosKey(double t, PosKey p)
    {
        if (posKeys.containsKey(t))
            return false;

        posKeys.put(t, p);
        return true;
    }

    /**
     * Remove a key position
     *
     * @param t key time
     *
     * @return removed key or null if not exists
     */
    public PosKey remove(float t)
    {
        return posKeys.remove(t);
    }

    /**
     * Get pos at given time
     *
     * @param t time
     *
     * @return scale
     */
    public Vector2D getPosValue(double t)
    {
        // Get start and end pos
        Map.Entry<Double, PosKey> startE = posKeys.floorEntry(tScale);
        Map.Entry<Double, PosKey> endE = posKeys.higherEntry(tScale);

        if (startE == null)
            return rec.getCenter();

        if (endE == null) // Just return start
            return startE.getValue().value;

        // Prepare stuff
        KeyType type = startE.getValue().type;
        Vector2D startV = startE.getValue().value;
        Vector2D endV = endE.getValue().value;
        Vector2D vec = endV.sub(startV);
        float start = 0;
        float end = vec.normalize();
        float duration = (float) (endE.getKey() - startE.getKey());
        float time = (float) (t - startE.getKey());

        float f1, f2, v;
        // Get the right function
        switch (type)
        {
            case LINEAR:
                v = linearInterpolation(start, end, duration, time);
                break;
            case EXP:
                f1 = ((PosKey1) startE.getValue()).f1;
                v = expInterpolation(start, end, duration, time, f1);
                break;
            case LOG:
                f1 = ((PosKey1) startE.getValue()).f1;
                v = logInterpolation(start, end, duration, time, f1);
                break;
            case POW:
                f1 = ((PosKey1) startE.getValue()).f1;
                f2 = ((PosKey2) startE.getValue()).f2;
                v = powInterpolation(start, end, duration, time, f1, f2);
                break;
            default:
                return startE.getValue().value;
        }
        return startV.add(vec.multiply(v));
    }
}

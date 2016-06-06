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

    public class ScaleKey extends Key
    {
        public float value;
    }

    public class ScaleKey1 extends ScaleKey
    {
        public float f1;
    }

    public class ScaleKey2 extends ScaleKey1
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

    private NavigableMap<Double, ScaleKey> scaleKeys;
    private NavigableMap<Double, PosKey> posKeys;

    private Map.Entry<Double, PosKey> currentPos;
    private Map.Entry<Double, ScaleKey> currentScale;

    private double tScale;
    private double tPos;

    public Camera()
    {
        rec = new sRectangle();
        scaleKeys = new TreeMap<Double, ScaleKey>();
        posKeys = new TreeMap<Double, PosKey>();

        currentPos = null;
        currentScale = null;
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
        tScale = 0;
        scaleKeys.clear();

        currentScale = null;
    }

    /**
     * Reset dt to 0 and update scale
     */
    public void resetScale()
    {
        tScale = 0;
        this.updateScale(0);

        currentScale = null;
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
    public boolean addScaleKey(double t, ScaleKey s)
    {
        if (scaleKeys.containsKey(t))
            return false;

        scaleKeys.put(t, s);
        return true;
    }

    /**
     * Remove a key scale
     *
     * @param t key time
     *
     * @return removed key or null if not exists
     */
    public ScaleKey removeScaleKey(double t)
    {
        return scaleKeys.remove(t);
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
        Map.Entry<Double, ScaleKey> startE = scaleKeys.floorEntry(tScale);
        Map.Entry<Double, ScaleKey> endE = scaleKeys.higherEntry(tScale);

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
                f1 = ((ScaleKey1) startE.getValue()).f1;
                return expInterpolation(start, end, duration, time, f1);
            case LOG:
                f1 = ((ScaleKey1) startE.getValue()).f1;
                return logInterpolation(start, end, duration, time, f1);
            case POW:
                f1 = ((ScaleKey1) startE.getValue()).f1;
                f2 = ((ScaleKey2) startE.getValue()).f2;
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
        tPos = 0;
        posKeys.clear();

        currentPos = null;
    }

    /**
     * Reset dt to 0 and update pos
     */
    public void resetPos()
    {
        tPos = 0;
        this.updatePos(0);

        currentPos = null;
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
    public PosKey removePosKey(float t)
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

    // Scale
    public Map.Entry<Double, ScaleKey> getFirstScaleEntry()
    {
        return scaleKeys.firstEntry();
    }

    public Map.Entry<Double, ScaleKey> getLastScaleEntry()
    {
        return scaleKeys.lastEntry();
    }

    public ScaleKey getFirstScaleKey()
    {
        return scaleKeys.firstEntry().getValue();
    }

    public ScaleKey getLastScaleKey()
    {
        return scaleKeys.lastEntry().getValue();
    }

    public double getFirstScaleTime()
    {
        return scaleKeys.firstEntry().getKey();
    }

    public double getLastScaleTime()
    {
        return scaleKeys.lastEntry().getKey();
    }

    public Map.Entry<Double, ScaleKey> getFloorScaleEntry(double t)
    {
        return scaleKeys.floorEntry(t);
    }

    public ScaleKey getFloorScaleKey(double t)
    {
        return scaleKeys.floorEntry(t).getValue();
    }

    public double getFloorScaleTime(double t)
    {
        return scaleKeys.floorEntry(t).getKey();
    }

    public Map.Entry<Double, ScaleKey> getCeilingScaleEntry(double t)
    {
        return scaleKeys.ceilingEntry(t);
    }

    public ScaleKey getCeilingScaleKey(double t)
    {
        return scaleKeys.ceilingEntry(t).getValue();
    }

    public double getCeilingScaleTime(double t)
    {
        return scaleKeys.ceilingEntry(t).getKey();
    }

    public Map.Entry<Double, ScaleKey> getLowerScaleEntry(double t)
    {
        return scaleKeys.lowerEntry(t);
    }

    public ScaleKey getLowerScaleKey(double t)
    {
        return scaleKeys.lowerEntry(t).getValue();
    }

    public double getLowerScaleTime(double t)
    {
        return scaleKeys.lowerEntry(t).getKey();
    }

    public Map.Entry<Double, ScaleKey> getHigherScaleEntry(double t)
    {
        return scaleKeys.higherEntry(t);
    }

    public ScaleKey getHigherScaleKey(double t)
    {
        return scaleKeys.higherEntry(t).getValue();
    }

    public double getHigherScaleTime(double t)
    {
        return scaleKeys.higherEntry(t).getKey();
    }

    public boolean hasFinishedScale()
    {
        return scaleKeys.size() == 0 || tScale >= scaleKeys.lastKey();
    }

    public double getTScale()
    {
        return tScale;
    }

    public Map.Entry<Double, ScaleKey> getCurrentScaleEntry(double t)
    {
        return currentScale;
    }

    public ScaleKey getCurrentScaleKey(double t)
    {
        return currentScale.getValue();
    }

    public double getCurrentScaleTime(double t)
    {
        return currentScale.getKey();
    }

    // Pos
    public Map.Entry<Double, PosKey> getFirstPosEntry()
    {
        return posKeys.firstEntry();
    }

    public Map.Entry<Double, PosKey> getFirstLastEntry()
    {
        return posKeys.lastEntry();
    }

    public PosKey getFirstPosKey()
    {
        return posKeys.firstEntry().getValue();
    }

    public PosKey getLastPosKey()
    {
        return posKeys.lastEntry().getValue();
    }

    public double getFirstPosTiùe()
    {
        return posKeys.firstEntry().getKey();
    }

    public double getLastPosTime()
    {
        return posKeys.lastEntry().getKey();
    }

    public Map.Entry<Double, PosKey> getFloorPosEntry(double t)
    {
        return posKeys.floorEntry(t);
    }

    public PosKey getFloorPosKey(double t)
    {
        return posKeys.floorEntry(t).getValue();
    }

    public double getFloorPosTime(double t)
    {
        return posKeys.floorEntry(t).getKey();
    }

    public Map.Entry<Double, PosKey> getCeilingPosEntry(double t)
    {
        return posKeys.ceilingEntry(t);
    }

    public PosKey getCeilingPosKey(double t)
    {
        return posKeys.ceilingEntry(t).getValue();
    }

    public double getCeilingPosTime(double t)
    {
        return posKeys.ceilingEntry(t).getKey();
    }

    public Map.Entry<Double, PosKey> getLowerPosEntry(double t)
    {
        return posKeys.lowerEntry(t);
    }

    public PosKey getLowerPosKey(double t)
    {
        return posKeys.lowerEntry(t).getValue();
    }

    public double getLowerPosTime(double t)
    {
        return posKeys.lowerEntry(t).getKey();
    }

    public Map.Entry<Double, PosKey> getHigherPosEntry(double t)
    {
        return posKeys.higherEntry(t);
    }

    public PosKey getHigherPosKey(double t)
    {
        return posKeys.higherEntry(t).getValue();
    }

    public double getHigherPosTime(double t)
    {
        return posKeys.higherEntry(t).getKey();
    }

    public boolean hasFinishedPos()
    {
        return posKeys.size() == 0 || tPos >= posKeys.lastKey();
    }

    public double getTPos()
    {
        return tPos;
    }

    public Map.Entry<Double, PosKey> getCurrentPosEntry(double t)
    {
        return currentPos;
    }

    public PosKey getCurrentPosKey(double t)
    {
        return currentPos.getValue();
    }

    public double getCurrentPosTime(double t)
    {
        return currentPos.getKey();
    }
}

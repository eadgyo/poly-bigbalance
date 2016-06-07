package org.polytech.polybigbalance.game.entity;

import org.cora.maths.Form;
import org.cora.maths.Vector2D;
import org.polytech.polybigbalance.game.entity.key.*;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import static org.cora.maths.utils.Interpolation.*;

/**
 * Created by ronan-j on 07/06/16.
 */
public abstract class Moveable
{
    protected Form form;

    private NavigableMap<Double, FloatKey> rotKeys;
    private NavigableMap<Double, FloatKey> scaleKeys;
    private NavigableMap<Double, Vector2DKey> posKeys;

    private Map.Entry<Double, FloatKey> currentRot;
    private Map.Entry<Double, FloatKey> currentScale;
    private Map.Entry<Double, Vector2DKey> currentPos;

    private double tRot;
    private double tScale;
    private double tPos;

    public Moveable()
    {
        rotKeys = new TreeMap<Double, FloatKey>();
        scaleKeys = new TreeMap<Double, FloatKey>();
        posKeys = new TreeMap<Double, Vector2DKey>();

        currentRot = null;
        currentPos = null;
        currentScale = null;
    }

    public Form getForm()
    {
        return form;
    }

    public void setForm(Form form)
    {
        this.form = form;
    }

    public void update(float dt)
    {
        updateRot(dt);
        updateScale(dt);
        updatePos(dt);
    }

    /**
     * Clear all keys
     */
    public void clear()
    {
        clearRot();
        clearScale();
        clearPos();
    }

    /**
     * Reset dt to 0 and update
     */
    public void reset()
    {
        resetRot();
        resetScale();
        resetPos();
    }

    /**
     * Clear scaling keys
     */
    public void clearRot()
    {
        tRot = 0;
        rotKeys.clear();

        currentRot = null;
    }

    /**
     * Reset dt to 0 and update rot
     */
    public void resetRot()
    {
        tRot = 0;
        this.updateRot(0);

        currentRot = null;
    }

    /**
     * Update camera rot
     *
     * @param dt time between frame
     */
    public void updateRot(float dt)
    {
        tRot += dt;

        // Get value
        float currentRot = getRotValue(tRot);
        form.setRadians(currentRot);
    }

    /**
     * Add a key rot
     *
     * @param t time
     * @param s scaling key
     *
     * @return creation success
     */
    public boolean addRotKey(double t, FloatKey s)
    {
        if (rotKeys.containsKey(t))
            return false;

        rotKeys.put(t, s);
        return true;
    }

    /**
     * Remove a key rot
     *
     * @param t key time
     *
     * @return removed key or null if not exists
     */
    public FloatKey removeRotKey(double t)
    {
        return rotKeys.remove(t);
    }

    /**
     * Get rot at given time
     *
     * @param t time
     *
     * @return rot
     */
    public float getRotValue(double t)
    {
        // Get start and end rot
        Map.Entry<Double, FloatKey> startE = rotKeys.floorEntry(tRot);
        Map.Entry<Double, FloatKey> endE = rotKeys.higherEntry(tRot);

        if (startE == null)
            return form.getOmega();

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
                f1 = ((FloatKey1) startE.getValue()).f1;
                return expInterpolation(start, end, duration, time, f1);
            case LOG:
                f1 = ((FloatKey1) startE.getValue()).f1;
                return logInterpolation(start, end, duration, time, f1);
            case POW:
                f1 = ((FloatKey1) startE.getValue()).f1;
                f2 = ((FloatKey2) startE.getValue()).f2;
                return powInterpolation(start, end, duration, time, f1, f2);
            default:
                return startE.getValue().value;
        }
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
        form.setScale(currentScale);
    }

    /**
     * Add a key scale
     *
     * @param t time
     * @param s scaling key
     *
     * @return creation success
     */
    public boolean addScaleKey(double t, FloatKey s)
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
    public FloatKey removeScaleKey(double t)
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
        Map.Entry<Double, FloatKey> startE = scaleKeys.floorEntry(tScale);
        Map.Entry<Double, FloatKey> endE = scaleKeys.higherEntry(tScale);

        if (startE == null)
            return form.getScale();

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
                f1 = ((FloatKey1) startE.getValue()).f1;
                return expInterpolation(start, end, duration, time, f1);
            case LOG:
                f1 = ((FloatKey1) startE.getValue()).f1;
                return logInterpolation(start, end, duration, time, f1);
            case POW:
                f1 = ((FloatKey1) startE.getValue()).f1;
                f2 = ((FloatKey2) startE.getValue()).f2;
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
        form.setPos(pos);
    }

    /**
     * Add a key position
     *
     * @param t time
     * @param p pos key
     *
     * @return creation success
     */
    public boolean addPosKey(double t, Vector2DKey p)
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
    public Vector2DKey removePosKey(float t)
    {
        return posKeys.remove(t);
    }

    /**
     * Get pos at given time
     *
     * @param t time
     *
     * @return pos
     */
    public Vector2D getPosValue(double t)
    {
        // Get start and end pos
        Map.Entry<Double, Vector2DKey> startE = posKeys.floorEntry(tScale);
        Map.Entry<Double, Vector2DKey> endE = posKeys.higherEntry(tScale);

        if (startE == null)
            return form.getCenter();

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
                f1 = ((Vector2DKey1) startE.getValue()).f1;
                v = expInterpolation(start, end, duration, time, f1);
                break;
            case LOG:
                f1 = ((Vector2DKey1) startE.getValue()).f1;
                v = logInterpolation(start, end, duration, time, f1);
                break;
            case POW:
                f1 = ((Vector2DKey1) startE.getValue()).f1;
                f2 = ((Vector2DKey2) startE.getValue()).f2;
                v = powInterpolation(start, end, duration, time, f1, f2);
                break;
            default:
                return startE.getValue().value;
        }
        return startV.add(vec.multiply(v));
    }

    // Rot
    public Map.Entry<Double, FloatKey> getFirstRotEntry()
    {
        return rotKeys.firstEntry();
    }

    public Map.Entry<Double, FloatKey> getLastRotEntry()
    {
        return rotKeys.lastEntry();
    }

    public FloatKey getFirstRotKey()
    {
        return rotKeys.firstEntry().getValue();
    }

    public FloatKey getLastRotKey()
    {
        return rotKeys.lastEntry().getValue();
    }

    public double getFirstRotTime()
    {
        return rotKeys.firstEntry().getKey();
    }

    public double getLastRotTime()
    {
        return rotKeys.lastEntry().getKey();
    }

    public Map.Entry<Double, FloatKey> getFloorRotEntry(double t)
    {
        return rotKeys.floorEntry(t);
    }

    public FloatKey getFloorRotKey(double t)
    {
        return rotKeys.floorEntry(t).getValue();
    }

    public double getFloorRotTime(double t)
    {
        return rotKeys.floorEntry(t).getKey();
    }

    public Map.Entry<Double, FloatKey> getCeilingRotEntry(double t)
    {
        return rotKeys.ceilingEntry(t);
    }

    public FloatKey getCeilingRotKey(double t)
    {
        return rotKeys.ceilingEntry(t).getValue();
    }

    public double getCeilingRotTime(double t)
    {
        return rotKeys.ceilingEntry(t).getKey();
    }

    public Map.Entry<Double, FloatKey> getLowerRotEntry(double t)
    {
        return rotKeys.lowerEntry(t);
    }

    public FloatKey getLowerRotKey(double t)
    {
        return rotKeys.lowerEntry(t).getValue();
    }

    public double getLowerRotTime(double t)
    {
        return rotKeys.lowerEntry(t).getKey();
    }

    public Map.Entry<Double, FloatKey> getHigherRotEntry(double t)
    {
        return rotKeys.higherEntry(t);
    }

    public FloatKey getHigherRotKey(double t)
    {
        return rotKeys.higherEntry(t).getValue();
    }

    public double getHigherRotTime(double t)
    {
        return rotKeys.higherEntry(t).getKey();
    }

    public boolean hasFinishedRot()
    {
        return rotKeys.size() == 0 || tRot >= rotKeys.lastKey();
    }

    public double getTRot()
    {
        return tRot;
    }

    public Map.Entry<Double, FloatKey> getCurrentRotEntry(double t)
    {
        return currentRot;
    }

    public FloatKey getCurrentRotKey(double t)
    {
        return currentRot.getValue();
    }

    public double getCurrentRotTime(double t)
    {
        return currentRot.getKey();
    }

    // Scale
    public Map.Entry<Double, FloatKey> getFirstScaleEntry()
    {
        return scaleKeys.firstEntry();
    }

    public Map.Entry<Double, FloatKey> getLastScaleEntry()
    {
        return scaleKeys.lastEntry();
    }

    public FloatKey getFirstScaleKey()
    {
        return scaleKeys.firstEntry().getValue();
    }

    public FloatKey getLastScaleKey()
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

    public Map.Entry<Double, FloatKey> getFloorScaleEntry(double t)
    {
        return scaleKeys.floorEntry(t);
    }

    public FloatKey getFloorScaleKey(double t)
    {
        return scaleKeys.floorEntry(t).getValue();
    }

    public double getFloorScaleTime(double t)
    {
        return scaleKeys.floorEntry(t).getKey();
    }

    public Map.Entry<Double, FloatKey> getCeilingScaleEntry(double t)
    {
        return scaleKeys.ceilingEntry(t);
    }

    public FloatKey getCeilingScaleKey(double t)
    {
        return scaleKeys.ceilingEntry(t).getValue();
    }

    public double getCeilingScaleTime(double t)
    {
        return scaleKeys.ceilingEntry(t).getKey();
    }

    public Map.Entry<Double, FloatKey> getLowerScaleEntry(double t)
    {
        return scaleKeys.lowerEntry(t);
    }

    public FloatKey getLowerScaleKey(double t)
    {
        return scaleKeys.lowerEntry(t).getValue();
    }

    public double getLowerScaleTime(double t)
    {
        return scaleKeys.lowerEntry(t).getKey();
    }

    public Map.Entry<Double, FloatKey> getHigherScaleEntry(double t)
    {
        return scaleKeys.higherEntry(t);
    }

    public FloatKey getHigherScaleKey(double t)
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

    public Map.Entry<Double, FloatKey> getCurrentScaleEntry(double t)
    {
        return currentScale;
    }

    public FloatKey getCurrentScaleKey(double t)
    {
        return currentScale.getValue();
    }

    public double getCurrentScaleTime(double t)
    {
        return currentScale.getKey();
    }

    // Pos
    public Map.Entry<Double, Vector2DKey> getFirstPosEntry()
    {
        return posKeys.firstEntry();
    }

    public Map.Entry<Double, Vector2DKey> getFirstLastEntry()
    {
        return posKeys.lastEntry();
    }

    public Vector2DKey getFirstPosKey()
    {
        return posKeys.firstEntry().getValue();
    }

    public Vector2DKey getLastPosKey()
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

    public Map.Entry<Double, Vector2DKey> getFloorPosEntry(double t)
    {
        return posKeys.floorEntry(t);
    }

    public Vector2DKey getFloorPosKey(double t)
    {
        return posKeys.floorEntry(t).getValue();
    }

    public double getFloorPosTime(double t)
    {
        return posKeys.floorEntry(t).getKey();
    }

    public Map.Entry<Double, Vector2DKey> getCeilingPosEntry(double t)
    {
        return posKeys.ceilingEntry(t);
    }

    public Vector2DKey getCeilingPosKey(double t)
    {
        return posKeys.ceilingEntry(t).getValue();
    }

    public double getCeilingPosTime(double t)
    {
        return posKeys.ceilingEntry(t).getKey();
    }

    public Map.Entry<Double, Vector2DKey> getLowerPosEntry(double t)
    {
        return posKeys.lowerEntry(t);
    }

    public Vector2DKey getLowerPosKey(double t)
    {
        return posKeys.lowerEntry(t).getValue();
    }

    public double getLowerPosTime(double t)
    {
        return posKeys.lowerEntry(t).getKey();
    }

    public Map.Entry<Double, Vector2DKey> getHigherPosEntry(double t)
    {
        return posKeys.higherEntry(t);
    }

    public Vector2DKey getHigherPosKey(double t)
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

    public Map.Entry<Double, Vector2DKey> getCurrentPosEntry(double t)
    {
        return currentPos;
    }

    public Vector2DKey getCurrentPosKey(double t)
    {
        return currentPos.getValue();
    }

    public double getCurrentPosTime(double t)
    {
        return currentPos.getKey();
    }
}

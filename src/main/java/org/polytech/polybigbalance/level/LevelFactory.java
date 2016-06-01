package org.polytech.polybigbalance.level;

import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.layers.Level1;
import org.polytech.polybigbalance.layers.Level2;

public abstract class LevelFactory
{
    private static final int LEVELS = 2;

    public static Level getNewLevel(int n)
    {
        Level level;

        switch (n) {
            case 0:
                level = getLevelOne();
                break;
            case 1:
                level = getLevelTwo();
                break;

            default:
                level = getLevelOne();
                break;
        }

        return level;
    }

    private static Level getLevelOne()
    {
        return new Level1();
    }

    private static Level getLevelTwo()
    {
        return new Level2();
    }

    public static int getNumberOfLevel()
    {
        return LevelFactory.LEVELS;
    }
}

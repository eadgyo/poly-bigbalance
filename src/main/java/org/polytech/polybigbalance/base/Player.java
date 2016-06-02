/**
 * @author Hugo PIGEON
 */

package org.polytech.polybigbalance.base;

/**
 * Defines a player
 */
public class Player
{
    private int score;
    private String defaultName;
    private String name;
    private boolean lost;

    public Player(String name)
    {
        this.defaultName = name;
        this.name = name;
        this.score = 0;
        this.lost = false;
    }

    public int getScore()
    {
        return score;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public void resetName() { this.name = defaultName; }

    public boolean hasLost()
    {
        return lost;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setLost(boolean lost)
    {
        this.lost = lost;
    }
}

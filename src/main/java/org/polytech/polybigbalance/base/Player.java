package org.polytech.polybigbalance.base;

/**
 * Defines a player
 */
public class Player
{
    private int score;
    private String name;
    private boolean lost;

    public Player(String name)
    {
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

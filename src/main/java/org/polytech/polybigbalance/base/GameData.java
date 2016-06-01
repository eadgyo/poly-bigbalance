/**
 * 
 * @author Tugdwal
 * 
 */

package org.polytech.polybigbalance.base;

public class GameData
{
    private int player;
    private int level;

    // ----- CONSTRUCTOR ----- //

    public GameData()
    {
        this.player = 0;
        this.level = 0;
    }

    // ----- GETTER ----- //

    public int getPlayer()
    {
        return this.player;
    }

    public int getLevel()
    {
        return this.level;
    }

    // ----- SETTER ----- //

    public void setPlayer(int n)
    {
        this.player = n;
    }

    public void setLevel(int n)
    {
        this.level = n;
    }
}

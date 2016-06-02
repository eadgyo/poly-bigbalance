package org.polytech.polybigbalance.score;

import java.io.Serializable;

/**
 * 
 * @author Tudal
 * 
 */

/**
 * 
 * Contains the name and the score of the player
 *
 */

public class Score implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final String player;
    private final int score;

    // ----- CONSTRUCTOR ----- //

    public Score(String player, int score)
    {
        this.player = player;
        this.score = score;
    }

    // ----- GETTER ----- //

    public String getPlayer()
    {
        return this.player;
    }

    public int getScore()
    {
        return this.score;
    }
}

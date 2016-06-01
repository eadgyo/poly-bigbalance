/**
 * 
 * @author Tugdwal
 * 
 */

package org.polytech.polybigbalance.score;

import java.io.Serializable;

public class Score implements Serializable
{
    private final String m_player;
    private final int m_score;

    // ----- CONSTRUCTOR ----- //

    public Score(String player, int score)
    {
        this.m_player = player;
        this.m_score = score;
    }

    // ----- GETTER ----- //

    public String getPlayer()
    {
        return this.m_player;
    }

    public int getScore()
    {
        return this.m_score;
    }

    @Override
    public String toString()
    {
        return "Score [" + m_player + ", " + m_score + "]";
    }

    // ----- SETTER ----- //
}

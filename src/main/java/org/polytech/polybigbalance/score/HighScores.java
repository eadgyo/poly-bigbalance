package org.polytech.polybigbalance.score;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 
 * @author Tudal
 * 
 */

/**
 * 
 * Contains an ordered list of scores
 *
 */

public class HighScores implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static final int MAX_SIZE = 5;

    private Score[] scores;
    private int size;

    // ----- CONSTRUCTOR ----- //

    public HighScores()
    {
        this.scores = new Score[HighScores.MAX_SIZE];
        this.size = 0;
    }

    // ----- GETTER ----- //

    public int getSize()
    {
        return this.size;
    }

    public Score[] getScore()
    {
        Score[] scores = new Score[this.size];

        for (int i = 0; i < this.size; i++)
        {
            scores[i] = this.scores[i];
        }

        return scores;
    }

    public Score getScore(int n)
    {
        if (n >= 0 && n < HighScores.MAX_SIZE)
        {
            return this.scores[n];
        }
        else
        {
            return null;
        }
    }

    public boolean isHighScore(int score)
    {
        return (score > 0) && (this.size < HighScores.MAX_SIZE || (score > this.scores[HighScores.MAX_SIZE - 1].getScore()));
    }

    @Override
    public String toString()
    {
        return "HighScores [m_scores=" + Arrays.toString(scores) + "]";
    }

    // ----- SETTER ----- //

    public void reset()
    {
        for (int i = 0; i < this.size; i++)
        {
            this.scores[i] = null;
        }
        this.size = 0;
    }

    public void deleteScore(int n)
    {
        if (n >= 0 && n < HighScores.MAX_SIZE)
        {
            this.scores[this.size] = null;
        }
    }

    public boolean addScore(Score score)
    {
        if (score.getScore() > 0)
        {
            for (int i = 0; i < this.size; i++)
            {
                if (score.getScore() > this.scores[i].getScore())
                {
                    for (int j = (this.size < HighScores.MAX_SIZE ? this.size++ : (this.size - 1)); j > i; j--)
                    {
                        this.scores[j] = this.scores[j - 1];
                    }
                    this.scores[i] = score;
                    return true;
                }
            }

            if (this.size < HighScores.MAX_SIZE)
            {
                this.scores[this.size++] = score;
                return true;
            }
        }

        return false;
    }
}

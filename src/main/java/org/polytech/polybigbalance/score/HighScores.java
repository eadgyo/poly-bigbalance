package org.polytech.polybigbalance.score;

import java.util.Arrays;

public class HighScores
{
    private static final int MAX_SIZE = 5;
    private int m_size;
    private Score[] m_scores;

    // ----- CONSTRUCTOR ----- //

    public HighScores()
    {
        this.m_size = 0;
        this.m_scores = new Score[HighScores.MAX_SIZE];
    }

    // ----- GETTER ----- //

    public int getSize()
    {
        return this.m_size;
    }

    public Score[] getScore()
    {
        Score[] scores = new Score[this.m_size];

        for (int i = 0; i < this.m_size; i++) {
            scores[i] = this.m_scores[i];
        }

        return scores;
    }

    public Score getScore(int n)
    {
        if (n >= 0 || n < HighScores.MAX_SIZE) {
            return this.m_scores[n];
        } else {
            return null;
        }
    }

    public boolean isHighScore(int score)
    {
        return this.m_size < HighScores.MAX_SIZE || (score > this.m_scores[HighScores.MAX_SIZE].getScore());
    }

    @Override
    public String toString()
    {
        return "HighScores [m_scores=" + Arrays.toString(m_scores) + "]";
    }

    // ----- SETTER ----- //

    public void reset()
    {
        for (int i = 0; i < this.m_size; i++) {
            this.m_scores[i] = null;
        }
        this.m_size = 0;
    }

    public boolean addScore(Score score)
    {
        if (isHighScore(score.getScore())) {
            return true;
        } else {
            return false;
        }
    }
}
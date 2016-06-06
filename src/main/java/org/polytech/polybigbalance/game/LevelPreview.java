package org.polytech.polybigbalance.game;

import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.Font;
import org.cora.graphics.font.TextPosition;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.score.HighScoresManager;
import org.polytech.polybigbalance.score.Score;

/**
 * @autor ronan-j
 * @autor Tudal-L
 * @date 01/06/16
 */

/**
 * Level preview and high scores
 */
public class LevelPreview extends TextButton
{
    private Level level;
    private TextRenderer scoreText;

    private int defaultwidth, defaultHeight;
    private int id;

    private int scoreFieldHeight;

    private HighScoresManager hsm;

    public LevelPreview(int x, int y, int width, int height, Font font, Level level, int id, HighScoresManager hsm)
    {
        super(x, y, width, height, font);
        this.level = level;

        level.initialize(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        this.setTextTopCenter();
        this.defaultwidth = width;
        this.defaultHeight = height;
        this.scoreText = (TextRenderer) getTextRenderer().clone();
        this.scoreText.setAlignement(Alignement.FULL);
        this.scoreText.setTextPosition(TextPosition.LEFT);
        this.scoreText.setWidth(16);
        this.scoreText.setVerticalSpacing(24);
        this.id = id;
        this.hsm = hsm;

        this.scoreFieldHeight = (this.hsm.getHighScores(this.id).getSize() + 1) * (this.scoreText.getHeight() + this.scoreText.getVerticalSpacing());
    }

    /**
     * Render on screen a level preview and associated high scores
     * 
     * @param g
     *            render tool
     */
    @Override
    public void render(Graphics g)
    {
        super.render(g);

        g.pushMatrix();

        int scoresHeight = (isHighlighted()) ? this.scoreFieldHeight : 0;

        // Compute x, y, width, height for rendering
        int y = (int) (getTextRenderer().getHeight() * 1.7f);
        int levelHeight = (int) (getHeight() - y - scoresHeight);

        int levelWidth = (int) (defaultwidth * levelHeight / defaultHeight);
        int x = (int) ((getWidth() - levelWidth) * 0.5f);

        g.setColor(myColor.WHITE());
        g.setOutpout((int) (x + getLeftX()), (int) (-y - getY() + Constants.WINDOW_HEIGHT + scoresHeight * 0.5f), levelWidth, levelHeight);

        g.fillRec(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        level.render(g);
        g.resetOuput();

        // Render high scores
        if (isHighlighted())
        {
            Score[] scores = this.hsm.getHighScores(id).getScore();

            y += levelHeight;
            scoreText.setMaxWidth(levelWidth);
            StringBuilder scorestxt = new StringBuilder();
            scorestxt.append("Scores:\n");

            // Create one string with all high scores
            for (int i = 0; i < scores.length; i++)
            {
                scorestxt.append(scores[i].getPlayer() + ": " + scores[i].getScore());
                scorestxt.append("\n");
            }
            scoreText.print(g, scorestxt.toString(), (int) (x + getLeftX()), (int) (y + getLeftY()));
        }

        g.popMatrix();
    }

    @Override
    public void update(float dt)
    {
        level.update(dt);
    }

    public void setHighlighted(boolean isHighlighted)
    {
        super.setHighlighted(isHighlighted);

        if (isHighlighted)
        {
            super.setHeight(defaultHeight + this.scoreFieldHeight);
        }
        else
        {
            super.setHeight(defaultHeight);
        }
    }
}

package org.polytech.polybigbalance.game;

import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.Font;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.layers.Level;

/**
 * Created by ronan-j on 01/06/16.
 */
public class LevelPreview extends TextButton
{
    private Level level;
    private TextButton button;
    private TextRenderer scoreText;

    private int defaultwidth, defaultHeight;

    public LevelPreview(int x, int y, int width, int height, Font font, Level level)
    {
        super(x, y, width, height, font);
        this.level = level;
        level.initialize();
        this.setTextTopCenter();
        this.defaultwidth = width;
        this.defaultHeight = height;
        this.scoreText = (TextRenderer) getTextRenderer().clone();
        this.scoreText.setAlignement(Alignement.LEFT);
    }


    @Override
    public void render(Graphics g)
    {
        super.render(g);

        g.pushMatrix();

        int scoresHeight = (isHighlighted()) ? scoreText.getHeight()*5 : 0;

        int y = (int) (getTextRenderer().getHeight() * 1.7f);
        int levelHeight = (int) (getHeight() - y - scoresHeight);

        int levelWidth = (int) (defaultwidth*levelHeight / defaultHeight);
        int x = (int) ((getWidth() - levelWidth)*0.5f);

        g.setColor(myColor.WHITE());
        g.setOutpout((int) (x + getLeftX()), (int) (- y - getY() + Constants.WINDOW_HEIGHT + scoresHeight*0.5f) , levelWidth, levelHeight);

        g.fillRec(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        level.render(g);
        g.resetOuput();


        if (isHighlighted())
        {
            //Score[] scores = level.getHighScores().getScore();
            /*
            y += levelHeight;

            for (int i = 0; i < scores.length; i++)
            {
                scoreText.print(g, scores[i].getPlayer() + ":\t" + scores[i].getScore());
                y += scoreText.getHeight();
            }*/
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
            super.setHeight(defaultHeight + 5*scoreText.getHeight());
        }
        else
        {
            super.setHeight(defaultHeight);
        }
    }
}

package org.polytech.polybigbalance.layers;

import org.cora.graphics.font.TextPosition;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Layer;

/**
 * Displays the score of the player
 */
public class TextScore extends Layer
{
    private int score;

    private TextRenderer text;

    private int x, y;

    /**
     * @param x x position of the top left corner of the text
     * @param y y position of the top left corner of the text
     * @param width text's width
     */
    public TextScore(int x, int y, int width)
    {
        super();

        this.x = x;
        this.y = y;

        this.text = new TextRenderer(Constants.FONT);
        this.text.setProportional(true);
        this.text.setMaxWidth(width);
        this.text.setFontColor(myColor.WHITE());
        this.text.setTextPosition(TextPosition.TOP_CENTER);

        this.setScore(0);
    }

    /**
     * Updates the score
     * @param score new score
     */
    public void setScore(int score)
    {
        this.score = score;
    }

    @Override
    public void render(Graphics g)
    {
        this.text.print(g, "Score : " + this.score, this.x, this.y);
    }

    @Override
    public void update(float dt)
    {

    }
}

package org.polytech.polybigbalance.layers;

import org.cora.graphics.base.Image;
import org.cora.graphics.font.Font;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.Surface;
import org.cora.graphics.graphics.myColor;
import org.polytech.polybigbalance.base.Layer;

/**
 * Displays the score of the player
 */
public class Score extends Layer
{
    private int score;
    private Font font;
    private TextRenderer text;
    private Image textImage;

    private int x, y;

    /**
     * @param fontSurface font to use
     * @param x x position of the center of the text
     * @param y y position of the center of the text
     * @param width text's width
     * @param height text's height
     */
    public Score(Surface fontSurface, int x, int y, int width, int height)
    {
        super();
        super.initialize(x, y, width, height);

        this.x = x;
        this.y = y;

        this.font = new Font();
        this.font.initialize(fontSurface, 32);
        this.font.setSpaceSize(15);

        this.text = new TextRenderer(this.font);
        this.text.setProportional(true);
        this.text.setMaxWidth(width);
        this.text.setFontColor(myColor.WHITE());
        this.text.setBackColor(myColor.BLACK());
    }

    /**
     * Updates the score
     * @param score new score
     * @param g graphics that will display the text
     */
    public void setScore(int score, Graphics g)
    {
        this.score = score;
        String s = "Score : " + this.score;

        this.textImage = this.text.transformToImage(s, this.x, this.y);
        g.loadTextureGL(this.textImage.getSpriteData().surface);
    }

    @Override
    public void render(Graphics g)
    {
        g.render(this.textImage);
    }

    @Override
    public void update(float dt)
    {

    }
}

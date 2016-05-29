package org.polytech.polybigbalance.layers;

import org.cora.graphics.base.Image;
import org.cora.graphics.font.Font;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.Surface;
import org.cora.graphics.graphics.myColor;
import org.polytech.polybigbalance.base.Layer;

/**
 * Displays the active player
 */
public class ActivePlayer extends Layer
{
    private String playerName;
    private boolean playerNameChanged;

    private Font font;
    private TextRenderer text;
    private Image textImage;

    private int x, y;

    /**
     * @param playerName player's name
     * @param fontSurface font to use
     * @param x x position of the center of the text
     * @param y y position of the center of the text
     * @param width text's width
     * @param height text's height
     */
    public ActivePlayer(String playerName, Surface fontSurface, int x, int y, int width, int height)
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

        this.setPlayerName(playerName);
    }

    public void setPlayerName(String playerName)
    {
        this.playerNameChanged = true;
        this.playerName = playerName;

        this.textImage = this.text.transformToImage(this.playerName, this.x, this.y);
    }

    @Override
    public void render(Graphics g)
    {
        if(this.playerNameChanged)
        {
            g.loadTextureGL(this.textImage.getSpriteData().surface);
        }

        g.render(this.textImage);
    }

    @Override
    public void update(float dt)
    {

    }
}

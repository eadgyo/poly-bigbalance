package org.polytech.polybigbalance.layers;

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

    private Font font;
    private TextRenderer text;

    private int x, y;

    /**
     * @param playerName player's name
     * @param fontSurface font to use
     * @param x x position of the top left corner of the text
     * @param y y position of the top left corner of the text
     * @param width text's width
     */
    public ActivePlayer(String playerName, Surface fontSurface, int x, int y, int width)
    {
        super();

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
        this.playerName = playerName;
    }

    @Override
    public void render(Graphics g)
    {
        this.text.print(g, playerName, this.x, this.y);
    }

    @Override
    public void update(float dt)
    {

    }
}

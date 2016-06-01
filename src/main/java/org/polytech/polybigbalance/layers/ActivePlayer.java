/**
 * @author Hugo PIGEON
 */

package org.polytech.polybigbalance.layers;

import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Layer;

/**
 * Displays the active player
 */
public class ActivePlayer extends Layer
{
    private String playerName;

    private TextRenderer text;

    private int x, y;

    /**
     * @param playerName player's name
     * @param x x position of the top left corner of the text
     * @param y y position of the top left corner of the text
     * @param width text's width
     */
    public ActivePlayer(String playerName, int x, int y, int width)
    {
        super();

        this.x = x;
        this.y = y;

        this.text = new TextRenderer(Constants.FONT);
        this.text.setProportional(true);
        this.text.setMaxWidth(width);
        this.text.setFontColor(myColor.WHITE());

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

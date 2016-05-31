package org.polytech.polybigbalance.layers;

import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Layer;
import org.polytech.polybigbalance.base.Player;

/**
 * Displays a summary of all the players' score
 */
public class ScoresSummary extends Layer
{
    private TextRenderer text;
    private Player[] players;
    private int x, y;

    /**
     * @param players players to get the scores from
     */
    public ScoresSummary(Player[] players)
    {
        final int WIDTH = Constants.WINDOW_WIDTH - 100;
        final int HEIGHT = Constants.WINDOW_HEIGHT - 200;
        this.x = Constants.WINDOW_WIDTH / 2 - WIDTH / 2;
        this.y = Constants.WINDOW_HEIGHT / 2 - HEIGHT / 2;

        this.players = players;
        this.initialize(this.x, this.y, WIDTH, HEIGHT);

        this.text = new TextRenderer(Constants.FONT);
        this.text.setProportional(true);
        this.text.setMaxWidth(WIDTH);
        this.text.setFontColor(myColor.WHITE());
        this.text.setBackColor(myColor.BLACK());
        this.text.setAlignement(Alignement.TOP_CENTER);
    }

    @Override
    public void render(Graphics g)
    {
        StringBuilder sb = new StringBuilder("Scores :\n\n");

        for(Player p : this.players)
        {
            sb.append(p.getName()).append(".......................").append(p.getScore()).append("\n");
        }
        sb.append("\n\nPress enter to continue");

        this.text.print(g, sb.toString(), this.x, this.y);
    }

    @Override
    public void update(float dt)
    {

    }
}

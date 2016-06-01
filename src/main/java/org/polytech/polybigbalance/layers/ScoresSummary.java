package org.polytech.polybigbalance.layers;

import org.cora.graphics.elements.Button;
import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;
import org.cora.maths.Vector2D;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.base.Layer;
import org.polytech.polybigbalance.base.Player;

import java.util.EnumSet;
import java.util.Set;

/**
 * Displays a summary of all the players' score
 */
public class ScoresSummary extends Layer
{
    private TextRenderer text;
    private Player[] players;
    private int x, y;
    private TextButton buttons[];

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
        this.text.setAlignement(Alignement.TOP_CENTER);

        final int BUTTONS_WIDTH = 200;
        final int BUTTONS_SPACING = 250;
        final int BUTTONS_POS_Y = Constants.WINDOW_HEIGHT / 2 + 150;
        this.buttons = new TextButton[3];

        for (int i = 0 ; i < this.buttons.length ; i++)
        {
            final int POS_X = Constants.WINDOW_WIDTH / 2 - BUTTONS_WIDTH / 2 + (i * BUTTONS_SPACING) - BUTTONS_SPACING;

            this.buttons[i] = new TextButton(POS_X, BUTTONS_POS_Y, BUTTONS_WIDTH, 50, Constants.FONT);
            this.buttons[i].setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
            this.buttons[i].setTxt(Constants.GAME_FINISHED_BUTTONS[i]);
        }
    }

    @Override
    public void render(Graphics g)
    {
        StringBuilder sb = new StringBuilder("Scores :\n\n");

        for(Player p : this.players)
        {
            sb.append(p.getName()).append(".......................").append(p.getScore()).append("\n");
        }

        this.text.print(g, sb.toString(), this.x, this.y);

        for(Button b : this.buttons)
        {
            b.render(g);
        }
    }

    public Set<InterfaceEvent> handleButtons(Input input)
    {
        if (input.isMouseMoving())
        {
            Vector2D mousePos = input.getMousePosV();

            for (Button b : this.buttons)
            {
                b.setHighlighted(b.isColliding(mousePos));
            }
        }

        if (input.isMousePressed(Input.MOUSE_BUTTON_1))
        {
            for (int i = 0 ; i < this.buttons.length ; i++)
            {
                if (this.buttons[i].isColliding(input.getMousePosV()))
                {
                    this.buttons[i].setHighlighted(false);
                    return EnumSet.of(Constants.GAME_FINISHED_EVENT[i]);
                }
            }
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void update(float dt)
    {

    }
}

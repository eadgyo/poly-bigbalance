package org.polytech.polybigbalance.interfaces;

import java.util.EnumSet;
import java.util.Set;

import org.cora.graphics.elements.Button;
import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.TextPosition;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;

/**
 * 
 * @author Tudal
 * 
 */

/**
 * 
 * Displays a list of buttons and optionally, a title Return an event given in
 * the event list when the corresponding button is clicked
 * 
 */

public class Menu extends Interface
{
    private TextButton buttons[];
    private InterfaceEvent events[];
    private TextRenderer title;
    private String text;

    public Menu(final int WIDTH, final int HEIGHT, String[] text, InterfaceEvent[] event)
    {
        this(WIDTH, HEIGHT, text, event, null);
    }

    public Menu(final int WIDTH, final int HEIGHT, String[] text, InterfaceEvent[] event, String title)
    {
        this.buttons = new TextButton[text.length];
        this.events = event;

        final int SPACING;
        final int START_Y;

        if (title == null)
        {
            SPACING = (Constants.WINDOW_HEIGHT - HEIGHT * this.buttons.length) / (this.buttons.length + 1);
            START_Y = 0;
        }
        else
        {
            this.text = title;
            this.title = new TextRenderer(Constants.FONT48);
            this.title.setTextPosition(TextPosition.TOP_CENTER);
            this.title.setAlignement(Alignement.TOP_CENTER);

            SPACING = (Constants.WINDOW_HEIGHT - HEIGHT * this.buttons.length - this.title.getHeight()) / (this.buttons.length + 2);
            START_Y = SPACING + this.title.getHeight();

            this.title.setPos(Constants.WINDOW_WIDTH / 2, SPACING);
        }

        for (int i = 0; i < this.buttons.length; i++)
        {
            this.buttons[i] = new TextButton(Constants.WINDOW_WIDTH / 2 - WIDTH / 2, START_Y + SPACING + (HEIGHT + SPACING) * i, WIDTH, HEIGHT, Constants.FONT);
            this.buttons[i].setHighLightColor(Constants.BUTTON_HIGHLIGHT_COLOR);
            this.buttons[i].setTxt(text[i]);
        }
    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public Set<InterfaceEvent> handleEvent(Input input)
    {
        if (input.isMouseMoving())
        {
            for (Button b : this.buttons)
            {
                b.setHighlighted(b.isColliding(input.getMousePosV()));
            }
        }

        if (input.isMousePressed(Input.MOUSE_BUTTON_1))
        {
            for (int i = 0; i < this.buttons.length; i++)
            {
                if (this.buttons[i].isColliding(input.getMousePosV()))
                {
                    this.buttons[i].setHighlighted(false);
                    return EnumSet.of(this.events[i]);
                }
            }
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);

        if (this.title != null)
        {
            this.title.print(g, this.text);
        }

        for (Button b : this.buttons)
        {
            b.render(g);
        }
    }
}

package org.polytech.polybigbalance.interfaces;

import java.util.EnumSet;
import java.util.Set;

import org.cora.graphics.elements.Button;
import org.cora.graphics.elements.TextButton;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.cora.maths.Vector2D;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;

public class Options extends Interface
{
    private TextButton buttons[];

    public Options()
    {
        this.buttons = new TextButton[Constants.OPTION_MENU_BUTTONS.length];

        final int WIDTH = 200, HEIGHT = 60;
        final int SPACING = (Constants.WINDOW_HEIGHT - HEIGHT * this.buttons.length) / (this.buttons.length + 1) - 20;

        for (int i = 0; i < this.buttons.length; i++) {
            final int POS_Y = SPACING + (HEIGHT + SPACING) * i + 100;

            this.buttons[i] = new TextButton(Constants.WINDOW_WIDTH / 2 - WIDTH / 2, POS_Y, WIDTH, HEIGHT, Constants.FONT);
            this.buttons[i].setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
            this.buttons[i].setTxt(Constants.OPTION_MENU_BUTTONS[i]);
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
        if (input.isMouseMoving()) {
            Vector2D mousePos = input.getMousePosV();

            for (Button b : this.buttons) {
                b.setHighlighted(b.isColliding(mousePos));
            }
        }

        if (input.isMousePressed(Input.MOUSE_BUTTON_1)) {
            for (int i = 0; i < this.buttons.length; i++) {
                if (this.buttons[i].isColliding(input.getMousePosV())) {
                    this.buttons[i].setHighlighted(false);
                    return EnumSet.of(Constants.OPTION_MENU_EVENT[i]);
                }
            }
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);
        g.render(Constants.MAIN_MENU_BACKGROUND);

        for (Button b : this.buttons) {
            b.render(g);
        }
    }
}

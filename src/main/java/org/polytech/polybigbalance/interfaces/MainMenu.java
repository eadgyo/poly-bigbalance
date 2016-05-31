package org.polytech.polybigbalance.interfaces;

import java.util.EnumSet;
import java.util.Set;

import org.cora.graphics.elements.Button;
import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Font;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;
import org.cora.maths.Vector2D;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;

public class MainMenu extends Interface
{
    private TextButton buttons[];

    public MainMenu()
    {
        this.buttons = new TextButton[Constants.MAIN_MENU_BUTTONS.length];

        final int WIDTH = 200, HEIGHT = 60;
        final int SPACING = (Constants.WINDOW_HEIGHT - HEIGHT * this.buttons.length) / (this.buttons.length + 1);

        Font font = new Font();
        font.initialize(Constants.TEXT_FONT_SURFACE, 32);
        font.setSpaceSize(15);

        for (int i = 0; i < this.buttons.length; i++) {
            this.buttons[i] = new TextButton(Constants.WINDOW_WIDTH / 2 - WIDTH / 2, SPACING + (HEIGHT + SPACING) * i, WIDTH, HEIGHT, font);
            this.buttons[i].setAddColor(new myColor(-0.3f, -0.3f, -0.3f, -0.3f));
            this.buttons[i].setTxt(Constants.MAIN_MENU_BUTTONS[i]);
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
                    return EnumSet.of(Constants.MAIN_MENU_EVENT[i]);
                }
            }
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        for (Button b : this.buttons) {
            b.render(g);
        }
    }
}

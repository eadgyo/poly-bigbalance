package org.polytech.polybigbalance.interfaces;

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

import java.util.EnumSet;
import java.util.Set;

public class MainMenu extends Interface
{
    private TextButton buttons[];

    public MainMenu()
    {
        this.buttons = new TextButton[Constants.MAIN_MENU_BUTTONS.length];

        final int WIDTH = 200, HEIGHT = 60;
        final int SPACING = (Constants.WINDOW_HEIGHT - HEIGHT * this.buttons.length) / (this.buttons.length + 1);
        final int START_HEIGHT = (Constants.WINDOW_HEIGHT - (HEIGHT + SPACING) * this.buttons.length + SPACING) / 2;

        Font font = new Font();
        font.initialize(Constants.TEXT_FONT_SURFACE, 32);
        font.setSpaceSize(15);

        for (int i = 0; i < this.buttons.length; i++) {
            this.buttons[i] = new TextButton(Constants.WINDOW_WIDTH / 2 - WIDTH / 2, START_HEIGHT + (HEIGHT + SPACING) * i, WIDTH, HEIGHT, font);
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
                b.setActive(b.isColliding(mousePos));
            }
        }

        if (input.getMousePressed(Input.MOUSE_BUTTON_1)) {
            for (int i = 0; i < this.buttons.length; i++) {
                Button b = this.buttons[i];

                if (b.isColliding(input.getMousePosV())) {
                    switch (i) {
                        case 0:
                            return EnumSet.of(InterfaceEvent.NEW_GAME);
                        case 1:
                            return EnumSet.of(InterfaceEvent.SCORE);
                        case 2:
                            return EnumSet.of(InterfaceEvent.HOW_TO);
                        case 3:
                            return EnumSet.of(InterfaceEvent.CREDIT);
                        case 4:
                            return EnumSet.of(InterfaceEvent.EXIT);
                    }
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

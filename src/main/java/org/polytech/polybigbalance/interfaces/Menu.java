package org.polytech.polybigbalance.interfaces;

import org.cora.graphics.elements.Button;
import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Font;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;

public class Menu extends Interface
{
    private TextButton buttons[];

    public Menu()
    {
        final int WIDTH = 200, HEIGHT = 60;
        this.buttons = new TextButton[5];

        Font font = new Font();
        font.initialize(Constants.TEXT_FONT_SURFACE, 32);
        font.setSpaceSize(15);

        for(int i = 0 ; i < this.buttons.length ; i++)
        {
            this.buttons[i] = new TextButton(Constants.WINDOW_WIDTH/2 - WIDTH/2, 100 + i * 100, WIDTH, HEIGHT, font);
        }

        this.buttons[0].setTxt("New game");
        this.buttons[1].setTxt("High scores");
        this.buttons[2].setTxt("How to play");
        this.buttons[3].setTxt("Credits");
        this.buttons[4].setTxt("Exit");
    }

    @Override
    public InterfaceEvent update(float dt)
    {
        return null;
    }

    @Override
    public InterfaceEvent handleEvent(Input input)
    {
        if(input.isMouseMoving())
        {
            for(Button b : this.buttons)
            {
                if(input.getMousePosX() > b.getLeftX() && input.getMousePosX() < b.getLeftX() + b.getWidth() &&
                        input.getMousePosY() > b.getLeftY() && input.getMousePosY() < b.getLeftY() + b.getHeight())
                {
                    b.setBackColor(0.7f, 0.7f, 0.7f, 1.0f);
                }
                else
                {
                    b.setBackColor(1.0f, 1.0f, 1.0f, 1.0f);
                }
            }
        }

        if(input.getMousePressed(Input.MOUSE_BUTTON_1))
        {
            for(int i = 0 ; i < this.buttons.length ; i++)
            {
                Button b = this.buttons[i];

                if(input.getMousePosX() > b.getLeftX() && input.getMousePosX() < b.getLeftX() + b.getWidth() &&
                   input.getMousePosY() > b.getLeftY() && input.getMousePosY() < b.getLeftY() + b.getHeight())
                {
                    switch(i)
                    {
                        case 0:
                            return InterfaceEvent.NEW_GAME;
                        case 1:
                            return InterfaceEvent.SCORE;
                        case 2:
                            return InterfaceEvent.HOW_TO;
                        case 3:
                            return InterfaceEvent.CREDIT;
                        case 4:
                            return InterfaceEvent.EXIT;
                    }
                }
            }
        }

        return InterfaceEvent.OK;
    }

    @Override
    public void render(Graphics g)
    {
        for(Button b : this.buttons)
        {
            b.render(g);
        }
    }
}
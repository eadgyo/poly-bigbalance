package org.polytech.polybigbalance.interfaces;

import java.util.EnumSet;
import java.util.Set;

import org.cora.graphics.elements.TextButton;
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
 * Displays and handles event of a single button, which return a POP event
 *
 */

public class Back extends Interface
{
    private TextButton button;

    public Back(String text)
    {
        this.button = new TextButton(10, 10, 150, 60, Constants.FONT);
        this.button.setHighLightColor(Constants.BUTTON_HIGHLIGHT_COLOR);
        this.button.setTxt(text);
    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public Set<InterfaceEvent> handleEvents(Input input)
    {
        if (input.isMouseMoving())
        {
            this.button.setHighlighted(this.button.isColliding(input.getMousePosV()));
        }

        if (input.isKeyDown(Input.KEY_ESC) || (input.isMousePressed(Input.MOUSE_BUTTON_1) && this.button.isColliding(input.getMousePosV())))
        {
            return EnumSet.of(InterfaceEvent.POP);
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);
        this.button.render(g);
    }
}

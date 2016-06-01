package org.polytech.polybigbalance.interfaces;

import org.cora.graphics.elements.TextButton;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;

import java.util.EnumSet;
import java.util.Set;

public class HighScoresInterface extends Interface
{
    private TextButton button;

    public HighScoresInterface()
    {
        this.button = new TextButton(10, 10, 150, 60, Constants.FONT);
        this.button.setAddColor(new myColor(-0.3f, -0.3f, -0.3f, -0.3f));
        this.button.setTxt("< Menu");

    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<InterfaceEvent> handleEvent(Input input)
    {
        boolean colliding = this.button.isColliding(input.getMousePosV());

        this.button.setHighlighted(colliding);

        if (input.isKeyDown(Input.KEY_ESC) || (input.isMousePressed(Input.MOUSE_BUTTON_1) && colliding)) {
            return EnumSet.of(InterfaceEvent.POP);
        } else {
            return EnumSet.of(InterfaceEvent.OK);
        }
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);
        this.button.render(g);
    }

}

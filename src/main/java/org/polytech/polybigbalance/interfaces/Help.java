package org.polytech.polybigbalance.interfaces;

import java.util.EnumSet;
import java.util.Set;

import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;

public class Help extends Interface
{
    private TextButton button;
    private TextRenderer text;

    public Help()
    {
        this.button = new TextButton(10, 10, 150, 60, Constants.FONT);
        this.button.setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
        this.button.setTxt("< Menu");

        this.text = new TextRenderer(Constants.FONT);
        this.text.setProportional(true);
        this.text.setMaxWidth(Constants.WINDOW_WIDTH - 200);
        this.text.setFontColor(myColor.WHITE());
        this.text.setBackColor(myColor.BLACK());
        this.text.setAlignement(Alignement.FULL);
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
        this.button.render(g);
        this.text.print(g, "Click and move the mouse while holding the left mouse button to draw a rectangle." + "The rectangle is created when you release the left mouse button.\n\n" + "Then wait 5 seconds, and if the rectangle doesn't touch the ground, your score is "
                + "increased. A big rectangle means a big increase.\n\n" + "If the rectangle falls, you lose the game!", Constants.WINDOW_WIDTH / 2 - this.text.getMaxWidth() / 2, 150);
    }

}

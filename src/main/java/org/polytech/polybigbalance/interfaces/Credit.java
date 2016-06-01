/**
 * @author Hugo PIGEON
 */

package org.polytech.polybigbalance.interfaces;

import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;

import java.util.EnumSet;
import java.util.Set;

public class Credit extends Interface
{
    private TextButton button;
    private TextRenderer text;

    public Credit()
    {
        this.button = new TextButton(10, 10, 150, 60, Constants.FONT);
        this.button.setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
        this.button.setTxt("< Menu");

        this.text = new TextRenderer(Constants.FONT);
        this.text.setProportional(true);
        this.text.setMaxWidth(Constants.WINDOW_WIDTH - 100);
        this.text.setFontColor(myColor.WHITE());
        this.text.setAlignement(Alignement.TOP_CENTER);
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

        this.text.print(g, "Developers:\n\n" + "Ronan JAMET\n" + "Tudal LE BOT\n" + "Pierre PÉTILLON\n" + "Hugo PIGEON", Constants.WINDOW_WIDTH / 2 - this.text.getMaxWidth() / 2, 200);
    }

}

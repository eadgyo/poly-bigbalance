package org.polytech.polybigbalance.interfaces;

import java.util.EnumSet;
import java.util.Set;

import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.Font;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.InterfaceEvent;

/**
 * 
 * @author Tudal
 * @author Hugo PIGEON
 * 
 */

/**
 * 
 * Displays some text, and a back button
 *
 */

public class SomeText extends Back
{
    private TextRenderer renderer;
    private String text;

    public SomeText(String text)
    {
        this(text, Alignement.FULL);
    }

    public SomeText(String text, myColor color)
    {
        this(text);
        setColor(color);
    }

    public SomeText(String text, Alignement alignement)
    {
        this(text, alignement, Constants.FONT);
    }

    public SomeText(String text, Alignement alignement, Font font)
    {
        this(text, alignement, font, myColor.WHITE());
    }

    public SomeText(String text, Alignement alignement, Font font, myColor color)
    {
        super("< Menu");

        this.text = text;

        this.renderer = new TextRenderer(font);
        this.renderer.setProportional(true);
        this.renderer.setMaxWidth(Constants.WINDOW_WIDTH * 9 / 10);
        this.renderer.setFontColor(color);
        this.renderer.setAlignement(alignement);
    }

    // ----- SETTER ----- //
    public void setColor(myColor color)
    {
        this.renderer.setFontColor(color);
    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public Set<InterfaceEvent> handleEvent(Input input)
    {
        return super.handleEvent(input);
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);
        this.renderer.print(g, this.text, (Constants.WINDOW_WIDTH - this.renderer.getMaxWidth()) / 2, 150);
    }
}

package org.polytech.polybigbalance.game.entity;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.maths.Form;

/**
 * Created by ronan-j on 07/06/16.
 */

/**
 * Entity that can be displayable
 */
public class Entity extends Moveable
{
    private myColor color;
    private myColor lineColor;

    public Entity(Form form)
    {
        super();
        setForm(form);

        color = myColor.WHITE();
        lineColor = myColor.BLACK();
    }

    public void render(Graphics g)
    {
        if (color.isVisible())
        {
            g.setColor(color);
            g.fillForm(form);
        }

        if (lineColor.isVisible())
        {
            g.setColor(lineColor);
            g.drawForm(form);
        }
    }

    public myColor getColor()
    {
        return color;
    }

    public void setColor(myColor color)
    {
        this.color.set(color);
    }

    public myColor getLineColor()
    {
        return lineColor;
    }

    public void setLineColor(myColor lineColor)
    {
        this.lineColor.set(lineColor);
    }
}

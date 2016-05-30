package org.polytech.polybigbalance.interfaces;

import java.util.EnumSet;
import java.util.Set;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;

public class Credit extends Interface
{

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<InterfaceEvent> handleEvent(Input input)
    {
        if (input.isKeyDown(input.KEY_ESC)) {
            return EnumSet.of(InterfaceEvent.POP);
        } else {
            return EnumSet.of(InterfaceEvent.OK);
        }
    }

    @Override
    public void render(Graphics g)
    {
        // TODO Auto-generated method stub

    }

}

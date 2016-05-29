package org.polytech.polybigbalance.interfaces;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.base.Layer;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.layers.TextScore;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles a game
 */
public class Game extends Interface
{
    private Map<String, Layer> layers;
    private boolean drawing;

    public Game(Level level)
    {
        this.layers = new HashMap<>();
        this.drawing = false;

        level.initialize();
        this.layers.put("level", level);

        this.layers.put("score", new TextScore(Constants.TEXT_FONT_SURFACE, Constants.WINDOW_WIDTH / 2, 30, 200, 40));
    }

    @Override
    public InterfaceEvent update(float dt)
    {
        for(Layer l : this.layers.values())
        {
            l.update(dt);
        }

        return InterfaceEvent.OK;
    }

    @Override
    public InterfaceEvent handleEvent(Input input)
    {
        if(input.getMouseDown(Input.MOUSE_BUTTON_1))
        {
            this.drawing = true;
            ((Level) this.layers.get("level")).drawRectangle(input.getMousePosV());
        }
        else if(this.drawing)
        {
            this.drawing = false;
            ((Level) this.layers.get("level")).endDrawRectangle();
        }

        return InterfaceEvent.OK;
    }

    @Override
    public void render(Graphics g)
    {
        for(Layer l : this.layers.values())
        {
            l.render(g);
        }
    }
}

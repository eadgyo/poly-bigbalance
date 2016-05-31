package org.polytech.polybigbalance;

import java.util.Set;
import java.util.Stack;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.interfaces.Credit;
import org.polytech.polybigbalance.interfaces.Help;
import org.polytech.polybigbalance.interfaces.HighScoresInterface;
import org.polytech.polybigbalance.interfaces.LevelSelector;
import org.polytech.polybigbalance.interfaces.MainMenu;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.layers.Level1;

public class PolyBigBalance
{
    private Graphics g;
    private Input input;
    private Stack<Interface> stack;
    private Level[] levels;
    private float timeElapsed;

    // ----- CONSTRUCTOR ----- //

    public PolyBigBalance()
    {
    }

    public void init()
    {
        this.g = Constants.g;
        this.g.loadTextureGL(Constants.TEXT_FONT_SURFACE);

        this.input = new Input();
        this.input.initGL(g.getScreen());

        this.stack = new Stack<>();
        this.stack.add(new MainMenu());

        this.levels = new Level[5];
        for (int i = 0; i < 5; i++) {
            this.levels[i] = new Level1();
        }

        this.timeElapsed = System.nanoTime() / 1000000000.0f;
    }

    // ----- METHODS ----- //

    public void mainLoop()
    {
        while (g.isNotTerminated()) {
            g.clear();

            timeElapsed = (System.nanoTime() / 1000000000.0f) - timeElapsed;
            stack.lastElement().update(timeElapsed);
            timeElapsed = System.nanoTime() / 1000000000.0f;

            stack.lastElement().render(g);

            input.update();
            handleEvent(stack.lastElement().handleEvent(input));

            g.swapGL();
        }
    }

    public void exit()
    {
        // Nothing yet
    }

    private void handleEvent(Set<InterfaceEvent> event)
    {

        if (event.contains(InterfaceEvent.OK)) {
        }

        if (event.contains(InterfaceEvent.POP)) {
            this.input.clear();
            stack.pop();
        }

        if (event.contains(InterfaceEvent.PLAY)) {
            this.input.clear();
            stack.push(new LevelSelector(this.levels));
        }

        if (event.contains(InterfaceEvent.SCORE)) {
            stack.push(new HighScoresInterface());
        }

        if (event.contains(InterfaceEvent.HOW_TO)) {
            stack.push(new Help());
        }

        if (event.contains(InterfaceEvent.CREDIT)) {
            stack.push(new Credit());
        }

        if (event.contains(InterfaceEvent.EXIT)) {
            g.terminate();
        }
    }
}

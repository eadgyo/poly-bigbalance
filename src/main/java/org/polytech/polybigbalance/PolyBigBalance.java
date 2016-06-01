package org.polytech.polybigbalance;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.base.GameData;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.interfaces.*;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.level.LevelFactory;
import org.polytech.polybigbalance.score.HighScoresManager;

import java.util.Set;
import java.util.Stack;

public class PolyBigBalance
{
    private Graphics g;
    private Input input;
    private Stack<Interface> stack;
    private float timeElapsed;

    private HighScoresManager hsm;
    private GameData gameData;

    // ----- CONSTRUCTOR ----- //

    public PolyBigBalance()
    {
    }

    public void init()
    {
        this.hsm = HighScoresManager.load();

        this.g = Constants.g;

        Constants.FONT.setSpaceSize(15);

        this.input = new Input();
        this.input.initGL(g.getScreen());

        this.stack = new Stack<>();
        this.stack.add(new MainMenu());

        this.gameData = new GameData();

        this.timeElapsed = System.nanoTime() / 1000000000.0f;
    }

    // ----- METHODS ----- //

    public void mainLoop()
    {
        while (g.isNotTerminated()) {
            this.g.clear();

            this.timeElapsed = (System.nanoTime() / 1000000000.0f) - this.timeElapsed;
            this.stack.lastElement().update(this.timeElapsed);
            this.timeElapsed = System.nanoTime() / 1000000000.0f;

            this.stack.lastElement().render(g);

            this.input.update();
            handleEvent(stack.lastElement().handleEvent(input));

            this.g.swapGL();
        }
    }

    public void exit()
    {
        this.hsm.save();
    }

    private void handleEvent(Set<InterfaceEvent> event)
    {

        if (event.contains(InterfaceEvent.OK)) {
        }

        if (event.contains(InterfaceEvent.POP)) {
            this.input.clear();
            this.stack.pop();
        }

        if (event.contains(InterfaceEvent.NEW_GAME)) {
            this.input.clear();
            this.stack.pop();

            Level level = LevelFactory.getNewLevel(this.gameData.getLevel());
            this.hsm.setHighScores(this.gameData.getLevel(), level);

            this.stack.push(new Game(this.gameData.getPlayer(), level));
        }

        if (event.contains(InterfaceEvent.PLAY)) {
            this.stack.push(new LevelSelector(this.gameData));
        }

        if (event.contains(InterfaceEvent.HOW_TO)) {
            this.stack.push(new Help());
        }

        if (event.contains(InterfaceEvent.CREDIT)) {
            this.stack.push(new Credit());
        }

        if (event.contains(InterfaceEvent.EXIT)) {
            this.g.terminate();
        }
    }
}

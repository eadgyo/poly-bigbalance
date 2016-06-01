package org.polytech.polybigbalance;

import java.util.Set;
import java.util.Stack;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.base.GameData;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.interfaces.Credit;
import org.polytech.polybigbalance.interfaces.Game;
import org.polytech.polybigbalance.interfaces.Help;
import org.polytech.polybigbalance.interfaces.LevelSelector;
import org.polytech.polybigbalance.interfaces.MainMenu;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.layers.Level1;
import org.polytech.polybigbalance.level.LevelFactory;
import org.polytech.polybigbalance.score.HighScoresManager;

public class PolyBigBalance
{
    private Graphics g;
    private Input input;
    private Stack<Interface> stack;
    private Level[] levels;
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

        this.levels = new Level[5];
        for (int i = 0; i < 5; i++) {
            this.levels[i] = new Level1();
        }

        this.gameData = new GameData();

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

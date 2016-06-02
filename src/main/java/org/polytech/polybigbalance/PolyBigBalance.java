/**
 * 
 * @author Tudal
 * @author Ronan
 * 
 */

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
import org.polytech.polybigbalance.interfaces.Menu;
import org.polytech.polybigbalance.interfaces.Options;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.level.LevelFactory;
import org.polytech.polybigbalance.score.HighScoresManager;

public class PolyBigBalance
{
    private Input input;
    private Graphics g;

    private HighScoresManager highScoresManager;

    private Interface menu;
    private Stack<Interface> stack;

    private GameData gameData;

    private float timeElapsed;

    // ----- CONSTRUCTOR ----- //

    public PolyBigBalance()
    {
    }

    public void init()
    {
        Constants.FONT.setSpaceSize(16);
        Constants.FONT48.setSpaceSize(16);

        this.highScoresManager = HighScoresManager.load();

        this.g = Constants.g;

        this.input = new Input();
        this.input.initGL(this.g.getScreen());

        this.menu = new Menu(200, 60, Constants.MAIN_MENU_BUTTONS, Constants.MAIN_MENU_EVENT, Constants.WINDOW_TITLE);

        this.stack = new Stack<>();
        this.stack.add(this.menu);

        this.gameData = new GameData();

        this.timeElapsed = System.nanoTime() / 1000000000.0f;
    }

    // ----- METHODS ----- //

    public void mainLoop()
    {
        while (this.g.isNotTerminated())
        {
            this.g.clear();

            this.timeElapsed = (System.nanoTime() / 1000000000.0f) - this.timeElapsed;
            this.stack.lastElement().update(this.timeElapsed);
            this.input.update(this.timeElapsed);
            this.timeElapsed = System.nanoTime() / 1000000000.0f;

            this.stack.lastElement().render(this.g);

            handleEvent(stack.lastElement().handleEvent(this.input));

            this.g.swapGL();
        }
    }

    public void exit()
    {
        this.highScoresManager.save();
    }

    private void handleEvent(Set<InterfaceEvent> event)
    {
        // if (event.contains(InterfaceEvent.OK)) { }

        if (event.contains(InterfaceEvent.POP))
        {
            this.input.clear();
            this.stack.pop();
        }

        if (event.contains(InterfaceEvent.PAUSE))
        {
            this.stack.push(new Options());
        }

        if (event.contains(InterfaceEvent.MENU))
        {
            this.stack.clear();
            this.stack.push(this.menu);
        }

        if (event.contains(InterfaceEvent.NEW_GAME))
        {
            this.input.clear();
            this.stack.pop();

            Level level = LevelFactory.getNewLevel(this.gameData.getLevel());
            this.highScoresManager.setHighScores(this.gameData.getLevel(), level);

            this.stack.push(new Game(this.gameData.getPlayer(), level));
        }

        if (event.contains(InterfaceEvent.PLAY))
        {
            this.stack.push(new LevelSelector(this.gameData, this.highScoresManager));
        }

        if (event.contains(InterfaceEvent.HOW_TO))
        {
            this.stack.push(new Help());
        }

        if (event.contains(InterfaceEvent.CREDIT))
        {
            this.stack.push(new Credit());
        }

        if (event.contains(InterfaceEvent.EXIT))
        {
            this.g.terminate();
        }
    }
}

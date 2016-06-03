package org.polytech.polybigbalance;

import org.cora.graphics.font.Alignement;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.interfaces.Game;
import org.polytech.polybigbalance.interfaces.LevelSelector;
import org.polytech.polybigbalance.interfaces.Menu;
import org.polytech.polybigbalance.interfaces.SomeText;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.level.LevelFactory;
import org.polytech.polybigbalance.score.HighScoresManager;

import java.util.Set;
import java.util.Stack;

/**
 * 
 * @author Tudal
 * @author Ronan
 * 
 */

public class PolyBigBalance
{
    private Input input;
    private Graphics g;

    private HighScoresManager highScoresManager;

    private Menu menu;
    private Menu pause;
    private Interface help;
    private Interface credits;
    private Stack<Interface> stack;

    private double timeElapsed;

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

        this.menu = new Menu(200, 60, Constants.MENU_MAIN_BUTTON, Constants.MENU_MAIN_EVENT, Constants.WINDOW_TITLE);
        this.pause = new Menu(200, 60, Constants.MENU_PAUSE_BUTTON, Constants.MENU_PAUSE_EVENT, Constants.MENU_PAUSE_TITLE);
        this.help = new SomeText(Constants.MENU_HELP_TEXT);
        this.credits = new SomeText(Constants.MENU_CREDITS_TEXT, Alignement.TOP_CENTER);

        this.pause.setMustClearOnPop(true);

        this.stack = new Stack<>();
        this.stack.add(this.menu);

        this.timeElapsed = System.nanoTime() / 1000000000.0f;
    }

    // ----- METHODS ----- //

    public void mainLoop()
    {
        float tmp;
        while (this.g.isNotTerminated())
        {
            this.g.clear();

            tmp = (System.nanoTime() / 1000000000.0f);
            this.timeElapsed = tmp - this.timeElapsed;

            this.stack.lastElement().update((float) this.timeElapsed);
            this.input.update((float) this.timeElapsed);

            this.stack.lastElement().render(this.g);

            handleEvent(stack.lastElement().handleEvents(this.input));

            this.g.swapGL();
            this.timeElapsed = tmp;
        }
    }

    public void exit()
    {
        this.highScoresManager.save();
    }

    private void handleEvent(Set<InterfaceEvent> event)
    {
        // if (event.contains(InterfaceEvent.OK)) { }

        if (event.contains(InterfaceEvent.CLEAR))
        {
            this.input.clear();
        }

        if (event.contains(InterfaceEvent.POP))
        {
            this.stack.pop();
        }

        if (event.contains(InterfaceEvent.PAUSE))
        {
            this.stack.push(this.pause);
        }

        if (event.contains(InterfaceEvent.MENU))
        {
            this.stack.push(this.menu);
        }

        if (event.contains(InterfaceEvent.NEW_GAME))
        {
            LevelSelector selector = (LevelSelector) this.stack.lastElement();
            int player = selector.getPlayer();
            Level level = LevelFactory.getNewLevel(selector.getSelectedLevel());

            this.highScoresManager.setHighScores(selector.getSelectedLevel(), level);

            this.stack.pop();
            this.stack.push(new Game(player, level));
        }

        if (event.contains(InterfaceEvent.PLAY))
        {
            this.stack.push(new LevelSelector(this.highScoresManager));
        }

        if (event.contains(InterfaceEvent.HOW_TO))
        {
            this.stack.push(this.help);
        }

        if (event.contains(InterfaceEvent.CREDIT))
        {
            this.stack.push(this.credits);
        }

        if (event.contains(InterfaceEvent.EXIT))
        {
            this.g.terminate();
        }
    }
}

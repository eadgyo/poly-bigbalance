package org.polytech.polybigbalance.interfaces;

import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.TextPosition;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.game.LevelPreview;
import org.polytech.polybigbalance.level.LevelFactory;
import org.polytech.polybigbalance.score.HighScoresManager;

import java.util.EnumSet;
import java.util.Set;

/**
 * 
 * @author Tudal
 * 
 */

/**
 * 
 * Displays a list of level, let the player configure the game and launch it
 * 
 */

public class LevelSelector extends Back
{
    private final static int COLUMNS = 3;

    private TextButton left;
    private TextButton right;
    private TextButton del;
    private TextButton add;
    private TextButton play;

    private TextButton[] level;

    private TextRenderer[] text;
    private final static String TEXT = "Players : ";

    private int selected;
    private int player;
    private int page;

    // ----- CONSTRUCTOR ----- //

    public LevelSelector(HighScoresManager hsm)
    {
        final int PLAYER_BUTTON_SIZE = 30;
        final int LEVEL_BUTTON_SIZE = 150;
        final int PAGE_BUTTON_SIZE = 100;
        final int PADDING = 10;
        final int SPACING = (Constants.WINDOW_WIDTH - 2 * (PADDING + PAGE_BUTTON_SIZE) - LEVEL_BUTTON_SIZE * LevelSelector.COLUMNS) / (LevelSelector.COLUMNS + 1);
        final int START_X = PADDING + PAGE_BUTTON_SIZE + SPACING;
        final int POS_Y = 40;

        this.selected = 0;
        this.player = 1;
        this.page = 0;

        this.left = new TextButton(PADDING, (Constants.WINDOW_HEIGHT - PAGE_BUTTON_SIZE) / 2, PAGE_BUTTON_SIZE, PAGE_BUTTON_SIZE, Constants.FONT);
        this.left.setHighLightColor(Constants.BUTTON_HIGHLIGHT_COLOR);
        this.left.setTxt("<");
        this.left.setActive(!isFirstPage());

        this.right = new TextButton(Constants.WINDOW_WIDTH - PAGE_BUTTON_SIZE - PADDING, (Constants.WINDOW_HEIGHT - PAGE_BUTTON_SIZE) / 2, PAGE_BUTTON_SIZE, PAGE_BUTTON_SIZE, Constants.FONT);
        this.right.setHighLightColor(Constants.BUTTON_HIGHLIGHT_COLOR);
        this.right.setTxt(">");
        this.right.setActive(!isLastPage());

        this.text = new TextRenderer[2];
        this.text[0] = new TextRenderer(Constants.FONT);
        this.text[0].setTextPosition(TextPosition.TOP_CENTER);
        this.text[0].setAlignement(Alignement.TOP_CENTER);
        this.text[1] = (TextRenderer) this.text[0].clone();

        final int TEXT_WIDTH = this.text[0].getWidth(LevelSelector.TEXT);
        final int POS_X = (Constants.WINDOW_WIDTH - TEXT_WIDTH - 3 * PLAYER_BUTTON_SIZE) / 2;

        this.text[0].setPos(POS_X + TEXT_WIDTH / 2, POS_Y);
        this.text[1].setPos(POS_X + TEXT_WIDTH + 3 * PLAYER_BUTTON_SIZE / 2, POS_Y);

        this.del = new TextButton(POS_X + TEXT_WIDTH, POS_Y, PLAYER_BUTTON_SIZE, PLAYER_BUTTON_SIZE, Constants.FONT);
        this.del.setHighLightColor(Constants.BUTTON_HIGHLIGHT_COLOR);
        this.del.setTxt("-");
        this.del.setActive(!isMinPlayer());

        this.add = new TextButton(POS_X + TEXT_WIDTH + 2 * PLAYER_BUTTON_SIZE, POS_Y, PLAYER_BUTTON_SIZE, PLAYER_BUTTON_SIZE, Constants.FONT);
        this.add.setHighLightColor(Constants.BUTTON_HIGHLIGHT_COLOR);
        this.add.setTxt("+");
        this.add.setActive(!isMaxPlayer());

        this.play = new TextButton(640, 10, 150, 60, Constants.FONT);
        this.play.setHighLightColor(Constants.BUTTON_HIGHLIGHT_COLOR);
        this.play.setTxt(Constants.MENU_MAIN_BUTTON[0]);

        this.level = new TextButton[LevelFactory.getNumberOfLevel()];

        for (int i = 0; i < LevelFactory.getNumberOfLevel(); i++)
        {
            this.level[i] = new LevelPreview(START_X + (LEVEL_BUTTON_SIZE + SPACING) * i, (Constants.WINDOW_HEIGHT - LEVEL_BUTTON_SIZE) / 2, LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE, Constants.FONT, LevelFactory.getNewLevel(i), i, hsm);
            this.level[i].setBackColor(Constants.SELECTOR_NOT_SELECTED_COLOR);
            this.level[i].setHighLightColor(Constants.BUTTON_HIGHLIGHT_COLOR);
            this.level[i].setTxt(Integer.toString(i));
            this.level[i].setActive(false);
        }

        setActiveButtons(true);
        this.level[this.selected].setBackColor(Constants.SELECTOR_SELECTED_COLOR);

    }

    // ----- GETTER ----- //

    public int getSelectedLevel()
    {
        return this.selected;
    }

    public int getPlayer()
    {
        return this.player;
    }

    private boolean isFirstPage()
    {
        return (this.page == 0);
    }

    private boolean isLastPage()
    {
        return (this.page == LevelFactory.getNumberOfLevel() / LevelSelector.COLUMNS);
    }

    private boolean isMinPlayer()
    {
        return (this.player == 1);
    }

    private boolean isMaxPlayer()
    {
        return (this.player == Constants.MAX_PLAYER);
    }

    private int getPageSize()
    {
        return (isLastPage() ? LevelFactory.getNumberOfLevel() % LevelSelector.COLUMNS : 3);
    }

    private int getButtonIndex(int n)
    {
        return this.page * LevelSelector.COLUMNS + n;
    }

    // ----- SETTER ----- //

    private void setActiveButtons(boolean value)
    {
        for (int i = 0; i < getPageSize(); i++)
        {
            this.level[this.page * LevelSelector.COLUMNS + i].setActive(value);
        }
    }

    private void selectButton(int n)
    {
        this.level[this.selected].setBackColor(Constants.SELECTOR_NOT_SELECTED_COLOR);
        this.level[n].setBackColor(Constants.SELECTOR_SELECTED_COLOR);
        this.level[n].setHighlighted(false);
        this.selected = n;
    }

    // ----- METHODS ----- //

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        for (int i = 0; i < getPageSize(); i++)
        {
            this.level[getButtonIndex(i)].update(dt);
        }
        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public Set<InterfaceEvent> handleEvents(Input input)
    {
        Set<InterfaceEvent> back = super.handleEvents(input);

        if (!back.contains(InterfaceEvent.OK))
        {
            return back;
        }

        if (input.isMousePressed(Input.MOUSE_BUTTON_1))
        {
            if (this.play.isColliding(input.getMousePosV()))
            {
                return EnumSet.of(InterfaceEvent.NEW_GAME, InterfaceEvent.CLEAR);
            }
            if (this.left.isColliding(input.getMousePosV()))
            {
                setActiveButtons(false);
                page--;
                setActiveButtons(true);
                this.left.setActive(!isFirstPage());
                this.left.setActive(true);
            }
            if (this.right.isHighlighted())
            {
                setActiveButtons(false);
                page++;
                setActiveButtons(true);
                this.left.setActive(true);
                this.right.setActive(!isLastPage());
            }
            if (this.del.isHighlighted())
            {
                this.player--;
                this.add.setActive(true);
                this.del.setActive(!isMinPlayer());
            }
            if (this.add.isHighlighted())
            {
                this.player++;
                this.add.setActive(!isMaxPlayer());
                this.del.setActive(true);
            }
            for (int i = 0; i < getPageSize(); i++)
            {
                if (getButtonIndex(i) != this.selected && this.level[getButtonIndex(i)].isHighlighted())
                {
                    selectButton(getButtonIndex(i));
                    this.level[getButtonIndex(i)].setHighlighted(this.level[getButtonIndex(i)].isColliding(input.getMousePosV()));
                }
            }
        }

        if (input.isMouseMoving())
        {
            this.play.setHighlighted(this.play.isColliding(input.getMousePosV()));
            this.left.setHighlighted(this.left.isColliding(input.getMousePosV()));
            this.right.setHighlighted(this.right.isColliding(input.getMousePosV()));
            this.del.setHighlighted(this.del.isColliding(input.getMousePosV()));
            this.add.setHighlighted(this.add.isColliding(input.getMousePosV()));

            for (int i = 0; i < getPageSize(); i++)
            {
                this.level[getButtonIndex(i)].setHighlighted(this.level[getButtonIndex(i)].isColliding(input.getMousePosV()));
            }
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);

        this.left.render(g);
        this.right.render(g);
        this.del.render(g);
        this.add.render(g);
        this.play.render(g);
        this.text[0].print(g, LevelSelector.TEXT);
        this.text[1].print(g, Integer.toString(this.player));

        for (int i = 0; i < getPageSize(); i++)
        {
            this.level[getButtonIndex(i)].render(g);
        }
    }
}

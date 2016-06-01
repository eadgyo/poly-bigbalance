package org.polytech.polybigbalance.interfaces;

import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Alignement;
import org.cora.graphics.font.TextPosition;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.GameData;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.game.LevelPreview;
import org.polytech.polybigbalance.level.LevelFactory;

import java.util.EnumSet;
import java.util.Set;

public class LevelSelector extends Interface
{
    private final int COLUMNS = 3;

    private TextButton returnButton;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton addPlayer;
    private TextButton removePlayer;
    private TextButton play;
    private TextButton[] buttons;
    private TextRenderer[] playerText;
    private int selected;
    private int player;
    private int page;
    private GameData gameData;

    public LevelSelector(GameData gameData)
    {
        final int PAGE_BUTTON_SIZE = 100;
        final int PADDING = 10;
        final int LEVEL_BUTTON_SIZE = 150;
        final int SPACING = (Constants.WINDOW_WIDTH - 2 * (PADDING + PAGE_BUTTON_SIZE) - LEVEL_BUTTON_SIZE * this.COLUMNS) / (this.COLUMNS + 1);
        final int START_WIDTH = PADDING + PAGE_BUTTON_SIZE + SPACING;
        final int PLAYER_BOUTON_SIZE = 30;
        final int POS_X = 300;
        final int POS_Y = 50;

        this.gameData = gameData;

        this.selected = 0;
        this.player = 1;
        this.page = 0;

        this.returnButton = new TextButton(10, 10, 150, 60, Constants.FONT);
        this.returnButton.setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
        this.returnButton.setTxt("< Menu");

        this.leftButton = new TextButton(10, (Constants.WINDOW_HEIGHT - PAGE_BUTTON_SIZE) / 2, PAGE_BUTTON_SIZE, PAGE_BUTTON_SIZE, Constants.FONT);
        this.leftButton.setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
        this.leftButton.setTxt("<");
        this.leftButton.setActive(false);
        this.rightButton = new TextButton(Constants.WINDOW_WIDTH - PAGE_BUTTON_SIZE - 10, (Constants.WINDOW_HEIGHT - PAGE_BUTTON_SIZE) / 2, PAGE_BUTTON_SIZE, PAGE_BUTTON_SIZE, Constants.FONT);
        this.rightButton.setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
        this.rightButton.setTxt(">");
        this.rightButton.setActive(!isLastPage());

        this.buttons = new TextButton[LevelFactory.getNumberOfLevel()];

        for (int i = 0; i < LevelFactory.getNumberOfLevel(); i++) {
            this.buttons[i] = new LevelPreview(START_WIDTH + (LEVEL_BUTTON_SIZE + SPACING) * (i % 3),
                    (Constants.WINDOW_HEIGHT - LEVEL_BUTTON_SIZE) / 2,
                    LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE,
                    Constants.FONT,
                    LevelFactory.getNewLevel(i), i);
            this.buttons[i].setBackColor(Constants.SELECTOR_NOT_SELECTED_COLOR);
            this.buttons[i].setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
            this.buttons[i].setActive(false);
            this.buttons[i].setTxt(Integer.toString(i));
        }

        setActiveBouttons(true);

        this.buttons[this.selected].setBackColor(Constants.SELECTOR_SELECTED_COLOR);

        this.playerText = new TextRenderer[2];
        this.playerText[0] = new TextRenderer(Constants.FONT);
        this.playerText[0].setTextPosition(TextPosition.TOP_CENTER);

        this.playerText[1] = (TextRenderer) this.playerText[0].clone();
        this.playerText[0].setPos(POS_X, POS_Y);
        this.playerText[1].setPos(POS_X + this.playerText[0].getWidth("Players :") + PLAYER_BOUTON_SIZE + PLAYER_BOUTON_SIZE / 2 + 10, POS_Y);
        this.playerText[1].setAlignement(Alignement.TOP_CENTER);

        this.removePlayer = new TextButton(POS_X + this.playerText[0].getWidth("Players :") + 10, POS_Y, PLAYER_BOUTON_SIZE, PLAYER_BOUTON_SIZE, Constants.FONT);
        this.removePlayer.setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
        this.removePlayer.setTxt("-");
        this.removePlayer.setActive(false);
        this.addPlayer = new TextButton(POS_X + this.playerText[0].getWidth("Players :") + 2 * PLAYER_BOUTON_SIZE + 10, POS_Y, PLAYER_BOUTON_SIZE, PLAYER_BOUTON_SIZE, Constants.FONT);
        this.addPlayer.setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
        this.addPlayer.setTxt("+");
        this.addPlayer.setActive(!isMaxPlayer());

        this.play = new TextButton(640, 10, 150, 60, Constants.FONT);
        this.play.setAddColor(Constants.MAIN_MENU_HIGHLIGHT_COLOR);
        this.play.setTxt(Constants.MAIN_MENU_BUTTONS[0]);
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
        return (this.page == LevelFactory.getNumberOfLevel() / this.COLUMNS);
    }

    private boolean isMinPlayer()
    {
        return (this.player == 1);
    }

    private boolean isMaxPlayer()
    {
        return (this.player == Constants.MAX_PLAYER);
    }

    private void setActiveBouttons(boolean value)
    {
        for (int i = 0; i < getPageSize(); i++) {
            this.buttons[this.page * this.COLUMNS + i].setActive(value);
        }
    }

    private int getPageSize()
    {
        return (isLastPage() ? LevelFactory.getNumberOfLevel() % this.COLUMNS : 3);
    }

    private int getButtonIndex(int n)
    {
        return this.page * this.COLUMNS + n;
    }

    private void selectButton(int n)
    {
        this.buttons[this.selected].setBackColor(Constants.SELECTOR_NOT_SELECTED_COLOR);
        this.buttons[n].setBackColor(Constants.SELECTOR_SELECTED_COLOR);
        this.buttons[n].setHighlighted(false);
        this.selected = n;
    }

    private void setGameData()
    {
        this.gameData.setPlayer(this.player);
        this.gameData.setLevel(this.selected);
    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        for (int i = 0; i < getPageSize(); i++) {
            if (this.selected != getButtonIndex(i)) {
                this.buttons[getButtonIndex(i)].update(dt);
            }
        }
        return null;
    }

    @Override
    public Set<InterfaceEvent> handleEvent(Input input)
    {
        if (input.isKeyDown(Input.KEY_ESC)) {
            return EnumSet.of(InterfaceEvent.POP);
        }

        if (input.isMousePressed(Input.MOUSE_BUTTON_1)) {
            if (this.returnButton.isHighlighted()) {
                return EnumSet.of(InterfaceEvent.POP);
            }
            if (this.play.isHighlighted()) {
                setGameData();
                return EnumSet.of(InterfaceEvent.NEW_GAME);
            }
            if (this.leftButton.isHighlighted()) {
                setActiveBouttons(false);
                page--;
                setActiveBouttons(true);
                this.leftButton.setActive(!isFirstPage());
                this.rightButton.setActive(true);
            }
            if (this.rightButton.isHighlighted()) {
                setActiveBouttons(false);
                page++;
                setActiveBouttons(true);
                this.leftButton.setActive(true);
                this.rightButton.setActive(!isLastPage());
            }
            if (this.removePlayer.isHighlighted()) {
                this.player--;
                this.addPlayer.setActive(true);
                this.removePlayer.setActive(!isMinPlayer());
            }
            if (this.addPlayer.isHighlighted()) {
                this.player++;
                this.addPlayer.setActive(!isMaxPlayer());
                this.removePlayer.setActive(true);
            }
            for (int i = 0; i < getPageSize(); i++) {
                if (getButtonIndex(i) != this.selected && this.buttons[getButtonIndex(i)].isHighlighted()) {
                    selectButton(getButtonIndex(i));
                    this.buttons[getButtonIndex(i)].setHighlighted(this.buttons[getButtonIndex(i)].isColliding(input.getMousePosV()));
                }
            }
        }


        if (input.isMouseMoving()) {
            this.returnButton.setHighlighted(this.returnButton.isColliding(input.getMousePosV()));
            this.play.setHighlighted(this.play.isColliding(input.getMousePosV()));
            this.leftButton.setHighlighted(this.leftButton.isColliding(input.getMousePosV()));
            this.rightButton.setHighlighted(this.rightButton.isColliding(input.getMousePosV()));
            this.addPlayer.setHighlighted(this.addPlayer.isColliding(input.getMousePosV()));
            this.removePlayer.setHighlighted(this.removePlayer.isColliding(input.getMousePosV()));

            for (int i = 0; i < getPageSize(); i++) {
                this.buttons[getButtonIndex(i)].setHighlighted(this.buttons[getButtonIndex(i)].isColliding(input.getMousePosV()));
            }
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        this.returnButton.render(g);

        this.leftButton.render(g);
        this.rightButton.render(g);

        for (int i = 0; i < getPageSize(); i++) {
            this.buttons[getButtonIndex(i)].render(g);
        }

        this.playerText[0].print(g, "Players :");
        this.playerText[1].print(g, Integer.toString(this.player));
        this.addPlayer.render(g);
        this.removePlayer.render(g);

        this.play.render(g);
    }
}

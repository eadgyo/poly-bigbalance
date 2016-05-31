package org.polytech.polybigbalance.interfaces;

import java.util.EnumSet;
import java.util.Set;

import org.cora.graphics.elements.Button;
import org.cora.graphics.elements.TextButton;
import org.cora.graphics.font.Font;
import org.cora.graphics.font.TextRenderer;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.layers.Level;

public class LevelSelector extends Interface
{
    private final int COLUMNS = 3;

    private TextButton returnButton;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton addPlayer;
    private TextButton removePlayer;
    private TextButton play;
    private Button[] buttons;
    private TextRenderer player_text;
    private Level[] levels;
    private int selected;
    private int player;
    private int page;

    public LevelSelector(Level[] levels)
    {
        final int PAGE_BUTTON_SIZE = 100;
        final int PADDING = 10;
        final int LEVEL_BUTTON_SIZE = 150;
        final int SPACING = (Constants.WINDOW_WIDTH - 2 * (PADDING + PAGE_BUTTON_SIZE) - LEVEL_BUTTON_SIZE * this.COLUMNS) / (this.COLUMNS + 1);
        final int START_WIDTH = PADDING + PAGE_BUTTON_SIZE + SPACING;

        this.levels = levels;
        this.selected = 1;
        this.player = 1;
        this.page = 0;

        Font font = new Font();
        font.initialize(Constants.TEXT_FONT_SURFACE, 32);
        font.setSpaceSize(15);

        this.returnButton = new TextButton(10, 10, 150, 60, font);
        this.returnButton.setAddColor(new myColor(-0.3f, -0.3f, -0.3f, -0.3f));
        this.returnButton.setTxt("< Menu");

        this.leftButton = new TextButton(10, (Constants.WINDOW_HEIGHT - PAGE_BUTTON_SIZE) / 2, PAGE_BUTTON_SIZE, PAGE_BUTTON_SIZE, font);
        this.leftButton.setTxt("<");
        this.rightButton = new TextButton(Constants.WINDOW_WIDTH - PAGE_BUTTON_SIZE - 10, (Constants.WINDOW_HEIGHT - PAGE_BUTTON_SIZE) / 2, PAGE_BUTTON_SIZE, PAGE_BUTTON_SIZE, font);
        this.rightButton.setTxt(">");

        this.buttons = new Button[this.levels.length];

        for (int i = 0; i < this.levels.length; i++) {
            this.buttons[i] = new Button(START_WIDTH + (LEVEL_BUTTON_SIZE + SPACING) * (i % 3), (Constants.WINDOW_HEIGHT - LEVEL_BUTTON_SIZE) / 2, LEVEL_BUTTON_SIZE, LEVEL_BUTTON_SIZE);
        }

        this.player_text = new TextRenderer(font);

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
        return (this.page == this.levels.length / this.COLUMNS);
    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<InterfaceEvent> handleEvent(Input input)
    {
        boolean colliding = this.returnButton.isColliding(input.getMousePosV());

        this.returnButton.setHighlighted(colliding);

        if (input.isKeyDown(Input.KEY_ESC) || (input.isMousePressed(Input.MOUSE_BUTTON_1) && colliding)) {
            return EnumSet.of(InterfaceEvent.POP);
        }

        if (input.isMousePressed(Input.MOUSE_BUTTON_1)) {
            if (!isFirstPage() && this.leftButton.isColliding(input.getMousePosV())) {
                page--;
            }
            if (!isLastPage() && this.rightButton.isColliding(input.getMousePosV())) {
                page++;
            }
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        this.returnButton.render(g);

        if (!isFirstPage()) {
            this.leftButton.render(g);
        }
        if (!isLastPage()) {
            this.rightButton.render(g);
        }

        for (int i = 0; i < (isLastPage() ? this.levels.length % this.COLUMNS : 3); i++) {
            this.buttons[this.page * this.COLUMNS + i].render(g);
        }

        this.player_text.print(g, "AAA", 500, 500);
        this.player_text.print(g, Integer.toString(this.player), 600, 500);
    }
}

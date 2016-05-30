package org.polytech.polybigbalance.interfaces;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.cora.maths.Rectangle;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.base.Layer;
import org.polytech.polybigbalance.base.Player;
import org.polytech.polybigbalance.layers.ActivePlayer;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.layers.TextScore;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Handles a game
 */
public class LevelSelector extends Interface
{
    private Map<String, Layer> layers;
    private boolean drawing;

    private Player[] players;
    private int currentPlayer;

    private long waitStartTime;
    private Rectangle drawnRectangle;

    /**
     * @param nbPlayers number of players
     * @param level level to display
     */
    public LevelSelector(int nbPlayers, Level level)
    {
        this.players = new Player[nbPlayers];
        for(int i = 0 ; i < nbPlayers ; i++)
        {
            this.players[i] = new Player("Player " + (i + 1));
        }
        this.currentPlayer = 0;

        this.layers = new HashMap<>();
        this.drawing = false;

        level.initialize();
        this.layers.put("level", level);

        this.layers.put("score", new TextScore(Constants.TEXT_FONT_SURFACE, Constants.WINDOW_WIDTH / 2, 20, 200));

        if(nbPlayers > 1)
        {
            String playerName = this.players[this.currentPlayer].getName();
            this.layers.put("activePlayer", new ActivePlayer(playerName, Constants.TEXT_FONT_SURFACE, 20, 20, 200));
        }

        this.waitStartTime = 0;
    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        if(this.waitStartTime != 0)
        {
            if(this.waitStartTime + 5000 <= System.currentTimeMillis())
            {
                this.waitStartTime = 0;
                this.updateScore();
                this.nextPlayer();
            }
        }

        for(Layer l : this.layers.values())
        {
            l.update(dt);
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public Set<InterfaceEvent> handleEvent(Input input)
    {
        if(this.waitStartTime == 0)
        {
            if(input.isMouseDown(Input.MOUSE_BUTTON_1))
            {
                this.drawing = true;
                ((Level) this.layers.get("level")).drawRectangle(input.getMousePosV());
            }
            else if(this.drawing)
            {
                this.drawing = false;
                this.drawnRectangle = ((Level) this.layers.get("level")).endDrawRectangle();

                if(this.drawnRectangle != null)
                {
                    this.waitStartTime = System.currentTimeMillis();
                }
            }
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        for(Layer l : this.layers.values())
        {
            l.render(g);
        }
    }

    /**
     * Turn goes to next player who hasn't lost yet
     */
    private void nextPlayer()
    {
        do
        {
            if(this.currentPlayer < this.players.length - 1)
            {
                this.currentPlayer++;
            }
            else
            {
                this.currentPlayer = 0;
            }
        } while(this.players[this.currentPlayer].hasLost());

        ((ActivePlayer) this.layers.get("activePlayer")).setPlayerName(this.players[this.currentPlayer].getName());
        ((TextScore) this.layers.get("score")).setScore(this.players[this.currentPlayer].getScore());
    }

    /**
     * Updates the score of the current player with the drawn rectangle
     */
    private void updateScore()
    {
        int score = this.players[this.currentPlayer].getScore();
        score += (this.drawnRectangle.getWidth() * this.drawnRectangle.getHeight()) / 10;

        this.players[this.currentPlayer].setScore(score);
    }
}
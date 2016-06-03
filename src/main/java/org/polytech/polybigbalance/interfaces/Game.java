/**
 * @author Hugo PIGEON
 * @author Ronan JAMET
 * (Enter name feature)
 */

package org.polytech.polybigbalance.interfaces;

import org.cora.graphics.elements.TextButton;
import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;
import org.cora.maths.Rectangle;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.Layer;
import org.polytech.polybigbalance.base.Player;
import org.polytech.polybigbalance.layers.ActivePlayer;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.layers.ScoresSummary;
import org.polytech.polybigbalance.layers.TextScore;
import org.polytech.polybigbalance.score.Score;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Handles a game
 */
public class Game extends Interface
{
    private Map<String, Layer> layers;
    private boolean drawing;

    private Player[] players;
    private int currentPlayer;

    private long waitStartTime;
    private Rectangle drawnRectangle;

    private TextButton namefield;

    private int enteringName;

    private boolean gameFinished;
    private boolean highScoresRecorded;

    /**
     * @param nbPlayers
     *            number of players
     * @param level
     *            level to display
     */
    public Game(int nbPlayers, Level level)
    {
        this.players = new Player[nbPlayers];
        for (int i = 0; i < nbPlayers; i++)
        {
            this.players[i] = new Player("Player " + (i + 1));
        }
        this.currentPlayer = 0;

        this.layers = new HashMap<>();
        this.drawing = false;

        level.initialize();
        this.layers.put("level", level);

        this.layers.put("score", new TextScore(Constants.WINDOW_WIDTH / 2, 20, 200));

        String playerName = this.players[this.currentPlayer].getName();
        this.layers.put("activePlayer", new ActivePlayer(playerName, 20, 20, 200));

        namefield = new TextButton(Constants.WINDOW_WIDTH / 2 - Constants.WINDOW_WIDTH / 8, Constants.WINDOW_HEIGHT / 2 - Constants.WINDOW_HEIGHT / 32, Constants.WINDOW_WIDTH / 4, Constants.WINDOW_HEIGHT / 16, Constants.FONT);
        namefield.setTextMiddleLeft();
        namefield.setTxt("");
        namefield.setPreRendering(false);

        namefield.setBackColor(myColor.WHITE(0.7f));
        namefield.setRecColor(myColor.BLACK());
        namefield.setActive(false);

        enteringName = 0;

        this.waitStartTime = 0;
        this.gameFinished = false;
        this.highScoresRecorded = false;
    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        if (this.waitStartTime != 0)
        {
            if (this.waitStartTime + 5000 <= System.currentTimeMillis())
            {
                this.waitStartTime = 0;
                this.updateScore();
                this.nextPlayer();
            }
            else if (((Level) this.layers.get("level")).checkRectangleFallen() > 0)
            {
                this.players[this.currentPlayer].setLost(true);
                this.waitStartTime = 0;
                this.nextPlayer();
            }
        }

        for (Layer l : this.layers.values())
        {
            l.update(dt);
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public Set<InterfaceEvent> handleEvents(Input input)
    {
        if (input.isKeyPressed(Input.KEY_ESC))
        {
            return EnumSet.of(InterfaceEvent.PAUSE);
        }

        if (this.enteringName > 0)
        {
            return enteringName(input);
        }

        if (this.gameFinished)
        {
            return finishGame(input);
        }

        if (this.waitStartTime == 0)
        {
            if (input.isMouseDown(Input.MOUSE_BUTTON_1))
            {
                this.drawing = true;
                ((Level) this.layers.get("level")).drawRectangle(input.getMousePosV());
            }
            else if (this.drawing)
            {
                this.drawing = false;
                this.drawnRectangle = ((Level) this.layers.get("level")).endDrawRectangle();

                if (this.drawnRectangle != null)
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
        super.render(g);
        for (Layer l : this.layers.values())
        {
            l.render(g);
        }

        namefield.render(g);
    }

    /**
     * Turn goes to next player who hasn't lost yet
     */
    private void nextPlayer()
    {
        int cpt = 0;

        do
        {
            if (this.currentPlayer < this.players.length - 1)
            {
                this.currentPlayer++;
            }
            else
            {
                this.currentPlayer = 0;
                cpt++;

                if (cpt > 1)
                {
                    this.gameFinished = true;
                }
            }
        } while (this.players[this.currentPlayer].hasLost() && !this.gameFinished);

        ((ActivePlayer) this.layers.get("activePlayer")).setPlayerName(this.players[this.currentPlayer].getName());
        ((TextScore) this.layers.get("score")).setScore(this.players[this.currentPlayer].getScore());
    }

    /**
     * Enter name
     * Created by Ronan-j
     * Modified by Tudal-L
     */
    private Set<InterfaceEvent> enteringName(Input input)
    {
        String name;

        if (!namefield.getActive())
        {
            input.clearTemp();
            namefield.setActive(true);
        }

        if (input.getTemp().equals(""))
        {
            name = players[enteringName - 1].getName();
            namefield.getTextRenderer().setFontColor(myColor.BLACK(0.4f));
        }
        else
        {
            name = input.getTemp();
            namefield.getTextRenderer().setFontColor(myColor.BLACK());
        }

        if (input.isKeyDown(Input.KEY_ENTER) && !input.getTemp().equals(""))
        {
            players[enteringName - 1].setName(name);

            this.namefield.setActive(false);

            this.enteringName = -this.enteringName;

            input.clearKeys();
        }
        else
        {
            namefield.setTxt(name);
        }

        return EnumSet.of(InterfaceEvent.OK);
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

    /**
     * Tasks to do before ending the game (scores summary, highscores)
     * 
     * @return true when the tasks are finished
     */
    private Set<InterfaceEvent> finishGame(Input input)
    {
        if (!this.highScoresRecorded)
        {
            Level level = (Level) this.layers.get("level");

            if (this.enteringName < 0)
            {
                this.enteringName = -this.enteringName - 1;

                level.getHighScores().addScore(new Score(this.players[this.enteringName].getName(), this.players[this.enteringName].getScore()));

                this.enteringName++;
            }

            while (this.enteringName < this.players.length && !level.getHighScores().isHighScore(this.players[this.enteringName].getScore()))
            {
                this.enteringName++;
            }

            this.enteringName++;

            if (this.enteringName > this.players.length)
            {
                this.enteringName = -1;
                this.highScoresRecorded = true;
                this.layers.put("scoresSummary", new ScoresSummary(this.players));
            }

            return EnumSet.of(InterfaceEvent.OK);
        }
        else
        {
            return ((ScoresSummary) this.layers.get("scoresSummary")).handleEvents(input);
        }
    }
}
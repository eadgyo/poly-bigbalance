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

    private boolean gameFinished;
    private boolean highScoresRecorded;

    /**
     * @param nbPlayers number of players
     * @param level level to display
     */
    public Game(int nbPlayers, Level level)
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

        this.layers.put("score", new TextScore(Constants.WINDOW_WIDTH / 2, 20, 200));

        String playerName = this.players[this.currentPlayer].getName();
        this.layers.put("activePlayer", new ActivePlayer(playerName, 20, 20, 200));

        this.waitStartTime = 0;
        this.gameFinished = false;
        this.highScoresRecorded = false;
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
            else if(((Level) this.layers.get("level")).checkRectangleFallen() > 0)
            {
                this.players[this.currentPlayer].setLost(true);
                this.waitStartTime = 0;
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
        if(this.gameFinished)
        {
            return this.finishGame(input);
        }

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
        else
        {
            input.clearMouse();
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);
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
        int cpt = 0;

        do
        {
            if(this.currentPlayer < this.players.length - 1)
            {
                this.currentPlayer++;
            }
            else
            {
                this.currentPlayer = 0;
                cpt++;

                if(cpt > 1)
                {
                    this.gameFinished = true;
                    this.layers.put("scoresSummary", new ScoresSummary(this.players));
                }
            }
        } while(this.players[this.currentPlayer].hasLost() && !this.gameFinished);

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

    /**
     * Tasks to do before ending the game (scores summary, highscores)
     * @return true when the tasks are finished
     */
    private Set<InterfaceEvent> finishGame(Input input)
    {
        if(!this.highScoresRecorded)
        {
            Level level = (Level) this.layers.get("level");

            for(Player p : this.players)
            {
                if(level.getHighScores().isHighScore(p.getScore()))
                {
                    // TODO ask player's name
                    level.getHighScores().addScore(new Score(p.getName(), p.getScore()));
                }
            }

            this.highScoresRecorded = true;
        }

        return ((ScoresSummary) this.layers.get("scoresSummary")).handleButtons(input);
    }
}
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
import org.cora.maths.Circle;
import org.cora.maths.Rectangle;
import org.cora.maths.Vector2D;
import org.polytech.polybigbalance.Constants;
import org.polytech.polybigbalance.base.Interface;
import org.polytech.polybigbalance.base.InterfaceEvent;
import org.polytech.polybigbalance.base.Player;
import org.polytech.polybigbalance.layers.ActivePlayer;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.layers.ScoresSummary;
import org.polytech.polybigbalance.layers.TextScore;
import org.polytech.polybigbalance.score.Score;

import java.util.EnumSet;
import java.util.Set;

/**
 * Handles a game
 */
public class Game extends Interface
{
    private final static int WAITING_TIME = 5000;

    private boolean drawing;

    private Level levelLayer;
    private TextScore scoreLayer;
    private ActivePlayer activePlayerLevel;
    private ScoresSummary scoresSummary;

    private Player[] players;
    private int currentPlayer;

    private Rectangle drawnRectangle;

    private TextButton namefield;

    private int enteringName;

    private boolean gameFinished;
    private boolean highScoresRecorded;
    private Circle waitingCircle;

    /**
     * @param nbPlayers
     *            number of players
     * @param level
     *            level to display
     */
    public Game(int nbPlayers, Level level)
    {
        players = new Player[nbPlayers];
        for (int i = 0; i < nbPlayers; i++)
        {
            players[i] = new Player("Player " + (i + 1));
        }
        currentPlayer = 0;
        drawing = false;

        level.initialize(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        level.startIntroAnimation();

        levelLayer = level;
        scoreLayer = new TextScore(Constants.WINDOW_WIDTH / 2, 20, 200);

        String playerName = this.players[this.currentPlayer].getName();
        activePlayerLevel = new ActivePlayer(playerName, 20, 20, 200);

        namefield = new TextButton(Constants.WINDOW_WIDTH / 2 - Constants.WINDOW_WIDTH / 8, Constants.WINDOW_HEIGHT / 2 - Constants.WINDOW_HEIGHT / 32, Constants.WINDOW_WIDTH / 4, Constants.WINDOW_HEIGHT / 16, Constants.FONT);
        namefield.setTextMiddleLeft();
        namefield.setTxt("");
        namefield.setPreRendering(false);

        namefield.setBackColor(myColor.WHITE(0.7f));
        namefield.setRecColor(myColor.BLACK());
        namefield.setActive(false);

        enteringName = 0;

        scoresSummary = null;
        waitingCircle = new Circle(new Vector2D(Constants.WINDOW_WIDTH - Constants.WINDOW_HEIGHT / 16, Constants.WINDOW_HEIGHT / 20), Constants.WINDOW_HEIGHT/24);

        gameFinished = false;
        highScoresRecorded = false;
    }

    @Override
    public Set<InterfaceEvent> update(float dt)
    {
        if (!levelLayer.hasFinishedWaiting())
        {
            if (levelLayer.getEndWaiting() <= System.currentTimeMillis())
            {
                levelLayer.setWaitStartTime(0);
                updateScore();
                nextPlayer();
            }
            else if (levelLayer.checkRectangleFallen())
            {
                players[this.currentPlayer].setLost(true);
                levelLayer.setWaitStartTime(0);
                nextPlayer();
            }
        }

        levelLayer.update(dt);
        scoreLayer.update(dt);
        activePlayerLevel.update(dt);

        if (scoresSummary != null)
            scoresSummary.update(dt);

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

        if (input.isMouseDown(Input.MOUSE_BUTTON_1) && levelLayer.ready())
        {
            drawing = true;
            levelLayer.drawRectangle(input.getMousePosV());
        }
        else if (drawing)
        {
            if (levelLayer.hasFinishedWaiting())
            {
                drawing = false;
                drawnRectangle = levelLayer.endDrawRectangle();

                if (drawnRectangle != null)
                {
                    levelLayer.setWaitStartTime(System.currentTimeMillis());
                }
            }
            else
            {
                drawing = false;
                levelLayer.resetRectangle();
            }
        }
        else
        {
            drawing = false;
        }

        return EnumSet.of(InterfaceEvent.OK);
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);

        levelLayer.render(g);
        scoreLayer.render(g);
        activePlayerLevel.render(g);

        namefield.render(g);

        if (!levelLayer.hasFinishedWaiting())
        {
            g.setColor(myColor.BLACK());
            float rad = (float) (Math.PI/2 + 2*Math.PI * (WAITING_TIME - (System.currentTimeMillis() - levelLayer.getWaitStartTime())) /WAITING_TIME);
            g.fillCircleFixed(waitingCircle, 60, (float) -Math.PI/2, -rad);
        }

        if (scoresSummary != null)
            scoresSummary.render(g);
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

        activePlayerLevel.setPlayerName(this.players[this.currentPlayer].getName());
        scoreLayer.setScore(this.players[this.currentPlayer].getScore());
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
        if (!highScoresRecorded)
        {
            if (enteringName < 0)
            {
                enteringName = -enteringName - 1;
                levelLayer.getHighScores().addScore(new Score(players[enteringName].getName(), players[enteringName].getScore()));
                enteringName++;
            }

            while (enteringName < players.length && !levelLayer.getHighScores().isHighScore(players[enteringName].getScore()))
            {
                this.enteringName++;
            }

            enteringName++;

            if (this.enteringName > players.length)
            {
                enteringName = -1;
                highScoresRecorded = true;
                scoresSummary = new ScoresSummary(players);
            }

            return EnumSet.of(InterfaceEvent.OK);
        }
        else
        {
            return scoresSummary.handleEvents(input);
        }
    }
}
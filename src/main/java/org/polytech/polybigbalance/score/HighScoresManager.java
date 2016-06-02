package org.polytech.polybigbalance.score;

import java.io.Serializable;
import java.util.HashMap;

import org.cora.graphics.manager.FileManager;
import org.polytech.polybigbalance.layers.Level;

/**
 * 
 * @author Tudal
 * 
 */

/**
 * 
 * Contains a map of high scores, mapped with the level id
 *
 */

public class HighScoresManager implements Serializable
{
    private static final long serialVersionUID = 1L;

    private static String FILE_PATH = "./save/";
    private static String FILE_NAME = "hs.save";

    private HashMap<Integer, HighScores> highScores;

    // ----- CONSTRUCTOR ----- //

    public HighScoresManager()
    {
        this.highScores = new HashMap<>();
    }

    // ----- GETTER ----- //

    public HighScores getHighScores(int id)
    {
        HighScores hs = this.highScores.get(new Integer(id));
        if (hs == null)
        {
            hs = addHighScores(id);
        }
        return hs;
    }

    // ----- SETTER ----- //

    public static HighScoresManager load()
    {
        if (FileManager.isFileExisting(HighScoresManager.FILE_PATH + HighScoresManager.FILE_NAME))
        {
            return (HighScoresManager) FileManager.loadObject(HighScoresManager.FILE_PATH + HighScoresManager.FILE_NAME);
        }
        else
        {
            return new HighScoresManager();
        }

    }

    public void save()
    {
        if (!FileManager.isFolderExisting(HighScoresManager.FILE_PATH))
        {
            FileManager.createFolder(HighScoresManager.FILE_PATH);
        }
        FileManager.saveObject(HighScoresManager.FILE_PATH + HighScoresManager.FILE_NAME, this);
    }

    public HighScores addHighScores(int id)
    {
        this.highScores.put(new Integer(id), new HighScores());
        return this.highScores.get(new Integer(id));
    }

    public void setHighScores(int id, Level level)
    {
        level.setHighScores(getHighScores(id));
    }
}

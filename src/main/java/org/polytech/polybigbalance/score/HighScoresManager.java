/**
 * 
 * @author Tugdwal
 * 
 */

package org.polytech.polybigbalance.score;

import java.io.Serializable;
import java.util.HashMap;

import org.cora.graphics.manager.FileManager;
import org.polytech.polybigbalance.layers.Level;

public class HighScoresManager implements Serializable
{
    private static String FILE_PATH = "./save/";
    private static String FILE_NAME = "hs.save";
    private HashMap<Integer, HighScores> m_scores;

    // ----- CONSTRUCTOR ----- //

    public HighScoresManager()
    {
        this.m_scores = new HashMap<>();
    }

    // ----- GETTER ----- //

    public HighScores getHighScores(int id)
    {
        HighScores hs = this.m_scores.get(new Integer(id));
        if (hs == null) {
            hs = addHighScores(id);
        }
        return hs;
    }

    // ----- SETTER ----- //

    public static HighScoresManager load()
    {
        if (FileManager.isFileExisting(HighScoresManager.FILE_PATH + HighScoresManager.FILE_NAME)) {
            return (HighScoresManager) FileManager.loadObject(HighScoresManager.FILE_PATH + HighScoresManager.FILE_NAME);
        } else {
            return new HighScoresManager();
        }

    }

    public void save()
    {
        if (!FileManager.isFolderExisting(HighScoresManager.FILE_PATH)) {
            FileManager.createFolder(HighScoresManager.FILE_PATH);
        }
        FileManager.saveObject(HighScoresManager.FILE_PATH + HighScoresManager.FILE_NAME, this);
    }

    public HighScores addHighScores(int id)
    {
        return this.m_scores.put(new Integer(id), new HighScores());
    }

    public void setHighScores(int id, Level level)
    {
        level.setHighScores(getHighScores(id));
    }

    public void test()
    {
        System.out.println(this.m_scores.keySet());
    }
}

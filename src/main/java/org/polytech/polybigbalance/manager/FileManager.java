package org.polytech.polybigbalance.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;

public class FileManager
{
    private DocumentBuilderFactory factory;

    // Parseur
    public final String binFolder = "res";
    public final String objectsFolder = binFolder + "/" + "Objects";
    public final String textureFolder = binFolder + "/" + "pic";

    private static FileManager INSTANCE = new FileManager();

    private FileManager()
    {
	createFolder(binFolder);
	createFolder(objectsFolder);
    }

    // Load
    public BufferedImage loadTexture(String file)
    {
	BufferedImage texture;
	try
	{
	    texture = ImageIO.read(new File(file));
	} catch (IOException e)
	{
	    texture = null;
	}
	return texture;
    }

    public ArrayList<String> getAllFilesName(String Directory, boolean canCreate)
    {
	ArrayList<String> files = new ArrayList<String>();
	File folder = new File(Directory);
	if (folder.exists())
	{
	    File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++)
	    {
		if (listOfFiles[i].isFile())
		{
		    files.add(listOfFiles[i].getName());
		}
	    }
	} 
	else if (canCreate)
	{
	    try
	    {
		folder.mkdir();
	    } 
	    catch (SecurityException se)
	    {
		
	    }
	}
	return files;
    }

    public boolean isFolderExisting(String Directory)
    {
	File folder = new File(Directory);
	return folder.exists();
    }

    public boolean createFolder(String directory)
    {
	File folder = new File(directory);
	if (!folder.exists())
	{
	    try
	    {
		folder.mkdir();
		return true;
	    } catch (SecurityException se)
	    {

	    }
	}
	return true;
    }

    public boolean isFileExisting(String Directory)
    {
	File file = new File(Directory);
	return file.exists() && file.isFile();
    }

    public static FileManager getInstance()
    {
	return INSTANCE;
    }
}

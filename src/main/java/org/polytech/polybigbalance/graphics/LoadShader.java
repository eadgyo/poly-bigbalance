package org.polytech.polybigbalance.graphics;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import static org.lwjgl.opengl.GL20.*;

/**
 * Class that allows to load shaders
 */
public abstract class LoadShader
{
    /**
     * Loads vertex shader and fragment shader from files
     * @param vertexFilePath path to the vertex shader file
     * @param fragmentFilePath path to the fragment shader file
     * @return the ID of the program
     */
    public static int loadShaders(final String vertexFilePath, final String fragmentFilePath)
    {
        // Creating shaders
        int vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);

        String vertexShaderCode = "", fragmentShaderCode = "";

        // Read shaders' code from files
        try
        {
            // vertex shader
            List<String> lines = Files.readAllLines(FileSystems.getDefault().getPath(vertexFilePath));

            StringBuilder sb = new StringBuilder();
            for(String s : lines)
            {
                sb.append("\n" + s);
            }
            vertexShaderCode = sb.toString();


            // fragment shader
            lines = Files.readAllLines(FileSystems.getDefault().getPath(fragmentFilePath));

            sb = new StringBuilder();
            for(String s : lines)
            {
                sb.append("\n" + s);
            }
            fragmentShaderCode = sb.toString();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        // Compiling vertex shader
        //System.out.println("Compiling shader : " + vertexFilePath);
        glShaderSource(vertexShaderId, vertexShaderCode);
        glCompileShader(vertexShaderId);

        // Compiling fragment shader
        //System.out.println("Compiling shader : " + fragmentFilePath);
        glShaderSource(fragmentShaderId, fragmentShaderCode);
        glCompileShader(fragmentShaderId);

        // Linking the program
        //System.out.println("Linking program");
        int programId = glCreateProgram();
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        return programId;
    }
}

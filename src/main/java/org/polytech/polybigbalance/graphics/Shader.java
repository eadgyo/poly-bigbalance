package org.polytech.polybigbalance.graphics;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Class that handles shaders
 */
public class Shader
{
    private int vertexId, fragmentId, programId;
    private String vertexFilePath, fragmentFilePath;

    /**
     * @param vertexFilePath path to the file containing the vertex shader's code
     * @param fragmentFilePath path to the file containing the fragment shader's code
     */
    public Shader(String vertexFilePath, String fragmentFilePath)
    {
        this.vertexFilePath = vertexFilePath;
        this.fragmentFilePath = fragmentFilePath;
        this.vertexId = 0;
        this.fragmentId = 0;
        this.programId = 0;
    }

    /**
     * Loads the shaders from the files
     * @return false if an error occurred
     */
    public boolean load()
    {
        // Deleting the shaders that already exist
        if(glIsShader(this.vertexId))
        {
            glDeleteShader(this.vertexId);
        }
        if(glIsShader(this.fragmentId))
        {
            glDeleteShader(this.fragmentId);
        }
        if(glIsProgram(this.programId))
        {
            glDeleteProgram(this.programId);
        }


        // Compiling the shaders
        try
        {
            this.vertexId = compileShader(GL_VERTEX_SHADER, this.vertexFilePath);
            this.fragmentId = compileShader(GL_FRAGMENT_SHADER, this.fragmentFilePath);
        }
        catch(ShaderException e)
        {
            System.err.println(e.getMessage());
            return false;
        }

        // Creating and linking the program
        this.programId = glCreateProgram();

        glAttachShader(this.programId, this.vertexId);
        glAttachShader(this.programId, this.fragmentId);

        glLinkProgram(this.programId);

        // Checking program
        int errorLink = glGetProgrami(this.programId, GL_LINK_STATUS);

        if(errorLink != GL_TRUE)
        {
            String error = glGetShaderInfoLog(this.programId);
            System.err.println(error);

            glDeleteProgram(this.programId);

            return false;
        }

        return true;
    }

    /**
     * Reads and compiles a shader
     * @param type type of the shader
     * @param filePath path to the file containing the shader's code
     * @return the shader's ID
     * @throws ShaderException
     */
    public int compileShader(int type, String filePath) throws ShaderException
    {
        // Creating the shader
        int shader = glCreateShader(type);

        if(shader == 0)
        {
            throw new ShaderException("Error: shader type '" + type + "' does not exist");
        }

        // Reading the file
        List<String> lines = null;
        try
        {
            lines = Files.readAllLines(FileSystems.getDefault().getPath(filePath));
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        for(String s : lines)
        {
            sb.append("\n" + s);
        }
        String shaderCode = sb.toString();

        // Compiling the shader
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);

        // Checking compilation
        int compilationError = glGetShaderi(shader, GL_COMPILE_STATUS);

        if(compilationError != GL_TRUE)
        {
            String error = glGetShaderInfoLog(shader);

            glDeleteShader(shader);

            throw new ShaderException(error);
        }

        return shader;
    }

    public int getProgramId()
    {
        return programId;
    }

    /**
     * Correctly deletes the shaders
     */
    public void delete()
    {
        glDeleteShader(this.vertexId);
        glDeleteShader(this.fragmentId);
        glDeleteProgram(this.programId);
    }
}

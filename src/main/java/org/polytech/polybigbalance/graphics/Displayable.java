package org.polytech.polybigbalance.graphics;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

/**
 * Defines an object that can be displayed
 */
public abstract class Displayable
{
    protected Shader shader;
    protected int vboVertices, vboIndices, vao;
    protected FloatBuffer verticesBuffer;
    protected ShortBuffer indicesBuffer;

    /**
     * @param pathVertexShader path to the file containing the vertex shader's code
     * @param pathFragmentShader path to the file containing the fragment shader's code
     */
    public Displayable(String pathVertexShader, String pathFragmentShader)
    {
        this.shader = new Shader(pathVertexShader, pathFragmentShader);
        this.shader.load();
    }

    /**
     * Creates the data to use for the display
     */
    public void init()
    {
        // Creating vertices VBO
        if(glIsBuffer(this.vboVertices))
        {
            glDeleteBuffers(this.vboVertices);
        }

        this.vboVertices = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.vboVertices);
        glBufferData(GL_ARRAY_BUFFER, this.verticesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Creating indices VBO
        if(glIsBuffer(this.vboIndices))
        {
            glDeleteBuffers(this.vboIndices);
        }

        this.vboIndices = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.vboIndices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        // Creating the VAO
        if(glIsVertexArray(this.vao))
        {
            glDeleteVertexArrays(this.vao);
        }

        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);
        glBindBuffer(GL_ARRAY_BUFFER, this.vboVertices);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 6, 0);

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, Float.BYTES * 6, Float.BYTES * 3);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    /**
     * Displays the object
     * @param mvpBuffer buffer containing the MVP matrix
     */
    public void display(FloatBuffer mvpBuffer)
    {
        glUseProgram(this.shader.getProgramId());

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.vboIndices);
        glBindVertexArray(this.vao);
        glUniformMatrix4fv(glGetUniformLocation(this.shader.getProgramId(), "MVP"), false, mvpBuffer);

        glDrawElements(GL_TRIANGLES, this.indicesBuffer);
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glUseProgram(0);
    }

    /**
     * Correctly deletes the buffers
     */
    public void delete()
    {
        glDeleteBuffers(this.vboVertices);
        glDeleteBuffers(this.vboIndices);
        glDeleteVertexArrays(this.vao);
        this.shader.delete();
    }
}

package org.polytech.polybigbalance.graphics;

import org.lwjgl.BufferUtils;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Allows to draw a 2D rectangle
 */
public class Rectangle
{
    // data
    private Vec2 pos, dim;
    private Color color;

    // display
    private Shader shader;
    private int vboVertices, vboIndices, vao;
    private FloatBuffer verticesBuffer;
    private ShortBuffer indicesBuffer;

    /**
     * @param position position of the top left corner
     * @param dimension width and the height of the rectangle
     * @param color color that will fill the rectangle
     * @param pathVertexShader path to the file containing the vertex shader's code
     * @param pathFragmentShader path to the file containing the fragment shader's code
     */
    public Rectangle(Vec2 position, Vec2 dimension, Color color, String pathVertexShader, String pathFragmentShader)
    {
        this.pos = position;
        this.dim = dimension;
        this.color = color;
        this.shader = new Shader(pathVertexShader, pathFragmentShader);
        this.shader.load();
        this.init();
    }

    /**
     * Use black as the default color
     * @param position position of the top left corner
     * @param dimension width and the height of the rectangle
     * @param pathVertexShader path to the file containing the vertex shader's code
     * @param pathFragmentShader path to the file containing the fragment shader's code
     */
    public Rectangle(Vec2 position, Vec2 dimension, String pathVertexShader, String pathFragmentShader)
    {
        this(position, dimension, Color.BLACK, pathVertexShader, pathFragmentShader);
    }

    /**
     * Creates the data to use for the display
     */
    private void init()
    {
        // Generating data
        float r = this.color.getRed() / 255.0f, g = this.color.getGreen() / 255.0f, b = this.color.getBlue() / 255.0f;

        float[] vertices = {
                pos.getX(),              pos.getY(),              0.0f, r, g, b,
                pos.getX(),              pos.getY() + dim.getY(), 0.0f, r, g, b,
                pos.getX() + dim.getX(), pos.getY() + dim.getY(), 0.0f, r, g, b,
                pos.getX() + dim.getX(), pos.getY(),              0.0f, r, g, b
        };
        this.verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        this.verticesBuffer.put(vertices).flip();

        short[] indices = {
                0, 1, 3,
                1, 2, 3
        };
        this.indicesBuffer = BufferUtils.createShortBuffer(indices.length);
        this.indicesBuffer.put(indices).flip();

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
     * Displays the rectangle
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
     * Correctly deletes the rectangle
     */
    public void delete()
    {
        glDeleteBuffers(this.vboVertices);
        glDeleteBuffers(this.vboIndices);
        glDeleteVertexArrays(this.vao);
        this.shader.delete();
    }
}

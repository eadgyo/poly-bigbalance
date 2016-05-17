package org.polytech.polybigbalance.graphics;

import org.lwjgl.BufferUtils;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Allows to draw a 2D rectangle
 */
public class Rectangle extends Displayable
{
    private Vec2 pos, dim;
    private Color color;

    /**
     * @param position position of the top left corner
     * @param dimension width and the height of the rectangle
     * @param color color that will fill the rectangle
     * @param pathVertexShader path to the file containing the vertex shader's code
     * @param pathFragmentShader path to the file containing the fragment shader's code
     */
    public Rectangle(Vec2 position, Vec2 dimension, Color color, String pathVertexShader, String pathFragmentShader)
    {
        super(pathVertexShader, pathFragmentShader);

        this.pos = position;
        this.dim = dimension;
        this.color = color;
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

    @Override
    public void init()
    {
        // Generating data
        float r = this.color.getRed() / 255.0f, g = this.color.getGreen() / 255.0f, b = this.color.getBlue() / 255.0f;

        float[] vertices = {
                pos.getX(),              pos.getY(),              0.0f, r, g, b,
                pos.getX(),              pos.getY() + dim.getY(), 0.0f, r, g, b,
                pos.getX() + dim.getX(), pos.getY() + dim.getY(), 0.0f, r, g, b,
                pos.getX() + dim.getX(), pos.getY(),              0.0f, r, g, b};
        this.verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        this.verticesBuffer.put(vertices).flip();

        short[] indices = {
                0, 1, 3,
                1, 2, 3
        };
        this.indicesBuffer = BufferUtils.createShortBuffer(indices.length);
        this.indicesBuffer.put(indices).flip();

        super.init();
    }

    @Override
    protected void createVao()
    {
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
}

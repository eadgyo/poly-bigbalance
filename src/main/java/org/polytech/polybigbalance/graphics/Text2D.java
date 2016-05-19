package org.polytech.polybigbalance.graphics;

import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Displays a text in 2D
 */
public class Text2D extends Displayable
{
    // data
    private String text;
    private Vec2 pos;
    private int size;

    // display
    private int textureId;
    private int samplerId;

    /**
     * @param fontPath path to the font as a BMP file
     * @param text text to display
     * @param pos top left corner of the text's first letter
     * @param size size of the text
     * @param shader shader to use
     * @throws IOException
     */
    public Text2D(String fontPath, String text, Vec2 pos, int size, Shader shader) throws IOException
    {
        super(shader);

        this.text = text;
        this.pos = pos;
        this.size = size;
        this.textureId = Texture.loadFromFile(fontPath);
        this.init();
    }

    /**
     * @param fontPath path to the font as a BMP file
     * @param text text to display
     * @param pos top left corner of the text's first letter
     * @param size size of the text
     * @param pathVertexShader path to the file containing the vertex shader's code
     * @param pathFragmentShader path to the file containing the fragment shader's code
     * @throws IOException
     */
    public Text2D(String fontPath, String text, Vec2 pos, int size, String pathVertexShader, String pathFragmentShader)
            throws IOException
    {
        super(pathVertexShader, pathFragmentShader);

        this.text = text;
        this.pos = pos;
        this.size = size;
        this.textureId = Texture.loadFromFile(fontPath);
        this.init();
    }

    /**
     * @param fontPath path to the font as a BMP file
     * @param text text to display
     * @param pos top left corner of the text's first letter
     * @param pathVertexShader path to the file containing the vertex shader's code
     * @param pathFragmentShader path to the file containing the fragment shader's code
     * @throws IOException
     */
    public Text2D(String fontPath, String text, Vec2 pos, String pathVertexShader, String pathFragmentShader)
            throws IOException
    {
        this(fontPath, text, pos, 16, pathVertexShader, pathFragmentShader);
    }

    @Override
    public void init()
    {
        this.samplerId = glGetUniformLocation(this.shader.getProgramId(), "textureSampler");

        this.verticesBuffer = BufferUtils.createFloatBuffer(this.text.length() * 16);
        this.indicesBuffer = BufferUtils.createShortBuffer(this.text.length() * 6);

        for(int i = 0 ; i < this.text.length() ; i++)
        {
            char c = this.text.charAt(i);

            float uvXLeft = (c % 16) / 16.0f;
            float uvXRight = uvXLeft + (1.0f / 16.0f);
            float uvYTop = (c / 16) / 16.0f;
            float uvYBottom = uvYTop + (1.0f / 16.0f);

            float[] vertices = {
                    this.pos.getX() + i * this.size,             this.pos.getY() + this.size, uvXLeft,  uvYBottom,
                    this.pos.getX() + i * this.size + this.size, this.pos.getY() + this.size, uvXRight, uvYBottom,
                    this.pos.getX() + i * this.size + this.size, this.pos.getY(),             uvXRight, uvYTop,
                    this.pos.getX() + i * this.size,             this.pos.getY(),             uvXLeft,  uvYTop
            };

            this.verticesBuffer.put(vertices);

            short row = (short) (i * 4);

            short[] indices = {
                    row, (short) (row + 1), (short) (row + 2),
                    row, (short) (row + 2), (short) (row + 3)
            };
            this.indicesBuffer.put(indices);
        }

        this.verticesBuffer.flip();
        this.indicesBuffer.flip();

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
                glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 4, 0);

                glEnableVertexAttribArray(1);
                glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 4, Float.BYTES * 2);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void display(FloatBuffer mvpBuffer)
    {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, this.textureId);
            glUniform1i(this.samplerId, 0);

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            super.display(mvpBuffer);

            glDisable(GL_BLEND);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
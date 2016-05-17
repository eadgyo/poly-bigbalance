package org.polytech.polybigbalance.graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Operations on textures
 */
public abstract class Texture
{
    /**
     * Loads a texture from the given BMP file
     * @param imagePath path to the BMP image to load
     * @return ID of the created texture
     * @throws IOException
     */
    public static int loadFromFile(String imagePath) throws IOException
    {
        final short BYTES_PER_PIXEL = 4;

        BufferedImage img = ImageIO.read(new File(imagePath));

        // Loading all the pixels
        int[] pixels = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * BYTES_PER_PIXEL);

        for(int y = 0 ; y < img.getHeight() ; y++)
        {
            for(int x = 0 ; x < img.getWidth() ; x++)
            {
                int pixel = pixels[y * img.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF)); // red
                buffer.put((byte) ((pixel >> 8) & 0xFF)); // green
                buffer.put((byte) (pixel & 0xFF)); // blue
                buffer.put((byte) ((pixel >> 24) & 0xFF)); // alpha
            }
        }

        buffer.flip();

        // Create an OpenGL texture
        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0);

        return textureId;
    }
}

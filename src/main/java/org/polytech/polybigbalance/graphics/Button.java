package org.polytech.polybigbalance.graphics;

import java.awt.*;
import java.nio.FloatBuffer;

/**
 * Defines a button made of a text inside a rectangle
 */
public class Button
{
    private GLRectangle rectangle;
    private Text2D text;

    /**
     * @param position position of the top left corner
     * @param text text to display inside the buttons
     * @param font ID of the font to use for the text
     * @param rectangleShader shader to use for the rectangle
     * @param textShader shader to use for the text
     */
    public Button(Vec2 position, String text, int font, Shader rectangleShader, Shader textShader)
    {
        Vec2 textPosition = new Vec2(position.getX() + 5.0f, position.getY() + 5.0f);
        this.text = new Text2D(font, text, textPosition, 24, textShader);

        Vec2 textSize = this.text.getPixelSize();
        Vec2 rectangleSize = new Vec2(textSize.getX() + 10.0f, textSize.getY() + 10.0f);
        this.rectangle = new GLRectangle(position, rectangleSize, Color.GRAY, rectangleShader);
    }

    /**
     * Displays the button
     * @param mvpBuffer buffer containing the MVP matrix
     */
    public void display(FloatBuffer mvpBuffer)
    {
        this.rectangle.display(mvpBuffer);
        this.text.display(mvpBuffer);
    }

    /**
     * Correctly deletes the buffers
     */
    public void delete()
    {
        this.rectangle.delete();
        this.text.delete();
    }
}

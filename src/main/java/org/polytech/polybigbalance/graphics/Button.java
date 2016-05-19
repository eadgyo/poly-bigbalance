package org.polytech.polybigbalance.graphics;

import java.awt.*;
import java.io.IOException;
import java.nio.FloatBuffer;

public class Button
{
    private Rectangle rectangle;
    private Text2D text;

    public Button(Vec2 position, String text, int font, Shader colorShader, Shader textShader)
    {
        Vec2 textPosition = new Vec2(position.getX() + 5.0f, position.getY() + 5.0f);
        this.text = new Text2D(font, text, textPosition, 24, textShader);

        Vec2 textSize = this.text.getPixelSize();
        Vec2 rectangleSize = new Vec2(textSize.getX() + 10.0f, textSize.getY() + 10.0f);
        this.rectangle = new Rectangle(position, rectangleSize, Color.GRAY, colorShader);
    }

    public void display(FloatBuffer mvpBuffer)
    {
        this.rectangle.display(mvpBuffer);
        this.text.display(mvpBuffer);
    }

    public void delete()
    {
        this.rectangle.delete();
        this.text.delete();
    }
}

package org.polytech.polybigbalance;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.Surface;
import org.cora.graphics.input.Input;
import org.cora.graphics.manager.TextureManager;
import org.polytech.polybigbalance.layers.Level;
import org.polytech.polybigbalance.layers.Level1;
import org.polytech.polybigbalance.layers.Score;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;

public class PolyBigBalance
{
    public static void main(String[] args)
    {
        Graphics g = new Graphics();
        g.init(Constants.WINDOW_TITLE, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        g.initGL(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        Surface textFontSurface = TextureManager.createTexture(Constants.RESOURCES_PATH + "font.bmp");
        g.loadTextureGL(textFontSurface);

        Input input = new Input();
        input.initGL(g.getScreen());

        Level lvl1 = new Level1();
        lvl1.initialize();

        Score score = new Score(textFontSurface, Constants.WINDOW_WIDTH / 2, 30, 200, 40);
        score.setScore(1000, g);

        float timeElapsed = System.nanoTime() / 1000000000.0f;

        while (glfwWindowShouldClose(g.getScreen()) == GL_FALSE)
        {
            g.clear();

            timeElapsed = (System.nanoTime() / 1000000000.0f) - timeElapsed;
            lvl1.update(timeElapsed);
            timeElapsed = System.nanoTime() / 1000000000.0f;

            lvl1.render(g);
            score.render(g);

            input.update();

            g.swapGL();
        }
    }
}

package org.polytech.polybigbalance;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.input.Input;
import org.polytech.polybigbalance.interfaces.Game;
import org.polytech.polybigbalance.layers.Level1;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;

public class PolyBigBalance
{
    public static void main(String[] args)
    {
        Graphics g = new Graphics();
        g.init(Constants.WINDOW_TITLE, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        g.initGL(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        g.loadTextureGL(Constants.TEXT_FONT_SURFACE);

        Input input = new Input();
        input.initGL(g.getScreen());

        Game game = new Game(3, new Level1());

        float timeElapsed = System.nanoTime() / 1000000000.0f;

        while (glfwWindowShouldClose(g.getScreen()) == GL_FALSE)
        {
            g.clear();

            timeElapsed = (System.nanoTime() / 1000000000.0f) - timeElapsed;
            game.update(timeElapsed);
            timeElapsed = System.nanoTime() / 1000000000.0f;

            game.render(g);

            input.update();
            game.handleEvent(input);

            g.swapGL();
        }
    }
}

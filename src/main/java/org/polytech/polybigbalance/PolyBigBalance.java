package org.polytech.polybigbalance;

import org.cora.graphics.graphics.Graphics;
import org.cora.graphics.graphics.myColor;
import org.cora.graphics.input.Input;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;

public class PolyBigBalance
{
    public static void main(String[] args)
    {
        Graphics g = new Graphics();
        g.init(Constants.WINDOW_TITLE, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        g.initGL(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        Input input = new Input();
        input.initGL(g.getScreen());

        while (glfwWindowShouldClose(g.getScreen()) == GL_FALSE)
        {
            g.clear();
            g.setColor(myColor.WHITE());

            input.update();

            g.swapGL();
        }
    }
}

package org.polytech.polybigbalance.test;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.glClear;

import org.polytech.polybigbalance.graphics.Graphics;
import org.polytech.polybigbalance.graphics.base.Image;
import org.polytech.polybigbalance.input.Input;
import org.polytech.polybigbalance.manager.FileManager;

public class Main
{
    public static void main(String[] args)
    {
	Graphics g = new Graphics();
	g.init("B2OBA", 800, 600);
	g.initGL(800, 600);
	
	Input input = new Input();
	input.initGL(g.getScreen());
	
	FileManager fm = FileManager.getInstance();
	
	
	Image test = new Image();
	//test.initialize(surface, width, height, currentFrame)
	
        while (glfwWindowShouldClose(g.getScreen()) == GL_FALSE)
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
 
            glfwSwapBuffers(g.getScreen());
            
            input.update();
            
            if (input.getKeyDown(Input.KEY_ENTER))
            {
        	System.out.println("Enter");
            }
            
            if (input.isMouseMoving())
            {
        	System.out.println("mouse moving:" + input.getMousePosX() + ", " + input.getMousePosY());
            }
            
            if (input.isMouseScrolling())
            {
        	System.out.println("mouse scolling:" + input.getMouseWheelX() + ", " + input.getMouseWheelY());
            }
            
        }
    }
}

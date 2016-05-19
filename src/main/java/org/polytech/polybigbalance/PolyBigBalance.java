package org.polytech.polybigbalance;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.polytech.polybigbalance.graphics.Button;
import org.polytech.polybigbalance.graphics.Shader;
import org.polytech.polybigbalance.graphics.Vec2;

import java.io.IOException;
import java.nio.FloatBuffer;

import static com.hackoeur.jglm.Matrices.lookAt;
import static com.hackoeur.jglm.Matrices.ortho;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Main class
 */
public class PolyBigBalance
{

    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;

    // The window handle
    private long window;

    public static void main(String[] args)
    {
        new PolyBigBalance().run();
    }

    /**
     * Starts the program
     */
    private void run()
    {
        try
        {
            init();
            loop();

            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        }
        finally
        {
            // Terminate GLFW and release the GLFWErrorCallback
            glfwTerminate();
            errorCallback.release();
        }
    }

    /**
     * Initialisation of the window
     */
    private void init()
    {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if(glfwInit() != GLFW_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create the window
        window = glfwCreateWindow(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, Constants.WINDOW_TITLE, NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback()
        {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods)
            {
                if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                    glfwSetWindowShouldClose(window, GLFW_TRUE); // We will detect this in our rendering loop
            }
        });

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(window,
                (vidmode.width() - Constants.WINDOW_WIDTH) / 2, (vidmode.height() - Constants.WINDOW_HEIGHT) / 2);

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    /**
     * Game loop
     */
    private void loop()
    {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        int vertexArrayId = glGenVertexArrays();
        glBindVertexArray(vertexArrayId);

        Mat4 projection = ortho(0.0f, (float)Constants.WINDOW_WIDTH, (float)Constants.WINDOW_HEIGHT, 0.0f, 1.0f, 0.0f);
        Mat4 view = lookAt(new Vec3(0, 0, 1), new Vec3(0, 0, 0), new Vec3(0, 1, 0));
        Mat4 model = new Mat4(1.0f);
        Mat4 mvp = projection.multiply(view).multiply(model);


        // Loading the shaders
        Shader colorShader = new Shader(Constants.SHADERS_PATH + "ColorVertShader.glsl", Constants.SHADERS_PATH +
                                                                                         "ColorFragShader.glsl");
        Shader textShader = new Shader(Constants.SHADERS_PATH + "TextVertShader.glsl", Constants.SHADERS_PATH +
                                                                                       "TextFragShader.glsl");
        colorShader.load();
        textShader.load();

        Button button = null;
        try
        {
            button = new Button(new Vec2(100.0f, 100.0f), "Button", "src/main/resources/font.bmp", colorShader,
                    textShader);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Uncomment for 3D
        // glEnable(GL_DEPTH_TEST);
        // glDepthFunc(GL_LESS);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while(glfwWindowShouldClose(window) == GLFW_FALSE)
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            FloatBuffer mvpBuffer = BufferUtils.createFloatBuffer(mvp.getNumColumns() * mvp.getNumRows());
            mvpBuffer.put(mvp.getBuffer()).flip();

            if(button != null)
            {
                button.display(mvpBuffer);
            }

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }

        if(button != null)
        {
            button.delete();
        }
    }
}
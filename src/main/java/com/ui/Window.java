package com.ui;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    public static final int INIT_WIDTH = 1000;
    public static final int INIT_HEIGHT = 500;
    public static final String INIT_TITLE = "Window";

    private static final HashMap<Long,Window> windows = new HashMap<>();

    private long glfwWindow = 0L;
    private int width, height;
    private String title;
    private float aspect;
    public boolean isRunning = false;
    private final GLFWKeyCallback keyCallback;
    private boolean vsync;

    public Window() {
        this(INIT_WIDTH, INIT_HEIGHT, INIT_TITLE, true);
    }

    public Window(int width, int height, String title, boolean vsync) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.aspect = (float) width / height;
        this.vsync = vsync;

        /* Creating a temporary window for getting the available OpenGL version */
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        long temp = glfwCreateWindow(1, 1, "", NULL, NULL);
        glfwMakeContextCurrent(temp);
        GL.createCapabilities();
        GLCapabilities caps = GL.getCapabilities();
        glfwDestroyWindow(temp);

        /* Reset and set window hints */
        glfwDefaultWindowHints();
        if (caps.OpenGL32) {
            /* Hints for OpenGL 3.2 core profile */
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        } else if (caps.OpenGL21) {
            /* Hints for legacy OpenGL 2.1 */
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        } else {
            throw new RuntimeException("Neither OpenGL 3.2 nor OpenGL 2.1 is "
                    + "supported, you may want to update your graphics driver.");
        }
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        /* Create window with specified OpenGL context */
        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfwWindow == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window!");
        }

        windows.put(glfwWindow,this);

        /* Center window on screen */
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(glfwWindow,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        /* Create OpenGL context */
        glfwMakeContextCurrent(glfwWindow);
        GL.createCapabilities();

        /* Enable v-sync */
        if (vsync) {
            glfwSwapInterval(1);
        }

        /* Set key callback */
        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        };
        glfwSetKeyCallback(glfwWindow, keyCallback);
        glfwShowWindow(glfwWindow);

        this.isRunning = true;
    }

    public void update() {
        glfwSwapBuffers(glfwWindow);
        glfwPollEvents();
    }

    public void dispose() {
        this.isRunning = false;
        windows.put(glfwWindow,null);
        glfwDestroyWindow(glfwWindow);
        keyCallback.free();
    }

    public static Window getWindow(long window) {
        return windows.get(window);
    }

    /**
     * Returns if the window is closing.
     *
     * @return true if the window should close, else false
     */
    public boolean isClosing() {
        return glfwWindowShouldClose(glfwWindow);
    }

    public void setVSync(boolean vsync) {
        this.vsync = vsync;
        glfwSwapInterval(vsync ? 1 : 0);
    }

    public void setWidth(int width) {
        this.width = width;
        this.aspect = (float) width / height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.aspect = (float) width / height;
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(glfwWindow,title);
    }

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public float getAspect() { return this.aspect; }
    public boolean isRunning() { return this.isRunning; }
    public boolean isVSyncEnabled() { return this.vsync; }

}

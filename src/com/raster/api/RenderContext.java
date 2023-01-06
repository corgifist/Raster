package com.raster.api;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGetIntegeri;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class RenderContext {

    private String tittle;
    private int width, height;
    private long window;

    public RenderContext(String title, int width, int height) {
        this.tittle = title;
        this.width = width;
        this.height = height;

        if (!glfwInit()) {
            throw new RenderException("failed to initialize glfw");
        }
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        this.window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RenderException("failed to create glfw window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glViewport(0, 0, width, height);
        // glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEBUG_OUTPUT);
        glDebugMessageCallback(new GLDebugMessageCallback() {
            @Override
            public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
                try (MemoryStack stack = MemoryStack.stackPush()) {
                    PointerBuffer msg = stack.pointers(message);
                    ByteBuffer msgText = msg.getByteBuffer(length);
                    System.out.println(MemoryUtil.memUTF8(msgText));
                }
            }
        }, 0);


        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer maxAttribs = stack.callocInt(1);
            glGetIntegerv(GL_MAX_VERTEX_ATTRIBS, maxAttribs);
            System.out.println("GL_MAX_VERTEX_ATTRIBS: " + maxAttribs.get(0));
        }

        glfwSetFramebufferSizeCallback(window, new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int newWidth, int newHeight) {
                glViewport(0, 0, newWidth, newHeight);
            }
        });
        glfwSwapInterval(1);
    }

    public void setClearColor(float x) {
        setClearColor(x, x, x);
    }
    public void setClearColor(float r, float g, float b) {
        glClearColor(r, g, b, 1.0f);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void update() {
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public void terminate() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }
}

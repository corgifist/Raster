package com.raster.registry;

import com.raster.api.*;
import com.raster.util.ApplicationAdapter;
import org.joml.Vector3f;

public class GLTest implements ApplicationAdapter {

    private RenderContext context;
    private RenderQueue queue;
    private ShaderProgram shader;
    private Actor triangle;

    private Camera camera;

    @Override
    public void prepare() {
        this.context = new RenderContext("GLTest", 800, 600);
        this.queue = new RenderQueue(context);

        this.context.setClearColor(0.2f);
        this.context.clear();

        this.shader = new ShaderProgram("default.vert", "default.frag");
        this.queue.setShader(shader);

        this.camera = new Camera(new Vector3f(), new Vector3f(), 800, 600, 60, 0.1f, 1000f);
        camera.getPosition().z = -3;

        this.triangle = new Actor(new float[] {
                0.5f,  0.5f, 0.0f,  // top right
                0.5f, -0.5f, 0.0f,  // bottom right
                -0.5f, -0.5f, 0.0f,  // bottom left
                -0.5f,  0.5f, 0.0f   // top left
        }, new int[] {
                0, 1, 3,   // first triangle
                1, 2, 3
        }, new float[] {
                1, 1,
                1, 0,
                0, 0,
                0, 1
        });
        triangle.setDiffuse(new Texture("banknote.png"));
        triangle.getScale().x = 3;
        triangle.getScale().y = 2;
    }

    @Override
    public void render() {
        context.clear();
        camera.load(queue);

        queue.push(triangle);
        queue.render();
        queue.reset();
    }

    @Override
    public void event() {
        triangle.getRotation().add(0.01f, 0.01f, 0.01f);
    }

    @Override
    public RenderContext context() {
        return context;
    }
}

package com.raster.registry;

import com.raster.api.*;
import com.raster.util.ApplicationAdapter;
import org.joml.Vector3f;

public class GLTest implements ApplicationAdapter {

    private RenderContext context;
    private RenderQueue queue;
    private ShaderProgram shader;
    private StaticMeshActor cube;

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

        this.cube = new StaticMeshActor("cube.obj");
        cube.setDiffuse(Texture.create("banknote.png"));
        cube.getScale().mul(0.5f);
    }

    @Override
    public void render() {
        context.clear();
        camera.load(queue);

        queue.push(cube);
        queue.render();
        queue.reset();
    }

    @Override
    public void event() {
        cube.getRotation().add(0.01f, 0.01f, 0.01f);
    }

    @Override
    public RenderContext context() {
        return context;
    }
}

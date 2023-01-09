package com.raster.registry;

import com.raster.api.actors.*;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderQueue;
import com.raster.util.ApplicationAdapter;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class GLTest implements ApplicationAdapter {

    private RenderContext context;
    private RenderQueue queue;
    private ShaderProgram shader;
    private StaticMeshActor cube;
    private PointLightActor light, light2;
    private AmbientLightActor ambient;
    private DirectionalLightActor directional;

    private CameraActor camera;

    private LightDispatcherActor lightDispatcher;

    @Override
    public void prepare() {
        this.context = new RenderContext("GLTest", 800, 600, true);
        this.queue = new RenderQueue(context);

        this.context.setClearColor(0.2f);
        this.context.clear();

        this.shader = new ShaderProgram("default.vert", "default.frag");
        this.queue.setShader(shader);

        this.camera = new CameraActor(new Vector3f(), new Vector3f(), 800, 600, 60, 0.1f, 1000f);

        this.cube = new StaticMeshActor("stanford-bunny.obj");
        cube.getScale().mul(0.7f);
        cube.getPosition().z = -3;
        cube.getPosition().y = -0.1f;
        this.light = new PointLightActor(new Vector3f(0, 0, 2));
        light.setColor(new Vector3f(1, 1, 0).mul(0.4f));
        this.light2 = new PointLightActor(new Vector3f(0, 0, 2));
        light2.setColor(new Vector3f(0, 1, 1).mul(0.4f));
        this.ambient = new AmbientLightActor(0.1f);
        this.directional = new DirectionalLightActor(new Vector3f(0.2f, 0, 1), new Vector3f(0.2f, 0, 0));

        this.lightDispatcher = new LightDispatcherActor();
    }

    @Override
    public void render() {
        context.clear();

        lightDispatcher.dispatch(queue, light, light2, ambient, directional);
        queue.push(camera);
        queue.push(cube);
        queue.render();
        queue.reset();
    }

    @Override
    public void resize(int width, int height) {
        camera.resizeProjection(CameraActor.createResizedCamera(camera, width, height));
    }

    @Override
    public void event() {
        light.getPosition().x = (float) Math.sin(glfwGetTime()) * 3;
        light2.getPosition().x = (float) -Math.sin(glfwGetTime()) * 3;

        cube.getRotation().add(0.01f, 0.01f, 0.01f);
    }

    @Override
    public RenderContext context() {
        return context;
    }
}

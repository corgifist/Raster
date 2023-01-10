package com.raster.registry;

import com.raster.api.actors.*;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderQueue;
import com.raster.util.ApplicationAdapter;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Random;

public class VoidFlow implements ApplicationAdapter {

    private RenderContext context;
    private RenderQueue queue;
    private ArrayList<StaticMeshActor> flow;

    private CameraActor camera;

    private LightDispatcherActor dispatcher;

    private static Vector3f[] pastelColors = new Vector3f[] {
            new Vector3f(1, 0.6f, 0.7f),
            new Vector3f(1, 0.8f, 0.7f),
            new Vector3f(1, 1, 0.7f),
            new Vector3f(0.7f, 1, 0.8f),
            new Vector3f(0.7f, 0.9f, 1)
    };

    private AmbientLightActor ambient;
    private DirectionalLightActor directional;

    @Override
    public void prepare() {
        this.context = new RenderContext("VoidFlow", 800, 600, true);
        this.context.setClearColor(0.8f, 1, 0.9f);

        this.queue = new RenderQueue(context);
        this.queue.setShader(new ShaderProgram("default.vert", "default.frag"));

        this.flow = new ArrayList<>();
        Random pastelGenerator = new Random();
        StaticMeshActor flowSource = new StaticMeshActor("cube.obj");
        for (int x = 0; x < 60; x++) {
            for (int y = 0; y < 60; y++) {
                int targetX = x * 4;
                int targetY = y * 4;
                StaticMeshActor flowElement = new StaticMeshActor(flowSource);
                flowElement.setPosition(new Vector3f(targetX, -targetY, -1));
                flowElement.setColor(pastelColors[pastelGenerator.nextInt(0, pastelColors.length)]);
                flowElement.setScale(new Vector3f(pastelGenerator.nextFloat(1, 2)));
                flow.add(flowElement);
            }
        }

        this.camera = new CameraActor(new Vector3f(-8, 8, -10), new Vector3f(0, 0.2f, 0), 800, 600, 60, 0.1f, 1000);

        this.dispatcher = new LightDispatcherActor();

        this.directional = new DirectionalLightActor(new Vector3f(0, 1, 0.4f));
        this.ambient = new AmbientLightActor(0.2f);
    }

    @Override
    public void render() {
        context.clear();
        dispatcher.dispatch(queue, ambient, directional);
        queue.push(camera);

        for (AbstractActor flowElement : flow) {
            queue.push(flowElement);
        }

        queue.render();
        queue.reset();
    }

    @Override
    public void event() {
        float slideSpeed = 0.01f;
        camera.getPosition().x -= slideSpeed;
        camera.getPosition().y += slideSpeed;
    }

    @Override
    public void mouseMoveEvent(Vector2f position) {
    }

    @Override
    public void resize(int width, int height) {
        camera.resizeProjection(CameraActor.createResizedCamera(camera, width, height));
    }

    @Override
    public RenderContext context() {
        return context;
    }
}

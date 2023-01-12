package com.raster.registry;

import com.raster.api.actors.*;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderQueue;
import com.raster.util.ApplicationAdapter;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Sponza implements ApplicationAdapter {

    private RenderContext context;
    private RenderQueue queue;

    private CameraActor camera;
    private FreeCameraActor freedom;

    private StaticMeshActor sponza;

    private AmbientLightActor ambient;
    private ArrayList<AbstractActor> lights;

    private ProjectionDispatcherActor projectionDispatcher;
    private LightDispatcherActor lightDispatcher;
    private LightMarkerDispatcherActor markerDispatcher;

    @Override
    public void prepare() {
        this.context = new RenderContext("Sponza", 1280, 720, true);
        this.context.setClearColor(0.2f);

        this.queue = new RenderQueue(context);
        queue.setShader(new ShaderProgram("default"));

        this.camera = new CameraActor(new Vector3f(), new Vector3f(), 1280, 720, 60, 0.1f, 1000f);
        this.freedom = new FreeCameraActor(camera);

        this.projectionDispatcher = new ProjectionDispatcherActor(1280, 720);
        this.lightDispatcher = new LightDispatcherActor();
        this.markerDispatcher = new LightMarkerDispatcherActor();

        this.sponza = new StaticMeshActor("sponza.obj");
        sponza.setScale(new Vector3f(0.002f));
        sponza.setSpecularIntensity(0);

        this.ambient = new AmbientLightActor(0.2f);
        this.lights = new ArrayList<>();
        lights.add(ambient);
        lights.add(new DirectionalLightActor(new Vector3f(0.2f, 1, 0.5f)));
    }

    @Override
    public void render() {
        projectionDispatcher.bind();
        context.clear();
        lightDispatcher.dispatch(queue, lights.toArray(new AbstractActor[] {}));
        markerDispatcher.dispatch(queue, lights.toArray(new AbstractActor[] {}));
        queue.push(freedom);
        queue.push(sponza);
        queue.render();
        projectionDispatcher.unbind();

        projectionDispatcher.render(queue);
        queue.reset();
    }


    @Override
    public void event() {
        freedom.keyboard(context);
    }

    @Override
    public void mouseMoveEvent(Vector2f position) {
        freedom.mouse(position);
    }

    @Override
    public void resize(int width, int height) {
        camera.resizeProjection(CameraActor.createResizedCamera(camera, width, height));
        projectionDispatcher.resize(width, height);
    }

    @Override
    public RenderContext context() {
        return context;
    }
}

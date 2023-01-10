package com.raster.registry;

import com.raster.api.actors.*;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.gl.Texture;
import com.raster.api.gl.TextureLoadData;
import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderQueue;
import com.raster.util.ApplicationAdapter;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Gazebo implements ApplicationAdapter {

    private RenderContext context;
    private RenderQueue queue;

    private StaticMeshActor gazeboMesh;

    private CameraActor camera;

    private AmbientLightActor ambient;
    private DirectionalLightActor directional;
    private LightDispatcherActor lightDispatcher;

    private FreeCameraActor freedom;

    private EnvironmentSkyboxActor skybox;

    @Override
    public void prepare() {
        this.context = new RenderContext("Gazebo Demo", 1280, 720, true);
        this.context.setClearColor(0.5f);

        this.queue = new RenderQueue(context);
        this.queue.setShader(new ShaderProgram("default.vert", "default.frag"));

        this.ambient = new AmbientLightActor(0.1f);
        this.directional = new DirectionalLightActor(new Vector3f(0, 1, 0.2f));

        this.lightDispatcher = new LightDispatcherActor();

        this.gazeboMesh = new StaticMeshActor("gazebo.obj");

        this.camera = new CameraActor(new Vector3f(), new Vector3f(), 1280, 720, 60, 0.1f, 1000);
        this.freedom = new FreeCameraActor(camera);
        this.skybox = new EnvironmentSkyboxActor("lightsky");
    }


    @Override
    public void render() {
        context.clear();

        lightDispatcher.dispatch(queue, ambient, directional);
        queue.push(freedom);
        queue.push(gazeboMesh);
        queue.push(skybox);
        queue.render();
        queue.reset();
        freedom.resetState();
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

    }

    @Override
    public RenderContext context() {
        return context;
    }
}

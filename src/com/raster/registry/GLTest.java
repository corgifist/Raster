package com.raster.registry;

import com.raster.api.actors.AmbientLightActor;
import com.raster.api.actors.CameraActor;
import com.raster.api.actors.PointLightActor;
import com.raster.api.actors.StaticMeshActor;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.gl.Texture;
import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderQueue;
import com.raster.util.ApplicationAdapter;
import org.joml.Vector3f;

public class GLTest implements ApplicationAdapter {

    private RenderContext context;
    private RenderQueue queue;
    private ShaderProgram shader;
    private StaticMeshActor cube;
    private PointLightActor light;
    private AmbientLightActor ambient;

    private CameraActor camera;

    @Override
    public void prepare() {
        this.context = new RenderContext("GLTest", 800, 600);
        this.queue = new RenderQueue(context);

        this.context.setClearColor(0.2f);
        this.context.clear();

        this.shader = new ShaderProgram("default.vert", "default.frag");
        this.queue.setShader(shader);

        this.camera = new CameraActor(new Vector3f(), new Vector3f(), 800, 600, 60, 0.1f, 1000f);

        this.cube = new StaticMeshActor("stanford-bunny.obj");
        cube.setDiffuse(Texture.create("banknote.png"));
        cube.getScale().mul(0.7f);
        cube.getPosition().z = -3;
        cube.getPosition().y = -0.1f;
        this.light = new PointLightActor(new Vector3f(0, 0, 2), new Vector3f(0.6f));
        this.ambient = new AmbientLightActor(0.1f);
    }

    @Override
    public void render() {
        context.clear();

        queue.push(light, ambient);
        queue.push(camera);
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

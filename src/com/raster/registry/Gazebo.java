package com.raster.registry;

import com.raster.api.actors.*;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderQueue;
import com.raster.util.ApplicationAdapter;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Gazebo implements ApplicationAdapter {

    private RenderContext context;
    private RenderQueue queue;

    private StaticMeshActor gazeboMesh;
    private StaticMeshActor planeMesh;

    private CameraActor camera;

    private AmbientLightActor ambient;
    private DirectionalLightActor directional;
    private LightDispatcherActor lightDispatcher;

    private FreeCameraActor freedom;

    private EnvironmentSkyboxActor skybox;
    private ProjectionDispatcherActor projection;

    private InversionEffectActor inversionEffect;

    private PointLightActor point1, point2;
    private PointLightActor redGloss;

    private LightMarkerDispatcherActor marker;

    @Override
    public void prepare() {
        this.context = new RenderContext("Gazebo Demo", 1280, 720, true);
        this.context.setClearColor(0.5f);

        this.queue = new RenderQueue(context);
        this.queue.setShader(new ShaderProgram("default"));

        this.ambient = new AmbientLightActor(0.1f);
        this.directional = new DirectionalLightActor(new Vector3f(0, 1, 0.2f)); // looks too funky lol
        this.point1 = new PointLightActor(new Vector3f(0, 0, 0), new Vector3f(1, 1, 0));
        this.point2 = new PointLightActor(new Vector3f(), new Vector3f(0, 1, 1));
        this.redGloss = new PointLightActor(new Vector3f(0, 0.2f, 0), new Vector3f(1, 0, 0));

        this.lightDispatcher = new LightDispatcherActor();
        this.marker = new LightMarkerDispatcherActor();

        this.gazeboMesh = new StaticMeshActor("gazebo.obj");
        this.planeMesh = new StaticMeshActor("quad.obj");
        planeMesh.setRotation(new Vector3f(-1.6f, 0, 0));
        planeMesh.setPosition(new Vector3f(0, -0.01f, 0));

        this.camera = new CameraActor(new Vector3f(), new Vector3f(), 1280, 720, 60, 0.1f, 1000);
        this.freedom = new FreeCameraActor(camera);
        this.skybox = new EnvironmentSkyboxActor("lightsky");

        this.projection = new ProjectionDispatcherActor(1280, 720);

        this.inversionEffect = new InversionEffectActor(0);
    }


    @Override
    public void render() {
        projection.bind();
        context.clear();

        projection.dispatch(queue);
        lightDispatcher.dispatch(queue, ambient, point1, point2, redGloss);
        marker.dispatch(queue, point1, point2, redGloss);

        queue.push(freedom);
        queue.push(gazeboMesh, planeMesh);
        queue.push(skybox);
        queue.render();
        freedom.resetState();
        projection.unbind();

        projection.render(queue);
        queue.reset();
    }

    @Override
    public void event() {
        freedom.keyboard(context);

        point1.setPosition(new Vector3f((float) Math.sin(glfwGetTime())));
        point2.setPosition(new Vector3f((float) -Math.sin(glfwGetTime())));
        redGloss.getPosition().y = (float) Math.sin(glfwGetTime());
    }

    @Override
    public void mouseMoveEvent(Vector2f position) {
        freedom.mouse(position);
    }

    @Override
    public void resize(int width, int height) {
        camera.resizeProjection(CameraActor.createResizedCamera(camera, width, height));
        projection.resize(width, height);
    }

    @Override
    public RenderContext context() {
        return context;
    }
}

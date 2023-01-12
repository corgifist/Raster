package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.gl.CubemapTexture;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderQueue;
import com.raster.api.render.WorldMatrix;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class EnvironmentSkyboxActor implements AbstractActor {

    private CubemapTexture skyboxTexture;
    private ShaderProgram skyboxShader;

    private StaticMeshActor cubeMesh;


    public EnvironmentSkyboxActor(String name) {
        this.skyboxTexture = new CubemapTexture(name);
        this.skyboxShader = new ShaderProgram("skybox.vert", "skybox.frag");
        this.cubeMesh = new StaticMeshActor("cube.obj");
        cubeMesh.setScale(new Vector3f(1));
    }

    @Override
    public void render(RenderQueue queue) {
        glDisable(GL_CULL_FACE);
        glDepthFunc(GL_LEQUAL);
        ShaderProgram initialShader = queue.getShader();
        queue.setShader(skyboxShader);

        Matrix4f oldView = new Matrix4f(WorldMatrix.view);
        WorldMatrix.view = new Matrix4f(new Matrix3f(WorldMatrix.view));
        skyboxTexture.updateSampler(queue, 0);
        cubeMesh.render(queue);
        WorldMatrix.view = oldView;

        queue.setShader(initialShader);
        glDepthFunc(GL_LESS);
        glEnable(GL_CULL_FACE);
    }
}

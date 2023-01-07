package com.raster.api.actors;

import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderQueue;
import org.joml.Vector3f;

public class AmbientLightActor extends AbstractActor {

    private float intensity;
    private Vector3f color;

    public AmbientLightActor(float intensity, Vector3f color) {
        this.intensity = intensity;
        this.color = color;
    }

    public AmbientLightActor(float intensity) {
        this(intensity, new Vector3f(1));
    }

    @Override
    public void render(RenderQueue queue) {
        ShaderProgram shader = queue.getShader();
        shader.setUniform("ambient.intensity", intensity);
        shader.setUniform("ambient.color", color);
    }
}

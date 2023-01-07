package com.raster.api.actors;

import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderQueue;
import org.joml.Vector3f;

public class PointLightActor extends AbstractActor {

    private Vector3f position, color;

    public PointLightActor(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    @Override
    public void render(RenderQueue queue) {
        ShaderProgram shader = queue.getShader();

        // support of only one light for now
        shader.setUniform("light.position", position);
        shader.setUniform("light.color", color);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}

package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderQueue;
import org.joml.Vector3f;

public class PointLightActor implements AbstractActor {

    private Vector3f position, color;

    private float specularDamper;

    public PointLightActor(Vector3f position, Vector3f color, float specularDamper) {
        this.position = position;
        this.color = color;
        this.specularDamper = specularDamper;
    }

    public PointLightActor(Vector3f position, Vector3f color) {
        this(position, color, 32);
    }

    public PointLightActor(Vector3f position) {
        this(position, new Vector3f(1));
    }

    @Override
    public void render(RenderQueue queue) {
        Raster.warning("use LightDispatcherActor to load point light/lights");
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

    public float getSpecularDamper() {
        return specularDamper;
    }

    public void setSpecularDamper(float specularDamper) {
        this.specularDamper = specularDamper;
    }
}

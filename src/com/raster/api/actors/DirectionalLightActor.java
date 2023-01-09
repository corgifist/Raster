package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.render.RenderQueue;
import org.joml.Vector3f;

public class DirectionalLightActor implements AbstractActor {

    private Vector3f direction, color;
    private float specularDamper;

    public DirectionalLightActor(Vector3f direction, Vector3f color, float specularDamper) {
        this.direction = direction;
        this.color = color;
        this.specularDamper = specularDamper;
    }

    public DirectionalLightActor(Vector3f direction, Vector3f color) {
        this(direction, color, 32);
    }

    public DirectionalLightActor(Vector3f direction) {
        this(direction, new Vector3f(1));
    }

    @Override
    public void render(RenderQueue queue) {
        Raster.warning("use LightDispatcherActor to load directional light (no multiple directional lights support)");
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
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

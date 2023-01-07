package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderQueue;
import org.joml.Vector3f;

public class AmbientLightActor implements AbstractActor {

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
        Raster.warning("use LightDispatcherActor to load ambient light!");
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}

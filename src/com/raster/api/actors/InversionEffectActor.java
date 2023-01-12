package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.render.RenderQueue;

public class InversionEffectActor extends PostProcessor implements AbstractActor {

    private float inversionIntensity;

    public InversionEffectActor(float inversionIntensity) {
        this.inversionIntensity = inversionIntensity;
    }

    @Override
    public void render(RenderQueue queue) {
        ProjectionDispatcherActor.checkScope();
        queue.getShader().setUniform("postProcessingMetadata.inversionIntensity", inversionIntensity);
    }

    public float getInversionIntensity() {
        return inversionIntensity;
    }

    public void setInversionIntensity(float inversionIntensity) {
        this.inversionIntensity = inversionIntensity;
    }
}

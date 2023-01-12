package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.gl.Texture;
import com.raster.api.render.RenderQueue;

public class RawTexturedActor implements AbstractActor {

    private RawActor raw;
    public Texture texture;

    public RawTexturedActor(RawActor actor, Texture texture) {
        this.raw = actor;
        this.texture = texture;
    }

    public RawTexturedActor(RawActor actor) {
        this(actor, null);
    }

    @Override
    public void render(RenderQueue queue) {
        queue.getShader().setUniform("properties.texturesEnabled", fromBoolean(texture != null));
        if (texture != null) texture.updateSampler(queue, 0); // diffuse
        raw.render(queue);
    }

    private float fromBoolean(boolean bool) {
        return bool ? 1 : 0;
    }

    public RawActor getRaw() {
        return raw;
    }

    public void setRaw(RawActor raw) {
        this.raw = raw;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}

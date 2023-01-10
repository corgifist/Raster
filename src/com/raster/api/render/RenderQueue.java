package com.raster.api.render;

import com.raster.Raster;
import com.raster.api.actors.AbstractActor;
import com.raster.api.gl.ShaderProgram;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderQueue {

    private RenderContext context;

    private ConcurrentLinkedQueue<AbstractActor> queue;

    private ShaderProgram shader;

    public RenderQueue(RenderContext context) {
        Raster.checkCapabilities("cannot create render queue when gl capabilities is not available");
        this.context = context;
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public AbstractActor poll() {
        return queue.poll();
    }

    public AbstractActor push(AbstractActor actor) {
        queue.add(actor);
        return actor;
    }

    public AbstractActor push(AbstractActor... actors) {
        Arrays.stream(actors).forEach(this::push);
        return actors[0];
    }

    public void render() {
        if (shader == null) {
            throw new RenderException("no shader specified");
        }
        ConcurrentLinkedQueue<AbstractActor> storage = new ConcurrentLinkedQueue<>(queue);
        AbstractActor actor;
        while ((actor = poll()) != null) {
            actor.render(this);
        }
        queue = storage;
    }

    public void reset() {
        queue.clear();
    }

    public RenderContext getContext() {
        return context;
    }

    public void setContext(RenderContext context) {
        this.context = context;
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public void setShader(ShaderProgram shader) {
        this.shader = shader;
        this.shader.use();
    }
}

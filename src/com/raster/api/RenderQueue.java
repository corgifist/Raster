package com.raster.api;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderQueue {

    private RenderContext context;

    private ConcurrentLinkedQueue<AbstractActor> queue;

    private ShaderProgram shader;

    public RenderQueue(RenderContext context) {
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

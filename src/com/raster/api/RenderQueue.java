package com.raster.api;

import java.util.concurrent.ConcurrentLinkedQueue;

import static org.lwjgl.opengl.GL11.*;

public class RenderQueue {

    private RenderContext context;

    private ConcurrentLinkedQueue<Actor> queue;

    private ShaderProgram shader;

    public RenderQueue(RenderContext context) {
        this.context = context;
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public Actor poll() {
        return queue.poll();
    }

    public Actor push(Actor actor) {
        queue.add(actor);
        return actor;
    }

    public void render() {
        if (shader == null) {
            throw new RenderException("no shader specified");
        }
        ConcurrentLinkedQueue<Actor> storage = new ConcurrentLinkedQueue<>(queue);
        Actor actor;
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

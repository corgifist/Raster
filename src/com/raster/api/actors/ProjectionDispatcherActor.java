package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.gl.ColorAttachment;
import com.raster.api.gl.Framebuffer;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderQueue;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class ProjectionDispatcherActor implements AbstractActor, Dispatcher {

    private Framebuffer projectionFramebuffer;
    private StaticMeshActor quadMesh;
    private ShaderProgram projectionShader;
    private boolean projectionBound;
    private int width, height;

    private AbstractActor[] postProcessBuffer;

    public static boolean isDispatchingRightNow = false;

    public ProjectionDispatcherActor(int width, int height) {
        setupProjectionFramebuffer(width, height);

        this.projectionShader = new ShaderProgram("projection");
        this.quadMesh = new StaticMeshActor("quad.obj");
        quadMesh.setScale(new Vector3f(10));
        quadMesh.setPosition(new Vector3f(0, 0, -1));

        this.width = width;
        this.height = height;

        this.postProcessBuffer = new AbstractActor[0];
    }

    public static void checkScope() {
        if (!isDispatchingRightNow) {
            Raster.warning("PostProcessingWrongScope", "post-processing effects doesn't work outside of ProjectionDispatcherActor");
        }
    }

    @Override
    public void render(RenderQueue queue) {
        if (projectionBound) {
            Raster.warning("WrongBindOrders", "projection framebuffer is bound, but render of that projection was requested \n\t(rejecting this render request)");
            return;
        }

        isDispatchingRightNow = true;
        glDisable(GL_DEPTH_TEST);
        ShaderProgram initialShader = queue.getShader();
        queue.setShader(projectionShader);
        projectionShader.setUniform("metadata.resolution", new Vector2f(width, height));

        projectionFramebuffer.updateAttachmentSampler(0, queue, 0);
        projectionFramebuffer.updateAttachmentSampler(1, queue, 1);

        for (AbstractActor effect : postProcessBuffer) {
            effect.render(queue);
        }

        quadMesh.render(queue);
        queue.setShader(initialShader);
        glEnable(GL_DEPTH_TEST);
        isDispatchingRightNow = false;
    }

    @Override
    public void dispatch(RenderQueue queue, AbstractActor... objects) {
        for (AbstractActor processor : objects) {
            if (!processor.getClass().getSuperclass().getSimpleName().equals("PostProcessor")) {
                Raster.warning("NotPostProcessor", "non-post-processing actor was passed to post-processing dispatcher\n\t(rejecting dispatch request)");
                return;
            }
        }
        postProcessBuffer = objects;
    }

    public void resize(int width, int height) {
        projectionFramebuffer.delete();
        setupProjectionFramebuffer(width, height);
        this.width = width;
        this.height = height;
    }

    public void setupProjectionFramebuffer(int width, int height) {
        this.projectionFramebuffer = new Framebuffer(width, height,
                new ColorAttachment(width, height, 0)
              , new ColorAttachment(width, height, 1));
        projectionFramebuffer.setAttachmentSamplerName(0, "projectionTextures.diffuse");
        projectionFramebuffer.setAttachmentSamplerName(1, "projectionTextures.depth");
    }

    public void bind() {
        projectionBound = true;
        projectionFramebuffer.bind();
    }

    public void unbind() {
        projectionBound = false;
        projectionFramebuffer.unbind();
    }
}

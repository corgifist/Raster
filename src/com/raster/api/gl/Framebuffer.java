package com.raster.api.gl;

import com.raster.api.render.RenderException;
import com.raster.api.render.RenderQueue;

import static org.lwjgl.opengl.GL30.*;

public class Framebuffer {

    private int fbo, rbo;
    private ColorAttachment[] attachments;
    private int width, height;

    public Framebuffer(int width, int height, ColorAttachment... attachments) {
        this.width = width;
        this.height = height;
        this.attachments = attachments;

        this.fbo = glGenFramebuffers();
        bind();

        int[] generatedAttachments = new int[attachments.length];
        for (ColorAttachment attachment : attachments) {
            generatedAttachments[attachment.getIndex()] = GL_COLOR_ATTACHMENT0 + attachment.getIndex();
            attachment.attach();
        }

        glDrawBuffers(generatedAttachments);

        this.rbo = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rbo);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new RenderException("framebuffer is not complete!");
        }

        unbind();
    }

    public void delete() {
        glDeleteFramebuffers(fbo);
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int getFbo() {
        return fbo;
    }

    public void setFbo(int fbo) {
        this.fbo = fbo;
    }

    public int getRbo() {
        return rbo;
    }

    public void setRbo(int rbo) {
        this.rbo = rbo;
    }

    public ColorAttachment[] getAttachments() {
        return attachments;
    }

    public void setAttachments(ColorAttachment[] attachments) {
        this.attachments = attachments;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAttachmentSamplerName(int i, String s) {
        attachments[i].getTexture().setSamplerName(s);
    }

    public void updateAttachmentSampler(int i, RenderQueue queue, int i1) {
        attachments[i].getTexture().updateSampler(queue, i1);
    }
}

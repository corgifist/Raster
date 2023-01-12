package com.raster.api.gl;

import static org.lwjgl.opengl.GL30.*;

public class ColorAttachment {

    private Texture texture;
    private int index;

    public ColorAttachment(int width, int height, int index) {
        this.index = index;

        this.texture = new Texture(width, height);
    }

    public void attach() {
        texture.bind();
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + index, GL_TEXTURE_2D, texture.getTextureID(), 0);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

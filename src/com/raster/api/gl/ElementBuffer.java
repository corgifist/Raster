package com.raster.api.gl;

import static org.lwjgl.opengl.GL15.*;

public class ElementBuffer {

    private int object;

    private int[] data;

    public ElementBuffer(int[] data) {
        this.object = glGenBuffers();
        this.data = data;

        bind();
        bufferData(data);
        unbind();
    }

    public void bufferData(int[] data) {
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, object);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getObject() {
        return object;
    }

    public void setObject(int object) {
        this.object = object;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }
}

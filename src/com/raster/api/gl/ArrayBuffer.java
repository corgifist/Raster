package com.raster.api.gl;

import static org.lwjgl.opengl.GL15.*;

public class ArrayBuffer {

    private int buffer;
    private float[] data;
    private int size;

    public ArrayBuffer(float[] data, int size) {
        this.buffer = glGenBuffers();
        this.data = data;
        this.size = size;

        bind();
        bufferData(data);
        unbind();
    }

    public void delete() {
        glDeleteBuffers(buffer);
    }

    public void bufferData(float[] data) {
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, buffer);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

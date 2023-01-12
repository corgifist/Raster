package com.raster.api.gl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class VertexAttributes {

    private int vao;

    private ArrayBuffer[] attributes;
    private ElementBuffer indices;

    public VertexAttributes(ElementBuffer indices, ArrayBuffer... attributes) {
        this.vao = glGenVertexArrays();
        this.attributes = attributes;
        this.indices = indices;

        bind();
        for (int i = 0; i < attributes.length; i++) {
            ArrayBuffer buffer = attributes[i];
            buffer.bind();
            attributePointer(i, buffer.getSize());
        }
        indices.bind();
        unbind();
    }

    public void delete() {
        glDeleteVertexArrays(vao);
    }

    public void attributePointer(int index, int size) {
        glVertexAttribPointer(index, size, GL_FLOAT, false, size * Float.BYTES, NULL);
        glEnableVertexAttribArray(index);
    }

    public void bind() {
        glBindVertexArray(vao);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public int getVao() {
        return vao;
    }

    public void setVao(int vao) {
        this.vao = vao;
    }

    public ArrayBuffer[] getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayBuffer[] attributes) {
        this.attributes = attributes;
    }

    public ElementBuffer getIndices() {
        return indices;
    }

    public void setIndices(ElementBuffer indices) {
        this.indices = indices;
    }
}

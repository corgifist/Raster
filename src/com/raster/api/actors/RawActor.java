package com.raster.api.actors;

import com.raster.api.gl.ArrayBuffer;
import com.raster.api.gl.ElementBuffer;
import com.raster.api.render.RenderQueue;
import com.raster.api.gl.VertexAttributes;

import static org.lwjgl.opengl.GL11.*;

public class RawActor extends AbstractActor {

    private ArrayBuffer verticesBuffer, texCoordsBuffer, normalsBuffer;

    private ElementBuffer indicesBuffer;

    private VertexAttributes attributes;


    public RawActor(float[] vertices, int[] indices, float[] texCoords, float[] normals) {
        this.verticesBuffer = new ArrayBuffer(vertices, 3);
        this.texCoordsBuffer = new ArrayBuffer(texCoords, 2);
        this.normalsBuffer = new ArrayBuffer(normals, 3);
        this.indicesBuffer = new ElementBuffer(indices);
        this.attributes = new VertexAttributes(indicesBuffer, verticesBuffer, texCoordsBuffer, normalsBuffer);
    }

    @Override
    public void render(RenderQueue queue) {
        attributes.bind();
        glDrawElements(GL_TRIANGLES, indicesBuffer.getData().length, GL_UNSIGNED_INT, 0);
    }

    public ArrayBuffer getVerticesBuffer() {
        return verticesBuffer;
    }

    public void setVerticesBuffer(ArrayBuffer verticesBuffer) {
        this.verticesBuffer = verticesBuffer;
    }

    public VertexAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(VertexAttributes attributes) {
        this.attributes = attributes;
    }

    public ElementBuffer getIndicesBuffer() {
        return indicesBuffer;
    }

    public void setIndicesBuffer(ElementBuffer indicesBuffer) {
        this.indicesBuffer = indicesBuffer;
    }

    public ArrayBuffer getTexCoordsBuffer() {
        return texCoordsBuffer;
    }

    public void setTexCoordsBuffer(ArrayBuffer texCoordsBuffer) {
        this.texCoordsBuffer = texCoordsBuffer;
    }

    public ArrayBuffer getNormalsBuffer() {
        return normalsBuffer;
    }

    public void setNormalsBuffer(ArrayBuffer normalsBuffer) {
        this.normalsBuffer = normalsBuffer;
    }
}

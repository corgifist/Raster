package com.raster.api;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Actor {

    private ArrayBuffer verticesBuffer, texCoordsBuffer;

    private ElementBuffer indicesBuffer;

    private VertexAttributes attributes;

    private Vector3f position, rotation, scale;

    private Matrix4f transformation;

    private Texture diffuse;

    public Actor(float[] vertices, int[] indices, float[] texCoords) {
        this.verticesBuffer = new ArrayBuffer(vertices, 3);
        this.texCoordsBuffer = new ArrayBuffer(texCoords, 2);
        this.indicesBuffer = new ElementBuffer(indices);
        this.attributes = new VertexAttributes(indicesBuffer, verticesBuffer, texCoordsBuffer);
        this.transformation = new Matrix4f();

        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1);
    }

    public void render(RenderQueue queue) {
        transformation.identity()
                .translate(position)
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .rotateZ(rotation.z)
                .scale(scale);

        queue.getShader().setUniform("transformations.transformation", transformation);


        attributes.bind();
        diffuse.updateSampler(queue, 0);
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

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public ArrayBuffer getTexCoordsBuffer() {
        return texCoordsBuffer;
    }

    public void setTexCoordsBuffer(ArrayBuffer texCoordsBuffer) {
        this.texCoordsBuffer = texCoordsBuffer;
    }

    public Matrix4f getTransformation() {
        return transformation;
    }

    public void setTransformation(Matrix4f transformation) {
        this.transformation = transformation;
    }

    public Texture getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Texture diffuse) {
        this.diffuse = diffuse;
    }
}

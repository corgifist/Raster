package com.raster.api.actors;

import com.raster.api.render.RenderQueue;
import com.raster.api.render.WorldMatrix;
import org.joml.Vector3f;

public class CameraActor implements AbstractActor {

    private Vector3f position, rotation;

    private float aspectRatio, fov, near, far;

    private int width, height;

    public CameraActor(Vector3f position, Vector3f rotation, int width, int height, float fov, float near, float far) {
        this.position = position;
        this.rotation = rotation;
        this.width = width;
        this.height = height;
        this.aspectRatio = (float) width / height;
        this.fov = fov;
        this.near = near;
        this.far = far;
    }

    public static CameraActor createResizedCamera(CameraActor camera, int width, int height) {
        return new CameraActor(camera.getPosition(), camera.getRotation(), width, height, camera.getFov(), camera.getNear(), camera.getFar());
    }

    @Override
    public void render(RenderQueue queue) {
        load(queue);
    }

    public void load(RenderQueue queue) {
        WorldMatrix.projection.identity().perspective((float) Math.toRadians(fov), aspectRatio, near, far);
        WorldMatrix.view.identity().translate(position)
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .rotateZ(rotation.z);

        queue.getShader().setUniform("transformations.view", WorldMatrix.view);
        queue.getShader().setUniform("transformations.projection", WorldMatrix.projection);

        queue.getShader().setUniform("metadata.cameraPosition", position);
    }

    public void resizeProjection(CameraActor camera) {
        this.position = camera.getPosition();
        this.rotation = camera.getRotation();
        this.width = camera.getWidth();
        this.height = camera.getHeight();
        this.aspectRatio = (float) camera.getWidth() / camera.getHeight();
        this.fov = camera.getFov();
        this.near = camera.getNear();
        this.far = camera.getFar();
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

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
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

    public float getNear() {
        return near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return far;
    }

    public void setFar(float far) {
        this.far = far;
    }
}

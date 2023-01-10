package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderQueue;
import com.raster.api.render.ViewMatrixType;
import com.raster.api.render.WorldMatrix;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class FreeCameraActor implements AbstractActor {

    private Vector3f cameraPos, cameraTarget, cameraDirection;
    private Vector3f up, cameraRight, cameraUp, cameraFront;

    private float cameraSpeed, cameraSensitivity;

    private CameraActor camera;

    private float yaw, pitch;
    private float lastX, lastY;

    private boolean firstMouse;

    private RenderContext context;

    public FreeCameraActor(CameraActor camera) {
        this.context = RenderContext.adapter.context();
        this.camera = camera;
        this.cameraPos = new Vector3f(0, 0, 1);
        this.cameraTarget = new Vector3f(0);
        this.cameraDirection = new Vector3f(cameraPos).sub(cameraTarget).normalize();

        this.up = new Vector3f(0, 1, 0);
        this.cameraRight = new Vector3f(up).cross(cameraDirection).normalize();
        this.cameraUp = new Vector3f(cameraDirection).cross(cameraRight);
        this.cameraFront = new Vector3f(0, 0, -1);

        this.cameraSpeed = 0.05f;
        this.cameraSensitivity = 0.1f;

        this.yaw = -90;
        this.lastX = (float) context.getWidth() / 2;
        this.lastY = (float) context.getHeight() / 2;

        this.firstMouse = true;
        // it is not recommended to use hardcoded glfw functions
        // it's better to wait safe "Raster" alternative
        // glfwSetInputMode(context.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    @Override
    public void render(RenderQueue queue) {
        WorldMatrix.viewMatrixType = ViewMatrixType.LOOKAT;
        camera.lookAt(cameraPos, new Vector3f(cameraPos).add(cameraFront), cameraUp);
        camera.setPosition(cameraPos);
        camera.render(queue);
    }

    public void mouse(Vector2f position) {
        if (firstMouse) {
            lastX = position.x;
            lastY = position.y;
            firstMouse = false;
        }
        float xOffset = position.x - lastX;
        float yOffset = lastY - position.y;

        lastX = position.x;
        lastY = position.y;

        xOffset *= cameraSensitivity;
        yOffset *= cameraSensitivity;

        yaw += xOffset;
        pitch += yOffset;

        if (pitch > 89) pitch = 89;
        if (pitch < -89) pitch = -89;

        Vector3f direction = new Vector3f();
        direction.x = (float) ((float) Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        direction.y = (float) Math.sin(Math.toRadians(pitch));
        direction.z =(float) ((float) Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        this.cameraFront = direction.normalize();
    }

    public void keyboard(RenderContext context) {
        if (context.keyState(GLFW_KEY_W)) {
            cameraPos.add(new Vector3f(cameraSpeed).mul(cameraFront));
        }
        if (context.keyState(GLFW_KEY_S)) {
            cameraPos.sub(new Vector3f(cameraSpeed).mul(cameraFront));
        }
        if (context.keyState(GLFW_KEY_A)) {
            cameraPos.sub(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(cameraSpeed));
        }
        if (context.keyState(GLFW_KEY_D)) {
            cameraPos.add(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(cameraSpeed));
        }
    }

    public Vector3f getCameraPos() {
        return cameraPos;
    }

    public void setCameraPos(Vector3f cameraPos) {
        this.cameraPos = cameraPos;
    }

    public Vector3f getCameraTarget() {
        return cameraTarget;
    }

    public void setCameraTarget(Vector3f cameraTarget) {
        this.cameraTarget = cameraTarget;
    }

    public Vector3f getCameraDirection() {
        return cameraDirection;
    }

    public void setCameraDirection(Vector3f cameraDirection) {
        this.cameraDirection = cameraDirection;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }

    public Vector3f getCameraRight() {
        return cameraRight;
    }

    public void setCameraRight(Vector3f cameraRight) {
        this.cameraRight = cameraRight;
    }

    public Vector3f getCameraUp() {
        return cameraUp;
    }

    public void setCameraUp(Vector3f cameraUp) {
        this.cameraUp = cameraUp;
    }

    public Vector3f getCameraFront() {
        return cameraFront;
    }

    public void setCameraFront(Vector3f cameraFront) {
        this.cameraFront = cameraFront;
    }

    public float getCameraSpeed() {
        return cameraSpeed;
    }

    public void setCameraSpeed(float cameraSpeed) {
        this.cameraSpeed = cameraSpeed;
    }

    public void resetState() {
        WorldMatrix.viewMatrixType = ViewMatrixType.SCRATCH;
    }

    public float getCameraSensitivity() {
        return cameraSensitivity;
    }

    public void setCameraSensitivity(float cameraSensitivity) {
        this.cameraSensitivity = cameraSensitivity;
    }

    public CameraActor getCamera() {
        return camera;
    }

    public void setCamera(CameraActor camera) {
        this.camera = camera;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getLastX() {
        return lastX;
    }

    public void setLastX(float lastX) {
        this.lastX = lastX;
    }

    public float getLastY() {
        return lastY;
    }

    public void setLastY(float lastY) {
        this.lastY = lastY;
    }

    public RenderContext getContext() {
        return context;
    }

    public void setContext(RenderContext context) {
        this.context = context;
    }
}

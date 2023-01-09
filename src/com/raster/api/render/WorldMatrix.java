package com.raster.api.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class WorldMatrix {

    public static Matrix4f transformation = new Matrix4f().identity();
    public static Matrix4f view = new Matrix4f().identity();
    public static Matrix4f projection = new Matrix4f().identity();

    public static ViewMatrixType viewMatrixType = ViewMatrixType.SCRATCH;

    public static void uploadTransformation(RenderQueue queue) {
        queue.getShader().setUniform("transformations.transformation", transformation);
        queue.getShader().setUniform("transformations.view", view);
        queue.getShader().setUniform("transformations.projection", projection);
    }

    public static void updateTransformations(Vector3f position, Vector3f rotation, Vector3f scale) {
        transformation.identity()
                .translate(position)
                .rotateX(rotation.x)
                .rotateY(rotation.y)
                .rotateZ(rotation.z)
                .scale(scale);
    }

    public static void identity() {
        transformation.identity();
    }
}

package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderException;
import com.raster.api.render.RenderQueue;
import com.raster.api.gl.Texture;
import com.raster.api.render.WorldMatrix;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;

public class StaticMeshActor implements AbstractActor {

    private ArrayList<AbstractActor> actors;

    private Vector3f position, rotation, scale;
    private Vector3f color, tint;

    private float specularIntensity;

    public StaticMeshActor(String path) {
        Raster.checkCapabilities("cannot create actors when gl capabilities are not available");
        this.actors = new ArrayList<>();
        reset();
        processModel("assets/" + path);
    }

    public StaticMeshActor(StaticMeshActor source) {
        this.actors = source.getActors();
        reset();
    }

    @Override
    public void render(RenderQueue queue) {
        WorldMatrix.updateTransformations(position, rotation, scale);
        WorldMatrix.uploadTransformation(queue);
        updateObjectProperties(queue);
        for (AbstractActor actor : actors) {
            actor.render(queue);
        }
    }

    public void reset() {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.scale = new Vector3f(1);
        this.color = new Vector3f(1);
        this.tint = new Vector3f(1);
        this.specularIntensity = 0.2f;
    }

    private void updateObjectProperties(RenderQueue queue) {
        ShaderProgram shader = queue.getShader();
        shader.setUniform("properties.tint", tint);
        shader.setUniform("properties.specularIntensity", specularIntensity);
        shader.setUniform("properties.color", color);
    }

    private void processModel(String path) {
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate
                | Assimp.aiProcess_OptimizeMeshes
                | Assimp.aiProcess_GenUVCoords
                | Assimp.aiProcess_GenSmoothNormals);
        if (scene == null
                || (scene.mFlags() & Assimp.AI_SCENE_FLAGS_INCOMPLETE) == Assimp.AI_SCENE_FLAGS_INCOMPLETE
                || scene.mRootNode() == null) {
            throw new RenderException("assimp failure: " + Assimp.aiGetErrorString());
        }
        processNode(scene.mRootNode(), scene);
        Assimp.aiReleaseImport(scene);
    }

    private void processNode(AINode node, AIScene scene) {
        PointerBuffer meshesBuffer = scene.mMeshes();
        IntBuffer meshesIndicesBuffer = node.mMeshes();
        for (int i = 0; i < node.mNumMeshes(); i++) {
            AIMesh mesh = AIMesh.create(meshesBuffer.get(meshesIndicesBuffer.get(i)));
            actors.add(processActor(mesh, scene));
        }

        PointerBuffer childrenBuffer = node.mChildren();
        for (int i = 0; i < node.mNumChildren(); i++) {
            AINode child = AINode.create(childrenBuffer.get(i));
            processNode(child, scene);
        }
    }

    private RawTexturedActor processActor(AIMesh mesh, AIScene scene) {
        ArrayList<Float> vertices = new ArrayList<>();
        ArrayList<Float> texCoords = new ArrayList<>();
        ArrayList<Float> normals = new ArrayList<>();

        ArrayList<Integer> indices = new ArrayList<>();

        AIVector3D.Buffer verticesBuffer = mesh.mVertices();
        AIVector3D.Buffer texCoordsBuffer = mesh.mTextureCoords(0);
        AIVector3D.Buffer normalsBuffer = mesh.mNormals();

        for (int i = 0; i < mesh.mNumVertices(); i++) {
            AIVector3D vertexPos = verticesBuffer.get(i);
            vertices.add(vertexPos.x());
            vertices.add(vertexPos.y());
            vertices.add(vertexPos.z());

            if (texCoordsBuffer != null) {
                AIVector3D texCoordsPos = texCoordsBuffer.get(i);
                texCoords.add(texCoordsPos.x());
                texCoords.add(texCoordsPos.y());
            } else {
                texCoords.add(0f); texCoords.add(0f);
            }

            AIVector3D normalPos = normalsBuffer.get(i);
            normals.add(normalPos.x());
            normals.add(normalPos.y());
            normals.add(normalPos.z());
        }

        AIFace.Buffer facesBuffer = mesh.mFaces();
        for (int i = 0; i < mesh.mNumFaces(); i++) {
            AIFace face = facesBuffer.get(i);

            IntBuffer indicesBuffer = face.mIndices();
            for (int j = 0; j < face.mNumIndices(); j++) {
                indices.add(indicesBuffer.get(j));
            }
        }

        Texture possibleDiffuse = null;
        int materialIndex = mesh.mMaterialIndex();
        if (materialIndex >= 0) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                PointerBuffer materialsBuffer = scene.mMaterials();
                AIMaterial material = AIMaterial.create(materialsBuffer.get(materialIndex));

                AIString diffuseString = AIString.calloc(stack);
                Assimp.aiGetMaterialTexture(material, Assimp.aiTextureType_DIFFUSE, 0, diffuseString,
                        (IntBuffer) null, null, null, null, null, null);

                String diffusePath = diffuseString.dataString();
                if (diffusePath != null && diffusePath.length() > 0) {
                    possibleDiffuse = Texture.create(diffusePath);
                    possibleDiffuse.setSamplerName("properties.diffuseSampler");
                }
            }
        }

        float[] verticesArray = listToArrayFloat(vertices);
        float[] texCoordsArray = listToArrayFloat(texCoords);
        float[] normalsArray = listToArrayFloat(normals);

        int[] indicesArray = listToArrayInteger(indices);

        RawActor raw = new RawActor(verticesArray, indicesArray, texCoordsArray, normalsArray);
        return new RawTexturedActor(raw, possibleDiffuse);
    }

    private float[] listToArrayFloat(ArrayList<Float> list) {
        float[] array = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private int[] listToArrayInteger(ArrayList<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public ArrayList<AbstractActor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<AbstractActor> actors) {
        this.actors = actors;
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

    public Vector3f getTint() {
        return tint;
    }

    public void setTint(Vector3f tint) {
        this.tint = tint;
    }

    private float fromBoolean(boolean bool) {
        return bool ? 1f : 0f;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getSpecularIntensity() {
        return specularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        this.specularIntensity = specularIntensity;
    }
}

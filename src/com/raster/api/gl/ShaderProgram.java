package com.raster.api.gl;

import com.raster.Raster;
import com.raster.api.render.RenderException;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Random;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20C.glCreateShader;

public class ShaderProgram {

    private int programID;

    private HashMap<String, Integer> uniformsCache;

    public ShaderProgram(String vertexShader, String fragmentShader) {
        try {
            this.uniformsCache = new HashMap<>();
            this.programID = glCreateProgram();

            int vertexID = createShader(GL_VERTEX_SHADER, vertexShader);
            int fragmentID = createShader(GL_FRAGMENT_SHADER, fragmentShader);

            glAttachShader(programID, vertexID);
            glAttachShader(programID, fragmentID);
            glLinkProgram(programID);

            glDeleteShader(vertexID);
            glDeleteShader(fragmentID);
        } catch (IOException ex) {
            throw new RenderException(ex.getMessage());
        }
    }

    public void setUniform(String name, Vector3f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer vectorData = stack.callocFloat(3);
            value.get(vectorData);
            glUniform3fv(getUniformLocation(name), vectorData);
        }
    }

    public void setUniform(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform(String name, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer matrixData = stack.callocFloat(16); // 4 * 4
            value.get(matrixData);
            glUniformMatrix4fv(getUniformLocation(name), false, matrixData);
        }
    }

    public void setUniform(String name, int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    private int getUniformLocation(String name) {
        if (uniformsCache.containsKey(name)) return uniformsCache.get(name);
        int location = glGetUniformLocation(programID, name);
        if (location == -1) Raster.warning("UnreferencedUniform", "unreferenced uniform " + name + " (glsl compiler strips off unused uniforms, maybe it is a problem?)");
        uniformsCache.put(name, location);
        return uniformsCache.get(name);
    }

    public void use() {
        glUseProgram(programID);
    }

    public void deactivate() {
        glUseProgram(0);
    }

    private int createShader(int type, String path) throws IOException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            int shaderID = glCreateShader(type);
            String shaderSource = GLSLAPI.getAPIString(type) + "\n" + Files.readString(Path.of("shaders/" + path)); // another variable for debugging purposes
            System.out.println(shaderSource);
            glShaderSource(shaderID, shaderSource);
            glCompileShader(shaderID);

            IntBuffer success = stack.callocInt(1);
            glGetShaderiv(shaderID, GL_COMPILE_STATUS, success);
            if (success.get(0) != 1) {
                System.out.println(glGetShaderInfoLog(shaderID));
                throw new RenderException("failed to compile shader");
            }
            return shaderID;
        }
    }
}

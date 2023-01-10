package com.raster.api.gl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20C.GL_VERTEX_SHADER;

public class GLSLAPI {

    public static String VERTEX_API;
    public static String FRAGMENT_API;

    static {
        try {
            VERTEX_API = Files.readString(Path.of("shaders/vertex.api"));
            FRAGMENT_API = Files.readString(Path.of("shaders/fragment.api"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getAPIString(int type) {
        if (type == GL_VERTEX_SHADER) return VERTEX_API;
        return FRAGMENT_API;
    }
}

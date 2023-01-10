package com.raster.api.gl;

import com.raster.Raster;
import com.raster.api.render.RenderException;
import com.raster.api.render.RenderQueue;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.Pointer;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

public class CubemapTexture {

    private int textureID;

    private String samplerName;

    public CubemapTexture(String name) {
        Raster.checkCapabilities("cannot create cubemap when gl capabilities is not available");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            this.textureID = glGenTextures();
            String extension = (new File("assets/" + name + "_left.png").exists() ? "png" : "jpg");

            bind();

            // cubemap always have 6 sides
            for (int i = 0; i < 6; i++) {
                IntBuffer widthBuffer = stack.callocInt(1);
                IntBuffer heightBuffer = stack.callocInt(1);
                IntBuffer channelsBuffer = stack.callocInt(1);
                String concatenatedName = "assets/" + name + "_" + getSideString(i) + "." + extension;
                System.out.println(concatenatedName);
                ByteBuffer imageBuffer = stbi_load(concatenatedName, widthBuffer, heightBuffer, channelsBuffer, 0);
                int width = widthBuffer.get(0);
                int height = heightBuffer.get(0);
                int channels = channelsBuffer.get(0);
                stbi_set_flip_vertically_on_load(false);
                glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i,
                        0, Texture.getFormatByChannels(channels), width, height,
                        0, Texture.getFormatByChannels(channels), GL_UNSIGNED_BYTE, imageBuffer);
                stbi_image_free(imageBuffer);
                stbi_set_flip_vertically_on_load(false);
            }
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

            unbind();

            this.samplerName = "properties.cubemapSampler";
        }
    }


    public void updateSampler(RenderQueue queue, int index) {
        if (samplerName == null) throw new RenderException("cubemap sampler name is not defined!");
        glActiveTexture(GL_TEXTURE0 + index);
        bind();
        queue.getShader().setUniform(samplerName, index);
    }

    private String getSideString(int index) {
        return switch (index) {
            case 0 -> "right";
            case 1 -> "left";
            case 2 -> "top";
            case 3 -> "bottom";
            case 4 -> "front";
            case 5 -> "back";
            default -> "right";
        };
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }

}

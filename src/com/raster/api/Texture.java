package com.raster.api;

import org.lwjgl.opengl.GL11C;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL13C.GL_TEXTURE0;
import static org.lwjgl.opengl.GL30.GL_RG;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL46.GL_MAX_TEXTURE_MAX_ANISOTROPY;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private int textureID;
    private int width, height, channels;
    private String samplerName;

    public static float ANISOTROPY_LEVEL = 8;

    private static HashMap<String, Texture> cache = new HashMap<>();

    public static Texture create(String path) {
        if (cache.containsKey(path)) return cache.get(path);
        Texture texture = new Texture(path);
        cache.put(path, texture);
        return texture;
    }

    public Texture(String path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            stbi_set_flip_vertically_on_load(true);
            IntBuffer widthStack = stack.mallocInt(1);
            IntBuffer heightStack = stack.mallocInt(1);
            IntBuffer nrChannelsStack = stack.mallocInt(1);
            ByteBuffer data = stbi_load("assets/" + path, widthStack, heightStack, nrChannelsStack, 0);

            this.width = widthStack.get(0);
            this.height = heightStack.get(0);
            this.channels = nrChannelsStack.get(0);

            this.textureID = glGenTextures();
            bind();

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, getFormatByChannels(), GL_UNSIGNED_BYTE, data);
            glTexParameterf(GL_TEXTURE_2D, GL_MAX_TEXTURE_MAX_ANISOTROPY, ANISOTROPY_LEVEL);
            glGenerateMipmap(GL_TEXTURE_2D);
            stbi_image_free(data);
            unbind();

            this.samplerName = "diffuseSampler";
        }
    }

    public void updateSampler(RenderQueue queue, int index) {
        if (samplerName == null) throw new RenderException("no sampler name found!");
        glActiveTexture(GL_TEXTURE0 + index);
        bind();
        queue.getShader().setUniform(samplerName, index);
    }

    public int getFormatByChannels() {
        return switch (channels) {
            case 1 -> GL_R;
            case 2 -> GL_RG;
            case 3 -> GL_RGB;
            case 4 -> GL_RGBA;
            default -> GL_RGB;
        };
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
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

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public String getSamplerName() {
        return samplerName;
    }

    public void setSamplerName(String samplerName) {
        this.samplerName = samplerName;
    }
}
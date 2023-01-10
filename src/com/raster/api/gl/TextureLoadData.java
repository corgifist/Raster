package com.raster.api.gl;

// not really part of gl api but required for loading textures, cubemaps and etc

import java.nio.ByteBuffer;

public class TextureLoadData {

    private ByteBuffer imageBuffer;
    private int width, height, nrChannels;

    public TextureLoadData(ByteBuffer imageBuffer, int width, int height, int nrChannels) {
        this.imageBuffer = imageBuffer;
        this.width = width;
        this.height = height;
        this.nrChannels = nrChannels;
    }

    public ByteBuffer getImageBuffer() {
        return imageBuffer;
    }

    public void setImageBuffer(ByteBuffer imageBuffer) {
        this.imageBuffer = imageBuffer;
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

    public int getNrChannels() {
        return nrChannels;
    }

    public void setNrChannels(int nrChannels) {
        this.nrChannels = nrChannels;
    }

    @Override
    public String toString() {
        return "TextureLoadData{" +
                "imageBuffer=" + imageBuffer +
                ", width=" + width +
                ", height=" + height +
                ", nrChannels=" + nrChannels +
                '}';
    }
}

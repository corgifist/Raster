package com.raster.util;

import com.raster.api.render.RenderContext;

public interface ApplicationAdapter {

    void prepare();

    void event();

    void render();

    void resize(int width, int height);

    RenderContext context();
}

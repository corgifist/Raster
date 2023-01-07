package com.raster.util;

import com.raster.api.render.RenderContext;

public interface ApplicationAdapter {

    void prepare();

    void event();

    void render();

    RenderContext context();
}

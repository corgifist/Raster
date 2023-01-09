package com.raster.util;

import com.raster.Raster;
import com.raster.api.render.RenderContext;
import org.joml.Vector2f;

public interface ApplicationAdapter {

    void prepare();

    void event();

    default void mouseMoveEvent(Vector2f position) {
        if (Raster.mouseMoveEventWarningPrinted) return;
        Raster.warning("AdapterMethodMissing", "'mouseMoveEvent' is not defined (not critical, but anyway..)");
        Raster.mouseMoveEventWarningPrinted = true;
    }

    void render();

    void resize(int width, int height);

    RenderContext context();
}

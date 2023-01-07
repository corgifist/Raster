package com.raster.api.actors;

import com.raster.api.render.RenderQueue;

public interface LightDispatcher {

    void dispatch(RenderQueue queue, AbstractActor... lights);

}

package com.raster.api.actors;

import com.raster.api.render.RenderQueue;

public interface Dispatcher {

    void dispatch(RenderQueue queue, AbstractActor... objects);

}

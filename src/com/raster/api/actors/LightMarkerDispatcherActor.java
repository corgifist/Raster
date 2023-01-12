package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderQueue;
import org.joml.Vector3f;

public class LightMarkerDispatcherActor implements AbstractActor, Dispatcher {

    private StaticMeshActor markMesh;
    private ShaderProgram solidShader;

    public LightMarkerDispatcherActor() {
        this.markMesh = new StaticMeshActor("cube.obj");
        this.markMesh.setScale(new Vector3f(0.05f));
        this.solidShader = new ShaderProgram("solid");
    }

    @Override
    public void render(RenderQueue queue) {
        Raster.warning("RenderWarning", "LightMarkerDispatcherActor is not renderable, dispatch it instead");
    }

    @Override
    public void dispatch(RenderQueue queue, AbstractActor... objects) {
        ShaderProgram initialShader = queue.getShader();
        queue.setShader(solidShader);
        for (AbstractActor light : objects) {
            if (!light.getClass().getSimpleName().equals("PointLightActor")) {
                Raster.warning("WrongActorType", light.getClass().getSimpleName() + " is not markable! (can't draw light mark for " + light.getClass() + ")");
                continue;
            }
            PointLightActor pla = (PointLightActor) light;
            markMesh.setColor(pla.getColor());
            markMesh.setPosition(pla.getPosition());
            markMesh.render(queue);
        }

        queue.setShader(initialShader);
    }
}

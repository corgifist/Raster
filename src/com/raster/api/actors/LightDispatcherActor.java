package com.raster.api.actors;

import com.raster.Raster;
import com.raster.api.gl.ShaderProgram;
import com.raster.api.render.RenderQueue;

public class LightDispatcherActor implements AbstractActor, LightDispatcher{
    @Override
    public void render(RenderQueue queue) {
        Raster.warning("RenderWarning", "LightDispatcherActor cannot be rendered, but it is trying to be rendered!");
    }

    @Override
    public void dispatch(RenderQueue queue, AbstractActor... lights) {
        ShaderProgram shader = queue.getShader();
        int pointLightIndex = 0;
        int ambientLightExists = 0;
        for (AbstractActor light : lights) {
            if (light instanceof PointLightActor pla) {
                shader.setUniform(getPointString(pointLightIndex, "position"), pla.getPosition());
                shader.setUniform(getPointString(pointLightIndex, "color"), pla.getColor());

                shader.setUniform(getPointString(pointLightIndex, "specularDamper"), pla.getSpecularDamper());

                shader.setUniform(getPointString(pointLightIndex, "linear"), pla.getLinear());
                shader.setUniform(getPointString(pointLightIndex, "constant"), pla.getConstant());
                shader.setUniform(getPointString(pointLightIndex, "quadratic"), pla.getQuadratic());

                pointLightIndex++;
            }

            if (light instanceof AmbientLightActor ala) {
                shader.setUniform("ambient.intensity", ala.getIntensity());
                shader.setUniform("ambient.color", ala.getColor());
                ambientLightExists = 1;
            }
        }
        shader.setUniform("metadata.pointLightCount", (float) pointLightIndex);
        shader.setUniform("metadata.ambientLightExists", (float) ambientLightExists);
    }

    private String getPointString(int index, String attribute) {
        return "points[" + index + "]." + attribute;
    }
}
